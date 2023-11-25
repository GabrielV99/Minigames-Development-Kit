package ro.Gabriel.Database;

import ro.Gabriel.Data.EntityDataService;
import ro.Gabriel.Data.ServiceImpl.EntityDataServiceFactory;
import ro.Gabriel.Data.ServiceImpl.MySqlDataService;

public enum DatabaseType {
    MySql(MySqlDataService::new),
    SqLite(MySqlDataService::new);

    private final EntityDataServiceFactory entityDataServiceFactory;

    DatabaseType(EntityDataServiceFactory entityDataServiceFactory) {
        this.entityDataServiceFactory = entityDataServiceFactory;
    }

    public EntityDataService getEntityDataService(String source) {
        return this.entityDataServiceFactory.create(source);
    }
}