package guru.springframework.sfgpetclinic.service.map;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.service.PetService;
import guru.springframework.sfgpetclinic.service.PetTypeService;
import guru.springframework.sfgpetclinic.service.VisitService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"default", "map"})
public class PetServiceMap extends AbstractMapService<Pet, Long> implements PetService {
    private PetTypeService petTypeService;
    private VisitService visitService;

    public PetServiceMap(PetTypeService petTypeService, VisitService visitService) {
        this.petTypeService = petTypeService;
        this.visitService = visitService;
    }

    @Override
    public Pet save(Pet object) {
        petTypeService.save(object.getType());
        object.getVisits().forEach(visitService::save);
        return super.save(object);
    }
}
