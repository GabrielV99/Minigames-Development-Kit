package ro.Gabriel.Language;

import ro.Gabriel.Class.ClassUtils;

public interface LanguageCategory extends ClassUtils.EnumId<String> {
    String getStoragePath();

    @Override
    default String getId() {
        return this.getStoragePath();
    }
}