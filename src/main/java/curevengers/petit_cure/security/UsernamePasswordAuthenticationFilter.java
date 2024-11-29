//package curevengers.petit_cure.security;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationManagerResolver;
//import org.springframework.security.authentication.AuthenticationServiceException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
//import org.springframework.security.web.authentication.AuthenticationConverter;
//import org.springframework.security.web.authentication.AuthenticationFilter;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.io.InvalidObjectException;
//import java.util.Objects;
//
//@Component
//public class UsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
//
//    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/login", "POST");
//    private boolean postOnly = true;
//    public UsernamePasswordAuthenticationFilter() {
//        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
//    }
//    @Autowired
//    authenticationProvider authenticationProvider;
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//
//        if(!this.postOnly && request.getMethod().equals("POST")) {
//            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
//        }
//        else if(Objects.isNull(username) || Objects.isNull(password)) {
//            throw new InvalidObjectException("Username and password are required");
//        }
//
//        return authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//    }
//}
