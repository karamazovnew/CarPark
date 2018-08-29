package com.vladsoft.carpark;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FluxRecordsMapper implements RowMapper<FluxRecord>{

	@Override
	public FluxRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
		FluxRecord fluxRecord = new FluxRecord();
		fluxRecord.setTokenId(rs.getString("token_id")==null ? "N/A": rs.getString("token_id"));
		fluxRecord.setInDate(rs.getString("in_date")==null ? "N/A": rs.getString("in_date"));
		fluxRecord.setInTime(rs.getString("in_time")==null ? "N/A": rs.getString("in_time"));
		fluxRecord.setPayDate(rs.getString("pay_date")==null ? "N/A": rs.getString("pay_date"));
		fluxRecord.setPayTime(rs.getString("pay_time")==null ? "N/A": rs.getString("pay_time"));
		fluxRecord.setSection(rs.getString("section_name")==null ? "N/A": rs.getString("section_name"));
		fluxRecord.setPayed(rs.getString("token_payed")==null ? "N/A": rs.getString("token_payed"));
		return fluxRecord;
	}

	
}
