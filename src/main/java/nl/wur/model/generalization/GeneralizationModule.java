package nl.wur.model.generalization;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.wur.hutn.HutnConverter;

@Getter
@EqualsAndHashCode
public class GeneralizationModule {
    private String name, id;

    public GeneralizationModule(Class clazz) {
        this.name = clazz.getCanonicalName();
        this.id = clazz.getCanonicalName();
    }

    @Override
    public String toString() {
        return HutnConverter.convert(this);
    }
}
