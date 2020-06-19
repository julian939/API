package de.njsystems.mysql;

import de.njsystems.API;

public class MySQLClanUtils {

    private MySQLDataUtils mySQLDataUtils = API.getAPI.getMySQLDataUtils();

    public MySQL mySQL = new MySQL(mySQLDataUtils.getMySQLValue("general", "host"),
            mySQLDataUtils.getMySQLValue("general", "port"),
            mySQLDataUtils.getMySQLValue("clan", "database"),
            mySQLDataUtils.getMySQLValue("clan", "user"),
            mySQLDataUtils.getMySQLValue("clan", "password"));

    public MySQLClanUtils() {
        mySQL.connect();
        createTables();
    }

    private void createTables() {
        mySQL.update("CREATE TABLE IF NOT EXISTS clans(id INT AUTO_INCREMENT, name VARCHAR(100), tag VARCHAR(100), PRIMARY KEY (id))");
        mySQL.update("CREATE TABLE IF NOT EXISTS user_clan(user_id INT, clan_id INT, rank VARCHAR(100))");
    }

    public void close() {
        mySQL.close();
    }

}
