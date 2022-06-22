package nl.wur.model.generalization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.wur.hutn.HutnConverter;
import nl.wur.model.Viewpoint;

import java.util.Set;

@Getter
@AllArgsConstructor
public class Generalization implements Viewpoint {
    private Set<GeneralizationModule> generalizationModules;
    private Set<GeneralizationRelation> generalizationRelations;

    @Override
    public String toString() {
      return HutnConverter.convert(this);
    }

    @Override
    public String getName() {
        return "samplecase_generalization";
    }

    @Override
    public String getMetamodel() {
        return "Generalization";
    }

    @Override
    public String getNsUri() {
        return "Generalization";
    }
}
