package kr.co.fittnerserver.service.user;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.common.CommonErrorCode;
import kr.co.fittnerserver.common.CommonException;
import kr.co.fittnerserver.dto.user.LoginRequestDto;
import kr.co.fittnerserver.dto.user.TestDto;
import kr.co.fittnerserver.dto.user.TestPageDto;
import kr.co.fittnerserver.dto.user.TokenResDto;
import kr.co.fittnerserver.entity.BlackListToken;
import kr.co.fittnerserver.entity.common.RefreshToken;
import kr.co.fittnerserver.entity.user.Trainer;
import kr.co.fittnerserver.repository.BlackListTokenRepository;
import kr.co.fittnerserver.repository.common.RefreshTokenRepository;
import kr.co.fittnerserver.repository.user.TrainerRepository;
import kr.co.fittnerserver.results.CacheablePage;
import kr.co.fittnerserver.results.MtnPageable;
import kr.co.fittnerserver.util.AES256Cipher;
import kr.co.fittnerserver.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final JwtTokenUtil jwtTokenUtil;
    private final TrainerRepository trainerRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final BlackListTokenRepository blackListTokenRepository;

    @Transactional
    public TokenResDto loginProcess(LoginRequestDto loginRequestDto) throws Exception {
        Trainer trainer = trainerRepository.findByTrainerEmail(AES256Cipher.encrypt(loginRequestDto.getTrainerEmail()))
                .orElseThrow(() -> new CommonException(CommonErrorCode.NOT_FOUND_TRAINER.getCode(), CommonErrorCode.NOT_FOUND_TRAINER.getMessage()));

        //엑세스 토큰 생성
        String accessToken = jwtTokenUtil.generateAccessToken(trainer.getTrainerId());
        //리프레시 토큰 생성
        String refreshToken = jwtTokenUtil.generateRefreshToken(trainer.getTrainerId());

        RefreshToken refreshTokenByTrainer = refreshTokenRepository.findByTrainer(trainer).orElse(null);
        if (refreshTokenByTrainer != null) {
            refreshTokenByTrainer.updateToken(refreshToken);
            return new TokenResDto(accessToken, refreshTokenByTrainer.getRefreshTokenId());
        } else {
            RefreshToken refreshTokenInfo = refreshTokenRepository.save(new RefreshToken(refreshToken, trainer));
            return new TokenResDto(accessToken, refreshTokenInfo.getRefreshTokenId());
        }
    }

    @Transactional
    public void logoutProcess(HttpServletRequest request, CustomUserDetails customUserDetails) throws Exception {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Bearer 토큰 부분만 추출
            String accessToken = authorizationHeader.substring(7); // "Bearer " 부분을 제거
            blackListTokenRepository.save(new BlackListToken(accessToken, customUserDetails.getTrainerEmail()));
        } else {
            new RuntimeException("로그아웃 실패");
        }
    }

    public TestDto listTest() {
        TestDto testDto =new TestDto();
        testDto.setTest("test");
        return testDto;
    }

    public Page<Object> pageTest(Pageable pageable) {
        Page<Trainer> all = trainerRepository.findAll(pageable);
        return new CacheablePage<>(
                all.map(trainer -> {
                    return TestPageDto.builder()
                            .trainerName(trainer.getTrainerName())
                            .build();
                })
        );
    }
}
