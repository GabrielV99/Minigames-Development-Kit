package ro.Gabriel.Data;

import ro.Gabriel.Data.ServiceImpl.DataPack.DataPack;

public abstract class EntityDataService {

    public abstract DataPack getDataPack(String id);

    public abstract <T> T getData(String id, String label, Class<T> type);

    public abstract <T> T getData(String id, String label, Class<T> type, T defaultValue);

    public abstract void setData(String id, String label, Object value);
}