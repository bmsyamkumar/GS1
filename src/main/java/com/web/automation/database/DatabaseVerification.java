package com.web.automation.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.web.automation.accelerators.ActionsLibrary;
import com.web.automation.utilities.ConfigManager;

public class DatabaseVerification extends ActionsLibrary{

	String db_connect_string ;
	String db_userid;
	String db_password;
	List<String> resultSet;

	private void updateProperty(){
		try {
			if(!ConfigManager.ArePropertiesSet.get()){
				ConfigManager.setProperties();
			}
			if(!ConfigManager.ArePropertiesUpdated.get()){
				ConfigManager.UpdateProperties();
			}
		} 
		catch (Exception e) {
			System.out.println("Failed to load/update Properties file");
		}
		db_connect_string = ConfigManager.getProperties().getProperty("dbServerName").trim();
		db_userid = ConfigManager.getProperties().getProperty("dbUserName").trim();
		db_password = ConfigManager.getProperties().getProperty("dbPassword").trim();
	}

	/**
	 * @author Cigniti
	 * @param db_connect_string
	 * @param db_userid
	 * @param db_password
	 * @param queryString
	 * @return
	 * @throws Exception
	 */
	public ResultSet getDataFromDB(String db_connect_string, String db_userid,
			String db_password, String queryString) throws Exception {
		updateProperty();
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection conn = DriverManager.getConnection(db_connect_string,
				db_userid, db_password);
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(queryString);
		return rs;
	}

	/**
	 * @author Cigniti
	 * @param rs
	 * @return
	 */
	public List<Map<String, String>> getdatabaseResultInList(ResultSet rs) {
		List<Map<String, String>> resultSet = new ArrayList<Map<String, String>>();
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {
				Map<String, String> rowData= new HashMap<String, String>();
				for (Integer i = 1; i <= rsmd.getColumnCount(); i++) {
					rowData.put(rsmd.getColumnLabel(i), rs.getString(i));
				}
				resultSet.add(rowData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * @author Cigniti
	 * @param DBName
	 * @param queryString
	 * @return
	 * @throws Exception
	 */
	public ResultSet getDataFromDataBase(String DBName, String queryString) throws Exception {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		ResultSet rs = null;
		updateProperty();
		String connectionUrl = ConfigManager.getProperties().getProperty("dbServerName")
				+ "; databaseName=" + DBName + ";" + "user=" + ConfigManager.getProperties().getProperty("dbUserName") + ";"
				+ "password= "+ ConfigManager.getProperties().getProperty("dbPassword") ;
		for(int i=0;i<3;i++){
			try{
				Connection conn = DriverManager.getConnection(connectionUrl);
				Statement stmt = conn.createStatement();
				rs = stmt.executeQuery(queryString);
				return rs;
			}
			catch(Exception eq){}
		}
		return rs;
	}

	/**
	 * @author Cigniti
	 * @param dbServerName
	 * @param DBName
	 * @param queryString
	 * @return
	 * @throws Exception
	 */
	public ResultSet getDataFromDataBase(String dbServerName, String DBName, String queryString) throws Exception {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		updateProperty();
		String connectionUrl = dbServerName + "; databaseName=" + DBName + ";" + "user=" + ConfigManager.getProperties().getProperty("dbUserName") + ";"
				+ "password= "+ ConfigManager.getProperties().getProperty("dbPassword") ;
		Connection conn = DriverManager.getConnection(connectionUrl);
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(queryString);
		return rs;
	}

	/**
	 * @author Cigniti
	 * @param time in 24hr format.
	 * @return time in 12hr format.
	 * @throws ParseException
	 */
	@SuppressWarnings("unused")
	private String get12HrTime(String s) throws ParseException{
		DateFormat f1 = new SimpleDateFormat("hh:mm");
		Date d = f1.parse(s);
		DateFormat f2 = new SimpleDateFormat("h:mma");
		f2.format(d).toLowerCase();
		return f2.format(d).toUpperCase();

	}

	/**
	 * @author Cigniti
	 * @param DBName
	 * @param queryString
	 * @throws Exception
	 */
	public void InsertInToDataBase(String DBName, String queryString)
			throws Exception {
		updateProperty();
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		String connectionUrl = ConfigManager.getProperties().getProperty("dbServerName")
				+ "; databaseName=" + DBName + ";" + "user=" + ConfigManager.getProperties().getProperty("dbUserName") + ";"
				+ "password= "+ ConfigManager.getProperties().getProperty("dbPassword") ;
		Connection conn = DriverManager.getConnection(connectionUrl);
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(queryString);
	}

	/**
	 * @author Cigniti
	 * @return Update Query returns the no of row affected
	 * @throws Exception
	 */
	public int executeUpdateQuery(String DBName, String queryString)
			throws Exception {
			int rowAffected;
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		updateProperty();
		String connectionUrl = ConfigManager.getProperties().getProperty("dbServerName")
				+ "; databaseName=" + DBName + ";" + "user=" + ConfigManager.getProperties().getProperty("dbUserName") + ";"
				+ "password= "+ ConfigManager.getProperties().getProperty("dbPassword") ;
		Connection conn = DriverManager.getConnection(connectionUrl);
		Statement stmt = conn.createStatement();
		rowAffected= stmt.executeUpdate(queryString);
		return rowAffected;
	}

	/**
	 * @author Cigniti
	 * @param queryString
	 * @return
	 * @throws Exception
	 */
	public int executeUpdateQueryInDataBase(String queryString) throws Exception {
		int rowAffected;
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		updateProperty();
		String connectionUrl = ConfigManager.getProperties().getProperty("dbServerName")
				+ "; databaseName=database;" + "user=" + ConfigManager.getProperties().getProperty("dbUserName") + ";"
				+ "password= "+ ConfigManager.getProperties().getProperty("dbPassword") ;
		Connection conn = DriverManager.getConnection(connectionUrl);
		Statement stmt = conn.createStatement();
		rowAffected= stmt.executeUpdate(queryString);
		return rowAffected;
	}
}