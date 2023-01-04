package avada.spacelab.reasa.model;

import avada.spacelab.reasa.model.common.MappedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = true)
public class BankingCard extends MappedEntity {

    //  fixme: dummy class
    private String name;
    private String number;
    private LocalDate expiryDate;
    private String cvv;

}
