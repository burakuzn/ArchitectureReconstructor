package nl.wur;

import nl.wur.extractor.*;
import nl.wur.hutn.HutnWriter;
import nl.wur.model.decomposition.Decomposition;
import nl.wur.model.generalization.Generalization;
import nl.wur.model.layered.Layered;
import nl.wur.model.shareddata.SharedData;
import nl.wur.model.uses.Uses;
import nl.wur.util.ReflectionPopulator;
import org.reflections.Reflections;

public class ArchitectureReconstructor {

    private GeneralizationViewpointExtractor generalizationViewpointExtractor = GeneralizationViewpointExtractor.getGeneralizationViewpointExtractor();
    private DecompositionViewpointExtractor decompositionViewpointExtractor = DecompositionViewpointExtractor.getDecompositionViewpointExtractor();
    private UsesViewpointExtractor usesViewpointExtractor = UsesViewpointExtractor.getUsesViewpointExtractor();
    private SharedDataViewpointExtractor sharedDataViewpointExtractor = SharedDataViewpointExtractor.getSharedDataViewpointExtractor();
    private LayeredViewpointExtractor layeredViewpointExtractor = LayeredViewpointExtractor.getLayeredViewpointExtractor();
    private HutnWriter hutnWriter = HutnWriter.getHutnWriter();

    public void reconstruct(String path) {
        ReflectionPopulator reflectionPopulator = ReflectionPopulator.getReflectionPopulator(path);
        Reflections reflections = reflectionPopulator.getReflections();
        Generalization generalizationView = generalizationViewpointExtractor.extract(reflections);
        Decomposition decompositionView = decompositionViewpointExtractor.extract(reflections);
        Uses usesView = usesViewpointExtractor.extract(reflections);
        SharedData sharedDataView = sharedDataViewpointExtractor.extract(reflections);
        Layered layeredView = layeredViewpointExtractor.extract(reflections);
        hutnWriter.write(generalizationView, decompositionView, usesView, sharedDataView, layeredView);
    }
}
