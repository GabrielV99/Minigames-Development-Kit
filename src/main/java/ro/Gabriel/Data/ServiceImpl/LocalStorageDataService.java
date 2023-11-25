package ro.Gabriel.Data.ServiceImpl;

import ro.Gabriel.Data.ServiceImpl.DataPack.SectionDataPack;
import ro.Gabriel.Data.ServiceImpl.DataPack.DataPack;
import ro.Gabriel.Storage.DataStorage.DataStorage;
import ro.Gabriel.Data.EntityDataService;

import com.google.gson.internal.Primitives;

public class LocalStorageDataService extends EntityDataService {

    private final DataStorage file;

    public LocalStorageDataService(String path) {
        this.file = DataStorage.getStorage(path);
    }

    @Override
    public DataPack getDataPack(String id) {
        DataStorage dataPack = this.file.getSection(id);
        return dataPack != null ? new SectionDataPack(dataPack) : null;
    }

    @Override
    public <T> T getData(String id, String label, Class<T> type) {
        return Primitives.wrap(type).cast(this.file.get( (id + "." + label) ));
    }

    @Override
    public <T> T getData(String id, String label, Class<T> type, T defaultValue) {
        T value = this.getData(id, label, type);

        if(value == null && defaultValue != null) {
            this.setData(id, label, defaultValue);
            return defaultValue;
        }
        return value;
    }

    @Override
    public void setData(String id, String label, Object value) {
        this.file.set((id + "." + label), value, false);
    }
}