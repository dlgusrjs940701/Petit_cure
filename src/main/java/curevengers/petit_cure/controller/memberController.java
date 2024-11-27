package curevengers.petit_cure.controller;

import curevengers.petit_cure.Dto.memberDTO;
import curevengers.petit_cure.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class memberController {



    @Autowired
    UserServiceImpl userservice;

    // 회원가입화면
    @GetMapping(value = "/mplus")
    public String mplus() {
        return "mplus";
    }

    @PostMapping(value = "/memplus")        // 회원가입
    public String memplus(@ModelAttribute memberDTO memberdto) {
        userservice.signup(memberdto);
        return "redirect:/login";
    }

    @GetMapping(value = "/")
    public String home(Model model) {
        return "main";
    }

    @GetMapping("/login")
    public String loginPage() { // 로그인되지 않은 상태이면 로그인 페이지를, 로그인된 상태이면 main 페이지를 보여줌
        return "login";
    }

}

