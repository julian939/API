package de.njsystems.instance.group;

import de.njsystems.API;
import de.njsystems.mysql.MySQL;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Group {

    public GroupMeta groupMeta = new GroupMeta();
    public GroupPermission groupPermission = new GroupPermission();

    private String groupname;

    public Group(String groupname) {
        this.groupname = groupname;
    }

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

    public void create(int weight) {
        if(!exists())
            mySQL.update("INSERT INTO groups(name, weight) VALUES ('" + groupname + "','" + (weight + 1000) + "')");
    }

    public void delete() {
        if(exists())
            mySQL.update("DELETE FROM groups WHERE name='" + groupname + "'");
    }

    public ArrayList<Integer> getMembers() {
        ArrayList<String> members = new ArrayList<>();
        ResultSet resultSet = mySQL.getResult("SELECT * FROM groups WHERE name='" + groupname + "'");
    }

}
