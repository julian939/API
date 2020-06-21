package de.njsystems.instance.group;

import de.njsystems.API;
import de.njsystems.mysql.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Group {

    private String groupname;

    public Group(String groupname) {
        this.groupname = groupname;
    }

    public GroupMeta groupMeta = new GroupMeta(groupname);
    public GroupPermission groupPermission = new GroupPermission(groupname);

    private MySQL mySQL = API.getAPI.mySQLGroupUtils.mySQL;

    public boolean exists() {
        ResultSet resultSet = mySQL.getResult("SELECT * FROM groups WHERE name='" + groupname + "'");
        try {
            return resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean hasMember(int user_id) {
        ResultSet resultSet = mySQL.getResult("SELECT group_id FROM user_group WHERE user_id='" + user_id + "'");
        try {
            return resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public int getID() {
        ResultSet resultSet = mySQL.getResult("SELECT user_group.group_id FROM user_group, groups"
                + "WHERE user_group.group_id=groups.id AND groups.name='" + groupname + "'");
        try {
            if (resultSet.next())
                return resultSet.getInt("user_group.group_id");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public void create(int weight) {
        if (!exists())
            mySQL.update("INSERT INTO groups(name, weight) VALUES ('" + groupname + "','" + (weight + 1000) + "')");
    }

    public void delete() {
        if (exists())
            mySQL.update("DELETE FROM groups WHERE name='" + groupname + "'");
    }

    public ArrayList<Integer> getMembers() {
        ArrayList<Integer> members = new ArrayList<>();
        if (exists()) {
            try {
                ResultSet resultSet = mySQL.getResult("SELECT user_id FROM user_group WHERE group_id='" + getID() + "'");
                while (resultSet.next()) {
                    members.add(resultSet.getInt("user_id"));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return members;
    }

    public void addMember(int user_id) {
        if (exists() && !hasMember(user_id))
            mySQL.update("INSERT INTO user_group(user_id, group_id) VALUES ('" + user_id + "','" + getID() + "')");

    }

    public void removeMember(int user_id) {
        if (exists() && hasMember(user_id)) {
            mySQL.update("DELETE FROM user_group WHERE user_id='" + user_id + "'");
        }
    }

}
