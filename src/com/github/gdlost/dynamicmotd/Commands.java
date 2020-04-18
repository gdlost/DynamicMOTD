package com.github.gdlost.dynamicmotd;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Commands implements CommandExecutor {
	JavaPlugin plugin;
	public Commands(JavaPlugin plugin) {
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		if (label.equalsIgnoreCase("dynamicmotd")) {
			switch (args.length) {
				case 0:
					commandSender.sendMessage(ChatColor.GOLD + "DynamicMotd 1.0, by gdlost");
					return true;
				case 1:
					if(args[0].equalsIgnoreCase("help")) {
						showUserHelp(commandSender);
					}
					else if(args[0].equalsIgnoreCase("reload")
							|| args[0].equalsIgnoreCase("rl")) {

						plugin.reloadConfig();
						ServerList.mlist.execWhenReload();
						commandSender.sendMessage(ChatColor.GOLD + "DynamicMotd: " + ChatColor.WHITE
								 + "Config reloaded.");
						commandSender.sendMessage(ChatColor.AQUA + Integer.toString(ServerList.mlist.getMotdLen()) +
								" MOTDs found");
					}
					else if (args[0].equalsIgnoreCase("add")) {
						commandSender.sendMessage(ChatColor.RED + "Usage: " + ChatColor.BLUE +
								"/dynamicmotd add This is a new motd text");
					}
					return true;
				default:
					if (args[0].equalsIgnoreCase("add")
						|| args[0].equalsIgnoreCase("addt")) {
						StringBuilder stringBuilder = new StringBuilder();
						for(int i = 1; i < args.length; i++)
						{
							stringBuilder.append(args[i]);
						}
						if (args[0].equalsIgnoreCase("add")) {
							ServerList.mlist.addMotd(stringBuilder.toString());
							commandSender.sendMessage(ChatColor.GREEN + "New motd added: " +
									ChatColor.translateAlternateColorCodes('&', stringBuilder.toString()));
						}
						else {
							ServerList.mlist.addMotdForNow(stringBuilder.toString());
							commandSender.sendMessage(ChatColor.GREEN + "New motd added: " +
									ChatColor.translateAlternateColorCodes('&', stringBuilder.toString()));
							commandSender.sendMessage(ChatColor.RED + "> Only available for this session! " +
									"(or until the plugin reload)");
						}

					}
					return true;
			}
		}
		return false;
	}

	private void showUserHelp(CommandSender s) {
		s.sendMessage(ChatColor.GREEN + "DynamicMOTD arguments:");
		s.sendMessage(__command("add <Insert MOTD here>", "Add a new MOTD to the list"));
		s.sendMessage(__command("addt <Insert MOTD here>", "Add a new MOTD to the list temporary"));
		s.sendMessage(__command("reload", "Reloads plugin configuration"));
		s.sendMessage(__command("help", "Shows this help text"));
	}
	private String __command(String command, String description)
	{
		return "- " + ChatColor.GOLD + command + ChatColor.WHITE + " : " + description;
	}
}
