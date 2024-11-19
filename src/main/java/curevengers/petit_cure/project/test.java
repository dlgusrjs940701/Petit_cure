package curevengers.petit_cure.project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class test {

    @GetMapping(value = "/test")
    public String test() {
        return "/test";
    }

}
