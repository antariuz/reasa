package avada.spacelab.reasa.model;

import avada.spacelab.reasa.model.common.MappedEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProfile extends MappedEntity {

    private String fullName;
    private String nickname;
    private LocalDate birthday;
    private String phoneNumber;
    @OneToOne(fetch = FetchType.LAZY)
    private Country country;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String profilePhoto;
    private boolean isDarkMode;
    @OneToOne(fetch = FetchType.LAZY)
    private Language language;

    @Getter
    public enum Gender {
        MALE,
        FEMALE
    }

}
