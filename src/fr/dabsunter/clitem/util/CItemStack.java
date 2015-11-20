package fr.dabsunter.clitem.util;

import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CItemStack extends ItemStack {
	
	public void setDisplayName(String displayName) {
		ItemMeta meta = getItemMeta();
		meta.setDisplayName(displayName);
		setItemMeta(meta);
	}
	
	public void setLore(List<String> lore) {
		ItemMeta meta = getItemMeta();
		meta.setLore(lore);
		setItemMeta(meta);
	}

}
