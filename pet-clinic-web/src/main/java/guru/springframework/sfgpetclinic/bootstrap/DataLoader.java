package guru.springframework.sfgpetclinic.bootstrap;

import com.google.common.collect.Sets;
import guru.springframework.sfgpetclinic.model.*;
import guru.springframework.sfgpetclinic.service.OwnerService;
import guru.springframework.sfgpetclinic.service.PetTypeService;
import guru.springframework.sfgpetclinic.service.SpecialtyService;
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
    private final SpecialtyService specialtyService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialtyService specialtyService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialtyService = specialtyService;
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
        owner1.setLastName("Pool");
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
        pet2.setBirthDate(LocalDate.now().minus(Period.ofYears(3)));
        pet2.setName("Nova");
        owner2.setPets(Sets.newHashSet(pet2));
        Visit pet2Visit = new Visit();
        pet2Visit.setDate(LocalDate.now());
        pet2Visit.setDescription("Bobo");
        pet2Visit.setPet(pet2);
        pet2.setVisits(Sets.newHashSet(pet2Visit));
        ownerService.save(owner2);

        Owner owner3 = new Owner();
        owner3.setFirstName("Not Jimmy");
        owner3.setLastName("Pouliot");
        Pet pet3 = new Pet();
        pet3.setType(cat);
        pet3.setBirthDate(LocalDate.now().minus(Period.ofYears(3)));
        pet3.setName("Not Nova");
        owner3.setPets(Sets.newHashSet(pet3));
        Visit pet3Visit = new Visit();
        pet3Visit.setDate(LocalDate.now());
        pet3Visit.setDescription("Gros Bobo");
        pet3Visit.setPet(pet3);
        pet3.setVisits(Sets.newHashSet(pet3Visit));
        ownerService.save(owner3);

        Specialty radiology = new Specialty();
        radiology.setDescription("radiology");
        specialtyService.save(radiology);

        Specialty surgery = new Specialty();
        surgery.setDescription("surgery");
        specialtyService.save(surgery);

        Specialty dentistry = new Specialty();
        dentistry.setDescription("dentistry");
        specialtyService.save(dentistry);

        Vet vet1 = new Vet();
        vet1.setFirstName("Vetty");
        vet1.setLastName("Fixpets");
        vet1.setSpecialties(Sets.newHashSet(radiology, surgery));
        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Vett");
        vet2.setLastName("Fixmoarpets");
        vet2.setSpecialties(Sets.newHashSet(dentistry));
        vetService.save(vet2);
    }
}
