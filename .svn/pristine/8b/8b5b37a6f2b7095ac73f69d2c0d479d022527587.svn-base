package com.web.automation.bmp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import com.web.automation.utilities.CommonVariables;
import com.web.automation.utilities.ConfigManager;
import net.lightbody.bmp.proxy.ProxyServer;


/**
 * Main Proxy class that starts the BrowserMob Proxy Server.
 */
public class BrowserMobProxyServer{

	ProxyServer server;
	AnalyseHAR analyseHar;
	int port;
	public BrowserMobProxyServer(){
		this.port = CommonVariables.portNumForBMP;
	}
	public BrowserMobProxyServer(int port){
		this.port = port;
	}
	
	/**
	 * @author Cigniti
	 * @return
	 * @throws IOException
	 */
	public ProxyServer startBMPServer() throws IOException {
		/**
		 * Creates an instance of ProxyServer
		 */
		this.port = getfirstAvailablePortNumber(this.port);
		server=new ProxyServer(port);
		CommonVariables.portNumForBMP = this.port;
        
		try {
			/*
			 * Starts the Proxy Server at port 8091
			 */
			server.start();
			System.out.println("Successfully Started the BMP Proxy server on port Number: " + port);
					/**	
					 * Captures the headers and other body content. This is mandatory.
					 */
	        server.setCaptureHeaders(true);
	        server.setCaptureContent(true);
			ConfigManager.setProperties();
	        server.autoBasicAuthorization(ConfigManager.getProperties().getProperty("stag_url").trim(), ConfigManager.getProperties().getProperty("userAuthId"), ConfigManager.getProperties().getProperty("userAuthPwd"));
	        server.autoBasicAuthorization(ConfigManager.getProperties().getProperty("qa_url").trim(), ConfigManager.getProperties().getProperty("userAuthId"), ConfigManager.getProperties().getProperty("userAuthPwd"));
	        server.autoBasicAuthorization(ConfigManager.getProperties().getProperty("dev_url").trim(), ConfigManager.getProperties().getProperty("userAuthId"), ConfigManager.getProperties().getProperty("userAuthPwd"));
	        server.autoBasicAuthorization(ConfigManager.getProperties().getProperty("preprod_url").trim(), ConfigManager.getProperties().getProperty("userAuthId"), ConfigManager.getProperties().getProperty("userAuthPwd"));

	       /**
	        * Defining the custom interceptor class that intercepts BMP Request as well as BMP Response.
	        */
	        RequestResponseInterceptor interceptor=new RequestResponseInterceptor();
	        server.addRequestInterceptor(interceptor);
	        server.addResponseInterceptor(interceptor);
	        
	        /**
//	         * Proxy Server can rewrite any Request. 
	         */
//	        server.rewriteUrl("http://*.*", "http://www.google.com");
	        /**
	         * Proxy Server blacklists any request which is for HTTP protocol.
	         */
//	         server.blacklistRequests(".*http.*", 404);
	        analyseHar=new AnalyseHAR();
	       
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return server;
	}
	
	/**
	 * @author Cigniti
	 * Stops the Browsermobproxy server and ends all open connections.
	 */
	public void stopServer(){
		try {
			server.stop();
			server=null;
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("Exception while stopping the proxy server");
		}
	}
	
	/**
	 * @author Cigniti
	 * @param portNum
	 * @return
	 * @throws IOException
	 */
	private int getfirstAvailablePortNumber(int portNum) throws IOException{
		String s = "";
		boolean portBusy = false;
		String command = "netstat -a -n -p TCP";
		Process p = Runtime.getRuntime().exec(command);
		do{
			try{
				portBusy = false;
				System.out.println("checking port Number availability at: "+portNum);
				BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
				//BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
				while ((s = stdInput.readLine()) != null){
					try{
						String connectInfo = s.replaceAll("[\\t\\n\\r\\x0b ]+"," ").trim().split(" ")[1];
						if(connectInfo.contains(":"+portNum)){
							portNum++;
							portBusy = true;
							System.out.println("Port is busy ("+s+"). Going to check next port (Plus 1 in the same number)");
						}
					}
					catch(ArrayIndexOutOfBoundsException ex){}
				}
			}
			catch(IOException ioExec){
				ioExec.printStackTrace();
			}
		}while(portBusy);
		return portNum;
	}
}