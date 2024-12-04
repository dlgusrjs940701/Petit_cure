package curevengers.petit_cure.kakaoapi;

import curevengers.petit_cure.Dto.memberDTO;
import curevengers.petit_cure.Service.UserServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Configuration
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    UserServiceImpl userService;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("로그인 완료 후 사용자의 정보를 받는 공간");

//        // loadUser함수를 호출하여 카카오로부터 회원프로필을 받음
        Map<String, Object> oAuth2UserAttributes = super.loadUser(userRequest).getAttributes();

        // registrationId를 가져옴(kakao)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

//        // userNameAttributeName 가져옴
//        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // 유저 정보생성(카카오 기반으로 정보 추출및 생성)
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfo.of(registrationId,oAuth2UserAttributes);

        String id = userService.getMemberByphone(oAuth2UserInfo.phone());
        String role= null;

        if(id!=null){

            memberDTO memberdto = userService.getMemberById(id);
            role = memberdto.getAuth_name();
        }


//        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("USER"));

        return new PrincipalDetails(role, oAuth2UserInfo);
    }



    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
    private memberDTO getOrSave(OAuth2UserInfo oAuth2UserInfo) {
        String id = userService.getMemberByphone(oAuth2UserInfo.phone());
        System.out.println("가져온 전화번호를 이용해서 찾아낸 아이디값 : "+id);
        memberDTO memberDTO = null;
        if(id!=null){
            memberDTO = userService.getMemberById(id);
        }
        return memberDTO;
    }

}
