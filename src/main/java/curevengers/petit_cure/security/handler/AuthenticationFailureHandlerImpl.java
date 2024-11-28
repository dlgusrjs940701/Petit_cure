package curevengers.petit_cure.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AuthenticationFailureHandlerImpl.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        logger.info("로그인에 실패하였습니다.");
        response.sendRedirect(request.getContextPath() + "/login");
    }
}
