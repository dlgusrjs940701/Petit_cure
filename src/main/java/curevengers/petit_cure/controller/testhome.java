package curevengers.petit_cure.controller;

import curevengers.petit_cure.Dao.MemberMapper;
import curevengers.petit_cure.Dto.*;


import curevengers.petit_cure.Dto.testDto;

import curevengers.petit_cure.Service.dpBoardService;
import curevengers.petit_cure.Service.dpCheckService;
import curevengers.petit_cure.Service.healthCheckService;
import curevengers.petit_cure.Service.testService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@Controller
public class testhome {

    @Autowired
    testService testservice;

    @Autowired
    healthCheckService healthcheckservice;

    @Autowired
    MemberMapper membermapper;

    @Autowired
    dpCheckService dpcheckservice;

    @Autowired
    dpBoardService dpboardservice;

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

    // 우울증 게시판 글쓰기 저장
    @PostMapping(value = "/dpsave")
    public String dpsave(@ModelAttribute dpBoardDTO dto) throws Exception {
        dpboardservice.insert(dto);
        return "redirect:/depboard";
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

    // 우울증게시판
    @GetMapping(value = "/depboard")
    public String depBoard(Model model, @ModelAttribute pageDTO pagedto) throws Exception {
        if (pagedto.getPage() == null) {
            pagedto.setPage(1);
        }
        pagedto.setTotalCount(dpboardservice.countAll());
        List<dpBoardDTO> dpBoardList = dpboardservice.selectAll();
        model.addAttribute("pageDTO", pagedto);
        model.addAttribute("list", dpBoardList);
        return "dpBoard";
    }

    // 자유게시판 글 자세히 보기
    @GetMapping(value = "/view")
    public String boardView(@RequestParam("no") String no, Model model, @ModelAttribute freecommentDTO freecommendto) {
        freeBoardDTO board = testservice.getBoardNo(no);
        testservice.updateVisit(Integer.parseInt(no));
        List<freecommentDTO> freecommentFreeList = testservice.getFreeComment(no);
//        List<String> attachList=testservice.getAttach(no);
        model.addAttribute("dto", board);
        model.addAttribute("commentFreeList", freecommentFreeList);
//        model.addAttribute("attachList", attachList);
        return "view";
    }

    // Q&A게시판 글 자세히 보기
    @GetMapping(value = "/qaview")
    public String QAboardView(@RequestParam("no") String no, Model model, @ModelAttribute qacommentDTO qacommentdto, @ModelAttribute memberDTO memberdto) {
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

    // 우울증게시판 글 자세히 보기
    @GetMapping(value = "/dpview")
    public String dpview(@RequestParam("no") int no, Model model) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        memberDTO memberDTO = membermapper.getMemberByID(username);

        dpBoardDTO dto = dpboardservice.selectOne(no);
        List<dpcommentDTO> dpcommentList = dpboardservice.getdpComment(no);
        model.addAttribute("dto", dto);
        model.addAttribute("commentList", dpcommentList);
        model.addAttribute("member", memberDTO);
        return "dpview";
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

    // 우울증게시판 글쓰기
    @GetMapping(value = "/dpwrite")
    public String dpWrite() {
        return "dpBoardWrite";
    }

    // 건강검진화면
    @GetMapping(value = "/health")
    public String health() {
        return "healthcheck";
    }

    // 건강검진결과로
    @PostMapping(value = "/healthresult")
    public String healthresult(@ModelAttribute healthCheckDTO dto, Model model, HttpServletRequest request) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nowId = authentication.getName();
        dto.setId(nowId);
        healthcheckservice.insert(dto);
        healthCheckDTO result = healthcheckservice.showOne(dto);
        model.addAttribute("dto", result);
        return "healthcheckresult";
    }

    // 지도에 매핑하기
    @ResponseBody
    @PostMapping(value = "/addmapper")
    public ArrayList<hospitalDTO> addMapper(@RequestParam("h_type") String h_type) throws Exception {
        ArrayList<hospitalDTO> list = healthcheckservice.mappingHospital(h_type);
        System.out.println(list.get(0).getH_lat() + "/" + list.get(0).getH_lng());
        return list;
    }

    // 건강검진결과 전체 리스트 보기
    @GetMapping(value = "/moreresult")
    public String moreresult(Model model, HttpServletRequest request) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nowId = authentication.getName();
        List<healthCheckDTO> list = healthcheckservice.selectAll(nowId);
        model.addAttribute("list", list);
        return "healthcheckresultmore";
    }

