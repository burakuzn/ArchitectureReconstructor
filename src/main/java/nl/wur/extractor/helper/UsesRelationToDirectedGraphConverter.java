package nl.wur.extractor.helper;

import nl.wur.model.uses.Uses;
import nl.wur.model.uses.UsesModule;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class UsesRelationToDirectedGraphConverter {

    private static UsesRelationToDirectedGraphConverter converter;

    private UsesRelationToDirectedGraphConverter() {
    }

    public DefaultDirectedGraph<UsesModule, DefaultEdge> convert(Uses uses) {
        DefaultDirectedGraph<UsesModule, DefaultEdge> graph = new DefaultDirectedGraph(DefaultEdge.class);
        uses.getUsesModules().forEach(graph::addVertex);
        uses.getUsesRelations().forEach(relation -> graph.addEdge(relation.getSource(), relation.getTarget()));
        return graph;
    }

    public static UsesRelationToDirectedGraphConverter getGraphConverter() {
        if (converter == null) {
            converter = new UsesRelationToDirectedGraphConverter();
        }
        return converter;
    }
}
