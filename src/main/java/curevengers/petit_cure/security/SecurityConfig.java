package curevengers.petit_cure.security;

import curevengers.petit_cure.security.Provider.TokenProvider;
import curevengers.petit_cure.security.handler.JwtAccessDeniedHandler;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private TokenProvider tokenProvider;
    private final AuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;


    public static final String[] allowUrls = {
            "/login","/","/mplus","/api/**",
            "/css**/**","/resources**/**","/freeboard","/qanda","company",
            "/api/user/**","/login/oauth2/code/kakao"
    };




    public SecurityConfig(AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler,
                          TokenProvider tokenProvider,AuthenticationEntryPoint jwtAuthenticationEntryPoint,JwtAccessDeniedHandler jwtAccessDeniedHandler) {
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http    .csrf(csrf -> csrf.disable())
                .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(sessionManagement
                        -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(allowUrls).permitAll()
                        .requestMatchers("/user/**").hasRole("USER")
                        .anyRequest().authenticated() // 모든 요청 인증 필요

                        // 기본적으로 메인화면, 로그인 화면, 회원가입 화면은 모두 이용가능하다

                        // 비회원이 게시판의 글을 볼수는 있지만, 글작성-댓글작성은 불가능하게 하고자하는 컨셉
                        // 비회원은 자유게시판, Q&A 게시판의 글 목록만 볼 수 있으며
                        // 자세히 보기 및 글작성-댓글작성은 불가하다
                )
//                .oauth2Login(conf->conf
//                        .authorizationEndpoint(end -> end.baseUri("/oauth2/login"))
//                        .loginProcessingUrl("/login"))
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .loginProcessingUrl("/api/authenticate")
//                        .defaultSuccessUrl("/")
//                        .failureUrl("/login?error")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .successHandler(successHandler)
                        .failureHandler(failureHandler)
                        ) // 로그인 페이지 활성화, 파라미터로 아이디, 비밀번호 받아서 인증진행

                .logout(formLogout -> formLogout
                .logoutUrl("/logout")   // 로그아웃 처리 URL
                .addLogoutHandler((request, response, authentication) -> {
                    HttpSession session = request.getSession();
                    if(session != null) {
                        session.invalidate();           // logout시에 세션을 무효화
                    }
                })
                .logoutSuccessUrl("/"));
        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationProvider authenticationProvider) {
        return new ProviderManager(Collections.singletonList(authenticationProvider));
    }

    @Bean
    public PasswordEncoder passwordencoder() {
        return new BCryptPasswordEncoder();
    }

}
