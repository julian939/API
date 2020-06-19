package de.njsystems.mysql;

import de.njsystems.API;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class MySQLDataUtils {

    public MySQL mySQL = new MySQL(API.getAPI.configcfg.getString("host"),
            API.getAPI.configcfg.getString("port"),
            API.getAPI.configcfg.getString("database"),
            API.getAPI.configcfg.getString("user"),
            API.getAPI.configcfg.getString("password"));

    public MySQLDataUtils() {
        mySQL.connect();
        createTables();
    }

    private void createTables() {
        mySQL.update("CREATE TABLE IF NOT EXISTS strings(plugin VARCHAR(100), type VARCHAR(100), value VARCHAR(100))");
        mySQL.update("CREATE TABLE IF NOT EXISTS mysqldatas(plugin VARCHAR(100), type VARCHAR(100), value VARCHAR(100))");
    }

    public void close() {
        mySQL.close();
    }

    public HashMap<String, String> getMySQLValues(String plugin) {
        HashMap<String, String> values = new HashMap<>();
        ResultSet resultSet = mySQL.getResult("SELECT type, value FROM mysqldatas WHERE plugin='" + plugin + "'");
        try {
            while (resultSet.next()) {
                values.put(resultSet.getString("type"), resultSet.getString("value"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return values;
    }

    public String getMySQLValue(String plugin, String type) {
        return getMySQLValues(plugin).get(type);
    }

    public HashMap<String, String> getStringValues(String plugin) {
        HashMap<String, String> values = new HashMap<>();
        ResultSet resultSet = mySQL.getResult("SELECT type, value FROM strings WHERE plugin='" + plugin + "'");
        try {
            while (resultSet.next()) {
                values.put(resultSet.getString("type"), resultSet.getString("value"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return values;
    }

    public String getStringValue(String plugin, String type) {
        return getStringValues(plugin).get(type);
    }

}

