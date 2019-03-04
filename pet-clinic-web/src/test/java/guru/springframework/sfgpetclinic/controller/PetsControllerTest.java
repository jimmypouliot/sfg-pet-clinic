package guru.springframework.sfgpetclinic.controller;

import com.google.common.collect.Sets;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.service.OwnerService;
import guru.springframework.sfgpetclinic.service.PetService;
import guru.springframework.sfgpetclinic.service.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PetsControllerTest {

    @Mock
    OwnerService ownerService;

    @Mock
    PetService petService;

    @Mock
    PetTypeService petTypeService;

    @InjectMocks
    PetsController petsController;

    MockMvc mockMvc;

    Owner owner;
    Set<PetType> petTypes;

    @BeforeEach
    void setUp() {
        Pet pet = Pet.builder().id(1L).name("The pet").build();
        owner = Owner.builder().id(1L).build();

        petTypes = Sets.newHashSet(
                PetType.builder().id(1L).type("Dog").build(),
                PetType.builder().id(2L).type("Cat").build()
        );

        when(ownerService.findById(1L)).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        mockMvc = MockMvcBuilders.standaloneSetup(petsController).build();
    }

    @Test
    void processGetNew() throws Exception {
        mockMvc.perform(get("/owners/1/pets/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner", "pet", "petTypes"))
                .andExpect(view().name("pets/createOrUpdatePetForm"));
    }

    @Test
    void processPostNew() throws Exception {
        mockMvc.perform(post("/owners/1/pets/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));

        verify(ownerService).save(owner);
    }

    @Test
    void processGetEdit() throws Exception {
        when(petService.findById(1L)).thenReturn(Pet.builder().id(1L).build());

        mockMvc.perform(get("/owners/1/pets/1/edit"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner", "pet", "petTypes"))
                .andExpect(view().name("pets/createOrUpdatePetForm"));
    }

    @Test
    void processPostEdit() throws Exception {
        mockMvc.perform(post("/owners/1/pets/1/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));

        verify(ownerService).save(any());
    }
}