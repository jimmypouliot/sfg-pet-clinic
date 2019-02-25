package guru.springframework.sfgpetclinic.controller;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.service.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
        return "owner/index";
    }

    @GetMapping({"/find"})
    public ModelAndView findOwner() {
        return new ModelAndView("owner/findOwners", "owner", new Owner());
    }

    @GetMapping({"/{id}"})
    public ModelAndView displayOwner(@PathVariable("id") Long id) {
        return new ModelAndView("owner/ownerDetails", "owner", ownerService.findById(id));
    }
}
