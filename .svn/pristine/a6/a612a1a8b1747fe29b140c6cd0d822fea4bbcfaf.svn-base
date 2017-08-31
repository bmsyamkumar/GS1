package com.web.automation.bmp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.core.har.HarLog;
import net.lightbody.bmp.core.har.HarNameValuePair;
import net.lightbody.bmp.core.har.HarPage;
import net.lightbody.bmp.core.har.HarPageTimings;
import net.lightbody.bmp.core.har.HarPostData;
import net.lightbody.bmp.core.har.HarRequest;
import net.lightbody.bmp.core.har.HarResponse;
import net.lightbody.bmp.core.har.HarTimings;

public class AnalyseHAR {

	private static String rootPath=System.getProperty("user.dir").replace("\\", "/");
	/**
	 * Saves the Har file at the directory path.
	 * @param path
	 * @param harfile
	 */
	public void saveHarFile(String filename, Har harfile){
		File classpathRoot = new File(rootPath);
		File file = new File(classpathRoot,"/Har/"+filename ); 		
		System.out.println("Path of File is="+file.getPath());

		try {
			FileOutputStream fos=new FileOutputStream(file);
			System.out.println("Harfile="+harfile);
			harfile.writeTo(fos);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Parses the HAR file and prints out all the information
	 * @param harfile
	 */
	public void investigateHar(Har harfile) {
		/*Root of Exported Data*/
		HarLog log=harfile.getLog();
		/*Comment information*/
		@SuppressWarnings("unused")
		String comment=log.getComment();
		/*Entries information*/
		List<HarEntry> listEntries=log.getEntries();
		for(HarEntry entry:listEntries){
			long time=entry.getTime();
			String pageRef=entry.getPageref();
			HarTimings timings=entry.getTimings();
			@SuppressWarnings("unused")
			long blocked=timings.getBlocked();
			long connect=timings.getConnect();
			long dns=timings.getDns();
			long recieve=timings.getReceive();
			long send= timings.getSend();
			long ssl=timings.getSsl();
			long wait=timings.getWait();
			@SuppressWarnings("unused")
			long sum=connect+dns+recieve+send+ssl+wait;
			@SuppressWarnings("unused")
			String connection=entry.getConnection();
			@SuppressWarnings("unused")
			String pageref=	entry.getPageref();
			@SuppressWarnings("unused")
			String serverIP=entry.getServerIPAddress();
			HarRequest request=	entry.getRequest();
			String requestURL=request.getUrl();
			System.out.println("\nRequest URL: "+requestURL);
			List<HarNameValuePair> queryString=	request.getQueryString();
			HarPostData postData=request.getPostData();
			@SuppressWarnings("unused")
			String postDataText=null;
			if(postData!=null)
				postDataText=postData.getText();
			System.out.println("PageRef= "+pageRef);
			if(queryString.size()>0){
				System.out.println("Request Query String:");
				for(HarNameValuePair pair: queryString){
					String name= pair.getName();
					String value= pair.getValue();

					System.out.println("Name= "+name+"  Value= "+value);
				}
			}
			/*You can dig in to Response Object also*/
			HarResponse response=entry.getResponse();
			int status=response.getStatus();
			System.out.println("Time="+time);
			System.out.println("Response code="+status);
		}
		/*Pages Information*/
		List<HarPage> harPage=log.getPages();
		for(HarPage page: harPage){

			String id=page.getId();
			String title=page.getTitle();
			Date date=page.getStartedDateTime();

			System.out.println("**********************************************************************");

			HarPageTimings timing=page.getPageTimings();
			if(timing!=null){
				long onload=timing.getOnLoad();
				System.out.println("PageTiming Data: Title="+title+"   Id="+id+"   onLoad="+onload+"   Date="+date);
			}
		}
	}
}