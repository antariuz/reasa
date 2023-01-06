package avada.spacelab.reasa.model;


import avada.spacelab.reasa.model.common.MappedEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "facilities")
public class Facility extends MappedEntity {
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinTable(
            name = "real_estate_facility",
            joinColumns = @JoinColumn(name = "real_estate_id"),
            inverseJoinColumns = @JoinColumn(name = "facility_id"))
    private List<RealEstate> realEstates;

    private String name;
    private String image;
}
