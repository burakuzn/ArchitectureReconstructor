package nl.wur.model.layered;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.wur.hutn.HutnConverter;
import nl.wur.model.Viewpoint;

import java.util.Set;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Layered implements Viewpoint {
    private Set<Layer> layers;
    private Set<Relation> relations;

    @Override
    public String toString() {
        return HutnConverter.convert(this);
    }

    @Override
    public String getName() {
        return "samplecase_layered";
    }

    @Override
    public String getMetamodel() {
        return "Layered";
    }

    @Override
    public String getNsUri() {
        return "Layered";
    }
}
