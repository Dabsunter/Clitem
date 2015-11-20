package fr.dabsunter.clitem;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;

import fr.dabsunter.clitem.api.ClickListener;
import fr.dabsunter.clitem.api.event.HotbarEvent;
import fr.dabsunter.clitem.api.event.InventoryEvent;
import fr.dabsunter.clitem.hotbar.Targeted;

public class ClickAction {
	
	private String command;
	private ClickableItem item;
	private Set<ClickListener> listeners = new HashSet<ClickListener>();
	
	public ClickAction(ClickableItem item) {
		this.item = item;
	}
	
	public ClickAction(ClickableItem item, String command) {
		this.command = command;
		this.item = item;
		
	}
	
	public void addListener(ClickListener listener) {
		listeners.add(listener);
	}
	
	public void removeListener(ClickListener listener) {
		listeners.remove(listener);
	}
	
	public void run(Player sender, Targeted target) {
		if(command != null) {
			sender.performCommand(mkCommand(target));
		}
		if(!listeners.isEmpty()) {
			HotbarEvent event = new HotbarEvent(sender, item, target);
			dispatchEvent(event, item.getClickType(this));
		}
		
	}
	
	public void run(Player sender) {
		if(command != null) {
			sender.performCommand(command);
		}
		if(!listeners.isEmpty()) {
			ClickType type = item.getClickType(this);
			if(type.name().contains("INVENTORY")) {
				InventoryEvent event = new InventoryEvent(sender, item);
				dispatchEvent(event, type);
			}else{
				HotbarEvent event = new HotbarEvent(sender, item);
				dispatchEvent(event, type);
			}
		}
	}
	
	public void dispatchEvent(HotbarEvent e, ClickType type) {
		switch(type) {
		case CLICK:
			click(e);
			break;
		case LEFT_CLICK:
			leftClick(e);
			break;
		case LEFT_CLICK_BLOCK:
			leftClickBlock(e);
			break;
		case LEFT_CLICK_ENTITY:
			leftClickEntity(e);
			break;
		case RIGHT_CLICK:
			rightClick(e);
			break;
		case RIGHT_CLICK_BLOCK:
			rightClickBlock(e);
			break;
		case RIGHT_CLICK_ENTITY:
			rightClickEntity(e);
			break;
		default:
			break;
		}
	}
	
	public void dispatchEvent(InventoryEvent e, ClickType type) {
		switch(type) {
		case INVENTORY_CLICK:
			invClick(e);
			break;
		case INVENTORY_LEFT_CLICK:
			invLeftClick(e);
			break;
		case INVENTORY_RIGHT_CLICK:
			invRightClick(e);
			break;
		default:
			break;
		}
	}
	
	private String mkCommand(Targeted target) {
		String cmd = command;
		cmd = cmd.replace("%CLICKED%", target.getName());
		cmd = cmd.replace("%CLICKED_LOC%", target.getStringLocation());
		cmd = cmd.replace("%CLICKED_LOCATION%", target.getFullStringLocation());
		return cmd;
	}
	
	private void click(HotbarEvent e) {
		for(ClickListener listener : listeners) {
			listener.onClick(e);
		}
	}
	
	private void invClick(InventoryEvent e) {
		for(ClickListener listener : listeners) {
			listener.onInventoryClick(e);
		}
	}
	
	private void invLeftClick(InventoryEvent e) {
		for(ClickListener listener : listeners) {
			listener.onInventoryLeftClick(e);
		}
	}
	
	private void invRightClick(InventoryEvent e) {
		for(ClickListener listener : listeners) {
			listener.onInventoryRightClick(e);
		}
	}
	
	private void leftClick(HotbarEvent e) {
		for(ClickListener listener : listeners) {
			listener.onLeftClick(e);
		}
	}
	
	private void leftClickBlock(HotbarEvent e) {
		for(ClickListener listener : listeners) {
			listener.onLeftClickBlock(e);
		}
	}
	
	private void leftClickEntity(HotbarEvent e) {
		for(ClickListener listener : listeners) {
			listener.onLeftClickEntity(e);
		}
	}
	
	private void rightClick(HotbarEvent e) {
		for(ClickListener listener : listeners) {
			listener.onRightClick(e);
		}
	}
	
	private void rightClickBlock(HotbarEvent e) {
		for(ClickListener listener : listeners) {
			listener.onRightClickBlock(e);
		}
	}
	
	private void rightClickEntity(HotbarEvent e) {
		for(ClickListener listener : listeners) {
			listener.onRightClickEntity(e);
		}
	}

}
