package nl.wur.hutn;

import nl.wur.model.Viewpoint;
import nl.wur.model.decomposition.Decomposition;
import nl.wur.model.decomposition.DecompositionModule;
import nl.wur.model.generalization.Generalization;
import nl.wur.model.generalization.GeneralizationModule;
import nl.wur.model.generalization.GeneralizationRelation;
import nl.wur.model.layered.Layer;
import nl.wur.model.layered.Layered;
import nl.wur.model.layered.Relation;
import nl.wur.model.shareddata.*;
import nl.wur.model.uses.Uses;
import nl.wur.model.uses.UsesModule;
import nl.wur.model.uses.UsesRelation;

import java.util.stream.Collectors;

public class HutnConverter {
    private static final String TAB = "\t";
    private static final String NEW_LINE = "\n";
    private static final String QUOTATION = "\"";
    private static final String EMPTY_SPACE = " ";
    private static final String OPEN_PARANTHESIS = "{";
    private static final String CLOSE_PARANTHESIS = "}";
    private static final String SEMI_COLON = ":";
    private static final String JOINT = ",\n";
    private static final String COMMA = ",";

    public static String convert(Generalization generalization) {
        return new StringBuilder()
                .append(getHeader(generalization))
                .append("generalization")
                .append(EMPTY_SPACE).append(OPEN_PARANTHESIS).append(EMPTY_SPACE).append(NEW_LINE).append(TAB)
                .append("GeneralizationModel").append(EMPTY_SPACE).append(QUOTATION).append("generalization").append(QUOTATION)
                .append(EMPTY_SPACE).append(OPEN_PARANTHESIS).append(NEW_LINE)
                .append(TAB).append(TAB)
                .append("modules")
                .append(EMPTY_SPACE).append(SEMI_COLON).append(NEW_LINE)
                .append(String.join(JOINT, generalization.getGeneralizationModules().stream().map(GeneralizationModule::toString).collect(Collectors.toList())))
                .append(NEW_LINE).append(TAB).append(TAB).append("decl").append(EMPTY_SPACE).append(SEMI_COLON)
                .append(NEW_LINE)
                .append(String.join(JOINT, generalization.getGeneralizationRelations().stream().map(GeneralizationRelation::toString).collect(Collectors.toList())))
                .append(NEW_LINE).append(TAB).append(CLOSE_PARANTHESIS).append(NEW_LINE).append(CLOSE_PARANTHESIS)
                .toString();
    }

    private static String getHeader(Viewpoint viewpoint) {
        return new StringBuilder().append("@Spec")
                .append(EMPTY_SPACE).append(OPEN_PARANTHESIS).append(NEW_LINE).append(TAB)
                .append("metamodel").append(EMPTY_SPACE).append(QUOTATION)
                .append(viewpoint.getMetamodel())
                .append(QUOTATION).append(EMPTY_SPACE).append(OPEN_PARANTHESIS).append(NEW_LINE)
                .append(TAB).append(TAB)
                .append("nsUri").append(EMPTY_SPACE).append(SEMI_COLON).append(EMPTY_SPACE)
                .append(QUOTATION)
                .append(viewpoint.getNsUri())
                .append(QUOTATION).append(NEW_LINE).append(TAB)
                .append(CLOSE_PARANTHESIS).append(NEW_LINE)
                .append(CLOSE_PARANTHESIS).append(NEW_LINE)
                .toString();
    }

    public static String convert(GeneralizationModule generalizationModule) {
        return new StringBuilder()
                .append(TAB).append(TAB).append(TAB)
                .append("Module")
                .append(EMPTY_SPACE).append(QUOTATION)
                .append(generalizationModule.getId())
                .append(QUOTATION).append(EMPTY_SPACE).append(OPEN_PARANTHESIS).append(EMPTY_SPACE)
                .append(NEW_LINE).append(TAB).append(TAB).append(TAB).append(TAB)
                .append("name")
                .append(EMPTY_SPACE).append(SEMI_COLON).append(EMPTY_SPACE).append(QUOTATION)
                .append(generalizationModule.getName())
                .append(QUOTATION).append(NEW_LINE).append(TAB).append(TAB).append(TAB).append(CLOSE_PARANTHESIS).toString();
    }

