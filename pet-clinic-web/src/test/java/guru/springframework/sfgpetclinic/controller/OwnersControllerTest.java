package guru.springframework.sfgpetclinic.controller;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.service.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class OwnersControllerTest {

    @Mock
    private OwnerService ownerService;

    @InjectMocks
    private OwnersController controller;

    private Set<Owner> owners;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        owners =  new HashSet<>();
        owners.add(Owner.builder().id(1L).build());
        owners.add(Owner.builder().id(2L).build());

        mockMvc = standaloneSetup(controller).build();
    }

    @Test
    void index() throws Exception {
        when(ownerService.findAll()).thenReturn(owners);

        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owner/index"))
                .andExpect(model().attribute("owners", owners));
    }

    @Test
    void findOwner() throws Exception {
        mockMvc.perform(get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("owner/findOwners"))
                .andExpect(model().attribute("owner", notNullValue()));
    }

    @Test
    void ownerDetails() throws Exception {
        when(ownerService.findById(123L)).thenReturn(Owner.builder().id(123L).build());

        mockMvc.perform(get("/owners/123"))
                .andExpect(status().isOk())
                .andExpect(view().name("owner/ownerDetails"))
                .andExpect(model().attribute("owner", hasProperty("id", equalTo(123L))));
    }
}