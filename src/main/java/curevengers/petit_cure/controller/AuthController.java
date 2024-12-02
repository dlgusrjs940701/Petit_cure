package curevengers.petit_cure.controller;

import curevengers.petit_cure.Dto.Token.TokenDTO;
import curevengers.petit_cure.security.Provider.TokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public ResponseEntity<TokenDTO> authorize(@RequestParam("username") String username, @RequestParam("password") String password) {

        // 헤더정보를 토대로 UsernamePasswordAuthenticationToken을 생성함
        // 생성한 토큰을 담아서 AuthenticationManager에서 authenticate()를 호출함
        // AuthenticationProvider에서 등록한 DetailsService를 호출
        // loadUserByUsername 메소드를 실행하여 DB에서 정보 확인하고 Authentication객체에 UserDetails담아서 리턴
        // 리턴된 Authentication 객체를 SecurityContext에 담음(인가)
        // Authentication 정보를 기반으로 JWT를 생성
        // 응답헤더에 담아서 클라이언트에 리턴
        System.out.println(username+"이 토큰생성쪽으로 들어옴");
        
        // 1. usernamePasswordToken을 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password, null);
        // 2. 토큰기반 authenticate() -> loadUserbyUsername()
        // 생선된 토큰을 넘겨서 권한을 생성
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        // 3. Token 기반 권한(인가)
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 4. JWT 생성
        Iterator<? extends GrantedAuthority> iterator = authentication.getAuthorities().iterator();
        String jwt = tokenProvider.createToken(username,iterator.next().getAuthority());
        System.out.println("생성된 토큰은"+jwt);
        // 응답헤더에 jwt를 추가
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION,"Bearer"+jwt)
                .body(TokenDTO.builder().token(jwt).build());

    }

}
