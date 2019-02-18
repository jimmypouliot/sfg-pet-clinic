package guru.springframework.sfgpetclinic.model;

import javax.persistence.Entity;

@Entity
public class PetType extends BaseEntity {

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
