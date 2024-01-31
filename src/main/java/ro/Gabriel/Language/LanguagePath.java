package ro.Gabriel.Language;

import ro.Gabriel.Placeholder.Placeholder;
import ro.Gabriel.Class.ClassUtils;

public interface LanguagePath extends LanguageCategory {
    String getPath();
    Placeholder<?> getPlaceholder();

    @Override
    default String getId() {
        return this.getPath();
    }

    @Override
    default String getStoragePath() {
        return getLanguageId(this.getClass());
    }

    static String getLanguageId(Class<?> clazz) {
        return ClassUtils.getObjectClassId(clazz);
    }
}