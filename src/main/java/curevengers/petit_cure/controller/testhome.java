package curevengers.petit_cure.controller;

import curevengers.petit_cure.Dao.MemberMapper;
import curevengers.petit_cure.Dto.*;


import curevengers.petit_cure.Dto.testDto;

import curevengers.petit_cure.Service.healthCheckService;
import curevengers.petit_cure.Service.testService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class testhome {

    @Autowired
    testService testservice;

    @Autowired
    healthCheckService healthcheckservice;

    @Autowired
    MemberMapper membermapper;


    @GetMapping(value = "/")
    public String home() {
        return "main";
    }

    @GetMapping(value = "/aa")
    public String home(@ModelAttribute testDto dto) {
        testservice.add(dto);
        return "test";
    }

    // 자유게시판 글쓰기 저장
    @PostMapping(value = "/save")
    public String save(@ModelAttribute freeBoardDTO dto, Model model) {
        testservice.addFreeBoard(dto);
        model.addAttribute("freeBoardDTO", dto.getNo());
        return "redirect:/freeboard";
    }

    // QA게시판 글쓰기 저장
    @PostMapping(value = "/qasave")
    public String qasave(@ModelAttribute QABoardDTO dto, Model model) {
        testservice.addQABoard(dto);
        model.addAttribute("qaBoard", dto.getNo());
        return "redirect:/qanda";
    }


    // 자유게시판
    @GetMapping(value = "/freeboard")
    public String getFreeBoardList(Model model, @ModelAttribute pageDTO pagedto) {
        if (pagedto.getPage() == null) {
            pagedto.setPage(1);
        }
        pagedto.setTotalCount(testservice.totalCountBoard());
        List<freeBoardDTO> freeBoardList = testservice.getAllFreeBoards(pagedto);
        model.addAttribute("list", freeBoardList);
        model.addAttribute("pageDTO", pagedto);
        return "freeBoard";
    }

    // QA게시판
    @GetMapping(value = "/qanda")
    public String getQABoardList(Model model, @ModelAttribute pageDTO pagedto) {
        if (pagedto.getPage() == null) {
            pagedto.setPage(1);
        }
        pagedto.setTotalCount(testservice.totalQACountBoard());
        List<QABoardDTO> QABoardList = testservice.getAllQABoards(pagedto);
        model.addAttribute("qalist", QABoardList);
        model.addAttribute("pageDTO", pagedto);
        return "Q&A";
    }

    // 자유게시판 글 자세히 보기
    @GetMapping(value = "/view")
    public String boardView(@RequestParam("no") String no, Model model, @ModelAttribute freecommentDTO freecommendto) {
        freeBoardDTO board = testservice.getBoardNo(no);
        testservice.updateVisit(Integer.parseInt(no));
        List<freecommentDTO> freecommentFreeList=testservice.getFreeComment(no);
//        List<String> attachList=testservice.getAttach(no);
        model.addAttribute("dto", board);
        model.addAttribute("commentFreeList", freecommentFreeList);
//        model.addAttribute("attachList", attachList);
        return "view";
    }

    //    // Q&A게시판 글 자세히 보기
    @GetMapping(value = "/qaview")
    public String QAboardView( @RequestParam("no") String no, Model model, @ModelAttribute qacommentDTO qacommentdto, @ModelAttribute memberDTO memberdto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        memberDTO memberDTO = membermapper.getMemberByID(username);

        QABoardDTO board = testservice.getQABoardNo(no);
        List<qacommentDTO> qacommentList = testservice.getqaComment(no);
        System.out.println("QABoard: " + board);
        System.out.println("Comment List: " + qacommentList);
        model.addAttribute("dto", board);
        model.addAttribute("commentList", qacommentList);
        model.addAttribute("member", memberDTO);
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
        dto.setId((String) nowId);
        healthcheckservice.insert(dto);
        healthCheckDTO result = healthcheckservice.selectOne((String) nowId);
        model.addAttribute("dto", result);
        return "healthcheckresult";
    }

    @GetMapping(value = "/moreresult")
    public String moreresult(Model model, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
//        Object nowId = session.getAttribute("id");
        // 임의값 설정
        Object nowId = "1";
        List<healthCheckDTO> list = healthcheckservice.selectAll((String) nowId);
        model.addAttribute("list", list);
        return "healthcheckresultmore";
    }

    @GetMapping(value = "/dpcheck")
    public String dpcheck() {
        return "dpcheck";
    }

    @GetMapping(value = "/depresult")
    public String depresult() throws Exception {
        return "ResultSheet";
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

    // QA게시판 좋아요 기능
    @GetMapping(value = "/goodUp")
    public String goodUp(@RequestParam("no") int no) {
        ;
        testservice.updateGood((no));

        return "redirect:/qaview?no=" + no;
    }

    // QA게시판 좋아요 취소 기능
    @GetMapping(value = "/goodDown")
    public String goodDown(@RequestParam("no") int no) {
        ;
        testservice.updateGoodDown((no));

        return "redirect:/qaview?no=" + no;
    }

    @GetMapping(value = "/company")
    public String company() {
        return "company";
    }

    // 댓글 기능
    @PostMapping(value = "/comment")
    public String reply(@ModelAttribute qacommentDTO dto) {

        testservice.addComment(dto);
//        List<commentDTO> commentList = testservice.getAllComments(dto);
//        model.addAttribute("commentList", commentList);
        return "redirect:/qanda";
    }

    @PostMapping(value = "/freecomment")
    public String freecomment(@ModelAttribute freecommentDTO dto) {
        testservice.addFreeComment(dto);
        return "redirect:/freeboard";
    }
}

