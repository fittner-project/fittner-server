package kr.co.fittnerserver.service.user.sign;

import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.common.CommonErrorCode;
import kr.co.fittnerserver.common.CommonException;
import kr.co.fittnerserver.dto.user.sign.request.SignReqDto;
import kr.co.fittnerserver.dto.user.sign.response.SignResrvationDto;
import kr.co.fittnerserver.dto.user.sign.response.SignResrvationForMemberResDto;
import kr.co.fittnerserver.dto.user.sign.response.SignResrvationResDto;
import kr.co.fittnerserver.entity.common.File;
import kr.co.fittnerserver.entity.common.FileGroup;
import kr.co.fittnerserver.mapper.file.FileMapper;
import kr.co.fittnerserver.mapper.user.sign.SignMapper;
import kr.co.fittnerserver.results.FittnerPageable;
import kr.co.fittnerserver.service.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignService {

    private final SignMapper signMapper;
    private final FileMapper fileMapper;

    public SignResrvationResDto getReservations(String reservationStartDate, CustomUserDetails customUserDetails) throws Exception {

        SignResrvationResDto r = new SignResrvationResDto();

        List<SignResrvationDto> reservationDtoList = signMapper.selectReservationByTrainerId(customUserDetails.getTrainerId(), reservationStartDate);

        r.setReservationTotalCnt(String.valueOf(reservationDtoList.size()));
        r.setReservationList(reservationDtoList);

        return r;
    }

    public SignResrvationForMemberResDto getReservationsForMember(String ticketId, CustomUserDetails customUserDetails, FittnerPageable pageable){
        return signMapper.selectReservationByTicketId(customUserDetails.getTrainerId(), ticketId, pageable.getCurrentPageNo());
    }

    public void reservationSign(SignReqDto signReqDto, CustomUserDetails customUserDetails) throws Exception{
        //file 체크
        FileGroup fileGroupInfo = fileMapper.selectFileGroup(signReqDto.getFileGroupId());

        if(fileGroupInfo==null){
            throw new CommonException(CommonErrorCode.NOT_FOUND_FILE_GROUP.getCode(), CommonErrorCode.NOT_FOUND_FILE_GROUP.getMessage());
        }

        signMapper.insertSign(signReqDto,customUserDetails.getTrainerId());
    }

}
