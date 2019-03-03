package guru.springframework.sfgpetclinic.service.jpa;

import com.google.common.collect.Sets;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repository.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JpaOwnerServiceTest {

    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private JpaOwnerService service;

    @Test
    void findByLastName() {
        Owner owner = Owner.builder().id(1L).lastName("Smith").build();

        when(ownerRepository.findAllByLastNameLikeIgnoreCase("%Smith%")).thenReturn((Sets.newHashSet(owner)));

        assertEquals(owner, service.findAllByLastNameLikeIgnoreCase("Smith").iterator().next());
    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void save() {
    }

    @Test
    void delete() {
    }

    @Test
    void deleteById() {
    }
}