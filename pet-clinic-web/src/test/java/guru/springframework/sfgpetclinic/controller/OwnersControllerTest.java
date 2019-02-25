package guru.springframework.sfgpetclinic.controller;

import com.google.common.collect.Sets;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.service.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
                .andExpect(view().name("owner/ownersList"))
                .andExpect(model().attribute("selections", owners));
    }

    @Test
    void findOwner() throws Exception {
        mockMvc.perform(get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("owner/findOwners"))
                .andExpect(model().attribute("owner", notNullValue()));
    }

    @Test
    void foundOwnerFoundZero() throws Exception {
        when(ownerService.findByLastName("NotFoundName")).thenReturn(Collections.emptySet());

        mockMvc.perform(get("/owners").param("lastName", "NotFoundName"))
                .andExpect(status().isOk())
                .andExpect(view().name("owner/findOwners"))
                .andExpect(model().attributeHasFieldErrors("owner", "lastName"));
    }

    @Test
    void foundOwnerFoundOne() throws Exception {
        Set<Owner> oneOwnerSet = Sets.newHashSet(Owner.builder().id(12L).lastName("OneName").build());

        when(ownerService.findByLastName("OneName")).thenReturn(oneOwnerSet);

        mockMvc.perform(get("/owners").param("lastName", "OneName"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/12"));
    }

    @Test
    void foundOwnerFoundSeveral() throws Exception {
        Set<Owner> severalOwners = Sets.newHashSet(
                Owner.builder().id(12L).firstName("Bob").lastName("OneName").build(),
                Owner.builder().id(123L).firstName("Joe").lastName("OneName").build());

        assertEquals(2, severalOwners.size());

        when(ownerService.findByLastName("OneName")).thenReturn(severalOwners);

        mockMvc.perform(get("/owners").param("lastName", "OneName"))
                .andExpect(status().isOk())
                .andExpect(view().name("owner/ownersList"))
                .andExpect(model().attribute("selections", is(severalOwners)));
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