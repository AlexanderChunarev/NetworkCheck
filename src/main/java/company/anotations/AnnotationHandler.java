package company.anotations;


import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AnnotationHandler {

    public List<String> handle(Object object) {
        Field[] fields = object.getClass().getFields();

        return Arrays.stream(fields)
                .filter(it -> it.isAnnotationPresent(JSON.class))
                .map(it -> it.getAnnotation(JSON.class).name())
                .collect(Collectors.toList());
    }
}
