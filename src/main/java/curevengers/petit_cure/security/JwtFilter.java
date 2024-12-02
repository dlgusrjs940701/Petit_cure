package curevengers.petit_cure.security;

import curevengers.petit_cure.security.Provider.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Slf4j
@Component
public class JwtFilter extends GenericFilterBean {
    // JwtFilter에서 요청을 인터셉트함
    // 요청 헤더 정보에서 토큰을 꺼내와서 TokenProvider로 토큰을 검증할 것
    // 검증을 통과한다면 Authentication 객체를 생성한다.
    // Session영역의 SecurityContext에 Authentication 객체를 담고 필터를 통과시킨다.

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private TokenProvider tokenProvider;

    public JwtFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    // 토큰의 인증정보를 SecurityContext에 저장함
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("JWT doFilter에 진입함");

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String jwt = resolveToken(httpServletRequest);
        String requestURI = httpServletRequest.getRequestURI();
        
        // 만약 jwt text가 존재하고, 해당값이 유효한 토큰이라면 -> securitycontext에 해당값을 저장함
        if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            Authentication auth = tokenProvider.getAuthentication(jwt);
            // SecurityContext에 Authentication 객체를 저장
            SecurityContextHolder.getContext().setAuthentication(auth);
            System.out.println("Security Context에 '{"+auth.getName()+"}' 인증정보를 저장했습니다, uri는 {"+requestURI+"}");
        }else {
            System.out.println("유효한 JWT 토큰이 없습니다. uri는 {+"+requestURI+"}");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
    
    // 요청값에서 온 request에서 header부분에 저장되어있는 토큰을 가져옴 
    // -> 해당 토큰 값이 존재하고 Bearer로 시작한다면(토큰 타입이 Bearer이라면) 앞 7글자를 반환
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);        // Bearer 이후의 값을 꺼내감
        }
        // Bearer이 아니면 null값을 반환해서 그냥 필터 통과시킬거임
        return null;
    }
}
