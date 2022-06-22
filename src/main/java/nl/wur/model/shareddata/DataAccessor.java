package nl.wur.model.shareddata;

import nl.wur.hutn.HutnConverter;

public class DataAccessor extends Element {

    public DataAccessor(Class clazz){
       this.setName(clazz.getCanonicalName());
        this.setId(clazz.getCanonicalName());
    }

    @Override
    public String toString() {
        return HutnConverter.convert(this);
    }
}
