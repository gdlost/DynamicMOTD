package com.github.gdlost.dynamicmotd;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Random;

public class MotdList {

	/* Used for reference the main class */
	private JavaPlugin plugin;

	/* Random object*/
	private Random randomNo;

	/* By default enableRandom is false.
	 * The plugin choose MOTD in descendant order. */
	private boolean enableRandom = false;

	private Timer timer;
	private TimerAction timerAction;

	private boolean enableChangeAfterTime = false;

	/* index of current motd */
	private int index = 0;

	/* length of list of motd */
	private int motdLen = 0;

	/* List of all motds written in the config file */
	private List<String> list;

	/* if motd list has no elements, then, the motd is the default of server. */
	private String defaultMotd;

	/*--*/
	public MotdList(JavaPlugin plugin, String defaultServerMotd) {
		this.plugin = plugin;
		this.defaultMotd = defaultServerMotd;
		this.execWhenReload();

	}

	public void execWhenReload() {

		/* if task is running, stop it
		* enableChangeAfterTime is used, because will only be true if
		* timer will used, and, after that, timerAction will be set */
		if (enableChangeAfterTime) {
			timer.stop();
		}

		/* Get configs */
		list = plugin.getConfig().getStringList("MotdList");
		motdLen = list.size();

		/* if list motd is reduced, and the last motd displayed
		is in a higher index than the length of motd list, then,
		an error will occurs, is better prevent that error, no?:)
		 */
		verifyListIndex();

		if (motdLen == 0)
		{
			list.add(defaultMotd);
		}

		if (plugin.getConfig().getBoolean("RandomMotd")) {
			enableRandom = true;
			randomNo = new Random();
		}

		if (plugin.getConfig().getBoolean("ChangeAfterTime")) {
			enableChangeAfterTime = true;
			timer = new Timer(this.plugin, plugin.getConfig().getInt("Time"));
			timerAction = new TimerAction() {
				@Override
				public void executeAction() {
					verifyListIndex();
					if (enableRandom) {
						index = randomNo.nextInt(motdLen);
					}
					index++;
				}
			};
			timer.registerAction(timerAction);
			try {
				timer.init();
			} catch (TimerNullActionException e) {
				e.printStackTrace();
			}
		}
	}

	/* Add new motd to the list, and save to
	the config.yml file */
	public void addMotd(String motd) {
		list.add(motd);
		motdLen = list.size();
		plugin.getConfig().set("MotdList", list);
		plugin.saveConfig();
	}

	/* Add new motd to the list, but without
	saving to the config.yml file */
	public void addMotdForNow(String motd) {
		list.add(motd);
		motdLen = list.size();
	}

	/* Check if index is equal to list length,
	 * avoiding java exception.
	 *
	 * greater or equal, because if motd list length before
	 * doing a reload of plugin config is greater than the
	 * new length, will cause a exception.*/
	public void verifyListIndex() {
		if (index >= this.motdLen) {
			index = 0;
		}
	}

	public String getRandomMotd() {
		index = randomNo.nextInt(motdLen);
		return ChatColor.translateAlternateColorCodes('&', list.get(index));
	}

	/* if motd change after each request */
	public String getReqMotd() {
		verifyListIndex();

		if (enableRandom) {
			return getRandomMotd();
		}

		String m = ChatColor.translateAlternateColorCodes('&', list.get(index));
		index++;
		return m;

	}
	/* if motd change after x seconds */
	public String getTimerMotd() {
		verifyListIndex();
		/* in action method of TimerAction,
		 * chooses a random index, if is enabled
		 */
		return ChatColor.translateAlternateColorCodes('&', list.get(index));
	}

	public String getMotd() {
		if (enableChangeAfterTime)
		{
			return getTimerMotd();
		}
		return getReqMotd();
	}

	public int getMotdLen() {
		return list.size();
	}


}
