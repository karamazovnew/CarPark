package com.vladsoft.carpark;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static com.vladsoft.carpark.UsefulMethods.*;

import javax.servlet.http.HttpServletResponse;

public class DatabaseManager {
	private ApplicationContext context;
	private FluxQueries fluxQueries;	

	public DatabaseManager(){
		this.context= new ClassPathXmlApplicationContext("jdbc.xml");
		this.fluxQueries = (FluxQueries)context.getBean("fluxQueries");	
	}	
        
        //prevents sending empty arguments to SQL queries.       
        private boolean hasEmptyArgs(String[] args){
            for(String arg : args){
                if(arg.length()==0) return true;
            }
            return false;
        }
	
	public String ProcessQuery(String[] args, HttpServletResponse response) {
		if(hasEmptyArgs(args)) return "0";
                String result="0";	
		int tokenExists=0;
		
		if(args[0].equals("checkin")){				
			result = Integer.toString(fluxQueries.insertFluxRecord(args[1],args[2],args[3]));			
			response.addHeader("section", args[3]);			
		}		
		
		else if(args[0].equals("pay")){
			tokenExists = fluxQueries.existsFluxRecord(args[1]);			
			if(tokenExists>0) {			
				result=Integer.toString(fluxQueries.updateFluxRecord(args[1],args[2],args[3],args[4]));				
			}
			else result=Integer.toString(0);			
		}
		
		else if(args[0].equals("checkout")){
			tokenExists = fluxQueries.existsFluxRecord(args[1]);
			if(tokenExists>0) {
				result=Integer.toString(fluxQueries.outFluxRecord(args[1], args[2], args[3]));
			}
			else result=Integer.toString(0);
		}
		
		else if(args[0].equals("showfluxtable")) {	
			result = outputFluxTable(fluxQueries.listFluxRecords());
		}	
		
		else if(args[0].equals("showhistorytable")) {	
			result = outputHistoryTable(fluxQueries.listHistoryRecords());
		}	
		
		else if(args[0].equals("listSections")) {	
			result = outputSections(fluxQueries.listSections(), response);
		}
		else if(args[0].equals("countOccupied")) {
			result= Integer.toString(fluxQueries.countOccupied(args[1]));
			response.addHeader("section", args[1]);	
		}	
		
		else if(args[0].equals("getTokenDetails")) {			
			tokenExists = fluxQueries.existsFluxRecord(args[1]);			
			if(tokenExists>0) {
				result = outputTokenDetails( fluxQueries.getFluxRecord(args[1]) ,response);
			}			
			else {
				result=Integer.toString(tokenExists);
			}
		}		
		return result;		
	}
}
