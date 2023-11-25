package ro.Gabriel.DatabaseN.Structures;

import ro.Gabriel.BuildBattle.Main;
import ro.Gabriel.Class.ClassManager;
import ro.Gabriel.DatabaseN.Data.CustomDataPack;
import ro.Gabriel.DatabaseN.Data.DataPack;
import ro.Gabriel.Misc.ReflectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.stream.Stream;

public abstract class DatabaseStructure {

    private String id;
    private DataPack defaultDataPack;
    private DatabaseField<?>[] fields;

    public DatabaseStructure() {
        Class<?> clazz = getClass();

        DatabaseStructureId databaseStructureId = clazz.getAnnotation(DatabaseStructureId.class);
        this.id = databaseStructureId != null ? databaseStructureId.id() : clazz.getSimpleName();
        Supplier<Stream<DatabaseField<?>>> stream = () ->  Arrays.stream(this.getClass().getFields())
                .filter(field ->
                                java.lang.reflect.Modifier.isStatic(field.getModifiers()) &&
                                        ReflectionUtils.getFieldType(field) == DatabaseField.class
                            //&& !field.getType().isAnnotationPresent(DatabaseStructurePrimaryKey.class)
                    ).map(field -> {
                        try {
                            return (DatabaseField<?>) ReflectionUtils.getValue(null, false, field.getDeclaringClass(), field.getName());
                        } catch (IllegalAccessException | NoSuchFieldException e) {
                            e.printStackTrace();
                            return null;
                        }
                    });

            this.fields = stream.get().toArray(DatabaseField[]::new);

            this.defaultDataPack = new CustomDataPack(
                    stream.get().filter(field -> !field.getType().isAnnotationPresent(DatabaseStructurePrimaryKey.class))
                            .collect(Collectors.toMap(DatabaseField::getId, DatabaseField::getDefaultValue))
            );
    }

    public String getId() {
        return this.id;
    }

    public DataPack getDefaultDataPack() {
        return this.defaultDataPack;
    }

    public DatabaseField<?>[] getFields() {
        return this.fields;
    }
}

/*    public DatabaseStructure() {
        Class<?> clazz = getClass();

        DatabaseStructureId databaseStructureId = clazz.getAnnotation(DatabaseStructureId.class);
        if(databaseStructureId != null) {
            this.id = databaseStructureId.id();

            Stream<DatabaseField<?>> stream = Arrays.stream(this.getClass().getFields())
                    .filter(field ->
                            java.lang.reflect.Modifier.isStatic(field.getModifiers()) &&
                                    ReflectionUtils.getFieldType(field) == DatabaseField.class &&
                                    !field.getType().isAnnotationPresent(DatabaseStructurePrimaryKey.class)
                    ).map(field -> {
                        try {
                            return (DatabaseField<?>) ReflectionUtils.getValue(null, false, field.getName());
                        } catch (IllegalAccessException | NoSuchFieldException e) {
                            e.printStackTrace();
                            return null;
                        }
                    });

            this.fields = stream.toArray(DatabaseField[]::new);

            this.defaultDataPack = new CustomDataPack(
                    stream.collect(Collectors.toMap(
                    databaseField -> databaseField != null ? databaseField.getId() : null,
                    databaseField1 -> databaseField1 != null ? databaseField1.getDefaultValue() : null)
                    )
            );


        }
    }*/