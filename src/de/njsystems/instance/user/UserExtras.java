package de.njsystems.instance.user;

import com.gtranslate.Translator;
import de.njsystems.API;
import de.njsystems.mysql.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserExtras {

    private int user_id;

    public UserExtras(int user_id) {
        this.user_id = user_id;
    }

    private MySQL mySQL = API.getAPI.mySQLUserUtils.mySQL;
    private User user = API.getAPI.getUser(User.getUUIDByID(user_id));

    //TeamSpeak Extras
    public boolean isTeamspeakRegistered() {
        if(user.isRegistered()) {
            ResultSet resultSet = mySQL.getResult("SELECT teamspeak_uid FROM users WHERE id='" + user_id + "'");
            try {
                return resultSet.next();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    public String getTeamspeakUID() {
        if(user.isRegistered() && isTeamspeakRegistered()) {
            ResultSet resultSet = mySQL.getResult("SELECT teamspeak_uid FROM users WHERE id='" + user_id + "'");
            try {
                return resultSet.getString("teamspeak_uid");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }

    public void setTeamspeakUID(String ts3uid) {
        if(user.isRegistered() && !isTeamspeakRegistered())
            mySQL.update("UPDATE users SET teamspeak_uid='" + ts3uid + "' WHERE id='" + user_id + "'");
    }

    //Website Extras
    public boolean isWebsiteRegistered() {
        if(user.isRegistered()) {
            ResultSet resultSet = mySQL.getResult("SELECT website_uid FROM users WHERE id='" + user_id + "'");
            try {
                return resultSet.next();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    public String getWebsiteUID() {
        if(user.isRegistered() && isWebsiteRegistered()) {
            ResultSet resultSet = mySQL.getResult("SELECT website_uid FROM users WHERE id='" + user_id + "'");
            try {
                return resultSet.getString("website_uid");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }

    public void setWebsiteUID(String ts3uid) {
        if(user.isRegistered() && !isWebsiteRegistered())
            mySQL.update("UPDATE users SET website_uid='" + ts3uid + "' WHERE id='" + user_id + "'");
    }

    //Ingame Extras
    public long getPlayTime() {
        if(user.isRegistered()) {
            ResultSet resultSet = mySQL.getResult("SELECT playtime FROM users WHERE id='" + user_id + "'");
            try {
                return resultSet.getLong("playtime");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return -1;
    }

    public void addPlayTime(long time) {
        if(user.isRegistered())
            mySQL.update("UPDATE users SET playtime='" + (System.currentTimeMillis() + time) + "' WHERE id='" + user_id + "'");
    }

    public void remPlayTime(long time) {
        if(user.isRegistered())
            mySQL.update("UPDATE users SET playtime='" + (System.currentTimeMillis() - time) + "' WHERE id='" + user_id + "'");
    }

    public void setPlayTime(long time) {
        if(user.isRegistered())
            mySQL.update("UPDATE users SET playtime='" + time + "' WHERE id='" + user_id + "'");
    }

    public String getLanguage() {
        if(user.isRegistered()) {
            ResultSet resultSet = mySQL.getResult("SELECT lang FROM users WHERE id='" + user_id + "'");
            try {
                return resultSet.getString("lang");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }

    public void setLanguage(String language) {
        if(user.isRegistered())
            mySQL.update("UPDATE users SET lang='" + language + "' WHERE id='" + user_id + "'");
    }

    public String translateMessage(String message, String lang) {
        Translator translator = Translator.getInstance();
        if(getLanguage() == "de") {
            return message;
        }else
            return translator.translate(message, "de", lang);
    }

}
