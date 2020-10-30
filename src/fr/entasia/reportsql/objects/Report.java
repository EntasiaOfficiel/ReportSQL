package fr.entasia.reportsql.objects;

public class Report {
	
	private String reporter, target, reason, servername, date;
	private boolean seen;
	
	public Report(String reporter, String target, String reason, String servername, String date, boolean seen) {
		this.reporter = reporter;
		this.target = target;
		this.reason = reason;
		this.servername = servername;
		this.date = date;
		this.seen = seen;
	}
	
	public String getReporter() {
		return reporter;
	}
	
	public String getTarget() {
		return target;
	}
	
	public String getReason() {
		return reason;
	}
	
	public String getServerName() {
		return servername;
	}
	
	public String getDate() {
		return date;
	}
	
	public boolean isSeen() {
		return seen;
	}
	
}
