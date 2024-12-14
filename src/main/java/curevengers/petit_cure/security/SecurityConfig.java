package curevengers.petit_cure.security;

import curevengers.petit_cure.Service.UserServiceImpl;
import curevengers.petit_cure.kakaoapi.KakaoApi;
import curevengers.petit_cure.kakaoapi.PrincipalOauth2UserService;
import curevengers.petit_cure.security.Provider.TokenProvider;
import curevengers.petit_cure.security.handler.CustomLogoutSuccessHandler;
import curevengers.petit_cure.security.handler.JwtAccessDeniedHandler;
import curevengers.petit_cure.security.LogFilter;
import curevengers.petit_cure.security.handler.SecurityContextLogoutHandler;
import jakarta.servlet.http.HttpSession;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


import java.util.Collections;

@EnableWebSecurity
@Configuration
public class SecurityConfig {


    private final TokenProvider tokenProvider;
//    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;
    private final AuthenticationConfiguration authenticationConfiguration;
//    private final PrincipalOauth2UserService userService;
    private final SecurityContextLogoutHandler securityContextLogoutHandler;



    public static final String[] allowUrls = {
            "/freeboardVisitList","/qaboardVisitList","/dpboardVisitList","/mainpagelist",
            "/login","/","/mplus","/api/**","/memplus","/idCheck","/images/**",
            "/css**/**","/resources**/**","/freeboard*","/qanda*","company","/notice","/noticeview",
            "/api/user/**","/api/authenticate","/kakaomplus","/qaboardVisitList","/freeboardVisitList","/error"
    };

    public SecurityConfig(AuthenticationFailureHandler failureHandler,AuthenticationSuccessHandler successHandler,
                          TokenProvider tokenProvider, SecurityContextLogoutHandler securityContextLogoutHandler,
                          AuthenticationConfiguration authenticationConfiguration) {
        this.failureHandler = failureHandler;
        this.tokenProvider = tokenProvider;
        this.authenticationConfiguration = authenticationConfiguration;
        this.successHandler = successHandler;
        this.securityContextLogoutHandler = securityContextLogoutHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http    .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(allowUrls).permitAll()
                        .requestMatchers("/oauth2/code/kakao").permitAll()
                        .requestMatchers("user/**").hasAnyAuthority("USER","MEMBER","ADMIN")
                        .requestMatchers("/depboard","/dpview").hasAnyAuthority("MEMBER","ADMIN")
                        .requestMatchers("/withdraw","/alert","withdrawview","/alertview").hasAnyAuthority("ADMIN")
                        .anyRequest().authenticated() // 모든 요청 인증 필요

                        // 기본적으로 메인화면, 로그인 화면, 회원가입 화면은 모두 이용가능하다

                        // 비회원이 게시판의 글을 볼수는 있지만, 글작성-댓글작성은 불가능하게 하고자하는 컨셉
                        // 비회원은 자유게시판, Q&A 게시판의 글 목록만 볼 수 있으며
                        // 자세히 보기 및 글작성-댓글작성은 불가하다

                        // 추가적으로 우울증게시판에 관련된사항은 카카오 로그인을 통하여
                        // 실명인증을 한 사람만 접근가능하도록 설정
                )
                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/login")
                        .authorizationEndpoint(authorizationEndpointConfig ->
                                authorizationEndpointConfig.baseUri("/oauth2/authorize")))
//                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(new PrincipalOauth2UserService()))
//                        .failureHandler(failureHandler))
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .loginProcessingUrl("/logincon")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .successHandler(successHandler)
                        .failureHandler(failureHandler)
                        ) // 로그인 페이지 활성화, 파라미터로 아이디, 비밀번호 받아서 인증진행

                .logout(formLogout -> formLogout
                .logoutUrl("/logout")   // 로그아웃 처리 URL
                .addLogoutHandler(securityContextLogoutHandler)
                .logoutSuccessUrl("/"))
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
                        .accessDeniedPage("/access-denied"));
        return http.build();
    }


    @Bean
    public BCryptPasswordEncoder passwordencoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

}
