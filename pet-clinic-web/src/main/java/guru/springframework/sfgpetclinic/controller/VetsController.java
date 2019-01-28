package guru.springframework.sfgpetclinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/vets"})
public class VetsController {

    @RequestMapping({""})
    public String index() {
        return "vets";
    }
}
