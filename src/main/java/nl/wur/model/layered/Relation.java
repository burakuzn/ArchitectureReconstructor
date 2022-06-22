package nl.wur.model.layered;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.wur.hutn.HutnConverter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Relation {
    private Layer sourceLayer,targetLayer;
    @Override
    public String toString() {
        return HutnConverter.convert(this);
    }
}
