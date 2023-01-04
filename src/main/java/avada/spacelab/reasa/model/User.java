package avada.spacelab.reasa.model;

import avada.spacelab.reasa.model.common.MappedEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "_user")
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User extends MappedEntity {

    private String email;
    private String password;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserProfile userProfile;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserIdentification userIdentification;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserNotificationSettings userNotificationSettings;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserSecuritySettings userSecuritySettings;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserPayment userPayment;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private HelpCenterFaq helpCenterFaq;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private HelpCenterContactUs helpCenterContactUs;

    //  fixme: use when classes be ready
//    @ManyToMany(fetch = FetchType.LAZY)
//    private List<Review> reviews = new ArrayList<>();
//    @ManyToMany(fetch = FetchType.LAZY)
//    private Set<Review> likedReviews = new HashSet<>();
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    private List<RealEstate> favoriteRealEstates = new ArrayList<>();

    //  fixme: rework when Chat feature be ready
//    @ManyToMany(fetch = FetchType.LAZY)
//    private List<Chat> chats = new ArrayList<>();

}
