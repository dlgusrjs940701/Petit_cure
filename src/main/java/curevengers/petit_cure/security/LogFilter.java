package curevengers.petit_cure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import curevengers.petit_cure.security.Provider.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@RequiredArgsConstructor
public class LogFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = "";
        String password = "";
        StringBuilder requestBody = new StringBuilder();
        try(BufferedReader reader = request.getReader()){
            String line;
            while ((line = reader.readLine())!=null){
                requestBody.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("request body를 읽을 수 없음",e);
        }

        System.out.println("Request Body: "+requestBody.toString());

        username = request.getParameter("username").toString();
        password = request.getParameter("password").toString();
        System.out.println(username + "/" +password+"(LogFilter에서 토큰을 얻기전상태)");

        // 해당 username과 password를 검증하기위하여 token에 담아서 provider에 넘겨줄 것
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,password,null);

        return authenticationManager.authenticate(authToken);
    }

    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        System.out.println(username+"(아이디 인증 후 토큰 생성과정의 userDeatils에 저장된 username)");
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();
        System.out.println("인증된 사용자의 권한 : "+role);
        String token = tokenProvider.createToken(username,role);

        response.addHeader("Authorization","Bearer"+token);     // 헤더에 인가를 담아서 전달
    }

    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed){
        response.setStatus(401);
    }


}
