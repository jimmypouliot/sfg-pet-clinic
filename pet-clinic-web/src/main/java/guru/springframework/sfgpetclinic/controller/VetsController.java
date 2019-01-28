package guru.springframework.sfgpetclinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VetsController {

    @RequestMapping({"/vets"})
    public String index() {
        return "vets";
    }
}
