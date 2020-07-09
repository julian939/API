package de.njsystems.instance.user;

import de.njsystems.API;
import de.njsystems.mysql.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMute {

    private int user_id;

    public UserMute(int user_id) {
        this.user_id = user_id;
    }

    private User user = API.getAPI.getUser(User.getUUIDByID(user_id));
    private MySQL mySQL = API.getAPI.mySQLMuteUtils.mySQL;

    public boolean isMuted() {
        if(user.isRegistered()) {
            ResultSet resultSet = mySQL.getResult("SELECT * FROM mutes WHERE user_id='" + user_id + "'");
            ResultSet resultSet1 = mySQL.getResult("SELECT * FROM cmutes WHERE user_id='" + user_id + "'");
            try {
                if(resultSet.next() && resultSet1.next())
                    return true;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    public void mute(int reason_id, int staff_id, long end) {
        if(user.isRegistered()) {
            if(!isMuted()) {
                //Inserts for bans
                mySQL.update("INSERT INTO mutes(user_id, reason_id, staff_id, end) VALUES " +
                        "('" + user_id + "','" + reason_id + "','" + staff_id + "','" + end + "')");

                //BanHistory

            }
        }
    }

    public void customMute(String reason, int staff_id, long time, long end) {
        if(user.isRegistered()) {
            if(!isMuted()) {
                //Inserts for bans
                mySQL.update("INSERT INTO cmutes(user_id, reason, staff_id, time, end) VALUES " +
                        "('" + user_id + "','" + reason + "','" + staff_id + "','" + time + "','" + end + "')");

                //BanHistory
                ResultSet resultSet = mySQL.getResult("SELECT * FROM cmutes WHERE user_id='" + user_id + "'");
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

    public void unmute(String type) {
        if(user.isRegistered()) {
            if(isMuted()) {
                mySQL.update("DELETE FROM " + type + " WHERE user_id='" + user_id + "'");
            }
        }
    }

    public String getReason(String type) {
        if(isMuted()) {
            ResultSet resultSet = mySQL.getResult("SELECT reason_id FROM " + type + " WHERE user_id='" + user_id + "'");
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

    public long getEnd(String type) {
        if(isMuted()) {
            ResultSet resultSet = mySQL.getResult("SELECT end FROM " + type + " WHERE user_id='" + user_id + "'");
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

    public long getTime(String type) {
        if(isMuted()) {
            ResultSet resultSet = mySQL.getResult("SELECT reason_id FROM " + type + " WHERE user_id='" + user_id + "'");
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

    public String getStaffName(String type) {
        if(isMuted()) {
            ResultSet resultSet = mySQL.getResult("SELECT staff_id FROM " + type + " WHERE user_id='" + user_id + "'");
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
