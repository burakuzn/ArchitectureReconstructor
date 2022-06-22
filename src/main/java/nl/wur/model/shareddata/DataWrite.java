package nl.wur.model.shareddata;

import nl.wur.hutn.HutnConverter;

public class DataWrite extends DataOperation {
    public DataWrite(DataAccessor dataAccessor, Repository repository, String methodName) {
        super(dataAccessor, repository, methodName);
    }

    @Override
    public String toString() {
        return HutnConverter.convert(this);
    }
}
