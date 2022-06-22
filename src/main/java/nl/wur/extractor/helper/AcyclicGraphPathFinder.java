package nl.wur.extractor.helper;

import nl.wur.model.uses.UsesModule;
import nl.wur.model.uses.UsesRelation;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.cycle.CycleDetector;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class AcyclicGraphPathFinder {

    private static AcyclicGraphPathFinder acyclicGraphPathFinder;

    private AcyclicGraphPathFinder() {
    }

    public Set<UsesRelation> findLayeredUsesRelations(DefaultDirectedGraph<UsesModule, DefaultEdge> graph) {
        List<GraphPath<UsesModule, DefaultEdge>> allPaths = getAllAcyclicPaths(graph);
        Set<UsesRelation> usesRelationSet = new HashSet<>();
        allPaths.forEach(path -> {
            List<UsesModule> vertexList = path.getVertexList();
            for (int i = 0; i < vertexList.size(); i++) {
                for (int j = i + 1; j < vertexList.size(); j++) {
                    usesRelationSet.add(new UsesRelation(vertexList.get(j), vertexList.get(i)));
                }
            }
        });
        return usesRelationSet;
    }

    List<GraphPath<UsesModule, DefaultEdge>> getAllAcyclicPaths(DefaultDirectedGraph<UsesModule, DefaultEdge> graph) {
        List<GraphPath<UsesModule, DefaultEdge>> allPaths = new AllDirectedPaths<>(graph).getAllPaths(graph.vertexSet(), graph.vertexSet(), true, 10);
        return allPaths.stream()
                .filter(path -> !path.getStartVertex().equals(path.getEndVertex()))
                .filter(this::noCycleExists)
                .collect(Collectors.toList());
    }

    private boolean noCycleExists(GraphPath<UsesModule, DefaultEdge> path) {
        CycleDetector<UsesModule, DefaultEdge> cycleDetector = new CycleDetector<>(path.getGraph());
        List<UsesModule> pathVertexList = path.getVertexList();
        AtomicBoolean noCycle = new AtomicBoolean(true);
        pathVertexList.forEach(usesModule -> {
            Set<UsesModule> vertexInCycles = cycleDetector.findCyclesContainingVertex(usesModule);
            vertexInCycles.remove(usesModule);
            if (Collections.frequency(pathVertexList, usesModule) > 1) {
                noCycle.set(false);
            }
            vertexInCycles.forEach(cycleVertex -> {
                if (pathVertexList.contains(cycleVertex)) {
                    noCycle.set(false);
                }
            });
        });
        return noCycle.get();
    }

    public static AcyclicGraphPathFinder getAcyclicGraphPathFinder() {
        if (acyclicGraphPathFinder == null) {
            acyclicGraphPathFinder = new AcyclicGraphPathFinder();
        }
        return acyclicGraphPathFinder;
    }

}
