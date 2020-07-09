package de.njsystems.instance.group;

import de.njsystems.API;
import de.njsystems.mysql.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GroupMeta {

    private String groupname;

    public GroupMeta(String groupname) {
        this.groupname = groupname;
    }

    private MySQL mySQL = API.getAPI.mySQLGroupUtils.mySQL;
    private Group group = API.getAPI.getGroup(groupname);

    public boolean has(String meta) {
        ResultSet resultSet = mySQL.getResult("SELECT * FROM group_meta WHERE meta='" + meta + "' AND group_id='" + group.getID() + "'");
        if(group.exists()) {
            try {
                return resultSet.next();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    public void add(String meta, String value) {
        if(group.exists() && !has(meta))
            mySQL.update("INSERT INTO group_meta(group_id, meta, value) VALUES ('" + group.getID() + "','" + meta + "','" + value + "')");
    }

    public void rem(String meta) {
        if(group.exists() && has(meta))
            mySQL.update("DELETE FROM group_meta WHERE meta='" + meta + "' AND group_id='" + group.getID() + "'");
    }

    public void set(String meta, String value) {
        if(group.exists() && has(meta))
            mySQL.update("UPDATE group_meta SET value='" + value + "' WHERE group_id='" + group.getID() + "' AND meta='" + meta + "'");
    }

    public String getValue(String meta) {
        ResultSet resultSet = mySQL.getResult("SELECT value FROM group_meta WHERE group_id='" + group.getID() + "' AND meta='" + meta + "'");
        if(group.exists() && has(meta)) {
            try {
                return resultSet.getString("value");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }

    public ArrayList<String> getMetas() {
        ArrayList<String> metas = new ArrayList<>();
        ResultSet resultSet = mySQL.getResult("SELECT meta FROM group_meta WHERE group_id='" + group.getID() + "'");
        try {
            while(resultSet.next())
                metas.add(resultSet.getString("meta"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return metas;
    }

}
