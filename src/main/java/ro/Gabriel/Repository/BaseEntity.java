package ro.Gabriel.Repository;

public abstract class BaseEntity<IdType> {
    private final IdType id;

    public BaseEntity(IdType id) {
        this.id = id;
    }

    public IdType getId() {
        return this.id;
    }
}