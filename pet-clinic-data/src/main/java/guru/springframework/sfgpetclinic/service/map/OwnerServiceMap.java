package guru.springframework.sfgpetclinic.service.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.service.OwnerService;
import guru.springframework.sfgpetclinic.service.PetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"default", "map"})
public class OwnerServiceMap extends AbstractMapService<Owner, Long> implements OwnerService {

    private PetService petService;

    public OwnerServiceMap(PetService petService) {
        this.petService = petService;
    }

    @Override
    public Owner findByLastName(String lastName) {
        return findAll()
                .stream()
                .filter(o -> lastName.equals(o.getLastName()))
                .findAny()
                .orElse(null);
    }

    @Override
    public Owner save(Owner object) {
        object.getPets().forEach(petService::save);

        return super.save(object);
    }
}
