package formula1.api.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import formula1.api.exceptions.misc.UnknownEnumValueException;

import java.util.Arrays;

public enum DobOrderEnum {
    YTO("YTO"),
    OTY("OTY");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static DobOrderEnum of(String value) {
        if (null == value) {
            return null;
        }

        for (DobOrderEnum item : DobOrderEnum.values()) {
            if (value.equals(item.getValue())) {
                return item;
            }
        }

        throw new UnknownEnumValueException("Unknown value: " + value + "\nSupported values: " + Arrays.toString(DobOrderEnum.values()));
    }

    DobOrderEnum(String value) {
        this.value = value;
    }
}
