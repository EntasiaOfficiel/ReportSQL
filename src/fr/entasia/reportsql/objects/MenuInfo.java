package fr.entasia.reportsql.objects;

import org.bukkit.OfflinePlayer;

public class MenuInfo {

	private String invTitle, menuName;
	private OfflinePlayer reported;
	
	public MenuInfo(OfflinePlayer reported, String menuName, String invTitle) {
		this.reported = reported;
		this.menuName = menuName;
		this.invTitle = invTitle;
	}
	
	public OfflinePlayer getReported() {
		return reported;
	}
	
	public String getInventoryTitle() {
		return invTitle;
	}
	
	public String getMenuName() {
		return menuName;
	}
	
}
