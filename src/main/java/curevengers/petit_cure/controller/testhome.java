package curevengers.petit_cure.controller;

import curevengers.petit_cure.Dto.*;


import curevengers.petit_cure.Dto.testDto;

import curevengers.petit_cure.Service.healthCheckService;
import curevengers.petit_cure.Service.testService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

import static org.thymeleaf.util.StringUtils.substring;

@Controller
public class testhome {

    @Autowired
    testService testservice;

    @Autowired
    healthCheckService healthcheckservice;


    @GetMapping(value = "/")
    public String home(Model model) {
        return "main";
    }

    @GetMapping(value = "/aa")
    public String home(@ModelAttribute testDto dto) {
        testservice.add(dto);
        return "test";
    }

    // 자유게시판 글쓰기 저장
    @PostMapping(value = "/save")
    public String save(@ModelAttribute freeBoardDTO dto) {
        testservice.addFreeBoard(dto);
        return "freeBoard";
    }

    // QA게시판 글쓰기 저장
    @PostMapping(value = "/qasave")
    public String qasave(@ModelAttribute QABoardDTO dto) {
        testservice.addQABoard(dto);
        return "Q&A";
    }


    // 자유게시판
    @GetMapping(value = "/freeboard")
    public String getFreeBoardList(Model model) {
//        if (pagedto.getPage()==null){
//            pagedto.setPage(1);
//        }
//        pagedto.setTotalCount(testservice.totalCountBoard());
        List<freeBoardDTO> freeBoardList = testservice.getAllFreeBoards();
        model.addAttribute("list", freeBoardList);
        return "freeBoard";
    }

    // QA게시판
    @GetMapping(value = "/qanda")
    public String getQABoardList(Model model) {
        List<QABoardDTO> QABoardList = testservice.getAllQABoards();
        model.addAttribute("qalist", QABoardList);
        return "Q&A";
    }

    // 자유게시판 글 자세히 보기
    @GetMapping(value = "/view")
    public String boardView(@RequestParam("no") String no, Model model) {
        freeBoardDTO board = testservice.getBoardNo(no);
//        List<String> attachList=testservice.getAttach(no);
        model.addAttribute("dto", board);
//        model.addAttribute("attachList", attachList);
        return "view";
    }

    //    // Q&A게시판 글 자세히 보기
    @GetMapping(value = "/qaview")
    public String QAboardView(@RequestParam("no") String no, Model model) {
        QABoardDTO board = testservice.getQABoardNo(no);

        model.addAttribute("dto", board);

        return "qaview";
    }

    // 우울증게시판
    @GetMapping(value = "/depboard")
    public String dep() {
        return "depBoard";
    }

    // 자유게시판 글쓰기
    @GetMapping(value = "/write")
    public String write() {
        return "write";
    }

    // QA게시판 글쓰기
    @GetMapping(value = "/qawrite")
    public String QAwrite() {
        return "qawrite";
    }

    // 회원가입화면
    @GetMapping(value = "/mplus")
    public String mplus() {
        return "mplus";
    }


    // 건강검진화면
    @GetMapping(value = "/health")
    public String health() {
        return "healthcheck";
    }

    // 건강검진결과로
    @PostMapping(value = "/healthresult")
    public String healthresult(@ModelAttribute healthCheckDTO dto, Model model, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
//        Object nowId = session.getAttribute("id");
        // 임의값 설정
        Object nowId = "1";
        dto.setId((String)nowId);
        healthcheckservice.insert(dto);
        healthCheckDTO result = healthcheckservice.selectOne((String)nowId, dto.getDate());
        model.addAttribute("dto", result);
        return "healthcheckresult";
    }

    @GetMapping(value = "/moreresult")
    public String moreresult(Model model, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
//        Object nowId = session.getAttribute("id");
        // 임의값 설정
        Object nowId = "1";
        List<healthCheckDTO> list = healthcheckservice.selectAll((String)nowId);
        model.addAttribute("list", list);
        return "healthcheckresultmore";
    }

    @PostMapping(value = "/healthresultone")
    public String healthresultOne(@RequestParam("date") String date, Model model, HttpServletRequest request) throws Exception {
        System.out.println(date);
        HttpSession session = request.getSession();
//        Object nowId = session.getAttribute("id");
        // 임의값 설정
        Object nowId = "1";
        healthCheckDTO result = healthcheckservice.selectOne((String)nowId, date);
        model.addAttribute("dto", result);
        return "healthcheckresult";
    }

    @GetMapping(value = "/dpcheck")
    public String dpcheck() {
        return "dpcheck";
    }

    @GetMapping(value = "/searchTitle")
    public String searchBoard(@RequestParam("title") String title, Model model) {
        List<freeBoardDTO> board = testservice.getsearchFreeBoards(title);
        model.addAttribute("list", board);
        return "searchfreeBoard";
    }

    @GetMapping(value = "/searchQATitle")
    public String searchQABoard(@RequestParam("title") String title, Model model) {
        List<QABoardDTO> board = testservice.getsearchQABoards(title);
        model.addAttribute("list", board);
        return "searchQABoard";
    }
}

