package de.njsystems.instance.ban;

import de.njsystems.API;
import de.njsystems.mysql.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BanReason {

    MySQL mySQL = API.getAPI.mySQLBanUtils.mySQL;

    public String getReason(int id) {
        ResultSet resultSet = mySQL.getResult("SELECT * FROM reasons WHERE id='" + id + "'");
        try {
            if(resultSet.next()) {
                return resultSet.getString("reason");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public int getID(String reason) {
        ResultSet resultSet = mySQL.getResult("SELECT id FROM reasons WHERE reason='" + reason + "'");
        try {
            if(resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public long getTimeByID(int id) {
        ResultSet resultSet = mySQL.getResult("SELECT time FROM reasons WHERE id='" + id + "'");
        try {
            if(resultSet.next()) {
                return resultSet.getLong("time");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public long getTimeByReason(String reason) {
        ResultSet resultSet = mySQL.getResult("SELECT time FROM reasons WHERE reason='" + reason + "'");
        try {
            if(resultSet.next()) {
                return resultSet.getLong("time");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

}
