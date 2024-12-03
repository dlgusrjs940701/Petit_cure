package curevengers.petit_cure.kakaoapi;

import curevengers.petit_cure.controller.memberController;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class kakaoController {

    @Autowired
    memberController membercontroller;

    @Autowired
    private final KakaoApi kakaoApi;

    @RequestMapping("/oauth2/login")
    public String kakaologin(@RequestParam("code") String code, Model model) {
        // redirect에 code를 같이 설정해줌으로써 인가에 대한 인증코드를 함께 받을 수 있다.

        // Step1. 인가 코드 받기(@RequestParam String code)  ->  받아온 정보 후 진행
        // Step2. 토큰 받기(사용자의 로그인을 통하여 발급된 인증 code를 통해서,,
        // 토큰까지 받아야 카카오 로그인이 완료된다.
        String accessToken = kakaoApi.getAccessToken(code);

        // Strp3. 사용자 정보를 받기(카카오에 accessToken을 제출함으로써 해당 사용자의 정보를 얻는다.)
        Map<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);

        String id = (String) userInfo.get("id");
        String nickname = (String) userInfo.get("nickname");

        System.out.println(id+" / "+nickname+" / "+accessToken);

        return "redirect:/";
    }

}
