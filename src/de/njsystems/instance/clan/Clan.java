package de.njsystems.instance.clan;

import de.njsystems.API;
import de.njsystems.mysql.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Clan {

    private String clanname;

    public Clan(String clanname) {
        this.clanname = clanname;
    }

    private MySQL mySQL = API.getAPI.mySQLClanUtils.mySQL;
    
    public boolean exists() {
        ResultSet resultSet = mySQL.getResult("SELECT * FROM clans WHERE name='" + clanname + "'");
        try {
            return resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean hasMember(int user_id) {
        ResultSet resultSet = mySQL.getResult("SELECT * FROM user_clan WHERE clan_id='" + getID() + "' AND user_id='" + user_id + "'");
        try {
            return resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public int getID() {
        ResultSet resultSet = mySQL.getResult("");
        try {
            if(exists() && resultSet.next())
                return resultSet.getInt("id");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public void create(int user_id, String tag) {
        if(!exists()) {
            mySQL.update("INSERT INTO clans(name, tag) VALUES ('" + clanname + "','" + tag + "')");
            mySQL.update("INSERT INTO user_clan(user_id, clan_id, rank) VALUES ('" + user_id + "','" + getID() + "','owner')");
        }
    }

    public void delete(int user_id) {
        if(exists()) {
            mySQL.update("DELETE FROM clans WHERE name='" + clanname + "'");
            mySQL.update("DELETE FROM user_clan WHERE clan_id='" + getID() + "' AND user_id='" + user_id + "'");
        }
    }

    public String getTag() {
        ResultSet resultSet = mySQL.getResult("SELECT tag FROM clans WHERE name='" + clanname + "'");
        try {
            if(resultSet.next())
                return resultSet.getString("tag");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public ArrayList<Integer> getMembers() {
        ArrayList<Integer> members = new ArrayList<>();
        ResultSet resultSet = mySQL.getResult("SELECT user_id FROM user_clan WHERE clan_id='" + getID() + "'");
        try {
            while (resultSet.next())
                members.add(resultSet.getInt("user_id"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return members;
    }

    public void addMember(int user_id) {
        if(exists() && !hasMember(user_id))
            mySQL.update("INSERT INTO user_clan(user_id, clan_id, rank) VALUES ('" + user_id + "', '" + getID() + "', 'member')");
    }

    public void removeMember(int user_id) {
        if(exists() && hasMember(user_id))
            mySQL.update("DELETE FROM user_clan WHERE user_id='" + user_id + "' AND clan_id='" + getID() + "'");
    }

    public String getMemberRank(int user_id) {
        ResultSet resultSet = mySQL.getResult("SELECT rank FROM user_clan WHERE clan_id='" + getID() + "' AND user_id='" + user_id + "'");
        try {
            if(hasMember(user_id) && exists() && resultSet.next())
                return resultSet.getString("rank");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void setMemberRank(int user_id, String rank) {
        if(hasMember(user_id) && exists())
            mySQL.update("UPDATE user_clan SET rank='" + rank + "' WHERE user_id='" + user_id + "' AND clan_id='" + getID() + "'");
    }

    public int getOwner() {
        ResultSet resultSet = mySQL.getResult("SELECT user_id FROM user_clan WHERE clan_id='" + getID() + "' AND rank='owner'");
        try {
            if(resultSet.next())
                return resultSet.getInt("user_id");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public long getPoints() {
        ResultSet resultSet = mySQL.getResult("SELECT points FROm clans WHERE name='" + clanname + "'");
        try {
            if(resultSet.next() && exists())
                return resultSet.getLong("points");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public void addPoints(int points) {
        if(exists())
            mySQL.update("UPDATE clans SET points='" + (getPoints() + points) + "' WHERE name='" + clanname + "'");
    }

    public void setPoints(long points) {
        if(exists())
            mySQL.update("UPDATE clans SET points='" + points + "' WHERE name='" + clanname + "'");
    }

    public void remPoints(long points) {
        if(exists())
            mySQL.update("UPDATE clans SET points='" + (getPoints() - points) + "' WHERE name='" + clanname + "'");
    }

}
