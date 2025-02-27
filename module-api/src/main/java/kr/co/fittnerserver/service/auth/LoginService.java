package kr.co.fittnerserver.service.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import kr.co.fittnerserver.auth.CustomUserDetails;
import kr.co.fittnerserver.common.CommonErrorCode;
import kr.co.fittnerserver.common.CommonException;
import kr.co.fittnerserver.dto.user.user.*;
import kr.co.fittnerserver.dto.user.user.request.AccessTokenReqDto;
import kr.co.fittnerserver.dto.user.user.request.AppleRedirectReqDto;
import kr.co.fittnerserver.dto.user.user.request.LoginRequestDto;
import kr.co.fittnerserver.dto.user.user.response.TokenResDto;
import kr.co.fittnerserver.entity.BlackListToken;
import kr.co.fittnerserver.entity.user.Trainer;
import kr.co.fittnerserver.entity.user.TrainerRefreshToken;
import kr.co.fittnerserver.entity.user.enums.TrainerStatus;
import kr.co.fittnerserver.exception.JwtException;
import kr.co.fittnerserver.mapper.user.user.UserMapper;
import kr.co.fittnerserver.repository.BlackListTokenRepository;
import kr.co.fittnerserver.repository.user.TrainerRefreshTokenRepository;
import kr.co.fittnerserver.repository.user.TrainerRepository;
import kr.co.fittnerserver.results.CacheablePage;
import kr.co.fittnerserver.util.AES256Cipher;
import kr.co.fittnerserver.util.AppleJwtUtil;
import kr.co.fittnerserver.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final JwtTokenUtil jwtTokenUtil;
    private final TrainerRepository trainerRepository;
    private final TrainerRefreshTokenRepository trainerRefreshTokenRepository;
    private final BlackListTokenRepository blackListTokenRepository;
    private final UserMapper userMapper;

    @Transactional
    public TokenResDto loginProcess(LoginRequestDto loginRequestDto) throws Exception {
        Trainer trainer = trainerRepository.findByTrainerEmail(AES256Cipher.encrypt(loginRequestDto.getTrainerEmail()))
                .orElseThrow(() -> new CommonException(CommonErrorCode.NOT_FOUND_TRAINER.getCode(), CommonErrorCode.NOT_FOUND_TRAINER.getMessage()));

        if(trainer.getTrainerStatus().equals(TrainerStatus.INACTIVE)) {
            throw new CommonException(CommonErrorCode.NOT_ACTIVE_TRAINER.getCode(), CommonErrorCode.NOT_ACTIVE_TRAINER.getMessage());
        }

        //엑세스 토큰 생성
        String accessToken = jwtTokenUtil.generateAccessToken(trainer.getTrainerId());
        //리프레시 토큰 생성
        String refreshToken = jwtTokenUtil.generateRefreshToken(trainer.getTrainerId());

        TrainerRefreshToken trainerRefreshTokenByTrainer = trainerRefreshTokenRepository.findByTrainer(trainer).orElse(null);
        if (trainerRefreshTokenByTrainer != null) {
            trainerRefreshTokenByTrainer.updateToken(refreshToken);
            return new TokenResDto(accessToken, trainerRefreshTokenByTrainer.getRefreshTokenId());
        } else {
            TrainerRefreshToken trainerRefreshTokenInfo = trainerRefreshTokenRepository.save(new TrainerRefreshToken(trainer, refreshToken));
            return new TokenResDto(accessToken, trainerRefreshTokenInfo.getRefreshTokenId());
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
            throw new JwtException(CommonErrorCode.LOGOUT_FAIL.getCode(), CommonErrorCode.LOGOUT_FAIL.getMessage());
        }
    }

    public TestDto listTest() {
        TestDto testDto = new TestDto();
        testDto.setTrainerName("test");
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

    public List<TestDto> mybatisTest() {
        return userMapper.selectTrainerInfo();
    }


    @Transactional
    public TokenResDto makeToken(AccessTokenReqDto accessTokenReqDto) {
        String trainerId = jwtTokenUtil.validateTokenAndGetTrainerId(accessTokenReqDto.getAccessToken());
        if (trainerId != null) {
            throw new JwtException(CommonErrorCode.GOOD_TOKEN.getCode(), CommonErrorCode.GOOD_TOKEN.getMessage());
        }

        TrainerRefreshToken trainerRefreshToken = trainerRefreshTokenRepository.findById(accessTokenReqDto.getRefreshTokenId())
                .orElseThrow(() -> new CommonException(CommonErrorCode.NOT_FOUND_REFRESH_TOKEN_INFO.getCode(), CommonErrorCode.NOT_FOUND_REFRESH_TOKEN_INFO.getMessage()));

        //리프레시 토큰 유효 검증
        String refreshTrainerId = jwtTokenUtil.validateTokenAndGetTrainerId(trainerRefreshToken.getRefreshToken());

        //리프레시 토큰이 유효하지 않을때 리프레시 토큰 재생성
        if (refreshTrainerId == null) {
            String newRefreshToken = jwtTokenUtil.generateRefreshToken(trainerRefreshToken.getTrainer().getTrainerId());
            trainerRefreshToken.updateToken(newRefreshToken);
        }
        String newAccessToken = jwtTokenUtil.generateAccessToken(trainerRefreshToken.getTrainer().getTrainerId());
        return new TokenResDto(newAccessToken, trainerRefreshToken.getRefreshTokenId());
    }

    public RedirectView redirectUrl(AppleRedirectReqDto appleRedirectReqDto) throws Exception {
        if(appleRedirectReqDto.getUser() != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode userNode = objectMapper.readTree(appleRedirectReqDto.getUser());
            String email = userNode.get("email").asText();
            return new RedirectView("https://m.fittner.co.kr/sign-in?email=" + email);
        }else{
            return new RedirectView("https://m.fittner.co.kr/sign-in");
        }
    }
}
