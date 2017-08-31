package com.web.automation.executor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.web.automation.utilities.ConfigManager;

public class TestRunner{

	private static FileWriter fos;
	private static String outputFileName = System.getProperty("user.dir").replace("\\", "/")+ "/testng_xmls/runtime.xml";
	private static List<String> list = null;
	public static String testClassesSheet= System.getProperty("user.dir").replace("\\", "/")+ "/testdata/TestClasses.xlsx";

	/**
	 * Factory method to send dynamically generated test classes to testNg
	 * @param environment 
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static Object[] testCaseConfiguration() throws FileNotFoundException, IOException {
		try{
			ConfigManager.setProperties();
			if(ConfigManager.getProperties().get("localExecution").toString().equalsIgnoreCase("true")
					&& ConfigManager.getProperties().get("IsExcelRequired").toString().equalsIgnoreCase("true")){
				try{
					list = getClassList();
					System.out.println(":::::::: Local Class List = "+list.toString());
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
			}
			else if(System.getenv("jenkins_home")!=null && System.getenv("jenkins_home").length()>=1){
				try {
					list = getClassListfromJenkins("TestCases");
					System.out.println(":::::::: Jenkins Class List = "+list.toString());

				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			else{
				System.out.println("*** There are no class list to execute through excel or jenkins.");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				create_xml_for_excel_jenkins(list);
			}catch(Exception e1){
				e1.getLocalizedMessage();
			}
		}
		Object[] data = null;
		data = list == null? null : list.toArray() ;
		return data;
	}

	/**
	 * method to get execution class list with flag 'Y'/'YES'
	 * 
	 * @return List<Object>
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static List<String> getClassList() throws FileNotFoundException, IOException {
		LinkedList<String> list = new LinkedList<String>();
		try {
			HashMap<String, String> excelValueMap = readExcelData();
			for (String className : excelValueMap.keySet()) {
				String executeable = excelValueMap.get(className);
				if (executeable.equalsIgnoreCase("Y")
						|| executeable.equalsIgnoreCase("YES")) {
					list.add(className);
					// Do not un-comment this code. It is required only if we need to use @ Factory annotation.
					/*	List<Object> list1 = new ArrayList<Object>();
					Object classObj = Class.forName(className).newInstance();
					list1.add(classObj);*/
				}
			}
		} 
		catch (Exception e) {
			e.getLocalizedMessage();
		}
		return list;
	}

	/**
	 * Method to read excel data
	 * 
	 * @return HashMap<String,String>
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static HashMap<String, String> readExcelData() throws FileNotFoundException, IOException {
		LinkedHashMap<String, String> excelValueMap = new LinkedHashMap<String, String>();
		XSSFWorkbook workbook=new XSSFWorkbook(new FileInputStream(testClassesSheet));
		try {
			XSSFSheet sheet1 = workbook.getSheetAt(0);
			LinkedList<String> excelVal = read(sheet1);
			for (String rowVal : excelVal) {
				String[] col1DatatArray = rowVal.split("~");
				excelValueMap.put(col1DatatArray[0], col1DatatArray[1]);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return excelValueMap;
	}

	//Implemented for jenkins
	/**
	 * @author Cigniti
	 * @return
	 */
	public static List<String> getClassListfromJenkins(String jenkinsTestCaseVariableName) {
		List<String> list = new ArrayList<String>();
		try {
			String testCases = System.getenv(jenkinsTestCaseVariableName).toString();
			System.out.println("::::::::Test Cases Selected = "+testCases);
			String[] MyData = testCases.split(",");
			for (String Data : MyData) {
				list.add(Data);
				Collections.sort(list);
			}
		}  catch (Exception e) {
			e.getLocalizedMessage();
		}
		return list;
	}	

	/**
	 * to read data from shete
	 * 
	 * @param sheet
	 * @return
	 * @throws IOException
	 */
	public static LinkedList<String> read(XSSFSheet sheet) throws IOException {
		LinkedList<String> excelVal = new LinkedList<String>();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row rovVal = (Row) rowIterator.next();
			String rowVal = "";
			for (int i = 0; i < rovVal.getLastCellNum(); i++) {
				XSSFCell cellA1 = (XSSFCell) rovVal.getCell(i);
				rowVal = rowVal + cellA1.getStringCellValue() + "~";
			}
			excelVal.add(rowVal);
		}
		return excelVal;
	}

	/**
	 * @author Cigniti
	 * @throws IOException
	 */
	public static void create_xml_for_excel_jenkins(List<String> list) throws IOException {
		Properties config_prop = new Properties();
		try{
			FileReader reader;
			reader = new FileReader("config.properties");
			config_prop.load(reader);
		}
		catch (IOException e) {
			System.out.println("Failed to fetch/read 'config.properties' file.");
		}
		try{
			if (new File(outputFileName).exists()) {
				System.out.println("Test suite exists at location :- " + outputFileName);
				new File(outputFileName).delete();
			}
			fos = new FileWriter(outputFileName);
			fos.write("<?xml version='1.0' encoding='UTF-8'?>\n");
			fos.write("<!DOCTYPE suite SYSTEM 'http://testng.org/testng-1.0.dtd'>\n");
			fos.write("<suite name='Selenium-Automation' parallel='tests' thread-count='1' preserve-order='true' configfailurepolicy='continue' junit='false' skipfailedinvocationcounts='false' data-provider-thread-count='10' group-by-instances='false' allow-return-values='false'>\n\n");
			fos.write("<listeners>\n");
			fos.write("\t<listener class-name=\"com.web.automation.Listeners.ExecutionListener\" />\n");
			fos.write("</listeners>\n");
			fos.write("\t<test name='Test-"+ config_prop.getProperty("deviceType")+ "' junit='false' skipfailedinvocationcounts='false' preserve-order='true' group-by-instances='false' allow-return-values='false'>\n");
			if(config_prop.getProperty("localExecution").toString().equalsIgnoreCase("true")
					&& config_prop.getProperty("IsExcelRequired").toString().equalsIgnoreCase("true")){
				fos.write("\t\t\t<parameter name=\"platform\" value=\""+ config_prop.getProperty("platform")+ "\"></parameter>\n");
				fos.write("\t\t\t<parameter name=\"browser\" value=\""+ config_prop.getProperty("deviceType") + "\"></parameter>\n");
				fos.write("\t\t\t<parameter name=\"Version\" value=\""+ config_prop.getProperty("browser_version") + "\"></parameter>\n");
				fos.write("\t\t\t<parameter name=\"environment\" value=\""+ config_prop.getProperty("environment") + "\"></parameter>\n");
				fos.write("\t\t\t<parameter name=\"userRegion\" value=\""+ config_prop.getProperty("userRegion") + "\"></parameter>\n");
				fos.write("\t\t\t<parameter name=\"seleniumGrid\" value=\""+ config_prop.getProperty("seleniumGrid") + "\"></parameter>\n");
				fos.write("\t\t\t<parameter name=\"cloudExecution\" value=\""+ config_prop.getProperty("cloudExecution") + "\"></parameter>\n");
				fos.write("\t\t\t<parameter name=\"emailReport\" value=\""+ config_prop.getProperty("emailReport") + "\"></parameter>\n");
			}else{
				fos.write("\t\t\t<parameter name=\"browser\" value=\""+ System.getenv("Browser") + "\"></parameter>\n");
				fos.write("\t\t\t<parameter name=\"environment\" value=\""+ System.getenv("Environment") + "\"></parameter>\n");
				fos.write("\t\t\t<parameter name=\"userRegion\" value=\""+ System.getenv("userRegion") + "\"></parameter>\n");
			}
			fos.write("\t\t<groups>\n");
			fos.write("\t\t\t<run>\n");
			fos.write("\t\t\t<include name=\"smoke\"/>\n");
			fos.write("\t\t\t<include name=\"regression\"/>\n");
			fos.write("\t\t\t</run>\n");
			fos.write("\t\t</groups>\n");
			fos.write("\t\t<classes>\n");
			for(int k = 0; k<list.size(); k++){
				fos.write("\t\t\t<class name=\"" + list.get(k) + "\"/>\n");
			}
			fos.write("\t\t</classes>\n");
			fos.write("\t\t</test>\n");
			fos.write("\n");
			fos.write("</suite>");
			fos.flush();
			if (new File(outputFileName).exists()) {
				System.out.println("Test suite successfully generated :-" + outputFileName);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @author Cigniti
	 * @param args
	 */
	public static void main(String[] args){
		try {
			testCaseConfiguration();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}