package guru.springframework.sfgpetclinic.controller;

import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.service.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@Controller
public class VetsController {

    private final VetService vetService;

    public VetsController(VetService vetService) {
        this.vetService = vetService;
    }

    @GetMapping({"vets.html"})
    public String index(Model model) {
        model.addAttribute("vets", vetService.findAll());
        return "vets";
    }

    @GetMapping({"api/vets.json"})
    public @ResponseBody Set<Vet> getVetsJson() {
        return vetService.findAll();
    }
}
