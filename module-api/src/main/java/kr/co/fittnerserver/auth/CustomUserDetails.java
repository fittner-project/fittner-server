package kr.co.fittnerserver.auth;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
public class CustomUserDetails implements UserDetails {
    private String trainerId;
    private String trainerName;
    private String trainerPhone;
    private String trainerEmail;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(String trainerId,String trainerName,String trainerPhone,String trainerEmail, Collection<? extends GrantedAuthority> authorities) {
        this.trainerId = trainerId;
        this.trainerName = trainerName;
        this.trainerPhone = trainerPhone;
        this.trainerEmail = trainerEmail;
        this.authorities = authorities;


    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return trainerId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
