package de.njsystems.instance.user;

import de.njsystems.API;
import de.njsystems.mysql.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserClan {

    private int user_id;

    public UserClan(int user_id) {
        this.user_id = user_id;
    }

    private MySQL mySQL = API.getAPI.mySQLClanUtils.mySQL;
    private User user = API.getAPI.getUser(User.getUUIDByID(user_id));

    public boolean isInClan() {
        if(user.isRegistered()) {
            ResultSet resultSet = mySQL.getResult("SELECT * FROM user_clan WHERE user_id='" + user_id + "'");
            try {
                return resultSet.next();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }
    public boolean isInClan(String clanname) {
        if(user.isRegistered()) {
            ResultSet resultSet = mySQL.getResult("SELECT * FROM user_clan WHERE user_id='" + user_id + "' AND clan_id='" + API.getAPI.getClan(clanname).getID() + "'");
            try {
                return resultSet.next();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }


    public void join(String clanname) {
        if(user.isRegistered() && !isInClan())
            mySQL.update("INSERT INTO user_clan(user_id, clan_id, rank) VALUES ('" + user_id + "'," +
                    "'" + API.getAPI.getClan(clanname).getID() +  "','member')");
    }

    public void quit(String clanname) {
        if(user.isRegistered() && isInClan(clanname))
            mySQL.update("DELETE FROM user_clan WHERE user_id='" + user_id + "' AND clan_id='" + API.getAPI.getClan(clanname).getID() + "'");
    }

    public String getClanName() {
        if(user.isRegistered() && isInClan()) {
            ResultSet resultSet = mySQL.getResult("SELECT clans.name FROM user_clan, clans WHERE user_clan.user_id='" + user_id + "' AND clans.id=user_clan.clan_id");
            try {
                return resultSet.getString("clans.name");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }

    public void promote() {
        if(user.isRegistered() && isInClan()) {
            ResultSet resultSet = mySQL.getResult("SELECT rank FROM user_clan WHERE user_id='" + user_id + "'");
            try {
                switch (resultSet.getString("rank")){
                    case "member":
                        mySQL.update("UPDATE user_clan SET rank='moderator' WHERE user_id='" + user_id + "'");
                        break;
                    case "moderator":
                        mySQL.update("UPDATE user_clan SET rank='owner' WHERE user_id='" + user_id + "'");
                        break;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void degrade() {
        if(user.isRegistered() && isInClan()) {
            ResultSet resultSet = mySQL.getResult("SELECT rank FROM user_clan WHERE user_id='" + user_id + "'");
            try {
                switch (resultSet.getString("rank")){
                    case "moderator":
                        mySQL.update("UPDATE user_clan SET rank='member' WHERE user_id='" + user_id + "'");
                        break;
                    case "owner":
                        mySQL.update("UPDATE user_clan SET rank='owner' WHERE user_id='" + user_id + "'");
                        break;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public String getClanRank() {
        ResultSet resultSet = mySQL.getResult("SELECT rank FROM user_clan WHERE user_id='" + user_id + "'");
        try {
            return resultSet.getString("rank");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

}
