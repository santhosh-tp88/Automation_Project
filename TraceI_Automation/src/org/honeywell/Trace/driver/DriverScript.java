package org.honeywell.Trace.driver;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.honeywell.Trace.common_methods.GenricApplicationMethods;
import org.honeywell.Trace.utility.Datatable;
import org.honeywell.Trace.utility.Utility;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;


public class DriverScript {
	// Declaration
	public static WebDriver oBrowser = null;
	public static Utility appInd = null;
	public static GenricApplicationMethods appDep = null;
	public static Map<String, String> objMap = null;
	public static Map<String, String> objMaptemp = null;
	public static Map<String, String> otestcaseclassesMap = null;
	public static LinkedHashMap<String, String> oTreeMapTCResult = new LinkedHashMap<String,String>();
	public static Map<String, String> otestdataMap = null;
	public static Datatable datatable = null;
	public static boolean blnRes = false;
	public static String strTestdatapath = null;
	public static String strObjectmappingpath = null;
	public static String strTestdataSheetName = "TestData";
	public static String strModuleExceutionSheetName = "Execution";
	public static String strresultSheetName = null;
	public static String strObjectmappingSheetName = "ObjectRepository";
	public static String strHTMLreportpath = null;
	public static String strStartTime = null;
	public static String strEndTime = null;
	public static String strTCLogicalName = null;
	public static String strTCID = null;
	public static String strModuleexecutionpath = null;
	public static String strModulenamestatus = null;
	public static String strrespectivemodulepath = null;
	public static String strModulename = null; 
	public static String strBootstrapFilepath = null;
	public static String strrespectivemoduleResultpath = null;
	public static String strBootstrapreportpath=null;
	public static Map<String, HashMap> oOutputMap = new HashMap<String, HashMap>();

	@BeforeSuite
	public static void loadClasses() {

		// Creation of Object classes
		datatable = new Datatable();

		appInd = new Utility();

		appDep = new GenricApplicationMethods();


		// capturing Automation of Start Message
		appInd.writeLog("#", "**********************Automation Execution Started*******************");

		// Module Execution file path
		strModuleexecutionpath = System.getProperty("user.dir") + "\\Modules\\Module_Execution.xlsx";
   		// Reading all the test scenarios classes and their methods and map it with TestCase id.
 		otestcaseclassesMap = appInd.testcaseidclassmap();
 		
		// HTML Report path
		strHTMLreportpath = System.getProperty("user.dir") + "\\Results\\Reports\\HTML_Report";
		
		// HTML Report path
		strBootstrapreportpath = System.getProperty("user.dir") + "\\Results\\Reports\\HTML_Report\\Bootstrap337.css";
		//Support Folder path
		strBootstrapFilepath=System.getProperty("user.dir") + "\\Support_files\\Bootstrap337.css";
		
		//copying Bootstrap file into the result path
		appInd.copyFileUsingStream(strBootstrapFilepath, strBootstrapreportpath);	
		
		// Reading all the Global Object Mapping and storing it into Map
		objMap = appInd.getElementObjects(strModuleexecutionpath, "Global_ObjectRepository");
					
		// Reading the configuration  file contents
		appInd.readConfig();
	}

	public String getTestcaseId() {
		return strTCID;
	}

	
	public WebDriver getWebDriver() {
		return oBrowser;
	}
	
