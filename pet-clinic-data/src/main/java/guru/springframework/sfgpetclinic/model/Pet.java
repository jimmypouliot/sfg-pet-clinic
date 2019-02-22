package guru.springframework.sfgpetclinic.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(exclude = {"owner"}, callSuper = false)
public class Pet extends BaseEntity {

    @ManyToOne
    private PetType type;

    @ManyToOne
    private Owner owner;

    private LocalDate birthDate;

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "pet_visit", inverseJoinColumns = @JoinColumn(name = "visit_id"))
    private Set<Visit> visits = new HashSet<>();

}
