package nl.wur.model.layered;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.wur.hutn.HutnConverter;
import nl.wur.model.uses.UsesModule;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Layer {
    private String name, id;

    public Layer(UsesModule target) {
        this.id = target.getId();
        this.name = target.getName();
    }

    @Override
    public String toString() {
        return HutnConverter.convert(this);
    }
}
