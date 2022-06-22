package nl.wur.extractor;

import nl.wur.model.Viewpoint;
import org.reflections.Reflections;

public interface Extractor {
    Viewpoint extract(Reflections reflections);
}
