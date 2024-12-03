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

    // 일반 로그인
    public PrincipalDetails(memberDTO memberdto){
        this.memberdto = memberdto;
    }

    // oauth 로그인
    public PrincipalDetails(memberDTO memberdto, Map<String,Object> attributes){
        this.memberdto = memberdto;
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return "";
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
