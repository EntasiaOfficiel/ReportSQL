package fr.entasia.reportsql.objects.menus;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import fr.entasia.apis.menus.MenuClickEvent;
import fr.entasia.apis.other.ItemBuilder;
import fr.entasia.reportsql.core.RSQLCore;
import fr.entasia.reportsql.events.MenuManager.MenuInteraction;
import fr.entasia.reportsql.objects.MenuInfo;
import fr.entasia.reportsql.objects.ReportMenu;

public class MainMenu extends ReportMenu implements MenuInteraction{

	public MainMenu() {
		super("Main", new ItemBuilder(Material.BARRIER).name(" ").build(), 5);
	}

	@Override
	protected ItemStack[] generateItems() {
		ItemStack[] items = new ItemStack[45];
		for(int i = 0; i < lines * 9; i++) {
			if(i == 0 || i == 8 || i == 36 || i == 44) {
				items[i] = new ItemBuilder(Material.NETHER_STAR).name(" ").build();
			} else if(i < 9 || (i > 35 && i < 44) || i % 9 == 0 || i % 9 == 8) {
				items[i] = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).name(" ").build();
			} else {
				items[i] = new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).name(" ").build();
			}
			
		}
		
		//Arrow pattern
		
		items[18] = new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).name(" ").build();
		items[26] = new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).name(" ").build();
		
		items[10] = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).name(" ").build();
		items[19] = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).name(" ").build();
		items[20] = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).name(" ").build();
		items[28] = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).name(" ").build();
		
		items[16] = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).name(" ").build();
		items[24] = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).name(" ").build();
		items[25] = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).name(" ").build();
		items[34] = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).name(" ").build();
		
		int menu = 0;
		for(int i : new int[]{12, 13, 14, 21, 22, 23, 30, 31, 32}) {
			if(RSQLCore.ICON_PLACES[menu] < RSQLCore.getMenus().size()) {
				items[i] = RSQLCore.getMenus().get(RSQLCore.ICON_PLACES[menu]).getIcon();
			} else {
				items[i] = new ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE).name(" ").build();
			}
			menu++;
		}
		return items;
	}
	
	@Override
	public void onMenuClick(MenuClickEvent e, MenuInfo info) {
		if(info.getMenuName().equals(name)) {
			ReportMenu menu = isIconItem(e.item);
			if(menu != null) {
				menu.openMenu(e.player, info.getReported(), "§8Signaler §4"+info.getReported().getName());
			}
			
		}
		
	}
	
	private ReportMenu isIconItem(ItemStack it) {
		for(int i = 0; i < RSQLCore.getMenus().size(); i++) {
			if(RSQLCore.getMenus().get(i).getIcon().equals(it)) {
				return RSQLCore.getMenus().get(i);
			}
			
		}
		return null;
	}

}
