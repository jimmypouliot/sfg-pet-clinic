package guru.springframework.sfgpetclinic.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@ToString(exclude = "owner")
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
    private Set<Visit> visits;

    @Builder
    public Pet(Long id, PetType type, Owner owner, LocalDate birthDate, String name, Set<Visit> visits) {
        super(id);
        this.type = type;
        this.owner = owner;
        this.birthDate = birthDate;
        this.name = name;
        this.visits = visits != null ? visits : new HashSet<>();
    }

    public boolean isNew() {
        return getId() == null;
    }
}
