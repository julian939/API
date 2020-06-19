package de.njsystems.instance.user;

import de.njsystems.API;
import de.njsystems.mysql.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

    //instances
    public UserPermissions permissions = new UserPermissions(getID());
    public UserBan userBan = new UserBan(getID());
    public UserMeta userMeta = new UserMeta(getID());
    public UserGroup userGroup = new UserGroup(getID());
    public UserClan userClan = new UserClan(getID());
    public UserExtras userExtras = new UserExtras(getID());

    private MySQL mySQL = API.getAPI.mySQLUserUtils.mySQL;

    private String uuid;

    public User(String uuid) {
        this.uuid = uuid;
    }

    public boolean isRegistered() {
        ResultSet resultSet = mySQL.getResult("SELECT * FROM users WHERE uuid='" + uuid + "'");
        try {
            return resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public void register(String name) {
        if (!isRegistered()) {
            mySQL.update("INSERT INTO users(uuid, name, lang, firstregister) VALUES" +
                    " ('" + uuid + "', '" + name + "', 'de', '" + String.valueOf(System.currentTimeMillis()) + "')");
        }
    }

    public void unregister() {
        if (isRegistered()) {
            mySQL.update("DELETE FROM users WHERE uuid='" + uuid + "'");
        }
    }

    public int getID() {
        if(isRegistered()) {
            ResultSet resultSet = mySQL.getResult("SELECT id FROM users WHERE uuid= '" + uuid + "'");
            try {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return -1;
    }

    public String getName() {
        if(isRegistered()) {
            ResultSet resultSet = mySQL.getResult("SELECT name FROM users WHERE uuid='" + uuid + "'");
            try {
                if(resultSet.next()) {
                    return resultSet.getString("name");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }

    public void setName(String name) {
        if(isRegistered()) {
            mySQL.update("UPDATE users SET name='" + name + "' WHERE uuid='" + uuid + "'");
        }
    }

    public static String getUUIDByID(int id) {
        ResultSet resultSet = API.getAPI.mySQLUserUtils.mySQL.getResult("SELECT uuid FROM users WHERE id='" + id + "'");
        try {
            if(resultSet.next()) {
                return resultSet.getString("uuid");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static String getUUIDByName(String name) {
        ResultSet resultSet = API.getAPI.mySQLUserUtils.mySQL.getResult("SELECT uuid FROM users WHERE name='" + name + "'");
        try {
            if(resultSet.next()) {
                return resultSet.getString("uuid");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

}
