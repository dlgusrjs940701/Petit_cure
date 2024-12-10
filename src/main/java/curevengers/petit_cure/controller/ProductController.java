package curevengers.petit_cure.controller;

import curevengers.petit_cure.Dto.memberDTO;
import curevengers.petit_cure.Service.UserServiceImpl;
import curevengers.petit_cure.security.Provider.TokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.HttpCookie;

@Controller
public class ProductController {

    TokenProvider tokenProvider;
    UserServiceImpl userService;

    public ProductController(TokenProvider tokenProvider, UserServiceImpl userService) {
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    @GetMapping(value = "/loginsuc")
    public String home(HttpSession session,HttpServletRequest request, HttpServletResponse response, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println(request.getCookies().toString()+"요청값에서 온 쿠키값**********************");
//        System.out.println(authentication.getPrincipal()+"요청값에서온 principal값");
//        System.out.println(request.getSession().getAttribute("kakaoToken")+"요청 값의 토큰값");
        String username = authentication.getName();
        memberDTO member = userService.getMemberById(username);
        String phone = member.getPhone_num();
        String age = member.getAge();

        if(member.getAuth_name().equals("ADMIN")){
            session.setAttribute("auth", authentication.getAuthorities());
        }
        if(authentication != null && phone == null) {
            model.addAttribute("id", username);
            session.setAttribute("id", username);
        }else if(authentication != null&&phone!=null) {
            session.setAttribute("id", username);
            session.setAttribute("age",age);
        } else{
            new ExceptionInInitializerError().printStackTrace();
        }

        System.out.println(username);

        return "main";
    }

    @GetMapping(value = "/login/error")
    public String error(HttpSession session,HttpServletRequest request, HttpServletResponse response, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("black",username);
        return "loginerror";
    }

    // passport-jwt & jsonwebtoken??

    // JWT 토큰을 저장하는 방법, LocalStorage
    // 로그인 이후 추후 요청에 있어서 토큰을 함께 보내줘야 하기 때문에 Client는 토큰을 유지하고 있어야 한다.
    // 크게 방법은 LocalStorage 와 Cookie가 존재..
    // 하지만 cross-site scripting(XSS)공격에서 LocalStorage가 취약성이 보인다.
    // XSS공격이란 공격자가 script가 삽입될 수 잇는 취약점이 있는 사이트를 물색하여 악성 script삽입을 통해 사용자의 정보를 빼가는 것
    // 즉 script가 삽입되어야 하기 때문에 입력가능한 Form이 필터링이 제대로 이뤄지지 않으면 발생

    // 따라서 JWT토큰을 Cookie로 저장하는 방법을 선택
    // 하지만 Cookie또한 js에서 global변수여서 접근이 가능 -> XXS위험이 존재할 수 있다.

    // httpOnly Cookie를 통해 client side에서 데이터에 접근하는 것을 막을 수 있다는 것을 알게됨

    // Access Token, Refresh Token 사용할 것
    // Access Token은 리소스에 접근하기 위한 토큰으로, 짧은 유효기간을 가지며 사용자가 주로 사용하는 토큰
    // Refresh Token은 Access Token 대비 긴 시간을 가지며 사용자 정보를 확인하여 Access Token을 재발급하기위한 토큰

}
