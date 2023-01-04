package avada.spacelab.reasa.model;

import avada.spacelab.reasa.model.common.MappedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.*;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = true)
public class Chat extends MappedEntity {

    //  fixme: dummy class
    private Set<User> participants = new HashSet<>();
    //  List<Document>
    private List<String> documents = new ArrayList<>();
    //  List<Image>
    private List<String> images = new ArrayList<>();
    //  List<Voice>
    private List<String> voices = new ArrayList<>();
    @CreationTimestamp
    private Date createdAt;

}
