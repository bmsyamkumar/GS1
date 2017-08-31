package com.api.automation.scripts;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import com.web.automation.fileutils.ExcelReader;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


import java.util.List;
import java.util.Map;
public class SampleTest   {

	
	private String firstName, lastName, customerID, address, url;
	
	protected String sheetPath = System.getProperty("user.dir").replace("\\", "/") + "/testdata/TestData.xlsx";
	protected String sheetName = "API";
	Map<String, List<String>> testdata = null;
	public static Response response;
	public static String jsonAsString;
	
	@Before()
	public void beforeMethod() throws Throwable {
		
		
		this.firstName = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "FirstName");
		this.lastName = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "LastName");
		this.customerID = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "CustomerID");
		this.address = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "address");
		this.url = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, 1, "URL");
		
		
	}
		

	    @Test public void
	    given_when_then_works_with_xpath_assertions() {
	    	
	    	System.out.println("Test started : given_when_then_works_with_xpath_assertions " );
	    	
	    	String url=this.url+"/bank/customers/"+this.customerID+"/";
	    	
	        given().log().all().
	        	param("firstName", this.firstName, "lastName", this.lastName).
	        when().
	        	get(url).
	        then().
	                body(hasXPath("//*[text()='"+this.address+"']"));
	    }
	    

	    @Test public void
	    given_when_then_works_with_multiple_body_assertions() {
	    	
	    	System.out.println("Test started :given_when_then_works_with_multiple_body_assertions" );
	    	
	    	String url=this.url+"/bank/customers/"+this.customerID+"/";
	    	
	        given().log().all().
	        	param("firstName",  this.firstName, "lastName", this.lastName).
	        when().
	        	get(url).
	        then().
	                body(containsString(this.firstName)).
	                body(containsString(this.lastName));
	        
	        System.out.println("Test Completed ");
	    }
	  
	    @Test
	    public void simpleInvalidDetailJsonValidation() throws Exception {
	    
	        given().log().all().
	        	param("customerId", 12212).
	        	param("newAccountType", 3).
	        	param("fromAccountId", 10000).
	        when().
	        
	                post(this.url+"/bank/createAccount").
	                
	        then().
	                statusCode(400).
	                body(containsString("Could not create new account for customer 0 from account 0"));
	              
	    }
	    
	   
	    
	    @Test
	    @Ignore
	    public void simpleSecondFailedExampleJsonValidation() throws Exception {
		    
	    	given().log().all().
		      	param("text", "test").
		      when().
	            get("http://md5.jsontest.com").
	          then().
	            body("md5",equalTo("098f6bcd4621d373cade4e832627b4f6")).
	          and().
	            body("original", equalTo("incorrect"));
	    }
	    
	    @Test
	    public void simpleSecondPassedExampleJsonValidation() throws Exception {
		      given().log().all().
		      	param("text", "test").
		      when().
	            get("http://md5.jsontest.com").
	          then().
	            body("md5",equalTo("098f6bcd4621d373cade4e832627b4f6")).
	          and().
	            body("original", equalTo("test"));
	    }
	    
	
	   
	    
	    

}
