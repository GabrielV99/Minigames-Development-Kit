package ro.Gabriel.Class.Validators;

import ro.Gabriel.Class.ClassValidator;
import ro.Gabriel.Data.test.TableTemplate;
import ro.Gabriel.Misc.Utils;
import ro.Gabriel.Repository.BaseEntity;

import java.util.function.Predicate;

public class DatabaseTableTemplateValidator implements ClassValidator {
    @Override
    public Predicate<? super Class<?>> validate(Object... parameters) {
        return clazz -> Utils.isAnnotated(clazz, TableTemplate.class) && Utils.isExtends(clazz, BaseEntity.class);
    }
}
