package nl.wur.model.shareddata;

import nl.wur.hutn.HutnConverter;

public class DataRead extends DataOperation {

    public DataRead(DataAccessor dataAccessor, Repository repository, String methodName) {
        super(dataAccessor, repository, methodName);
    }

    @Override
    public String toString() {
        return HutnConverter.convert(this);
    }
}
