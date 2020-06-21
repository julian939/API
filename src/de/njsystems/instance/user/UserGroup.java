package de.njsystems.instance.user;

import de.njsystems.API;
import de.njsystems.mysql.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserGroup {

    private int user_id;

    public UserGroup(int user_id) {
        this.user_id = user_id;
    }

    private User user = API.getAPI.getUser(User.getUUIDByID(user_id));
    private MySQL mySQL = API.getAPI.mySQLGroupUtils.mySQL;

    public boolean has(String groupname) {
        ResultSet resultSet = mySQL.getResult("SELECT * FROM user_group WHERE user_id='" + user_id + "' AND group_id='" + API.getAPI.getGroup(groupname).getID() + "'");
        if(user.isRegistered() && API.getAPI.getGroup(groupname).exists()) {
            try {
                return resultSet.next();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    public void add(String groupname) {
        if(user.isRegistered() && API.getAPI.getGroup(groupname).exists() && !has(groupname))
            mySQL.update("INSERT INTO user_group(user_id, group_id, end) VALUES ('" + user_id + "','" + API.getAPI.getGroup(groupname).getID() + "', '-1')");
    }

    public void rem(String groupname) {
        if(user.isRegistered() && API.getAPI.getGroup(groupname).exists() && has(groupname))
            mySQL.update("DELETE FROM user_group WHERE user_id='" + user_id + "' AND group_id='" + API.getAPI.getGroup(groupname).getID() + "'");
    }

    public ArrayList<String> getAll() {
        ArrayList<String> groups = new ArrayList<>();
        ResultSet resultSet = mySQL.getResult("SELECT groups.name FROM groups, user_group " +
                "WHERE user_group.user_id='" + user_id + "' AND user_group.group_id=groups.id");
        try {
            while(resultSet.next())
                groups.add(resultSet.getString("groups.name"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return groups;
    }

}
