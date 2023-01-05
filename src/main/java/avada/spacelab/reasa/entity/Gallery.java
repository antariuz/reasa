package avada.spacelab.reasa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "galleries")
public class Gallery {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JoinColumn(name = "real_estate_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private RealEstate realEstate;
    @JoinColumn(name = "gallery_id")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private List<Image> images;
    private String name;
}
