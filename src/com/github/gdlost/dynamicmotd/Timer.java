package com.github.gdlost.dynamicmotd;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class Timer {

	JavaPlugin plugin;
	int seconds, seconds_copy;

	BukkitTask thread;
	TimerAction action;

	public Timer(JavaPlugin plugin, int seconds) {
		this.plugin = plugin;
		this.seconds = seconds;
		this.seconds_copy = seconds;
	}

	public final void init() throws TimerNullActionException {
		if (action == null)
		{
			throw new TimerNullActionException("please call Timer.registerAction method before!");
		}
		plugin.getServer().broadcastMessage("Init timer");
		thread = new BukkitRunnable() {

			@Override
			public void run() {
				if (seconds-- <= 0) {
					action.executeAction();
					seconds = seconds_copy;
				}
			}
		}.runTaskTimer(this.plugin, 0L, 20L);
	}

	public void registerAction(TimerAction action)
	{
		this.action = action;
	}

	public void stop() {
		thread.cancel();
	}
}
