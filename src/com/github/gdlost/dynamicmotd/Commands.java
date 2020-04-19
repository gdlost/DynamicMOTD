package com.github.gdlost.dynamicmotd;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.StringJoiner;

public class Commands implements CommandExecutor {
	JavaPlugin plugin;
	public Commands(JavaPlugin plugin) {
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		if (label.equalsIgnoreCase("dynamicmotd") && commandSender.hasPermission("dynamicmotd.global")) {
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
					else if (args[0].equalsIgnoreCase("list")) {
						commandSender.sendMessage(ChatColor.GREEN + "DynamicMOTD - MOTD List:");
						for (String s : ServerList.mlist.getCurrentMotdList()) {
							commandSender.sendMessage("- " + ChatColor.translateAlternateColorCodes('&', s));
						}
					}
					else if (args[0].equalsIgnoreCase("listt")) {

						List<String> tempMotd = ServerList.mlist.getForNowMotdList();
						if (tempMotd == null) {
							commandSender.sendMessage(ChatColor.RED + "Temporary MOTDs list is empty");
							return true;
						}
						commandSender.sendMessage(ChatColor.GREEN + "DynamicMOTD - MOTD List:");
						for (String s : ServerList.mlist.getForNowMotdList()) {
							commandSender.sendMessage("- " + ChatColor.translateAlternateColorCodes('&', s));
						}
					}
					else if (args[0].equalsIgnoreCase("listp")) {
						commandSender.sendMessage(ChatColor.GREEN + "DynamicMOTD - Saved MOTD List:");
						for (String s : ServerList.mlist.getFileMotdList()) {
							commandSender.sendMessage("- " + ChatColor.translateAlternateColorCodes('&', s));
						}
					}
					return true;
				default:
					if (args[0].equalsIgnoreCase("add")
						|| args[0].equalsIgnoreCase("addt")) {
						StringJoiner motdString = new StringJoiner(" ");
						for(int i = 1; i < args.length; i++)
						{
							motdString.add(args[i]);
						}
						if (args[0].equalsIgnoreCase("add")) {
							ServerList.mlist.addMotd(motdString.toString());
							commandSender.sendMessage(ChatColor.GREEN + "New motd added: " +
									ChatColor.translateAlternateColorCodes('&', motdString.toString()));
						}
						else {
							if (ServerList.mlist.addMotdForNow(motdString.toString())) {
								commandSender.sendMessage(ChatColor.GREEN + "New motd added: " +
										ChatColor.translateAlternateColorCodes('&', motdString.toString()));
								commandSender.sendMessage(ChatColor.RED + "> Only available for this session! " +
										"(or until the plugin reload)");
							} else commandSender.sendMessage(ChatColor.RED +
									"Error: You can't add more temporary motds, max reached");
						}

					}
					else /* if no arguments specified */
						commandSender.sendMessage(ChatColor.GOLD + "Please provide correct arguments. " +
								"See /dynamicmotd help");
					return true;
			}
		}
			return  true;
		}
	}

	private void showUserHelp(CommandSender s) {
		s.sendMessage(ChatColor.GREEN + "DynamicMOTD arguments:");
		s.sendMessage(__command("add <Insert MOTD here>", "Add a new MOTD to the list"));
		s.sendMessage(__command("addt <Insert MOTD here>", "Add a new MOTD to the list temporary"));
		s.sendMessage(__command("list", "List all MOTDs"));
		s.sendMessage(__command("listt", "List all temporary MOTDs"));
		s.sendMessage(__command("listp", "List all permanent MOTDs"));
		s.sendMessage(__command("reload", "Reloads plugin configuration"));
		s.sendMessage(__command("help", "Shows this help text"));
	}
	private String __command(String command, String description)
	{
		return "- " + ChatColor.GOLD + command + ChatColor.WHITE + " : " + description;
	}
}
