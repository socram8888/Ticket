
package org.s4x8.bukkit.ticket.chip;

import org.tal.redstonechips.RedstoneChips;

public class TicketChipStopTask implements Runnable {
	private ticket chip;
	
	public TicketChipStopTask(ticket chip) {
		RedstoneChips redstoneChips = chip.getPlugin();
		redstoneChips.getServer().getScheduler().scheduleSyncDelayedTask(redstoneChips, this, 20L);
		this.chip = chip;
	};
	
	public void run() {
		chip.stopOutput();
	};
};
