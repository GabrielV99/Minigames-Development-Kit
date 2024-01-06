package ro.Gabriel.Class.Validators;

import ro.Gabriel.Class.ClassUtils;
import ro.Gabriel.Language.LanguageCategory;
import ro.Gabriel.Class.ClassValidator;
import ro.Gabriel.Misc.ReflectionUtils;

import java.util.function.Predicate;

public class LanguageCategoryValidator  implements ClassValidator {
    @Override
    public Predicate<? super Class<?>> validate(Object... parameters) {
        return clazz -> clazz.isEnum() && ClassUtils.extendsClass(clazz, LanguageCategory.class);
    }
}