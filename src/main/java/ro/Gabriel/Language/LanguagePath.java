package ro.Gabriel.Language;

import ro.Gabriel.Language.Categories.CommandMessages;
import ro.Gabriel.Placeholder.Placeholder;
import ro.Gabriel.Class.ClassUtils.EnumId;
import ro.Gabriel.Class.ClassUtils;

public interface LanguagePath extends EnumId<String> {
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