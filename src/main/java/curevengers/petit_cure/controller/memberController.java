package curevengers.petit_cure.controller;

import curevengers.petit_cure.Dto.memberDTO;
import curevengers.petit_cure.Dto.myActivityDTO;
import curevengers.petit_cure.Service.UserServiceImpl;
import curevengers.petit_cure.kakaoapi.KakaoApi;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


@RequiredArgsConstructor
@Controller
public class memberController {



    @Autowired
    UserServiceImpl userservice;

    @Autowired
    private final KakaoApi kakaoApi;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // 일반 회원가입화면
    @GetMapping(value = "/mplus")
    public String mplus() {
        return "mplus";
    }

    // 카카오 회원가입화면
    @PostMapping(value = "/kakaomplus")
    public String kakaomplus(Model m, @ModelAttribute("member") memberDTO member) {
        System.out.println(member.getPhone_num()+"/"+member.getName());
        m.addAttribute("member",member);
        return "kakaomplus";
    }

    // 회원가입버튼 - 정보 전달
    @PostMapping(value = "/memplus")
    public String memplus(@ModelAttribute memberDTO memberdto) {
        if(memberdto.getAuth_name()==null) {
            memberdto.setAuth_name("USER");
        }
        userservice.signup(memberdto);
        return "redirect:/login";
    }

    // 회원가입시 아이디 중복 체크
    @ResponseBody
    @GetMapping(value = "/idCheck")
    public int idCheck(@RequestParam("id") String id) {
        System.out.println("아이디 확인 중");
        return userservice.cofrmID(id);
    }

    // 로그인 화면
    @GetMapping("/login")
    public String loginPage(Model model) { // 로그인되지 않은 상태이면 로그인 페이지를, 로그인된 상태이면 main 페이지를 보여줌
        model.addAttribute("KakaoapiKey",kakaoApi.getKakaoapiKey());
        model.addAttribute("redirectUrl",kakaoApi.getKakaoredirectUri());
        return "login";
    }


    // 로그인 버튼
    @PostMapping("/logincon")
    public String logincon(@RequestParam("username") String username, @RequestParam("password") String password) {
        return "redirect:/";
    }

    // 마이페이지로 이동
    @GetMapping("/mypage")
    public String mypage(Model m) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nowId = authentication.getName();
        memberDTO dto = userservice.getMemberById(nowId);
        List<myActivityDTO> list = userservice.getMyActivity(nowId);
        m.addAttribute("dto", dto);
        m.addAttribute("list", list);
        return "MyPage";
    }

    // 회원수정/탈퇴 할 떄 다시 한번 확인하는 창
    @GetMapping(value = "/usermodify")
    public String usermodify() {

        return "memberupdate";
    }

    @GetMapping(value = "/userdelete")
    public String userdelete() {

        return "memberupdate1";
    }



    @GetMapping(value = "/usermod")
    public String usermod(Model m) {

        return "member";
    }

    @GetMapping(value = "/userdel")
    public String userdel(Model m) {

        return "memberdelete";
    }


    // 메인화면세어 자유게시판 최고조회글에 있는 버튼을 누르면 자유게시판으로 ㄱㄱ
    @GetMapping(value = "/freeBO")
    public String freeBO(Model m) {

        return "redirect:/freeboard";
    }

    // 메인화면세어 Q&A게시판 최고조회글에 있는 버튼을 누르면 자유게시판으로 ㄱㄱ
    @GetMapping(value = "/Q&ABO")
    public String QABO(Model m) {
        return "redirect:/qanda";
    }

    // 메인화면세어 우울증게시판 최고조회글에 있는 버튼을 누르면 자유게시판으로 ㄱㄱ
    @GetMapping(value = "/dpBO")
    public String dpBO(Model m) {

        return "redirect:/depboard";
    }

}





