package nl.wur.model.uses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.wur.hutn.HutnConverter;

import java.lang.reflect.Type;

@Getter
@EqualsAndHashCode
public class UsesModule {
    private String name, id;

    public UsesModule(String name, String id){
        this.name = name;
        this.id = id;
    }

    public UsesModule(Class clazz) {
        this.name = clazz.getPackage().getName();
        this.id = clazz.getPackage().getName();
    }

    public UsesModule(Type type) {
        this.name = ((Class) type).getPackage().getName();
        this.id = ((Class) type).getPackage().getName();
    }

    @Override
    public String toString() {
        return HutnConverter.convert(this);
    }
}
