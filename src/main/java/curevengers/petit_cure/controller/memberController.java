package curevengers.petit_cure.controller;

import curevengers.petit_cure.Dto.memberDTO;
import curevengers.petit_cure.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    @GetMapping(value = "/memplus")        // 회원가입
    public String memplus(@ModelAttribute memberDTO memberdto) {
        userservice.signup(memberdto);
        return "redirect:/login";
    }

    @ResponseBody
    @GetMapping(value = "/idCheck")
    public int idCheck(@RequestParam String id) {
        return userservice.cofrmID(id);
    }


    @GetMapping("/login")
    public String loginPage() { // 로그인되지 않은 상태이면 로그인 페이지를, 로그인된 상태이면 main 페이지를 보여줌
        return "login";
    }

    @PostMapping("/logincon")
    public String logincon(@RequestParam("username") String username, @RequestParam("password") String password) {
        return "redirect:/";
    }

}

