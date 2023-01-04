package avada.spacelab.reasa.model;

import avada.spacelab.reasa.model.common.MappedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = true)
public class HelpCenterFaq extends MappedEntity {

    //  fixme: rework
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "help_center_faq_id", referencedColumnName = "id")
    private List<Faq> faqList = new ArrayList<>();

}
