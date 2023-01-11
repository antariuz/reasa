package avada.spacelab.reasa.model;

import avada.spacelab.reasa.model.common.MappedEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class BankingCard extends MappedEntity {

    //  fixme: dummy class
    private String name;
    private String number;
    private LocalDate expiryDate;
    private String cvv;

}
