package curevengers.petit_cure.controller;

import curevengers.petit_cure.Dao.MemberMapper;
import curevengers.petit_cure.Dto.*;


import curevengers.petit_cure.Dto.testDto;

import curevengers.petit_cure.Service.*;
import curevengers.petit_cure.common.util.FileDataUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class testhome {

    @Autowired
    allBoardService testservice;

    @Autowired
    healthCheckService healthcheckservice;

    @Autowired
    MemberMapper membermapper;

    @Autowired
    dpCheckService dpcheckservice;

    @Autowired
    dpBoardService dpboardservice;

    @Autowired
    FileDataUtil filedatautil;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


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
    public String save(@ModelAttribute freeBoardDTO dto, MultipartFile[] file, Model model,
                       freeboard_attachDTO attachDTO) throws IOException {
//        System.out.println(file.length);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        memberDTO member = userService.getMemberById(username);     
        // 현재 로그인을 한 회원정보를 이용하여 게시글의 아이디와 비밀번호 값을 추가
        dto.setId(username);
        dto.setPassword(member.getPass());
//        System.out.println(member.getPass()+"----사용자의 비밀번호 확인");
        String[] newFileName = filedatautil.fileUpload(file);
//        System.out.println(newFileName + "kkkkkkkk");
        dto.setNewFileName(newFileName);
        testservice.addFreeBoard(dto);
        String freeboard_no = dto.getNo();
        for (int i = 0; i < file.length; i++) {
            if (!file[i].isEmpty()) {
                attachDTO = new freeboard_attachDTO();
                attachDTO.setFreeboard_no(freeboard_no);
                attachDTO.setFilename(newFileName[i]);
                testservice.insertAttach(attachDTO);
            }
        }
        model.addAttribute("freeBoardDTO", dto.getNo());
        model.addAttribute("attachDTO", attachDTO);
//        model.addAttribute("dto", dto);
        return "redirect:/freeboard";
    }


    // 게시글 수정, 삭제시에 입력값이 암호화된 회원 비밀번호와 일치하는지 확인
    @ResponseBody
    @PostMapping(value="/confirmpassword")
    public boolean confirmpassword(@RequestParam String password, @RequestParam String id) {
        System.out.println(password+" / 패스워드 채킹 ---------------->" + id + "아이디 채킹 =========");
        memberDTO member = userService.getMemberById(id);
        if(passwordEncoder.matches(password,member.getPass())){
            System.out.println("비밀번호 일치함");
            return true;
        }else {
            return false;
        }
    }

    // QA게시판 글쓰기 저장
    @PostMapping(value = "/qasave")
    public String qasave(@ModelAttribute QABoardDTO dto, MultipartFile[] file, Model model, qaboard_attachDTO qaattachDTO) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        memberDTO member = userService.getMemberById(username);
        dto.setId(username);
        dto.setPassword(member.getPass());
        String[] newFileName = filedatautil.fileUpload(file);
        dto.setNewFileName(newFileName);
        testservice.addQABoard(dto);
        String qaboard_no = dto.getNo();
        for (int i = 0; i < file.length; i++) {
            if (!file[i].isEmpty()) {
                qaattachDTO = new qaboard_attachDTO();
                qaattachDTO.setQaboard_no(qaboard_no);
                qaattachDTO.setFilename(newFileName[i]);
                testservice.insertQAAttach(qaattachDTO);
            }
        }
        model.addAttribute("qaBoard", dto.getNo());
        model.addAttribute("qaattachDTO", qaattachDTO);
        return "redirect:/qanda";
    }

    // 우울증 게시판 글쓰기 저장
    @PostMapping(value = "/dpsave")
    public String dpsave(@ModelAttribute dpBoardDTO dto, MultipartFile[] file, Model model, dpboard_attachDTO dpattachDTO) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        memberDTO member = userService.getMemberById(username);
        dto.setId(username);
        dto.setPassword(member.getPass());
        String[] newFileName = filedatautil.fileUpload(file);
        dto.setNewFileName(newFileName);
        dpboardservice.insert(dto);
