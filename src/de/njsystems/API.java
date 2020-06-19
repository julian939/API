package de.njsystems;

import de.njsystems.instance.ban.BanReason;
import de.njsystems.instance.user.User;
import de.njsystems.listener.ConnectionListener;
import de.njsystems.mysql.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class API extends JavaPlugin {

    //Weitermachen ab User.grouß

    public static API getAPI;

    public FileConfiguration configcfg;

    private File config = new File(getDataFolder(), "config.yml");

    //MySQLUtilClasses
    public MySQLDataUtils mySQLDataUtils;
    public MySQLBanUtils mySQLBanUtils;
    public MySQLPermUtils mySQLPermUtils;
    public MySQLUserUtils mySQLUserUtils;
    public MySQLMuteUtils mySQLMuteUtils;
    public MySQLGroupUtils mySQLGroupUtils;
    public MySQLClanUtils mySQLClanUtils;
    public MySQLStatsUtils mySQLStatsUtils;

    @Override
    public void onEnable() {

        getAPI = this;

        //DataFolder
        if(!getDataFolder().exists())
            getDataFolder().mkdirs();

        //Config
        if(!config.exists()) {
            try {
                config.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        saveDefaultConfig();

        configcfg = YamlConfiguration.loadConfiguration(config);

        //MySQL connect
        mySQLDataUtils = new MySQLDataUtils();
        mySQLBanUtils = new MySQLBanUtils();
        mySQLPermUtils = new MySQLPermUtils();
        mySQLUserUtils = new MySQLUserUtils();
        mySQLMuteUtils = new MySQLMuteUtils();
        mySQLGroupUtils = new MySQLGroupUtils();
        mySQLClanUtils = new MySQLClanUtils();
        mySQLStatsUtils = new MySQLStatsUtils();

        registerEvents(Bukkit.getPluginManager());

        Bukkit.getConsoleSender().sendMessage(getMySQLDataUtils().getStringValue("api", "prefix") + " §adas System wurde gestartet!");

        super.onEnable();
    }

    @Override
    public void onDisable() {

        //MySQL close
        mySQLBanUtils.close();
        mySQLClanUtils.close();
        mySQLDataUtils.close();
        mySQLGroupUtils.close();
        mySQLMuteUtils.close();
        mySQLPermUtils.close();
        mySQLUserUtils.close();
        mySQLStatsUtils.close();

        Bukkit.getConsoleSender().sendMessage(getMySQLDataUtils().getStringValue("api", "prefix") + " §cdas System wurde gestoppt!");

        super.onDisable();
    }

    private void registerEvents(PluginManager pluginManager) {
        pluginManager.registerEvents(new ConnectionListener(), this);
    }

    public MySQLDataUtils getMySQLDataUtils() {
        return mySQLDataUtils;
    }

    public User getUser(String uuid) {
        return new User(uuid);
    }

    public BanReason getBanReasons() {
        return new BanReason();
    }

}
