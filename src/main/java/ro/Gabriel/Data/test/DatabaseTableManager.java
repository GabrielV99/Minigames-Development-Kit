package ro.Gabriel.Data.test;

import ro.Gabriel.Class.ClassManager;
import ro.Gabriel.Class.Validators.DatabaseTableTemplateValidator;
import ro.Gabriel.Managers.Annotations.ManagerClass;
import ro.Gabriel.Misc.Utils;

import java.util.stream.Stream;

@ManagerClass(bungee = true, lobby = true, arena = true)
public class DatabaseTableManager {
    private static DatabaseTableManager INSTANCE;

    public static void load() {
        ClassManager.searchClasses(DatabaseTableTemplateValidator.class).forEach(clazz -> Stream.concat(Stream.of(clazz.getFields()), Stream.of(clazz.getDeclaredFields()))
                .filter(field -> Utils.isAnnotated(field, TableValue.class)).forEach(field -> {

        }));
    }
}
