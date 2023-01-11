package avada.spacelab.reasa.model;

import avada.spacelab.reasa.model.common.MappedEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ContactUs extends MappedEntity {

    //  fixme: rework serviceChat
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Chat serviceChat;
    private String whatsApp;
    private String website;
    private String facebook;
    private String twitter;
    private String instagram;

}
