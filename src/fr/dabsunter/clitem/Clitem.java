package fr.dabsunter.clitem;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Clitem extends JavaPlugin {
	
	public static Plugin plugin;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		
		plugin = this;
		
		getCommand("clitem").setExecutor(new ClitemCommandExecutor());
		
		loadItems();
		
		getLogger().info("Clitem successful loaded!");
	}
	
	@Override
	public void onDisable() {
		getLogger().info("Disabling Clitem...");
	}
	
	public void loadItems() {
		for(String name : getConfig().getConfigurationSection("items").getKeys(false)) {
			ConfigurationSection itemCfg = getConfig().getConfigurationSection("items." + name);
			ClickableItem ci = new ClickableItem(name, itemCfg.getItemStack("item", new ItemStack(Material.REDSTONE)));
			for(String event : itemCfg.getConfigurationSection("event").getKeys(false)) {
				String command = itemCfg.getString("event." + event);
				ci.addClickAction(ClickType.valueOf(event.toUpperCase()), new ClickAction(ci, command));
			}
		}
	}

}
