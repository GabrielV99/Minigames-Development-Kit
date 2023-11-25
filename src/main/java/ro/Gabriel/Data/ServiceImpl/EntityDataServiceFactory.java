package ro.Gabriel.Data.ServiceImpl;

import ro.Gabriel.Data.EntityDataService;

public interface EntityDataServiceFactory {
    EntityDataService create(String source);
}
