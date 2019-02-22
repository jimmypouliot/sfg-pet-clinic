package guru.springframework.sfgpetclinic.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(exclude = {"pet"}, callSuper = false)
public class Visit extends BaseEntity {

    @ManyToOne
    private Pet pet;
    private LocalDate date;
    private String description;

}
