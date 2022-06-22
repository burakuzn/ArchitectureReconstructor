package nl.wur.extractor;

import lombok.Setter;
import nl.wur.extractor.helper.AcyclicGraphPathFinder;
import nl.wur.extractor.helper.UsesRelationToDirectedGraphConverter;
import nl.wur.model.layered.Layer;
import nl.wur.model.layered.Layered;
import nl.wur.model.layered.Relation;
import nl.wur.model.uses.Uses;
import nl.wur.model.uses.UsesRelation;
import org.reflections.Reflections;

import java.util.HashSet;
import java.util.Set;

@Setter
public class LayeredViewpointExtractor implements Extractor {

    private LayeredViewpointExtractor() {
    }

    private static LayeredViewpointExtractor layeredViewpointExtractor;
    private UsesViewpointExtractor usesViewpointExtractor = UsesViewpointExtractor.getUsesViewpointExtractor();
    private UsesRelationToDirectedGraphConverter converter = UsesRelationToDirectedGraphConverter.getGraphConverter();
    private AcyclicGraphPathFinder pathFinder = AcyclicGraphPathFinder.getAcyclicGraphPathFinder();

    @Override
    public Layered extract(Reflections reflections) {
        Uses uses = usesViewpointExtractor.extract(reflections);
        Set<UsesRelation> layeredUses = pathFinder.findLayeredUsesRelations(converter.convert(uses));
        Set<Layer> layers = new HashSet<>();
        Set<Relation> relations = new HashSet<>();
        for (UsesRelation relation : layeredUses) {
            Layer sourceLayer = new Layer(relation.getSource());
            Layer targetLayer = new Layer(relation.getTarget());
            layers.add(sourceLayer);
            layers.add(targetLayer);
            relations.add(new Relation(sourceLayer, targetLayer));
        }
        return new Layered(layers, relations);
    }

    public static LayeredViewpointExtractor getLayeredViewpointExtractor() {
        if (layeredViewpointExtractor == null) {
            layeredViewpointExtractor = new LayeredViewpointExtractor();
        }
        return layeredViewpointExtractor;
    }
}


