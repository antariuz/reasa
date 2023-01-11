package avada.spacelab.reasa.model;

import avada.spacelab.reasa.model.common.MappedEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "owners")
@NoArgsConstructor
@AllArgsConstructor
public class Owner extends MappedEntity {
    @JoinColumn(name = "owner_id")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private List<RealEstate> realEstates = new ArrayList<>();
    private String firstName;
    private String lastName;
    private String profilePhoto;
    private String email;

    @Transient
    public String getProfilePhoto() {
        String PHOTO_PATH = "/uploaded/";
        if (profilePhoto == null || getId() == null) return null;
        else return PHOTO_PATH + profilePhoto;
    }

}
