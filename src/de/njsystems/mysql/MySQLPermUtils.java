package de.njsystems.mysql;

import de.njsystems.API;

public class MySQLPermUtils {

    private MySQLDataUtils mySQLDataUtils = API.getAPI.getMySQLDataUtils();

    public MySQL mySQL = new MySQL(mySQLDataUtils.getMySQLValue("general", "host"),
            mySQLDataUtils.getMySQLValue("general", "port"),
            mySQLDataUtils.getMySQLValue("perm", "database"),
            mySQLDataUtils.getMySQLValue("perm", "user"),
            mySQLDataUtils.getMySQLValue("perm", "password"));

    public MySQLPermUtils() {
        mySQL.connect();
        createTables();
    }

    private void createTables() {
        mySQL.update("CREATE TABLE IF NOT EXISTS user_permissions(user_id INT, permission VARCHAR(100), server VARCHAR(100), end BIGINT)");
        mySQL.update("CREATE TABLE IF NOT EXISTS user_meta(user_id INT, meta VARCHAR(100), value VARCHAR(100))");
    }

    public void close() {
        mySQL.close();
    }

}
