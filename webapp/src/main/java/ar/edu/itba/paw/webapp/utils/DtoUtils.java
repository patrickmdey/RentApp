package ar.edu.itba.paw.webapp.utils;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class DtoUtils {

    private DtoUtils(){}

    public static String getFromMap(Map<String, List<FormDataBodyPart>> map, String key) {
        return getFromMap(map, key, DtoUtils::stringGetter);
    }

    public static <T> T getFromMap(Map<String, List<FormDataBodyPart>> map, String key,
                                    Function<List<FormDataBodyPart>, T> getter) {
        List<FormDataBodyPart> list = map.getOrDefault(key, Collections.emptyList());
        if (list.isEmpty()) {
            return null;
        }

        try {
            return getter.apply(list);
        } catch (Exception e) {
            return null;
        }
    }

    private static String stringGetter(List<FormDataBodyPart> list) {
        return list.get(0).getValue();
    }
}