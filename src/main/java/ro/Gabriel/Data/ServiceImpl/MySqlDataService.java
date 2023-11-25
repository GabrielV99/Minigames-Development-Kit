package ro.Gabriel.Data.ServiceImpl;

import ro.Gabriel.BuildBattle.Main;
import ro.Gabriel.Data.EntityDataService;
import com.google.gson.internal.Primitives;
import ro.Gabriel.Data.ServiceImpl.DataPack.DataPack;
import ro.Gabriel.Data.ServiceImpl.DataPack.ResultSetDataPack;
import ro.Gabriel.Storage.DefaultValues.DataDefaultValues;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.Map;

public class MySqlDataService extends EntityDataService {

    private final String table;

    public MySqlDataService(String table) {
        this.table = table;
    }

    @Override
    public DataPack getDataPack(String id) {
        try {
            if(!Main.isDatabaseConnected()) {
                return null;
            }
            return new ResultSetDataPack(Main.getDatabase().select("*", this.table, ("WHERE id = '" + id + "'")));
        } catch (Exception ignored) { }
        return null;
    }

    @Override
    public <T> T getData(String id, String label, Class<T> type) {
        try {
            if(!Main.isDatabaseConnected()) {
                return DataDefaultValues.get(type);
            }

            ResultSet resultSet = Main.getDatabase().select(label, table, ("WHERE id = '" + id + "'"));

            if(resultSet.next()) {
                return Primitives.wrap(type).cast(resultSet.getObject(label));
            }
            return null;
        } catch (Exception ignored) {}
        return null;
    }

    @Override
    public <T> T getData(String id, String label, Class<T> type, T defaultValue) {
        if(!Main.isDatabaseConnected()) {
            return defaultValue;
        }

        T value = this.getData(id, label, type);

        if(value == null && defaultValue != null) {
            this.setData(id, label, defaultValue);
            return defaultValue;
        }
        return value;
    }

    @Override
    public void setData(String id, String label, Object value) {
        Main.runTaskAsynchronously(() -> {
            try {
                if(!Main.isDatabaseConnected()) {
                    return;
                }
                PreparedStatement preparedStatement = Main.getDatabase().getConnection().prepareStatement("UPDATE " + table + " SET " + label + " = '" + value + "' WHERE id = '" + id + "';");
                preparedStatement.executeUpdate();
                preparedStatement.closeOnCompletion();
            } catch(Exception ignored) { }
        });
    }

    private void test(Map<String, Class<?>> tableTemplate) {

    }
}