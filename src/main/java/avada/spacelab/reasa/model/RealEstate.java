package avada.spacelab.reasa.model;

import avada.spacelab.reasa.model.common.MappedEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "real_estates")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RealEstate extends MappedEntity {
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private User user;
    @JoinColumn(name = "owner_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private Owner owner;
    @JoinColumn(name = "real_estate_id")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private List<Gallery> galleries = new ArrayList<>();
    @JoinColumn(name = "realEstate_id")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private List<Review> reviews;
    @ManyToMany(mappedBy = "realEstates")
    private List<Facility> facilities;

    private String name;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Location location;
    private int price;
    private float averageRating;
    private String image;
    @Enumerated(EnumType.STRING)
    private Type type;

    private String overview;
    private int beds;
    private int bathrooms;
    private int sqft;

    @Getter
    public enum Type {
        HOUSE("House"),
        VILLA("Villa"),
        APARTMENT("Apartment");

        private final String value;
        Type(String value) {
            this.value = value;
        }
    }
}
