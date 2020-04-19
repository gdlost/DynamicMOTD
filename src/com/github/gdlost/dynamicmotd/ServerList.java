package com.github.gdlost.dynamicmotd;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class ServerList implements Listener {

	JavaPlugin plugin;

	public static MotdList mlist;

	/* Just, the dark side */
	boolean dummy;
	int dummyNumber;
	Random dummyRandom = new Random();

	public ServerList(JavaPlugin plugin) {
		this.plugin = plugin;

		/* dummy settings */
		resetDummy();
		/* static member, we need to access to execWhenReload() class method */
		mlist = new MotdList(this.plugin, this.plugin.getServer().getMotd());
	}

	private void resetDummy()
	{
		dummy = plugin.getConfig().getBoolean("dummy");
		dummyNumber = plugin.getConfig().getInt("dummyNumber");

	}

	@EventHandler
	public void onUpdateServerList(ServerListPingEvent e) {
		if(dummy) e.setMaxPlayers(dummyRandom.nextInt(dummyNumber));
		e.setMotd(mlist.getMotd());
	}

}
