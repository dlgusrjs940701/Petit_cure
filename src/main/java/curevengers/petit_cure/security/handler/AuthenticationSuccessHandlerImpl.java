package curevengers.petit_cure.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AuthenticationSuccessHandlerImpl.class);


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info("로그인에 성공하였습니다~!!!");
        System.out.println(authentication.getAuthorities().stream());
        if(authentication.getAuthorities().stream().anyMatch(a -> "BLACKLIST".equals(a.getAuthority()))) {
            response.sendRedirect("/login/error");
        }else{
            response.sendRedirect("/loginsuc");
        }
    }
}
