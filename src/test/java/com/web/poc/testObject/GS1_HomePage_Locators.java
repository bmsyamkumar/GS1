package com.web.poc.testObject;

import org.openqa.selenium.By;
public class GS1_HomePage_Locators{
	public static By home_title=By.xpath("//.[contains(text(),'Welcome to MyGS1')]");
	public static By mygs1_dashboard_lnk = By.linkText("MyGS1 Dashboard");	
	public static By verifyBarcode_lnk = By.linkText("Verify your barcode scans");
	public static By prodPhotography_lnk = By.linkText("Product Photography");
	public static By barcodeImgGen_lnk = By.linkText("Barcode Image Generator");
	public static By financialStuff_lnk = By.linkText("Financial Stuff");
	public static By changePwd_lnk = By.linkText("Change Password");
	public static By logout_lnk = By.linkText("Logout");
}
	
