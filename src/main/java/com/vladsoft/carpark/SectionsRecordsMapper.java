package com.vladsoft.carpark;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SectionsRecordsMapper implements RowMapper<SectionsRecord>{
	@Override
	public SectionsRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
		SectionsRecord sectionsRecord = new SectionsRecord();
		sectionsRecord.setSection_id(rs.getString("section_id")==null ? "N/A": rs.getString("section_id"));
		sectionsRecord.setSection_name(rs.getString("section_name")==null ? "N/A": rs.getString("section_name"));
		sectionsRecord.setSection_size(rs.getString("section_size")==null ? "N/A": rs.getString("section_size"));		
		return sectionsRecord;
	}
}
