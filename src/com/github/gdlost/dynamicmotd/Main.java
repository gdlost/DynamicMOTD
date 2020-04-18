package com.github.gdlost.dynamicmotd;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	@Override
	public void onEnable() {
		saveDefaultConfig();
		getLogger().info("Enabling DynamicMOTD");
		getServer().getPluginManager().registerEvents(new ServerList(this), this);
	}

	@Override
	public void onDisable() {
		getLogger().info("Disabling DynamicMOTD");
	}

}
