package com.web.automation.contentmanagement;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.web.automation.accelerators.ActionsLibrary;
import com.web.automation.utilities.CommonVariables;
import com.web.automation.utilities.ConfigManager;

public class Sitecore {
	public WebDriver driver;
	public boolean IsLaunched = false;
	public boolean IsInitialized = false;
	private ActionsLibrary objCommonFunc;
	private String BaseSitecoreURL = "";
	private By RootNode = By.xpath("");// Eg.. for root node locator value :- //div[@id='ContentTreeActualSize']/div[@class='scContentTreeNode']
	private By username;
	private By password;
	private By login;
	private WebElement CurrentNode = null;
	private By childNode;

	public Sitecore(){
		if(!IsInitialized){
			driver = CommonVariables.CommonDriver.get();
			objCommonFunc = CommonVariables.getActionLib();
			String env = ConfigManager.getProperties().getProperty("environment") + "-";
			if(!env.contains("Prod")){
				BaseSitecoreURL = ConfigManager.getProperties().getProperty("urlSuppressCredentialSitecore");
			}else{
				BaseSitecoreURL = ConfigManager.getProperties().getProperty("sitecore_url");
			}
			IsInitialized = true;
		}
	}

	public Sitecore(WebDriver driver){
		this.IsInitialized = true;
		this.driver = driver;
		objCommonFunc = CommonVariables.getActionLib();
		BaseSitecoreURL = ConfigManager.getProperties().getProperty("urlSuppressCredentialSitecore");
	}

	public void LaunchSiteCore(){
		// TODO Auto-generated method stub
		if(!IsLaunched){
			try{
				String osname = System.getProperty("os.name");
				String ChromeDriverPath = "";
				if(osname.toLowerCase().contains("windows")){
					ChromeDriverPath = System.getProperty("user.dir").replace("\\", "/") + "/Tool/chromedrivers/windows/chromedriver.exe";
				}
				System.setProperty("webdriver.chrome.driver", ChromeDriverPath);

				ChromeOptions chromeoptions = new ChromeOptions();
				chromeoptions.addArguments("test-type");
				DesiredCapabilities objCapabilities = new DesiredCapabilities();
				objCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeoptions);
				driver = new ChromeDriver(objCapabilities);
				driver.manage().window().maximize();
				driver.navigate().to(BaseSitecoreURL);
				driver.findElement(username).clear();
				driver.findElement(username).sendKeys(ConfigManager.getProperties().getProperty("sitecore_username"));
				driver.findElement(password).sendKeys(ConfigManager.getProperties().getProperty("sitecore_password"));
				driver.findElement(login).click();
				Thread.sleep(10000);
				IsLaunched = true;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public void CloseSiteCore(){
		try{
			driver.close();
			driver.quit();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public boolean IsNodeExpanded(WebElement elem){	
		if(elem.findElement(By.xpath("./img")).getAttribute("src").toString().contains("collapse")){
			return true;
		}else{
			return false;
		}
	}

	public String GetNodeName(WebElement elem){
		try{
			return elem.findElement(By.xpath("./a/span")).getText();
		}catch(Exception e){
			return "Error";
		}
	}

	public WebElement GetSelectedNode(){
		try{
			return CurrentNode;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public List<WebElement> GetChildNodes(WebElement elem){
		try{
			List<WebElement> childnodes;// = new ArrayList<WebElement>();
			childnodes = elem.findElements(childNode);
			return childnodes;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public int GetChildNodesCountOfCurrentlySelectedNode(){
		return GetChildNodes(CurrentNode).size();
	}

	public boolean ExpandNode(WebElement elem){
		boolean flag = true;
		try{
			if(!IsNodeExpanded(elem)){
				elem.findElement(By.xpath("./img")).click();
				Thread.sleep(5000);
				if(IsNodeExpanded(elem)){
					flag = true;
				}else{
					flag=false;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	public String getElementXPath(WebElement element) {
		try{
			String str = element.toString().split("->")[1].trim();
			String str2 = str.split("xpath: ")[1].trim();
			str = str2.substring(0, str2.length()-1);
			return str;
		}
		catch(Exception e){
			e.printStackTrace();
			return "<failed to retrive xpath>";
		}
	}

	public String GetXpathFromBy(By by){
		try{
			return by.toString().split("By.xpath: ")[1].trim();
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}

	public boolean SelectNode(WebElement node){
		boolean flag = true;
		try{
			node.findElement(By.xpath("./a/span")).click();
			Thread.sleep(5000);
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	//**************************************************************************************************************************************************	
	// Navigate to specific tree node and node path needs to be provide with delimeter as ">" For example: "Content>sites>abc>Home>Articles>"
	//**************************************************************************************************************************************************
	public boolean NavigateToSpecificNode(String node_hierarchy){
		boolean flag = true;
		//		node_hierarchy = "Content>sites>QBlog>Home>Articles>";
		String Arr[] = node_hierarchy.split(">");
		String Node_Name="";
		WebElement RootNodeElem = driver.findElement(RootNode);
		try{
			WebElement closeSearchResult = driver.findElement(By.xpath("//div[@id='SearchHeader']/a[@href='#']"));
			if(closeSearchResult!=null && closeSearchResult.isDisplayed()){
				closeSearchResult.click();
			}
		}
		catch(NoSuchElementException ex){
			ex.printStackTrace();
		}
		catch(ElementNotVisibleException ex){
			ex.printStackTrace();
		}
		try{
			for(int i=0;i<Arr.length;i++){
				Node_Name = Arr[i];
				List<WebElement> childnodeelems;
				childnodeelems = GetChildNodes(RootNodeElem);
				for(WebElement node:childnodeelems){
					if(GetNodeName(node).equalsIgnoreCase(Node_Name)){
						ExpandNode(node);
						RootNodeElem = node;
						break;
					}
				}
			}
			Thread.sleep(5000);
			SelectNode(RootNodeElem);
			Thread.sleep(5000);
			CurrentNode = RootNodeElem;
		}catch(Exception e){
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	public WebElement findElement(By by , int timeout){
		try{
			WebElement Elem = new WebDriverWait(driver,timeout).until(ExpectedConditions.presenceOfElementLocated(by));
			return Elem;
		}catch(Exception e){
			return null;
		}
	}

	public List<WebElement> findElements(By by){
		try{
			List<WebElement> Elem = driver.findElements(by);
			return Elem;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public void switchtoframe(WebElement ele){
		try{
			driver.switchTo().frame(ele);
		}
		catch(Exception e){

		}
	}

	public void switchtoDefault(){
		try{
			driver.switchTo().defaultContent();
		}
		catch(Exception e){
		}
	}

	public String getItemIDOfSelectedNode(){
		WebElement itemIDElem = findElement(By.xpath("//td[text()='Item ID:']//following-sibling::td/input[@class='scEditorHeaderQuickInfoInput']"), 1);
		return itemIDElem.getAttribute("value");
	}

	public void searchNode(String nodeValue)
	{
		try
		{
			WebElement elem = new WebDriverWait(driver,15).until(ExpectedConditions.presenceOfElementLocated(By.id("TreeSearch")));
			elem.clear();
			elem.sendKeys(nodeValue);
			elem.sendKeys(Keys.ENTER);
			Thread.sleep(8000);
		}
		catch(Exception e)
		{
			e.getMessage();
		}
	}
}