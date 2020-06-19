package de.njsystems.mysql;

import de.njsystems.API;

public class MySQLStatsUtils {

    private MySQLDataUtils mySQLDataUtils = API.getAPI.getMySQLDataUtils();

    public MySQL mySQL = new MySQL(mySQLDataUtils.getMySQLValue("general", "host"),
            mySQLDataUtils.getMySQLValue("general", "port"),
            mySQLDataUtils.getMySQLValue("stats", "database"),
            mySQLDataUtils.getMySQLValue("stats", "user"),
            mySQLDataUtils.getMySQLValue("stats", "password"));

    public MySQLStatsUtils() {
        mySQL.connect();
        createTables();
    }

    private void createTables() {
        mySQL.update("CREATE TABLE IF NOT EXISTS alltime(user_id INT, points INT, kills INT, deaths INT)");
        mySQL.update("CREATE TABLE IF NOT EXISTS monthly(user_id INT, points INT, kills INT, deaths INT)");
    }

    public void close() {
        mySQL.close();
    }

}
