package de.njsystems.mysql;

import de.njsystems.API;

public class MySQLBanUtils {

    private MySQLDataUtils mySQLDataUtils = API.getAPI.getMySQLDataUtils();

    public MySQL mySQL = new MySQL(mySQLDataUtils.getMySQLValue("general", "host"),
            mySQLDataUtils.getMySQLValue("general", "port"),
            mySQLDataUtils.getMySQLValue("ban", "database"),
            mySQLDataUtils.getMySQLValue("ban", "user"),
            mySQLDataUtils.getMySQLValue("ban", "password"));

    public MySQLBanUtils() {
        mySQL.connect();
        createTables();
    }

    private void createTables() {
        mySQL.update("CREATE TABLE IF NOT EXISTS bans(user_id INT, reason_id INT, staff_id INT, end BIGINT)");
        mySQL.update("CREATE TABLE IF NOT EXISTS cbans(user_id INT, reason VARCHAR(100), staff_id INT, " +
                "time BIGINT, end BIGINT)");
        mySQL.update("CREATE TABLE IF NOT EXISTS reasons(id INT, reason VARCHAR(100), time BIGINT)");
        mySQL.update("CREATE TABLE IF NOT EXISTS history(uuid VARCHAR(100)," +
                " reason VARCHAR(100), end BIGINT, time BIGINT, staff_uuid VARCHAR(100))");
    }

    public void close() {
        mySQL.close();
    }

}
