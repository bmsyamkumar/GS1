package com.web.automation.bmp;


import java.util.HashMap;

import com.web.automation.utilities.ConfigManager;

import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.proxy.http.BrowserMobHttpRequest;
import net.lightbody.bmp.proxy.http.BrowserMobHttpResponse;
import net.lightbody.bmp.proxy.http.RequestInterceptor;
import net.lightbody.bmp.proxy.http.ResponseInterceptor;
import net.lightbody.bmp.proxy.jetty.http.HttpRequest;

/**
 * Interceptor class that watches every data while the request crosses the Proxy Server.
 */
public class RequestResponseInterceptor implements RequestInterceptor, ResponseInterceptor{
	public HttpRequest request;
	public org.apache.http.HttpResponse response;
	public boolean isAPIRequest = false;
	int flag=1;
	
	public RequestResponseInterceptor(){
		ConfigManager.setProperties();
	}
	
	public void process(BrowserMobHttpRequest mobrequest, Har har) {

		/**
		 * Here we get access to net.lightbody.bmp.proxy.jetty.http.HttpRequest object and not org.apache.http.HttpRequest
		 */
		request= mobrequest.getProxyRequest();
		StringBuffer url=request.getRequestURL();
		if(url.toString().contains("api."+ConfigManager.getProperties().getProperty("BMP_DomainName"))){
			isAPIRequest = true;
		}else{
			isAPIRequest = false;
		}
		/**
		 * This prints all the header data encapsulated in the HTTPRequest. Example:
			Host = github.com
			User-Agent = Mozilla/5.0 (Windows NT 6.1; WOW64; rv:26.0) Gecko/20100101 Firefox/26.0
			Accept = text/html,application/xhtml+xml,application/xml;q=0.9,**;q=0.8
			Accept-Language = en-US,en;q=0.5
			Accept-Encoding = gzip, deflate
			Connection = keep-alive
		 * 
		 */

		/**
		 * We can re-execute the Http Request and check the response received.
		 */
		/*
		if(flag==1){
			flag=flag+1; //This is mandatory other the execute method will cause indefinite loop.
		}
		else
			return;

		BrowserMobHttpResponse mobresponse=mobrequest.execute();
		org.apache.http.HttpResponse response=mobresponse.getRawResponse();
		System.out.println("Inner HttpResponse status="+response.getStatusLine());
		 */
	}

	public void process(BrowserMobHttpResponse mobresponse, Har har) {
		/**
		 * We get access to this object org.apache.http.HttpResponse and not net.lightbody.bmp.proxy.jetty.http.HttpResponse.
		 */
		HashMap<String, String> requestDetails = new HashMap<String, String>();
		HarEntry harentry = mobresponse.getEntry();
		String RequestedURL = harentry.getRequest().getUrl().toString();
		String RequestMethod = harentry.getRequest().getMethod().toString();
		int ResponseCode = harentry.getResponse().getStatus();
		if(RequestedURL.contains("api."+ConfigManager.getProperties().getProperty("BMP_DomainName")+".com/")){
			BMP_Proxy_Controller.APIharlog.addEntry(harentry);
			requestDetails.put("URL", RequestedURL);
			requestDetails.put("Method", RequestMethod);
			requestDetails.put("ResponseCode", String.valueOf(ResponseCode));
			BMP_Proxy_Controller.APINetworkCalls.add(requestDetails);
		}
	}
}