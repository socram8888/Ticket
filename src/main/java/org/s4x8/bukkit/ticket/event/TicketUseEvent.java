
package org.s4x8.bukkit.ticket.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import org.s4x8.bukkit.ticket.chip.ticket;

public class TicketUseEvent extends PlayerEvent implements Cancellable {
	private static HandlerList handlers = new HandlerList();

	private ticket chip;
	private boolean cancelled = false;
	
	public TicketUseEvent(Player player, ticket chip) {
		super(player);
		this.chip = chip;
	};
	
	public ticket getChip() {
		return chip;
	};
	
	public boolean isCancelled() {
		return cancelled;
	};
	
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	};
	
	public HandlerList getHandlers() {
		return handlers;
	};

	public static HandlerList getHandlerList() {
		return handlers;
	};
};
