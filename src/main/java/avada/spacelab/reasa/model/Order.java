package avada.spacelab.reasa.model;

import avada.spacelab.reasa.model.common.MappedEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order extends MappedEntity {
    private UUID transactionId;

    @JoinColumn(name = "real_estate_id")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private RealEstate realEstate;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    private int tax;
    private int total;
    private String noteToOwner;
    private String barcodeImage;
    @Enumerated(EnumType.STRING)
    private Status status;
    private boolean active;

    @Getter
    public enum Status {
        UNPAID("Unpaid"),
        PAID("Paid"),
        COMPLETED("Completed");

        private final String value;
        Status(String value) {
            this.value = value;
        }
    }
}
