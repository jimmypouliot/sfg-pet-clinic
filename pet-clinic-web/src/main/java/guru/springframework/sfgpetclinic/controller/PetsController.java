package guru.springframework.sfgpetclinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/pets"})
public class PetsController {

    @RequestMapping({""})
    public String index() {
        return "pets";
    }
}
