package curevengers.petit_cure.kakaoapi;

import curevengers.petit_cure.Dto.memberDTO;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
public class PrincipalDetails implements UserDetails, OAuth2User {

    private memberDTO memberdto;
    private Map<String, Object> attributes;
    private String role;
    private OAuth2UserInfo userInfo;

    // 일반 로그인
    public PrincipalDetails(memberDTO memberdto){
        this.memberdto = memberdto;
    }

    // oauth 로그인
    public PrincipalDetails(String role, OAuth2UserInfo userInfo) {
        this.role = role;
        this.userInfo = userInfo;
    }

    @Override
    public String getName() {
        return userInfo.phone();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }
}
