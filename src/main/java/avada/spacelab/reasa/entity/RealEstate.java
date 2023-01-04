package avada.spacelab.reasa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "real_estates")
public class RealEstate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private User user;
    @JoinColumn(name = "owner_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private Owner owner;
    @JoinColumn(name = "real_estate_id")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private List<Gallery> galleries;
    @JoinColumn(name = "realEstate_id")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private List<Review> reviews;
    @ManyToMany(mappedBy = "realEstates")
    private List<Facility> facilities;

    private String name;
    private String location;
    private int price;
    private float averageRating;
    private String image;
    @Enumerated(EnumType.STRING)
    private Type type;

    private String overview;
    private byte beds;
    private byte bathrooms;
    private int sqft;




}
