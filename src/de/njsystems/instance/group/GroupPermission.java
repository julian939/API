package de.njsystems.instance.group;

import de.njsystems.API;
import de.njsystems.mysql.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GroupPermission {

    private String groupname;

    public GroupPermission(String groupname) {
        this.groupname = groupname;
    }

    private MySQL mySQL = API.getAPI.mySQLGroupUtils.mySQL;
    private Group group = API.getAPI.getGroup(groupname);

    public boolean has(String permission) {
        if(group.exists()) {
            ResultSet resultSet = mySQL.getResult("SELECT * FROM group_permissions WHERE group_id='"
                    + group.getID() + "' AND permission='" + permission + "'");
            try {
                return resultSet.next();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    public boolean has(String permission, String server) {
        if(group.exists()) {
            ResultSet resultSet = mySQL.getResult("SELECT * FROM group_permissions WHERE group_id='"
                    + group.getID() + "' AND permission='" + permission + "' AND server='" + server + "'");
            try {
                return resultSet.next();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    public void add(String permission) {
        if(group.exists() && !has(permission)) {
            mySQL.update("INSERT INTO group_permissions(group_id, permission, server, end) VALUES" +
                    " ('" + group.getID() + "','" + permission + "','global', '-1')");
        }
    }

    public void add(String permission, long time) {
        if(group.exists() && !has(permission)) {
            mySQL.update("INSERT INTO group_permissions(group_id, permission, server, end) VALUES" +
                    " ('" + group.getID() + "','" + permission + "','global', '" + (System.currentTimeMillis() + time) + "')");
        }
    }

    public void add(String permission, String server) {
        if(group.exists() && !has(permission)) {
            mySQL.update("INSERT INTO group_permissions(group_id, permission, server, end) VALUES" +
                    " ('" + group.getID() + "','" + permission + "','" + server + "', '-1')");
        }
    }

    public void add(String permission, String server, long time) {
        if(group.exists() && !has(permission)) {
            mySQL.update("INSERT INTO group_permissions(group_id, permission, server, end) VALUES" +
                    " ('" + group.getID() + "','" + permission + "','" + server + "', '" + (System.currentTimeMillis() + time) + "')");
        }
    }

    public void set(String permission, String server) {
        if(group.exists() && has(permission)) {
            mySQL.update("UPDATE group_permissions SET server='" + server + "' WHERE permission='" + permission + "' AND group_id='" + group.getID() + "'");
        }
    }

    public void set(String permission, long time) {
        if(group.exists() && has(permission)) {
            mySQL.update("UPDATE group_permissions SET end='" + (System.currentTimeMillis() + time) + "' WHERE permission='" + permission + "' AND group_id='" + group.getID() + "'");
        }
    }

    public void set(String permission, String server, long time) {
        if(group.exists() && has(permission)) {
            mySQL.update("UPDATE group_permissions SET server='" + server + "', " +
                    "end='" + (System.currentTimeMillis() + time) + "' WHERE permission='" + permission + "' AND group_id='" + group.getID() + "'");
        }
    }

    public void rem(String permission) {
        if(group.exists() && has(permission)) {
            mySQL.update("DELETE FROM user_permission WHERE permission='" + permission + "' AND group_id='" + group.getID() + "'");
        }
    }

    public void rem(String permission, String server) {
        if(group.exists() && has(permission)) {
            mySQL.update("DELETE FROM user_permission WHERE permission='" + permission + "' AND server='" + server + "' AND group_id='" + group.getID() + "'");
        }
    }

    public long getTime(String permission) {
        ResultSet resultSet = mySQL.getResult("SELECT end FROM group_permissions WHERE permission='" + permission + "' AND group_id='" + group.getID() + "'");
        try {
            if(resultSet.next())
                return (resultSet.getLong("end") - System.currentTimeMillis());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }
    public long getTime(String permission, String server) {
        ResultSet resultSet = mySQL.getResult("SELECT end FROM group_permissions WHERE permission='" + permission + "' AND server='" + server + "' AND group_id='" + group.getID() + "'");
        try {
            if(resultSet.next())
                return (resultSet.getLong("end") - System.currentTimeMillis());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public ArrayList<String> getServers(String permission) {
        ArrayList<String> servers = new ArrayList<>();
        if(group.exists() && has(permission)) {
            ResultSet resultSet = mySQL.getResult("SELECT server FROM group_permissions WHERE permission='" +permission + "' AND group_id='" + group.getID() + "'");
            try {
                while(resultSet.next()) {
                    servers.add(resultSet.getString("server"));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return servers;
    }

    public ArrayList<String> getPermissions() {
        ArrayList<String> permissions = new ArrayList<>();
        if(group.exists()) {
            ResultSet resultSet = mySQL.getResult("SELECT permission FROM group_permissions WHERE group_id='" + group.getID() + "'");
            try {
                while(resultSet.next()) {
                    permissions.add(resultSet.getString("permission"));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return permissions;
    }

}
