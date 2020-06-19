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
    private MySQL mySQL = API.getAPI.mySQLCBanUtils.mySQL;

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
                ResultSet resultSet = mySQL.getResult("SELECT * FROM bans WHERE user_id='" + user_id + "'");
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

}
