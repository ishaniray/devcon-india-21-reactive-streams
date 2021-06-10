package com.cerner.devcon.hic.constants;

public enum CrawlInd {

	NOT_STARTED("NS"),
	IN_PROGRESS("IP"),
	COMPLETED("CO");
	
	private final String indicator;
	
	private CrawlInd(final String indicator) {
		this.indicator = indicator;
	}
	
	public String getIndicator() {
		return this.indicator;
	}
	
}
