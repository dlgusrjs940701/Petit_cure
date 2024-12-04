//package curevengers.petit_cure.kakaoapi;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
//@Component
//@Getter
//@NoArgsConstructor      //  기본 생성자(protected)
//@JsonIgnoreProperties(ignoreUnknown = true)
//public class KaKaoTokenResponseDTO {
//    // 받아온 정보를 역으로 세팅할 것
//    // 역직렬화가 필요
//    @JsonProperty("token_type")
//    public String tokenType;
//
//    @JsonProperty("access_token")
//    public String accessToken;
//
//    @JsonProperty("id_token")
//    public String idToken;
//
//    @JsonProperty("expires_in")
//    public Integer expireIn;
//
//    @JsonProperty("refresh_token")
//    public String refreshToken;
//
//    @JsonProperty("refresh_token_expires_in")
//    public Integer refreshTokenExpiresIn;
//
//    @JsonProperty("scope")
//    public String scope;
//}
