package avada.spacelab.reasa.model;

import avada.spacelab.reasa.model.common.MappedEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "galleries")
@NoArgsConstructor
@AllArgsConstructor
public class Gallery extends MappedEntity {
    private String name;
    @JoinColumn(name = "real_estate_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private RealEstate realEstate;
    @JoinColumn(name = "gallery_id")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private List<Image> images;

}
