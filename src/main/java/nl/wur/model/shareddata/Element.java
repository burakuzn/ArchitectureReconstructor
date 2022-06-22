package nl.wur.model.shareddata;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
abstract class Element {
    private String name;
    private String id;
}
