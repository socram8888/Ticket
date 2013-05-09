
package org.s4x8.bukkit.ticket.chip;

import org.tal.redstonechips.circuit.CircuitIndex;
import org.tal.redstonechips.RedstoneChips;

public class TicketChipIndex implements CircuitIndex {
	public String getIndexName() {
		return "Ticket";
	};
	
	public String getVersion() {
		return "1.0";
	};

	public Class[] getCircuitClasses() {
		return new Class[] { ticket.class };
	};
	
	public void onRedstoneChipsEnable(RedstoneChips rc) {
		// Hi!
	};
};
