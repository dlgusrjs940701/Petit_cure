package curevengers.petit_cure.kakaoapi;

import jakarta.security.auth.message.AuthException;
import lombok.Builder;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@Builder
public record OAuth2UserInfo(
     String name,
     String email,
     String phone,
     String age_range,
     String gender
) {
    public static OAuth2UserInfo of(String registrationId, Map<String, Object> attributes) {
        return switch (registrationId) {     // 만일 다른 소셜 로그인이 있을 경우를 대비해서 나눠서 작성
            case "kakao" -> ofkakao(attributes);
            default -> {
                try {
                    throw new AuthException();
                } catch (AuthException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }


    private static OAuth2UserInfo ofkakao(Map<String, Object> attributes) {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");

        return OAuth2UserInfo.builder()
                .name((String) account.get("name"))
                .email((String) account.get("email"))
                .phone((String) account.get("phone_number"))
                .age_range((String) account.get("age_range"))
                .gender((String) account.get("gender"))
                .build();
    }
}


