package fr.entasia.reportsql.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.entasia.reportsql.core.RSQLCore;


public class ReportCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(label.equalsIgnoreCase("debug")) {
			if(sender instanceof Player) {
				Player dev = (Player) sender;
				if(args.length > 0) {
					try {
						int index = Integer.parseInt(args[0]);
						RSQLCore.getMenus().get(index).openMenu(dev, dev, "§8Menu d'aperçu");
					} catch(NumberFormatException e) {
						dev.sendMessage("§cLe menu \""+args[0]+"\" n'existe pas.");
					}
					
				}
				
			}
			return false;
			
		} else {
			if(sender instanceof Player){
				Player reporter = (Player) sender;
				if(args.length > 0){
					OfflinePlayer target = RSQLCore.getOfflinePlayer(args[0]);
					
					if(target != null) {
						if(target.getName().equals(reporter.getName())) {
							sender.sendMessage("§cVous ne pouvez pas vous signaler vous-même !");
						} else {
							/**if(Core.hasCooldown(reporter, target)){
								ReportCooldown rc = core.getReportCooldown(reporter, reported);
								p.sendMessage("§cVous devez attendre encore "+rc.getTime()+" avant de pouvoir signaler a nouveau ce joueur !");
							}**/
							RSQLCore.getMenus().get(0).openMenu(reporter, target, "§8Signaler §4"+target.getName());
						}
						
					} else {
						reporter.sendMessage("§cLe joueur \""+args[0]+"\" n'existe pas !");
					}
						
				} else {
					reporter.sendMessage("§cVous devez ajouter le joueur que vous voulez signaler !");
				}
				
			} else {
				Bukkit.getConsoleSender().sendMessage("§cYou need to be connected on Minecraft server while sending this command!");
			}
			return true;
		}

	}
	
}