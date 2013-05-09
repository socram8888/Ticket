
package org.s4x8.bukkit.ticket.chip;

import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Location;

import org.s4x8.bukkit.ticket.TicketStack;

import org.tal.redstonechips.circuit.Circuit;

import java.util.HashMap;
import java.util.Map;

public class ticket extends Circuit {
	private String ticketEvent;
	private String ticketEmitter;
	private boolean outputLevel;
	private TicketChipInteractListener listener;
	
	public void inputChange(int index, boolean state) {
		// Hi!
	};

	public boolean init(CommandSender sender, String[] args) {
		if (inputs.length != 0) {
			error(sender, "This chip does not need any input");
			return false;
		};
		
		if (outputs.length == 0) {
			error(sender, "This chip needs at least one output");
			return false;
		};
		
		if (interfaceBlocks.length == 0) {
			error(sender, "This chip needs at least one interface");
			return false;
		};
		
		ticketEvent = args[0];
		if (sender instanceof Player) {
			ticketEmitter = ((Player) sender).getName();
		} else {
			ticketEmitter = "UNKNOWN";
		};

		listener = new TicketChipInteractListener(this);
		
		return true;
	};
	
	public void circuitShutdown() {
		listener.unregister();
	};
	
	public boolean isStateless() {
		return false;
	};
	
	public Map<String, String> getInternalState() {
		HashMap state = new HashMap<String,String>();
		state.put("event", ticketEvent);
		state.put("emitter", ticketEmitter);
		return state;
	};
	
	public void setInternalState(Map<String, String> state) {
		ticketEvent = state.get("event");
		if (ticketEvent == null) ticketEvent = "";
		ticketEmitter = state.get("emitter");
		if (ticketEmitter == null) ticketEmitter = "UNKNOWN";
	};
	
	public boolean isInterfaceBlock(Block block) {
		if (block == null) return false;
		Location location = block.getLocation();
		for (int i = 0; i < interfaceBlocks.length; i++) {
			if (interfaceBlocks[i].getLocation().equals(location)) return true;
		};
		return false;
	};
	
	private void setAllOutputs(boolean outputLevel) {
		if (this.outputLevel != outputLevel) {
			for (int i = 0; i < outputs.length; i++) {
				outputs[i].setState(outputLevel);
			};
			this.outputLevel = outputLevel;
		};
	};
	
	public void startOutput() {
		setAllOutputs(true);
		new TicketChipStopTask(this);
	};
	
	public void stopOutput() {
		setAllOutputs(false);
	};
	
	public boolean isOutputHigh() {
		return outputLevel;
	};
	
	public TicketStack getTicket() {
		return new TicketStack(ticketEvent, ticketEmitter);
	};
};
