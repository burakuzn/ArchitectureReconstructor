package nl.wur.extractor;

import nl.wur.model.generalization.Generalization;
import nl.wur.model.generalization.GeneralizationModule;
import nl.wur.model.generalization.GeneralizationRelation;
import org.reflections.Reflections;

import java.util.HashSet;
import java.util.Set;

import static nl.wur.model.generalization.GeneralizationRelation.RelationType.IMPLEMENT;
import static nl.wur.model.generalization.GeneralizationRelation.RelationType.INHERIT;

/*
 * For each class
 *   add the class to modules if not present
 *   get superclass
 *      if the superclass is not Object.class
 *          add it to modules
 *          add inheritance relation to relations
 *   get all implemented interfaces
 *      if the implemented interface not present add it to modules
 *      if the class object we are working on interface type
 *          add it to relations as inheritance
 *      if the class object we are working on class type
 *          add it to relations as implementation
 * */

public class GeneralizationViewpointExtractor implements Extractor {

    private static GeneralizationViewpointExtractor generalizationViewpointExtractor;

    private GeneralizationViewpointExtractor() {
    }

    public Generalization extract(Reflections reflections) {
        Set<GeneralizationModule> modules = new HashSet<GeneralizationModule>();
        Set<GeneralizationRelation> generalizationRelations = new HashSet<GeneralizationRelation>();
        Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);
        for (Class<?> clazz : classes) {
            GeneralizationModule module = findOrAddModule(clazz, modules);
            GeneralizationModule superModule = findOrAddModule(clazz.getSuperclass(), modules);
            addRelation(module, superModule, INHERIT, generalizationRelations);
            Class<?>[] interfaces = clazz.getInterfaces();
            for (Class<?> anInterface : interfaces) {
                superModule = findOrAddModule(anInterface, modules);
                GeneralizationRelation.RelationType relationType = clazz.isInterface() ? INHERIT : IMPLEMENT;
                addRelation(module, superModule, relationType, generalizationRelations);
            }
        }
        return new Generalization(modules, generalizationRelations);
    }

    private void addRelation(GeneralizationModule module, GeneralizationModule superModule, GeneralizationRelation.RelationType type, Set<GeneralizationRelation> generalizationRelations) {
        if (module != null && superModule != null) {
            generalizationRelations.add(new GeneralizationRelation(superModule, module, type));
        }
    }

    private GeneralizationModule findOrAddModule(Class clazz, Set<GeneralizationModule> modules) {
        if (clazz != null && !clazz.equals(Object.class)) {
            GeneralizationModule module = new GeneralizationModule(clazz);
            boolean isAdded = modules.add(module);
            return isAdded ? module : modules.stream().filter(module::equals).findAny().get();
        }
        return null;
    }

    public static GeneralizationViewpointExtractor getGeneralizationViewpointExtractor() {
        if (generalizationViewpointExtractor == null) {
            generalizationViewpointExtractor = new GeneralizationViewpointExtractor();
        }
        return generalizationViewpointExtractor;
    }
}
