package nl.wur.extractor;

import nl.wur.model.uses.Uses;
import nl.wur.model.uses.UsesModule;
import nl.wur.model.uses.UsesRelation;
import org.reflections.Reflections;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UsesViewpointExtractor implements Extractor {

    private static UsesViewpointExtractor usesViewpointExtractor;

    private UsesViewpointExtractor() {
    }

    @Override
    public Uses extract(Reflections reflections) {
        Set<UsesModule> moduleSet = new HashSet<>();
        Set<UsesRelation> relationSet = new HashSet<>();
        Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);

        for (Class<?> clazz : classes) {
            UsesModule sourceModule = new UsesModule(clazz);
            moduleSet.add(sourceModule);
            for (Field field : getDeclaredNonPrimitiveFields(clazz)) {
                boolean added = addRelationAccordingToType(moduleSet, relationSet, sourceModule, field.getGenericType());
                if (!added) {
                    if (field.getType().isArray()) {
                        addRelationWithClass(moduleSet, relationSet, sourceModule, field.getType().getComponentType());
                    } else {
                        addRelationWithClass(moduleSet, relationSet, sourceModule, field.getType());
                    }
                }
            }
            Set<Method> methods = Arrays.stream(clazz.getMethods()).filter(method -> !Arrays.asList(Object.class.getMethods()).contains(method)).collect(Collectors.toSet());
            for (Method method : methods) {
                for (Parameter parameter : getNonPrimitiveParameters(method)) {
                    boolean added = addRelationAccordingToType(moduleSet, relationSet, sourceModule, parameter.getParameterizedType());
                    if (!added) {
                        if (parameter.getType().isArray()) {
                            addRelationWithClass(moduleSet, relationSet, sourceModule, parameter.getType().getComponentType());
                        } else {
                            addRelationWithClass(moduleSet, relationSet, sourceModule, parameter.getType());
                        }
                    }
                }
                boolean added = addRelationAccordingToType(moduleSet, relationSet, sourceModule, method.getGenericReturnType());
                if (!added) {
                    if (!method.getReturnType().isPrimitive()) {
                        if (method.getReturnType().isArray()) {
                            addRelationWithClass(moduleSet, relationSet, sourceModule, method.getReturnType().getComponentType());
                        } else {
                            addRelationWithClass(moduleSet, relationSet, sourceModule, method.getReturnType());
                        }
                    }
                }
            }
        }
        return new Uses(moduleSet, relationSet);
    }

    private Collection<Parameter> getNonPrimitiveParameters(Method method) {
        return Arrays.stream(method.getParameters()).filter(parameter -> !parameter.getType().isPrimitive()).collect(Collectors.toList());
    }

    private Collection<Field> getDeclaredNonPrimitiveFields(Class clazz) {
        return Arrays.stream(clazz.getDeclaredFields()).filter(field -> !field.getType().isPrimitive()).collect(Collectors.toList());
    }

    private boolean addRelationAccordingToType(Set<UsesModule> moduleSet, Set<UsesRelation> relationSet, UsesModule sourceModule, Type type) {
        if (type instanceof ParameterizedType) {
            for (Type actualTypeArgument : ((ParameterizedType) type).getActualTypeArguments()) {
                addRelationWithType(moduleSet, relationSet, sourceModule, actualTypeArgument);
            }
            return true;
        }
        if (type instanceof GenericArrayType) {
            addRelationWithType(moduleSet, relationSet, sourceModule, ((GenericArrayType) type).getGenericComponentType());
            return true;
        }
        return false;
    }

    private void addRelationWithType(Set<UsesModule> moduleSet, Set<UsesRelation> relationSet, UsesModule sourceModule, Type type) {
        if (type.getTypeName().equals("T")) {
            return;
        }
        UsesModule targetModule = new UsesModule(type);
        moduleSet.add(targetModule);
        relationSet.add(new UsesRelation(targetModule, sourceModule));
    }

    private void addRelationWithClass(Set<UsesModule> moduleSet, Set<UsesRelation> relationSet, UsesModule sourceModule, Class clazz) {
        if (clazz.getPackage().getName().startsWith("java.")) return;
        UsesModule targetModule = new UsesModule(clazz);
        moduleSet.add(targetModule);
        relationSet.add(new UsesRelation(targetModule, sourceModule));
    }

    public static UsesViewpointExtractor getUsesViewpointExtractor() {
        if (usesViewpointExtractor == null) {
            usesViewpointExtractor = new UsesViewpointExtractor();
        }
        return usesViewpointExtractor;
    }
}
