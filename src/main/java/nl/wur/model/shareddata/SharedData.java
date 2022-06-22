package nl.wur.model.shareddata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.wur.hutn.HutnConverter;
import nl.wur.model.Viewpoint;

import java.util.Set;

@Getter
@AllArgsConstructor
public class SharedData implements Viewpoint {
    private Set<Repository> repositorySet;
    private Set<DataAccessor> dataAccessorSet;
    private Set<DataOperation> dataOperationSet;

    @Override
    public String toString() {
        return HutnConverter.convert(this);
    }

    @Override
    public String getName() {
        return "samplecase_shared_data";
    }

    @Override
    public String getMetamodel() {
        return "Shared_Data";
    }

    @Override
    public String getNsUri() {
        return "Shared_Data";
    }
}
