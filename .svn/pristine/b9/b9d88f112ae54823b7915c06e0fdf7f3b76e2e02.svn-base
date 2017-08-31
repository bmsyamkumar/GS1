package com.web.automation.googledrive;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleOAuthConstants;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;
import com.google.api.services.gmail.model.MessagePartHeader;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Exception;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class GmailAPI {
	// Check https://developers.google.com/gmail/api/auth/scopes for all available scopes
	private final String SCOPE = "https://www.googleapis.com/auth/gmail.readonly";
	private final String APP_NAME = "Gmail API Quickstart";
	// Path to the client_secret.json file downloaded from the Developer Console
	private String CLIENT_SECRET_PATH;
	private GoogleClientSecrets clientSecrets;
	private Gmail Gmailservice;
	private String username, password;
	private Properties config_prop;
	private GoogleCredential credential;
	private List<Map<String,String>> EmailList = new ArrayList<Map<String, String>>();
	private GoogleTokenResponse response;
	private HttpTransport httpTransport;
	private boolean isAuthenticated = false;
	private JacksonFactory jsonFactory;

	/**
	 * @author Cigniti
	 * @description : Gmail Authentication
	 * @throws Exception
	 */
	public void authenticate() throws Exception {

		config_prop = new Properties();
		try{
			FileReader reader;
			reader = new FileReader("config.properties");
			config_prop.load(reader);
		}
		catch (IOException e) {
			System.out.println("Failed to fetch/read 'config.properties' file.");
		}

		username=config_prop.getProperty("gmailUser");
		password=config_prop.getProperty("gmailPassword");

		//        CLIENT_SECRET_PATH = System.getProperty("user.dir") + CLIENT_SECRET_PATH;
		httpTransport = new NetHttpTransport();
		jsonFactory = new JacksonFactory();
		CLIENT_SECRET_PATH =  System.getProperty("user.dir").replace("\\", "/") + "/lib/"+config_prop.getProperty("gmailClientSecretFile")+".json";
		clientSecrets = GoogleClientSecrets.load(jsonFactory,  new FileReader(CLIENT_SECRET_PATH));
		// 	    // Allow user to authorize via url.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				httpTransport, jsonFactory, clientSecrets, Arrays.asList(SCOPE))
				.setAccessType("online")
				.setApprovalPrompt("auto").build();

		String url = flow.newAuthorizationUrl().setRedirectUri(GoogleOAuthConstants.OOB_REDIRECT_URI)
				.build();

		System.out.println("Please open the following URL in your browser then type"
				+ " the authorization code:\n" + url);       
	}

	/**
	 * @author Cigniti
	 * @description: Read Email from Gmail Account
	 * @param count
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> readEmail(int count) throws Exception{
		if(!isAuthenticated){
			this.authenticate();
		}
		boolean flag = credential.refreshToken();

		//	    // Create a new authorized Gmail API client
		Gmailservice = new Gmail.Builder(httpTransport, jsonFactory, credential)
				.setApplicationName(APP_NAME).build();
		List <String> labelids = new ArrayList<String>();
		labelids.add("INBOX");
		ListMessagesResponse res = Gmailservice.users().messages().list(username).setLabelIds(labelids).setMaxResults((long) count).execute();

		Message message2 = Gmailservice.users().messages().get(username, config_prop.getProperty("gmailUserMessageSecret")).setFormat("raw").execute();
		for (Message message : res.getMessages()) {
			Map<String,String> EmailMap = new HashMap<String,String>();
			Message message1 = getSpecificEmail(message.getId().toString());
			String subject = getMessageSubject(message1);
			EmailMap.put("Id",message.getId().toString());
			System.out.println("Email Subject: \n" + subject);
			EmailMap.put("Subject",getMessageSubject(message1));
			EmailMap.put("Body",getMessageBody(message1));
			EmailMap.put("From",getMessageFrom(message1));
			EmailMap.put("To",getMessageTo(message1));
			EmailMap.put("Time",getMessageTime(message1));
			EmailList.add(EmailMap);
		}
		return EmailList;
	}

	/**
	 * @author Cigniti
	 * @param id
	 * @return
	 * @throws Exception
	 */
	private Message getSpecificEmail(String id) throws Exception{
		Message message = Gmailservice.users().messages().get(username, id).execute();
		return message;
	}

	/**
	 * @author Cigniti
	 * @param message
	 * @return
	 * @throws Exception
	 */
	private String getMessageSubject(Message message) throws Exception{
		MessagePart msgpart = message.getPayload();

		List<MessagePartHeader> headers = message.getPayload().getHeaders();
		for(MessagePartHeader header : headers){
			if(header.getName().toString().equals("Subject")){
				return header.getValue().toString();
			}
		}    
		return "";
	}

	/**
	 * @author Cigniti
	 * @param message
	 * @return
	 * @throws Exception
	 */
	private String getMessageFrom(Message message) throws Exception{
		MessagePart msgpart = message.getPayload();
		List<MessagePartHeader> headers = message.getPayload().getHeaders();
		for(MessagePartHeader header : headers){
			if(header.getName().toString().equals("From")){
				return header.getValue().toString();
			}
		}
		return "";
	}

	/**
	 * @author Cigniti
	 * @param message
	 * @return
	 * @throws Exception
	 */
	private String getMessageTo(Message message) throws Exception{
		MessagePart msgpart = message.getPayload();
		List<MessagePartHeader> headers = message.getPayload().getHeaders();
		for(MessagePartHeader header : headers){
			if(header.getName().toString().equals("To")){
				return header.getValue().toString();
			}
		}
		return "";
	}

	/**
	 * @author Cigniti
	 * @param message
	 * @return
	 * @throws Exception
	 */
	private String getMessageTime(Message message) throws Exception{
		MessagePart msgpart = message.getPayload();
		List<MessagePartHeader> headers = message.getPayload().getHeaders();
		for(MessagePartHeader header : headers){
			if(header.getName().toString().equals("Date")){
				return header.getValue().toString();
			}
		}
		return "";
	}

	/**
	 * @author Cigniti
	 * @param message
	 * @return
	 * @throws Exception
	 */
	private String getMessageBody(Message message)throws Exception{
		String MailBody = "";
		String MailBodyDecoded = "";
		//        message = Gmailservice.users().messages().get(USER, "vgvxsuus").setFormat("raw").execute();
		try{
			MessagePart msgpart = message.getPayload();			
			List<MessagePart> bodyParts = msgpart.getParts();
			for(MessagePart part : bodyParts){
				MailBody = StringUtils.newStringUtf8(Base64.decodeBase64(part.getBody().getData()));
				if(MailBody == null){
					MailBody = StringUtils.newStringUtf8(Base64.decodeBase64(part.getParts().get(1).getBody().getData()));
				}
				//              MailBodyDecoded = StringUtils.newStringUtf8(Base64.decodeBase64(MailBody));
				//				System.out.println(MailBody);
				break;
			}
			return MailBody;
		}catch(NullPointerException e){
			return null;
		}
	}

	/**
	 * @author Cigniti
	 * @param url
	 * @param username
	 * @param encodedPassword
	 * @return
	 * @throws MalformedURLException
	 * @throws InterruptedException
	 */
	private String getTokenFromURL(String url, String username, String encodedPassword) throws MalformedURLException, InterruptedException{
		username = this.username;
		encodedPassword = this.password;
		String decodedPassword = encodedPassword;//StringUtils.newStringUtf8(Base64.decodeBase64(EncodedPassword));
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setJavascriptEnabled(true);                //< not really needed: JS enabled by default
		caps.setCapability("takesScreenshot", true);    //< yeah, GhostDriver haz screenshotz!
		String phantomPath=System.getProperty("user.dir").replace("\\", "/")+"/Tool/PhantomJS/ghostdriver-1.1.1/src/phantomjs.exe";
		File f = new File(phantomPath);
		if(!f.exists())
		{
			System.err.println("Could not find phantomjs.exe at "+phantomPath+". Getting it from /Tool/PhantomJS/ghostdriver-1.1.1/src/phantomjs.ex");
			phantomPath= "/Tool/PhantomJS/ghostdriver-1.1.1/src/phantomjs.exe";
		}
		caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, phantomPath
				);
		// Launch driver (will take care and ownership of the phantomjs process)
		WebDriver driver = new PhantomJSDriver(caps);
		//        WebDriver driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.navigate().to(url);
		driver.findElement(By.id("Email")).sendKeys(username);
		driver.findElement(By.id("next")).click();;
		Thread.sleep(5000);
		driver.findElement(By.id("Passwd")).sendKeys(decodedPassword);
		driver.findElement(By.id("signIn")).click();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id("submit_approve_access")));
		element.click();
		WebElement code = wait.until(ExpectedConditions.elementToBeClickable(By.id("code")));
		System.out.println( code.getText() );
		String accessCode = code.getAttribute("value");
		System.out.println(accessCode);
		driver.close();driver.quit();
		return accessCode;
	}
}