package fr.entasia.reportsql.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReportHelpCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player){
			Player p = (Player) sender;
			String listHeader = "§bListe des commandes de signalement disponibles";
			
			if(p.hasPermission("mod.reportsql")) {
				p.sendMessage(listHeader+" §2(Les commandes en vert sont les commandes de modération) :");
				p.sendMessage("§2  - reportlist (Alias : rlist) :§a Ouvre le menu avec tous les signalements encore enregistrés.");
				p.sendMessage("§2  - reportsearch <filtres...> (Alias : rsearch) :§a Ouvre un menu contenant tous les signalements "
						+ "avec le(s) filtre(s) donnés en argument (Voir /reportfilters pour les filtres).");
				p.sendMessage("§2  - reportfilters (Alias : rfilters) :§aAffiche la liste des filtres de recherche disponibles.");
			} else {
				p.sendMessage(listHeader+" :");
			}
			p.sendMessage("§3  - report <joueur> :§b Ouvre le menu de sélection des raisons pour laquelle vous voulez signaler"
					+ " le joueur mis en arguement.");
			p.sendMessage("§3  - reporthelp (Alias : rhelp) :§b Affiche cette liste."); 
		} else {
			Bukkit.getConsoleSender().sendMessage("§cYou need to be connected on Minecraft server while sending this command!");
		}
		return true;
	}

}
