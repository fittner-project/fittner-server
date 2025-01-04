package kr.co.fittnerserver.service.user;


import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.common.CommonErrorCode;
import kr.co.fittnerserver.common.CommonException;
import kr.co.fittnerserver.dto.user.JoinReqDto;
import kr.co.fittnerserver.dto.user.MemberRegisterReqDto;
import kr.co.fittnerserver.dto.user.UserCenterListResDto;
import kr.co.fittnerserver.entity.admin.Center;
import kr.co.fittnerserver.entity.user.*;
import kr.co.fittnerserver.repository.common.CenterJoinRepository;
import kr.co.fittnerserver.repository.common.CenterRepository;
import kr.co.fittnerserver.repository.user.MemberRepository;
import kr.co.fittnerserver.repository.user.TicketRepository;
import kr.co.fittnerserver.repository.user.TrainerProductRepository;
import kr.co.fittnerserver.repository.user.TrainerRepository;
import kr.co.fittnerserver.util.AES256Cipher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final TrainerRepository trainerRepository;
    private final CenterRepository centerRepository;
    private final CenterJoinRepository centerJoinRepository;
    private final TrainerProductRepository trainerProductRepository;
    private final MemberRepository memberRepository;
    private final TicketRepository ticketRepository;

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
        Trainer trainer = trainerRepository.save(new Trainer(joinReqDto, center));

        //센터조인 테이블에 추가
        centerJoinRepository.save(new CenterJoin(center, trainer));
    }

    private void joinValidation(JoinReqDto joinReqDto) throws Exception {
        if (trainerRepository.existsByTrainerEmail(AES256Cipher.encrypt(joinReqDto.getTrainerEmail()))) {
            throw new CommonException(CommonErrorCode.ALREADY_TRAINER.getCode(), CommonErrorCode.ALREADY_TRAINER.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<UserCenterListResDto> getCenterList(CustomUserDetails customUserDetails) {
        Trainer trainer = trainerRepository.findById(customUserDetails.getTrainerId())
                .orElseThrow(() -> new CommonException(CommonErrorCode.NOT_FOUND_TRAINER.getCode(), CommonErrorCode.NOT_FOUND_TRAINER.getMessage()));


        return centerJoinRepository.findAllByCenterJoinApprovalYnAndTrainer("Y", trainer)
                .stream()
                .map(userCenter -> UserCenterListResDto.builder()
                        .centerJoinId(userCenter.getCenterJoinId())
                        .centerName(userCenter.getCenter().getCenterName())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public void registerUser(MemberRegisterReqDto memberRegisterReqDto, CustomUserDetails customUserDetails) throws Exception {
        Trainer trainer = trainerRepository.findById(customUserDetails.getTrainerId())
                .orElseThrow(() -> new CommonException(CommonErrorCode.NOT_FOUND_TRAINER.getCode(), CommonErrorCode.NOT_FOUND_TRAINER.getMessage()));

        //member 테이블 추가, 전화번호 암호화
        memberRegisterReqDto.setMemberPhone(AES256Cipher.encrypt(memberRegisterReqDto.getMemberPhone()));
        Member member = memberRepository.save(new Member(memberRegisterReqDto, trainer));

        //trainerproduct 테이블 추가
        TrainerProduct trainerProduct = trainerProductRepository.save(new TrainerProduct(memberRegisterReqDto, trainer, member));

        //ticket 테이블 추가
        ticketRepository.save(new Ticket(memberRegisterReqDto, trainerProduct, trainer, member));
    }
}
