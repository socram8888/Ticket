
package org.s4x8.bukkit.ticket;

import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.Material;

public class TicketBuildBlocker implements Listener {
	public TicketBuildBlocker(TicketPlugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	};

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if (event.getItemInHand().getType().equals(Material.BED_BLOCK)) {
			event.setBuild(false);
		};
	};
};
