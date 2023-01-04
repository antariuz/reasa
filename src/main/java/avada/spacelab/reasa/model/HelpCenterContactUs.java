package avada.spacelab.reasa.model;

import avada.spacelab.reasa.model.common.MappedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = true)
public class HelpCenterContactUs extends MappedEntity {

    //  fixme: rework customerServiceChat
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Chat serviceChat;
    private String whatsApp;
    private String website;
    private String facebook;
    private String twitter;
    private String instagram;

}
