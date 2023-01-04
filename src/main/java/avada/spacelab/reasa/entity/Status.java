package avada.spacelab.reasa.entity;

import lombok.Getter;

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
