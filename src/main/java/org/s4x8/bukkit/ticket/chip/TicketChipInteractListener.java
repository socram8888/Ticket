
package org.s4x8.bukkit.ticket.chip;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import org.s4x8.bukkit.ticket.event.TicketUseEvent;
import org.s4x8.bukkit.ticket.TicketStack;
import org.s4x8.bukkit.InvalidItemStackException;

import org.tal.redstonechips.RedstoneChips;

public class TicketChipInteractListener implements Listener {
	private ticket chip;
	
	public TicketChipInteractListener(ticket chip) {
		RedstoneChips redstoneChips = chip.getPlugin();
		redstoneChips.getServer().getPluginManager().registerEvents(this, redstoneChips);
		this.chip = chip;
	};

	public void unregister() {
		HandlerList.unregisterAll(this);
	};

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent interactEvent) {
		if (!chip.isInterfaceBlock(interactEvent.getClickedBlock())) return;
		
		Player player = interactEvent.getPlayer();
		TicketStack playerTicket = null;
		boolean error = false;
		try {
			playerTicket = new TicketStack(player.getItemInHand());
			if (playerTicket.getAmount() == 0) error = true;
		} catch (InvalidItemStackException ex) {
			error = true;
		};
		if (error) {
			chip.error(player, "Sooo... Where's your ticket?");
			return;
		};
		
		TicketStack chipTicket = chip.getTicket();
		if (!chipTicket.getEvent().equalsIgnoreCase(playerTicket.getEvent())) {
			chip.error(player, "That ticket is not for this event!");
			return;
		};
		
		if (!chipTicket.getEmitter().equalsIgnoreCase(playerTicket.getEmitter())) {
			chip.error(player, "That's a fake ticket! I'm calling the security guards!");
			return;
		};
		
		if (chip.isOutputHigh()) {
			chip.info(player, "Please wait a few moments and try again!");
			return;
		};
		
		TicketUseEvent useEvent = new TicketUseEvent(player, chip);
		chip.getPlugin().getServer().getPluginManager().callEvent(useEvent);
		if (useEvent.isCancelled()) return;
		
		playerTicket.setAmount(playerTicket.getAmount() - 1);
		player.setItemInHand(playerTicket.getItemStack());
		
		chip.startOutput();
		chip.info(player, "Valid ticket");
	};
};
