package curevengers.petit_cure.security.handler;


import curevengers.petit_cure.kakaoapi.KakaoApi;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        KakaoApi kakaoApi = new KakaoApi();
        System.out.println(request.getSession().getAttribute("kakaoToken") + "카카오 액세스 토큰");

        if(authentication!=null) {
            try {
                if (session != null && session.getAttribute("kakaoToken") == null) {
                    System.out.println("여긴 일반 로그아웃");
//            System.out.println(session.getAttribute("kakaoToken").toString()+"카카오 액세스 토큰(여긴 일반 로그아웃)");
//            kakaoApi.kakaoLogout(session.getAttribute("kakaoToken").toString());
                    session.invalidate();           // logout시에 세션을 무효화
                } else if (session != null && session.getAttribute("kakaoToken") != null) {
                    System.out.println(session.getAttribute("kakaoToken").toString() + "카카오 액세스 토큰");
                    kakaoApi.kakaoLogout(session.getAttribute("kakaoToken").toString());
                    session.invalidate();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect("/");
    }
}
