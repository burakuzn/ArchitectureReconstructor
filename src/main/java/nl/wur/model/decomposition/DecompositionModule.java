package nl.wur.model.decomposition;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.wur.hutn.HutnConverter;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode
public class DecompositionModule {
    private String name, id;
    private Set<DecompositionModule> subElements;

    public DecompositionModule(String aPackage, Collection<String> subElements) {
        this(aPackage);
        this.subElements = subElements.stream().map(DecompositionModule::new).collect(Collectors.toSet());
    }

    public DecompositionModule(String aPackage) {
        this.name = aPackage;
        this.id = aPackage;
    }

    @Override
    public String toString() {
        return HutnConverter.convert(this);
    }
}
