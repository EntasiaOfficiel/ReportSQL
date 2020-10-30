package fr.entasia.reportsql.objects;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.entasia.reportsql.events.MenuManager;

public abstract class ReportMenu {
	
	private ItemStack icon;
	protected String name;
	protected int lines;
	
	protected ReportMenu(String name, ItemStack icon, int lines) {
		this.icon = icon;
		this.lines = lines;
		this.name = name;
	}
	
	protected abstract ItemStack[] generateItems();
	
	public void openMenu(Player p, OfflinePlayer target, String title) {
		ItemStack[] items = generateItems();
		Inventory inv = MenuManager.getCreator().createInv(lines, title, new MenuInfo(target, name, title));
		for(int i = 0; i < items.length; i++) {
			inv.setItem(i, items[i]);
		}
		p.openInventory(inv);
	}
	
	public ItemStack getIcon() {
		return icon;
	}
	
}
