package guru.springframework.sfgpetclinic.model;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Specialty extends BaseEntity {
    private String description;
}
