package fr.entasia.reportsql.commands.mod;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.entasia.reportsql.core.ReportManager;

public class ReportListCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player){
			Player p = (Player) sender;
			if(p.hasPermission("mod.report.list")){
				try {
					//Open menu here
					//Inventory inv = RSQLCore.getMenuCreator().createInv(size, name, data);
					//sender.sendMessage("§cLa liste des signalement n'est pas encore implémentée !");
					p.openInventory(ReportManager.getPageFrom(0, ReportManager.getReports()));
				} catch (Exception e) {
					p.sendMessage("§cUne erreur interne est survenue ! Voici le message correspondant : "+e.getMessage()
						+" (Voir logs pour erreur complète)");
					e.printStackTrace();
				}
			
			} else {
				p.sendMessage("§4Vous n'avez pas la permission d'utiliser cette commande !");
			}
			
		} else {
			Bukkit.getConsoleSender().sendMessage("§cYou need to be connected on Minecraft server while sending this command!");
		}
		return false;
	}

}
