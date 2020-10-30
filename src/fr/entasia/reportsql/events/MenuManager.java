package fr.entasia.reportsql.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

import fr.entasia.apis.menus.MenuClickEvent;
import fr.entasia.apis.menus.MenuCreator;
import fr.entasia.reportsql.core.ReportManager;
import fr.entasia.reportsql.objects.MenuInfo;

public class MenuManager {

	private static List<MenuInteraction> mi = new ArrayList<>();
	
	private static MenuCreator creator = new MenuCreator() {
		
		public void onMenuClick(MenuClickEvent e) {
			if(e.data instanceof MenuInfo) {
				MenuInfo info = (MenuInfo) e.data;
				if(info.getReported() != null) {
					if(info.getInventoryTitle().startsWith("§8Signaler §4")) {
						for(int i = 0; i < mi.size(); i++) {
							mi.get(i).onMenuClick(e, info);
						}
						
					}
					
				}  else {
					e.player.closeInventory();
					e.player.sendMessage("§cUne erreur est survenue durant l'interaction avec un des éléments du menu."
							+ " Merci de contacter le staff.");
					throw new IllegalArgumentException("Data of report menu cannot contain a null OfflinePlayer!");
				}
				
			} else if(e.data instanceof Integer) {
				int page = (int) e.data;
				/**if(e.item.getItemMeta().getDisplayName().startsWith("§2Joueur signalé : §a")) {
					
				}**/
				if(e.item.getType() == Material.PAPER) {
					if(e.item.getItemMeta().getDisplayName().equals("§aPage précédente")) {
						e.player.openInventory(ReportManager.getPageFrom(page - 1, ReportManager.getReports()));
					} else if(e.item.getItemMeta().getDisplayName().equals("§aPage suivante")) {
						e.player.openInventory(ReportManager.getPageFrom(page + 1, ReportManager.getReports()));
					}
					
				} else if(e.item.getType() == Material.NETHER_STAR && e.item.getItemMeta()
						.getDisplayName().equals("§bActualiser")) {
					e.player.openInventory(ReportManager.getPageFrom(page, ReportManager.getReports()));
				}
				
			}
			
		}
		
	};
	
	/**
	 * Gives the MenuCreator that the ReportSQL core uses.
	 * The MenuCreator from the Entasia's Libraries API is a menu system that creates menus and can store an Object as data.
	 * 
	 * @return The MenuCreator of the ReportSQL core.
	 * @since 1.0.00
	 * @see fr.entasia.apis.menus.MenuCreator
	 * @see fr.entasia.reportsql.events.MenuManager.MenuInteraction
	 **/
	
	public static MenuCreator getCreator() {
		return creator;
	}
	
	/**
	 * Registers the given MenuInteraction in the interaction system.
	 *
	 * @param i - The MenuInteraction that must be registered in the interaction core. 
	 * @since 1.0.00
	 * @see onMenuClick(MenuClickEvent, Pair&lt;String, OfflinePlayer&gt;)
	 **/
	
	public static void registerInteraction(MenuInteraction i) {
		mi.add(i);
	}
	
	/**
	 * Implementation of the click interaction for the ReportSQL menu interaction system.
	 * 
	 * This interface let the menu system execute the registered <strong>MenuInteraction's onMenuClick()</strong> method for the plugin's APIs.
	 * A registered menu that implements this interface is automatically registered in the interaction system.
	 * 
	 * @since 1.0.00
	 * @see fr.entasia.reportsql.events.MenuManager.MenuInteraction#onMenuClick()
	 **/
	
	public static interface MenuInteraction {
		
		/**
		 * Method called when a player clicks on an item of the corresponding menu.
		 * 
		 * This method is similar to an event system, but for only one action.
		 * In order to be called by the core of the interaction system, it must be registered.
		 * 
		 * @param e - The MenuClickEvent sent by the core
		 * @param menuInfo - The relative menu informations containing name as key and player as value
		 * 
		 * @since 1.0.00
		 * @see fr.entasia.reportsql.event.MenuInteraction
		 **/
		
		public void onMenuClick(MenuClickEvent e, MenuInfo info);
		
	}
	
}