    public static String convert(GeneralizationRelation generalizationRelation) {
        return new StringBuilder()
                .append(TAB).append(TAB).append(TAB)
                .append(generalizationRelation.getRelationType().getValue())
                .append(EMPTY_SPACE).append(OPEN_PARANTHESIS).append(NEW_LINE)
                .append(TAB).append(TAB).append(TAB).append(TAB)
                .append("child")
                .append(EMPTY_SPACE).append(SEMI_COLON).append(EMPTY_SPACE)
                .append("Module")
                .append(EMPTY_SPACE).append(QUOTATION)
                .append(generalizationRelation.getChild().getId())
                .append(QUOTATION).append(NEW_LINE).append(TAB).append(TAB).append(TAB).append(TAB)
                .append("parent")
                .append(EMPTY_SPACE).append(SEMI_COLON).append(EMPTY_SPACE)
                .append("Module")
                .append(EMPTY_SPACE).append(QUOTATION)
                .append(generalizationRelation.getParent().getId())
                .append(QUOTATION).append(NEW_LINE).append(TAB).append(TAB).append(TAB).append(CLOSE_PARANTHESIS)
                .toString();
    }

    public static String convert(DecompositionModule decompositionModule) {
        StringBuilder stringBuilder = new StringBuilder().append(TAB).append(TAB).append(TAB)
                .append("Module").append(EMPTY_SPACE).append(QUOTATION)
                .append(decompositionModule.getId())
                .append(QUOTATION).append(EMPTY_SPACE).append(OPEN_PARANTHESIS).append(NEW_LINE)
                .append(TAB).append(TAB).append(TAB).append(TAB).append("name").append(EMPTY_SPACE).append(SEMI_COLON)
                .append(EMPTY_SPACE).append(QUOTATION).append(decompositionModule.getName()).append(QUOTATION);
        if (!decompositionModule.getSubElements().isEmpty()) {
            stringBuilder.append(NEW_LINE).append(TAB).append(TAB).append(TAB).append(TAB)
                    .append("subelements").append(EMPTY_SPACE).append(SEMI_COLON)
                    .append(EMPTY_SPACE);
        }
        stringBuilder.append(String.join(COMMA + EMPTY_SPACE, decompositionModule.getSubElements().stream().map(HutnConverter::getSubElementModule).collect(Collectors.toSet())));
        stringBuilder.append(NEW_LINE).append(TAB).append(TAB).append(TAB).append(CLOSE_PARANTHESIS);
        return stringBuilder
                .toString();
    }

    private static String getSubElementModule(DecompositionModule subElement) {
        return new StringBuilder().append("Module")
                .append(EMPTY_SPACE).append(QUOTATION)
                .append(subElement.getId()).append(QUOTATION)
                .toString();
    }

    public static String convert(Decomposition decomposition) {
        return new StringBuilder()
                .append(getHeader(decomposition)).append(EMPTY_SPACE).append("decomposition").append(EMPTY_SPACE).append(OPEN_PARANTHESIS).append(NEW_LINE)
                .append(TAB).append("Model").append(EMPTY_SPACE).append(QUOTATION).append("decomposition").append(QUOTATION)
                .append(EMPTY_SPACE).append(OPEN_PARANTHESIS).append(NEW_LINE).append(TAB).append(TAB)
                .append("elements").append(EMPTY_SPACE).append(SEMI_COLON).append(NEW_LINE)
                .append(String.join(JOINT, decomposition.getModules().stream().map(DecompositionModule::toString).collect(Collectors.toSet())))
                .append(NEW_LINE).append(TAB).append(CLOSE_PARANTHESIS).append(NEW_LINE)
                .append(CLOSE_PARANTHESIS)
                .toString();
    }

