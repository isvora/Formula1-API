package formula1.api.converters;

import formula1.api.types.DobOrderEnum;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

@Component
public class AgeOrderTypeConverter implements Converter<String, DobOrderEnum> {
    @Override
    public DobOrderEnum convert(String value) {
        return DobOrderEnum.of(value);
    }
}