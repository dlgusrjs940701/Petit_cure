package curevengers.petit_cure.common.security;

import curevengers.petit_cure.Dto.memberDTO;
import curevengers.petit_cure.Service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthProvider implements AuthenticationProvider {
    @Autowired
    private userService userservice;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String id = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UsernamePasswordAuthenticationToken token;
        memberDTO memberdto = userservice.getMemberById(id);

        if(memberdto != null && passwordEncoder.matches(password, memberdto.getPass())){
            List<GrantedAuthority> roles = new ArrayList<>();
            roles.add(new SimpleGrantedAuthority("USER"));  // 권한 부여

            token = new UsernamePasswordAuthenticationToken(memberdto.getId(), null, roles);

            return token;
        }
        throw new BadCredentialsException("No such user or wrong password");
        // Exception을 던지지 않고 다른 값을 반환하면 authenticate() 메서드는 정상적으로 실행된 것이므로 인증되지 않았다면 Exception을 throw 해야 한다.

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
