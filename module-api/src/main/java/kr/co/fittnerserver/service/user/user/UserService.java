package kr.co.fittnerserver.service.user.user;


import jakarta.servlet.http.HttpServletRequest;
import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.common.CommonErrorCode;
import kr.co.fittnerserver.common.CommonException;
import kr.co.fittnerserver.domain.user.CenterJoinDto;
import kr.co.fittnerserver.domain.user.TrainerDto;
import kr.co.fittnerserver.dto.user.user.request.CancelCenterApprovalReqDto;
import kr.co.fittnerserver.dto.user.user.request.CenterRegisterReqDto;
import kr.co.fittnerserver.dto.user.user.request.JoinReqDto;
import kr.co.fittnerserver.dto.user.user.request.MemberRegisterReqDto;
import kr.co.fittnerserver.dto.user.user.response.*;
import kr.co.fittnerserver.entity.admin.Center;
import kr.co.fittnerserver.entity.common.FileGroup;
import kr.co.fittnerserver.entity.common.PushSet;
import kr.co.fittnerserver.entity.common.Terms;
import kr.co.fittnerserver.entity.common.TermsAgree;
import kr.co.fittnerserver.entity.common.enums.PushKind;
import kr.co.fittnerserver.entity.common.enums.TermsKind;
import kr.co.fittnerserver.entity.common.enums.TermsState;
import kr.co.fittnerserver.entity.user.*;
import kr.co.fittnerserver.entity.user.enums.TrainerStatus;
import kr.co.fittnerserver.exception.JwtException;
import kr.co.fittnerserver.mapper.user.user.UserMapper;
import kr.co.fittnerserver.repository.common.*;
import kr.co.fittnerserver.repository.user.*;
import kr.co.fittnerserver.util.AES256Cipher;
import kr.co.fittnerserver.util.PhoneFormatUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    private final FileRepository fileRepository;
    private final FileGroupRepository fileGroupRepository;
    private final TermsAgreeRepository termsAgreeRepository;
    private final TermsRepository termsRepository;
    private final UserMapper userMapper;
    private final PushSetRepository pushSetRepository;
    private final ReservationRepository reservationRepository;

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

        //센터조인 저장
        centerJoinRepository.save(new CenterJoin(center, trainer));

        //약관 동의 저장
        joinReqDto.getAgreements().forEach(agreement -> {
            Terms terms = termsRepository.findById(agreement.getTermsId())
                    .orElseThrow(() -> new CommonException(CommonErrorCode.NOT_FOUND_TERMS.getCode(), CommonErrorCode.NOT_FOUND_TERMS.getMessage()));
            termsAgreeRepository.save(new TermsAgree(terms, agreement.getAgreed(), trainer));
        });

        //push set 저장
        Terms terms = termsRepository.findByTermsKindAndTermsState(TermsKind.ADVERTISE, TermsState.ING)
                .orElseThrow(() -> new CommonException(CommonErrorCode.NOT_FOUND_TERMS.getCode(), CommonErrorCode.NOT_FOUND_TERMS.getMessage()));

        TermsAgree termsAgree = termsAgreeRepository.findByTermsAndTrainer(terms, trainer)
                .orElseThrow(() -> new CommonException(CommonErrorCode.NOT_FOUND_TERMS_AGREE.getCode(), CommonErrorCode.NOT_FOUND_TERMS_AGREE.getMessage()));

        pushSetRepository.save(new PushSet(trainer, PushKind.ADVERTISE, termsAgree.getTermsAgreeYn().equals("Y") ? "Y" : "N"));
        pushSetRepository.save(new PushSet(trainer, PushKind.RESERVATION, "Y"));
    }

    private void joinValidation(JoinReqDto joinReqDto) throws Exception {
        if (trainerRepository.existsByTrainerEmail(AES256Cipher.encrypt(joinReqDto.getTrainerEmail()))) {
            throw new CommonException(CommonErrorCode.ALREADY_TRAINER.getCode(), CommonErrorCode.ALREADY_TRAINER.getMessage());
        }
    }

    @Transactional
    public void registerUser(MemberRegisterReqDto memberRegisterReqDto, CustomUserDetails customUserDetails) throws Exception {
        Trainer trainer = trainerRepository.findById(customUserDetails.getTrainerId())
                .orElseThrow(() -> new CommonException(CommonErrorCode.NOT_FOUND_TRAINER.getCode(), CommonErrorCode.NOT_FOUND_TRAINER.getMessage()));

        //휴대번호 끝자리 저장
        String memberPhoneEnd = memberRegisterReqDto.getMemberPhone().substring(memberRegisterReqDto.getMemberPhone().length() - 4);

        //member 테이블 추가, 전화번호 암호화
        memberRegisterReqDto.setMemberPhone(AES256Cipher.encrypt(memberRegisterReqDto.getMemberPhone()));
        memberRegisterReqDto.setMemberBirth(AES256Cipher.encrypt(memberRegisterReqDto.getMemberBirth()));

        Member member = memberRepository.save(new Member(memberRegisterReqDto, trainer, memberPhoneEnd));

        //trainerproduct 테이블 추가
        TrainerProduct trainerProduct = trainerProductRepository.save(new TrainerProduct(memberRegisterReqDto, trainer, member));

        //ticket 테이블 추가
        ticketRepository.save(new Ticket(memberRegisterReqDto, trainerProduct, trainer, member));
    }

    @Transactional(readOnly = true)
    public List<MemberListResDto> getMembers(CustomUserDetails customUserDetails) {
        List<Member> members = memberRepository.findAllByTrainer(customUserDetails.getTrainerId());


        return members.stream().map(member -> {
            try {
                return MemberListResDto.builder()
                        .memberId(member.getMemberId())
                        .memberName(member.getMemberName())
                        .memberPhone(PhoneFormatUtil.formatPhoneNumber(AES256Cipher.decrypt(member.getMemberPhone())))
                        .memberGender(member.getMemberGender())
                        .memberAge(ageCalculate(AES256Cipher.decrypt(member.getMemberBirth())))
                        .memberTotalCount(members.size())
                        .reservation(reservationRepository.existsByMember(member))
                        .build();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    private String ageCalculate(String memberBirth) {
        // 생년월일 포맷 처리 (yyMMdd)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        LocalDate birthDate = LocalDate.parse(memberBirth, formatter);

        // 2000년 이전 생년월일 처리
        if (birthDate.getYear() > LocalDate.now().getYear()) {
            birthDate = birthDate.minusYears(100); // 1900년대로 변환
        }

        // 현재 날짜 가져오기
        LocalDate currentDate = LocalDate.now();

        // 나이 계산
        int age = currentDate.getYear() - birthDate.getYear();

        // 생일이 지나지 않은 경우 나이 1 감소
        if (currentDate.getMonthValue() < birthDate.getMonthValue() ||
                (currentDate.getMonthValue() == birthDate.getMonthValue() &&
                        currentDate.getDayOfMonth() < birthDate.getDayOfMonth())) {
            age--;
        }

        // 결과 반환 (String 타입)
        return String.valueOf(age);
    }

    @Transactional(readOnly = true)
    public List<CenterListResDto> getCenterList() {

        return centerRepository.findAllByCenterDeleteYn("N")
                .stream()
                .map(center -> {
                    List<CenterFileResDto> fileUrls = new ArrayList<>();

                    if (center.getFileGroup() != null) {
                        FileGroup fileGroup = fileGroupRepository.findById(center.getFileGroup().getFileGroupId())
                                .orElseThrow(() -> new CommonException(CommonErrorCode.NOT_FOUND_FILE_GROUP.getCode(), CommonErrorCode.NOT_FOUND_FILE_GROUP.getMessage()));

                        fileUrls = fileRepository.getFileUrl(fileGroup, "N");
                    }
                    return CenterListResDto.builder()
                            .centerId(center.getCenterId())
                            .centerName(center.getCenterName())
                            .centerAddress(center.getCenterAddress())
                            .centerTel(center.getCenterTel())
                            .centerImage(fileUrls)
                            .build();
                })
                .toList();
    }

    public UserInfoResDto getUserInfo(CustomUserDetails customUserDetails) throws Exception {
        UserInfoResDto r = new UserInfoResDto();

        //트레이너 기본정보
        UserInfoResDto.DefaultInfo defaultInfo = new UserInfoResDto.DefaultInfo();
        TrainerDto trainer = userMapper.selectTrainerByTrainerId(customUserDetails.getTrainerId());
        defaultInfo.setTrainerId(customUserDetails.getTrainerId());
        defaultInfo.setTrainerEmail(AES256Cipher.decrypt(trainer.getTrainerEmail()));
        defaultInfo.setTrainerSnsKind(String.valueOf(trainer.getTrainerSnsKind()));
        defaultInfo.setTrainerName(trainer.getTrainerName());
        r.setDefaultInfo(defaultInfo);

        //트레이너 센터정보
        List<UserInfoResDto.CenterInfo> centerInfoList = new ArrayList<>();
        List<CenterJoinDto> centerJoinDtoList = userMapper.selectCenterJoinByTrainerId(customUserDetails.getTrainerId());
        for (CenterJoinDto centerJoinDto : centerJoinDtoList) {
            UserInfoResDto.CenterInfo centerInfo = new UserInfoResDto.CenterInfo();
            centerInfo.setCenterGroupId(centerJoinDto.getCenterGroupId());
            centerInfo.setCenterId(centerJoinDto.getCenterId());
            centerInfo.setCenterName(centerJoinDto.getCenterName());
            centerInfo.setCenterJoinMainYn(centerJoinDto.getCenterJoinMainYn());
            centerInfoList.add(centerInfo);
        }
        r.setCenterInfo(centerInfoList);

        return r;
    }

    @Transactional
    public void registerCenter(CenterRegisterReqDto centerRegisterReqDto, CustomUserDetails customUserDetails) {
        Trainer trainer = trainerRepository.findById(customUserDetails.getTrainerId())
                .orElseThrow(() -> new CommonException(CommonErrorCode.NOT_FOUND_TRAINER.getCode(), CommonErrorCode.NOT_FOUND_TRAINER.getMessage()));

        Center center = centerRepository.findById(centerRegisterReqDto.getCenterId())
                .orElseThrow(() -> new CommonException(CommonErrorCode.NOT_FOUND_CENTER.getCode(), CommonErrorCode.NOT_FOUND_CENTER.getMessage()));

        centerJoinRepository.save(new CenterJoin(center, trainer));
    }

    @Transactional(readOnly = true)
    public List<UserCenterListResDto> getCenterListByTrainer(String userEmail) throws Exception {
        Trainer trainer = trainerRepository.findByTrainerEmail(AES256Cipher.encrypt(userEmail))
                .orElseThrow(() -> new CommonException(CommonErrorCode.NOT_FOUND_TRAINER.getCode(), CommonErrorCode.NOT_FOUND_TRAINER.getMessage()));

        return centerJoinRepository.findAllByTrainer(trainer)
                .stream()
                .map(userCenter -> UserCenterListResDto.builder()
                        .centerJoinId(userCenter.getCenterJoinId())
                        .centerId(userCenter.getCenter().getCenterId())
                        .centerName(userCenter.getCenter().getCenterName())
                        .centerAddress(userCenter.getTrainer().getCenter().getCenterAddress())
                        .centerJoinApprovalYn(userCenter.getCenterJoinApprovalYn())
                        .centerJoinMainYn(userCenter.getCenterJoinMainYn())
                        .build())
                .toList();
    }

    @Transactional
    public void cancelCenterApproval(CancelCenterApprovalReqDto cancelCenterApprovalReqDto) throws Exception {
        CenterJoin centerJoin = centerJoinRepository.findById(cancelCenterApprovalReqDto.getCenterJoinId())
                .orElseThrow(() -> new CommonException(CommonErrorCode.NOT_FOUND_CENTER_JOIN.getCode(), CommonErrorCode.NOT_FOUND_CENTER_JOIN.getMessage()));

        //승인 되지않은 센터조인과 동시에 본인이 요청한 센터조인인지 체크
        if (centerJoin.getCenterJoinApprovalYn().equals("N") && centerJoin.getTrainer().getTrainerEmail().equals(AES256Cipher.encrypt(cancelCenterApprovalReqDto.getUserEmail()))){
            centerJoinRepository.deleteById(cancelCenterApprovalReqDto.getCenterJoinId());
        } else {
            throw new CommonException(CommonErrorCode.NOT_MATCH_TRAINER.getCode(), CommonErrorCode.NOT_MATCH_TRAINER.getMessage());
        }
    }

    public List<TermsResDto> getJoinTerms() {
        return userMapper.selectTermsForJoin();
    }


    @Transactional
    public List<MemberDetailResDto> getMemberTicketDetailInfo(String memberId) {
        return userMapper.selectMemberTicketDetailInfo(memberId);
    }

    @Transactional
    public void dropTrainer(HttpServletRequest request, CustomUserDetails customUserDetails) {
        Trainer trainer = trainerRepository.findById(customUserDetails.getTrainerId())
                .orElseThrow(() -> new CommonException(CommonErrorCode.NOT_FOUND_TRAINER.getCode(), CommonErrorCode.NOT_FOUND_TRAINER.getMessage()));

        trainer.dropInfo();


        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Bearer 토큰 부분만 추출
            String accessToken = authorizationHeader.substring(7);
            //TODO 추후에 주석 풀기
            //dropTrainerRepository.save(new DropTrainer(accessToken, customUserDetails.getTrainerId()));
        } else {
            throw new JwtException(CommonErrorCode.DROP_FAIL.getCode(), CommonErrorCode.DROP_FAIL.getMessage());
        }
    }

    @Transactional
    public void deleteUser(String memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CommonException(CommonErrorCode.NOT_FOUND_MEMBER.getCode(), CommonErrorCode.NOT_FOUND_MEMBER.getMessage()));

        List<Ticket> ticketInfos = ticketRepository.findByMember(member);

        List<Reservation> reservationInfos = reservationRepository.findByMember(member);


        //회원 삭제, 티켓 삭제, 스케줄 삭제
        member.delete();
        ticketInfos.forEach(Ticket::delete);
        reservationInfos.forEach(Reservation::delete);
    }

    @Transactional(readOnly = true)
    public TrainerResDto getTrainers() {
        List<Trainer> trainers = trainerRepository.findAllByTrainerStatus(TrainerStatus.ACTIVE);

        List<TrainerResultDto> trainerResultDtos = trainers.stream()
                .map(trainer -> TrainerResultDto.builder()
                        .trainerName(trainer.getTrainerName())
                        .build())
                .toList();

        return TrainerResDto.builder()
                .trainerTotal(trainers.size())
                .trainerInfo(trainerResultDtos)
                .build();
    }
}
