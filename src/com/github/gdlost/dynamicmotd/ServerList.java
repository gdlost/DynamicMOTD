package com.github.gdlost.dynamicmotd;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class ServerList implements Listener {

	MotdList mlist;
	JavaPlugin plugin;

	/* Just, the dark side */
	boolean dummy;
	int dummyNumber;
	Random dummyRandom = new Random();
	public ServerList(JavaPlugin plugin) {
		this.plugin = plugin;
		this.dummy = plugin.getConfig().getBoolean("dummy");
		this.dummyNumber = plugin.getConfig().getInt("dummyNumber");
		this.mlist = new MotdList(this.plugin, this.plugin.getServer().getMotd());
	}

	@EventHandler
	public void onUpdateServerList(ServerListPingEvent e) {
		if(dummy) e.setMaxPlayers(dummyRandom.nextInt(dummyNumber));
		e.setMotd(mlist.getMotd());
	}
}
