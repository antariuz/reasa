package avada.spacelab.reasa.model;

import avada.spacelab.reasa.model.common.MappedEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "_user")
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User extends MappedEntity implements UserDetails {
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Provider provider;
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

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Review> likedReviews = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<RealEstate> favoriteRealEstates = new ArrayList<>();

    //  fixme: rework when Chat feature be ready
//    @ManyToMany(fetch = FetchType.LAZY)
//    private List<Chat> chats = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public enum Provider {
        LOCAL, GOOGLE, FACEBOOK, APPLE
    }
}
