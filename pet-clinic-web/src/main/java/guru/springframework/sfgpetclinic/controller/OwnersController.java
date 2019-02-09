package guru.springframework.sfgpetclinic.controller;

import guru.springframework.sfgpetclinic.service.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/owners"})
public class OwnersController {

    private final OwnerService ownerService;

    public OwnersController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @RequestMapping({""})
    public String index(Model model) {
        model.addAttribute("owners", ownerService.findAll());
        return "owners";
    }
}
