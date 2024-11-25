package curevengers.petit_cure.common.security;

import curevengers.petit_cure.Service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthProvider implements AuthenticationProvider {
    @Autowired
    private userService userservice;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String id = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
