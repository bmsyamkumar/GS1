package com.web.automation.testmanagement;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.web.automation.accelerators.ActionsLibrary;
import com.web.automation.utilities.CommonVariables;
import com.web.automation.utilities.ConfigManager;

public class JiraIntegration {
	protected HttpURLConnection con;
	protected String Authentication = ConfigManager.getProperties().getProperty("jiraAuth");
	private static List<Map<String, String>> JIRA_CASES_MAPPINGS = new ArrayList<Map<String, String>>();
	private static Map<String, Map<String, String>> JIRA_STATUS;

	/*The use of this Class is to hit the Jira REST API to update the jira test case. 
	 * It has only one method "EditJiraStatus" to update the jira status and it 3 arguments.
	 * Example Call
	 *  	  JiraIntegration JI = new JiraIntegration();
	 *		  JI.EditJiraStatus("TCM-011","FAIL","2.10");
	*/
	/**
	 * @author Cigniti
	 * @description: Edit Jira Status
	 * @param IssusID
	 * @param Status
	 * @param ReleaseVersion
	 * @param DeviceName
	 */
	public void EditJiraStatus(String IssusID,String Status,String ReleaseVersion,String DeviceName){
		String updateTCstatus_json = "";
		String DeviceType = DeviceName;
		String issid = IssusID;
		String release = ReleaseVersion;
		String Issue_Status = Status + "_" + DeviceType + "_" + release;
		String JiraURL =ConfigManager.getProperties().getProperty("jiraUrl")+"/rest/api/latest/issue/" + issid;
		try{
			URL url = new URL(JiraURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("PUT");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Authorization", "Basic " + Authentication);  
			/*  To provide different user credential 
			 * Please use "http://www.base64encode.org/" web-site. 
			 * You need to pass username:password combination and click on the Encode button to generate
			 * credential in Base64 format.
			 * <example> dgarg:wrongpassword123 </example> 
			 * */

			if(DeviceType.trim().toLowerCase().contains("iphone")){
				updateTCstatus_json = "{\"update\":{\"customfield_11314\":[" +
						//Normal Pass Fail
						//Pass Fail for iPhone-Simulator
						"{\"remove\":\"FAIL_" + DeviceType + "_" + release + "\"}," +
						"{\"remove\":\"PASS_" + DeviceType + "_" + release + "\"}," +
						"{\"add\":\"" + Issue_Status + "\"}]}}";
			}else if(DeviceType.trim().toLowerCase().contains("ipad")){
				updateTCstatus_json = "{\"update\":{\"customfield_11314\":[" +
						//Normal Pass Fail
						//Pass Fail for iPhone-Simulator
						"{\"remove\":\"FAIL_" + DeviceType + "_" + release + "\"}," +
						"{\"remove\":\"PASS_" + DeviceType + "_" + release + "\"}," +
						"{\"add\":\"" + Issue_Status + "\"}]}}";
			}else if(DeviceType.trim().toLowerCase().contains("android")){
				updateTCstatus_json = "{\"update\":{\"customfield_11314\":[" +
						//Normal Pass Fail
						//Pass Fail for iPhone-Simulator
						"{\"remove\":\"FAIL_" + DeviceType + "_" + release + "\"}," +
						"{\"remove\":\"PASS_" + DeviceType + "_" + release + "\"}," +
						"{\"add\":\"" + Issue_Status + "\"}]}}";
			}
			else{
				updateTCstatus_json = "{\"update\":{\"customfield_11314\":[" +
						//Normal Pass Fail
						//Pass Fail for iPhone-Simulator
						"{\"remove\":\"FAIL_" + DeviceType + "_" + release + "\"}," +
						"{\"remove\":\"PASS_" + DeviceType + "_" + release + "\"}," +
						"{\"add\":\"" + Issue_Status + "\"}]}}";
			}
			OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream());
			os.write(updateTCstatus_json);
			os.flush();
			os.close();	
			if(conn.getResponseCode() == 204){
				System.out.println("Jira status is successfully updated for the issue_id: "+ issid);
			}else{
				System.out.println("Jira status is not updated for the issue_id: "+ issid + " as the jira response code is not 204. Actual code is: " + conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream()))); 
			String output;
			conn.disconnect();
		} catch (MalformedURLException e) {			  
			e.printStackTrace();
		} catch (IOException e) {		 
			e.printStackTrace();
		} catch (Exception e) {		 
			e.printStackTrace();
		}
	}
	
	
	public void EditJiraStatus(String IssusID,String Status,String ReleaseVersion){
		String updateTCstatus_json = "";
		
		String issid = IssusID;
		String release = ReleaseVersion;
		String Issue_Status = Status + "_" + release;
		String JiraURL =ConfigManager.getProperties().getProperty("jiraUrl")+"/rest/api/latest/issue/" + issid;
		try{
			URL url = new URL(JiraURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("PUT");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Authorization", "Basic " + Authentication);  
			/*  To provide different user credential 
			 * Please use "http://www.base64encode.org/" web-site. 
			 * You need to pass username:password combination and click on the Encode button to generate
			 * credential in Base64 format.
			 * <example> dgarg:wrongpassword123 </example> 
			 * */
			updateTCstatus_json = "{\"update\":{\"customfield_11314\":[" +			
						"{\"add\":\"" + Issue_Status + "\"}]}}";
			
			OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream());
			os.write(updateTCstatus_json);
			os.flush();
			os.close();	
			if(conn.getResponseCode() == 204){
				System.out.println("Jira status is successfully updated for the issue_id: "+ issid);
			}else{
				System.out.println("Jira status is not updated for the issue_id: "+ issid + " as the jira response code is not 204. Actual code is: " + conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream()))); 
			String output;
			conn.disconnect();
		} catch (MalformedURLException e) {			  
			e.printStackTrace();
		} catch (IOException e) {		 
			e.printStackTrace();
		} catch (Exception e) {		 
			e.printStackTrace();
		}
	}

	/**
	 * @author Cigniti
	 * @description: load jira mapping sheet
	 * @param JiraMappingSheetPath
	 * @throws IOException
	 */
	public void LoadJiraMappingSheet(String JiraMappingSheetPath) throws IOException{

		BufferedReader br = new BufferedReader(new FileReader(JiraMappingSheetPath));
		String line =  null;
		CommonVariables.jiramap.get().clear();
		HashMap<String,String> map = new HashMap<String, String>();
		line=br.readLine();
		while((line=br.readLine())!=null){
			String str[] = line.split(",");
			try{
				CommonVariables.jiramap.get().put(str[0], str[1]);
			}
			catch(ArrayIndexOutOfBoundsException e){
				e.getLocalizedMessage();
			}
		}
	}

	/**
	 * @description : Get sub list from the provided list based on Key-Value pair i.e. add each Map entry in another list which contains user provided key-Value pair
	 * @param allData  : Complete List of Map contains all data
	 * @param keyName : Key name whose value need to compare
	 * @param Value : Value which need to compare based on Key value provided. Comparision is case-insensitive.
	 * @return Sub list contains all those entries which contains provided Key-value pair
	 */
	public static List<Map<String,String>> getSubListBasedOnSearchCriteria(List<Map<String,String>> allData, String keyName, String Value){
		List<Map<String,String>> qualifiedData = new ArrayList<Map<String,String>>();
		for(Map<String,String> entry : allData){
			try{
				if(entry.get(keyName).equalsIgnoreCase(Value)){ // convert keyvalue in lower case because Google API returns all the header/Tag name in lower case.
					qualifiedData.add(entry);
				}
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return qualifiedData;
	}
	
	/**
	 * @description: Get the associated Test Case Id and other details.
	 * @param testMethodName  : test Method name from which associated test case Id need to find out
	 * @param className :  Class Name  : Leave blank, if not require.
	 * @return Map<String,String> contains the details of associated test case(s) Id and other details. If linked with multiple test case Id, then deliminated with || 
	 */
	public Map<String,String> associatedJiraIdDetail(List<Map<String,String>> jiraTestCaseMapping,String testMethodName, String className){
		try{
			List<Map<String,String>> linkJiraDetail = getSubListBasedOnSearchCriteria(jiraTestCaseMapping, "MethodName", testMethodName);
			if(linkJiraDetail!=null && linkJiraDetail.size()>0 && !className.isEmpty()){
				linkJiraDetail = getSubListBasedOnSearchCriteria(linkJiraDetail, "ClassName", className);
			}
			List<HashSet<String>> info = new ArrayList<HashSet<String>>();
			if(linkJiraDetail!=null && linkJiraDetail.size()>0){
				Set<String> keyValue = linkJiraDetail.get(0).keySet();  
				List<String> keyValues = new ArrayList<>();
				keyValues.addAll(keyValue);
				boolean addItemInList = true;
				for(Map<String,String> data : linkJiraDetail){
					int index = 0;
					for(String keyName: keyValues){
						if(addItemInList){
							info.add(new HashSet<String>());
						}
						info.get(index).add(data.get(keyName));
						index++;
					}
					addItemInList = false;
				}
				HashMap<String,String> associatedJiraIdInfo = new HashMap<String,String>();
				int index = 0;
				for(HashSet<String> uniqueRecord : info){
					associatedJiraIdInfo.put(keyValues.get(index), StringUtils.join(uniqueRecord.toArray(), "||"));
					index++;
				}
				return associatedJiraIdInfo;
			}
			else{	
				return new HashMap<String,String>(); 
			}
		}
		catch(Exception ex){
			return new HashMap<String,String>(); 
		}
	}
	
	/**
	 * @author Cigniti
	 * @description add comment in jira test case
	 * @param IssusID
	 * @param Comment
	 */
	public void AddCommentToJiraTestCase(String IssusID,String Comment){
		boolean UpdateFlag = false;		
		String JiraURL =ConfigManager.getProperties().getProperty("jiraUrl")+"/rest/api/2/issue/" + IssusID + "/comment";
		String Jira_Comment_json = "";
		try{
			URL url = new URL(JiraURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", "Basic " + Authentication); 
			Jira_Comment_json = "{\"body\": \"" + Comment + "\"}";
			OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream());
			os.write(Jira_Comment_json);
			os.flush();
			os.close();
			System.out.println(conn.getResponseMessage());
			if(conn.getResponseCode() == 201){
				System.out.println("Comment is successfully added for the issue_id: "+ IssusID);
			}else{
				System.out.println("Comment is not successfully added for the issue_id: "+ IssusID + " as the jira response code is not 201. Actual code is: " + conn.getResponseCode());
			}
			conn.disconnect();
		}catch (MalformedURLException e) {			  
			e.printStackTrace();

		} catch (IOException e) {		 
			e.printStackTrace();
		} catch (Exception e) {		 
			e.printStackTrace();
		}
	}

	/**
	 * @author Cigniti
	 * @description: edit automation status of jira test case in Jira
	 * @param IssusID
	 * @param AutomationStatus
	 */
	public void EditAutomationStatus(String IssusID,String AutomationStatus){
		String updateTCstatus_json = "";
		boolean UpdateFlag = false;
		String JiraURL =ConfigManager.getProperties().getProperty("jiraUrl")+"/rest/api/latest/issue/" + IssusID;
		try{
			if(AutomationStatus.equals("Automation")){
				UpdateFlag = true;
				updateTCstatus_json = "{\"update\":{\"customfield_11204\":[" +
						"{\"set\": [{\"value\": \"" + AutomationStatus + "\"}]}]}}";
			}else if(AutomationStatus.equals("Cannot_Be_Automate")){
				UpdateFlag = true;
				updateTCstatus_json = "{\"update\":{\"customfield_11204\":[" +
						"{\"set\": "
						+ "[{\"value\": \"" + "Manual" + "\"},"
						+   "{\"value\": \"" + "Cannot_Be_Automate" + "\"}"
						+ "]}]}}";
			}else if(AutomationStatus.equals("OnHold")){
				UpdateFlag = true;
				updateTCstatus_json = "{\"update\":{\"customfield_11204\":[" +
						"{\"set\": "
						+ "[{\"value\": \"" + "Manual" + "\"},"
						+   "{\"value\": \"" + "On_Hold_For_Automation" + "\"}"
						+ "]}]}}";
			}
			if(UpdateFlag){
				URL url = new URL(JiraURL);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("PUT");
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setRequestProperty("Accept", "application/json");
				conn.setRequestProperty("Authorization", "Basic " + Authentication); 

				OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream());
				os.write(updateTCstatus_json);
				os.flush();
				os.close();		
				if(conn.getResponseCode() == 204){
					System.out.println("Jira status is successfully updated for the issue_id: "+ IssusID);
				}else{
					System.out.println("Jira status is not updated for the issue_id: "+ IssusID + " as the jira response code is not 204. Actual code is: " + conn.getResponseCode());
				}
				conn.disconnect();
			}

		} catch (MalformedURLException e) {	
			System.out.println("Jira status is not updated for the issue_id: "+ IssusID + ". Need to update Staus as: "+AutomationStatus);
			e.printStackTrace();

		} catch (IOException e) {	
			System.out.println("Jira status is not updated for the issue_id: "+ IssusID + ". Need to update Staus as: "+AutomationStatus);
			e.printStackTrace();
		} catch (Exception e) {	
			System.out.println("Jira status is not updated for the issue_id: "+ IssusID + ". Need to update Staus as: "+AutomationStatus);
			e.printStackTrace();
		}
	}

	/**
	 * TO_BE_CALLED_AFTER_EACH_METHOD</br>
	 * Method will iterate over provided Map and attempts to push its status in
	 * global Map. If status is already present as FAIL. It will push the status
	 * in other references.
	 * 
	 * @param jiraStatus:
	 *            Key <b>jiratestcaseid</b> will contain <b>TCM-ID</b> and value
	 *            will be a Map with <b>status</b> and <b>method</b>
	 * @throws IllegalArgumentException
	 * @throws Exception
	 */
	public static void updateStatusInJiraTracker(Map<String, Map<String, String>> jiraStatus)
			throws IllegalArgumentException, Exception {
		if (jiraStatus != null) {
			iteratateOverMethodStatus: for (Entry<String, Map<String, String>> entry : jiraStatus.entrySet()) {
				iterateOverGlobal: for (Map<String, String> JIRA_CASE_MAPPING : JIRA_CASES_MAPPINGS) {
					if (JIRA_CASE_MAPPING.containsKey("JiraTCID")) {
						if (JIRA_CASE_MAPPING.get("JiraTCID") != null
								&& !JIRA_CASE_MAPPING.get("JiraTCID").isEmpty()) {
							if (entry.getKey().replaceAll(" ", "").toLowerCase().equals(
									(JIRA_CASE_MAPPING.get("jiratestcaseid").replaceAll(" ", "").toLowerCase()))) {
								if (entry.getValue() == null) {
									JIRA_CASE_MAPPING.put("status", "SKIP_NOT_EXECUTED");
									JIRA_CASE_MAPPING.put("otherreferences", "REVIEW");
									continue iteratateOverMethodStatus;
								}
								if (JIRA_CASE_MAPPING.get("status").isEmpty()
										|| JIRA_CASE_MAPPING.get("status").toLowerCase().contains("pass")) {
									JIRA_CASE_MAPPING.put("status", entry.getValue().get("status"));
									break iterateOverGlobal;
								} else {
									String otherRef = JIRA_CASE_MAPPING.get("otherreferences");
									JIRA_CASE_MAPPING.put("otherreferences",
											otherRef + "; " + entry.getValue().get("status") + ": "
													+ entry.getValue().get("methodname"));
									break iterateOverGlobal;
								}
							}
						}
					} else {
						throw new IllegalArgumentException("");
					}
				}
			}
		}
	}

	/**
	 * @description: @description: Get the associated Test Case Id and other
	 *               details from 'TestMethodsWithOwnerAndJiraTestCaseID' sheet
	 *               based on Testmethod and Class name provided.
	 * @param testMethodName
	 *            : test Method name from which associated test case Id need to
	 *            find out
	 * @param className
	 *            : Class Name : Leave blank, if not require.
	 * @return Map<String,String> contains the details of assciated test case(s)
	 *         Id and other details. If linked with multiple test case Id, then
	 *         deliminated with ||
	 */
	public Map<String, String> associatedjiratestcaseidDetail(String testMethodName, String className) {
		try {
			List<Map<String, String>> linkJiraDetail = getSubListBasedOnSearchCriteria(JIRA_CASES_MAPPINGS, "methodname", testMethodName);
			if (linkJiraDetail != null && linkJiraDetail.size() > 0 && !className.isEmpty()) {
				linkJiraDetail = getSubListBasedOnSearchCriteria(linkJiraDetail, "classname",className);
			}
			List<HashSet<String>> info = new ArrayList<HashSet<String>>();
			if (linkJiraDetail != null && linkJiraDetail.size() > 0) {
				Set<String> keyValue = linkJiraDetail.get(0).keySet();
				List<String> keyValues = new ArrayList<>();
				keyValues.addAll(keyValue);
				boolean addItemInList = true;
				for (Map<String, String> data : linkJiraDetail) {
					int index = 0;
					for (String keyName : keyValues) {
						if (addItemInList) {
							info.add(new HashSet<String>());
						}
						info.get(index).add(data.get(keyName));
						index++;
					}
					addItemInList = false;
				}
				HashMap<String, String> associatedjiratestcaseidInfo = new HashMap<String, String>();
				int index = 0;
				for (HashSet<String> uniqueRecord : info) {
					associatedjiratestcaseidInfo.put(keyValues.get(index),
							StringUtils.join(uniqueRecord.toArray(), "||"));
					index++;
				}
				return associatedjiratestcaseidInfo;
			} else {
				return new HashMap<String, String>();
			}
		} catch (Exception ex) {
			return new HashMap<String, String>();
		}
	}

	/**
	 * @return getter for JIRA_STATUS
	 */
	public static Map<String, Map<String, String>> getJiraStatus() {
		return JIRA_STATUS;
	}

	/**
	 * @param tcmId
	 *            test case id like <b><i>TCM-XXXXX</b></i>
	 * @param passFlag
	 *            Boolean <b>true</b> for <b>PASS</b> and <b>false</b> for
	 *            <b>FAIL</b>
	 * @param javaMethodName
	 *            name of the current method
	 */
	protected void markJiraStatus(String tcmId, String status, String javaMethodName) {
		Map<String, Map<String, String>> stepStatus = new HashMap<>();
		Map<String, String> tcmValue = new HashMap<>();
		tcmValue.put("status", status);
		tcmValue.put("method", javaMethodName);
		stepStatus.put(tcmId, tcmValue);
		if (!getJiraStatus().containsKey(tcmId))
			if (getJiraStatus().get(tcmId) == null || !getJiraStatus().get(tcmId).get("status").equals("SKIP")) {
				if (getJiraStatus().get(tcmId) == null) {
					getJiraStatus().putAll(stepStatus);
					return;
				} else if (!getJiraStatus().get(tcmId).get("status").equals("FAIL")) {
					getJiraStatus().putAll(stepStatus);
					return;
				}
			}
	}
	
	/**
	 * @author Cigniti
	 * @description: jira status
	 * @param methodName
	 */
	public static void JiraStatus(List<Map<String,String>> jiraTestCaseMapping, String methodName) {
		JIRA_STATUS = new HashMap<String, Map<String, String>>();
		List<String> tcmKeys = new ArrayList<>();
		for (Map<String, String> JIRA_CASES_MAPPING : jiraTestCaseMapping) {
			if (JIRA_CASES_MAPPING.get("methodname").trim().toLowerCase().equals(methodName.trim().toLowerCase())) {
				tcmKeys.add(JIRA_CASES_MAPPING.get("JiraTCID"));
			}
		}
		for (String tcmKey : tcmKeys)
			JIRA_STATUS.put(tcmKey, null);
	}
}