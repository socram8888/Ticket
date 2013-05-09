
package org.s4x8.bukkit.ticket;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;

import org.s4x8.bukkit.InvalidItemStackException;

import java.util.ArrayList;
import java.util.List;

public class TicketStack {
	private String event;
	private String emitter;
	private int amount = 1;
	
	private static final String ticketName = ChatColor.RESET.toString() + "Ticket";
	private static final String eventPrefix = ChatColor.GREEN.toString();
	private static final String emitterPrefix = ChatColor.RED.toString();

	public TicketStack() {
	
	};

	public TicketStack(int amount) {
		this.amount = amount;
	};
	
	public TicketStack(String event, String emitter) {
		this.event = event;
		this.emitter = emitter;
	};
	
	public TicketStack(String event, String emitter, int amount) {
		this.event = event;
		this.emitter = emitter;
		this.amount = amount;
	};

	public TicketStack(ItemStack itemStack) throws InvalidItemStackException {
		setItemStack(itemStack);
	};

	public boolean isMarked() {
		return event != null && emitter != null && event.length() != 0 && emitter.length() != 0;
	};

	public void mark(String event, String emitter) {
		this.event = event;
		this.emitter = emitter;
	};

	public void unmark() {
		this.event = null;
		this.emitter = null;
	};

	public ItemStack getItemStack() {
		if (amount == 0) return null;
		ItemStack stack = new ItemStack(Material.BED_BLOCK, amount);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(ticketName);
		if (isMarked()) {
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(eventPrefix + event);
			lore.add(emitterPrefix + emitter);
			meta.setLore(lore);
		};
		stack.setItemMeta(meta);
		return stack;
	};

	public void setItemStack(ItemStack itemStack) throws InvalidItemStackException {
		if (!importStack(itemStack)) {
			throw new InvalidItemStackException(itemStack);
		};
	};

	private boolean importStack(ItemStack itemStack) {
		event = null; emitter = null; amount = 0;
		
		if (itemStack == null) return true;
		if (itemStack.getType().equals(Material.AIR)) return true;
		if (!itemStack.getType().equals(Material.BED_BLOCK)) return false;

		ItemMeta meta = itemStack.getItemMeta();
		if (!meta.getDisplayName().equals(ticketName)) return false;
		
		amount = itemStack.getAmount();
		List<String> lore = meta.getLore();
		if (lore == null) return true;
		
		int loreCount = lore.size();
		if (loreCount == 0) return true;
		if (loreCount != 2) return false;
		
		String tempEvent = lore.get(0);
		if (!tempEvent.startsWith(eventPrefix)) return false;
		event = tempEvent.substring(eventPrefix.length());
		
		String tempEmitter = lore.get(1);
		if (!tempEmitter.startsWith(emitterPrefix)) return false;
		emitter = tempEmitter.substring(emitterPrefix.length());
		
		return true;
	};
	
	public String getEvent() {
		return event;
	};
	
	public void setEvent(String event) {
		this.event = event;
	};
	
	public String getEmitter() {
		return emitter;
	};
	
	public void setEmitter(String emitter) {
		this.emitter = emitter;
	};
	
	public int getAmount() {
		return amount;
	};
	
	public void setAmount(int amount) {
		this.amount = amount;
	};
};
