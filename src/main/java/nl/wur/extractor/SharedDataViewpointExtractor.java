package nl.wur.extractor;

import nl.wur.annotations.Read;
import nl.wur.annotations.Repo;
import nl.wur.annotations.Write;
import nl.wur.model.shareddata.*;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class SharedDataViewpointExtractor implements Extractor {

    private static SharedDataViewpointExtractor sharedDataViewpointExtractor;

    private SharedDataViewpointExtractor(){}

    @Override
    public SharedData extract(Reflections reflections) {
        Set<Repository> repositories = new HashSet<>();
        Set<DataAccessor> dataAccessors = new HashSet<>();
        Set<DataOperation> dataOperations = new HashSet<>();
        Set<Class<?>> clazzes = reflections.getTypesAnnotatedWith(Repo.class);

        for (Class<?> clazz : clazzes) {
            Repository repository = new Repository(clazz.getAnnotation(Repo.class).name());
            repositories.add(repository);
            DataAccessor dataAccessor = new DataAccessor(clazz);
            dataAccessors.add(dataAccessor);
            for (Method method : clazz.getMethods()) {
                if (method.getAnnotation(Read.class) != null) {
                    dataOperations.add(new DataRead(dataAccessor, repository, method.getName()));
                } else if (method.getAnnotation(Write.class) != null) {
                    dataOperations.add(new DataWrite(dataAccessor, repository, method.getName()));
                }
            }
        }
        return new SharedData(repositories, dataAccessors, dataOperations);
    }

    public static SharedDataViewpointExtractor getSharedDataViewpointExtractor(){
        if(sharedDataViewpointExtractor == null) {
            sharedDataViewpointExtractor = new SharedDataViewpointExtractor();
        }
        return sharedDataViewpointExtractor;
    }
}
