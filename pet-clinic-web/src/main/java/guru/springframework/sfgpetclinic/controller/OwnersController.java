package guru.springframework.sfgpetclinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/owners"})
public class OwnersController {

    @RequestMapping({""})
    public String index() {
        return "owners";
    }
}
