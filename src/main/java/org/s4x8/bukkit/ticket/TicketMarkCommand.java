
package org.s4x8.bukkit.ticket;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission; 
import org.bukkit.permissions.PermissionDefault;

import org.s4x8.bukkit.PlayerCommand;
import org.s4x8.bukkit.InvalidItemStackException;

public class TicketMarkCommand extends PlayerCommand {
	public TicketMarkCommand(TicketPlugin plugin) {
		super(
			"ticketmark",
			"Marks a ticket",
			"<event>",
			new Permission("ticket.mark", "Allows users to mark clean tickets", PermissionDefault.TRUE),
			plugin
		);
	};

	public void realExecute(Player player, String[] args) {
		if (args.length != 1) {
			sendError(player);
			return;
		};

		String event = args[0];
		String emitter = player.getName();

		ItemStack itemInHand = player.getItemInHand();
		TicketStack ticket;

		try {
			ticket = new TicketStack(itemInHand);
		} catch (InvalidItemStackException e) {
			sendError(player, "Sooo... where's your ticket?");
			return;
		};

		if (ticket.isMarked()) {
			sendError(player, "This ticket is already marked!");
			return;
		};

		ticket.mark(event, emitter);
		player.setItemInHand(ticket.getItemStack());
		sendSuccess(player, "Ticket marked successfully");
	};
};
