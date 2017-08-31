package com.web.automation.googledrive;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import com.google.common.base.Optional;
import com.google.gdata.client.docs.DocsService;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.docs.DocumentListEntry;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.InvalidEntryException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.web.automation.fileutils.ReadCSVFile;

public class GoogleSpreadsheetAPI 
{
	protected String USERNAME;
	protected String PASSWORD;
	public SpreadsheetService service;
	public List<Map<String,String>> allresults = new ArrayList<Map<String,String>>();
	private static String mailError = "";
	public String worksheetname = new SimpleDateFormat("YYYY_MMM_dd_hh:ss").format(Calendar.getInstance().getTime());
	public String spreadsheetname = "AutomationResults_"+ new SimpleDateFormat("MMM").format(Calendar.getInstance().getTime());
	static List<String> allsheetColumnNames=new ArrayList<String>();
	private Properties config_prop;

	public GoogleSpreadsheetAPI() throws Exception {
		config_prop = new Properties();
		try{
			FileReader reader;
			reader = new FileReader("config.properties");
			config_prop.load(reader);
		}
		catch (IOException e) {
			System.out.println("Failed to fetch/read 'config.properties' file.");
		}
		USERNAME = config_prop.getProperty("gmailUser");
		PASSWORD = config_prop.getProperty("gmailPassword");
		AuthenticateSession();
	}

	/**
	 * @author Cigniti
	 * @description: Google Credentials
	 * @param GmailUserName
	 * @param GmailPassword
	 */
	public GoogleSpreadsheetAPI(String GmailUserName, String GmailPassword){
		try{
		USERNAME = GmailUserName;
		PASSWORD = GmailPassword;
		AuthenticateSession();
		}
		catch(Exception ex){
		}
	}
	
	/**
	 * @author Cigniti
	 * @description : Google Drive Authentication
	 * @throws Exception
	 */
	private void AuthenticateSession() throws Exception {
		String p12_location = System.getProperty("user.dir").replace("\\", "/") + "/" + "Tool/"+config_prop.getProperty("p12KeyName")+".p12";
		File p12 = new File(p12_location);
        try {
            HttpTransport httpTransport = new NetHttpTransport();
            JacksonFactory jsonFactory = new JacksonFactory();
            String [] SCOPESArray= {"https://spreadsheets.google.com/feeds", "https://spreadsheets.google.com/feeds/spreadsheets/private/full", "https://docs.google.com/feeds"};
            final List<String> SCOPES = Arrays.asList(SCOPESArray);
            GoogleCredential credential = new GoogleCredential.Builder()
            .setTransport(httpTransport)
            .setJsonFactory(jsonFactory)
            .setServiceAccountId(config_prop.getProperty("serviceAccountId"))
            .setServiceAccountScopes(SCOPES)
            .setServiceAccountPrivateKeyFromP12File(p12)
            .build();
            service = new SpreadsheetService(config_prop.getProperty("spreadSheetServiceName"));
            service.setOAuth2Credentials(credential);
            System.out.println("Successfully Authentication done for Google Spreadsheets!!!!");
        }
        catch (Exception e) {
            System.out.println("ERROR: Google Drive Authentication ERROR!!!!");
            e.printStackTrace();
        }
	}

	/**
	 * @author Cigniti
	 * @param searchCriteria
	 * @param fileFullyQualifiedPath
	 * @return
	 */
	public List<Map<String,String>> getCSVdata(Map<String, String> searchCriteria, String fileFullyQualifiedPath){
		allresults = ReadCSVFile.GetCSVData(searchCriteria, fileFullyQualifiedPath);
		return allresults;
	}
	
