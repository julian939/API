package de.njsystems.instance.user;

import de.njsystems.API;
import de.njsystems.mysql.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserCBan {

    private int user_id;

    public UserCBan(int user_id) {
        this.user_id = user_id;
    }

    private User user = API.getAPI.getUser(User.getUUIDByID(user_id));
    private MySQL mySQL = API.getAPI.mySQLBanUtils.mySQL;

    public boolean isBanned() {
        if(user.isRegistered()) {
            ResultSet resultSet = mySQL.getResult("SELECT * FROM cbans WHERE user_id='" + user_id + "'");
            try {
                return resultSet.next();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    public void customBan(String reason, int staff_id, long time, long end) {
        if(user.isRegistered()) {
            if(!isBanned()) {
                //Inserts for bans
                mySQL.update("INSERT INTO cbans(user_id, reason, staff_id, time, end) VALUES " +
                        "('" + user_id + "','" + reason + "','" + staff_id + "','" + time + "','" + end + "')");

                //BanHistory
                ResultSet resultSet = mySQL.getResult("SELECT * FROM cbans WHERE user_id='" + user_id + "'");
                try {
                    if(resultSet.next())
                        mySQL.update("INSERT INTO history(uuid, reason, end, time, staff_uuid) VALUES " +
                                "('" + User.getUUIDByID(user_id) + "'," +
                                "'" + reason + "'," +
                                "'" + end + "'," +
                                "'" + time + "'," +
                                "'" + User.getUUIDByID(staff_id) + "')");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    public void unban() {
        if(user.isRegistered()) {
            if(isBanned()) {
                mySQL.update("DELETE FROM cbans WHERE user_id='" + user_id + "'");
            }
        }
    }

    public String getReason() {
        if(isBanned()) {
            ResultSet resultSet = mySQL.getResult("SELECT reason FROM cbans WHERE user_id='" + user_id + "'");
            try {
                if(resultSet.next()) {
                    return resultSet.getString("reason");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }

    public long getEnd() {
        if(isBanned()) {
            ResultSet resultSet = mySQL.getResult("SELECT end FROM cbans WHERE user_id='" + user_id + "'");
            try {
                if(resultSet.next()) {
                    return resultSet.getLong("end");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return -1;
    }

    public long getTime() {
        if(isBanned()) {
            ResultSet resultSet = mySQL.getResult("SELECT time FROM cbans WHERE user_id='" + user_id + "'");
            try {
                if(resultSet.next()) {
                    return resultSet.getLong("time");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return -1;
    }

    public String getStaffName() {
        if(isBanned()) {
            ResultSet resultSet = mySQL.getResult("SELECT staff_id FROM cbans WHERE user_id='" + user_id + "'");
            try {
                if(resultSet.next()) {
                    return API.getAPI.getUser(User.getUUIDByID(resultSet.getInt("staff_id"))).getName();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }

}
