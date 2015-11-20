package fr.dabsunter.clitem.inventory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import fr.dabsunter.clitem.ClickableItem;

public class ClickableInventory {
	
	private static Set<ClickableInventory> clickableInventories = new HashSet<ClickableInventory>();
	
	public static ClickableInventory get(String name) {
		for(ClickableInventory ci : clickableInventories) {
			if(ci.getName() == name) return ci;
		}
		return null;
	}
	
	public static Set<ClickableInventory> getAll() {
		return clickableInventories;
	}
	
	
	private String name;
	private InventoryType type;
	private int size;
	private Map<Integer, ClickableItem> invMap = new HashMap<Integer, ClickableItem>();
	private Player owner;
	
	public ClickableInventory(String name, int size) {
		this.name = name;
		this.size = size;
		type = InventoryType.CHEST;
		clickableInventories.add(this);
	}
	
	public ClickableInventory(String name, InventoryType type) {
		this.name = name;
		this.type = type;
		size = 27;
		clickableInventories.add(this);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setType(InventoryType type) {
		this.type = type;
	}

	public InventoryType getType() {
		return type;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public int getSize() {
		return size;
	}
	
	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	public Player getOwner() {
		return owner;
	}
	
	public boolean isPublic() {
		return (owner == null);
	}
	
	public void addItem(ClickableItem item) {
		for(int i = 0; i < size; i++) {
			if(!invMap.containsKey(i)) {
				invMap.put(i, item);
				return;
			}
		}
	}
	
	public void setItem(ClickableItem item, int slot) {
		invMap.put(slot, item);
	}
	
	public void setItem(ClickableItem item, int x, int y) {
		int slot = x+y*9;
		invMap.put(slot, item);
	}
	
	public void removeItem(int slot) {
		invMap.remove(slot);
	}
	
	public void removeItem(int x, int y) {
		int slot = x+y*9;
		invMap.remove(slot);
	}
	
	public void clearItems() {
		invMap.clear();
	}

	public boolean contains(ClickableItem item) {
		return invMap.containsValue(item);
	}
	
	public void open() {
		if(isPublic()) return;
		Inventory inv;
		if(type == InventoryType.CHEST) {
			inv = Bukkit.createInventory(owner, size, name);
		}else {
			inv = Bukkit.createInventory(owner, type, name);
		}
		
		for(int i = 0 ; i < size ; i++) {
			if(invMap.containsKey(i)) {
				inv.setItem(i, invMap.get(i).getItem());
			}
		}
		
		owner.openInventory(inv);
	}
	
	public void open(Player player) {
		if(!isPublic()) return;
		Inventory inv;
		if(type == InventoryType.CHEST) {
			inv = Bukkit.createInventory(player, size, name);
		}else{
			inv = Bukkit.createInventory(player, type, name);
		}
		
		for(int i = 0 ; i < size ; i++) {
			if(invMap.containsKey(i)) {
				inv.setItem(i, invMap.get(i).getItem());
			}
		}
		
		player.openInventory(inv);
	}
	
	public void destroy() {
		clickableInventories.remove(this);
	}
	
	public void destroyAll() {
		for(ClickableItem item : invMap.values()) {
			item.destroy();
		}
		destroy();
	}
	
	

}