//        System.out.println(dto.getPassword()+" / 입력한 패스워드---------------------");
//        System.out.println(dto.getTitle()+" / 입력한 제목----------------------");
        int dpboard_no=dto.getNo();
        for (int i = 0; i < file.length; i++) {
            if (!file[i].isEmpty()) {
                dpattachDTO = new dpboard_attachDTO();
                dpattachDTO.setDpboard_no(String.valueOf(dpboard_no));
                dpattachDTO.setFilename(newFileName[i]);
                dpboardservice.insertDPAttach(dpattachDTO);
            }
        }
        model.addAttribute("dpboard", dto.getNo());
        return "redirect:/depboard";
    }

    // 자유게시판 최신순
    @GetMapping(value = "/freeboard")
    public String getFreeBoardList(Model model, @ModelAttribute pageDTO pagedto, @ModelAttribute freeboard_attachDTO attachDTO) {
        if (pagedto.getPage() == null) {
            pagedto.setPage(1);
        }
        pagedto.setTotalCount(testservice.totalCountBoard());
        List<freeBoardDTO> freeBoardList = testservice.getAllFreeBoards(pagedto);
        if(freeBoardList!=null){
            for (freeBoardDTO freeBoardDTO : freeBoardList) {
                List<freeboard_attachDTO> attachList = testservice.getAttach(freeBoardDTO.getNo());
                if(attachList.size()>0){
                    freeBoardDTO.setFileExist("true");
                }
            }
        }
        int totalnum = testservice.totalCountBoard();
        model.addAttribute("list", freeBoardList);
        model.addAttribute("pageDTO", pagedto);
        model.addAttribute("attachDTO", attachDTO);
        System.out.println(attachDTO.toString());
        model.addAttribute("totalnum", totalnum);
        model.addAttribute("limit", "date");
        return "freeBoard";
    }

    // 자유게시판 조회순
    @GetMapping(value = "/freeboardVisit")
    public String freeboardVisit(Model model, @ModelAttribute pageDTO pagedto, @ModelAttribute freeboard_attachDTO attachDTO) {
        if (pagedto.getPage() == null) {
            pagedto.setPage(1);
        }
        pagedto.setTotalCount(testservice.totalCountBoard());
        List<freeBoardDTO> freeBoardList = testservice.getAllFreeBoardsVisit(pagedto);
        int totalnum = testservice.totalCountBoard();
        model.addAttribute("list", freeBoardList);
        model.addAttribute("pageDTO", pagedto);
        model.addAttribute("attachDTO", attachDTO);
        System.out.println(attachDTO.toString());
        model.addAttribute("totalnum", totalnum);
        model.addAttribute("limit", "visit");
        return "freeBoard";
    }

    // QA게시판 최신순
    @GetMapping(value = "/qanda")
    public String getQABoardList(Model model, @ModelAttribute pageDTO pagedto,HttpSession session) {
        if (pagedto.getPage() == null) {
            pagedto.setPage(1);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println(request.getCookies().toString()+"요청값에서 온 쿠키값**********************");
//        System.out.println(authentication.getPrincipal()+"요청값에서온 principal값");
//        System.out.println(request.getSession().getAttribute("kakaoToken")+"요청 값의 토큰값");
        String username = authentication.getName();
        memberDTO member = userService.getMemberById(username);
        if(member!=null){
            String age = member.getAge();
            session.setAttribute("age", age);
        }
//        String phone = member.getPhone_num();
        pagedto.setTotalCount(testservice.totalQACountBoard());
        List<QABoardDTO> QABoardList = testservice.getAllQABoards(pagedto);
        model.addAttribute("qalist", QABoardList);
        model.addAttribute("pageDTO", pagedto);
        model.addAttribute("limit", "date");
//        System.out.println(age);
        return "Q&A";
    }

    // QA게시판 추천순
    @GetMapping(value = "/qandaGood")
    public String qandaGood(Model model, @ModelAttribute pageDTO pagedto,HttpSession session) {
        if (pagedto.getPage() == null) {
            pagedto.setPage(1);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println(request.getCookies().toString()+"요청값에서 온 쿠키값**********************");
//        System.out.println(authentication.getPrincipal()+"요청값에서온 principal값");
//        System.out.println(request.getSession().getAttribute("kakaoToken")+"요청 값의 토큰값");
        String username = authentication.getName();
        memberDTO member = userService.getMemberById(username);
        if(member!=null){
            String age = member.getAge();
            session.setAttribute("age", age);
        }
//        String phone = member.getPhone_num();
        pagedto.setTotalCount(testservice.totalQACountBoard());
        List<QABoardDTO> QABoardList = testservice.getAllQABoardsGood(pagedto);
        model.addAttribute("qalist", QABoardList);
        model.addAttribute("pageDTO", pagedto);
        model.addAttribute("limit", "good");
//        System.out.println(age);
        return "Q&A";
    }

    // 우울증게시판
    @GetMapping(value = "/depboard")
    public String depBoard(Model model, @ModelAttribute pageDTO pagedto, HttpSession httpSession) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name=authentication.getName();
        if (pagedto.getPage() == null) {
            pagedto.setPage(1);
        }
        pagedto.setTotalCount(dpboardservice.countAll());
        List<dpBoardDTO> dpBoardList = dpboardservice.selectAll(pagedto);
        model.addAttribute("list", dpBoardList);
        model.addAttribute("pageDTO", pagedto);
        System.out.println(authentication.getName());
        return "dpBoard";
    }

    // 자유게시판 글 자세히 보기
    @GetMapping(value = "/view")
    public String boardView(HttpSession session,@RequestParam("no") String no, Model model, @ModelAttribute freecommentDTO freecommendto, freeboard_attachDTO attachdto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        memberDTO memberDTO = membermapper.getMemberByID(username);
        freeBoardDTO board = testservice.getBoardNo(no);
        testservice.updateVisit(Integer.parseInt(no));
//        System.out.println(board.getPassword());
        List<freecommentDTO> freecommentFreeList = testservice.getFreeComment(no);
        List<freeboard_attachDTO> attachList = testservice.getAttach(no);
        freeboardLikeDTO freeboardlike = new freeboardLikeDTO();
        freeboardlike.setFreeboard_no(no);
        freeboardlike.setId(username);
        freeboardlike = testservice.freegetBoardLike(freeboardlike);
        if(freeboardlike != null) {
            model.addAttribute("boardLike", freeboardlike);
        }else{
            model.addAttribute("boardLike", null);
        }
        model.addAttribute("dto", board);
        model.addAttribute("commentFreeList", freecommentFreeList);
        model.addAttribute("attachList", attachList);
        model.addAttribute("member", memberDTO);
        session.setAttribute("id",username);
        return "view";
    }

    // Q&A게시판 글 자세히 보기
    @GetMapping(value = "/qaview")
    public String QAboardView(@RequestParam("no") String no, Model model, @ModelAttribute qacommentDTO qacommentdto, HttpSession session, @ModelAttribute memberDTO memberdto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        memberDTO memberDTO = membermapper.getMemberByID(username);
        QABoardDTO board = testservice.getQABoardNo(no);
        List<qacommentDTO> qacommentList = testservice.getqaComment(no);
        List<qaboard_attachDTO> qaattachList = testservice.getQAAttach(no);
        qaboardLikeDTO qaboardlike = new qaboardLikeDTO();
        qaboardlike.setQaboard_no(no);
        qaboardlike.setId(username);
        qaboardlike = testservice.qagetBoardLike(qaboardlike);
        System.out.println(qaboardlike);
        model.addAttribute("boardLike", qaboardlike);
        model.addAttribute("dto", board);
        model.addAttribute("commentList", qacommentList);
        model.addAttribute("qaattachList", qaattachList);
        model.addAttribute("member", memberDTO);
        session.setAttribute("id",username);
        return "qaview";
    }

    // 우울증게시판 글 자세히 보기
    @GetMapping(value = "/dpview")
    public String dpview(@RequestParam("no") int no, Model model, @ModelAttribute dpboard_attachDTO dpattachDTO, HttpSession session) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        memberDTO memberDTO = membermapper.getMemberByID(username);
        dpBoardDTO dto = dpboardservice.selectOne(no);
        List<dpcommentDTO> dpcommentList = dpboardservice.getdpComment(no);
        List<dpboard_attachDTO> dpattachList = dpboardservice.getDPAttach(no);

        dpboardLikeDTO dpboardlike = new dpboardLikeDTO();
        dpboardlike.setDpboard_no(String.valueOf(no));
        dpboardlike.setId(username);
        dpboardlike = dpboardservice.dpgetBoardLike(dpboardlike);
        System.out.println(dpboardlike);
        model.addAttribute("boardLike", dpboardlike);
        model.addAttribute("dto", dto);
        model.addAttribute("commentList", dpcommentList);
        model.addAttribute("dpattachList", dpattachList);
        model.addAttribute("member", memberDTO);
        session.setAttribute("id",username);
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
    public String dpWrite(Model model, Authentication authentication, HttpSession session) throws Exception {
        String username = authentication.getName();
        memberDTO memberDTO = membermapper.getMemberByID(username);
        model.addAttribute("member", memberDTO);
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
    public String moreresult(Model model) throws Exception {
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
        System.out.println(dto.getResult());
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
        pageDTO pagedto = new pageDTO();
        pagedto.setPage(1);
        pagedto.setTotalCount(board.size());
        int totalnum = testservice.totalCountBoard();
        model.addAttribute("list", board);
        model.addAttribute("pageDTO", pagedto);
        model.addAttribute("totalnum", totalnum);
        return "freeBoard";
    }

    // Q&A게시판 검색 기능
    @GetMapping(value = "/searchQATitle")
    public String searchQABoard(@RequestParam("title") String title, Model model) {
        List<QABoardDTO> board = testservice.getsearchQABoards(title);
        pageDTO pagedto = new pageDTO();
        pagedto.setPage(1);
        pagedto.setTotalCount(testservice.totalQACountBoard());
        model.addAttribute("qalist", board);
        model.addAttribute("pageDTO", pagedto);

        return "Q&A";
    }

    // 우울증게시판 검색 기능
    @GetMapping(value = "/searchDPTitle")
    public String searchDPTitle(@RequestParam("title") String title, Model model) throws Exception {
        List<dpBoardDTO> board = dpboardservice.getsearchDPBoards(title);
        pageDTO pagedto = new pageDTO();
        pagedto.setPage(1);
        pagedto.setTotalCount(dpboardservice.countAll());
        model.addAttribute("list", board);
        model.addAttribute("pageDTO", pagedto);
        return "dpBoard";
    }

    // 자유게시판 좋아요 기능
    @ResponseBody
    @GetMapping(value = "/freeGoodUp")
    public int freeGoodUp(@RequestParam("no") int no, @RequestParam("id") String id) {
        freeboardLikeDTO freeboardLikeDTO = new freeboardLikeDTO();
        freeboardLikeDTO.setId(id);
        freeboardLikeDTO.setFreeboard_no(String.valueOf(no));
        testservice.freeaddLike(freeboardLikeDTO);
        return testservice.freeupdateGood((no));
    }

    // 자유게시판 좋아요 취소 기능
    @ResponseBody
    @GetMapping(value = "/freeGoodDown")
    public int freeGoodDown(@RequestParam("no") int no, @RequestParam("id") String id) {
        freeboardLikeDTO freeboardLikeDTO = new freeboardLikeDTO();
        freeboardLikeDTO.setId(id);
        freeboardLikeDTO.setFreeboard_no(String.valueOf(no));
        testservice.freedeleteLike(freeboardLikeDTO);
        return testservice.freeupdateGoodDown((no));
    }

    // QA게시판 좋아요 기능
    @ResponseBody
    @GetMapping(value = "/qaGoodUp")
    public int qaGoodUp(@RequestParam("no") int no, @RequestParam("id") String id) {
        qaboardLikeDTO qaboardLikeDTO = new qaboardLikeDTO();
        qaboardLikeDTO.setId(id);
        qaboardLikeDTO.setQaboard_no(String.valueOf(no));
        testservice.qaaddLike(qaboardLikeDTO);
        return testservice.qaupdateGood((no));
    }

    // QA게시판 좋아요 취소 기능
    @ResponseBody
    @GetMapping(value = "/qaGoodDown")
    public int qaGoodDown(@RequestParam("no") int no, @RequestParam("id") String id) {
        qaboardLikeDTO qaboardLikeDTO = new qaboardLikeDTO();
        qaboardLikeDTO.setId(id);
        qaboardLikeDTO.setQaboard_no(String.valueOf(no));
        testservice.qadeleteLike(qaboardLikeDTO);
        return testservice.qaupdateGoodDown((no));
    }

    // 우울증게시판 좋아요 기능
    @ResponseBody
    @GetMapping(value = "/dpGoodUp")
    public int dpGoodUp(@RequestParam("no") int no, @RequestParam("id") String id) {
        dpboardLikeDTO dpboardLikeDTO = new dpboardLikeDTO();
        dpboardLikeDTO.setId(id);
        dpboardLikeDTO.setDpboard_no(String.valueOf(no));
        dpboardservice.addLike(dpboardLikeDTO);
        return dpboardservice.updateGoodUP(no);
    }

    // 우울증게시판 좋아요 취소 기능
    @ResponseBody
    @GetMapping(value = "/dpGoodDown")
    public int dpGoodDown(@RequestParam("no") int no, @RequestParam("id") String id) {
        dpboardLikeDTO dpboardLikeDTO = new dpboardLikeDTO();
        dpboardLikeDTO.setId(id);
        dpboardLikeDTO.setDpboard_no(String.valueOf(no));
        dpboardservice.deleteLike(dpboardLikeDTO);
        return dpboardservice.updateGoodDown(no);
    }

    @GetMapping(value = "/company")
    public String company() {
        return "company";
    }

    // 자유게시판 댓글기능
    @PostMapping(value = "/freecomment")
    public String freecomment(@ModelAttribute freecommentDTO dto) {
        testservice.addFreeComment(dto);
        return "redirect:/view?no=" + dto.getFreeboard_no();
    }

    // Q&A댓글 기능
    @PostMapping(value = "/comment")
    public String reply(@ModelAttribute qacommentDTO dto) {

        testservice.addComment(dto);
//        List<commentDTO> commentList = testservice.getAllComments(dto);
//        model.addAttribute("commentList", commentList);
        return "redirect:/qaview?no=" + dto.getQaboard_no();
    }


    // 우울증게시판 댓글 기능
    @PostMapping(value = "/dpcomment")
    public String dpcomment(@ModelAttribute dpcommentDTO dto) throws Exception {
        dpboardservice.adddpComment(dto);
        return "redirect:/dpview?no="+dto.getDpboard_no();
    }

    // 자유게시판 신고 기능
    @ResponseBody
    @PostMapping(value = "/report")
    public int report(@ModelAttribute alertDTO alertDTO) {
        alertDTO.setAlert_cate("자유게시판");
        return testservice.alertFreeReport(alertDTO);
    }

    // QA게시판 신고 기능
    @ResponseBody
    @PostMapping(value = "/qareport")
    public int qareport(@ModelAttribute alertDTO alertDTO) {
        System.out.println(alertDTO.getWrite_id());
        alertDTO.setAlert_cate("Q&A게시판");
        return testservice.alertQAReport(alertDTO);
    }

    // 우울증게시판 신고 기능
    @ResponseBody
    @PostMapping(value = "/dpreport")
    public int dpreport(@ModelAttribute alertDTO alertDTO){
        alertDTO.setAlert_cate("우울증게시판");
        return dpboardservice.alertdpReport(alertDTO);

    }

    // 자유게시판 글 수정창
    @GetMapping(value = "/updateBoardView")
    public String updateBoardView(@RequestParam("no") String no, Model m) throws Exception {
        freeBoardDTO dto = testservice.getBoardNo(no);
        if(dto.getCate().equals("함께 공유해요")){
            dto.setCate("1");
        } else if (dto.getCate().equals("나 진지해요")) {
            dto.setCate("2");
        }else{
            dto.setCate("3");
        }
        m.addAttribute("dto", dto);
        return "overWrite";
    }

    // Q&A게시판 글 수정창
    @GetMapping(value = "/updateqaBoardView")
    public String updateqaBoardView(@RequestParam("no") String no, Model m) throws Exception {
        QABoardDTO dto = testservice.getQABoardNo(no);
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

    // 자유게시판 수정 저장
    @PostMapping(value = "/updateBoard")
    public String updateBoard(@ModelAttribute freeBoardDTO dto, Model m){
        System.out.println(dto.getNo()+" / 수정하러 온 no");
        testservice.updateBoard(dto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        memberDTO memberDTO = membermapper.getMemberByID(username);
        freeBoardDTO updatedto = testservice.getBoardNo(dto.getNo());
        if (updatedto.getCate() == "1") {
            updatedto.setCate("함께 공유해요");
        } else if (updatedto.getCate() == "2") {
            updatedto.setCate("나 진지해요");
        } else if (updatedto.getCate() == "3") {
            updatedto.setCate("우리 잡담해요");
        }
        List<freecommentDTO> dpcommentList = testservice.getFreeComment(dto.getNo());
        m.addAttribute("dto", updatedto);
        m.addAttribute("commentList", dpcommentList);
        m.addAttribute("member", memberDTO);
        return "view";
    }

    // Q&A게시판 수정 저장
    @PostMapping(value = "/updateqaBoard")
    public String updateqaBoard(@ModelAttribute QABoardDTO dto, Model m){
        testservice.updateQABoard(dto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        memberDTO memberDTO = membermapper.getMemberByID(username);

        QABoardDTO updatedto = testservice.getQABoardNo(dto.getNo());
        List<qacommentDTO> dpcommentList = testservice.getqaComment(dto.getNo());
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
        memberDTO member = userService.getMemberById(username);
        String pass = member.getPass();
        memberDTO memberDTO = membermapper.getMemberByID(username);

        dpBoardDTO updatedto = dpboardservice.selectOne(dto.getNo());
        List<dpcommentDTO> dpcommentList = dpboardservice.getdpComment(dto.getNo());
        m.addAttribute("dto", updatedto);
        m.addAttribute("commentList", dpcommentList);
        m.addAttribute("member", memberDTO);
        return "dpview";
    }

    // 자유게시판 댓글 수정
    @PostMapping(value = "/updatefreeComment")
    public String updatefreeComment(@RequestParam("commentNo") int commentNo, @RequestParam("content") String content, @RequestParam("boardNo") int boardNo, Model m) throws Exception {
        System.out.println(commentNo+"댓글번호"+content+"댓글내용");
        freecommentDTO commentdto = new freecommentDTO();
        commentdto.setNo(String.valueOf(commentNo));
        commentdto.setContent(content);
        testservice.updateComment(commentdto);
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//
//        memberDTO memberDTO = membermapper.getMemberByID(username);
//
//        freeBoardDTO dto = testservice.getBoardNo(String.valueOf(boardNo));
//        List<freecommentDTO> updateCommentList = testservice.getFreeComment(String.valueOf(boardNo));
//        m.addAttribute("dto", dto);
//        m.addAttribute("commentList", updateCommentList);
//        m.addAttribute("member", memberDTO);
        return "redirect:/view?no="+boardNo;
    }

    // Q&A게시판 댓글 수정
    @PostMapping(value = "/updateqaComment")
    public String updateqaComment(@RequestParam("commentNo") int commentNo, @RequestParam("content") String content, @RequestParam("boardNo") int boardNo, Model m) throws Exception {
//        System.out.println(commentNo + " (댓글 번호) /"+content+" (수정 내용) / "+ boardNo+ " (게시글 번호)");

        qacommentDTO qacommentdto = new qacommentDTO();
        qacommentdto.setNo(String.valueOf(commentNo));
        qacommentdto.setContent(content);
        testservice.updateqaComment(qacommentdto);
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//
//        memberDTO memberDTO = membermapper.getMemberByID(username);
//
//        QABoardDTO dto = testservice.getQABoardNo(String.valueOf(boardNo));
//        List<qacommentDTO> updateCommentList = testservice.getqaComment(String.valueOf(boardNo));
//        m.addAttribute("dto", dto);
//        m.addAttribute("commentList", updateCommentList);
//        m.addAttribute("member", memberDTO);
        return "redirect:/qaview?no="+boardNo;
    }

    // 우울증게시판 댓글 수정
    @PostMapping(value = "/updatedpComment")
    public String updatedpComment(@RequestParam("commentNo") int commentNo, @RequestParam("content") String content, @RequestParam("boardNo") int boardNo, Model m) throws Exception {
        dpboardservice.updatedpComment(commentNo, content);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        memberDTO memberDTO = membermapper.getMemberByID(username);

        dpBoardDTO dpdto = dpboardservice.selectOne(boardNo);
        System.out.println(dpboardservice.selectOne(boardNo));
        List<dpcommentDTO> updatedpCommentList = dpboardservice.getdpComment(boardNo);
        m.addAttribute("dto", dpdto);
        m.addAttribute("commentList", updatedpCommentList);
        m.addAttribute("member", memberDTO);
        return "dpview";
    }

    // 자유게시판 글 삭제기능
    @GetMapping(value = "/deleteBoard")
    public String deleteBoard(@RequestParam("no") String no) throws Exception {
        System.out.println("삭제하러 들어온 게시글 번호 / "+no);
        testservice.deleteBoard(no);
        return "redirect:/freeboard";
    }

    // Q&A게시판 글 삭제기능
    @GetMapping(value = "/deleteqaBoard")
    public String deleteqaBoard(@RequestParam("no") String no) throws Exception {
        testservice.deleteQABoard(no);
        return "redirect:/qanda";
    }

    // 우울증게시판 글 삭제기능
    @GetMapping(value = "/deletedpBoard")
    public String deletedpBoard(@RequestParam("no") int no) throws Exception {
        dpboardservice.deletedpBoard(no);
        return "redirect:/depboard";
    }

    // 자유게시판 댓글 삭제기능
    @PostMapping(value = "/deletefreeBoardComment")
    public String deletefreeBoardComment(@RequestParam("boardNo") int boardNo, @RequestParam("commentNo") String commentNo) throws Exception {
        System.out.println(boardNo+"/"+commentNo);
        freecommentDTO dto = new freecommentDTO();
        dto.setFreeboard_no(String.valueOf(boardNo));
        dto.setNo(commentNo);
        testservice.deletefreeBoardComment(dto);
        return "redirect:/view?no="+boardNo;
    }

    // Q&A게시판 댓글 삭제기능
    @PostMapping(value = "/deleteqaBoardComment")
    public String deleteqaBoardComment(@RequestParam("boardNo") int boardNo, @RequestParam("commentNo") String commentNo) throws Exception {
        System.out.println(boardNo+"/"+commentNo);
        qacommentDTO dto = new qacommentDTO();
        dto.setQaboard_no(String.valueOf(boardNo));
        dto.setNo(commentNo);
        System.out.println(dto.toString());
        testservice.deleteqaBoardComment(dto);
        return "redirect:/qaview?no="+boardNo;
    }

    // 우울증게시판 댓글 삭제기능
    @PostMapping(value = "/deletedpBoardComment")
    public String deletedpBoardComment(@RequestParam("boardNo") int boardNo, @RequestParam("commentNo") String commentNo) throws Exception {
        System.out.println(boardNo+"/"+commentNo);
        dpcommentDTO dto = new dpcommentDTO();
        dto.setDpboard_no(boardNo);
        dto.setNo(commentNo);
        System.out.println(dto.toString());
        dpboardservice.deletedpBoardComment(dto);
        return "redirect:/dpview?no="+boardNo;
    }

    // 자유게시판 조회수 나열
    @PostMapping(value = "/visitList")
    public String visitList(Model model, @ModelAttribute pageDTO pagedto) {
        if (pagedto.getPage() == null) {
            pagedto.setPage(1);
        }
        pagedto.setTotalCount(testservice.totalCountBoard());
        List<freeBoardDTO> visitList = testservice.visitList(pagedto);
        model.addAttribute("list", visitList);
        model.addAttribute("pageDTO", pagedto);
        model.addAttribute("limit", "visit");
        return "freeBoard";
    }

    // 자유게시판 최신순 나열
    @PostMapping(value = "/dateList")
    public String dateList(Model model, @ModelAttribute pageDTO pagedto) {
        if (pagedto.getPage() == null) {
            pagedto.setPage(1);
        }
        pagedto.setTotalCount(testservice.totalCountBoard());
        List<freeBoardDTO> dateList = testservice.dateList(pagedto);
        model.addAttribute("list", dateList);
        model.addAttribute("pageDTO", pagedto);
        model.addAttribute("limit", "date");
        return "freeBoard";
    }

    // Q&A게시판 추천순 나열
    @PostMapping(value = "/goodQAList")
    public String goodQAList(Model model, @ModelAttribute pageDTO pagedto) {
        if (pagedto.getPage() == null) {
            pagedto.setPage(1);
        }
        pagedto.setTotalCount(testservice.totalQACountBoard());
        List<QABoardDTO> goodQAList = testservice.goodQAList(pagedto);
        model.addAttribute("qalist", goodQAList);
        return "Q&A";
    }

    // Q&A게시판 최신순 나열
    @PostMapping(value = "/dateQAList")
    public String dateQAList(Model model, @ModelAttribute pageDTO pagedto) {
        if (pagedto.getPage() == null) {
            pagedto.setPage(1);
        }
        pagedto.setTotalCount(testservice.totalQACountBoard());
        List<QABoardDTO> dateQAList = testservice.dateQAList(pagedto);
        model.addAttribute("qalist", dateQAList);
        return "Q&A";
    }

    // 우울증게시판 추천순 나열
    @PostMapping(value = "/gooddpList")
    public String gooddpList(Model model, @ModelAttribute pageDTO pagedto) throws Exception {
        if (pagedto.getPage() == null) {
            pagedto.setPage(1);
        }
        pagedto.setTotalCount(dpboardservice.countAll());
        List<dpBoardDTO> gooddpList = dpboardservice.gooddpList(pagedto);
        model.addAttribute("list", gooddpList);
        return "dpBoard";
    }

    // 우울증게시판 최신순 나열
    @PostMapping(value = "/datedpList")
    public String datedpList(Model model, @ModelAttribute pageDTO pagedto) throws Exception {
        if (pagedto.getPage() == null) {
            pagedto.setPage(1);
        }
        pagedto.setTotalCount(dpboardservice.countAll());
        List<dpBoardDTO> datedpList = dpboardservice.datedpList(pagedto);
        model.addAttribute("list", datedpList);
        return "dpBoard";
    }

    // 메인페이지 자유게시판 글 중 최고 조회수 글 조회
    @ResponseBody
    @PostMapping(value = "freeboardVisitList")
    public freeBoardDTO freeboardVisitList() {
        pageDTO pagedto = new pageDTO();
        pagedto.setPage(1);
        System.out.println(testservice.visitList(pagedto).get(0).getTitle());

        return testservice.visitList(pagedto).get(0);
    }

    // 메인페이지 Q&A게시판 글 중 최고 추천수 글 조회
    @ResponseBody
    @PostMapping(value = "qaboardVisitList")
    public QABoardDTO qaboardVisitList() {
        pageDTO pagedto = new pageDTO();
        pagedto.setPage(1);

        return testservice.goodQAList(pagedto).get(0);
    }

    // 메인페이지 우울증게시판 글 중 최고 추천수 글 조회
    @ResponseBody
    @PostMapping(value = "dpboardVisitList")
    public dpBoardDTO dpboardVisitList() throws Exception {
        pageDTO pagedto = new pageDTO();
        pagedto.setPage(1);

        return dpboardservice.gooddpList(pagedto).get(0);
    }

    // 댓글 제한 기능
    @GetMapping(value = "/filterByAge")
    public String ageQABoard(@RequestParam("ageGroup") String ageGroup, Model model, pageDTO pagedto) {
//        memberDTO member = (memberDTO) session.getAttribute("member");
//        String memberAgeGroup=member.getAge();
        if (pagedto.getPage() == null) {
            pagedto.setPage(1);
        }
        pagedto.setTotalCount(testservice.totalQACountBoard());
        List<QABoardDTO> board = testservice.getAgeQABoards(ageGroup, pagedto);
        model.addAttribute("qalist", board);
        model.addAttribute("pagedto", pagedto);
//        model.addAttribute("memberAgeGroup", memberAgeGroup);
        return "Q&A";
    }

//    // 메인페이지 자유게시판 글 중 최고 조회수 글 조회
//    @ResponseBody
//    @PostMapping(value = "dpboardVisitList")
//    public dpBoardDTO dpboardVisitList() {
//        pageDTO pagedto = new pageDTO();
//        pagedto.setPage(1);
//        System.out.println(dpboardservice.visitList(pagedto).get(0).getTitle());
//
//        return testservice.visitList(pagedto).get(0);
//    }


}
