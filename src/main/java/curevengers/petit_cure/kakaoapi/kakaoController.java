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
        System.out.println("로그인 됐어용");
        // Step1. 인가 코드 받기(@RequestParam String code)  ->  받아온 정보 후 진행
        // Step2. 토큰 받기
        String accessToken = kakaoApi.getAccessToken(code);

        // Strp3. 사용자 정보를 받기
        Map<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);

        String email = (String) userInfo.get("email");
        String id = (String) userInfo.get("nickname");

        System.out.println(email+" / "+id+" / "+accessToken);

        return "redirect:/";
    }

}
