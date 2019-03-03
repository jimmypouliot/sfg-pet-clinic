package guru.springframework.sfgpetclinic.controller;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.service.OwnerService;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
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
                ? ownerService.findAllByLastNameLikeIgnoreCase(lastName)
                : Collections.emptySet();

        if (lastName != null && foundOwners.isEmpty()) {
            result.rejectValue("lastName", "notFound", new String[]{lastName}, "Last name not found");
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
    public ModelAndView displayOwner(@PathVariable Long id) {
        return new ModelAndView("owner/ownerDetails", "owner", ownerService.findById(id));
    }

    @GetMapping({"/new"})
    public ModelAndView getNew() {
        return new ModelAndView("owner/createOrUpdateOwnerForm", "owner", new Owner());
    }

    @PostMapping({"/new"})
    public ModelAndView postNew(@Valid Owner owner, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("owner/createOrUpdateOwnerForm", "owner", owner);
        } else {
            Owner savedOwner = ownerService.save(owner);
            return new ModelAndView("redirect:/owners/" + savedOwner.getId(), "owner", savedOwner);
        }
    }

    @GetMapping({"/{ownerId}/edit"})
    public ModelAndView getEdit(@PathVariable Long ownerId) {
        Owner owner = ownerService.findById(ownerId);
        return new ModelAndView("owner/createOrUpdateOwnerForm", "owner", owner);
    }

    @PostMapping("/{ownerId}/edit")
    public ModelAndView postEdit(@PathVariable Long ownerId, @Valid Owner owner, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("owner/createOrUpdateOwnerForm", "owner", owner);
        } else {
            owner.setId(ownerId);
            Owner savedOwner = ownerService.save(owner);
            return new ModelAndView("redirect:/owners/" + savedOwner.getId(), "owner", savedOwner);
        }
    }
}
