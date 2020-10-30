package fr.entasia.reportsql.objects.menus;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import fr.entasia.apis.menus.MenuClickEvent;
import fr.entasia.apis.other.ItemBuilder;
import fr.entasia.reportsql.core.RSQLCore;
import fr.entasia.reportsql.core.ReportManager;
import fr.entasia.reportsql.events.MenuManager.MenuInteraction;
import fr.entasia.reportsql.objects.MenuInfo;
import fr.entasia.reportsql.objects.Menus;
import fr.entasia.reportsql.objects.ReportMenu;

public class CheatMenu extends ReportMenu implements MenuInteraction{

	public CheatMenu() {
		super("Triche", new ItemBuilder(Material.IRON_SWORD).name("§cTriche").lore("§8• Accéder au menu de signalement",
				"§8  relatif à l'utilisation de logiciels de triche.").build(), 5);
	}
	
	@Override
	protected ItemStack[] generateItems() {
		ItemStack[] items = new ItemStack[lines * 9];
		
		int slot = 19;
		for(String str : new String[] {"Reach/TP-Aura", "Kill-Aura", "Fly", "Anti-KB", "Jesus", "Auto-click", "Aimbot"}) {
			items[slot] = new ItemBuilder(Material.IRON_SWORD).name("§2Signaler pour §a"+str).build();
			slot++;
		}
		items[40] = Menus.BACK_ICON;
		
		return items;
	}

	@Override
	public void onMenuClick(MenuClickEvent e, MenuInfo info) {
		if(info.getMenuName().equals(name)) {
			String itemName = e.item.getItemMeta().getDisplayName();
			if(e.item.getType() == Material.IRON_SWORD && itemName.startsWith("§2Signaler pour §a")) {
				String reason = itemName.replaceFirst("§2Signaler pour §a", "");
				ReportManager.addReport(e.player, info.getReported(), reason);
				e.player.sendMessage("§aVotre signalement de "+info.getReported().getName()+" pour "+reason+" a bien été pris en compte ! Merci de votre rapport !");
			} else if(e.item.equals(Menus.BACK_ICON)) {
				RSQLCore.getMenus().get(0).openMenu(e.player, info.getReported(), info.getInventoryTitle());
			}
			
		}
		
	}
	
}
