package ro.Gabriel.Repository;

import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;

public abstract class Repository<IdType, ObjectType extends BaseEntity<IdType>> {
    private final Map<IdType, ObjectType> data;

    public Repository() {
        this.data = new HashMap<>();
    }

    protected abstract ObjectType registerEntity(IdType id);

    public void add(ObjectType object) {
        this.data.put(object.getId(), object);
    }

    @SafeVarargs
    public final void addAll(ObjectType... entities) {
        for(ObjectType entity : entities) {
            this.add(entity);
        }
    }

    public final ObjectType findById(IdType id) {
        return id != null ? this.data.get(id) : null;
    }

    public final ObjectType getById(IdType id) {
        ObjectType entity = findById(id);

        if(entity == null) {
            entity = registerEntity(id);
            if(entity != null) {
                this.data.put(id, entity);
            }
        }
        return entity;
    }

    public Map<IdType, ObjectType> getAll() {
        return this.data;
    }

    public Map<IdType, ObjectType> getAll(Predicate<Map.Entry<IdType, ObjectType>> predicate) {
        return this.data.entrySet().stream().filter(predicate).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public boolean existById(IdType id) {
        return this.getById(id) != null;
    }
}