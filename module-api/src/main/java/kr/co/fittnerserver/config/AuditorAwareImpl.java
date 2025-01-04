package kr.co.fittnerserver.config;

import kr.co.fittnerserver.auth.CustomUserDetails;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {


    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(Authentication::isAuthenticated)
                .map(auth -> {
                    Object principal = auth.getPrincipal();
                    if (principal instanceof CustomUserDetails) {
                        CustomUserDetails userDetails = (CustomUserDetails) principal;
                        return userDetails.getTrainerId();
                    } else {
                        return "SYSTEM";
                    }
                });
    }
    }

