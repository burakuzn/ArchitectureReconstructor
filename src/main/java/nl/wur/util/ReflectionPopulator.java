package nl.wur.util;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.util.LinkedList;
import java.util.List;

public class ReflectionPopulator {

    private static ReflectionPopulator reflectionPopulator;
    private Reflections reflections;

    private ReflectionPopulator(String path) {
        reflections = getReflections(path);
    }

    public static ReflectionPopulator getReflectionPopulator(String path) {
        if (reflectionPopulator == null) {
            reflectionPopulator = new ReflectionPopulator(path);
        }
        return reflectionPopulator;
    }


    private Reflections getReflections(String path) {
        List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
        classLoadersList.add(ClasspathHelper.contextClassLoader());
        classLoadersList.add(ClasspathHelper.staticClassLoader());

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false /* don't exclude Object.class */), new ResourcesScanner(), new TypeAnnotationsScanner())
                .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
                .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(path))));
        return reflections;
    }

    public Reflections getReflections() {
        return reflections;
    }
}
