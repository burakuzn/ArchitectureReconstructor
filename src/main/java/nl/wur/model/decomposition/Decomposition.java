package nl.wur.model.decomposition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.wur.hutn.HutnConverter;
import nl.wur.model.Viewpoint;

import java.util.Set;

@Getter
@AllArgsConstructor
public class Decomposition implements Viewpoint {

    private Set<DecompositionModule> modules;

    @Override
    public String toString() {
        return HutnConverter.convert(this);
    }

    @Override
    public String getName() {
        return "sampleacase_decomposition";
    }

    @Override
    public String getMetamodel() {
        return "Decomposition";
    }

    @Override
    public String getNsUri() {
        return "Decomposition";
    }
}
