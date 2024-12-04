package curevengers.petit_cure.security.handler;

import curevengers.petit_cure.kakaoapi.KakaoApi;
import io.jsonwebtoken.lang.Assert;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextLogoutHandler implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Assert.notNull(request, "HttpServletRequest required");
        HttpSession session = request.getSession(false);
        KakaoApi kakaoApi = new KakaoApi();

        if(session != null) {
            try{
                if(session.getAttribute("kakaoToken")!=null){
                    System.out.println(session.getAttribute("kakaoToken").toString() + "카카오 액세스 토큰");
                    kakaoApi.kakaoLogout(session.getAttribute("kakaoToken").toString());
                    session.invalidate();
                }else{
                    System.out.println("여긴 일반 로그아웃");
                    session.invalidate();           // logout시에 세션을 무효화
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
