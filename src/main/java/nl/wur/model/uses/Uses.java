package nl.wur.model.uses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.wur.hutn.HutnConverter;
import nl.wur.model.Viewpoint;

import java.util.Set;

@Getter
@AllArgsConstructor
public class Uses implements Viewpoint {

    private Set<UsesModule> usesModules;
    private Set<UsesRelation> usesRelations;

    @Override
    public String toString() {
        return HutnConverter.convert(this);
    }

    @Override
    public String getName() {
        return "samplecase_uses";
    }

    @Override
    public String getMetamodel() {
        return "Uses";
    }

    @Override
    public String getNsUri() {
        return "Uses";
    }
}
