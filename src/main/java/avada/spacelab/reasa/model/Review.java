package avada.spacelab.reasa.model;

import avada.spacelab.reasa.model.common.MappedEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "reviews")
public class Review extends MappedEntity {
    @JoinColumn(name = "realEstate_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private RealEstate realEstate;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private List<User> users;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private Set<User> likes;

    private String text;
    private byte rate;
    private Date createdAt;
}
