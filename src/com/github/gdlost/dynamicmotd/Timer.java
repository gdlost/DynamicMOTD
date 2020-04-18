package com.github.gdlost.dynamicmotd;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class Timer {
	/* Timer class
	   The purpose of this, is make a countdown, until X seconds,
	   then, the Timer, executes a TimerAction, and, the counter
	   is set to its initial value.
	 */
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
