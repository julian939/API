package de.njsystems.instance.user;

import de.njsystems.API;
import de.njsystems.mysql.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserPermissions {

    private int user_id;

    public UserPermissions(int user_id) {
        this.user_id = user_id;
    }

    private MySQL mySQL = API.getAPI.mySQLPermUtils.mySQL;
    private User user = API.getAPI.getUser(User.getUUIDByID(user_id));

    public void add(String permission) {
        if(user.isRegistered()) {
            mySQL.update("INSERT INTO user_permissions(user_id, permission, server, end)" +
                    " VALUES ('" + user_id + "', '" + permission + "', 'global', '-1')");
        }
    }
    public void add(String permission, long time) {
        if(user.isRegistered()) {
            mySQL.update("INSERT INTO user_permissions(user_id, permission, server, end)" +
                    " VALUES ('" + user_id + "', '" + permission + "', 'global', '" + time + "')");
        }
    }

    public void add(String permission, String server) {
        if(user.isRegistered()) {
            mySQL.update("INSERT INTO user_permissions(user_id, permission, server, end)" +
                    " VALUES ('" + user_id + "', '" + permission + "', '" + server + "', '-1')");
        }
    }

    public void add(String permission, String server, long time) {
        if(user.isRegistered()) {
            mySQL.update("INSERT INTO user_permissions(user_id, permission, server, end)" +
                    " VALUES ('" + user_id + "', '" + permission + "', '" + server + "', '" + time + "')");
        }
    }

    public void set(String permission, String server) {
        if(user.isRegistered())
            mySQL.update("UPDATE user_permissions SET permission='" + permission + "', server='" + server + "' WHERE user_id='" + user_id + "'");
    }

    public void set(String permission, long time) {
        if(user.isRegistered())
            mySQL.update("UPDATE user_permissions SET permission='" + permission + "', end='" + time + "' WHERE user_id='" + user_id + "'");
    }

    public void set(String permission, String server, long time) {
        if(user.isRegistered())
            mySQL.update("UPDATE user_permissions SET permission='" + permission + "', end='" + time + "', server='" + server + "' WHERE user_id='" + user_id + "'");
    }

    public void rem(String permission) {
        if(user.isRegistered())
            mySQL.update("DELETE FROM user_permissions WHERE permission='" + permission + "'");
    }

    public void rem(String permission, String server) {
        if(user.isRegistered())
            mySQL.update("DELETE FROM user_permissions WHERE permission='" + permission + "' AND server='" + server + "'");
    }

    public boolean isSet(String permission) {
        if(user.isRegistered()) {
            ResultSet resultSet = mySQL.getResult("SELECT * WHERE user_id='" + user_id + "' AND permission='" + permission + "'");
            try {
                return resultSet.next();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    public boolean isSet(String permission, String server) {
        if(user.isRegistered()) {
            ResultSet resultSet = mySQL.getResult("SELECT * WHERE user_id='" + user_id + "' AND permission='" + permission + "' AND server='" + server + "'");
            try {
                return resultSet.next();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    public long getRemainingMillis(String permission) {
        if(user.isRegistered()) {
            ResultSet resultSet = mySQL.getResult("SELECT end WHERE user_id='" + user_id + "' AND permission='" + permission + "'");
            try {
                if(resultSet.next()) {
                    if(resultSet.getLong("end") != -1) {
                        return (resultSet.getLong("end") - System.currentTimeMillis());
                    }else
                        return -1;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return -1;
    }

    public long getRemainingMillis(String permission, String server) {
        if(user.isRegistered()) {
            ResultSet resultSet = mySQL.getResult("SELECT end WHERE user_id='" + user_id + "' AND permission='" + permission + "' AND server='" + server + "'");
            try {
                if(resultSet.next()) {
                    if(resultSet.getLong("end") != -1) {
                        return (resultSet.getLong("end") - System.currentTimeMillis());
                    }else
                        return -1;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return -1;
    }

    public ArrayList<String> getServers(String permission) {
        if(user.isRegistered()) {
            ResultSet resultSet = mySQL.getResult("SELECT server WHERE user_id='" + user_id + "' AND permission='" + permission + "'");
            ArrayList<String> servers = new ArrayList<>();
            try {
                while (resultSet.next()) {
                    servers.add(resultSet.getString("server"));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return servers;
        }
        return null;
    }

    public ArrayList<String> getPermissions() {
        if(user.isRegistered()) {
            ResultSet resultSet = mySQL.getResult("SELECT permission WHERE user_id='" + user_id + "'");
            ArrayList<String> permissions = new ArrayList<>();
            try {
                while (resultSet.next()) {
                    permissions.add(resultSet.getString("permission"));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return permissions;
        }
        return null;
    }

}
