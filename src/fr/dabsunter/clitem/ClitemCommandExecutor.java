package fr.dabsunter.clitem;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClitemCommandExecutor implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		//sender.sendMessage("args.length=" + args.length);
		switch(args.length) {
		case 0:
			helpCommand(sender);
			return true;
		case 1:
			if(args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
				helpCommand(sender);
			}else if(args[0].equalsIgnoreCase("give")) {
				helpCommand(sender, "give");
			}
			break;
		case 2:
			if(args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
				helpCommand(sender, args[1]);
			}else if(args[0].equalsIgnoreCase("give")) {
				giveCommand(sender, args[1]);
			}
			break;
		case 3:
			if(args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
				helpCommand(sender, args[1]);
			}else if(args[0].equalsIgnoreCase("give")) {
				giveCommand(sender, args[1], args[2]);
			}
			break;
		}
		
		return false;
	}
	
	private void helpCommand(CommandSender sender) {
		sender.sendMessage(new String[] {
				"",
				""
		});
	}
	
	private void helpCommand(CommandSender sender, String arg) {
		
	}
	
	private void giveCommand(CommandSender sender, String item) {
		ClickableItem ci = ClickableItem.get(item);
		if(ci != null) {
			ci.give((Player) sender);
		} else {
			sender.sendMessage("'" + item + "' n'existe pas!");
		}
	}
	
	private void giveCommand(CommandSender sender, String item, String name) {
		ClickableItem ci = ClickableItem.get(item);
		if(ci != null) {
			Player player = Bukkit.getPlayer(name);
			if(player != null && player.isOnline()) {
				ci.give(player);
				sender.sendMessage("L'item '" + item + "' a bien été ajouté à l'inventaire de " + player.getName());
			} else {
				sender.sendMessage(name + " n'est pas connecté!");
			}
		} else {
			sender.sendMessage("L'item '" + item + "' n'existe pas!");
		}
	}

}
