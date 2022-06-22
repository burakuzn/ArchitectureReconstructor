package nl.wur.model.generalization;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.wur.hutn.HutnConverter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class GeneralizationRelation {
    private GeneralizationModule parent;
    private GeneralizationModule child;
    private RelationType relationType;

    @Override
    public String toString() {
        return HutnConverter.convert(this);
    }

    public enum RelationType {
        INHERIT("Inheritance"), IMPLEMENT("Implementation");
        private final String value;

        RelationType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
