package curevengers.petit_cure.controller;

import curevengers.petit_cure.Dao.MemberMapper;
import curevengers.petit_cure.Dto.*;
import curevengers.petit_cure.Service.allBoardService;
import curevengers.petit_cure.Service.dpBoardService;
import curevengers.petit_cure.Service.userService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    allBoardService allboardservice;

    @Autowired
    MemberMapper memberMapper;

    @Autowired
    dpBoardService dpboardservice;

    @Autowired
    userService userservice;

    @GetMapping(value = "/withdraw")
    public String withdraw(Model model, @ModelAttribute pageDTO pagedto) {
        if(pagedto.getPage() == null){      // 디폴트값 세팅
            pagedto.setPage(1);
        }
        List<withdrawMemDTO> cnt = userservice.countWithdraw();
        pagedto.setTotalCount(cnt.size());      // 총 갯수 세팅
        List<withdrawMemDTO> list = userservice.selectWithdraw(pagedto); // 페이징까지 값 가져오기
        model.addAttribute("list", list);
        model.addAttribute("pageDTO", pagedto);
        return "withdrawPage";
    }


    @GetMapping(value = "/alert")
    public String alert(Model model, @ModelAttribute pageDTO pagedto) {
        if(pagedto.getPage() == null){      // 디폴트값 세팅
            pagedto.setPage(1);
        }
        List<alertDTO> list = allboardservice.alertList();
        pagedto.setTotalCount(list.size());
        List<alertDTO> alertlist = allboardservice.findalertAllBoards(pagedto);
//        System.out.println(list.size() +" / 현재 저장되어있는 신고글 갯수");
        model.addAttribute("pageDTO", pagedto);
        model.addAttribute("list", alertlist);
        return "alertPage";
    }

    @GetMapping(value = "/withdrawview")
    public String withdrawview(HttpSession session, Model model, @ModelAttribute memberDTO memberdto,
                               @RequestParam("cause") String cause) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        // 관리자를 확인하기 위하여 정보 가져올것
        session.setAttribute("id", username);

        List<withdrawMemDTO> withdraw = userservice.withdrawCause(cause);
        model.addAttribute("withdrawList", withdraw);
        model.addAttribute("cause", cause);

        return "withdrawview";
    }

    @ResponseBody
    @GetMapping(value = "/deleteWithdraw")
    public int deleteWithdraw(@RequestParam("cause") String cause) {
        return userservice.deleteWithdraw(cause);
    }

    @GetMapping(value="/alertview")
    public String alertQAview(HttpSession session, @RequestParam("no") int no,
                              @RequestParam("cate") String cate,Model model,
                              @RequestParam("singonum") String singonum,
                              @ModelAttribute memberDTO memberdto, alertDTO alertdto) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        // 관리자를 확인하기 위하여 정보 가져올것
        session.setAttribute("id", username);

        alertdto.setNo(no);
        String category =cate;

        System.out.println(category+" / 넘어온 카테고리 확인");

        if(category.equals("Q&A게시판")){
            QABoardDTO board = allboardservice.getQABoardNo(String.valueOf(no));
            alertdto.setAlert_cate("Q&A게시판");
            model.addAttribute("board", board);
        }else if(category.equals("자유게시판")){
            freeBoardDTO board = allboardservice.getBoardNo(String.valueOf(no));
            alertdto.setAlert_cate("자유게시판");
            model.addAttribute("board", board);
        }else{
            dpBoardDTO board = dpboardservice.selectOne(no);
            alertdto.setAlert_cate("우울증게시판");
            board.setCate("우울증게시판");
            model.addAttribute("board", board);
        }

        List<alertDTO> alert = allboardservice.selectAlertcomment(alertdto);
        model.addAttribute("singonum", singonum);
        model.addAttribute("nowcate", category);
        model.addAttribute("alertList", alert);

        return "alertview";
    }



    // 관리자에 의한 Q&A게시판 글 삭제기능
    @GetMapping(value = "/deleteQAalert")
    public String deleteqaBoard(@RequestParam("no") String no, alertDTO alertDTO) throws Exception {
        System.out.println(no+" / Q&A에서 삭제하려는 번호----------------");
        alertDTO.setNo(Integer.valueOf(no));
        alertDTO.setAlert_cate("Q&A게시판");
        allboardservice.deleteQABoard(no);
        allboardservice.deleteAlert(alertDTO);
        return "redirect:/alert";

    }

    // 관리자에 의한 자유게시판 글 삭제기능
    @GetMapping(value = "/deleteFreealert")
    public String deletefreeBoard(@RequestParam("no") String no, alertDTO alertDTO) throws Exception {
        System.out.println(no+" / 자유게시판에서 삭제하려는 번호----------------");
        alertDTO.setNo(Integer.valueOf(no));
        alertDTO.setAlert_cate("자유게시판");
        allboardservice.deleteBoard(no);
        allboardservice.deleteAlert(alertDTO);
        return "redirect:/alert";
    }

    // 관리자에 의한 자유게시판 글 삭제기능
    @GetMapping(value = "/deleteDepalert")
    public String deleteDepalert(@RequestParam("no") String no, alertDTO alertDTO) throws Exception {
        System.out.println(no+" / 우울증게시판에서 삭제하려는 번호----------------");
        alertDTO.setNo(Integer.valueOf(no));
        alertDTO.setAlert_cate("우울증게시판");
        dpboardservice.deletedpBoard(Integer.valueOf(no));
        allboardservice.deleteAlert(alertDTO);
        return "redirect:/alert";
    }

    @ResponseBody
    @GetMapping(value = "/stopUser")
    public int stopUser(blackListDTO blacklistdto, memberDTO memberdto) throws Exception {
        memberdto.setId(blacklistdto.getId());
        memberdto.setAuth_name("BLACKLIST");
        userservice.addBlacklist(blacklistdto);
        return userservice.updateBlacklist(memberdto);
    }

    @ResponseBody
    @GetMapping(value = "/confirmBlack")
    public boolean confirmBlack(String id) throws Exception {
//        System.out.println(id + "계정 정지로 넘어온 id값 ----------------");
        if(userservice.selectBlack(id) == null){
            return true;
        }else{
            return false;
        }
    }

    // 공지사항 목록
    @GetMapping(value = "/notice")
    public String notice(Model model, @ModelAttribute pageDTO pagedto,HttpSession session) throws Exception {
        if(pagedto.getPage()==null){
            pagedto.setPage(1);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        memberDTO memberdto = userservice.getMemberById(username);

        pagedto.setTotalCount(allboardservice.getNoticenum().size());
        List<noticeDTO> noticeList = allboardservice.getNotices(pagedto);
        model.addAttribute("noticeList", noticeList);
        model.addAttribute("pageDTO", pagedto);
//        model.addAttribute("memberDTO", memberdto);
        if(memberdto != null){
            session.setAttribute("auth_name", memberdto.getAuth_name());
        }else{
            session.setAttribute("auth_name", null);
        }

        return "notice";
    }

    // 공지사항 자세히보기
    @GetMapping(value = "/noticeview")
    public String noticeview(Model model, @ModelAttribute("no") String no,HttpSession session) throws Exception {
        noticeDTO dto = allboardservice.getNotice(no);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        memberDTO memberdto = userservice.getMemberById(username);

//        model.addAttribute("memberDTO", memberdto);
        if(memberdto != null){
            session.setAttribute("auth_name", memberdto.getAuth_name());
        }else{
            session.setAttribute("auth_name", null);
        }
        model.addAttribute("dto", dto);

        return "noticeview";
    }

    // 공지사항 삭제
    @ResponseBody
    @GetMapping(value="/deleteNoticeboard")
    public int deleteNoticeboard(@RequestParam("no") String no) throws Exception {
        System.out.println(no+" / 공지사항 삭제용 번호 --------");
        return allboardservice.delnoticeOne(no);
    }

    // 공지사항 작성
    @GetMapping(value="/noticewrite")
    public String noticewrite() throws Exception {
        return "noticeWrite";
    }

    // 공지사항 작성저장
    @ResponseBody
    @PostMapping(value = "/noticesave")
    public int noticesave(@ModelAttribute noticeDTO noticedto){
        return allboardservice.noticesave(noticedto);
    }


    @GetMapping(value = "/adminPage")
    public String adminpage(){
        return "adminPage";
    }

    @ResponseBody
    @GetMapping(value = "adminlist")
    public List<myActivityDTO> mypagelist(@RequestParam("board") String board) {
        System.out.println(board+"어떤보드인지 확인");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nowId = authentication.getName();
        myActivityDTO dto = new myActivityDTO();
        dto.setBoard(board);
        dto.setId(nowId);
//        System.out.println(userservice.getMyActivityList(dto).get(0).getId());
        List<myActivityDTO> dtoList = userservice.adminList(dto);
//        for (int i = 0; i < dtoList.size(); i++) {
//            dtoList.get(i).setBoard(board);
//        }
        System.out.println(dtoList.size() +" / 결과로 넘어온 list길이---------");
        return dtoList;
    }
}
