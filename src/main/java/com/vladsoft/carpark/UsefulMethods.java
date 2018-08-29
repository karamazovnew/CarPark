package com.vladsoft.carpark;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

abstract public class UsefulMethods {
	public static String outputFluxTable(List<FluxRecord> fluxRecords) {
		String result="";		
		result+="<table>";
		result+="<tr><th rowspan=\"2\">Tichet</th>"
				+ "<th colspan=\"2\">Intrare</th> "
				+ "<th rowspan=\"2\">Sectiune</th>"
				+ "<th colspan=\"2\">Plata</th></tr>";
		result+="<tr><th>Data</th><th>Ora</th><th>Data</th><th>Ora</th></tr>";		
		
		for(FluxRecord record: fluxRecords){			
			result+="<tr><td>";
				result=result.concat(record.getTokenId());
			result+="</td>";
			result+="<td>";
				result=result.concat(record.getInDate());
			result+="</td>";
			result+="<td>";			
				result=result.concat(record.getInTime());
			result+="</td>";
			result+="<td>";			
				result=result.concat(record.getSection());
			result+="</td>";
			result+="<td>";			
				result=result.concat(record.getPayDate());
			result+="</td>";
			result+="<td>";			
				result=result.concat(record.getPayTime());
			result+="</td>";
			result=result+"</tr>";
		}
		result += "</table>";
		return result;
	}	
	
	public static String outputHistoryTable(List<HistoryRecord> historyRecords) {
		String result="";		
		result+="<table>";
		result+="<tr><th>Id</th><th>Operatiune</th><th>Data</th><th>Ora</th>"
				+ "<th>Tichet</th><th>Sectiune</th><th>Suma Plata</th></tr>";
				
		for(HistoryRecord record: historyRecords){
			
			result+="<tr><td>";
				result=result.concat(record.getHistoryId());
			result+="</td>";
			result+="<td>";
				result=result.concat(record.getHistoryType());
			result+="</td>";
			result+="<td>";			
				result=result.concat(record.getHistoryDate());
			result+="</td>";
			result+="<td>";			
				result=result.concat(record.getHistoryTime());
			result+="</td>";
			result+="<td>";			
				result=result.concat(record.getHistoryToken());
			result+="</td>";
			result+="<td>";			
				result=result.concat(record.getHistorySection());
			result+="</td>";
			result+="<td>";			
				result=result.concat(record.getHistoryPay());
			result+="</td>";
			result=result+"</tr>";
		}
		result += "</table>";
		return result;
	}
	
	public static String outputSections(List<SectionsRecord> sectionsRecords, HttpServletResponse response) {		
		
		for(SectionsRecord record: sectionsRecords){			
			response.addHeader("name", record.getSection_name());
			response.addHeader("size", record.getSection_size());
		}		
		return Integer.toString(sectionsRecords.size());
	}
	
	
	public static int parseMapIndex(String s)
	{
		int result=0;
		for (int i=0; i<s.length();i++) {
			if(Character.isDigit(s.charAt(i))) {
				result*=10;
				result+=Character.getNumericValue(s.charAt(i));
			}
			else {
				result=0;
			}
		}		
		return result;
	}
	
	public static String outputTokenDetails(FluxRecord record, HttpServletResponse response) {
		String result = "1";		
		response.setHeader("tokenId", record.getTokenId());
		response.addHeader("inDate", record.getInDate());
		response.addHeader("inTime" , record.getInTime());		
		response.addHeader("payDate", record.getPayDate());
		response.addHeader("payTime", record.getPayTime());
		response.addHeader("section", record.getSection());
		response.addHeader("payed", record.getPayed());		
		return result;
		
	}

}
