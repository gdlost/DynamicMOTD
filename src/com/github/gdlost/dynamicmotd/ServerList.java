package com.github.gdlost.dynamicmotd;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ServerList implements Listener {

	MotdList mlist;
	JavaPlugin plugin;

	/* Just, the dark side */
	boolean dummy;
	int dummyNumber;

	public ServerList(JavaPlugin plugin) {
		this.plugin = plugin;
		this.dummy = plugin.getConfig().getBoolean("dummy");
		this.dummyNumber = plugin.getConfig().getInt("dummyNumber");
		this.mlist = new MotdList(this.plugin, this.plugin.getServer().getMotd());
	}

	@EventHandler
	public void onUpdateServerList(ServerListPingEvent e) {
		if(dummy) e.setMaxPlayers(dummyNumber);
		e.setMotd(mlist.getMotd());
	}
}
