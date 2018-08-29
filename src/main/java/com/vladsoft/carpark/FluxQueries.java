package com.vladsoft.carpark;

import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class FluxQueries implements FluxDAO{
	
	static final Logger logger=Logger.getLogger(LoginController.class);
	private String schemaName;
	private DataSourceTransactionManager transactionManager;	
	private DataSource dataSource;
	private TransactionStatus transaction;
	private Map<String, Object> inParamMap;
	private SqlParameterSource parameters;
	private SimpleJdbcCall jdbcCall;	
	private int queryResult;	
	
	private JdbcTemplate jdbcQuery;
	
	@Autowired
	public void setTransactionManager(DataSourceTransactionManager transactionManager) 
	{ 
		this.transactionManager = transactionManager;
		this.dataSource = this.transactionManager.getDataSource();		
		this.jdbcQuery = new JdbcTemplate(dataSource);
	}		
	@Autowired
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	
	private void initializeSQLFunction() {
		jdbcCall = new SimpleJdbcCall(transactionManager.getDataSource());
		jdbcCall.setSchemaName(this.schemaName);
		jdbcCall.setFunction(true);
		inParamMap = new HashMap<String,Object>();
		queryResult=0;		
	}
	private void trySQLFunction() {
		try{		
			transaction = transactionManager.getTransaction(new DefaultTransactionDefinition());
			queryResult = jdbcCall.executeFunction(BigDecimal.class, parameters).intValue();			
			if(queryResult<=0) throw new CarParkSqlException(queryResult);			
			transactionManager.commit(transaction);           
	        logger.trace("Success on (" + jdbcCall.getProcedureName()+") result: " + queryResult);
	        //success
		}	
		catch(CarParkSqlException e) {
			//expected
			transactionManager.rollback(transaction);
			logger.warn("SQL on (" + jdbcCall.getProcedureName()+") procedure:\n" + e.getMessage());
			//adapts custom SQL error code to html handling
			queryResult = e.repackageError(queryResult); 
		}
		catch(Exception e) {
			//not expected
			transactionManager.rollback(transaction);
			logger.error("Error on (" + jdbcCall.getProcedureName()+") procedure:\n" + e.getMessage());
			e.printStackTrace();
			//set to generic Java error code
			queryResult = -1;		
		}	
		finally {
			transaction = null;
			parameters = null;
			inParamMap = null;
			jdbcCall = null;
		}
	}
	
	@Override	
	public int insertFluxRecord(String inDate, String inTime, String inSection) {
		initializeSQLFunction();
		jdbcCall.setProcedureName("client_checkin");		
		inParamMap.put("in_date", inDate);
		inParamMap.put("in_time", inTime);
		inParamMap.put("in_section", inSection);		
		parameters = new MapSqlParameterSource(inParamMap);
		trySQLFunction();
		return queryResult;		
	}
	
	@Override
	public int updateFluxRecord(String id, String payDate, String payTime, String paySum) {		
		initializeSQLFunction();
		jdbcCall.setProcedureName("client_pay");
		inParamMap.put("in_id", id);
		inParamMap.put("in_date", payDate);
		inParamMap.put("in_time", payTime);
		inParamMap.put("in_sum", paySum);		
		parameters = new MapSqlParameterSource(inParamMap);		
		trySQLFunction();		
		return queryResult;
	}
	
	@Override
	public int outFluxRecord(String id, String outDate, String outTime) {		
		initializeSQLFunction();
		jdbcCall.setProcedureName("client_checkout");
		inParamMap.put("in_id", id);
		inParamMap.put("in_date", outDate);
		inParamMap.put("in_time", outTime);		
		parameters = new MapSqlParameterSource(inParamMap);
		trySQLFunction();
		return queryResult;
	}
	
	@Override
	public int existsFluxRecord(String id) {
		initializeSQLFunction();
		jdbcCall.setProcedureName("exists_token");
		inParamMap.put("in_id", id);			
		parameters = new MapSqlParameterSource(inParamMap);
		trySQLFunction();
		return queryResult;		
	}
	
	@Override
	public FluxRecord getFluxRecord(String id) {
		transaction = transactionManager.getTransaction(new DefaultTransactionDefinition());
		FluxRecord result = new FluxRecord();
		jdbcCall = new SimpleJdbcCall(transactionManager.getDataSource())
				.withSchemaName(this.schemaName)
				.withProcedureName("get_Token_Details");	
		SqlParameterSource params = new MapSqlParameterSource().addValue("in_id", id);						
		try {
			Map<String, Object> out = jdbcCall.execute(params);	
			result.setTokenId((String)out.get("OUT_TOKEN_ID"));
			result.setInDate((String)out.get("OUT_IN_DATE"));
			result.setInTime((String)out.get("OUT_IN_TIME"));
			result.setPayDate((String)out.get("OUT_PAY_DATE"));
			result.setPayTime((String)out.get("OUT_PAY_TIME"));
			result.setSection((String)out.get("OUT_SECTION"));
			result.setPayed((String)out.get("OUT_PAY"));
		}		
		catch(Exception e) {
			logger.error("Error on (" + jdbcCall.getProcedureName()+") procedure:\n" + e.getMessage());
			e.printStackTrace();
			return null;
		}
		finally {
			transaction = null;					
			jdbcCall = null;
		}
		return result;
	}

	@Override
	public List<FluxRecord> listFluxRecords() {
		String SQL = "SELECT * FROM flux ORDER BY in_date, in_time";
        List<FluxRecord> fluxRecords = jdbcQuery.query(SQL, new FluxRecordsMapper());        
        return fluxRecords;
	}
	
	@Override
	public List<HistoryRecord> listHistoryRecords() {
		String SQL = "SELECT * FROM history ORDER BY h_date, h_time";
        List<HistoryRecord> historyRecords = jdbcQuery.query(SQL, new HistoryRecordsMapper());        
        return historyRecords;
	}
	
	@Override
	public List<SectionsRecord> listSections() {
		String SQL = "SELECT * FROM sections ORDER BY section_id";
        List<SectionsRecord> sectionsRecords = jdbcQuery.query(SQL, new SectionsRecordsMapper());        
        return sectionsRecords;
	}
	@Override
	public int countOccupied(String sectionName) {
		int result;
		String SQL = "SELECT COUNT(*) FROM flux WHERE section_name = ?";		
		try {
			result = (int) jdbcQuery.queryForObject(SQL, new Object[] { sectionName }, Integer.class);
		}
		catch(Exception e) {
			return -1;
		}
		return result;
	}
	
	//old methods using jdbcQuery instead of database procedures
	/*	@Override
	public int insertFluxRecord(String inDate, String inTime, String inSection) {
		TransactionDefinition def = new DefaultTransactionDefinition();		
		TransactionStatus status = transactionManager.getTransaction(def);
		int result;
		try{
			
			String SQL1 = "INSERT INTO flux(in_date,in_time,section_name,token_payed) VALUES (?, ?, ?, ?)";			
			String SQL2 = "INSERT INTO history(h_type, h_date, h_time, h_section, h_token) " + 
					"VALUES (?, ?, ?, ?, token_id_seq.currval)";	
			String SQL3;
			result = jdbcQuery.update(SQL1, inDate, inTime, inSection, "0"); 
			if (result!=0) {
				result *= jdbcQuery.update(SQL2, "checkin", inDate, inTime, inSection);
			}
			if (result!=0) {
				SQL3="SELECT token_id FROM flux WHERE in_date = ? AND in_time = ? AND section_name = ?";
				result= (int) jdbcQuery.queryForObject(SQL3, new Object[] { inDate, inTime, inSection }, Integer.class);				
			}
			           
            logger.trace("Record created");
            transactionManager.commit(status);
           
		}
		catch (CannotCreateTransactionException e) {
			return -1;
		}
		catch(DataAccessException e) {			
			transactionManager.rollback(status);
			logger.error("Error in creating record, rolling back");
			return 0;
		}
		return result;
	}

	
	@Override
	public int updateFluxRecord(String id, String payDate, String payTime, String paySum) { 
		TransactionDefinition def = new DefaultTransactionDefinition();		
		TransactionStatus status = transactionManager.getTransaction(def);
		int result;
		try{
            String SQL1 = "UPDATE flux SET pay_date = ?, pay_time = ?, token_payed = '1' "+
            		"where token_id = ? and token_payed = 0";
            String SQL2 = "INSERT INTO history(h_type, h_date, h_time, h_token, h_section, h_pay) " + 
					"VALUES (?, ?, ?, ?, (select section_name from flux where token_id = ?), ?)";
            result = jdbcQuery.update(SQL1, payDate, payTime, id); 
            if (result!=0) { 
            	result*= jdbcQuery.update(SQL2, "pay", payDate, payTime, id, id, paySum);
            }            
            logger.trace("Record updated ");
            transactionManager.commit(status);           
		}
		catch (CannotCreateTransactionException e) {
			return -1;
		}
		catch(DataAccessException e) {			
			transactionManager.rollback(status);
			logger.error("Error in creating record, rolling back");
			return 0;
		}
		return result;
	}
	

	
	@Override
	public int outFluxRecord(HttpServletResponse response, String id, String outDate, String outTime) {
		TransactionDefinition def = new DefaultTransactionDefinition();		
		TransactionStatus status = transactionManager.getTransaction(def);
		int result;
		String section;
		try{
			String SQL1 = "SELECT section_name FROM flux where token_id = ?";
			try {
				section = (String) jdbcQuery.queryForObject(SQL1, new Object[] { id }, String.class);
			}
			catch (Exception e){
				section="";
			}			
            String SQL2 = "DELETE flux WHERE token_id = ? AND token_payed = 1";            
            String SQL3 = "INSERT INTO history(h_type, h_date, h_time, h_token, h_section) " +
            		"VALUES (?, ?, ?, ?, ?)"; 
            result = jdbcQuery.update(SQL2, id);            
            if (result!=0) {            	
            	result*= jdbcQuery.update(SQL3, "checkout", outDate, outTime, id, section);
            }
            response.addHeader("section", section);	           
            
            logger.trace("Record updated ");
            transactionManager.commit(status);           
		}
		catch (CannotCreateTransactionException e) {
			return -1;
		}
		catch(DataAccessException e) {			
			transactionManager.rollback(status);
			logger.error("Error in creating record, rolling back");
			return 0;
		}
		return result;	
	}
*/	
}

