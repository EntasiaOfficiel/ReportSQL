package fr.entasia.reportsql.core;

public class SearchFilter {

	public static SearchFilter[] translate(String[] filters) {
		SearchFilter[] translated = new SearchFilter[filters.length];
		return translated;
	}
	
	
	private boolean excluded;
	private SearchFilterType type;
	private String arg;
	public SearchFilter(SearchFilterType type, String arg, boolean excluded) {
		this.excluded = excluded;
	}
	
	public SearchFilterType getType() {
		return type;
	}
	
	public String getArgument() {
		return arg;
	}
	
	public boolean isExcluded() {
		return excluded;
	}
	
	public static enum SearchFilterType{
		
		REPORTER,
		REPORTED,
		REASON,
		SERVER,
		DATE,
		READ;
		
	}
	
}
