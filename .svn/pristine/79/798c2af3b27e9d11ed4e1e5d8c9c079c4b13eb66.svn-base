package com.web.automation.listeners;

import org.testng.*;
import com.relevantcodes.extentreports.LogStatus;
import com.web.automation.accelerators.ActionsLibrary;
import com.web.automation.utilities.CommonVariables;

public class ExecutionListener extends ActionsLibrary implements ITestListener, ISuiteListener, IInvokedMethodListener,IExecutionListener {

	// This belongs to ISuiteListener and will execute before the Suite start
	@Override
	public void onStart(ISuite arg0) {
		Reporter.log("About to begin executing Suite :- " + arg0.getName(), true);
	}

	// This belongs to ISuiteListener and will execute, once the Suite is finished
	@Override
	public void onFinish(ISuite arg0) {
		Reporter.log("About to end executing Suite :- " + arg0.getName(), true);
		//	CommonVariables.extentTest.get().log(LogStatus.INFO,"About to end executing Suite " + arg0.getName());
	}

	// This belongs to ITestListener and will execute before starting of Test set/batch
	public void onStart(ITestContext arg0) {
		Reporter.log("About to begin executing Test :- " + arg0.getName(), true);
		//	CommonVariables.extentTest.get().log(LogStatus.INFO,"About to begin executing Test" + arg0.getName());
	}

	// This belongs to ITestListener and will execute, once the Test set/batch is finished
	public void onFinish(ITestContext arg0) {
		Reporter.log("Completed executing test :- " + arg0.getName(), true);
		//	CommonVariables.extentTest.get().log(LogStatus.INFO,"Completed executing test " + arg0.getName());
	}

	// This belongs to ITestListener and will execute before the main test start (@Test)
	public void onTestStart(ITestResult arg0) {
		Reporter.log("About to begin Test :- " + arg0.getName(), true);
		//CommonVariables.extentTest.get().log(LogStatus.INFO,"About to begin Test " + arg0.getName());
	}

	// This belongs to ITestListener and will execute only when the test is pass
	public void onTestSuccess(ITestResult arg0) {
		CommonVariables.extentTest.get().log(LogStatus.PASS,"Status","TEST CASE PASSED.");
	}

	// This belongs to ITestListener and will execute only on the event of fail test
	public void onTestFailure(ITestResult arg0) {
		CommonVariables.extentTest.get().log(LogStatus.FAIL,"Status","TEST CASE FAILED.");
		/*Boolean flag=true;
		try{
			XmlSuite suite = new XmlSuite();
			suite.setName("rerunFailedTestClasses");
			XmlTest test = new XmlTest(suite);
			test.setName(arg0.getTestName());
			List<XmlClass> classes = new ArrayList<XmlClass>();
			classes.add(arg0.getTestClass().getXmlClass());
			test.setXmlClasses(classes) ;
			List<XmlSuite> suites = new ArrayList<XmlSuite>();
			suites.add(suite);
			TestNG tng = new TestNG();
			tng.setXmlSuites(suites);
			tng.run();
		}catch(Exception e){
			e.getLocalizedMessage();
			flag=false;
		}finally{
			if(flag=true){
				CommonVariables.extentTest.get().log(LogStatus.PASS,"Re-Run Status","TEST CASE PASSED.");
			}else{
				CommonVariables.extentTest.get().log(LogStatus.FAIL,"Re-Run Status","TEST CASE FAILED.");
			}
		}*/
	}

	// This belongs to ITestListener and will execute only if any of the main test(@Test) get skipped
	public void onTestSkipped(ITestResult arg0) {
		CommonVariables.extentTest.get().log(LogStatus.SKIP,"Status","TEST CASE SKIPPED.");
	}

	// This is just a piece of shit, ignore this
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		//CommonVariables.extentTest.get().log(LogStatus.WARNING,"Status","Test Failed But Within Success Percentage : "+arg0.SUCCESS_PERCENTAGE_FAILURE);
	}

	// This belongs to IInvokedMethodListener and will execute before every method including @Before @After @Test
	public void beforeInvocation(IInvokedMethod arg0, ITestResult arg1) {
		String textMsg = "About to begin executing method :- " + returnMethodName(arg0.getTestMethod());
		Reporter.log(textMsg, true);
		//CommonVariables.extentTest.get().log(LogStatus.INFO, textMsg + arg0.getTestMethod().getMethodName());
	}

	// This belongs to IInvokedMethodListener and will execute after every method including @Before @After @Test
	public void afterInvocation(IInvokedMethod arg0, ITestResult arg1) {
		String textMsg = "Completed executing method  :- " + returnMethodName(arg0.getTestMethod());
		Reporter.log(textMsg, true);
		//CommonVariables.extentTest.get().log(LogStatus.INFO, textMsg + arg0.getTestMethod().getMethodName());
	}

	// This will return method names to the calling function
	private String returnMethodName(ITestNGMethod method) {
		return method.getRealClass().getSimpleName() + "." + method.getMethodName();
	}

	@Override
	public void onExecutionStart() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onExecutionFinish() {
		// TODO Auto-generated method stub
	}
}