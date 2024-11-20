package curevengers.petit_cure.controller;

import curevengers.petit_cure.Dto.testDto;
import curevengers.petit_cure.Service.testService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class testhome {

    @Autowired
    testService testservice;

    @GetMapping(value = "/")
    public String home() {
        return "test";
    }

    @GetMapping(value = "/aa")
    public String home(@ModelAttribute testDto dto) {
        testservice.add(dto);
        return "test";
    }
}
