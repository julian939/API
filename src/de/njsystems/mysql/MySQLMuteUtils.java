package de.njsystems.mysql;

import de.njsystems.API;

public class MySQLMuteUtils {

    private MySQLDataUtils mySQLDataUtils = API.getAPI.getMySQLDataUtils();

    public MySQL mySQL = new MySQL(mySQLDataUtils.getMySQLValue("general", "host"),
            mySQLDataUtils.getMySQLValue("general", "port"),
            mySQLDataUtils.getMySQLValue("mute", "database"),
            mySQLDataUtils.getMySQLValue("mute", "user"),
            mySQLDataUtils.getMySQLValue("mute", "password"));

    public MySQLMuteUtils() {
        mySQL.connect();
        createTables();
    }

    private void createTables() {
        mySQL.update("CREATE TABLE IF NOT EXISTS mutes(user_id INT, reason_id INT, staff_id INT, end BIGINT)");
        mySQL.update("CREATE TABLE IF NOT EXISTS cmutes(user_id INT, reason VARCHAR(100), staff_id INT, time BIGINT, end BIGINT)");
        mySQL.update("CREATE TABLE IF NOT EXISTS reasons(id INT, reason VARCHAR(100), time BIGINT)");
        mySQL.update("CREATE TABLE IF NOT EXISTS history(username VARCHAR(100), uuid VARCHAR(100)," +
                " reason VARCHAR(100), end BIGINT, time BIGINT, staff_username VARCHAR(100), staff_uuid VARCHAR(100))");
    }

    public void close() {
        mySQL.close();
    }

}
