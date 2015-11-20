package fr.dabsunter.clitem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import fr.dabsunter.clitem.hotbar.Targeted;

public class ClickableItem implements Listener {
	
	private static Set<ClickableItem> clickableItems = new HashSet<ClickableItem>();
	
	public static ClickableItem get(String name) {
		for(ClickableItem ci : clickableItems) {
			if(ci.getName() == name) return ci;
		}
		return null;
	}
	
	public static ClickableItem get(ItemStack item) {
		for(ClickableItem ci : clickableItems) {
			if(ci.getItem() == item) return ci;
		}
		return null;
	}
	
	public static Set<ClickableItem> getAll() {
		return clickableItems;
	}
	
	
	private String name;
	private ItemStack item;
	private Map<ClickType, ClickAction> actions = new HashMap<ClickType, ClickAction>();
	private boolean usePerms = false;
	private boolean cancelEvent = false;
	
	public ClickableItem(String name, ItemStack item) {
		this.name = name;
		this.item = item;
		Bukkit.getPluginManager().registerEvents(this, Clitem.plugin);
		clickableItems.add(this);
	}
	
	public String getName() {
		return name;
	}
	
	public ItemStack getItem() {
		return item;
	}
	
	public void setUsePerms(boolean usePerms) {
		this.usePerms = usePerms;
	}
	
	public boolean isUsingPerms() {
		return usePerms;
	}
	
	public void setCancelEvent(boolean cancelEvent) {
		this.cancelEvent = cancelEvent;
	}
	
	public boolean isCancelEvent() {
		return cancelEvent;
	}
	
	public ClickType getClickType(ClickAction action) {
		for(int i = 0; i < actions.size(); i++) {
			if(action == actions.values().toArray()[i]) {
				return (ClickType) actions.keySet().toArray()[i];
			}
		}
		return null;
	}
	
	public void addClickAction(ClickType eventId, ClickAction action) {
		actions.put(eventId, action);
	}
	
	public void removeClickAction(ClickType eventId) {
		actions.remove(eventId);
	}
	
	public ClickAction getClickAction(ClickType eventId) {
		if(actions.containsKey(eventId)) {
			return actions.get(eventId);
		}else{
			return null;
		}
	}
	
	public void give(Player player) {
		player.getInventory().addItem(item);
	}
	
	public void destroy() {
		HandlerList.unregisterAll(this);
		clickableItems.remove(this);
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if(item.isSimilar(e.getItem())) {
			click(ClickType.CLICK, e.getPlayer());
			
			switch(e.getAction()) {
			case LEFT_CLICK_AIR:
				click(ClickType.LEFT_CLICK, e.getPlayer());
				break;
				
			case LEFT_CLICK_BLOCK:
				click(ClickType.LEFT_CLICK, e.getPlayer());
				click(ClickType.LEFT_CLICK_BLOCK, e.getPlayer(), new Targeted(e.getClickedBlock()));
				break;
				
			case RIGHT_CLICK_AIR:
				click(ClickType.RIGHT_CLICK, e.getPlayer());
				break;
				
			case RIGHT_CLICK_BLOCK:
				click(ClickType.RIGHT_CLICK, e.getPlayer());
				click(ClickType.RIGHT_CLICK_BLOCK, e.getPlayer(), new Targeted(e.getClickedBlock()));
				break;
				
			default:
				return;
			}

			if(cancelEvent) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
		if(item.isSimilar(e.getPlayer().getItemInHand())) {
			click(ClickType.RIGHT_CLICK_ENTITY, e.getPlayer(), new Targeted(e.getRightClicked()));
			
			if(cancelEvent) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player) {
			Player player = (Player) e.getDamager();
			if(item.isSimilar(player.getItemInHand())) {
				click(ClickType.LEFT_CLICK_ENTITY, player, new Targeted(e.getEntity()));
				
				if(cancelEvent) {
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if(item.isSimilar(e.getCurrentItem())) {
			click(ClickType.INVENTORY_CLICK, (Player) e.getWhoClicked());
			if(e.isLeftClick()) {
				click(ClickType.INVENTORY_LEFT_CLICK, (Player) e.getWhoClicked());
			}
			if(e.isRightClick()) {
				click(ClickType.INVENTORY_RIGHT_CLICK, (Player) e.getWhoClicked());
			}
			
			e.setCancelled(true);
		}
	}
	
	private void click(ClickType eventId, Player sender) {
		if(usePerms && !sender.hasPermission("clitem.use." + name)) {
			return;
		}
		if(actions.containsKey(eventId)) {
			actions.get(eventId).run(sender);
		}
	}
	
	private void click(ClickType eventId, Player sender, Targeted target) {
		if(usePerms && !sender.hasPermission("clitem.use." + name)) {
			return;
		}
		if(actions.containsKey(eventId)) {
			actions.get(eventId).run(sender, target);
		}
	}
	

}
