package curevengers.petit_cure.controller;

import curevengers.petit_cure.Dto.Message;
import curevengers.petit_cure.Dto.memberDTO;
import curevengers.petit_cure.Service.memberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class memberController {


    @Autowired
    memberService memberservice;

    @PostMapping(value = "/memplus")
    public String memplus(@ModelAttribute memberDTO memberdto) {
        System.out.println(memberdto.toString());
        memberservice.addmember(memberdto);
        return "/login";
    }

//    @PostMapping(value = "/idCheck")
//    public void idCheck(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        // 자바스크립트로 아이디 중복 체크 위한 함수 추가완료.
//        // 중복체크버튼 클릭시 idCheck로 요청
//        String userid = request.getParameter("userid");
//        memberservice.cofrmID(userid);
//
//        int result = memberservice.cofrmID(userid);
//
//        request.setAttribute("userid",userid);
//        request.setAttribute("result",result);
//        // 중복있으면 1, 없으면 -1
//
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/mplus.html");
//        dispatcher.forward(request, response);
//    }

    @ResponseBody
    @GetMapping("/idCheck")
    public int idCheck(@RequestParam String id) {
        int cnt = memberservice.cofrmID(id);
        return cnt;
    }

}

