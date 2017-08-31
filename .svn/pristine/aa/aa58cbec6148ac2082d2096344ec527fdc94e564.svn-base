/*package com.web.automation.utilities;

import static com.jayway.restassured.RestAssured.given;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import com.jayway.restassured.response.Response;

*//**
 * A utility that downloads a file from a URL.
 * @author Cigniti
 *
 *//*
public class DownloadAFileFromURL {
	
	String completePathOfdDownloadedFile = "";
	public Response response;
	public String body;
	public String env,apiURL;
	public String BaseURL = "https://api...../";
	public String authenticationToken;
	public boolean isLoggedIn = false;
	
	*//**
	 * @author Cigniti
	 *//*
	public DownloadAFileFromURL(){
		env = ConfigManager.getProperties().getProperty("environment").toLowerCase();
		if(env.equals("prod")){
			BaseURL = "https://api.....";
		}else{
			BaseURL = "https://"+env+"-api.....";
		
		}
	}
	
	*//**
	 * @author Cigniti
	 * @param username
	 * @param password
	 * @return
	 *//*
	public boolean authenticate(String username, String password){
		boolean flag=false;
		try{
			String uuid = UUID.randomUUID().toString();
			apiURL = BaseURL + "/authentication/login";
			
			String postData = "{\"username\":\""+username+"\","
					+ "\"password\":\""+password+"\","
					+ "\"deviceId\":\""+uuid+"\"}";
			
			response = given()
					.contentType("application/json")
					.body(postData)
			        .when()
			        .post(apiURL);
			body = response.getBody().asString();
			System.out.println(response.getStatusCode());
			System.out.println(body);
			authenticationToken = response.jsonPath().get("accessToken");
			isLoggedIn = true;
			flag = true;
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

    *//**
     * Downloads a file from a URL
     * @param fileURL HTTP URL of the file to be downloaded
     * @param saveDir path of the directory to save the file
     * @author Cigniti
     *//*
	@SuppressWarnings("unused")
	public boolean verifyFileDownloadLink(String fileURL, String saveDir){
		try{
			if(!isLoggedIn && (!env.equalsIgnoreCase("prod"))){
				authenticate(ConfigManager.getProperties().getProperty("gmailUser"), ConfigManager.getProperties().getProperty("gmailPassword"));
			}
			java.net.URL url = new URL(fileURL);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestMethod("GET");
			String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36";
			if(!env.equalsIgnoreCase("prod")){
				httpConn.setRequestProperty("eqx-token", authenticationToken);
				httpConn.setRequestProperty("Authorization", "Basic RGV2UHJldmlldzpFcXVpbm94MSE=");
			}
			httpConn.setDoOutput(true);
			int responseCode = httpConn.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				String fileName = "";
				String disposition = httpConn.getHeaderField("Content-Disposition");
				String contentType = httpConn.getContentType();
				int contentLength = httpConn.getContentLength();
				if(disposition != null) {
					int index = disposition.trim().indexOf("filename=");
					if (index > 0) {
						fileName = disposition.substring(index + 9).trim();
					}
				} else {
					fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,fileURL.length());
				}
				System.out.println("Content-Type = " + contentType);
				System.out.println("Content-Disposition = " + disposition);
				System.out.println("Content-Length = " + contentLength);
				System.out.println("fileName = " + fileName);
				InputStream inputStream = httpConn.getInputStream();
				String saveFilePath = saveDir + File.separator + fileName;
				FileOutputStream outputStream = new FileOutputStream(saveFilePath);
				int bytesRead = -1;
				byte[] buffer = new byte[2048];
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}
				outputStream.close();
				inputStream.close();
				System.out.println("File downloaded successfully");
				httpConn.disconnect();
				completePathOfdDownloadedFile = saveFilePath;
				return true;
			} else {
				System.out.println("No file to download. Server replied HTTP code: " + responseCode);
				return false;
			}
		}
		catch(Exception e){
			System.out.println("Failed to download from '' url. Exception message: "+e.getLocalizedMessage());
			return false;
		}
	}
}*/