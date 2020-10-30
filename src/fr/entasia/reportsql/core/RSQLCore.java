package fr.entasia.reportsql.core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import fr.entasia.reportsql.commands.ReportCommand;
import fr.entasia.reportsql.commands.ReportHelpCommand;
import fr.entasia.reportsql.commands.mod.ReportListCommand;
import fr.entasia.reportsql.events.MenuManager;
import fr.entasia.reportsql.events.MenuManager.MenuInteraction;
import fr.entasia.reportsql.objects.Menus;
import fr.entasia.reportsql.objects.ReportMenu;

public class RSQLCore extends JavaPlugin implements CommandExecutor{
	
	private final String[] STATES = {"de la configuration", "de la connexion SQL", "des évènements", "des commandes"};
	private static List<ReportMenu> menus;
	
	public static final byte[] ICON_PLACES = new byte[]
	{
		1, 6, 2,
		8, 3, 9,
		4, 7, 5
	};
	//public List<ReportCooldown> rcooldowns = new ArrayList<>();
	
	@Override
	public void onEnable() {
		int state = 0;
		try {
			Bukkit.getConsoleSender().sendMessage("§eStarting loading ReportSQL...");
			saveDefaultConfig();
			
			String database = getConfig().getString("database");
			if(database == null || database.length() == 0) {
				throw new IllegalArgumentException("Database name cannot be null or empty. Please check the config.");
			}
			
			String servername = getConfig().getString("servername");
			if(servername == null || servername.length() == 0) {
				servername = "Inconnu";
			}
			
			boolean devMode = getConfig().getBoolean("dev-mode", false);
			state++;
			
			int loaded = ReportManager.init(database, servername, devMode);
			state++;
			
			/**PluginManager pm = getServer().getPluginManager();
			MenuEvents e = new MenuEvents(this);
			pm.registerEvents(e, this);**/
			menus = new ArrayList<>();
			Menus.init();
			state++;
			
			getCommand("report").setExecutor(new ReportCommand());
			getCommand("reporthelp").setExecutor(new ReportHelpCommand());
			getCommand("reportlist").setExecutor(new ReportListCommand());
			//getCommand("reportsearch").setExecutor(new ReportSearchCommand());
			//getCommand("reportfilters").setExecutor(new ReportFiltersCommand());
			Bukkit.getConsoleSender().sendMessage("§bPlugin successfully enabled! Loaded §a"+loaded+"§b reports from the database.");
		} catch(Exception e) {
			Bukkit.getConsoleSender().sendMessage("§cUne erreur est survenue lors du chargement "+STATES[state]+". Arrêt du serveur...");
			e.printStackTrace();
			Bukkit.getServer().shutdown();
		}
		
	}
	
	/**
	 * Registers the given menu in the ReportSQL core.
	 * That allows to add your own menus to the ReportSQL core and make them fully working.
	 * Menu APIs for ReportSQL are used to add some new report reasons to the core and make them server-unique.
	 * If the menu implements the MenuInteraction interface, its interactions will be automatically registered to the interaction core.
	 * 
	 * @param name - The name of the OfflinePlayer you want to search
	 * @return The OfflinePlayer matching with the given name or null if there's no matching OfflinePlayer.
	 * @since 1.0.00
	 **/
	public static OfflinePlayer getOfflinePlayer(String name) {
		for(OfflinePlayer ps : Bukkit.getOfflinePlayers()){
			String pname = ps.getName();
			if(pname.equals(name)){
				return ps;
			}
			
		}
		return null;
	}
	
	/**
	 * Simple function to return the current date formatted as "day/month/year - hour:minutes:seconds".
	 * Example of returned date: "01/01/1970 - 00:00:00".
	 * 
	 * @return The current date as a string
	 * @since 1.0.00
	 **/
	
	public static String getDate(){
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
		Date calendar = new Date();
		String date = df.format(calendar);
		return date;
	}
	
	/**
	 * Registers the given menu in the ReportSQL core.
	 * That allows to add your own menus to the ReportSQL core and make them fully working.
	 * Menu APIs for ReportSQL are used to add some new report reasons to the core and make them server-unique.
	 * If the menu implements the MenuInteraction interface, its interactions will be automatically registered to the interaction core.
	 * 
	 * @param menu - The ReportMenu you want to register to the core.
	 * @since 1.0.00
	 * @see fr.entasia.reportsql.objects.ReportMenu
	 **/
	
	public static void registerMenu(ReportMenu menu) {
		if(menus.size() < 10) {
			menus.add(menu);
			if(menu instanceof MenuInteraction) {
				MenuInteraction mi = (MenuInteraction) menu;
				MenuManager.registerInteraction(mi);
			}
			
		}
		
	}
	
	public static List<ReportMenu> getMenus() {
		return menus;
	}
	
}
