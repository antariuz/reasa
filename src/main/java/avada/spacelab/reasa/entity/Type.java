package avada.spacelab.reasa.entity;

import lombok.Getter;

@Getter
public enum Type {
    HOUSE("House"),
    VILLA("Villa"),
    APARTMENT("Apartment");

    private final String value;
    Type(String value) {
        this.value = value;
    }
}
