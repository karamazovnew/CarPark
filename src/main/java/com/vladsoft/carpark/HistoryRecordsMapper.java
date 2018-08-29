package com.vladsoft.carpark;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class HistoryRecordsMapper implements RowMapper<HistoryRecord>{
	
	
	@Override
	public HistoryRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
		HistoryRecord historyRecord = new HistoryRecord();
		historyRecord.setHistoryId(rs.getString("h_id")==null ? "N/A": rs.getString("h_id"));
		historyRecord.setHistoryType(rs.getString("h_type")==null ? "N/A": rs.getString("h_type"));
		historyRecord.setHistoryDate(rs.getString("h_date")==null ? "N/A": rs.getString("h_date"));
		historyRecord.setHistoryTime(rs.getString("h_time")==null ? "N/A": rs.getString("h_time"));
		historyRecord.setHistoryToken(rs.getString("h_token")==null ? "N/A": rs.getString("h_token"));
		historyRecord.setHistorySection(rs.getString("h_section")==null ? "N/A": rs.getString("h_section"));
		historyRecord.setHistoryPay(rs.getString("h_pay")==null ? "N/A": rs.getString("h_pay"));
		
		return historyRecord;
	}
}
