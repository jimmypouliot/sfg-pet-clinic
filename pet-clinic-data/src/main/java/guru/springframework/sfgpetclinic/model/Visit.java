package guru.springframework.sfgpetclinic.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Data
@ToString(exclude = "pet")
@EqualsAndHashCode(exclude = {"pet"}, callSuper = false)
public class Visit extends BaseEntity {

    @ManyToOne
    private Pet pet;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private String description;

    public boolean isNew() {
        return getId() == null;
    }
}
