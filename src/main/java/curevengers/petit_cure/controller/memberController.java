package curevengers.petit_cure.controller;


import curevengers.petit_cure.Dao.MemberMapper;
import curevengers.petit_cure.Dto.*;

import curevengers.petit_cure.Dto.memberDTO;
import curevengers.petit_cure.Dto.myActivityDTO;
import curevengers.petit_cure.Dto.withdrawMemDTO;

import curevengers.petit_cure.Service.UserServiceImpl;
import curevengers.petit_cure.Service.allBoardService;
import curevengers.petit_cure.Service.dpBoardService;
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
    MemberMapper membermapper;

    @Autowired
    allBoardService testservice;

    @Autowired
    dpBoardService dpboardservice;

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

    // 회원 비밀번호 변경 업데이트
    @ResponseBody
    @PostMapping(value = "/updateMember")
    public int updateMember(@ModelAttribute memberDTO memberdto) {
        return userservice.updateMember(memberdto);
    }

    // 로그인 화면
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "exception", required = false) String exception,
                            Model model) {    // 로그인되지 않은 상태이면 로그인 페이지를, 로그인된 상태이면 main 페이지를 보여줌
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        System.out.println(error +"  (error값 확인) / "+ exception + "  (exception값 확인) ----------------");

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

    // 마이페이지 활동 리스트
    @ResponseBody
    @GetMapping(value = "mypagelist")
    public List<myActivityDTO> mypagelist(@RequestParam("board") String board) {
        System.out.println(board+"어떤보드인지 확인");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nowId = authentication.getName();
        myActivityDTO dto = new myActivityDTO();
        dto.setBoard(board);
        dto.setId(nowId);
        System.out.println(userservice.getMyActivityList(dto).get(0).getId());
        List<myActivityDTO> dtoList = userservice.getMyActivityList(dto);
        for (int i = 0; i < dtoList.size(); i++) {
            dtoList.get(i).setBoard(board);
        }
        return dtoList;
    }

    // 회원수정/탈퇴 할 떄 다시 한번 확인하는 창

    @GetMapping(value = "/usermodify")
    public String usermodify(HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nowId = authentication.getName();
        session.setAttribute("nowId",nowId);
        return "memberupdate";
    }

    // 회원수정/탈퇴 할 때 아이디-비밀번호 확인
    @ResponseBody
    @PostMapping("/confirmMember")
    public boolean confirmMember(@RequestParam("id") String id, @RequestParam("password") String password) {
        memberDTO dto = userservice.getMemberById(id);
        if(passwordEncoder.matches(password,dto.getPass())){
            return true;
        }else{
            return false;
        }
    }

    // 회원수정/탈퇴 할 때 다시 한번 확인하는 창
    @GetMapping(value = "/userdelete")
    public String userdelete(HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nowId = authentication.getName();
        session.setAttribute("nowId",nowId);
        return "memberupdate";
    }


    // 회원 정보 수정 화면
    @GetMapping(value = "/usermod")
    public String usermod(HttpSession session) {
        session.setAttribute("nowId",session.getAttribute("nowId"));
        return "member";
    }
    // 회원 탈퇴 화면
    @GetMapping(value = "/userdel")
    public String userdel(HttpSession session) {
        session.setAttribute("nowId",session.getAttribute("nowId"));
        return "memberdelete";
    }

    // 회원 탈퇴
    @ResponseBody
    @PostMapping(value = "/withdrawMember")
    public int withdrawMember(@ModelAttribute withdrawMemDTO withdrawmember) {
        System.out.println("탈퇴로 들어옴 ----------------");
        userservice.addWithdraw(withdrawmember);
        return userservice.deleteMember(withdrawmember.getId());
    }


    // 메인화면에서 자유게시판 최고조회글에 있는 버튼을 누르면 자유게시판으로 ㄱㄱ
    @GetMapping(value = "/freeBO")
    public String freeBO(Model m) {
        pageDTO pagedto = new pageDTO();
        pagedto.setPage(1);
        String freeno = testservice.visitList(pagedto).get(0).getNo();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        memberDTO memberDTO = membermapper.getMemberByID(username);
        freeBoardDTO board = testservice.getBoardNo(freeno);
        testservice.updateVisit(Integer.parseInt(freeno));
//        System.out.println(board.getPassword());
        List<freecommentDTO> freecommentFreeList = testservice.getFreeComment(freeno);
        List<freeboard_attachDTO> attachList = testservice.getAttach(freeno);
        freeboardLikeDTO freeboardlike = new freeboardLikeDTO();
        freeboardlike.setFreeboard_no(freeno);
        freeboardlike.setId(username);
        freeboardlike = testservice.freegetBoardLike(freeboardlike);
        m.addAttribute("boardLike", freeboardlike);
        m.addAttribute("dto", board);
        m.addAttribute("commentFreeList", freecommentFreeList);
        m.addAttribute("attachList", attachList);
        m.addAttribute("member", memberDTO);

        return "view";
    }

    // 메인화면에서 Q&A게시판 최고조회글에 있는 버튼을 누르면 자유게시판으로 ㄱㄱ
    @GetMapping(value = "/Q&ABO")
    public String QABO(Model m) {
        pageDTO pagedto = new pageDTO();
        pagedto.setPage(1);
        String qano = testservice.goodQAList(pagedto).get(0).getNo();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        memberDTO memberDTO = membermapper.getMemberByID(username);
        QABoardDTO board = testservice.getQABoardNo(qano);
        List<qacommentDTO> qacommentList = testservice.getqaComment(qano);
        List<qaboard_attachDTO> qaattachList = testservice.getQAAttach(qano);
        qaboardLikeDTO qaboardlike = new qaboardLikeDTO();
        qaboardlike.setQaboard_no(qano);
        qaboardlike.setId(username);
        qaboardlike = testservice.qagetBoardLike(qaboardlike);
        System.out.println(qaboardlike);
        m.addAttribute("boardLike", qaboardlike);
        m.addAttribute("dto", board);
        m.addAttribute("commentList", qacommentList);
        m.addAttribute("qaattachList", qaattachList);
        m.addAttribute("member", memberDTO);

        return "qaview";
    }

    // 메인화면세어 우울증게시판 최고조회글에 있는 버튼을 누르면 자유게시판으로 ㄱㄱ
    @GetMapping(value = "/dpBO")
    public String dpBO(Model m) throws Exception {
        pageDTO pagedto = new pageDTO();
        pagedto.setPage(1);
        int dpno = dpboardservice.gooddpList(pagedto).get(0).getNo();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        memberDTO memberDTO = membermapper.getMemberByID(username);
        dpBoardDTO dto = dpboardservice.selectOne(dpno);
        List<dpcommentDTO> dpcommentList = dpboardservice.getdpComment(dpno);
        List<dpboard_attachDTO> dpattachList = dpboardservice.getDPAttach(dpno);

        dpboardLikeDTO dpboardlike = new dpboardLikeDTO();
        dpboardlike.setDpboard_no(String.valueOf(dpno));
        dpboardlike.setId(username);
        dpboardlike = dpboardservice.dpgetBoardLike(dpboardlike);
        System.out.println(dpboardlike);
        m.addAttribute("boardLike", dpboardlike);
        m.addAttribute("dto", dto);
        m.addAttribute("commentList", dpcommentList);
        m.addAttribute("dpattachList", dpattachList);
        m.addAttribute("member", memberDTO);

        return "dpview";
    }

    // 메인화면 게시판 별 탑3 리스트
    @ResponseBody
    @GetMapping(value = "mainpagelist")
    public List<myActivityDTO> mainpagelist(@RequestParam("board") String board) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nowId = authentication.getName();
        myActivityDTO dto = new myActivityDTO();
        dto.setBoard(board);
        dto.setId(nowId);
        return userservice.getTopList(dto);
    }
}





