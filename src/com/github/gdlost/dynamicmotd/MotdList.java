package com.github.gdlost.dynamicmotd;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Random;

public class MotdList {

	/* Used for reference the main class */
	JavaPlugin plugin;

	/* Random object*/
	Random randomNo;

	/* By default enableRandom is false.
	 * The plugin choose MOTD in descendant order. */
	boolean enableRandom = false;

	/* index of current motd */
	int index = 0;

	/* length of list of motd */
	int motdLen = 0;

	/* List of all motds written in the config file */
	List<String> list;

	/* if motd list has no elements, then, the motd is the default of server. */
	String defaultMotd;

	/*--*/
	public MotdList(JavaPlugin plugin, String defaultServerMotd) {
		this.plugin = plugin;
		this.defaultMotd = defaultServerMotd;
		this.execWhenReload();

	}

	private void execWhenReload() {
		list = plugin.getConfig().getStringList("MotdList");
		motdLen = list.size();

		if (plugin.getConfig().getBoolean("RandomMotd")) {
			enableRandom = true;
			randomNo = new Random();
		}
	}

	public String getNextMotd() {
		if (index == this.motdLen) {
			index = 0;
		}
		if (enableRandom) {
			index = randomNo.nextInt(motdLen);
			return ChatColor.translateAlternateColorCodes('&', list.get(index));
		}

		String m = ChatColor.translateAlternateColorCodes('&', list.get(index));
		index++;
		return m;

	}

	public String getMotd()
	{
		return getNextMotd();
	}
}
