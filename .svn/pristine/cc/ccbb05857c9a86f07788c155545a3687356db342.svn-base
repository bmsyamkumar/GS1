package com.web.automation.bmp;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.testng.Assert;
import com.web.automation.logs.CustomLogs;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarLog;
import net.lightbody.bmp.core.har.HarNameVersion;
import net.lightbody.bmp.core.har.HarPage;
import net.lightbody.bmp.core.har.HarPageTimings;
import net.lightbody.bmp.proxy.ProxyServer;

public class BMP_Proxy_Controller {
	public static BrowserMobProxyServer BMPServer;
	public static ProxyServer Server;
	public static ArrayList<HashMap<String, String>> APINetworkCalls = new ArrayList<HashMap<String, String>>();
	public static HarLog APIharlog = new HarLog();
	public static Har APIhar = new Har();
	public static Har Serverhar = new Har();
	public static boolean IsAnyAutomationAPIFailed = false;
	public static void StartBMPServer() throws IOException{
		BMPServer=new BrowserMobProxyServer();
		Server=BMPServer.startBMPServer();
	}

	public static void StopBMPServer(){
		BMPServer.stopServer();
	}

	/**
	 * @author Cigniti
	 * @param csvPath
	 */
	public static void saveNetworkTrafficToCSV(String csvPath){
		try{
			FileWriter fw = new FileWriter(csvPath);
			PrintWriter out = new PrintWriter(fw);
			out.println("METHOD,Response_Code,URL");
			for (int i = 0; i < BMP_Proxy_Controller.APINetworkCalls.size(); i++) {
				HashMap<String, String> requestDetails = BMP_Proxy_Controller.APINetworkCalls.get(i);
				out.println(requestDetails.get("Method") + "," + requestDetails.get("ResponseCode") + "," + requestDetails.get("URL"));
			} 
			out.flush();
			out.close();
			fw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @author Cigniti
	 * @param csvPath
	 */
	public static void saveFailedNetworkTrafficToCSV(String csvPath){
		try{
			FileWriter fw = new FileWriter(csvPath);
			PrintWriter out = new PrintWriter(fw);
			out.println("METHOD,Response_Code,URL");
			for (int i = 0; i < BMP_Proxy_Controller.APINetworkCalls.size(); i++) {
				HashMap<String, String> requestDetails = BMP_Proxy_Controller.APINetworkCalls.get(i);
				if(!requestDetails.get("ResponseCode").toString().startsWith("2")){
					IsAnyAutomationAPIFailed = true;
					out.println(requestDetails.get("Method") + "," + requestDetails.get("ResponseCode") + "," + requestDetails.get("URL"));
				}		
			} 
			out.flush();
			out.close();
			fw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @author Cigniti
	 * @return
	 */
	public static String GetFailedAPICalls(){
		String failedAPICalls = "METHOD"+"\t\t" + "ResponseCode"+"\t\t" + "URL";
		try{		
			for (int i = 0; i < BMP_Proxy_Controller.APINetworkCalls.size(); i++) {
				HashMap<String, String> requestDetails = BMP_Proxy_Controller.APINetworkCalls.get(i);
				if(!requestDetails.get("ResponseCode").toString().startsWith("2")){
					failedAPICalls = failedAPICalls + "\n" + "\n" + requestDetails.get("Method") + "\t\t" + requestDetails.get("ResponseCode") + "\t\t" + requestDetails.get("URL");
				}
			} 
		}catch(Exception e){
			e.printStackTrace();
		}
		return failedAPICalls;
	}

	/**
	 * @author Cigniti
	 * @param harPath
	 */
	public static void saveAPIHarLogToHarFile(String harPath){
		try{
			HarNameVersion harnameversion_creator = new HarNameVersion("BrowserMob Proxy","2.0");
			harnameversion_creator.setComment("");
			APIharlog.setCreator(harnameversion_creator);
			HarPage harpage = new HarPage();
			harpage.setId("Automation");
			harpage.setComment("");
			harpage.setTitle("BMP Selenium Automation");
			HarPageTimings harpagetimings = new HarPageTimings();
			harpagetimings.setComment("");
			harpage.setStartedDateTime(new Date());
			List<HarPage> harpages = new ArrayList<HarPage>();
			harpages.add(harpage);
			APIharlog.addPage(harpage);		
			HarNameVersion harnameversion_browser = new HarNameVersion("Chrome","40");
			harnameversion_browser.setComment("");
			APIharlog.setBrowser(harnameversion_browser);
			APIhar.setLog(APIharlog);
			File file = new File(harPath);
			FileOutputStream fos = new FileOutputStream(file);
			APIhar.writeTo(fos);
			fos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void saveServerHarLogToHarFile(String harPath){
		try{
			Serverhar = Server.getHar();
			File file = new File(harPath);
			FileOutputStream fos = new FileOutputStream(file);
			Serverhar.writeTo(fos);
			fos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @author Cigniti
	 */
	public void verifyAPIResponseCodes(){
		boolean isTestcaseFailed = false;
		try
		{
			for (int i = 0; i < APINetworkCalls.size(); i++) {
				HashMap<String, String> requestDetails = APINetworkCalls.get(i);
				if(!requestDetails.get("ResponseCode").toString().startsWith("2")){
					isTestcaseFailed = true;
					CustomLogs.addToLog("CurrentTestCaseLog", "debug", requestDetails.get("ResponseCode") + "," + requestDetails.get("Method") + "," + requestDetails.get("URL"));
				}
			} 
		}catch(Exception e){
			CustomLogs.addToLog("CurrentTestCaseLog", "error", e.getMessage());
		}
		if(isTestcaseFailed){
			Assert.fail("API failed due to non 2XX response code");
		}
	}
}