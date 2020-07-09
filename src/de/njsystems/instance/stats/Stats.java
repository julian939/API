package de.njsystems.instance.stats;

import de.njsystems.API;
import de.njsystems.instance.user.User;
import de.njsystems.mysql.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Stats {

    private int user_id;

    public Stats(int user_id) {
        this.user_id = user_id;
    }

    private MySQL mySQL = API.getAPI.mySQLStatsUtils.mySQL;
    private User user = API.getAPI.getUser(User.getUUIDByID(user_id));

    public void addKills(int user_id, String mode) {
        if(user.isRegistered()) {
            ResultSet resultSet = mySQL.getResult("SELECT kills FROM " + mode + " WHERE user_id='" + user_id + "'");
            try {
                mySQL.update("UPDATE " + mode + "_monthly SET kills='" + (resultSet.getInt("kills") + 1) +  "' WHERE user_id='" + user_id + "'");
                mySQL.update("UPDATE " + mode + "_alltime SET kills='" + (resultSet.getInt("kills") + 1) +  "' WHERE user_id='" + user_id + "'");
                mySQL.update("UPDATE general_monthly SET kills='" + (resultSet.getInt("kills") + 1) +  "' WHERE user_id='" + user_id + "'");
                mySQL.update("UPDATE general_alltime SET kills='" + (resultSet.getInt("kills") + 1) +  "' WHERE user_id='" + user_id + "'");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void addKills(int user_id, String mode, int amount) {
        if(user.isRegistered()) {
            ResultSet resultSet = mySQL.getResult("SELECT kills FROM " + mode + " WHERE user_id='" + user_id + "'");
            try {
                mySQL.update("UPDATE " + mode + "_monthly SET kills='" + (resultSet.getInt("kills") + amount) +  "' WHERE user_id='" + user_id + "'");
                mySQL.update("UPDATE " + mode + "_alltime SET kills='" + (resultSet.getInt("kills") + amount) +  "' WHERE user_id='" + user_id + "'");
                mySQL.update("UPDATE general_monthly SET kills='" + (resultSet.getInt("kills") + amount) +  "' WHERE user_id='" + user_id + "'");
                mySQL.update("UPDATE general_alltime SET kills='" + (resultSet.getInt("kills") + amount) +  "' WHERE user_id='" + user_id + "'");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void setKills(int user_id, String mode, String type, int amount) {
        if(user.isRegistered())
            mySQL.update("UPDATE " + mode + "_" + type + " SET kills='" + amount +  "' WHERE user_id='" + user_id + "'");
    }

    public int getKills(int user_id, String mode, String type) {
        if(user.isRegistered()) {
            ResultSet resultSet = mySQL.getResult("SELECT kills FROM " + mode + "_" + type + " WHERE user_id='" + user_id + "'");
            try {
                resultSet.getInt("kills");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return -1;
    }

    public void remKills(int user_id, String mode, String type) {
        if(user.isRegistered())
            mySQL.update("UPDATE " + mode + "_" + type + " SET kills='0' WHERE user_id='" + user_id + "'");
    }

    public void addDeaths(int user_id, String mode) {
        if(user.isRegistered()) {
            ResultSet resultSet = mySQL.getResult("SELECT deaths FROM " + mode + " WHERE user_id='" + user_id + "'");
            try {
                mySQL.update("UPDATE " + mode + "_alltime SET deaths='" + (resultSet.getInt("deaths") + 1) +  "' WHERE user_id='" + user_id + "'");
                mySQL.update("UPDATE " + mode + "_monthly SET deaths='" + (resultSet.getInt("deaths") + 1) +  "' WHERE user_id='" + user_id + "'");
                mySQL.update("UPDATE general_alltime SET deaths='" + (resultSet.getInt("deaths") + 1) +  "' WHERE user_id='" + user_id + "'");
                mySQL.update("UPDATE general_monthly SET deaths='" + (resultSet.getInt("deaths") + 1) +  "' WHERE user_id='" + user_id + "'");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void addDeaths(int user_id, String mode, int amount) {
        if(user.isRegistered()) {
            ResultSet resultSet = mySQL.getResult("SELECT deaths FROM " + mode + " WHERE user_id='" + user_id + "'");
            try {
                mySQL.update("UPDATE " + mode + "_alltime SET deaths='" + (resultSet.getInt("deaths") + amount) +  "' WHERE user_id='" + user_id + "'");
                mySQL.update("UPDATE " + mode + "_monthly SET deaths='" + (resultSet.getInt("deaths") + amount) +  "' WHERE user_id='" + user_id + "'");
                mySQL.update("UPDATE general_alltime SET deaths='" + (resultSet.getInt("deaths") + amount) +  "' WHERE user_id='" + user_id + "'");
                mySQL.update("UPDATE general_monthly SET deaths='" + (resultSet.getInt("deaths") + amount) +  "' WHERE user_id='" + user_id + "'");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void setDeaths(int user_id, String mode, String type, int amount) {
        if(user.isRegistered())
            mySQL.update("UPDATE " + mode + "_" + type + " SET deaths='" + amount +  "' WHERE user_id='" + user_id + "'");
    }

    public int getDeaths(int user_id, String mode, String type) {
        if(user.isRegistered()) {
            ResultSet resultSet = mySQL.getResult("SELECT deaths FROM " + mode + "_" + type + " WHERE user_id='" + user_id + "'");
            try {
                resultSet.getInt("deaths");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return -1;
    }

    public void remDeaths(int user_id, String mode, String type) {
        if(user.isRegistered())
            mySQL.update("UPDATE " + mode + "_" + type + " SET deaths='0' WHERE user_id='" + user_id + "'");
    }

    public void addPoints(int user_id, String mode, int amount) {
        if(user.isRegistered()) {
            ResultSet resultSet = mySQL.getResult("SELECT points FROM " + mode + " WHERE user_id='" + user_id + "'");
            try {
                mySQL.update("UPDATE " + mode + "_alltime SET points='" + (resultSet.getInt("points") + amount) +  "' WHERE user_id='" + user_id + "'");
                mySQL.update("UPDATE " + mode + "_monthly SET points='" + (resultSet.getInt("points") + amount) +  "' WHERE user_id='" + user_id + "'");
                mySQL.update("UPDATE general_alltime SET points='" + (resultSet.getInt("points") + amount) +  "' WHERE user_id='" + user_id + "'");
                mySQL.update("UPDATE general_monthly SET points='" + (resultSet.getInt("points") + amount) +  "' WHERE user_id='" + user_id + "'");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void addPoints(int user_id, String mode, String type, int amount) {
        if(user.isRegistered()) {
            ResultSet resultSet = mySQL.getResult("SELECT points FROM " + mode + " WHERE user_id='" + user_id + "'");
            try {
                mySQL.update("UPDATE " + mode + "_" + type + " SET points='" + (resultSet.getInt("points") + amount) +  "' WHERE user_id='" + user_id + "'");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void setPoints(int user_id, String mode, String type, int amount) {
        if(user.isRegistered()) {
            mySQL.update("UPDATE " + mode + "_" + type + " SET points='" + amount +  "' WHERE user_id='" + user_id + "'");
        }
    }

    public int getPoints(int user_id, String mode, String type) {
        if(user.isRegistered()) {
            ResultSet resultSet = mySQL.getResult("SELECT points FROM " + mode + "_" + type + " WHERE user_id='" + user_id + "'");
            try {
                resultSet.getInt("points");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return -1;
    }

    public void remPoints(int user_id, String mode, String type) {
        if(user.isRegistered())
            mySQL.update("UPDATE " + mode + "_" + type + " SET points='0' WHERE user_id='" + user_id + "'");
    }

    public double getKD(int user_id, String mode, String type) {
        if(user.isRegistered()) {
            ResultSet resultSet = mySQL.getResult("SELECT * FROM " + mode + "_" + type + " WHERE user_id='" + user_id + "'");
            try {
                return (resultSet.getInt("kills") / resultSet.getInt("deaths"));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return -1;
    }

    public void reset(int user_id, String mode, String type) {
        if(user.isRegistered())
            mySQL.update("DELETE FROM " + mode + "_" + type + " WHERE user_id='" + user_id + "'");
    }

    public HashMap<Integer, Integer> getAllKills(String mode, String type) {
        HashMap<Integer, Integer> kills = new HashMap<>();
        ResultSet resultSet = mySQL.getResult("SELECT * FROM " + mode + "_" + type);
        try {
            while(resultSet.next())
                kills.put(resultSet.getInt("user_id"), resultSet.getInt("kills"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return kills;
    }

    public HashMap<Integer, Integer> getAllDeaths(String mode, String type) {
        HashMap<Integer, Integer> deaths = new HashMap<>();
        ResultSet resultSet = mySQL.getResult("SELECT * FROM " + mode + "_" + type);
        try {
            while(resultSet.next())
                deaths.put(resultSet.getInt("user_id"), resultSet.getInt("deaths"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return deaths;
    }

    public HashMap<Integer, Integer> getAllPoints(String mode, String type) {
        HashMap<Integer, Integer> points = new HashMap<>();
        ResultSet resultSet = mySQL.getResult("SELECT * FROM " + mode + "_" + type);
        try {
            while(resultSet.next())
                points.put(resultSet.getInt("user_id"), resultSet.getInt("points"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return points;
    }

    public void resetAll(String mode, String type) {
        mySQL.update("DELETE FROM " + mode + "_" + type);
    }

}
