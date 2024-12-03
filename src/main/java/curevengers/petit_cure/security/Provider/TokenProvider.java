package curevengers.petit_cure.security.Provider;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
@Configuration
public class TokenProvider implements InitializingBean {
// 유저 정보를 기반으로 토큰 생성+만료시간 체크

    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    private static final String AUTHORITIES_KEY = "auth";

    private final String secret;
    private final long tokenValidityInSeconds;

    private Key key;

    // properties에 미리 등록해놓은 secretkey값과 만료시간으로 TokenProvider를 생성
    public TokenProvider(@Value("${jwt.token.key}") String secret,
                         @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds) {
        this.secret = secret;
        this.tokenValidityInSeconds = tokenValidityInSeconds*1000;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // afterPropertiesSet은 InitializingBean 인터페이스의 메소드로 BeanFactory에 의해 모든 property가
        // 설정되고 난 뒤에 실행되는 메소드이다.
        // 생성자가 실행된 이후 시크릿 키를 Base64로 인코딩한 후 HMAC-SHA 알고리즘으로 암호화 할 것
        // 해시 함수를 이용해서 메시지 인증코드를 구성하는 것을 HMAC이라고 함
        // 원본 메시지가 변하면 그 해시값도 변하는 해싱의 특징을 활용하여 메시지 변조여부를 확인하여 무결성과 기밀성을 제공
        // HMAC은 인증을 위한 secret Key와 임의의 길이를 가진 Message를 해시함수 알고리즘을 사용해서 생성함
        // 해시 함수로는 MD5, SHA-256과 같은 일반적인 해시함수를 그대로 사용할 수 있으며 
        // 각 알고리즘에 따라 다른 고정길이의 MAC(Hash value)가 생성됨
        // HMAC-XXX에 붙는 XXX가 해시 알고리즘을 의미한다.
        // 각 알고리즘마다 최소or권장되는 secret key의 사이즈가 다름
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }


    // 토큰을 생성
    public String createToken(String username, String role) {

        long now = new Date().getTime();
        Date validity = new Date(now+this.tokenValidityInSeconds);

        return Jwts.builder()
                .claim("username",username)
                .claim("role",role)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(validity)
                .compact();
    }

    // 토큰을 해독 (즉, 이 메서드를 통하여 토큰으로부터 사용자의 Authentication 객체를 추출함)
    public Authentication getAuthentication(String token){
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String token) {
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SignatureException | MalformedJwtException e) {
            logger.info("잘못된 JWT 입니다.");
        } catch (ExpiredJwtException e){
            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e){
            logger.info("지원 JWT 토큰입니다.");
        } catch (IllegalArgumentException e){
            logger.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
