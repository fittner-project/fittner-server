package kr.co.fittnerserver.service.user;


import jakarta.persistence.EntityManager;
import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.common.CommonErrorCode;
import kr.co.fittnerserver.common.CommonException;
import kr.co.fittnerserver.dto.user.*;
import kr.co.fittnerserver.entity.admin.Center;
import kr.co.fittnerserver.entity.common.FileGroup;
import kr.co.fittnerserver.entity.user.*;
import kr.co.fittnerserver.repository.common.CenterJoinRepository;
import kr.co.fittnerserver.repository.common.CenterRepository;
import kr.co.fittnerserver.repository.common.FileGroupRepository;
import kr.co.fittnerserver.repository.common.FileRepository;
import kr.co.fittnerserver.repository.user.MemberRepository;
import kr.co.fittnerserver.repository.user.TicketRepository;
import kr.co.fittnerserver.repository.user.TrainerProductRepository;
import kr.co.fittnerserver.repository.user.TrainerRepository;
import kr.co.fittnerserver.results.CacheablePage;
import kr.co.fittnerserver.spec.MemberSpec;
import kr.co.fittnerserver.util.AES256Cipher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
    public Page<UserCenterListResDto> getCenterListByTrainer(CustomUserDetails customUserDetails, Pageable pageable) {
        Trainer trainer = trainerRepository.findById(customUserDetails.getTrainerId())
                .orElseThrow(() -> new CommonException(CommonErrorCode.NOT_FOUND_TRAINER.getCode(), CommonErrorCode.NOT_FOUND_TRAINER.getMessage()));

        return new CacheablePage<>(
                centerJoinRepository.findAllByCenterJoinApprovalYnAndTrainer("N", trainer, pageable)
                        .map(userCenter -> UserCenterListResDto.builder()
                                .centerJoinId(userCenter.getCenterJoinId())
                                .centerName(userCenter.getCenter().getCenterName())
                                .build()));
    }

    @Transactional
    public void registerUser(MemberRegisterReqDto memberRegisterReqDto, CustomUserDetails customUserDetails) throws Exception {
        Trainer trainer = trainerRepository.findById(customUserDetails.getTrainerId())
                .orElseThrow(() -> new CommonException(CommonErrorCode.NOT_FOUND_TRAINER.getCode(), CommonErrorCode.NOT_FOUND_TRAINER.getMessage()));

        //휴대번호 끝자리 저장
        String memberPhoneEnd = memberRegisterReqDto.getMemberPhone().substring(memberRegisterReqDto.getMemberPhone().length() - 4);

        //member 테이블 추가, 전화번호 암호화
        memberRegisterReqDto.setMemberPhone(AES256Cipher.encrypt(memberRegisterReqDto.getMemberPhone()));

        Member member = memberRepository.save(new Member(memberRegisterReqDto, trainer, memberPhoneEnd));

        //trainerproduct 테이블 추가
        TrainerProduct trainerProduct = trainerProductRepository.save(new TrainerProduct(memberRegisterReqDto, trainer, member));

        //ticket 테이블 추가
        ticketRepository.save(new Ticket(memberRegisterReqDto, trainerProduct, trainer, member));
    }

    @Transactional(readOnly = true)
    public Page<MemberListResDto> getMembers(MemberListSearchDto memberListSearchDto, CustomUserDetails customUserDetails, Pageable pageable) {

        Trainer trainer = trainerRepository.findById(customUserDetails.getTrainerId())
                .orElseThrow(() -> new CommonException(CommonErrorCode.NOT_FOUND_TRAINER.getCode(), CommonErrorCode.NOT_FOUND_TRAINER.getMessage()));

        Page<Member> members = memberRepository.findAll(MemberSpec.searchMember(memberListSearchDto,trainer), pageable);

        return new CacheablePage<>(
                members.map(member -> MemberListResDto.builder()
                        .memberId(member.getMemberId())
                        .memberName(member.getMemberName())
                        .memberPhone(member.getMemberPhone())
                        .memberAge(ageCalculate(member.getMemberBirth()))
                        .memberTotalCount(members.getTotalElements())
                        .build())
        );
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
    public Page<CenterListResDto> getCenterList(Pageable pageable) {
        return new CacheablePage<>(centerRepository.findAllByCenterDeleteYn("N", pageable)
                .map(center -> {
                    List<CenterFileResDto> fileUrls = new ArrayList<>();

                    if(center.getFileGroup() != null) {
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
                }));
    }
}
