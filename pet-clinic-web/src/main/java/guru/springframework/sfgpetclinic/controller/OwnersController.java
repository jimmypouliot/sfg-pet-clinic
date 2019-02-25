package guru.springframework.sfgpetclinic.controller;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.service.OwnerService;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.Set;

@Controller
@RequestMapping({"/owners"})
@Log
public class OwnersController {

    private final OwnerService ownerService;

    public OwnersController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping({""})
    public String index(Owner owner, BindingResult result, Model model) {
        String lastName = owner.getLastName();

        log.info("Got /owners request with lastName param: " + lastName);

        Set<Owner> foundOwners = lastName != null
                ? ownerService.findByLastNameLike(lastName)
                : Collections.emptySet();

        if (lastName != null && foundOwners.isEmpty()) {
            result.rejectValue("lastName", "notFound", "Last name not found");
            return "owner/findOwners";
        } else if (foundOwners.size() == 1) {
            return "redirect:/owners/" + foundOwners.iterator().next().getId();
        } else {
            Set<Owner> owners = foundOwners.isEmpty() ? ownerService.findAll() : foundOwners;
            model.addAttribute("selections", owners);
            return "owner/ownersList";
        }
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
