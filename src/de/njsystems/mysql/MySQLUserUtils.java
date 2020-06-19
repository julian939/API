package de.njsystems.mysql;

import de.njsystems.API;

public class MySQLUserUtils {

    private MySQLDataUtils mySQLDataUtils = API.getAPI.getMySQLDataUtils();

    public MySQL mySQL = new MySQL(mySQLDataUtils.getMySQLValue("general", "host"),
            mySQLDataUtils.getMySQLValue("general", "port"),
            mySQLDataUtils.getMySQLValue("user", "database"),
            mySQLDataUtils.getMySQLValue("user", "user"),
            mySQLDataUtils.getMySQLValue("user", "password"));

    public MySQLUserUtils() {
        mySQL.connect();
        createTables();
    }

    private void createTables() {
        mySQL.update("CREATE TABLE IF NOT EXISTS users(id INT AUTO_INCREMENT, name VARCHAR(100), uuid VARCHAR(100)," +
                " lang VARCHAR(100), firstregister LONG, teamspeak_uid VARCHAR(100)," +
                " website_uid VARCHAR(100), playtime BIGINT, PRIMARY KEY (id))");
    }

    public void close() {
        mySQL.close();
    }

}