	/**
	 * @author Cigniti
	 * @param spreadSheet
	 * @return
	 */
	public com.google.gdata.data.spreadsheet.SpreadsheetEntry getSpreadSheetEntry(String spreadSheet){
		try{
			URL SPREADSHEET_FEED_URL = new URL("https://spreadsheets.google.com/feeds/spreadsheets/private/full");
			SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL,SpreadsheetFeed.class);
			List<com.google.gdata.data.spreadsheet.SpreadsheetEntry> spreadsheets = feed.getEntries();
			if (spreadsheets.isEmpty()){
				System.out.println("There were no spreadsheets found in User drive.");
				return null;
			}
			else{
				for(com.google.gdata.data.spreadsheet.SpreadsheetEntry spreadsheet:spreadsheets){
					String SpreadsheetName = spreadsheet.getTitle().getPlainText();
					if(SpreadsheetName.equalsIgnoreCase(spreadSheet.trim())){
						return spreadsheet;
					}
				}
				System.out.println("There is no spreadsheet with name as "+spreadSheet);
				return null;
			}
		}
		catch(Exception ex){
			System.out.println("Spreadsheet not found.");
			return null;
		}
	}
	
	/**
	 * @author Cigniti
	 * @description: Read workbook from google drive
	 * @param worksheetname
	 * @param workBookName
	 * @param createIfNotFound
	 * @param uploadFromTemplateIfNotFound
	 * @param filePathToUpload
	 * @return
	 */
	public WorksheetEntry getYourWorkSheetEntry(Optional<String> worksheetname,Optional<String> workBookName, Boolean createIfNotFound, Boolean uploadFromTemplateIfNotFound, String filePathToUpload){
		try
		{
			this.spreadsheetname = workBookName.isPresent()?workBookName.get():this.spreadsheetname;
			this.worksheetname = worksheetname.isPresent()?worksheetname.get():this.worksheetname;
			URL workBookFeed_URL = null;
			com.google.gdata.data.spreadsheet.SpreadsheetEntry spreadsheet = getSpreadSheetEntry(this.spreadsheetname);
			if(spreadsheet!=null){
				workBookFeed_URL = spreadsheet.getWorksheetFeedUrl();
			}
			else{
				if(createIfNotFound || uploadFromTemplateIfNotFound){
					if(uploadFromTemplateIfNotFound){
						if(uploadASpreadSheetInGoogleDrive(this.spreadsheetname.trim(),filePathToUpload)){
							System.out.println("uploaded successfully. So there is no need to create new one.");
							spreadsheet = getSpreadSheetEntry(this.spreadsheetname);
							if(spreadsheet!=null)
								workBookFeed_URL = spreadsheet.getWorksheetFeedUrl();
						}
						else if(createIfNotFound){
							workBookFeed_URL = createASpreadSheetInGoogleDrive(this.spreadsheetname.trim());		
						}
					}
					else if(createIfNotFound){
						workBookFeed_URL = createASpreadSheetInGoogleDrive(this.spreadsheetname.trim());		
					}
				}
				else{
					System.out.println("Spreadsheet/Workbook not found. Does not created/uploaded due to parameters,'createIfNotFound' & 'uploadFromTemplateIfNotFound', value provided as false.");
					return null;
				}
			}
			if(workBookFeed_URL!=null){
				WorksheetFeed worksheetFeed = service.getFeed(workBookFeed_URL, WorksheetFeed.class);
				List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
				//System.out.println("Total Worksheets found in selected Spreadsheet/WorkBook: "+ worksheets.size());
				//Fetch the list feed of the work sheet.
				WorksheetEntry worksheet = null;
				for (WorksheetEntry worksheetEntry : worksheets) 
				{
					String WorkSheetName = worksheetEntry.getTitle().getPlainText();
					if(WorkSheetName.equals(this.worksheetname)){
						//System.out.println("WorksheetName: "+WorkSheetName);
						//System.out.println("Worksheet Feed URL "+worksheetEntry.getListFeedUrl());
						return worksheetEntry;
					}
				}
				if(createIfNotFound || uploadFromTemplateIfNotFound){
				    worksheet = new WorksheetEntry();
				    worksheet.setTitle(new PlainTextConstruct(this.worksheetname));
				    worksheet.setColCount(10);
				    worksheet.setRowCount(500);
				    worksheet = service.insert(workBookFeed_URL, worksheet);
				    //System.out.println("WorksheetName: "+this.worksheetname);
					//System.out.println("Worksheet Feed URL "+worksheet.getListFeedUrl());
				    return worksheet;
				}
				else{
					System.out.println("Failed to get Worksheet Feed URL. It looks like it ,"+this.worksheetname+", is not available under '"+this.spreadsheetname+"' workbook.");
					return null;
				}
			}
			else{
				System.out.println("Failed to get WorkBook.");
				return null;
			}
		}
		catch(Exception ex){
			System.out.println("Exception in '"+Thread.currentThread().getStackTrace()[2].getMethodName()+"' method. "+ex.getLocalizedMessage());
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * @author Cigniti
	 * @description: Spreadsheet to upload in google drive
	 * @param fileName
	 * @param filePath
	 * @return
	 */
	public boolean uploadASpreadSheetInGoogleDrive(String fileName, String filePath){
		try{
			//@Cigniti: after some research I found this answer. We cannot create a new spreadsheet in google drive with Google Spreadsheet API.
			//For creating and uploading new Spreadsheet or any other kind of document which google drive supports we have to use Google Drive api.
			//Caution: It'll upload new workbook, even the same name sheet is already their.
			DocsService docsService = new DocsService(config_prop.getProperty("spreadsheetNameToUploadInDrive"));
			docsService.setUserCredentials(USERNAME, PASSWORD);
			URL GOOGLE_DRIVE_FEED_URL = new URL("https://docs.google.com/feeds/default/private/full/");
			DocumentListEntry uploadFile = new DocumentListEntry();
	        uploadFile.setTitle(new PlainTextConstruct(fileName));
	        uploadFile.setFile(new File(filePath), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); //multipart/related
	        uploadFile = docsService.insert(GOOGLE_DRIVE_FEED_URL, uploadFile);
        return true;
		}
		catch(Exception ex){
			ex.printStackTrace();
		return false;
		}
	}
	
	/**
	 * @author Cigniti
	 * @description: Spreadsheet to create in google drive
	 * @param fileName
	 * @return
	 */
	public URL createASpreadSheetInGoogleDrive(String fileName){
		try{
			//@Cigniti: after some research I found this answer. We cannot create a new spreadsheet in google drive with Google Spreadsheet API.
			//For creating and uploading new Spreadsheet or any other kind of document which google drive supports we have to use Google Drive api.
			// Caution: It'll create new workbook, even the same name sheet is already their.
			DocsService docsService = new DocsService(config_prop.getProperty("spreadsheetNameToCreateInDrive"));
			docsService.setUserCredentials(USERNAME, PASSWORD);
			URL GOOGLE_DRIVE_FEED_URL = new URL("https://docs.google.com/feeds/default/private/full/");
			DocumentListEntry documentListEntry = new com.google.gdata.data.docs.SpreadsheetEntry();
			documentListEntry.setTitle(new PlainTextConstruct(this.spreadsheetname.trim()));
			documentListEntry = docsService.insert(GOOGLE_DRIVE_FEED_URL, documentListEntry);
			com.google.gdata.data.docs.SpreadsheetEntry docsSpreadsheet = (com.google.gdata.data.docs.SpreadsheetEntry) documentListEntry.getAdaptedEntry();
			System.out.println("Spreadsheet Name: "+docsSpreadsheet.getTitle().getPlainText());
			return docsSpreadsheet.getWorksheetFeedUrl();
		}
		catch(Exception ex){
			ex.printStackTrace();
		return null;
		}
	}
	
	/**
	 * @author Cigniti
	 * @description: Insert data in google workbook
	 * @param worksheetEntry
	 * @param dataToInsert
	 * @return
	 */
	public boolean insertDataInWorkSheet(WorksheetEntry worksheetEntry, List<Map<String,String>> dataToInsert){
		try{
			Set<String> keyValues = dataToInsert.get(0).keySet();
			// Get Cell feed and insert Column heading. 
			URL cellFeedUrl = worksheetEntry.getCellFeedUrl();
			
		    service.getFeed(cellFeedUrl, CellFeed.class);
			int i =1;
			for(String keyValue : keyValues){
				//Insert the new cells (works when worksheet has no cells yet)
				URL celFeedUrl = worksheetEntry.getCellFeedUrl();
				service.insert(celFeedUrl, new CellEntry(1,i,keyValue));
				i++;
			}
			URL listFeedUrl = worksheetEntry.getListFeedUrl();
			service.getFeed(listFeedUrl, ListFeed.class);
			for (Map<String,String> eachRowData : dataToInsert){
				ListEntry row = new ListEntry();
				for(String keyValue : keyValues){
					row.getCustomElements().setValueLocal(keyValue, eachRowData.get(keyValue));
				}
				try{
				service.insert(listFeedUrl, row);
				}
				catch(InvalidEntryException ex){
					ex.printStackTrace();
					System.out.println("Failed to following enter in worksheet: "+eachRowData);
				}
			}
			System.out.println("Successfully Updated the Google Spreadsheet!");
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * @author Cigniti
	 * @description: Get spreadsheet data from google drive
	 * @param worksheet
	 * @return
	 */
	public List<Map<String,String>> getWorkSheetData(WorksheetEntry worksheet){
		// Fetch the list feed of the worksheet.
	    URL listFeedUrl = worksheet.getListFeedUrl();
	    ListFeed listFeed;
	    List<Map<String,String>> allRowData = new ArrayList<Map<String,String>>();
	    try {
	    	listFeed = service.getFeed(listFeedUrl, ListFeed.class);
	    	// TODO: Choose a row more intelligently to get all Column name.
	        ListEntry firstRow = listFeed.getEntries().get(0);
	        List<String> columnName = new ArrayList<String>();
	        columnName.addAll(firstRow.getCustomElements().getTags());
	    	// Iterate through each row, printing its cell values.
	    	for (ListEntry row : listFeed.getEntries()) {
		        Map<String,String> rowdata = new HashMap<String, String>();
	    		// Iterate over the remaining columns, and print each cell value
	    		int counter = 0;
	    		for (String tag : row.getCustomElements().getTags()) {
	    			try{
	    			rowdata.put(columnName.get(counter), row.getCustomElements().getValue(tag).trim());
	    			}
	    			catch(NullPointerException ex){}
	    			counter++;
	    		}
	    		allRowData.add(rowdata);
	    	}
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allRowData;
	}
}