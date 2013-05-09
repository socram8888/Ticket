
package org.s4x8.bukkit;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;

public abstract class PlayerCommand extends BaseCommand {
	protected PlayerCommand(String name, String description, String usage, String permission, Plugin plugin) {
		super(
			name,
			description,
			usage,
			permission,
			plugin
		);
	};
	
	protected PlayerCommand(String name, String description, String usage, Permission permission, Plugin plugin) {
		super(
			name,
			description,
			usage,
			permission,
			plugin
		);
	};

	public void realExecute(CommandSender sender, String[] args) {
		if (!(sender instanceof Player)) {
			sendError(sender, "This command must be run as a player!");
			return;
		};
		realExecute((Player) sender, args);
	};
	
	public abstract void realExecute(Player player, String[] args);
};
