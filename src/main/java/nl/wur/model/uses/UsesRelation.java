package nl.wur.model.uses;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.wur.hutn.HutnConverter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class UsesRelation {
    private UsesModule target;
    private UsesModule source;

    @Override
    public String toString() {
        return HutnConverter.convert(this);
    }

}
