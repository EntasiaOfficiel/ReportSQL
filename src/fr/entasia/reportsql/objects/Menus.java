package fr.entasia.reportsql.objects;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import fr.entasia.apis.other.ItemBuilder;
import fr.entasia.reportsql.core.RSQLCore;
import fr.entasia.reportsql.objects.menus.ChatMenu;
import fr.entasia.reportsql.objects.menus.CheatMenu;
import fr.entasia.reportsql.objects.menus.MainMenu;
import fr.entasia.reportsql.objects.menus.UnfairBehaviorMenu;

public class Menus {

	public static final int MAIN = 0, CHAT = 1, CHEAT = 2, UNFAIR_BEHAVIOR = 3;
	
	public static final ItemStack BACK_ICON = new ItemBuilder(Material.ARROW).name("§cRetour")
			.lore("§8• Accéder au menu précédent").build();
	
	public static void init() {
		RSQLCore.registerMenu(new MainMenu());
		RSQLCore.registerMenu(new ChatMenu());
		RSQLCore.registerMenu(new CheatMenu());
		RSQLCore.registerMenu(new UnfairBehaviorMenu());
	}
	
}
