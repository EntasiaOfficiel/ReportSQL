package fr.entasia.reportsql.core;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import fr.entasia.apis.other.ItemBuilder;
import fr.entasia.apis.sql.SQLConnection;
import fr.entasia.reportsql.events.MenuManager;
import fr.entasia.reportsql.objects.Report;

public class ReportManager{
	
	private static SQLConnection sql;
	private static String servername;
	
	private static List<Report> reports;
	
	public static int init(String database, String servername, boolean devMode) throws SQLException {
		sql = new SQLConnection(devMode).mariadb("report", database);
		ReportManager.servername = servername;
		return loadReports();
	}
	
	private static int loadReports() throws SQLException{
		reports = new ArrayList<>();
		ResultSet rs = sql.fastSelectUnsafe("SELECT * FROM reports");
		while(rs.next()) {
			reports.add(new Report(rs.getString(2), rs.getString(3), rs.getString(4),
					rs.getString(5), rs.getString(6), rs.getBoolean(7)));
		}
		rs.close();
		return reports.size();
	}
	
	public static boolean addReport(Player reporter, OfflinePlayer target, String reason) {
		try {
			//Args: reporter name, reported name, reason, server, date, false
			PreparedStatement req = sql.connection.prepareStatement("INSERT INTO reports(reporter, reported, reason, server, date, seen) VALUES(?,?,?,?,?,?)");
			req.setString(1, reporter.getName());
			req.setString(2, target.getName());
			req.setString(3, reason);
			req.setString(4, servername);
			req.setString(5, RSQLCore.getDate());
			req.setBoolean(6, false);
			
			req.execute();
			req.close();
			
			reports.add(new Report(reporter.getName(), target.getName(), reason, servername, RSQLCore.getDate(), false));
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static List<Report> filterWith(String[] tags){
		/**if(tags == null || tags.length == 0) {
			return reports;
		} else {
			List<Report> filtered = new ArrayList<>();
			for(SearchFilter tag : SearchFilter.translate(tags)) {
				if(tag.getType() == SearchFilterType.REPORTER) {
					
				}
				
			}
			return null;
		}**/
		return reports;
		
	}
	
	public static Inventory getPageFrom(int page, List<Report> reports){
		int maxPages = reports.size() == 0 ? 1:(int) Math.ceil(reports.size() / 45.0);
		
		if(page >= maxPages) {
			throw new IllegalArgumentException("Page number exceeds the number of possible pages");
		} else if(page < 0) {
			throw new IllegalArgumentException("Page number cannot be negative!");
		}
		
		Inventory inv = MenuManager.getCreator().createInv(6, "§4§lListe des reports ("+(page + 1)+"/"+maxPages+")");
		
		if(page > 0) {
			inv.setItem(48, new ItemBuilder(Material.PAPER).name("§aPage précédente").lore("§8• Ouvrir la page précédente").build());
		}
		inv.setItem(49, new ItemBuilder(Material.NETHER_STAR).name("§bActualiser").lore("§8• Actualiser les signalements").build());
		if(page < maxPages - 1) {
			inv.setItem(50, new ItemBuilder(Material.PAPER).name("§aPage suivante").lore("§8• Ouvrir la page suivante").build());
		}
		
		if(reports.size() == 0) {
			ItemBuilder item = new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
					.name("§cIl n'y a eu aucun signalement !").lore("§8Bizarre, tout est calme...");
			inv.setItem(22, item.build());
			return inv;
		}
		
		
		int end = Math.min(reports.size(), (page + 1) * 45);
		int start = page * 45;
		for(int i = start; i < end; i++) {
			Report report = reports.get(i);
			ItemBuilder item = new ItemBuilder(report.isSeen() ? Material.BOOK:Material.ENCHANTED_BOOK).name("§aJoueur signalé : "+report.getTarget())
					.lore(Arrays.asList("§7Signalé par "+report.getReporter(), "§7Raison : "+report.getReason(), "§7Sur le serveur "+report.getServerName(),
							"§7Signalé le "+report.getDate()));
			inv.setItem(i - start, item.build());
		}
		
		return inv;
	}
	
	public static List<Report> getReports() {
		return reports;
	}
	
}