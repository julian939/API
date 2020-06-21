package de.njsystems.instance.user;

import de.njsystems.API;
import de.njsystems.mysql.MySQL;

public class UserGroup {

    public

    private int user_id;

    public UserGroup(int user_id) {
        this.user_id = user_id;
    }

    private User user = API.getAPI.getUser(User.getUUIDByID(user_id));
    private MySQL mySQL = API.getAPI.mySQLGroupUtils.mySQL;

    public void add(String groupname) {

    }

}
