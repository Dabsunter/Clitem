package fr.dabsunter.clitem.api.event;

import org.bukkit.entity.Player;

import fr.dabsunter.clitem.ClickableItem;

public class InventoryEvent {
	
	private Player player;
	private ClickableItem item;
	
	public InventoryEvent(Player player, ClickableItem item) {
		this.player = player;
		this.item = item;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public ClickableItem getItem() {
		return item;
	}

}
