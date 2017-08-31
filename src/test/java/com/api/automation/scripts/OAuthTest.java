
package com.api.automation.scripts;

import org.junit.runners.MethodSorters;
import org.mvel2.ast.Convertable;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

import static com.jayway.restassured.config.EncoderConfig.encoderConfig;
import static com.jayway.restassured.RestAssured.config;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.with;
import static com.jayway.restassured.http.ContentType.JSON;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OAuthTest {
 
	
	private String access_Token = "aaa1a1346e49c73fd0a189f604ea04a9e4b1ee4b044102048ce3ff0ce48d";
	private String client_ID = "b932dc1dbb789fe275ea";
	
	private String APIUrl = "https://a.wunderlist.com/api/v1";
	
	static private long list_id   = 0 ; 
	static private long task_id   = 0 ;
	static private long folder_id = 0 ;
	
	@Test
	public void testA_oAuth2(){
		   given().headers("X-Access-Token",access_Token, "X-Client-ID",client_ID).    
		           auth().oauth2(access_Token).
		   when().
                  get(APIUrl + "/user").
           then().
                  statusCode(200);
	}
	
	@Test
	public void testB_getLists(){
		   given().
		           headers("X-Access-Token",access_Token, "X-Client-ID",client_ID).
	       when().
                   get(APIUrl + "/lists").
           then().
                  statusCode(200);
	}
	
	@Test
	public void testC_createList(){
		   SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	       Date now = new Date();
	       String strDate = sdfDate.format(now);
		   String listName = "ECLIPSE-list-" + strDate ; 
	       
		   Map<String, Object>  jsonAsMap = new HashMap<>();
		   jsonAsMap.put("title", listName );
		
		
	       given().headers("X-Access-Token",access_Token,"X-Client-ID",client_ID).
	               contentType(JSON).
	               body(jsonAsMap).
	       when().
                   post(APIUrl + "/lists").
           then().
                   statusCode(201);
	       
	       
	        final String body = with().headers("X-Access-Token",access_Token, "X-Client-ID",client_ID).
                                    
                                      contentType(JSON).
                                when().
                                      get(APIUrl + "/lists").asString();

             System.out.println("createList "+ body);
             
             
             final JsonPath jsonPath = new JsonPath(body);
             final List <Integer> lists = jsonPath.getList("id");
             list_id =  lists.get( lists.size() - 1);
             System.out.println ("Newly Created List Id: " + list_id );
    }
	
	@Test
	public void testD_createTask(){
		  
		
		
		     given().headers("X-Access-Token",access_Token, "X-Client-ID",client_ID).
                     contentType("application/json; charset=utf-8").	
                    
                     body("{\"list_id\":"+list_id+",\"title\":\"POST-TASK\"}").
		         
             when().
                    post(APIUrl + "/tasks").
             then().
                    statusCode(201);
		     
		     
		     final String body = with().
		    		                   headers("X-Access-Token",access_Token, "X-Client-ID",client_ID).
                                       contentType("application/json; charset=utf-8").
                                 when().
                                        get(APIUrl + "/tasks?list_id="+ list_id).asString();

             System.out.println("createTask  "+ body);

             final JsonPath jsonPath = new JsonPath(body);
             final List <Long> tasks = jsonPath.getList("id");
             task_id = tasks.get( tasks.size() - 1);
             System.out.println ("Newly Created Task Id: " + task_id );
     }
	
	@Test
	public void testE_fileUploadToTask(){
		try{
			

				
				Map<String, Object> params = new HashMap<>();
				params.put("content_type", "image/jpg");
				params.put("file_name", "Rocky.jpg");
				params.put("file_size", "236726");
				
				
				final String body = 
						with().headers("X-Access-Token",access_Token, "X-Client-ID",client_ID).
		                        contentType("application/json; charset=utf-8").
		                        body(params).
		                 when().
		                        post(APIUrl + "/uploads").asString();
		
		               System.out.println("fileUploadToTask  1 - >"+ body);
		               
		               final JsonPath jsonPath = new JsonPath(body);
		               
		               
		               String upload_id     = jsonPath.getString("id");
		    		   String awsUpload_URL = jsonPath.getString("part.url");
		    		   String awsAuth       = jsonPath.getString("part.authorization");
		    		   String awsTime       = jsonPath.getString("part.date");
		    		   
		    		   System.out.println("uploadID ---> " + upload_id);
		    		   System.out.println("amazongURL ---> " + awsUpload_URL);
		    		   System.out.println("awsAuth ---> " + awsAuth);
		    		   System.out.println("awsTime ---> " + awsTime);
		    		   System.out.println("------------------------------");
		    		  
		    		 final RequestSpecification request =  given().
		    				   config(config().encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).
		    				   with().
		    				   		headers("Authorization",awsAuth, "x-amz-date",awsTime,"Content-Type","").
							        body(new File("./src/test/resources/Rocky.jpg"));
		    		   final Response response = request.put(awsUpload_URL);
		               System.out.print(response.statusCode());
		               System.out.println(response.asString()); 
		    		 
		               
		               
		    	    
		               given().headers("X-Access-Token",access_Token, "X-Client-ID",client_ID).
		                         contentType("application/json; charset=utf-8").	
		                         body("{\"state\":\"finished\"}").
		               when().
		                     patch(APIUrl + "/uploads/" + upload_id).
		               then().
		                      statusCode(200);
		               
		      
		              final String body3 = 
		      				with().headers("X-Access-Token",access_Token, "X-Client-ID",client_ID).
		                              contentType("application/json; charset=utf-8").
		                              body("{\"upload_id\":"+upload_id+",\"task_id\":"+task_id+"}").
		                       when().
		                             post(APIUrl + "/files").asString();
		
		                     System.out.println("fileUploadToTask 2 -> "+ body3);
		                     
		               Thread.sleep(5000);      
		}catch(Exception e){
			e.printStackTrace();
		}
    	               
	}
	
	@Test
	public void testF_EditTask(){
				
				   final String body = with().headers("X-Access-Token",access_Token, "X-Client-ID",client_ID).
		                                      contentType("application/json; charset=utf-8").
		                                      get(APIUrl + "/tasks?list_id="+ list_id).asString();
		
		           System.out.println("EditTask  " + body);
		
		   
                   given().
                          headers("X-Access-Token",access_Token, "X-Client-ID",client_ID).
                          contentType("application/json; charset=utf-8").
                          body("{\"revision\":2,\"title\":\"PATCH-REQUEST-TASK\"}").
                   when().
                          patch(APIUrl + "/tasks/" + task_id).
                   then().       
                          statusCode(200);       
    }
	
	@Test
	public void testG_DeleteTask(){

		        final String body = with().headers("X-Access-Token",access_Token, "X-Client-ID",client_ID).
                                           contentType("application/json; charset=utf-8").
                                    when().
                                           get(APIUrl + "/tasks?list_id="+ list_id).asString();
                System.out.println("DeleteTask  "+ body);
		
		         
                given().headers("X-Access-Token",access_Token, "X-Client-ID",client_ID).
		                contentType("application/json; charset=utf-8").	
		        when().
		                delete(APIUrl + "/tasks/"+ task_id +"?revision=3").
		        then().
		                statusCode(204);
   }
	
	@Test
	@Ignore
	public void testH_EditList(){
	
		   final String body = with().headers("X-Access-Token",access_Token, "X-Client-ID",client_ID).
	                                  contentType("application/json; charset=utf-8").
	                                  get(APIUrl + "/lists/" + list_id).asString();

           System.out.println("EditList  " + body);

		   
		   Map<String, Object>  jsonAsMap = new HashMap<>();
		   jsonAsMap.put("revision", 6);
	       jsonAsMap.put("title", "NEW-NAME-OF-LIST");
	    
	       given().headers("X-Access-Token",access_Token, "X-Client-ID",client_ID).
	               
	               contentType(JSON).
	               body(jsonAsMap).
	       when().
                   patch(APIUrl + "/lists/"+ list_id).
           then().
                   statusCode(200);
	}
	
	@Test
	public void testI_FolderAPIs(){
		
	       String folderName = "API-Folder";
	       long[] list_ids = { list_id };
	       
	       Map<Object, Object>  jsonAsMap = new HashMap<>();
	       jsonAsMap.put("title", folderName  );
	       jsonAsMap.put("list_ids",list_ids );
	            
		     given().headers("X-Access-Token",access_Token, "X-Client-ID",client_ID).
	                 contentType("application/json; charset=utf-8").	
	                 contentType(JSON).
	                 body(jsonAsMap).
	         when().
	                 post(APIUrl + "/folders").
	         then().
	                 statusCode(201);
		     
		     
		     final String body = with().
		    		                   headers("X-Access-Token",access_Token, "X-Client-ID",client_ID).
	                                   contentType("application/json; charset=utf-8").
	                             when().
	                                   get(APIUrl + "/folders").asString();
	
	         System.out.println("Folders GET  "+ body);
	
	         final JsonPath jsonPath = new JsonPath(body);
	         final List <Integer> folders = jsonPath.getList("id");
	         folder_id =  folders.get( folders.size() - 1);
	         
	         System.out.println ("Newly Created Folder Id: " + folder_id );
         
	         
	         given().headers("X-Access-Token",access_Token, "X-Client-ID",client_ID).
                     contentType("application/json; charset=utf-8").	
             when().
                     delete(APIUrl + "/folders/"+ folder_id +"?revision=1").
             then().
                     statusCode(204);
	}
	
	@Test
	@Ignore
	public void testJ_DeleteList(){
		  
		   final String body = with().
				                      headers("X-Access-Token",access_Token, "X-Client-ID",client_ID).
                                      contentType("application/json; charset=utf-8").
                               get(APIUrl + "/lists/" + list_id).asString();

           System.out.println("DeleteList  " + body);
		   
		   given().headers("X-Access-Token",access_Token, "X-Client-ID",client_ID).
                   contentType("application/json; charset=utf-8").	
           when().
                  delete(APIUrl + "/lists/"+ list_id +"?revision=7").
           then().
                  statusCode(204);
	}
	
}



