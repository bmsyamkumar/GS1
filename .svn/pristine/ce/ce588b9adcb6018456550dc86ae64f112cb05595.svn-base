package com.web.automation.googledrive;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import com.google.common.base.Optional;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.web.automation.fileutils.ReadCSVFile;
import com.web.automation.testmanagement.JiraIntegration;
import com.web.automation.utilities.CommonVariables;

public class ExportResultInGoogleDrive {
	
	protected static String jiraStausCSVFile=System.getProperty("user.dir").replace("\\", "/") + "/" + "testdata/";
	protected static String resultFilePath=null;
	protected static String resultSheeet=null;
	private static String timestamp = new SimpleDateFormat("yyyy_MM_dd_hh_mm_sss_a").format(new Date());
	private static String testEnvironment=null;

	public static void main(String[] args) {
		try {
			
			Properties config_prop = new Properties();
			try{
				FileReader reader;
				reader = new FileReader("config.properties");
				config_prop.load(reader);
			}
			catch (IOException e) {
				System.out.println("Failed to fetch/read 'config.properties' file.");
			}
			
			ReadCSVFile csvRead = new ReadCSVFile();
			// Update Test Case execution status wrt each release
			jiraStausCSVFile=config_prop.getProperty("jiraResultSheet")+".csv";
			List<Map<String,String>> jiraStatus = ReadCSVFile.GetCSVData(null, jiraStausCSVFile);
			System.out.println(jiraStatus.size());
			JiraIntegration jiraIntegrate = new JiraIntegration();
			for(Map<String,String> eachTestInfo : jiraStatus){
				jiraIntegrate.EditJiraStatus(eachTestInfo.get("jiraId").trim(), eachTestInfo.get("status").trim(), "3.8", "Chrome");
			}
			
			resultFilePath = CommonVariables.RootResultFolderPath.get() + "/Results.csv";
			String month_Year  = new SimpleDateFormat("MMM").format(Calendar.getInstance().getTime())+"_"+new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());
			testEnvironment=config_prop.getProperty("environment");
			String workSpreadSheet = testEnvironment+"_"+month_Year;
			ReadCSVFile.createFileContainsFailedTC(resultFilePath, Optional.<String>absent());
			
			ReadCSVFile.createFileContainsFailedAndSkipTC(resultFilePath, Optional.<String>absent());
			
			//Create Google SpreadSheet API Service
			GoogleSpreadsheetAPI googleApiObj = new GoogleSpreadsheetAPI();
			
			// Get all Test Cases 
			List<Map<String,String>> allresults = googleApiObj.getCSVdata(null, resultFilePath);
			System.out.println("All TC Count: "+allresults.size() +"--***-- "+allresults);
			allresults.clear();
			
			// Get all Skipped Test Case list
			allresults = ReadCSVFile.getAllSkippedTestsDetail(resultFilePath);
			System.out.println("Skipped Count: "+allresults.size() +"--***-- "+allresults);
			allresults.clear();
			
			// Get all Failed Test Case list
			allresults = ReadCSVFile.getAllFailedTestsDetail(resultFilePath);
			System.out.println("Failed TC Count: "+allresults.size() +"--***-- "+allresults);
			allresults.clear();
			
			// Get all Failed and Skipped Test Case list
			allresults = ReadCSVFile.getAllFailedAndSkippedTestsDetail(resultFilePath);
			System.out.println("Skipped and Failed TC Count: "+allresults.size() +"--***-- "+allresults);

			WorksheetEntry worksheetEntry = googleApiObj.getYourWorkSheetEntry(Optional.<String>absent(), Optional.of(workSpreadSheet), true, false,jiraStausCSVFile+resultSheeet+".xlsx");
			googleApiObj.uploadASpreadSheetInGoogleDrive("AutomationTestResults_"+timestamp, System.getProperty("user.dir")+ "/testdata/"+resultSheeet+".xlsx");
			googleApiObj.createASpreadSheetInGoogleDrive("AutomationTestResults_"+timestamp);
			
			// Insert all the Failed and Skipped Test Cases.
			googleApiObj.insertDataInWorkSheet(worksheetEntry, allresults); 	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}