    public static String convert(UsesModule usesModule) {
        return new StringBuilder()
                .append(TAB).append(TAB).append(TAB).append("Module").append(EMPTY_SPACE)
                .append(QUOTATION).append(usesModule.getId()).append(QUOTATION).append(EMPTY_SPACE).append(OPEN_PARANTHESIS).append(NEW_LINE)
                .append(TAB).append(TAB).append(TAB).append(TAB).append("name").append(EMPTY_SPACE).append(SEMI_COLON).append(EMPTY_SPACE)
                .append(QUOTATION).append(usesModule.getName()).append(QUOTATION).append(NEW_LINE)
                .append(TAB).append(TAB).append(TAB).append(CLOSE_PARANTHESIS)
                .toString();
    }

    public static String convert(Uses uses) {
        return new StringBuilder()
                .append(getHeader(uses))
                .append("uses")
                .append(EMPTY_SPACE).append(OPEN_PARANTHESIS).append(NEW_LINE)
                .append(TAB).append("Model").append(EMPTY_SPACE).append(QUOTATION).append("uses").append(QUOTATION)
                .append(EMPTY_SPACE).append(OPEN_PARANTHESIS).append(NEW_LINE)
                .append(TAB).append(TAB).append("elements").append(EMPTY_SPACE)
                .append(SEMI_COLON).append(NEW_LINE)
                .append(String.join(JOINT, uses.getUsesModules().stream().map(UsesModule::toString).collect(Collectors.toSet())))
                .append(NEW_LINE).append(TAB).append(TAB).append("relations").append(EMPTY_SPACE)
                .append(SEMI_COLON).append(NEW_LINE)
                .append(String.join(JOINT, uses.getUsesRelations().stream().map(UsesRelation::toString).collect(Collectors.toSet())))
                .append(NEW_LINE).append(TAB).append(CLOSE_PARANTHESIS).append(NEW_LINE).append(CLOSE_PARANTHESIS)
                .toString();
    }

    public static String convert(UsesRelation usesRelation) {
        return new StringBuilder()
                .append(TAB).append(TAB).append(TAB)
                .append("Relation").append(EMPTY_SPACE).append(OPEN_PARANTHESIS).append(NEW_LINE)
                .append(TAB).append(TAB).append(TAB).append(TAB)
                .append("source").append(EMPTY_SPACE).append(SEMI_COLON).append(EMPTY_SPACE)
                .append("Module").append(EMPTY_SPACE).append(QUOTATION).append(usesRelation.getSource().getId()).append(QUOTATION).append(NEW_LINE)
                .append(TAB).append(TAB).append(TAB).append(TAB)
                .append("target").append(EMPTY_SPACE).append(SEMI_COLON).append(EMPTY_SPACE)
                .append("Module").append(EMPTY_SPACE).append(QUOTATION).append(usesRelation.getTarget().getId()).append(QUOTATION).append(NEW_LINE)
                .append(TAB).append(TAB).append(TAB).append(CLOSE_PARANTHESIS)
                .toString();
    }

    public static String convert(Repository repository) {
        return new StringBuilder()
                .append(TAB).append(TAB).append(TAB)
                .append("Repository").append(EMPTY_SPACE).append(QUOTATION).append(repository.getId()).append(QUOTATION)
                .append(EMPTY_SPACE).append(OPEN_PARANTHESIS).append(NEW_LINE)
                .append(TAB).append(TAB).append(TAB).append(TAB).append("name").append(EMPTY_SPACE)
                .append(SEMI_COLON).append(EMPTY_SPACE).append(QUOTATION).append(repository.getName()).append(QUOTATION)
                .append(NEW_LINE).append(TAB).append(TAB).append(TAB).append(CLOSE_PARANTHESIS)
                .toString();
    }

