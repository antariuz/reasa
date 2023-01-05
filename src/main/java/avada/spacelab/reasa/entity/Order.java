package avada.spacelab.reasa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
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
}
