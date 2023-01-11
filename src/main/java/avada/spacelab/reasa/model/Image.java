package avada.spacelab.reasa.model;

import avada.spacelab.reasa.model.common.MappedEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "images")
@NoArgsConstructor
public class Image extends MappedEntity {
    private String image;
    @JoinColumn(name = "gallery_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private Gallery gallery;

    public Image(String image) {
        this.image = image;
    }

}
