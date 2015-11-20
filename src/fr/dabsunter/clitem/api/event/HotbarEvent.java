package fr.dabsunter.clitem.api.event;

import org.bukkit.entity.Player;

import fr.dabsunter.clitem.ClickableItem;
import fr.dabsunter.clitem.hotbar.Targeted;

public class HotbarEvent {
	
	private Player player;
	private ClickableItem item;
	private Targeted targeted;
	
	public HotbarEvent(Player player, ClickableItem item) {
		this.player = player;
		this.item = item;
	}
	
	public HotbarEvent(Player player, ClickableItem item, Targeted targeted) {
		this.player = player;
		this.item = item;
		this.targeted = targeted;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public ClickableItem getItem() {
		return item;
	}
	
	public Targeted getTargeted() {
		return targeted;
	}
	
	public boolean hasTargeted() {
		return !targeted.equals(null);
	}

}
