package nl.wur.extractor;

import nl.wur.model.decomposition.Decomposition;
import nl.wur.model.decomposition.DecompositionModule;
import org.reflections.Reflections;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class DecompositionViewpointExtractor implements Extractor {

    private static DecompositionViewpointExtractor decompositionViewpointExtractor;

    private DecompositionViewpointExtractor() {

    }

    @Override
    public Decomposition extract(Reflections reflections) {
        Set<DecompositionModule> modules = new HashSet<>();
        Set<Class<?>> classes = reflections.getSubTypesOf(Object.class).stream().collect(Collectors.toSet());
        Set<String> packages = new HashSet<>();

        for (Class<?> aClass : classes) {
            StringTokenizer packageNameTokenizer = new StringTokenizer(aClass.getPackage().getName(), ".");
            packages.add(aClass.getPackage().getName());
            String name = packageNameTokenizer.nextToken();
            packages.add(name);
            while (packageNameTokenizer.hasMoreTokens()) {
                name = String.join(".", asList(name, packageNameTokenizer.nextToken()));
                packages.add(name);
            }
        }
        for (String packageName : packages) {
            Set<String> subelements = packages.stream().filter(t -> t.startsWith(packageName)
                    && !t.equals(packageName)
                    && packageName.split("\\.").length + 1 == t.split("\\.").length).collect(Collectors.toSet());
            modules.add(new DecompositionModule(packageName, subelements));
        }
        return new Decomposition(modules);
    }

    public static DecompositionViewpointExtractor getDecompositionViewpointExtractor(){
        if(decompositionViewpointExtractor == null) {
            decompositionViewpointExtractor = new DecompositionViewpointExtractor();
        }
        return decompositionViewpointExtractor;
    }
}
