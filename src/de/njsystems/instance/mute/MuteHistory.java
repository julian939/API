package de.njsystems.instance.mute;

import de.njsystems.API;
import de.njsystems.instance.user.User;
import de.njsystems.instance.user.UserMute;
import de.njsystems.mysql.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class MuteHistory {

    private int user_id;

    private MySQL mySQL = API.getAPI.mySQLMuteUtils.mySQL;
    private UserMute userBan = API.getAPI.getUser(User.getUUIDByID(user_id)).userMute;

    public MuteHistory(int user_id) {
        this.user_id = user_id;
    }

    public void addMute(int user_id, int reason_id, long end, int staff_id) {
        mySQL.update("INSERT INTO history(uuid, reason, end, time, staff_uuid) VALUES " +
                "('" + User.getUUIDByID(user_id) + "'," +
                "'" + API.getAPI.getBanReasons().getReason(reason_id) + "'," +
                "'" + end + "'," +
                "'" + API.getAPI.getBanReasons().getTimeByID(reason_id) + "'," +
                "'" + User.getUUIDByID(staff_id) + "')");
    }

    public String getReason() {
        if(userBan.isMuted()) {
            ResultSet resultSet = mySQL.getResult("SELECT reason_id FROM history WHERE user_id='" + user_id + "'");
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
        if(userBan.isMuted()) {
            ResultSet resultSet = mySQL.getResult("SELECT end FROM history WHERE user_id='" + user_id + "'");
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
        if(userBan.isMuted()) {
            ResultSet resultSet = mySQL.getResult("SELECT reason_id FROM history WHERE user_id='" + user_id + "'");
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
        if(userBan.isMuted()) {
            ResultSet resultSet = mySQL.getResult("SELECT staff_id FROM history WHERE user_id='" + user_id + "'");
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

    public HashMap<String, Long> getMutes(String uuid) {
        HashMap<String, Long> mutes = new HashMap<>();
        ResultSet resultSet = mySQL.getResult("SELECT * FROM history WHERE uuid='" + uuid + "'");
        try {
            while(resultSet.next())
                mutes.put(resultSet.getString("reason"), resultSet.getLong("time"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return mutes;
    }

}
