package com.web.automation.testmanagement;

import com.web.automation.utilities.ConfigManager;

import atu.alm.wrapper.ALMServiceWrapper;
import atu.alm.wrapper.IDefect;
import atu.alm.wrapper.ITestCase;
import atu.alm.wrapper.ITestCaseRun;
import atu.alm.wrapper.ITestSet;
import atu.alm.wrapper.enums.DefectPriority;
import atu.alm.wrapper.enums.DefectSeverity;
import atu.alm.wrapper.enums.DefectStatus;
import atu.alm.wrapper.enums.StatusAs;
import atu.alm.wrapper.exceptions.ALMServiceException;

public class QCIntegration {

	protected static String url=ConfigManager.getProperties().getProperty("QCURL");
	protected static String port=ConfigManager.getProperties().getProperty("QCPort");
	protected static String username=ConfigManager.getProperties().getProperty("QAUser");
	protected static String password=ConfigManager.getProperties().getProperty("QCPassword");
	protected static String domain=ConfigManager.getProperties().getProperty("QCDomain");
	protected static String project=ConfigManager.getProperties().getProperty("QCProject");
	
	public void updateResults(String testSetFolderPath, String testSetName,int testSetId,
			String testMethod,StatusAs status) throws ALMServiceException {
		ALMServiceWrapper wrapper = new ALMServiceWrapper("http://"+url+":"+port+"/qcbin");
		wrapper.connect(username, password, domain, project);
		wrapper.updateResult(testSetFolderPath, testSetName, testSetId,
				testMethod, status);
		wrapper.close();
	}
	
	/*
	 * Example For Creating an Attachment for a TestSet
	 */
	public static void createAttachmentForTestSet(String testSetFolderPath, String testSetName, int testSetId,
			String attahmentSheet, String attachmentDescription) throws ALMServiceException {
		ALMServiceWrapper wrapper = new ALMServiceWrapper("http://"+url+":"+port+"/qcbin");
		wrapper.connect(username, password, domain, project);
		ITestSet testSet = wrapper.getTestSet(
				testSetFolderPath, testSetName, testSetId);
		wrapper.newAttachment(attahmentSheet, attachmentDescription,
				testSet);
		wrapper.close();
	}

	/*
	 * Example For Creating a new Defect and Attaching a File
	 */
	public static void createDefect(String assignee, String reportee, String defectDescription, String date,
			String defectSummary,String attahmentSheet, String attachmentDescription, DefectPriority priority, 
			DefectSeverity severity,DefectStatus status) throws ALMServiceException {
		ALMServiceWrapper wrapper = new ALMServiceWrapper("http://"+url+":"+port+"/qcbin");
		wrapper.connect(username, password, domain, project);
		IDefect defect = wrapper.newDefect();
		defect.isReproducible(true);
		defect.setAssignedTo(assignee);
		defect.setDetectedBy(reportee);
		defect.setDescription(defectDescription);
		defect.setDetectionDate(date);
		defect.setPriority(priority);
		defect.setSeverity(severity);
		defect.setStatus(status);
		defect.setSummary(defectSummary);
		System.out.println(defect.getDefectID());
		wrapper.newAttachment(attahmentSheet, attachmentDescription,
				defect);
		defect.save();
		wrapper.close();
	}

	public static void createTestCaseRunAndTestCaseExecutionSteps(String testSetFolderPath, String testSetName,int testSetId,
			String testMethod,String attahmentSheet, String attachmentDescription,String runName, StatusAs status,
			ITestCaseRun testcase, String testStep)
			throws ALMServiceException {
		ALMServiceWrapper wrapper = new ALMServiceWrapper("http://"+url+":"+port+"/qcbin");
		wrapper.connect(username, password, domain, project);

		// Update Test Case Result and Attach a File
		ITestCase loginTest = wrapper.updateResult(
				testSetFolderPath, testSetName, testSetId,
				testMethod, status);
		wrapper.newAttachment(attahmentSheet, attachmentDescription,
				loginTest);

		// Create a new Run, Add the Steps for this Run and Attach a File
		testcase = wrapper.createNewRun(loginTest, runName, status);
		wrapper.addStep(testcase, testStep, status,
				"Enters username", "enter", "enter");
		wrapper.addStep(testcase, testStep, status,
				"Enters password", "enter", "enter");
		wrapper.addStep(testcase, testStep, status,
				"Enters username", "", "");
		wrapper.newAttachment(attahmentSheet, attachmentDescription,
				testcase);
		wrapper.close();
	}
	
}
