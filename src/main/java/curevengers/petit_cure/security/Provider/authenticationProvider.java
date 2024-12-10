package curevengers.petit_cure.security.Provider;

import curevengers.petit_cure.security.userdetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;

@Component
public class authenticationProvider implements AuthenticationProvider {

    @Autowired
    private userdetailsService userDetailsService;

    private BCryptPasswordEncoder passwordEncoder;

    public authenticationProvider(BCryptPasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
//        System.out.println(username+"/"+password);

        User userDetails = (User)userDetailsService.loadUserByUsername(username);
//        System.out.println(userDetails.getUsername()+"/"+userDetails.getPassword());

        System.out.println(password+"(사용자 입력값)/"+userDetails.getPassword()+"(데이터저장값)/"+passwordEncoder.matches(password, userDetails.getPassword()));
        if(passwordEncoder.matches(password, userDetails.getPassword())==false) {
            throw new BadCredentialsException("Bad credentials");
        }

        System.out.println(userDetails.getAuthorities()+"/ user생성 단계에서 저장된 authorities값");

        List<GrantedAuthority> authorities = new ArrayList<>();
        if(userDetailsService.confirmMember(username).equals("KAKAO")){
            authorities.add(new SimpleGrantedAuthority("MEMBER"));
        }else if(userDetailsService.confirmMember(username).equals("USER")){
            authorities.add(new SimpleGrantedAuthority("USER"));
        }else{
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        }
        System.out.println(authorities.toString()+"/ ----------권한 확인용");


//        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(),userDetails.getPassword(),userDetails.getAuthorities());
        return new UsernamePasswordAuthenticationToken(userDetails,authentication.getCredentials(),authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