    public static String convert(DataAccessor dataAccessor) {
        return new StringBuilder()
                .append(TAB).append(TAB).append(TAB)
                .append("DataAccessor").append(EMPTY_SPACE).append(QUOTATION).append(dataAccessor.getId()).append(QUOTATION)
                .append(EMPTY_SPACE).append(OPEN_PARANTHESIS).append(NEW_LINE)
                .append(TAB).append(TAB).append(TAB).append(TAB).append("name").append(EMPTY_SPACE)
                .append(SEMI_COLON).append(EMPTY_SPACE).append(QUOTATION).append(dataAccessor.getName()).append(QUOTATION)
                .append(NEW_LINE).append(TAB).append(TAB).append(TAB).append(CLOSE_PARANTHESIS)
                .toString();
    }

    public static String convert(SharedData sharedData) {
        return new StringBuilder()
                .append(getHeader(sharedData))
                .append("shared_Data").append(EMPTY_SPACE).append(OPEN_PARANTHESIS).append(NEW_LINE)
                .append(TAB).append("Model").append(EMPTY_SPACE).append(QUOTATION).append("shared_data").append(QUOTATION)
                .append(EMPTY_SPACE).append(OPEN_PARANTHESIS).append(NEW_LINE)
                .append(TAB).append(TAB).append("elements").append(EMPTY_SPACE).append(SEMI_COLON).append(NEW_LINE)
                .append(String.join(JOINT, sharedData.getRepositorySet().stream().map(Repository::toString).collect(Collectors.toSet())))
                .append(JOINT)
                .append(String.join(JOINT, sharedData.getDataAccessorSet().stream().map(DataAccessor::toString).collect(Collectors.toSet())))
                .append(NEW_LINE).append(TAB).append(TAB).append("attachments").append(EMPTY_SPACE).append(SEMI_COLON).append(NEW_LINE)
                .append(String.join(JOINT, sharedData.getDataOperationSet().stream().map(DataOperation::toString).collect(Collectors.toSet())))
                .append(NEW_LINE).append(TAB).append(CLOSE_PARANTHESIS).append(NEW_LINE).append(CLOSE_PARANTHESIS)
                .toString();
    }


    public static String convert(Layered layered) {
        return new StringBuilder()
                .append(getHeader(layered)).append("layered").append(EMPTY_SPACE).append(OPEN_PARANTHESIS).append(NEW_LINE)
                .append(TAB).append("Model").append(EMPTY_SPACE).append(QUOTATION).append(layered.getName()).append(QUOTATION)
                .append(EMPTY_SPACE).append(OPEN_PARANTHESIS).append(NEW_LINE)
                .append(TAB).append(TAB).append("layers").append(EMPTY_SPACE).append(SEMI_COLON).append(NEW_LINE)
                .append(String.join(JOINT, layered.getLayers().stream().map(Layer::toString).collect(Collectors.toSet())))
                .append(NEW_LINE).append(TAB).append(TAB).append("relations").append(EMPTY_SPACE).append(SEMI_COLON).append(NEW_LINE)
                .append(String.join(JOINT, layered.getRelations().stream().map(Relation::toString).collect(Collectors.toSet())))
                .append(NEW_LINE).append(TAB).append(CLOSE_PARANTHESIS).append(NEW_LINE).append(CLOSE_PARANTHESIS)
                .toString();
    }

