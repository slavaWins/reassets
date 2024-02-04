package org.slavawins.reassets.listener;


import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.slavawins.reassets.handles.ResourcepackSender;

public final class StartListener implements Listener {

    @EventHandler
    public void onFrameClick(PlayerJoinEvent event) {

        ResourcepackSender.toPlayer(event.getPlayer());

    }


}