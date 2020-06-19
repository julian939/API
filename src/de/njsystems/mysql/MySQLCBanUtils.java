package de.njsystems.mysql;

import de.njsystems.API;

public class MySQLCBanUtils {

    private MySQLDataUtils mySQLDataUtils = API.getAPI.getMySQLDataUtils();

    public MySQL mySQL = new MySQL(mySQLDataUtils.getMySQLValue("general", "host"),
            mySQLDataUtils.getMySQLValue("general", "port"),
            mySQLDataUtils.getMySQLValue("cban", "database"),
            mySQLDataUtils.getMySQLValue("cban", "user"),
            mySQLDataUtils.getMySQLValue("cban", "password"));

    public MySQLCBanUtils() {
        mySQL.connect();
        createTables();
    }

    private void createTables() {
        mySQL.update("CREATE TABLE IF NOT EXISTS cbans(user_id INT, reason VARCHAR(100), staff_id INT, time BIGINT, end BIGINT)");
    }

    public void close() {
        mySQL.close();
    }

}
