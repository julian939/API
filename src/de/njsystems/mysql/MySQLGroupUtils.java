package de.njsystems.mysql;

import de.njsystems.API;

public class MySQLGroupUtils {

    private MySQLDataUtils mySQLDataUtils = API.getAPI.getMySQLDataUtils();

    public MySQL mySQL = new MySQL(mySQLDataUtils.getMySQLValue("general", "host"),
            mySQLDataUtils.getMySQLValue("general", "port"),
            mySQLDataUtils.getMySQLValue("group", "database"),
            mySQLDataUtils.getMySQLValue("group", "user"),
            mySQLDataUtils.getMySQLValue("group", "password"));

    public MySQLGroupUtils() {
        mySQL.connect();
        createTables();
    }

    private void createTables() {
        mySQL.update("CREATE TABLE IF NOT EXISTS groups(id INT AUTO_INCREMENT, name VARCHAR(100), weight INT, PRIMARY KEY (id))");
        mySQL.update("CREATE TABLE IF NOT EXISTS group_permissions(group_id INT, permission VARCHAR(100), server VARCHAR(100), end BIGINT)");
        mySQL.update("CREATE TABLE IF NOT EXISTS group_meta(group_id INT, meta VARCHAR(100), value VARCHAR(100))");
        mySQL.update("CREATE TABLE IF NOT EXISTS user_group(user_id INT, group_id INT)");
    }

    public void close() {
        mySQL.close();
    }

}
