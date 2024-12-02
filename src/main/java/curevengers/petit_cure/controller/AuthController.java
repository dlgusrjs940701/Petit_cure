package curevengers.petit_cure.controller;

import curevengers.petit_cure.Dto.Token.SignInDto;
import curevengers.petit_cure.Dto.Token.TokenDTO;
import curevengers.petit_cure.security.Provider.TokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Iterator;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    public AuthController(TokenProvider tokenProvider, AuthenticationManager authenticationManager) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

//    @PostMapping("/auth")
//    public ResponseEntity<TokenDTO> authenticate(@Valid @RequestBody SignInDto login) {
//        User user = (User) User.builder()
//                .username(login.getUsername())
//                .password(login.getPassword())
//                .build();
//        return ResponseEntity.ok(authenticationManager.authenticate(user));
//    }

    @ResponseBody
    @PostMapping("/authenticate")
    public ResponseEntity<TokenDTO> authorize(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletResponse response) throws IOException {

        // 헤더정보를 토대로 UsernamePasswordAuthenticationToken을 생성함
        // 생성한 토큰을 담아서 AuthenticationManager에서 authenticate()를 호출함
        // AuthenticationProvider에서 등록한 DetailsService를 호출
        // loadUserByUsername 메소드를 실행하여 DB에서 정보 확인하고 Authentication객체에 UserDetails담아서 리턴
        // 리턴된 Authentication 객체를 SecurityContext에 담음(인가)
        // Authentication 정보를 기반으로 JWT를 생성
        // 응답헤더에 담아서 클라이언트에 리턴
        System.out.println(username+"이 토큰생성쪽으로 들어옴");

        // 1. usernamePasswordToken을 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        // 2. 토큰기반 authenticate() -> loadUserbyUsername()
        // 생선된 토큰을 넘겨서 권한을 생성
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        // 3. Token 기반 권한(인가)
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 4. JWT 생성
        Iterator<? extends GrantedAuthority> iterator = authentication.getAuthorities().iterator();
        String jwt = tokenProvider.createToken(username,iterator.next().getAuthority());
        System.out.println("생성된 토큰은"+jwt);

//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + jwt);
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        if(jwt!=null){
            // 'secure' 'httpOnly'가 설정된 cookie에 token을 담음
            Cookie cookie = new Cookie("JWT",jwt);
            cookie.setPath("/loginsuc");
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            response.addCookie(cookie);
            System.out.println("생성된 쿠키 : "+cookie);
        }

//        response.sendRedirect("/loginsuc");
        // 응답헤더에 jwt를 추가
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION,"Bearer"+jwt)
                .body(TokenDTO.builder().token(jwt).build());

    }

//    @GetMapping("/refresh")
//    public ResponseEntity<AccessTokenResponse> getAccessToken(HttpServletRequest request){
//        // 사용자 요청 쿠키에서 refresh token을 꺼내서 검증하고, 새로운 refresh token을 쿠키에 설정하고
//        // body로 새로운 accessToken을 반화해준다.(즉 이 과정은 만료된 token을 사용자 확인 후 업데이트해주는 느낌)
//        String refreshToken = getRefreshToken(request);
//    }




}
