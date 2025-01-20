package kr.co.fittnerserver.service.user.sign;

import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.common.CommonErrorCode;
import kr.co.fittnerserver.common.CommonException;
import kr.co.fittnerserver.domain.user.ReservationDto;
import kr.co.fittnerserver.dto.user.sign.request.SignReqDto;
import kr.co.fittnerserver.dto.user.sign.response.SignResrvationDto;
import kr.co.fittnerserver.dto.user.sign.response.SignResrvationForMemberResDto;
import kr.co.fittnerserver.dto.user.sign.response.SignResrvationResDto;
import kr.co.fittnerserver.entity.common.File;
import kr.co.fittnerserver.entity.common.FileGroup;
import kr.co.fittnerserver.mapper.file.FileMapper;
import kr.co.fittnerserver.mapper.user.reservation.ReservationMapper;
import kr.co.fittnerserver.mapper.user.sign.SignMapper;
import kr.co.fittnerserver.mapper.user.ticket.TicketMapper;
import kr.co.fittnerserver.results.FittnerPageable;
import kr.co.fittnerserver.service.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignService {

    private final SignMapper signMapper;
    private final FileMapper fileMapper;
    private final TicketMapper ticketMapper;
    private final ReservationMapper reservationMapper;

    public SignResrvationResDto getReservations(String reservationStartDate, FittnerPageable pageable, CustomUserDetails customUserDetails) throws Exception {

        SignResrvationResDto r = new SignResrvationResDto();

        int totalCnt = signMapper.selectReservationByTrainerIdCnt(customUserDetails.getTrainerId(), reservationStartDate);

        List<SignResrvationDto> reservationDtoList = signMapper.selectReservationByTrainerId(customUserDetails.getTrainerId(), reservationStartDate, pageable.getCurrentPageNo());

        r.setReservationTotalCnt(String.valueOf(totalCnt));
        r.setReservationList(reservationDtoList);

        return r;
    }

    public SignResrvationForMemberResDto getReservationsForMember(String ticketId, CustomUserDetails customUserDetails, FittnerPageable pageable){
        return signMapper.selectReservationByTicketId(customUserDetails.getTrainerId(), ticketId, pageable.getCurrentPageNo());
    }

    @Transactional
    public void reservationSign(SignReqDto signReqDto, CustomUserDetails customUserDetails) throws Exception{
        //file 체크
        FileGroup fileGroupInfo = fileMapper.selectFileGroup(signReqDto.getFileGroupId());

        if(fileGroupInfo==null){
            throw new CommonException(CommonErrorCode.NOT_FOUND_FILE_GROUP.getCode(), CommonErrorCode.NOT_FOUND_FILE_GROUP.getMessage()); //파일 그룹을 찾을 수 없습니다.
        }

        //예약 체크
        ReservationDto reservationDto = reservationMapper.selectReservationByReservationId(signReqDto.getReservationId());
        if(reservationDto == null){
            throw new CommonException(CommonErrorCode.NOT_FOUND_RESERVATION.getCode(), CommonErrorCode.NOT_FOUND_RESERVATION.getMessage()); //수업 정보를 찾을 수 없습니다.
        }

        //서명 저장
        signMapper.insertSign(signReqDto,customUserDetails.getTrainerId());

        //이용권 사용 카운트+1
        ticketMapper.updateTicketForUseCnt(reservationDto.getTicketId());
    }

}
