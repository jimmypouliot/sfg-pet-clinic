package guru.springframework.sfgpetclinic.controller;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.service.OwnerService;
import guru.springframework.sfgpetclinic.service.PetService;
import guru.springframework.sfgpetclinic.service.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/owners/{ownerId}")
public class PetsController {

    private static final String CREATE_OR_UPDATE_PET_FORM = "pets/createOrUpdatePetForm";

    private final PetService petService;
    private final PetTypeService petTypeService;
    private final OwnerService ownerService;

    public PetsController(PetService petService, PetTypeService petTypeService, OwnerService ownerService) {
        this.petService = petService;
        this.petTypeService = petTypeService;
        this.ownerService = ownerService;
    }

    @RequestMapping({"/pets"})
    public String index() {
        return "pets";
    }

    @ModelAttribute("petTypes")
    public Collection<PetType> populatePetTypes() {
        return petTypeService.findAll();
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable Long ownerId) {
        return ownerService.findById(ownerId);
    }

    @InitBinder("owner")
    public void initOwnerBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields(("id"));
    }

    @GetMapping("/pets/new")
    public ModelAndView getNew(@PathVariable Long ownerId) {
        Owner owner = ownerService.findById(ownerId);
        return new ModelAndView("pets/createOrUpdatePetForm", "pet", Pet.builder().owner(owner).build());
    }

    @PostMapping("/pets/new")
    public ModelAndView postNew(@PathVariable Long ownerId, @Valid Pet pet, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("pets/createOrUpdatePetForm", "pet", pet);
        }
        Owner owner = ownerService.findById(ownerId);

        owner.addPet(pet);
        pet.setOwner(owner);

        ownerService.save(owner);

        return new ModelAndView("redirect:/owners/" + ownerId, "owner", owner);
    }

    @GetMapping("/pets/{petId}/edit")
    public ModelAndView getEdit(@PathVariable Long petId) {
        Pet pet = petService.findById(petId);
        return new ModelAndView(CREATE_OR_UPDATE_PET_FORM, "pet", pet);
    }

    @PostMapping("/pets/{petId}/edit")
    public ModelAndView postEdit(@Valid Pet pet, BindingResult result, Owner owner) {
        if (result.hasErrors()) {
            pet.setOwner(owner);
            return new ModelAndView(CREATE_OR_UPDATE_PET_FORM, "pet", Pet.builder().build());
        } else {
            owner.getPets().removeIf(p -> p.getId() == pet.getId());
            owner.addPet(pet);
            ownerService.save(owner);
            return new ModelAndView("redirect:/owners/" + owner.getId(), "owner", owner);
        }
    }
}