    // 건강검진결과 전체 리스트 중 하나 보기
    @PostMapping(value = "/healthresultone")
    public String healthresultOne(@RequestParam("date") String date, Model model) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nowId = authentication.getName();
        healthCheckDTO dto = new healthCheckDTO();
        dto.setId(nowId);
        dto.setDate(date);
        healthCheckDTO result = healthcheckservice.selectOne(dto);
        model.addAttribute("dto", result);
        return "healthcheckresult";
    }

    // 우울증 검사
    @GetMapping(value = "/dpcheck")
    public String dpcheck() {
        return "dpcheck";
    }

    // 우울증 검사 결과
    @PostMapping(value = "/dpcheckresult")
    public String depresult(@ModelAttribute dpCheckDTO dto, Model m) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nowId = authentication.getName();
        dto.setId(nowId);
        dto.setResult(dto.getA() + dto.getB() + dto.getC() + dto.getD() + dto.getE() + dto.getF() + dto.getG() + dto.getH() + dto.getI());
        dpcheckservice.insert(dto);
        dpcheckservice.showOne(dto);
        m.addAttribute("dto", dto);
        return "dpCheckResult";
    }

    // 우울증 검사 결과 전체 리스트 보기
    @GetMapping(value = "/dpmoreresult")
    public String dpmoreresult(Model model) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nowId = authentication.getName();
        List<dpCheckDTO> list = dpcheckservice.selectAll(nowId);
        model.addAttribute("list", list);
        return "dpCheckResultMore";
    }

    // 우울증 검사 전체 리스트 중 하나 보기
    @PostMapping(value = "/dphresultone")
    public String dpresultOne(@RequestParam("date") String date, Model model) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nowId = authentication.getName();
        dpCheckDTO dto = new dpCheckDTO();
        dto.setId(nowId);
        dto.setDate(date);
        dpCheckDTO result = dpcheckservice.selectOne(dto);
        model.addAttribute("dto", result);
        return "dpCheckResult";
    }

    // 자유게시판 검색 기능
    @GetMapping(value = "/searchTitle")
    public String searchBoard(@RequestParam("title") String title, Model model) {
        List<freeBoardDTO> board = testservice.getsearchFreeBoards(title);
        model.addAttribute("list", board);
        return "searchfreeBoard";
    }

    // Q&A게시판 검색 기능
    @GetMapping(value = "/searchQATitle")
    public String searchQABoard(@RequestParam("title") String title, Model model) {
        List<QABoardDTO> board = testservice.getsearchQABoards(title);
        model.addAttribute("list", board);
        return "searchQABoard";
    }

    // 우울증게시판 검색 기능
    @GetMapping(value = "/searchDPTitle")
    public String searchDPTitle(@RequestParam("title") String title, Model model) throws Exception{
        List<dpBoardDTO> board = dpboardservice.getsearchDPBoards(title);
        model.addAttribute("list", board);
        return "searchDPBoard";
    }

    // QA게시판 좋아요 기능
    @GetMapping(value = "/goodUp")
    public String goodUp(@RequestParam("no") int no) {
        testservice.updateGood((no));

        return "redirect:/qaview?no=" + no;
    }

    // QA게시판 좋아요 취소 기능
    @GetMapping(value = "/goodDown")
    public String goodDown(@RequestParam("no") int no) {
        testservice.updateGoodDown((no));

        return "redirect:/qaview?no=" + no;
    }

    // 우울증게시판 좋아요 기능
    @GetMapping(value = "/dpGoodUp")
    public String dpGoodUp(@RequestParam("no") int no) throws Exception {
        dpboardservice.updateGoodUP(no);
        return "redirect:/dpview?no=" + no;
    }

    // 우울증게시판 좋아요 취소 기능
    @GetMapping(value = "/dpGoodDown")
    public String dpGoodDown(@RequestParam("no") int no) throws Exception {
        dpboardservice.updateGoodDown(no);
        return "redirect:/dpview?no=" + no;
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


    // 우울증게시판 댓글 기능
    @PostMapping(value = "/dpcomment")
    public String dpcomment(@ModelAttribute dpcommentDTO dto) throws Exception {
        dpboardservice.adddpComment(dto);
        return "redirect:/depboard";
    }

    // 자유게시판 신고 기능
    @GetMapping(value = "/report")
    public String report(@RequestParam("no") int no) {
        testservice.updateReport((no));

        return "redirect:/view?no=" + no;
    }

    // QA게시판 신고 기능
    @GetMapping(value = "/qareport")
    public String qareport(@RequestParam("no") int no) {
        testservice.updateQAReport((no));

        return "redirect:/qaview?no=" + no;

    }

    // 자유게시판 글 수정창
    @GetMapping(value = "/updateBoardView")
    public String updateBoardView(@RequestParam("no") int no, Model m) throws Exception {
        dpBoardDTO dto = dpboardservice.selectOne(no);
        m.addAttribute("dto", dto);
        return "overWrite";
    }

    // Q&A게시판 글 수정창
    @GetMapping(value = "/updateqaBoardView")
    public String updateqaBoardView(@RequestParam("no") int no, Model m) throws Exception {
        dpBoardDTO dto = dpboardservice.selectOne(no);
        m.addAttribute("dto", dto);
        return "qaOverWrite";
    }

    // 우울증게시판 글 수정창
    @GetMapping(value = "/updatedpBoardView")
    public String updatedpBoard(@RequestParam("no") int no, Model m) throws Exception {
        dpBoardDTO dto = dpboardservice.selectOne(no);
        m.addAttribute("dto", dto);
        return "dpBoardOverWrite";
    }

    // 자유게시판 수정 저장  * 미완
    @PostMapping(value = "/updateBoard")
    public String updateBoard(@ModelAttribute freeBoardDTO dto, Model m) throws Exception {
        testservice.updateBoard(dto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        memberDTO memberDTO = membermapper.getMemberByID(username);

        dpBoardDTO updatedto = dpboardservice.selectOne(dto.getNo());
        List<dpcommentDTO> dpcommentList = dpboardservice.getdpComment(dto.getNo());
        m.addAttribute("dto", updatedto);
        m.addAttribute("commentList", dpcommentList);
        m.addAttribute("member", memberDTO);
        return "view";
    }

    // Q&A게시판 수정 저장  * 미완
    @PostMapping(value = "/updateqaBoard")
    public String updateqaBoard(@ModelAttribute QABoardDTO dto, Model m) throws Exception {
        testservice.updateQABoard(dto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        memberDTO memberDTO = membermapper.getMemberByID(username);

        dpBoardDTO updatedto = dpboardservice.selectOne(dto.getNo());
        List<dpcommentDTO> dpcommentList = dpboardservice.getdpComment(dto.getNo());
        m.addAttribute("dto", updatedto);
        m.addAttribute("commentList", dpcommentList);
        m.addAttribute("member", memberDTO);
        return "qaview";
    }

    // 우울증게시판 수정 저장
    @PostMapping(value = "/updatedpBoard")
    public String updatedpBoard(@ModelAttribute dpBoardDTO dto, Model m) throws Exception {
        dpboardservice.updatedpBoard(dto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        memberDTO memberDTO = membermapper.getMemberByID(username);

        dpBoardDTO updatedto = dpboardservice.selectOne(dto.getNo());
        List<dpcommentDTO> dpcommentList = dpboardservice.getdpComment(dto.getNo());
        m.addAttribute("dto", updatedto);
        m.addAttribute("commentList", dpcommentList);
        m.addAttribute("member", memberDTO);
        return "dpview";
    }

    // 자유게시판 글 삭제기능  * 미완
    @GetMapping(value = "/deleteBoard")
    public String deleteBoard(@RequestParam("no") int no) throws Exception {

        return "redirect:/freeboard";
    }

    // Q&A게시판 글 삭제기능  * 미완
    @GetMapping(value = "/deleteqaBoard")
    public String deleteqaBoard(@RequestParam("no") int no) throws Exception {

        return "redirect:/qanda";
    }

    // 우울증게시판 글 삭제기능
    @GetMapping(value = "/deletedpBoard")
    public String deletedpBoard(@RequestParam("no") int no) throws Exception {
        dpboardservice.deletedpBoard(no);
        return "redirect:/depboard";
    }
}