	@Test
	public static void runTest() {
		// System.out.println("runtest started"); //self review test code

		Class cls = null;
		Object obj = null;
		Method method = null;
		String strScript = null;
		String strClass = null;
		String strMapvalue = null;
		String strTCExceution = null;
		String strresultval="abc";
		

		try {
			// capture framework Start time
			strStartTime = appInd.nowtimestamp("default");			
			// Launch IE driver
			oBrowser = appDep.launchBrowser(System.getProperty("BROWSER"));	 

			// navigate to the URL
			appDep.navigateURL(System.getProperty("URL"));

			// Reading maximum number of rows count in the Module Execution Sheet
			int MRows = datatable.getRowCount(strModuleexecutionpath, strModuleExceutionSheetName);
			//System.out.println("Modulesheet- row count:-" + MRows); // self review test code

			for (int i = 1; i < MRows-1; i++) {
				// Reading Module Name from the Module execution excel file
				strModulename = datatable.getCellData(strModuleexecutionpath, strModuleExceutionSheetName, "Module", i + 1).trim();
								
				if (strModulename != null && !strModulename.trim().isEmpty()) {
					// Reading Execution status from the Module execution excel file
					strModulenamestatus = datatable
							.getCellData(strModuleexecutionpath, strModuleExceutionSheetName, "Execute_Status", i + 1)
							.trim();

					if (strModulenamestatus != null && !strModulenamestatus.trim().isEmpty()) {
						if (strModulenamestatus.equalsIgnoreCase("yes")) {
							// read respective Module file path
							strrespectivemodulepath = System.getProperty("user.dir") + "\\Modules\\" + strModulename+ ".xlsx";
							
							//Storing the Result path from the respective Module 
							strrespectivemoduleResultpath = System.getProperty("user.dir")+ "\\Results\\Reports\\Excel_Report\\" + strModulename + ".xlsx";
							
							//copying the respective Module into the Result folder and Deleting the Object repository sheet
							appInd.copyFileUsingStream(strrespectivemodulepath, strrespectivemoduleResultpath);
 							
 							//appInd.deleteSheetFromExecle("ObjectRepository", strrespectivemoduleResultpath);
 							datatable.DeleteSheet(strrespectivemoduleResultpath, strObjectmappingSheetName);
 							//System.out.println("Result file of Module:" +strModulename+" created in the HTML result folder");
 							
							// Read the Object Mappings of respective Modules and storing into the ObjectMapp
							objMaptemp = appInd.getElementObjects(strrespectivemodulepath, strObjectmappingSheetName);

							// Adding respective module Object map into Global objectsMap
							objMap.putAll(objMaptemp);

							// Reading all the respective module test scenarios classes and their methods and map it with TestCase id.							
							otestdataMap = datatable.getInputTestData(strrespectivemodulepath, strTestdataSheetName);

							// Reading maximum number of rows count in the respective Module
							int pRows = datatable.getRowCount(strrespectivemodulepath, strTestdataSheetName);
							for (int TD_i = 1; TD_i < pRows-1; TD_i++) 
							{
								try {strTCID = datatable.getCellData(strrespectivemodulepath, strTestdataSheetName,"TestCaseID", TD_i + 1).trim();} 
								catch (Exception e){break;}		
								
								// Reading TestCase id from the TestData sheet in Module excel file
								if (strTCID != null && !strTCID.trim().isEmpty()) {
									//System.out.println("140_runtest-TC Id:-" + strTCID); // self review test code

									// Reading the Execution status from the TestData in Module excel file
									strTCExceution = datatable.getCellData(strrespectivemodulepath,strTestdataSheetName, "Execution_status", TD_i + 1).trim();

									// Checking for the Execution Status 'yes'
									if ((strTCExceution.equalsIgnoreCase("yes"))) {
										// Reading the Testcase logical name from the TestData sheet in Module Excel file
										strTCLogicalName = datatable.getCellData(strrespectivemodulepath,strTestdataSheetName, "TC_LogicalName", TD_i + 1).trim();

										//System.out.println("TC logical Name:-" + strTCLogicalName); // self review test code
										if (!(strTCLogicalName.equalsIgnoreCase(null)) && !(strTCLogicalName == "")) {

											// Reading the Mapping class name and the method name from the Mapping hash map
											strMapvalue = otestcaseclassesMap.get(strTCLogicalName);
											//System.out.println("Map Value:-" + strMapvalue); // self review test code

											if (!(strMapvalue.equalsIgnoreCase(null)) && !(strMapvalue == "")) {
												// Splitting and storing class name and method name
												String objArr[] = strMapvalue.split("#");
												strScript = objArr[1];
												strClass = objArr[0];

												if (!(strScript.equalsIgnoreCase(null)) && !(strScript == "")) {
													// loading and execution of test script classes methods
													cls = Class.forName(strClass);
													obj = cls.newInstance();
													method = obj.getClass().getMethod(strScript);					

													// Execution of Test scenario methods
													oOutputMap = (Map<String, HashMap>) method.invoke(obj);
													// writing to the result file												
													strresultval= appInd.writeResult(otestdataMap,oOutputMap, strTCID, (TD_i + 1),strTestdataSheetName,strrespectivemoduleResultpath);
													oTreeMapTCResult.put(strTCID, strresultval);
													System.out.println("Writing '"+strTCID+ "' result into the result file is completed"); // self review test code
												}
											}
										}
									}
								}								
								if(!(strModulename.equalsIgnoreCase("Login_Module"))&&(strTCLogicalName.equalsIgnoreCase("Login"))&&(strresultval.contains("fail"))) {break;}
							}
						}
					}
			}
			}
		} catch (Exception e) {
			 //System.out.println("176_runtest exception:-" +e.getMessage());//self review test code
			appInd.writeLog("Exception", "Exception while executing 'runTest' method. " + e.getMessage());
			
		} finally {
			// close all the Browser
			oBrowser.close();
			oBrowser.quit();

		}
	}

	@AfterSuite
	public  void teardown() {
		
		strEndTime = appInd.nowtimestamp("default");
		// create the HTML report
		appInd.CreateHTML(strHTMLreportpath, strStartTime, strEndTime, oTreeMapTCResult,strModulename);

		System.out.println("runtest end");
		appInd.writeLog("#", "**********************Automation Execution End*******************");
	}

}// Close of Driver Class
