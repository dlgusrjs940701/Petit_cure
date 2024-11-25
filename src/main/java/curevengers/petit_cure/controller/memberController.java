package curevengers.petit_cure.controller;

import curevengers.petit_cure.Dto.memberDTO;
import curevengers.petit_cure.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class memberController {



    @Autowired
    UserServiceImpl userservice;

    @PostMapping(value = "/memplus")        // 회원가입
    public String memplus(@ModelAttribute memberDTO memberdto) {
        try {
            userservice.signup(memberdto);
        } catch (DuplicateKeyException e) {
            return "redirect:/signup?error_code=-1";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/signup?error_code=-99";
        }
        return "redirect:/login";
    }

    @GetMapping(value = "/")
    public String home(Model model) {
        String id = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        memberDTO memberdto = userservice.getMemberById(id);
        memberdto.setPass(null);        // 비밀번호는 안보이도록 null로 설정
        model.addAttribute("member", memberdto);
        return "main";
    }

    @GetMapping("/login")
    public String loginPage() { // 로그인되지 않은 상태이면 로그인 페이지를, 로그인된 상태이면 main 페이지를 보여줌
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken)
            return "login";
        return "redirect:/";
    }

}

