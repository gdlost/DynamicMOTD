package com.github.gdlost.dynamicmotd;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	@Override
	public void onEnable() {
		saveDefaultConfig();
		getLogger().info("Enabling DynamicMOTD");

		/* ServerListPing event */
		getServer().getPluginManager().registerEvents(new ServerList(this), this);

		/* DynamicMotd main command */
		getCommand("dynamicmotd").setExecutor(new Commands(this));
	}

	@Override
	public void onDisable() {
		getLogger().info("Disabling DynamicMOTD");
	}

}
