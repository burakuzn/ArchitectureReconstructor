package nl.wur.model.shareddata;

import nl.wur.hutn.HutnConverter;

public class Repository extends Element {
    public Repository(String name) {
        this.setId(name);
        this.setName(name);
    }

    @Override
    public String toString() {
        return HutnConverter.convert(this);
    }
}