    public static String convert(DataRead dataOperation) {
        return new StringBuilder()
                .append(TAB).append(TAB).append(TAB).append("DataRead")
                .append(EMPTY_SPACE).append(OPEN_PARANTHESIS).append(NEW_LINE)
                .append(TAB).append(TAB).append(TAB).append(TAB).append("dataRead")
                .append(EMPTY_SPACE).append(SEMI_COLON).append(EMPTY_SPACE).append(QUOTATION).append(dataOperation.getMethodName()).append(QUOTATION).append(NEW_LINE)
                .append(TAB).append(TAB).append(TAB).append(TAB).append("da")
                .append(EMPTY_SPACE).append(SEMI_COLON).append(EMPTY_SPACE).append("DataAccessor").append(EMPTY_SPACE)
                .append(QUOTATION).append(dataOperation.getDataAccessor().getId()).append(QUOTATION).append(NEW_LINE)
                .append(TAB).append(TAB).append(TAB).append(TAB).append("rp").append(EMPTY_SPACE).append(SEMI_COLON)
                .append(EMPTY_SPACE).append("Repository").append(EMPTY_SPACE).append(QUOTATION).append(dataOperation.getRepository().getId()).append(QUOTATION)
                .append(NEW_LINE).append(TAB).append(TAB).append(TAB).append(CLOSE_PARANTHESIS)
                .toString();
    }

    public static String convert(DataWrite dataOperation) {
        return new StringBuilder()
                .append(TAB).append(TAB).append(TAB).append("DataWrite")
                .append(EMPTY_SPACE).append(OPEN_PARANTHESIS).append(NEW_LINE)
                .append(TAB).append(TAB).append(TAB).append(TAB).append("dataWrite")
                .append(EMPTY_SPACE).append(SEMI_COLON).append(EMPTY_SPACE).append(QUOTATION).append(dataOperation.getMethodName()).append(QUOTATION).append(NEW_LINE)
                .append(TAB).append(TAB).append(TAB).append(TAB).append("da")
                .append(EMPTY_SPACE).append(SEMI_COLON).append(EMPTY_SPACE).append("DataAccessor").append(EMPTY_SPACE)
                .append(QUOTATION).append(dataOperation.getDataAccessor().getId()).append(QUOTATION).append(NEW_LINE)
                .append(TAB).append(TAB).append(TAB).append(TAB).append("rp").append(EMPTY_SPACE).append(SEMI_COLON)
                .append(EMPTY_SPACE).append("Repository").append(EMPTY_SPACE).append(QUOTATION).append(dataOperation.getRepository().getId()).append(QUOTATION)
                .append(NEW_LINE).append(TAB).append(TAB).append(TAB).append(CLOSE_PARANTHESIS)
                .toString();
    }

    public static String convert(Layer layer) {
        return new StringBuilder().append(TAB).append(TAB).append(TAB)
                .append("Layer").append(EMPTY_SPACE).append(QUOTATION).append(layer.getId()).append(QUOTATION)
                .append(EMPTY_SPACE).append(OPEN_PARANTHESIS).append(NEW_LINE)
                .append(TAB).append(TAB).append(TAB).append(TAB).append("name").append(EMPTY_SPACE).append(SEMI_COLON)
                .append(EMPTY_SPACE).append(QUOTATION).append(layer.getName()).append(QUOTATION).append(NEW_LINE)
                .append(TAB).append(TAB).append(TAB).append(CLOSE_PARANTHESIS)
                .toString();
    }

    public static String convert(Relation relation) {
        return new StringBuilder().append(TAB).append(TAB).append(TAB)
                .append("Allowed_To_Use_Below").append(EMPTY_SPACE).append(OPEN_PARANTHESIS).append(NEW_LINE)
                .append(TAB).append(TAB).append(TAB).append(TAB).append("sourceLayer").append(EMPTY_SPACE).append(SEMI_COLON)
                .append(EMPTY_SPACE).append("Layer").append(EMPTY_SPACE).append(QUOTATION).append(relation.getSourceLayer().getId()).append(QUOTATION).append(NEW_LINE)
                .append(TAB).append(TAB).append(TAB).append(TAB).append("targetLayer").append(EMPTY_SPACE).append(SEMI_COLON)
                .append(EMPTY_SPACE).append("Layer").append(EMPTY_SPACE).append(QUOTATION).append(relation.getTargetLayer().getId()).append(QUOTATION).append(NEW_LINE)
                .append(TAB).append(TAB).append(TAB).append(CLOSE_PARANTHESIS)
                .toString();
    }
}
