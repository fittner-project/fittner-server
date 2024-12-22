package kr.co.fittnerserver.auth;

import kr.co.fittnerserver.entity.user.Trainer;
import kr.co.fittnerserver.repository.user.TrainerRepository;
import kr.co.fittnerserver.util.AES256Cipher;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final TrainerRepository trainerRepository;

    @Override
    public UserDetails loadUserByUsername(String trainerId) throws UsernameNotFoundException {
        Trainer trainer = null;
        try {
            trainer = trainerRepository.findById(trainerId)
                    .orElseThrow(() -> new RuntimeException("유저 정보를 찾을 수 없습니다."));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            return new CustomUserDetails(trainer.getTrainerId(),trainer.getTrainerName(),trainer.getTrainerPhone(),AES256Cipher.decrypt(trainer.getTrainerEmail()),List.of());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
