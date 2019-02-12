package guru.springframework.sfgpetclinic.bootstrap;

import com.google.common.collect.Sets;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.service.OwnerService;
import guru.springframework.sfgpetclinic.service.PetTypeService;
import guru.springframework.sfgpetclinic.service.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
    }

    @Override
    public void run(String... args) throws Exception {

        PetType dog = new PetType();
        dog.setType("dog");
        petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setType("cat");
        petTypeService.save(cat);

        Owner owner1 = new Owner();
        owner1.setFirstName("Jim");
        owner1.setLastName("Poul");
        owner1.setAddress("123 Street A");
        owner1.setCity("Montréal");
        Pet pet1 = new Pet();
        pet1.setType(dog);
        pet1.setOwner(owner1);
        pet1.setBirthDate(LocalDate.now().minus(Period.ofYears(2)));
        pet1.setName("Pitou");
        owner1.setPets(Sets.newHashSet(pet1));
        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Jimmy");
        owner2.setLastName("Pouliot");
        Pet pet2 = new Pet();
        pet2.setType(cat);
        pet2.setOwner(owner2);
        pet2.setBirthDate(LocalDate.now().minus(Period.ofYears(3)));
        pet2.setName("Nova");
        owner2.setPets(Sets.newHashSet(pet2));
        ownerService.save(owner2);

        Vet vet1 = new Vet();
        vet1.setFirstName("Vetty");
        vet1.setLastName("Fixpets");
        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Vett");
        vet2.setLastName("Fixmoarpets");
        vetService.save(vet2);
    }
}
