package com.vladsoft.carpark;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;

@WebListener
public class TomcatContextListener implements ServletContextListener{
	static final Logger logger=Logger.getLogger(LoginController.class);
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		System.out.println("Starting up!");		
		logger.info("Application started ");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		//ClassLoader cl = Thread.currentThread().getContextClassLoader();
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		logger.info("Application stopping");
		while (drivers.hasMoreElements()) {
			Driver driver = drivers.nextElement();
			//if (driver.getClass().getClassLoader() == cl) {				
				try {
	                DriverManager.deregisterDriver(driver);
	                logger.info("JDBC Driver forcefully stopped to prevent Tomcat errors");
	            } catch (SQLException e) {
	            	logger.error("Fatal error on JDBC driver forced stop");	        
	            }			
			//} else { 
			//	logger.trace("Not deregistering JDBC driver because not needed");
			//}
		}		
	}	

}
