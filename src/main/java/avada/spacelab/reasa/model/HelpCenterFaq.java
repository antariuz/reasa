package avada.spacelab.reasa.model;

import avada.spacelab.reasa.model.common.MappedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Map;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = true)
public class HelpCenterFaq extends MappedEntity {

    private Map<String, String> faqList;

}
