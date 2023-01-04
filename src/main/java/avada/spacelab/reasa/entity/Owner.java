package avada.spacelab.reasa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "owners")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JoinColumn(name = "owner_id")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private List<RealEstate> realEstates;
    private String firstName;
    private String lastName;
    private String profilePhoto;
}
