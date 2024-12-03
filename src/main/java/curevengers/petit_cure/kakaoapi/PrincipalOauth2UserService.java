package curevengers.petit_cure.kakaoapi;

import curevengers.petit_cure.Dto.memberDTO;
import curevengers.petit_cure.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("로그인 완료 후 사용자의 정보를 받는 공간");

        OAuth2User oAuth2User = super.loadUser(userRequest);
        // loadUser함수를 호출하여 카카오로부터 회원프로필을 받음
        String providerId = oAuth2User.getAttribute("id");
        String nickname = oAuth2User.getAttribute("nickname");
        String role = "USER";

        memberDTO member = null;
        

        member.setId(providerId);
        member.setName(nickname);
        member.setAuth_name(role);

        return new PrincipalDetails(member, oAuth2User.getAttributes());
    }

}
