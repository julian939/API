package de.njsystems.listener;

import de.njsystems.API;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListener implements Listener {

    @EventHandler
    public void onPlayerConnect(PlayerJoinEvent event) {
        API.getAPI.getUser(event.getPlayer().getUniqueId().toString()).register(event.getPlayer().getName());
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {

    }

}
