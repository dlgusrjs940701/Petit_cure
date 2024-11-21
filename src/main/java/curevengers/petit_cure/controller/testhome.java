package curevengers.petit_cure.controller;

import curevengers.petit_cure.Dto.freeBoardDTO;
import curevengers.petit_cure.Dto.testDto;
import curevengers.petit_cure.Service.testService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class testhome {

    @Autowired
    testService testservice;

    @GetMapping(value = "/")
    public String home() {
        return "main";
    }

    @GetMapping(value = "/aa")
    public String home(@ModelAttribute testDto dto) {
        testservice.add(dto);
        return "test";
    }


    @GetMapping(value = "/a")
    public String board(){
        return "freeBoard";
    }

    // 자유게시판
    @GetMapping(value = "/aaa")
    public String getFreeBoardList(Model model) {
        List<freeBoardDTO> freeBoardList=testservice.getAllFreeBoards();
        model.addAttribute("list", freeBoardList);
        return "freeBoard";
    }

    // 글 자세히 보기
    @GetMapping(value = "/view")
    public String boardView(@RequestParam("no") String no, Model model) {
        freeBoardDTO board=testservice.getBoardNo(no);

        model.addAttribute("dto", board);

        return "view";
    }
    @GetMapping(value = "/dep")
    public String dep() {
        return "depBoard";
    }
    @GetMapping(value = "/healthresult")
    public String healthresult() {
        return "healthcheckresult";
    }
    @GetMapping(value = "/health")
    public String health() {
        return "healthcheck";
    }

}

