package guru.springframework.sfgpetclinic.controller;

import guru.springframework.sfgpetclinic.service.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/vets", "/vets.html"})
public class VetsController {

    private final VetService vetService;

    public VetsController(VetService vetService) {
        this.vetService = vetService;
    }

    @RequestMapping({""})
    public String index(Model model) {
        model.addAttribute("vets", vetService.findAll());
        return "vets";
    }
}
