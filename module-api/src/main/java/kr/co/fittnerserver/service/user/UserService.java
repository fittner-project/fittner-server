package kr.co.fittnerserver.service.user;


import kr.co.fittnerserver.common.CommonErrorCode;
import kr.co.fittnerserver.common.CommonException;
import kr.co.fittnerserver.dto.user.JoinReqDto;
import kr.co.fittnerserver.entity.admin.Center;
import kr.co.fittnerserver.entity.user.Trainer;
import kr.co.fittnerserver.repository.common.CenterRepository;
import kr.co.fittnerserver.repository.user.TrainerRepository;
import kr.co.fittnerserver.util.AES256Cipher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final TrainerRepository trainerRepository;
    private final CenterRepository centerRepository;

    @Transactional
    public void joinProcess(JoinReqDto joinReqDto) throws Exception {
        //이미 가입한 트레이너가 있는지 체크
        joinValidation(joinReqDto);

        //센터 지점 조회
        Center center = centerRepository.findById(joinReqDto.getCenterId())
                .orElseThrow(() -> new CommonException(CommonErrorCode.NOT_FOUND_CENTER.getCode(), CommonErrorCode.NOT_FOUND_CENTER.getMessage()));

        //데이터 암호화 (이메일, 전화번호)
        joinReqDto.setTrainerEmail(AES256Cipher.encrypt(joinReqDto.getTrainerEmail()));
        joinReqDto.setTrainerPhone(AES256Cipher.encrypt(joinReqDto.getTrainerPhone()));

        //트레이너 회원가입
        trainerRepository.save(new Trainer(joinReqDto,center));
    }

    private void joinValidation(JoinReqDto joinReqDto) throws Exception {
        if(trainerRepository.existsByTrainerEmail(AES256Cipher.encrypt(joinReqDto.getTrainerEmail()))) {
            throw new CommonException(CommonErrorCode.ALREADY_TRAINER.getCode(), CommonErrorCode.ALREADY_TRAINER.getMessage());
        }
    }
}
