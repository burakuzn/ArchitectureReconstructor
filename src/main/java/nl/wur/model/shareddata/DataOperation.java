package nl.wur.model.shareddata;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public abstract class DataOperation {
    private DataAccessor dataAccessor;
    private Repository repository;
    private String methodName;
}
