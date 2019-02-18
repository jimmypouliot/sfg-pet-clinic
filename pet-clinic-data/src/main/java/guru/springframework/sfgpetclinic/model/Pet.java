package guru.springframework.sfgpetclinic.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
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

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Set<Visit> getVisits() {
        return visits;
    }

    public void setVisits(Set<Visit> visits) {
        this.visits = visits;
    }
}
