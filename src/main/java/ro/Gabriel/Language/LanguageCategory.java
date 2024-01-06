package ro.Gabriel.Language;

import ro.Gabriel.Class.ClassUtils;
import ro.Gabriel.Placeholder.Placeholder;

public interface LanguageCategory extends ClassUtils.EnumId<String> {
    String getPath();
    Placeholder<?> getPlaceholder();

    @Override
    default String getId() {
        return getPath();
    }

    static String getLanguageId(Class<?> clazz) {
        return ClassUtils.getObjectClassId(clazz);
    }
}