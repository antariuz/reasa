package avada.spacelab.reasa.model;

import avada.spacelab.reasa.model.common.MappedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = true)
public class Chat extends MappedEntity {

    //  fixme: dummy class
//    private Set<User> participants = new HashSet<>();
//    private List<Document> documents = new ArrayList<>();
//    private List<Image> images = new ArrayList<>();
//    private List<Voice> voices = new ArrayList<>();
    @CreationTimestamp
    private Date createdAt;

}
