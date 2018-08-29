package com.vladsoft.carpark;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public interface FluxDAO {	
	
	
	public int insertFluxRecord(String inDate, String inTime, String inSection);
	
	public int updateFluxRecord(String id, String payDate, String payTime, String paySum); 
	
	public int outFluxRecord(String id, String outDate, String outTime);
	
	public int existsFluxRecord(String id);
	
	public List<FluxRecord> listFluxRecords(); 
	
	public List<HistoryRecord> listHistoryRecords();
	
	public List<SectionsRecord> listSections();
	
	public int countOccupied(String sectionName);
	
	public FluxRecord getFluxRecord(String id);
	
	
}
