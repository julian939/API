package de.njsystems.instance.user;

import de.njsystems.API;
import de.njsystems.mysql.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserMeta {

    private int user_id;

    public UserMeta(int user_id) {
        this.user_id = user_id;
    }

    private User user = API.getAPI.getUser(User.getUUIDByID(user_id));
    private MySQL mySQL = API.getAPI.mySQLPermUtils.mySQL;

    public boolean has(String meta) {
        if (user.isRegistered()) {
            ResultSet resultSet = mySQL.getResult("SELECT * FROM user_meta WHERE meta='" + meta + "' AND user_id='" + user_id + "'");
            try {
                return resultSet.next();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    public void add(String meta, String value) {
        if (user.isRegistered()) {
            if (!has(meta))
                mySQL.update("INSERT INTO user_meta(user_id, meta, value) VALUES ('" + user_id + "','" + meta + "','" + value + "')");
        }
    }

    public void rem(String meta) {
        if (user.isRegistered()) {
            if (has(meta))
                mySQL.update("DELETE FROM user_meta WHERE meta='" + meta + "' AND user_id='" + user_id + "'");
        }
    }

    public void set(String meta, String value) {
        if (user.isRegistered()) {
            if (has(meta))
                mySQL.update("UPDATE user_meta SET meta='" + meta + "', value='" + value + "' WHERE user_id='" + user_id + "'");
        }
    }

    public String getValue(String meta) {
        if (user.isRegistered()) {
            if (has(meta)) {
                ResultSet resultSet = mySQL.getResult("SELECT value FROM user_meta WHERE meta='" + meta + "' AND user_id='" + user_id + "'");
                try {
                    if (resultSet.next())
                        return resultSet.getString("value");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return null;
    }

    public ArrayList<String> getMetas() {
        ArrayList<String> metas = new ArrayList<>();
        if (user.isRegistered()) {
            ResultSet resultSet = mySQL.getResult("SELECT * FROM user_meta WHERE user_id='" + user_id + "'");
            try {
                while (resultSet.next())
                    metas.add(resultSet.getString("meta"));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return metas;
    }

}
