package curevengers.petit_cure.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;

@Component
public class AuthenticationFailureHandlerImpl extends SimpleUrlAuthenticationFailureHandler {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AuthenticationFailureHandlerImpl.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        logger.info("로그인에 실패하였습니다.");

        String errorMessage;
        if(exception instanceof BadCredentialsException){
            errorMessage = "비밀번호가 맞지 않습니다. 다시 확인해주세요.";
        }else if(exception instanceof InternalAuthenticationServiceException){
            errorMessage = "내부 시스템 문제로 요청을 처리할 수 없습니다. 관리자에게 문의하세요.";
        }else if(exception instanceof UsernameNotFoundException){
            errorMessage = "아이디가 존재하지 않습니다. 회원가입 진행 후 로그인 해주세요.";
        }else if(exception instanceof AuthenticationCredentialsNotFoundException){
            errorMessage = "인증 요청이 거부되었습니다. 관리자에게 문의하세요.";
        }else {
            errorMessage = "알 수 없는 이유로 로그인에 실패하였습니다. 관리자에게 문의하세요.";
        }
        errorMessage = URLEncoder.encode(errorMessage, "UTF-8");
        setDefaultFailureUrl("/login?error=true&exception="+errorMessage);

        logger.error(errorMessage + " / 에러 메세지 ");
        super.onAuthenticationFailure(request, response, exception);
    }
}
