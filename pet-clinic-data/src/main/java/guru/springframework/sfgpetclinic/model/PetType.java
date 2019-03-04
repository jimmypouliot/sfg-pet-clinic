package guru.springframework.sfgpetclinic.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class PetType extends BaseEntity {

    private String type;

    @Builder
    public PetType(Long id, String type) {
        super(id);
        this.type = type;
    }

    @Override
    public String toString() {
        return String.valueOf(type);
    }
}
