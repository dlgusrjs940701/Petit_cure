package curevengers.petit_cure.security;

import curevengers.petit_cure.Dto.memberDTO;
import curevengers.petit_cure.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class userdetailsService implements UserDetailsService {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username+"(loadUserByname)");
        memberDTO memberdto = userService.getMemberById(username);

        UserDetails user = User.withUsername(memberdto.getId())
                .password(memberdto.getPass())
                .authorities(memberdto.getAuth_name())
                .build();

        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }


}
