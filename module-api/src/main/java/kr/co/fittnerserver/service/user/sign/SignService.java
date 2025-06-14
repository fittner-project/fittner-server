package kr.co.fittnerserver.service.user.sign;

import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.common.CommonErrorCode;
import kr.co.fittnerserver.common.CommonException;
import kr.co.fittnerserver.domain.user.ReservationDto;
import kr.co.fittnerserver.domain.user.TicketDto;
import kr.co.fittnerserver.domain.user.TrainerSettlementDto;
import kr.co.fittnerserver.dto.user.sign.request.SignReqDto;
import kr.co.fittnerserver.dto.user.sign.response.SignResrvationDto;
import kr.co.fittnerserver.dto.user.sign.response.SignResrvationForMemberResDto;
import kr.co.fittnerserver.dto.user.sign.response.SignResrvationResDto;
import kr.co.fittnerserver.entity.admin.enums.TrainerSettlementCode;
import kr.co.fittnerserver.entity.common.File;
import kr.co.fittnerserver.entity.common.FileGroup;
import kr.co.fittnerserver.mapper.file.FileMapper;
import kr.co.fittnerserver.mapper.user.reservation.ReservationMapper;
import kr.co.fittnerserver.mapper.user.sign.SignMapper;
import kr.co.fittnerserver.mapper.user.ticket.TicketMapper;
import kr.co.fittnerserver.mapper.user.user.UserMapper;
import kr.co.fittnerserver.results.FittnerPageable;
import kr.co.fittnerserver.service.file.FileService;
import kr.co.fittnerserver.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignService {

    private final SignMapper signMapper;
    private final FileMapper fileMapper;
    private final TicketMapper ticketMapper;
    private final ReservationMapper reservationMapper;
    private final UserMapper userMapper;

    public SignResrvationResDto getReservations(String reservationStartDate, String centerId, FittnerPageable pageable, CustomUserDetails customUserDetails) throws Exception {

        SignResrvationResDto r = new SignResrvationResDto();

        int totalCnt = signMapper.selectReservationByTrainerIdAndCenterIdCnt(centerId, customUserDetails.getTrainerId(), reservationStartDate);

        List<SignResrvationDto> reservationDtoList = new ArrayList<>();
        if(totalCnt > 0){
            reservationDtoList = signMapper.selectReservationByTrainerIdAndCenterId(centerId, customUserDetails.getTrainerId(), reservationStartDate, pageable.getCurrentPageNo());
        }

        r.setReservationTotalCnt(String.valueOf(totalCnt));
        r.setReservationList(reservationDtoList);

        return r;
    }

    public List<SignResrvationForMemberResDto> getReservationsForMember(String ticketId, CustomUserDetails customUserDetails, FittnerPageable pageable){
        return signMapper.selectReservationByTicketId(customUserDetails.getTrainerId(), ticketId, pageable.getCurrentPageNo());
    }

    @Transactional
    public void reservationSign(SignReqDto signReqDto, CustomUserDetails customUserDetails) throws Exception{
        //file 체크
        if(!StringUtils.isEmpty(signReqDto.getFileGroupId())){
            FileGroup fileGroupInfo = fileMapper.selectFileGroup(signReqDto.getFileGroupId());

            if(fileGroupInfo==null){
                throw new CommonException(CommonErrorCode.NOT_FOUND_FILE_GROUP.getCode(), CommonErrorCode.NOT_FOUND_FILE_GROUP.getMessage()); //파일 그룹을 찾을 수 없습니다.
            }
        }

        //예약 체크
        ReservationDto reservationDto = reservationMapper.selectReservationByReservationId(signReqDto.getReservationId());
        if(reservationDto == null){
            throw new CommonException(CommonErrorCode.NOT_FOUND_RESERVATION.getCode(), CommonErrorCode.NOT_FOUND_RESERVATION.getMessage()); //수업 정보를 찾을 수 없습니다.
        }
        if(!"WAITING".equals(reservationDto.getReservationStatus())){
            throw new CommonException(CommonErrorCode.ALREADY_SIGN.getCode(), CommonErrorCode.ALREADY_SIGN.getMessage()); //이미 처리된 예약입니다.
        }

        //이용권 체크
        TicketDto ticketDto = ticketMapper.selectTicketByTicketId(reservationDto.getTicketId(), "NORMAL");
        if(ticketDto == null){
            throw new CommonException(CommonErrorCode.NOT_FOUND_TICKET.getCode(), CommonErrorCode.NOT_FOUND_TICKET.getMessage()); //티켓을 찾을 수 없습니다.
        }

        //정산금액 정책 체크
        String settlementCode = signReqDto.getSignType();
        if("SIGN".equals(settlementCode)){
            if("Y".equals(ticketDto.getTicketRelayYn())){
                settlementCode = String.valueOf(TrainerSettlementCode.RE); //재등록 회원 정산코드
            }else{
                settlementCode = String.valueOf(TrainerSettlementCode.NEW); //신규 회원 정산코드
            }
        }
        TrainerSettlementDto trainerSettlementDto = userMapper.selectTrainerSettlementByTrainerIdAndSettlementCode(customUserDetails.getTrainerId(), settlementCode);
        if(trainerSettlementDto == null){
            throw new CommonException(CommonErrorCode.NOT_FOUND_SETTLEMENT.getCode(), CommonErrorCode.NOT_FOUND_SETTLEMENT.getMessage()); //정산 정책을 찾을 수 없습니다.
        }

        //서명은 수업이 시작해야만 가능하도록 변경
        if("SIGN".equals(signReqDto.getSignType())){
            String reservationStartDateTime = reservationDto.getReservationStartDate() + reservationDto.getReservationStartTime();
            String nowDate = Util.getFormattedToday("yyyyMMddHHmm");

            if(reservationStartDateTime.compareTo(nowDate) > 0){
                throw new CommonException(CommonErrorCode.SIGN_DATE_ERROR.getCode(), CommonErrorCode.SIGN_DATE_ERROR.getMessage()); //수업 시작전 서명이 불가합니다.
            }
        }

        //서명 저장
        signMapper.insertSign(signReqDto,customUserDetails.getTrainerId());

        //이용권 사용 카운트+1
        ticketMapper.updateTicketForUseCnt(reservationDto.getTicketId());

        //예약 상태 업데이트
        reservationMapper.updateReservationForSign(signReqDto.getReservationId(), signReqDto.getSignType(), customUserDetails.getTrainerId());

        //정산 금액 업데이트
        int ticketOneTimePrice = Integer.parseInt(ticketDto.getTicketPrice()) / Integer.parseInt(ticketDto.getTicketTotalCnt());
        double settlementPercent = Double.parseDouble(trainerSettlementDto.getTrainerSettlementPercent()) / 100.0;
        double settlementPrice = ticketOneTimePrice * settlementPercent;
        reservationMapper.updateReservationForSettlement(signReqDto.getReservationId(), customUserDetails.getTrainerId(), String.valueOf((int) settlementPrice), trainerSettlementDto.getTrainerSettlementId());
    }

}
