
package org.s4x8.bukkit;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.Server;

import org.s4x8.util.ArrayJoiner;

import java.util.ArrayList;
import java.lang.reflect.Method;

public abstract class BaseCommand extends Command implements PluginIdentifiableCommand {
	protected Plugin plugin;
	protected Permission permission;

	protected BaseCommand(String name, String description, String usage, Permission permission, Plugin plugin) {
		super(
			name,
			description,
			usage,
			new ArrayList()
		);
		this.plugin = plugin;
		this.permission = permission;
		setPermission(permission.getName());
	};

	private String[] parseArguments(String raw) {
		StringBuilder sb = new StringBuilder();
		ArrayList<String> args = new ArrayList<String>();
		boolean quote = false;
		
		for (int i = 0; i < raw.length(); i++) {
			char cur = raw.charAt(i);
			boolean feed = false;
			if (cur == '"') {
				feed = quote | (sb.length() > 0);
				quote = !quote;
			} else if (cur == ' ' && !quote) {
				feed = (sb.length() > 0);
			} else {
				sb.append(cur);
			};
			if (feed) {
				args.add(sb.toString());
				sb.setLength(0);
			};
		};
		if (sb.length() > 0) {
			args.add(sb.toString());
		};
		
		String[] argsArray = new String[args.size()];
		args.toArray(argsArray);
		return argsArray;
	};

	private String[] fixArgs(String[] orig) {
		String raw = ArrayJoiner.join(orig, " ");
		return parseArguments(raw);
	};
		
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		realExecute(sender, fixArgs(args));
		return true;
	};

	protected void sendError(CommandSender sender) {
		sender.sendMessage(ChatColor.RED + "Invalid command usage. See /help " + getName() + " for more info");
		return;
	};
	
	protected void sendError(CommandSender sender, String error) {
		sender.sendMessage(ChatColor.RED + error);
	};
	
	protected void sendSuccess(CommandSender sender, String message) {
		sender.sendMessage(ChatColor.GREEN + message);
	};
	
	public void register() {
		boolean success = true;
		Server server = plugin.getServer();

		CommandMap commandMap = null;
		try {
			Method commandMapGetter = server.getClass().getDeclaredMethod("getCommandMap", new Class[0]);
			commandMap = (CommandMap) commandMapGetter.invoke(server);
		} catch (Exception e) {
			System.out.println("Exception!");
			success = false;
		};
		
		if (success) {
			String fallbackPrefix = plugin.getDescription().getName().replace(" ", "").toLowerCase();
			success = commandMap.register(fallbackPrefix, this);
		};
		
		if (success) {
			server.getPluginManager().addPermission(permission);
		} else {
			getPlugin().getLogger().severe("Unable to register command " + getName());
		};
	};
	
	public void unregister() {
		plugin.getServer().getPluginManager().removePermission(permission);
	};

	public abstract void realExecute(CommandSender sender, String[] args);

	public Plugin getPlugin() {
		return this.plugin;
	};
};
