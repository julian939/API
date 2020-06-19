package de.njsystems.instance.user;

import de.njsystems.API;
import de.njsystems.mysql.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserBan {

    private int user_id;

    public UserBan(int user_id) {
        this.user_id = user_id;
    }

    private User user = API.getAPI.getUser(User.getUUIDByID(user_id));
    private MySQL mySQL = API.getAPI.mySQLBanUtils.mySQL;

    public boolean isBanned() {
        if(user.isRegistered()) {
            ResultSet resultSet = mySQL.getResult("SELECT * FROM bans WHERE user_id='" + user_id + "'");
            try {
                return resultSet.next();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    public void ban(int reason_id, int staff_id, long end) {
        if(user.isRegistered()) {
            if(!isBanned()) {
                //Inserts for bans
                mySQL.update("INSERT INTO bans(user_id, reason_id, staff_id, end) VALUES " +
                        "('" + user_id + "','" + reason_id + "','" + staff_id + "','" + end + "')");

                //BanHistory
                ResultSet resultSet = mySQL.getResult("SELECT * FROM bans WHERE user_id='" + user_id + "'");
                try {
                    if(resultSet.next())
                        mySQL.update("INSERT INTO history(uuid, reason, end, time, staff_uuid) VALUES " +
                                "('" + User.getUUIDByID(user_id) + "'," +
                                "'" + API.getAPI.getBanReasons().getReason(reason_id) + "'," +
                                "'" + end + "'," +
                                "'" + API.getAPI.getBanReasons().getTimeByID(reason_id) + "'," +
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
                mySQL.update("DELETE FROM bans WHERE user_id='" + user_id + "'");
            }
        }
    }

    public String getReason() {
        if(isBanned()) {
            ResultSet resultSet = mySQL.getResult("SELECT reason_id FROM bans WHERE user_id='" + user_id + "'");
            try {
                if(resultSet.next()) {
                    return API.getAPI.getBanReasons().getReason(resultSet.getInt("reason_id"));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }

    public long getEnd() {
        if(isBanned()) {
            ResultSet resultSet = mySQL.getResult("SELECT end FROM bans WHERE user_id='" + user_id + "'");
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
            ResultSet resultSet = mySQL.getResult("SELECT reason_id FROM bans WHERE user_id='" + user_id + "'");
            try {
                if(resultSet.next()) {
                    return API.getAPI.getBanReasons().getTimeByID(resultSet.getInt("reason_id"));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return -1;
    }

    public String getStaffName() {
        if(isBanned()) {
            ResultSet resultSet = mySQL.getResult("SELECT staff_id FROM bans WHERE user_id='" + user_id + "'");
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
