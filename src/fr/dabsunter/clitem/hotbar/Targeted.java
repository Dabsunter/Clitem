package fr.dabsunter.clitem.hotbar;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

public class Targeted {
	
	private String name;
	private Location loc;
	private Block block;
	private Entity entity;
	
	public Targeted(Entity entity) {
		this.name = entity.getName();
		this.loc = entity.getLocation();
		this.entity = entity;
	}
	
	public Targeted(Block block) {
		this.name = block.getType().toString();
		this.loc = block.getLocation();
		this.block = block;
	}
	
	public String getName() {
		return name;
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public Block getBlock() {
		return block;
	}
	
	public Entity getEntity() {
		return entity;
	}
	
	public boolean hasBlock() {
		return !block.equals(null);
	}
	
	public boolean hasEntity() {
		return !entity.equals(null);
	}
	
	public String getStringLocation() {
		return loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ();
	}
	
	public String getFullStringLocation() {
		return loc.getX() + " " + loc.getY() + " " + loc.getZ() + " " + loc.getYaw() + " " + loc.getPitch();
	}

}
