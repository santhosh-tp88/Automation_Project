package org.honeywell.Trace.utility;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.honeywell.Trace.driver.DriverScript;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class Utility extends DriverScript {
	/********************************
	 * Method Name : WriteLog Purpose : Author : Reviewer : Date of Creation : Date
	 * of modification : *
	 * 
	 * @param strvalue
	 * *****************************
	 */
	public void writeLog(String strStatus, String strMessage) {

		Logger log = null;
		try {
			log = Logger.getLogger("Trace_Automationlog");
			switch (strStatus.toLowerCase()) {
			case "pass":
				log.info(strMessage);
				break;
			case "fail":
				log.error(strMessage);
				break;
			case "warning":
				log.warn(strMessage);
				break;
			case "exception":
				log.fatal(strMessage);
				break;
			case "#":
				log.info(strMessage);
				break;
			default:
				System.out.println("Invalid status '" + strStatus + "' for writing the report");
			}
		} catch (Exception e) {
			System.out.println("Exception while executing 'writeLog' method. " + e.getMessage());
		}

	}
	
	
	public void highlight(WebDriver obrowser, WebElement webElement) {
		try {
			JavascriptExecutor jsExecutor = (JavascriptExecutor) obrowser;
			jsExecutor.executeScript(
					"arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", webElement);
			Thread.sleep(500);
			jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1]);", webElement, "");
		} catch (InterruptedException e) {
			System.out.println("Error while Highlighting - " + e.getMessage());
		}

	}

	/********************************
	 * Method Name : waitFor purpose : Author : Creation Date : Reviewed By :
	 * ******************************
	 */
	public boolean waitFor(WebDriver oDriver, String strObjectName, String strWaitType, String strValue,
			int intWaitTime) {
		WebDriverWait oWait = null;
		By objBy = null;
		try {
			oWait = new WebDriverWait(oDriver, intWaitTime);
			objBy = createAndGetByObject(strObjectName);
			switch (strWaitType.toLowerCase()) {
			case "text":
				oWait.until(ExpectedConditions.textToBePresentInElementLocated(objBy, strValue));
				break;
			case "value":
				oWait.until(ExpectedConditions.textToBePresentInElementValue(objBy, strValue));
				break;
			case "element":
				oWait.until(ExpectedConditions.presenceOfElementLocated(objBy));
				break;
			case "elements":
				oWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(objBy));
				break;
			case "clickable":
				oWait.until(ExpectedConditions.elementToBeClickable(objBy));
				break;
			case "invisible":
				oWait.until(ExpectedConditions.invisibilityOfElementLocated(objBy));
				break;
			default:
				writeLog("Fail", "Invalid wait option '" + strWaitType + "' was provided");
				return false;
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/********************************
	 * Method Name : getElementObjects Purpose : Author : Reviewer : Date of
	 * Creation : Date of modification : ******************************
	 */
	public Map<String, String> getElementObjects(String strObjectmappingpath, String strObjectmappingSheetName) {
		FileInputStream fin = null;
		Map<String, String> oMap = null;
		Workbook wb = null;
		Sheet sh = null;
		Row row = null;
		Cell cell1 = null;
		Cell cell2 = null;
		Cell cell3 = null;
		int rows = 0;
		String sKey = null;
		String sVal = null;
		try {
			oMap = new HashMap<String, String>();
			fin = new FileInputStream(strObjectmappingpath);
			wb = new XSSFWorkbook(fin);
			for (int i = 0; i < wb.getNumberOfSheets(); i++) {
				if (wb.getSheetName(i).equalsIgnoreCase(strObjectmappingSheetName)) {
					sh = wb.getSheet(strObjectmappingSheetName);
					break;
				}
			}

			if (sh == null)
				return null;
			rows = sh.getPhysicalNumberOfRows();
			for (int r = 1; r < rows; r++) {
				row = sh.getRow(r);
				cell1 = row.getCell(0);
				sKey = cell1.getStringCellValue();
				cell2 = row.getCell(1);
				cell3 = row.getCell(2);
				if ((cell2 != null) && (cell3 != null)) {
					sVal = cell2.getStringCellValue() + "#" + cell3.getStringCellValue();
					oMap.put(sKey, sVal);
				}
			}
			return oMap;
		} catch (Exception e) {
			appInd.writeLog("Exception", "Exception while executing 'getAndConvertToMap' method. " + e.getMessage());
			return oMap;
		} finally {
			try {
				fin.close();
				fin = null;
				cell1 = null;
				cell2 = null;
				cell3 = null;
				row = null;
				sh = null;
				wb.close();
				wb = null;

			} catch (Exception e) {
				appInd.writeLog("Exception",
						"Exception while executing 'getAndConvertToMap' method. " + e.getMessage());
				return oMap;
			}

		}

	}

	/********************************
	 * Method Name : readConfig 
	 * Purpose : 
	 * Author : 
	 * Reviewer : 
	 * Date of Creation :
	 * Date of modification :
	 *  ******************************/
	public void readConfig() {
		FileInputStream fin = null;
		Properties prop = null;
		try {
			fin = new FileInputStream(System.getProperty("user.dir") + "\\Support_files\\Config.properties");
			prop = new Properties();
			prop.load(fin);
			System.setProperty("URL", prop.getProperty("URL"));
			System.setProperty("BROWSER", prop.getProperty("BROWSER"));
		} catch (Exception e) {
			appInd.writeLog("Fail", "Exception while executing 'readConfig' method. " + e.getMessage());
		} finally {
			try {
				fin.close();
				fin = null;
				prop = null;
			} catch (Exception e) {
				appInd.writeLog("Fail", "Exception while executing 'readConfig' method. " + e.getMessage());
			}
		}
	}
	/********************************
	 * Method Name : nowtimestamp 
	 * Purpose : this function provide the current date with time stamp (if you provide 'default')
	 * Author : Mahesh TK
	 * Reviewer : 
	 * Date of Creation :
	 * Date of modification :11/12/2018
	 *  ******************************/

	public String nowtimestamp(String strdefault) {
		String strdateTime = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date();
			strdateTime = dateFormat.format(date);
			if (!(strdefault.equalsIgnoreCase("default"))) {
				strdateTime = "D_" + ((strdateTime.replaceAll("/", "-")).replaceAll(":", "-")).replaceAll(" ", "_T_");
			}
			return strdateTime;
		} catch (Exception e) {
			appInd.writeLog("Exception", "Exception while executing 'nowtimestamp' method. " + e.getMessage());
			return null;
		}
	}

	/********************************
	 * Method Name : testcaseidclassmap Purpose : It will create map, contians
	 * testcase with corresponding class name along with method name Author : Mahesh
	 * TK Reviewer : Date of Creation : Date of modification :
	 * ******************************
	 */

	@SuppressWarnings("finally")
	public Map<String, String> testcaseidclassmap() {
		// TODO Auto-generated method stub
		Map<String, String> oMap = null;
		boolean bolstatus = false;

		try 
		{
			oMap = new HashMap<String, String>();
			
			// Adding testcase id as 'key' and Packagename.ClassName with Method name as
			// 'value' separated by delimiter '#' 
			oMap.put("LoginScreen", "org.honeywell.Trace.testcase_scripts.TestCase_Scenarios#TC_Loginscreen");
			oMap.put("Login", "org.honeywell.Trace.testcase_scripts.TestCase_Scenarios#Login");
			
			
    			//=========Maruthiraja
           			oMap.put("TC_CD_Save_filter_Public", "org.honeywell.Trace.testcase_scripts.Change_Detection#TC_CD_Save_filter_Public");
    			oMap.put("TC_CD_Save_filter_Private", "org.honeywell.Trace.testcase_scripts.Change_Detection#TC_CD_Save_filter_Private");
    		
    			
			//=========Maruthiraja
            			oMap.put("TC_CD_Save_filter_Public", "org.honeywell.Trace.testcase_scripts.Change_Detection#TC_CD_Save_filter_Public");
    			oMap.put("TC_CD_Save_filter_Private", "org.honeywell.Trace.testcase_scripts.Change_Detection#TC_CD_Save_filter_Private");
			oMap.put("TC_CD_ActionAcknowledge", "org.honeywell.Trace.testcase_scripts.Change_Detection#TC_CD_ActionAcknowledge");
    			oMap.put("TC_CD_ActionUnacknowledge", "org.honeywell.Trace.testcase_scripts.Change_Detection#TC_CD_ActionUnacknowledge");
    			oMap.put("TC_CD_ActionUnsuppress", "org.honeywell.Trace.testcase_scripts.Change_Detection#TC_CD_ActionUnsuppress");
    			oMap.put("TC_CD_ActionUnassignCR", "org.honeywell.Trace.testcase_scripts.Change_Detection#TC_CD_ActionUnassignCR");
    			oMap.put("TC_CD_Editfilter", "org.honeywell.Trace.testcase_scripts.Change_Detection#TC_CD_Editfilter");
    			oMap.put("TC_CD_Deletefilter", "org.honeywell.Trace.testcase_scripts.Change_Detection#TC_CD_Deletefilter");
    			oMap.put("TC_CD_ActionSuppress", "org.honeywell.Trace.testcase_scripts.Change_Detection#TC_CD_ActionSuppress");
    			
			oMap.put("Logout", "org.honeywell.Trace.testcase_scripts.TestCase_Scenarios#Logout");
			
			
				
			//Maruthiraja BN
			oMap.put("TC_CD_Facebook_login", "org.honeywell.Trace.testcase_scripts.Facebook#TC_CD_Facebook_login");
			oMap.put("TC_CD_IRCTC_Train_Search", "org.honeywell.Trace.testcase_scripts.Irctc_Train_Search#TC_CD_IRCTC_Train_Search");
			
			//Santhosh TP
			oMap.put("TC_CD_Rediffmail_login", "org.honeywell.Trace.testcase_scripts.Rediffmail#TC_CD_Rediffmail_login");
			oMap.put("TC_CD_ActiTime_login", "org.honeywell.Trace.testcase_scripts.ActiTime#TC_CD_ActiTime_login");
      		oMap.put("TC_CD_Create_User","org.honeywell.Trace.testcase_scripts.ActiTime#TC_CD_Create_User");
      		oMap.put("TC_CD_WayToAutomation_login", "org.honeywell.Trace.testcase_scripts.WayToAutomation#TC_CD_WayToAutomation_login");
			
			bolstatus = true;

		} 
		catch (Exception e) 
		{
			
			appInd.writeLog("Exception", "Exception while executing 'testcaseidclassmap' method. " + e.getMessage());
			bolstatus = false;
		} 
		finally {
			
			try {
				if (bolstatus) {
					return oMap;
				} else
					return null;
			} catch (Exception e) {
				appInd.writeLog("Exception",
						"Exception while executing 'getAndConvertToMap' method. " + e.getMessage());
				return null;
			}
		}
	}

	/********************************
	 * Method Name : writeResult 
	 * Purpose : this function writes the result in excel and return the String value contain test case name and result
	 * Author : Mahesh Tk
	 * Reviewer : 
	 * Date of Creation :
	 * Date of modification :11/12/2018
	 *  ******************************/
	
	/*@SuppressWarnings("finally")
	public  String writeResult(Map<String, String> omapMasterTD,Map<String, HashMap> oOutputResultMap, String strTCID, int nrownum,String strMasterTestDatasheetName,String strresultfilePath) {

		String strresultvalue = null;
		String strvalue=null;
		String strTDresult = null;
        		String[] objarr = null;
        		String strstatus=null;
        		String strTCName=null;
        		Map <String,String> oinpuTDtMap= new HashMap<String,String>();
        
	     
        try {        	
		      
		        //Reading the Master test data Map 
		    	String strValueString = omapMasterTD.get(strTCID);
		    	
		    	//Splitting into the array and checking the TestData type
				objarr = strValueString.split("#");
				// Storing Test case name from Master Test data sheet
				strTCName=objarr[1];
				
				if(objarr[3].toString().equals("Multiple"))
				{
					//Read the TestData Output Map 
					for(int nTDcnt=0;nTDcnt<oOutputResultMap.size();nTDcnt++)
					{
						oinpuTDtMap=oOutputResultMap.get(String.valueOf(nTDcnt+1));				
						strTDresult = oinpuTDtMap.get("result");
						if(strTDresult.equalsIgnoreCase("pass"))
						{
							strstatus+="true";
							datatable.setCellData(strresultfilePath, objarr[5].toString(), "Result", (nTDcnt+2), "Pass", "Green");
						}else if(strTDresult.equalsIgnoreCase("fail"))  {
							strstatus+="false";
							datatable.setCellData(strresultfilePath, objarr[5].toString(), "Result", (nTDcnt+2), "Fail", "red");
						}
					}
					
					//update result in the Master test data 
					if(strstatus.contains("false"))
					{										
						strresultvalue="fail";
						datatable.setCellData(strresultfilePath, strMasterTestDatasheetName, "Result", nrownum, "Fail", "red");
					}else
					{				
						strresultvalue="pass";
						datatable.setCellData(strresultfilePath, strMasterTestDatasheetName, "Result", nrownum, "Pass", "Green");				
					}
					
				}else {
					for(int nTDcnt=0;nTDcnt<oOutputResultMap.size();nTDcnt++)
					{
						oinpuTDtMap=oOutputResultMap.get(String.valueOf(nTDcnt+1));				
						strTDresult = oinpuTDtMap.get("result");				
						if(strTDresult.equalsIgnoreCase("pass"))
						{
							strresultvalue="pass";
							datatable.setCellData(strresultfilePath, strMasterTestDatasheetName, "Result", nrownum, "Pass", "Green");
						}else if(strTDresult.equalsIgnoreCase("fail"))
						{					
							strresultvalue="fail";
							datatable.setCellData(strresultfilePath, strMasterTestDatasheetName, "Result", nrownum, "Fail", "red");
						}
					}
				}
				
				// Storing into the Result map
				strvalue=strTCName+"#"+strresultvalue;
				
        }
		 catch (Exception e) {
				appInd.writeLog("Fail", "Exception while executing 'writeResult' method. " + e.getMessage());
				strvalue=null+"#"+null;
			}
        finally {
        	return strvalue;
        }
        
		} 
        
*/   
/*	@SuppressWarnings("finally")
	public  String writeResult(Map<String, String> omapMasterTD,Map<String, HashMap> oOutputResultMap, String strTCID, int nrownum,String strMasterTestDatasheetName,String strresultfilePath) {

		String strresultvalue = null;
		String strvalue=null;
		String strTDresult = null;
        String[] objarr = null;
        String strstatus=null;
        String strTCName=null;
        String countvalues = null;
        Map <String,String> oinpuTDtMap= new HashMap<String,String>();
        
	     
        try {        	
		      
		        //Reading the Master test data Map 
		    	String strValueString = omapMasterTD.get(strTCID);
		    	
		    	//Splitting into the array and checking the TestData type
				objarr = strValueString.split("#");
				// Storing Test case name from Master Test data sheet
				strTCName=objarr[1];
				
				if(objarr[3].toString().equals("Multiple"))
				{
					//Read the TestData Output Map 
					for(int nTDcnt=0;nTDcnt<oOutputResultMap.size();nTDcnt++)
					{
						oinpuTDtMap=oOutputResultMap.get(String.valueOf(nTDcnt+1));	
						// Check the result is available in Map
						if(oinpuTDtMap.containsKey("result")) 
						{						
							strTDresult = oinpuTDtMap.get("result");
								if(strTDresult.equalsIgnoreCase("pass"))
							{
								strstatus+="true";
								datatable.setCellData(strresultfilePath, objarr[5].toString(), "Result", (nTDcnt+2), "Pass", "Green");
							}else if(strTDresult.equalsIgnoreCase("fail"))  {
								strstatus+="false";
								datatable.setCellData(strresultfilePath, objarr[5].toString(), "Result", (nTDcnt+2), "Fail", "red");
							}
						}
					}
					
					//update result in the Master test data 
					if(strstatus.contains("false"))
					{										
						strresultvalue="fail";
						datatable.setCellData(strresultfilePath, strMasterTestDatasheetName, "Result", nrownum, "Fail", "red");
					}else if(strstatus.contains("true"))
					{				
						strresultvalue="pass";
						datatable.setCellData(strresultfilePath, strMasterTestDatasheetName, "Result", nrownum, "Pass", "Green");				
					}
					
				}else {
					for(int nTDcnt=0;nTDcnt<oOutputResultMap.size();nTDcnt++)
					{
						oinpuTDtMap=oOutputResultMap.get(String.valueOf(nTDcnt+1));	
						if(oinpuTDtMap.containsKey("result")) 
						{						
							strTDresult = oinpuTDtMap.get("result");	
		
							if(strTDresult.equalsIgnoreCase("pass"))
							{
								strresultvalue="pass";
								datatable.setCellData(strresultfilePath, strMasterTestDatasheetName, "Result", nrownum, "Pass", "Green");
							}else if(strTDresult.equalsIgnoreCase("fail"))
							{					
								strresultvalue="fail";
								datatable.setCellData(strresultfilePath, strMasterTestDatasheetName, "Result", nrownum, "Fail", "red");
							}
						}
					}
				}
				
				// Storing into the Result map
				strvalue=strTCName+"#"+strresultvalue;
				
        }
		 catch (Exception e) {
				appInd.writeLog("Fail", "Exception while executing 'writeResult' method. " + e.getMessage());
				strvalue=null+"#"+null;
			}
        finally {
        	return strvalue;
        }
        
		} */

	@SuppressWarnings("finally")
	public  String writeResult(Map<String, String> omapMasterTD,Map<String, HashMap> oOutputResultMap, String strTCID, int nrownum,String strMasterTestDatasheetName,String strresultfilePath) {

		String strresultvalue = null;
		String strvalue=null;
		String strTDresult = null;
        String[] objarr = null;
        String strstatus=null;
        String strTCName=null;
        String countvalues = null;
        Map <String,String> oinpuTDtMap= new HashMap<String,String>();
        
	     
        try {        	
		      
		        //Reading the Master test data Map 
		    	String strValueString = omapMasterTD.get(strTCID);
		    	
		    	//Splitting into the array and checking the TestData type
				objarr = strValueString.split("#");
				// Storing Test case name from Master Test data sheet
				strTCName=objarr[1];
				
				if(objarr[3].toString().equals("Multiple"))
				{
					//Read the TestData Output Map 
					for(int nTDcnt=0;nTDcnt<oOutputResultMap.size();nTDcnt++)
					{
						oinpuTDtMap=oOutputResultMap.get(String.valueOf(nTDcnt+1));	
						// Check the result is available in Map
						if(oinpuTDtMap.containsKey("result")) 
						{						
							strTDresult = oinpuTDtMap.get("result");
							countvalues = oinpuTDtMap.get("countvalue");
							if(countvalues == null) {
								countvalues = " ";
							}
							if(strTDresult.equalsIgnoreCase("pass"))
							{
								strstatus+="true";
								datatable.setCellData(strresultfilePath, objarr[5].toString(), "Result", (nTDcnt+2), "Pass", "Green",countvalues);
							}else if(strTDresult.equalsIgnoreCase("fail"))  {
								strstatus+="false";
								datatable.setCellData(strresultfilePath, objarr[5].toString(), "Result", (nTDcnt+2), "Fail", "red",countvalues);
							}
						}
					}
					
					//update result in the Master test data 
					if(strstatus.contains("false"))
					{										
						strresultvalue="fail";
						datatable.setCellData(strresultfilePath, strMasterTestDatasheetName, "Result", nrownum, "Fail", "red",countvalues);
					}else if(strstatus.contains("true"))
					{				
						strresultvalue="pass";
						datatable.setCellData(strresultfilePath, strMasterTestDatasheetName, "Result", nrownum, "Pass", "Green",countvalues);				
					}
					
				}else {
					for(int nTDcnt=0;nTDcnt<oOutputResultMap.size();nTDcnt++)
					{
						oinpuTDtMap=oOutputResultMap.get(String.valueOf(nTDcnt+1));	
						if(oinpuTDtMap.containsKey("result")) 
						{						
							strTDresult = oinpuTDtMap.get("result");	
							countvalues = oinpuTDtMap.get("countvalue");
							if(countvalues == null) {
								countvalues = " ";
							}
							if(strTDresult.equalsIgnoreCase("pass"))
							{
								strresultvalue="pass";
								datatable.setCellData(strresultfilePath, strMasterTestDatasheetName, "Result", nrownum, "Pass", "Green",countvalues);
							}else if(strTDresult.equalsIgnoreCase("fail"))
							{					
								strresultvalue="fail";
								datatable.setCellData(strresultfilePath, strMasterTestDatasheetName, "Result", nrownum, "Fail", "red",countvalues);
							}
						}
					}
				}
				
				// Storing into the Result map
				strvalue=strTCName+"#"+strresultvalue;
				
        }
		 catch (Exception e) {
				appInd.writeLog("Fail", "Exception while executing 'writeResult' method. " + e.getMessage());
				strvalue=null+"#"+null;
			}
        finally {
        	return strvalue;
        }
        
		} 
	/********************************
	 * Method Name : copyFileUsingStream 
	 * Purpose : this function copy the file from the provided source and it will paste to provided destination folder
	 * Author :Vijay.k
	 * Reviewer : 
	 * Date of Creation :
	 * Date of modification :11/12/2018
	 *  ******************************/
	

	public void copyFileUsingStream(String source, String dest)
	{
		InputStream is = null;
		OutputStream os = null;
		try {
			File sourceString = new File(source);
			File destString = new File(dest);
			is = new FileInputStream(sourceString);
			os = new FileOutputStream(destString);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
		} catch (Exception e) {
			appInd.writeLog("Fail", "Exception while executing 'copyFileUsingStream' method. " + e.getMessage());
			//return false;
		}
		
		finally {
			try {
				is.close();
				os.close();
			} catch (IOException e) {
				appInd.writeLog("Fail", "Exception while executing finally block 'copyFileUsingStream' method. " + e.getMessage());
			}
			
		}
	}

	/********************************
	 * Method Name : clickObject Purpose : Author : Reviewer : Date of Creation :
	 * Date of modification : ******************************
	 */
	public boolean clickObject(WebDriver oDriver, String strObjectName) {
		WebElement oEle = null;
		try {
			oEle = createAndGetObject(oDriver, strObjectName);
			if (oEle.isDisplayed()) {
				highlight(oDriver, oEle);
				//oDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
             //   Thread.sleep(5000);
				oEle.click();
			    Thread.sleep(5000);
				appInd.writeLog("Pass", "The element '" + strObjectName + "' was clicked successful");
				return true;
			} else {
				appInd.writeLog("Fail", "The element '" + strObjectName + "' was not displayed");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			appInd.writeLog("Fail", "Exception while executing 'clickObject' method. " + e.getMessage());
			return false;
		}
	}
	
	/********************************
	 * Method Name : clickObject_js 
	 * Purpose :  To click the button using Javascript
	 * Author : Mahesh TK
	 * Reviewer : 
	 * Date of Creation :23-Jan-2019
	 * Date of modification :
	 *  ******************************/
	 
	public boolean clickObject_js(WebDriver oDriver, String strObjectName) {
		WebElement oEle = null;
		try {
			oEle = createAndGetObject(oDriver, strObjectName);
			if (oEle.isDisplayed()) {
				highlight(oDriver, oEle);						
				((JavascriptExecutor) oDriver).executeScript("arguments[0].click();",oEle);
				Thread.sleep(2000);			   
				appInd.writeLog("Pass", "The element '" + strObjectName + "' was clicked successful");
				return true;
			} else {
				appInd.writeLog("Fail", "The element '" + strObjectName + "' was not displayed");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			appInd.writeLog("Fail", "Exception while executing 'clickObject_js' method. " + e.getMessage());
			return false;
		}
	}


	/********************************
	 * Method Name : setObject Purpose : Author : Reviewer : Date of Creation : Date
	 * of modification : ******************************
	 *//*
	*//*******
	 public boolean setObject(WebDriver oDriver, String strObjectName, String strData) {
		WebElement oEle = null;
		try {
			oEle = createAndGetObject(oDriver, strObjectName);
			if (oEle.isDisplayed()) {
				highlight(oDriver, oEle);
				oEle.sendKeys(strData);
				appInd.writeLog("Pass",
						"The value '" + strData + "' was entered in the element '" + strObjectName + "' successful");
				return true;
			} else {
				appInd.writeLog("Fail", "The element '" + strObjectName + "' was not displayed");
				return false;
			}
		} catch (Exception e) {
			appInd.writeLog("Fail", "Exception while executing 'setObject' method. " + e.getMessage());
			return false;
		}
	}
     */
	/********************************
	 * Method Name : setObject Purpose : Author : Reviewer : Date of Creation : Date
	 * of modification : ******************************
	 */
	public boolean setObject(WebDriver oDriver, String strObjectName, String strData) {
		WebElement oEle = null;
		try {System.out.println("hello");
		Thread.sleep(2000);
		System.out.println("er");
			oEle = createAndGetObject(oDriver, strObjectName);
			if (oEle.isDisplayed()) {
				highlight(oDriver, oEle);
				if(strData=="E")
				oEle.sendKeys(Keys.ENTER);
				else
					oEle.sendKeys(strData);
				appInd.writeLog("Pass",
						"The value '" + strData + "' was entered in the element '" + strObjectName + "' successful");
				return true;
			} else {
				appInd.writeLog("Fail", "The element '" + strObjectName + "' was not displayed");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			appInd.writeLog("Fail", "Exception while executing 'setObject' method. " + e.getMessage());
			return false;
		}
	}
	/********************************
	 * Method Name : selectDropdownvalue Purpose : Author : Reviewer : Date of
	 * Creation : Date of modification : ******************************
	 */
	public boolean selectDropdownvalue(WebDriver oDriver, String strObjectName, String strData) {
		WebElement oEle = null;
		try {
			oEle = createAndGetObject(oDriver, strObjectName);
			if (oEle.isDisplayed()) {
				highlight(oDriver, oEle);
				Select oDropdown = new Select(oEle);
				oDropdown.selectByVisibleText(strData);
				Thread.sleep(500);
				WebElement option = oDropdown.getFirstSelectedOption();
				if (option.getText().equalsIgnoreCase(strData)) {
					appInd.writeLog("Pass", "The value '" + strData + "' was Selected in the element '" + strObjectName
							+ "' successful");
					return true;
				} else {
					appInd.writeLog("Pass", "The value '" + strData + "' was not Selected in the element '"
							+ strObjectName + "' successful");
					return false;
				}

			} else {
				appInd.writeLog("Fail", "The element '" + strObjectName + "' was not found");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			appInd.writeLog("Fail", "Exception while executing 'selectDropdownvalue' method. " + e.getMessage());
			return false;
		}
	}
	
	
	/********************************
	 * Method Name : selectDropdownvalue 
	 * Purpose : 
	 * Author : 
	 * Reviewer : 
	 * Date of Creation : 
	 * Date of modification : 
	 * ******************************
	 */
	public boolean selectDropdownvalue(WebDriver oDriver, String strObjectName, String strData,  String strSelectMethodtypes) {
		WebElement oEle = null;
		boolean bolstuts= false;
		String strActualSeletedvalue =null;
		try {
			oEle = createAndGetObject(oDriver, strObjectName);
			if (!oEle.isDisplayed()) {
				highlight(oDriver, oEle);				
				Select oDropdown = new Select(oEle);
				System.out.println("test");
				
				//get the first selected value before performing operation 
				WebElement option = oDropdown.getFirstSelectedOption();
				strActualSeletedvalue = option.getText();
				
				if(!(strActualSeletedvalue.equalsIgnoreCase(strData))) 
				{
				
					// choosing the select Method Types
					switch(strSelectMethodtypes.toLowerCase())
					{
						case "visibletext":
							oDropdown.selectByVisibleText(strData);
							break;
						case "value":
							oDropdown.selectByValue(strData);
							break;
							
						case "index":
							oDropdown.selectByIndex(Integer.parseInt(strData));
							break;
							
					}
					
					Thread.sleep(500);
				}
				
				
				// vallidation 
				option = oDropdown.getFirstSelectedOption();
				
				if (strSelectMethodtypes.equalsIgnoreCase("index"))
					{
						if (! (option.getText().equalsIgnoreCase(strData)))	bolstuts =true;
						 
					}
				else {						 
						 if (option.getText().equalsIgnoreCase(strData)) bolstuts =true;
						 		
					}
				
				if (bolstuts) 
					{
						appInd.writeLog("Pass", "The value '" + strData + "' was Selected in the element '" + strObjectName
							+ "' successful");						
					} else {
						appInd.writeLog("Fail", "The value '" + strData + "' was not Selected in the element '"
							+ strObjectName + "' successful");						
					}

			} else {
					appInd.writeLog("Fail", "The element '" + strObjectName + "' was not found");
					bolstuts= true; //element is present.
				   }
		} 
		catch (Exception e)
		{
			
			appInd.writeLog("Fail", "Exception while executing 'selectDropdownvalue' method. " + e.getMessage());
			return false;
		}finally 
		{return bolstuts;}
		
	}

	/********************************
	 * Method Name : clearAndSetObject Purpose : Author : Reviewer : Date of
	 * Creation : Date of modification : ******************************
	 */
	public boolean clearAndSetObject(WebDriver oDriver, String strObjectName, String strData) {
		WebElement oEle = null;
		try {
			oEle = createAndGetObject(oDriver, strObjectName);
			if (oEle.isDisplayed()) {
				highlight(oDriver, oEle);
			//	oEle.click();
				((JavascriptExecutor) oDriver).executeScript("arguments[0].click();",oEle);			
				oEle.clear();
				oEle.sendKeys(Keys.BACK_SPACE);
				oEle.sendKeys(strData);
				appInd.writeLog("Pass",
						"The value '" + strData + "' was entered in the element '" + strObjectName + "' successful");
				return true;
			} else {
				appInd.writeLog("Fail", "The element '" + strObjectName + "' was not displayed");
				return false;
			}
		} catch (Exception e) {
			appInd.writeLog("Fail", "Exception while executing 'clearAndSetObject' method. " + e.getMessage());
			return false;
		}
	}
	
	/********************************
	 * Method Name : clearAndSetObjects Purpose : Author : Reviewer : Date of
	 * Creation : Date of modification : ******************************
	 */
	
//	@SuppressWarnings("unused")
//	public boolean clearAndSetObjects(WebDriver oDriver, String strObjectName, List<String> strDatas) {
//		List<WebElement> oEles = null;
//		try {
//			oEles = createAndGetObjectList(oDriver, strObjectName);
//			boolean flag = ifElementsPresent(oDriver, strObjectName);
//			if(flag==true) {
//				for(int i=0;i<strDatas.size();i++) {
//					highlight(oDriver, oEles.get(i));
//					((JavascriptExecutor) oDriver).executeScript("arguments[0].click();",oEles.get(i));			
//					oEles.get(i).clear();
//					oEles.get(i).sendKeys(Keys.BACK_SPACE);
//					oEles.get(i).sendKeys(strDatas.get(i));
//					appInd.writeLog("Pass",
//							"The values '" + strDatas.get(i) + "' was entered in the element '" + strObjectName + "' successful");
//					return true;
//				}
//				return true;
//			} else {
//				appInd.writeLog("Fail", "The element '" + strObjectName + "' was not displayed");
//				return false;
//			}
//		} catch (Exception e) {
//			appInd.writeLog("Fail", "Exception while executing 'clearAndSetObjects' method. " + e.getMessage());
//			return false;
//		}
//	}


	/********************************
	 * Method Name : verifyText Purpose : Author : Reviewer : Date of Creation :
	 * Date of modification : ******************************
	 */
	public boolean verifyText(WebDriver oDriver, String strObjectName, String strExpected, String strObjType) {
		WebElement oEle = null;
		String strActual = null;
		Select oSel = null;
		try {
			oEle = createAndGetObject(oDriver, strObjectName);
			if (oEle.isDisplayed()) {
				highlight(oDriver, oEle);
				switch (strObjType.toLowerCase()) {
				case "text":
					strActual = oEle.getText();
					break;
				case "value":
					strActual = oEle.getAttribute("value");
					break;
				case "list":
					oSel = new Select(oEle);
					strActual = oSel.getFirstSelectedOption().getText();
					break;
				default:
					appInd.writeLog("Fail", "Invalid object type '" + strObjType + "' was specified");
					return false;
				}

				if (appInd.compareValue(strActual, strExpected)) {
					return true;
				} else {
					return false;
				}

			} else {
				appInd.writeLog("Fail", "The element '" + strObjectName + "' doesn't displayed");
				return false;
			}
		} catch (Exception e) {
			appInd.writeLog("Fail", "Exception while executing 'verifyText' method. " + e.getMessage());
			return false;
		}
	}

	/********************************
	 * Method Name : compareValue Purpose : Author : Reviewer : Date of Creation :
	 * Date of modification : ******************************
	 */
	public boolean compareValue(String strActual, String strExpected) {
		try {
			if (strActual.equalsIgnoreCase(strExpected)) {
				appInd.writeLog("Pass", "Both actual '" + strActual + "' and Expected '" + strExpected + "' are same");
				return true;
			} else {
				appInd.writeLog("Fail",
						"Both actual '" + strActual + "' and Expected '" + strExpected + "' are NOT same");
				return false;
			}
		} catch (Exception e) {
			appInd.writeLog("Fail", "Exception while executing 'compareValue' method. " + e.getMessage());
			return false;
		}
	}

	/********************************
	 * Method Name : verifyObjectExist Purpose : Author : Reviewer : Date of
	 * Creation : Date of modification : ******************************
	 */
	public boolean verifyObjectExist(WebDriver oDriver, String strObjectName) {
		WebElement oEle = null;
		try {
			oEle = appInd.createAndGetObject(oDriver, strObjectName);
			if (oEle.isDisplayed()) {
				highlight(oDriver, oEle);
				appInd.writeLog("Pass", "The element '" + strObjectName + "' displayed successful");
				return true;
			} else {
				appInd.writeLog("Fail", "The element '" + strObjectName + "' was not displayed");
				return false;
			}
		} catch (Exception e) {
			appInd.writeLog("Exception", "Exception while executing 'writeResult' method. " + e.getMessage());
			return false;
		} finally {
			oEle = null;
		}
	}

	/********************************
	 * Method Name : createAndGetObject Purpose : Author : Reviewer : Date of
	 * Creation : Date of modification : ******************************
	 */
	public WebElement createAndGetObject(WebDriver oDriver, String strObjectName) {
		WebElement oEle = null;
		String strObjDesc = null;
		try {
			strObjDesc = objMap.get(strObjectName);
			String objArr[] = strObjDesc.split("#");
			switch (objArr[0].toLowerCase()) {
			case "id":
				oEle = oDriver.findElement(By.id(objArr[1]));
				break;
			case "name":
				oEle = oDriver.findElement(By.name(objArr[1]));
				break;
			case "xpath":
				oEle = oDriver.findElement(By.xpath(objArr[1]));
				break;
			case "cssselector":
				oEle = oDriver.findElement(By.cssSelector(objArr[1]));
				break;
			case "linktext":
				oEle = oDriver.findElement(By.linkText(objArr[1]));
				break;
			case "className":
				oEle = oDriver.findElement(By.className(objArr[1]));
				break;
			
			default:
				appInd.writeLog("Fail", "Invalid locator name '" + objArr[0] + "'");
				return null;
			}
			return oEle;
		} catch (NoSuchElementException e) {			
			appInd.writeLog("#", e.getMessage());
			return null;
		}catch (Exception e) {
			appInd.writeLog("Exception", "Exception while executing 'createAndGetObject' method. " + e.getMessage());
			return null;
		}
	}

	/********************************
	 * Method Name : isElementPresent purpose : Author : Creation Date : Reviewed By
	 * : ******************************
	 */
	public boolean isElementPresent(WebDriver oDriver, String strObjectName) {
		// By objBy=null;
		WebElement oEle = null;
		boolean retstatus=false;
		try {
			// objBy = createAndGetByObject( strObjectName);
			// oDriver.findElement(objBy);
			oEle = createAndGetObject(oDriver, strObjectName);
			if (oEle != null) {
				if (oEle.isDisplayed()) 
				{
					retstatus = true;
				} 
			}
			
		} catch (Exception e) 
		{
			appInd.writeLog("Exception", "Exception while executing 'isElementPresent' method. " + e.getMessage());
			retstatus= false;
			
			
		}finally { return retstatus;}		
		
	}

	/********************************
	 * Method Name : isAlertPresent purpose : Author : Creation Date : Reviewed By :
	 * ******************************
	 */
	public boolean isAlertPresent(WebDriver oDriver) {
		try {
			Thread.sleep(2000);
			oDriver.switchTo().alert();
			appInd.writeLog("Pass", "Switched to Alert");
			return true;
		} catch (Exception e) {
			appInd.writeLog("FAIL", "exception:" + e.toString());
			return false;
		}
	}

	/********************************
	 * Method Name : handleAlert purpose : Author : Creation Date : Reviewed By :
	 * ******************************
	 */
	public boolean handleAlert(WebDriver oDriver) {
		try {
			Thread.sleep(1000);
			oDriver.switchTo().alert().accept();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/********************************
	 * Method Name : getAlertMessage purpose : Author : Creation Date : Reviewed By
	 * : ******************************
	 */
	public String getAlertMessage(WebDriver oDriver) {
		String strmesg = null;
		Alert alert = null;
		try {
			Thread.sleep(1000);
			alert = oDriver.switchTo().alert();
			appInd.writeLog("Pass", "Switched to Alert");
			strmesg = alert.getText();
			appInd.writeLog("Pass", "getting Alert Text");
			alert.accept();
			return strmesg;
		} catch (Exception e) {
			return strmesg;
		}
	}

	/********************************
	 * Method Name : SwitchToFrame Purpose : Author : Reviewer : Date of Creation :
	 * Date of modification : ******************************
	 */
	public boolean SwitchToFrame(WebDriver oDriver, String strObjectName) {
		WebElement oEle = null;
		try {
			oEle = appInd.createAndGetObject(oDriver, strObjectName);
			if (oDriver.switchTo().frame(oEle) != null) {
				appInd.writeLog("Pass", "The frame '" + strObjectName + "'  is Switched successful");
				return true;
			} else {
				appInd.writeLog("Fail", "The frame '" + strObjectName + "' was not Switched");
				return false;
			}
		} catch (Exception e) {
			appInd.writeLog("Exception", "Exception while executing 'SwitchToFrame' method. " + e.getMessage());
			return false;
		} finally {
			oEle = null;
		}
	}

	/********************************
	 * Method Name : SwitchBackfromFrame Purpose : Author : Reviewer : Date of
	 * Creation : Date of modification : ******************************
	 */
	public boolean SwitchBackfromFrame(WebDriver oDriver) {
		try {
			if (oDriver.switchTo().defaultContent() != null) {
				appInd.writeLog("Pass", "The frame Switched back is successful");
				return true;
			} else {
				appInd.writeLog("Fail", "The frame was not Switched back");
				return false;
			}
		} catch (Exception e) {
			appInd.writeLog("Exception", "Exception while executing 'SwitchToFrame' method. " + e.getMessage());
			return false;
		}
	}

	/********************************
	 * Method Name : getInputData Purpose : This method will return the map contains
	 * test data Author : Reviewer : Date of Creation : Date of modification :
	 * ******************************
	 */

	/*@SuppressWarnings({ "finally", "rawtypes" })
	public Map<String, HashMap> getInputData(String strTestCaseID) {
		String strvalue = null;
		String objarr[] = null;
		String objTDarr[] = null;
		HashMap<String, String> oTDMap = null;
		HashMap<String, String> oTDsheetMap = null;
		HashMap<String, HashMap> oTDretMap = null;
		String strkey = null;
		String strval = null;
		String strdatasource = null;
		String strTDvalue = null;

		int totalparam = 0;
		try {
			// oTDMap = new HashMap<String, String>();
			oTDsheetMap = new HashMap<String, String>();
			oTDretMap = new HashMap<String, HashMap>();
			oTDMap=new HashMap<String, String>();
			// Get Test case parameter value from the Map
			strvalue = otestdataMap.get(strTestCaseID);

			if (strvalue != null) {
				// Split the value based on delimiter
				objarr = strvalue.split("#");
				// read the data source in Test data sheet
				strdatasource = objarr[3];
				if (strdatasource.equalsIgnoreCase("Multiple")) {
					oTDsheetMap = datatable.getInputTestData(strrespectivemodulepath, objarr[5]);				

					// Read the "total parameters" number in 5th column of TestData Sheet.
					if (objarr.length > 4) {
						totalparam = Integer.parseInt(objarr[4]) + 2;
						// Set default value to 1 if parameter "not available"
						if (totalparam == 1) {
							oTDMap.put("param", "NA");
						}
						//System.out.println("");

						// Iterate all the values present in the sheet
						for (int icnt = 1; icnt < oTDsheetMap.size(); icnt++) {
							oTDMap = new HashMap<String, String>();
							strTDvalue = oTDsheetMap.get(String.valueOf(icnt));

							// Split the value based on delimiter
							objTDarr = strTDvalue.split("#");

							for (int i = 0; i <= totalparam - 3; i++) {
								strkey = "Param_" + String.valueOf(i + 1);
								//try {
										if ((i) <= totalparam) 
										{
											if (i < objTDarr.length-1)
											{
												strval = objTDarr[i + 1];
												oTDMap.put(strkey, strval);
											}
										}
//									}
//								catch (Exception e) 
//								{
//									System.out.println(e.getMessage() );
//								}
								
								
							}
					
							//storing the execution status
							if (objTDarr[0].contains("yes")) {
								oTDMap.put("Executestatus", "yes");
							}else {
								oTDMap.put("Executestatus", "no");
							}
							

							oTDretMap.put(String.valueOf(icnt), oTDMap);
						}
					}
				} else {
					// Read the "total parameters" number in 5th column of TestData Sheet.
					if (objarr.length > 4) {
						System.out.println(objarr.length);
						totalparam = Integer.parseInt(objarr[4]) + 6;
						// Set default value to 1 if parameter "not available"
						if (totalparam == 1) {
							oTDMap.put("param", "NA");
						}

						for (int i = 4; i < totalparam - 2; i++) {
							strkey = "Param_" + String.valueOf(i - 3);
							if ((i) < totalparam - 1) {
								strval = objarr[i + 1];
								oTDMap.put(strkey, strval);
							}
						}
						//storing the execution status
						if (objarr[2].equalsIgnoreCase("Yes")) {
							oTDMap.put("Executestatus", "yes");
						}else {
							oTDMap.put("Executestatus", "no");
						}

						oTDretMap.put(String.valueOf(1), oTDMap);
					} else {
						appInd.writeLog("#", "'getInputData':Total paramaters not found ");
					}

				}

			} else {
				appInd.writeLog("#", "'getInputData':TestCase ID " + strvalue + "not found ");
				return null;
			}

		} catch (Exception e) {
			appInd.writeLog("Exception", "Exception while executing 'getInputData' method. " + e.getMessage());
			return null;

		} finally {
			return oTDretMap;
		}
	}
*/	
	
	@SuppressWarnings({ "finally", "rawtypes" })
	public Map<String, HashMap> getInputData(String strTestCaseID) {
		String strvalue = null;
		String objarr[] = null;
		String objTDarr[] = null;
		HashMap<String, String> oTDMap = null;
		HashMap<String, String> oTDsheetMap = null;
		HashMap<String, HashMap> oTDretMap = null;
		String strkey = null;
		String strval = null;
		String strdatasource = null;
		String strTDvalue = null;

		int totalparam = 0;
		try {
			// oTDMap = new HashMap<String, String>();
			oTDsheetMap = new HashMap<String, String>();
			oTDretMap = new HashMap<String, HashMap>();
			oTDMap=new HashMap<String, String>();
			// Get Test case parameter value from the Map
			strvalue = otestdataMap.get(strTestCaseID);

			if (strvalue != null) {
				// Split the value based on delimiter
				objarr = strvalue.split("#");
				// read the data source in Test data sheet
				strdatasource = objarr[3];
				if (strdatasource.equalsIgnoreCase("Multiple")) {
					oTDsheetMap = datatable.getInputTestData(strrespectivemodulepath, objarr[5]);				

					// Read the "total parameters" number in 5th column of TestData Sheet.
					if (objarr.length > 4) {
						totalparam = Integer.parseInt(objarr[4]) + 2;
						// Set default value to 1 if parameter "not available"
						if (totalparam == 1) {
							oTDMap.put("param", "NA");
						}
						//System.out.println("");

						// Iterate all the values present in the sheet
						for (int icnt = 1; icnt < oTDsheetMap.size(); icnt++) {
							oTDMap = new HashMap<String, String>();
							strTDvalue = oTDsheetMap.get(String.valueOf(icnt));

							// Split the value based on delimiter
							objTDarr = strTDvalue.split("#");

							for (int i = 0; i <= totalparam - 3; i++) {
								strkey = "Param_" + String.valueOf(i + 1);
								//try {
										if ((i) <= totalparam) 
										{
											if (i < objTDarr.length-1)
											{
												strval = objTDarr[i + 1];
												oTDMap.put(strkey, strval);
											}
										}
//									}
//								catch (Exception e) 
//								{
//									System.out.println(e.getMessage() );
//								}
								
								
							}
					
							//storing the execution status
							if (objTDarr[0].contains("yes")) {
								oTDMap.put("Executestatus", "yes");
							}else {
								oTDMap.put("Executestatus", "no");
							}
							

							oTDretMap.put(String.valueOf(icnt), oTDMap);
						}
					}
				} else {
					// Read the "total parameters" number in 5th column of TestData Sheet.
					if (objarr.length > 4) {
						System.out.println(objarr.length);
						totalparam = Integer.parseInt(objarr[4]) + 6;
						// Set default value to 1 if parameter "not available"
						if (totalparam == 1) {
							oTDMap.put("param", "NA");
						}

						for (int i = 4; i < totalparam - 2; i++) {
							strkey = "Param_" + String.valueOf(i - 3);
							if ((i) < totalparam - 1) {
								strval = objarr[i + 1];
								oTDMap.put(strkey, strval);
							}
						}
						//storing the execution status
						if (objarr[2].equalsIgnoreCase("Yes")) {
							oTDMap.put("Executestatus", "yes");
						}else {
							oTDMap.put("Executestatus", "no");
						}

						oTDretMap.put(String.valueOf(1), oTDMap);
					} else {
						appInd.writeLog("#", "'getInputData':Total paramaters not found ");
					}

				}

			} else {
				appInd.writeLog("#", "'getInputData':TestCase ID " + strvalue + "not found ");
				return null;
			}

		} catch (Exception e) {
			appInd.writeLog("Exception", "Exception while executing 'getInputData' method. " + e.getMessage());
			return null;

		} finally {
			return oTDretMap;
		}
	}
	

		/********************************
	 * Method Name : createAndGetByObject Purpose : It will return 'By' Object based
	 * on the provided 'strObjectName' Name Author : Reviewer : Date of Creation :
	 * Date of modification : ******************************
	 */
	public By createAndGetByObject(String strObjectName) {
		By oEle = null;
		String strObjDesc = null;
		try {
			strObjDesc = objMap.get(strObjectName);
			String objArr[] = strObjDesc.split("#");
			switch (objArr[0].toLowerCase()) {
			case "id":
				oEle = (By.id(objArr[1]));
				break;
			case "name":
				oEle = (By.name(objArr[1]));
				break;
			case "xpath":
				oEle = (By.xpath(objArr[1]));
				break;
			case "cssselector":
				oEle = (By.cssSelector(objArr[1]));
				break;
			case "linktext":
				oEle = (By.linkText(objArr[1]));
				break;
			default:
				appInd.writeLog("Fail", "Invalid locator name '" + objArr[0] + "'");
				return null;
			}
			return oEle;
		} catch (Exception e) {
			appInd.writeLog("Exception", "Exception while executing 'createAndGetByObject' method. " + e.getMessage());
			return null;
		}
	}

	/********************************
	 * Method Name : CreateHTML Purpose : It will create the final Html report
	 * Author : Mahesh TK Reviewer : Date of Creation : Date of modification :
	 ******************************/

		public void CreateHTML(String FilePath, String Starttime, String Endtime,
			LinkedHashMap<String, String> oTreeMaptestcaseresult,String strModulename) {

		String resultFileName;
		String TestCaseName = null;
		String TestCaseID = null;
		String status = null;
		String Totaltime = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		resultFileName = FilePath + "\\Trace_AutomationResult.html";
		String snapshotPath = System.getProperty("user.dir")+"\\Results\\snapshot\\";
		try {
			// Get the Overall Testcase details
			LinkedHashMap<String, Integer> oTreeMapTCcount = getResultcount(oTreeMaptestcaseresult);
			if (oTreeMapTCcount != null) {

				// Create Pie chart
				generatepie(FilePath + "\\piechart.png", oTreeMapTCcount);
				// claculate the Time difference
				Totaltime = datedifference(Starttime, Endtime, "default");
				fw = new FileWriter(resultFileName);
				bw = new BufferedWriter(fw);
				bw.write("<html>");
				bw.write("<head>");
				bw.write("<title>Automation Results</title>");
				bw.write("<meta charset=\"utf-8\">");
				bw.write(" <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
				bw.write(" <link rel=\"stylesheet\" href=\"Bootstrap337.css\">");
				bw.write("</head>");
				bw.write("<body bgcolor=##FFF5EE>");
				bw.write("<h1 style=\"background-color:blue;color:White;text-align:center;\">Automation Execution Results</h1>");
				bw.write("<div style=\"border:3px solid MidnightBlue;margin-left:5px;margin-right:5px;\" >");
				bw.write("<div class=\"row\">");
				bw.write("<div class=\"col-sm-5\"  ><h3 Style=\"margin-left:65px;\"><u>AutomationDetails </u></h3>");
				bw.write("<div class=\"col-sm-7\" >");
				bw.write("</br>");
				bw.write("<table  border=1 width=300 cellspacing=2 cellpadding=1 align=left >");
				bw.write("<tr>");
				bw.write("<th Style=\"text-align:center;color:MidnightBlue;\" bgcolor=#F4A460 colspan=\"2\"><b>Framework Execution Details</b></th>");
				bw.write("</tr>");
				bw.write("<tr>");
				bw.write("<td Style=\"text-align:center;\">Application Name</td>");
				//bw.write("<td Style=\"text-align:center;\">"+System.getProperty("APPLICATIONNAME")+"</td>");
				bw.write("<td Style=\"text-align:center;\">Trace Automation</td>");
				bw.write("</tr>");
				bw.write("<tr>");
				bw.write("<td Style=\"text-align:center;\">Start Time</td>");
				bw.write("<td Style=\"text-align:center;\">" + Starttime + "</td>");
				bw.write("</tr>");
				bw.write("<tr>");
				bw.write("<td Style=\"text-align:center;\">End Time</td>");
				bw.write("<td Style=\"text-align:center;\">" + Endtime + "</td>");
				bw.write("</tr>");
				bw.write("<tr>");
				bw.write("<td Style=\"text-align:center;\">Total Execution Time</td>");
				bw.write("<td Style=\"text-align:center;\">" + Totaltime + "</td>");
				bw.write("</tr>");
				bw.write("</table>");
				bw.write("</br>");
				bw.write("</div>");
				bw.write("</div>");

				// attaching the Pie chart Image
				bw.write("<div class=\"col-sm-4\" ><h3 Style=\"margin-left:65px;\"><u>PIE Chart</u></h3>");
				bw.write("<div class=\"col-sm-6\">");
				bw.write(
						"<img src=\"piechart.png\" alt=\"Failed to generate Pie chart\" width=\"200\" height=\"200\">");
				bw.write("</div>");
				bw.write("</div>");

				// Execution Table details
				bw.write("<div class=\"col-sm-3\" ><h3 Style=\"margin-left:50px;\"><u>Execution Details</u></h3>");
				bw.write("</br>");
				bw.write("<table  border=1  cellspacing=2 cellpadding=1 align=left width=300>");
				bw.write("<tr>");
				bw.write(
						"<th Style=\"text-align:center;color:MidnightBlue\" bgcolor=#F4A460 align=center colspan=\"2\"><b>Overall TestCase Details</b></th>");
				bw.write("</tr>");
				bw.write("<tr>");
				bw.write("<td  Style=\"text-align:center;\">Total Testcase</td>");
				bw.write("<td Style=\"text-align:center;\">" + (oTreeMapTCcount.get("TotalTestcase")) + "</td>");
				bw.write("</tr>");
				bw.write("<tr>");
				bw.write("<td Style=\"text-align:center;\">Passed</td>");
				bw.write("<td Style=\"text-align:center;\">" + (oTreeMapTCcount.get("Passed")) + "</td>");
				bw.write("</tr>");
				bw.write("<tr>");
				bw.write("<td Style=\"text-align:center;\">Failed</td>");
				bw.write("<td Style=\"text-align:center;\">" + (oTreeMapTCcount.get("Failed")) + "</td>");
				bw.write("</table>");
				bw.write("</br>");
				bw.write("</div>");
				bw.write("</div>");
				bw.write("</br>");
				bw.write("</div>");
				bw.write("<br>");

				// TestCase details_Header columns
				bw.write(
						"<div style=\"border:3px solid MidnightBlue;margin-left:5px;margin-right:5px;margin-bottom:5px;\" >");
				bw.write("<h3 align=center><u>Testcase Execution Results</u></h3>");
				bw.write("<table align=center border=1 cellspacing=1 cellpadding=1 width=1000>");
				bw.write("<tr>");
				bw.write("<th Style=\"text-align:center;color:MidnightBlue;\"  bgcolor=#F4A460><b>TestCaseID</b></td>");
				bw.write("<th Style=\"text-align:center;color:MidnightBlue;\" bgcolor=#F4A460><b>TestCaseName</b></td>");
				bw.write("<th Style=\"text-align:center;color:MidnightBlue;\" bgcolor=#F4A460><b>Status</b></td>");
				bw.write("<th Style=\"text-align:center;color:MidnightBlue;\" bgcolor=#F4A460><b>Link</b></td>");
				bw.write("</tr>");

				// TestCase details
				Set<String> keys = oTreeMaptestcaseresult.keySet();
				for (String key : keys) {
					//System.out.println(oTreeMaptestcaseresult.get(key));
					// split the TestCase result

					String[] strTCResdetails = (oTreeMaptestcaseresult.get(key).split("#"));
					TestCaseID = key;
					TestCaseName = strTCResdetails[0];
					status = strTCResdetails[1];

					bw.write("<tr>");
					bw.write("<td align=center>" + TestCaseID + "</td>");
					bw.write("<td align=left>" + TestCaseName + "</td>");

					if (status.equalsIgnoreCase("Pass")) {
						bw.write("<td align=center bgcolor=#008000 Style=\"color:greenyellow;\">Pass</td>");
					} else {
						bw.write("<td align=center bgcolor=#FF0000 Style=\"color:ghostwhite;\">Fail</td>");
						if(!(strModulename.equalsIgnoreCase("Performance_module")))
						bw.write("<td align=center bgcolor=#FF0000 Style=\"color:ghostwhite;\"><a href= "+snapshotPath+" class=\"btn\">click</a></td>");
					}
					bw.write("</tr>");
				}
				bw.write("</table>");
				bw.write("</br>");
				bw.write("</div>");
				bw.write("</body>");
				bw.write("</html>");

			}
		} catch (Exception e) {
			appInd.writeLog("Exception", "Exception while executing 'createHTML' method. " + e.getMessage());
		} finally {
			try {
				bw.flush();
				fw.close();
				bw.close();
				fw = null;
				bw = null;
			} catch (Exception e) {
				appInd.writeLog("Exception",
						"Exception while executing 'createHTML' method in finally block. " + e.getMessage());
			}
		}
	}
	/********************************
	 * Method Name : getResultcount Purpose : It will returns the testcase count
	 * ,Passed count and Failed count. Author : Mahesh TK Reviewer : Date of
	 * Creation : Date of modification :
	 ******************************/

	public LinkedHashMap<String, Integer> getResultcount(LinkedHashMap<String, String> oTreeMaptestcaseresult) {
		try {
			String status = null;
			String strkeyvalue = null;
			int npasscount = 0;
			int nFailcount = 0;
			int totalcount = 0;
			LinkedHashMap<String, Integer> oTreeMapreturn = new LinkedHashMap<String, Integer>();
			Set<String> keys = oTreeMaptestcaseresult.keySet();
			for (String key : keys) {
				// split the TestCase result
				strkeyvalue = oTreeMaptestcaseresult.get(key);

				if (strkeyvalue != null) {
					String[] strTCResdetails = (strkeyvalue.split("#"));

					if (key != null) {
						totalcount += 1;
					}
					status = strTCResdetails[1];
					if (status.equalsIgnoreCase("pass")) {
						npasscount += 1;
					} else if (status.equalsIgnoreCase("fail")) {
						nFailcount += 1;
					}
				}

			}

			oTreeMapreturn.put("TotalTestcase", totalcount);
			oTreeMapreturn.put("Passed", npasscount);
			oTreeMapreturn.put("Failed", nFailcount);

			return oTreeMapreturn;
		} catch (Exception e) {
			appInd.writeLog("Exception", "Exception while executing 'getResultcount' method. " + e.getMessage());
			return null;
		}
	}

	/********************************
	 * Method Name : generatepie Purpose : It will generate the pie charts image
	 * Author : Mahesh TK Reviewer : Date of Creation : Date of modification :
	 ******************************/
	public void generatepie(String strImgpath, Map<String, Integer> oTreeMapTCcount) {
		try {

			// Prepare the data set
			DefaultPieDataset pieDataset = new DefaultPieDataset();
			pieDataset.setValue("Fail", (oTreeMapTCcount.get("Failed")));
			pieDataset.setValue("Pass", (oTreeMapTCcount.get("Passed")));

			// Create the chart
			JFreeChart chart = ChartFactory.createPieChart("Execution report", pieDataset, true, false, false);

			// Save chart as PNG
			ChartUtilities.saveChartAsPNG(new File(strImgpath), chart, 200, 200);
		} catch (Exception e) {
			appInd.writeLog("Exception", "Exception while executing 'generatepie' method. " + e.getMessage());
		}

	}

	/********************************
	 * Method Name : datedifference Purpose : It will returns the date difference
	 * Author : Mahesh TK Reviewer : Date of Creation : Date of modification :
	 ******************************/
	public String datedifference(String strStartTime, String strEndTime, String defaultstatus) {

		// HH converts hour in 24 hours format (0-23), day calculation
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		String strDatediff = null;
		Date d1 = null;
		Date d2 = null;

		try {
			d1 = format.parse(strStartTime);
			d2 = format.parse(strEndTime);

			// in milliseconds
			long diff = d2.getTime() - d1.getTime();

			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			if (defaultstatus.equalsIgnoreCase("default")) {
				strDatediff = (diffDays + " day,") + (diffHours + " hrs,") + (diffMinutes + " mins,")
						+ (diffSeconds + " secs.");
			} else {
				strDatediff = (diffDays) + "," + (diffHours);
			}

			return strDatediff;
		} catch (Exception e) {
			appInd.writeLog("Exception", "Exception while executing 'datedifference' method. " + e.getMessage());
			return null;
		}
	}

	/********************************
	 * Method Name : clickandclearObject Purpose : This method will first select the
	 * contents and it will replace the provied string Author : Suganthi Reviewer :
	 * Date of Creation : Date of modification : ******************************
	 */
	public boolean clickandclearObject(WebDriver oDriver, String strObjectName, String szString) {
		WebElement oEle = null;
		// WebElement oEle1 = null;
		try {
			oEle = createAndGetObject(oDriver, strObjectName);
			if (oEle.isDisplayed()) {

				highlight(oDriver, oEle);
				Thread.sleep(1000);

				// oEle.click();

				// oEle.clear();
				// Thread.sleep(1000);
				// oEle1 = createAndGetObject(oDriver,"oFailedKPI_Frequency_hrss");
				oEle.sendKeys(Keys.chord(Keys.CONTROL, "a"));
				Thread.sleep(1000);
				oEle.sendKeys(szString);
				// oEle.sendKeys(Keys.DELETE);
				Thread.sleep(1000);
				// appInd.writeLog("Pass","The element '"+strObjectName+"' was clicked
				// successful");
				appInd.writeLog("Pass",
						"The value '" + szString + "' was entered in the element '" + strObjectName + "' successful");
				return true;
			} else {
				appInd.writeLog("Fail", "The element '" + strObjectName + "' was not displayed");
				// appInd.writeLog("Fail","The element '"+strObjectName+"' was not displayed");
				return false;
			}
		} catch (Exception e) {
			appInd.writeLog("Fail", "Exception while executing 'clickObject' method. " + e.getMessage());
			return false;
		}
	}

	/********************************
	 * Method Name : clickandclearObject Purpose : This method will first select the
	 * contents and it will replace the provied string Author : Suganthi Reviewer :
	 * Date of Creation : Date of modification : ******************************
	 */
	public boolean click_and_clearObject(WebDriver oDriver, String strObjectName) {
		WebElement oEle = null;
		// WebElement oEle1 = null;
		try {
			oEle = createAndGetObject(oDriver, strObjectName);
			if (oEle.isDisplayed()) {

				highlight(oDriver, oEle);
				Thread.sleep(1000);

				// oEle.click();

				// oEle.clear();
				// Thread.sleep(1000);
				// oEle1 = createAndGetObject(oDriver,"oFailedKPI_Frequency_hrss");
				oEle.sendKeys(Keys.chord(Keys.CONTROL, "a"));
				Thread.sleep(1000);
				// oEle.sendKeys(szString);
				oEle.sendKeys(Keys.DELETE);
				Thread.sleep(1000);
				appInd.writeLog("Pass", "The element '" + strObjectName + "' was clicked successful");
				// appInd.writeLog("Pass","The value '"+szString+"' was entered in the element
				// '"+strObjectName+"' successful");
				return true;
			} else {
				appInd.writeLog("Fail", "The element '" + strObjectName + "' was not displayed");
				// appInd.writeLog("Fail","The element '"+strObjectName+"' was not displayed");
				return false;
			}
		} catch (Exception e) {
			appInd.writeLog("Fail", "Exception while executing 'clickObject' method. " + e.getMessage());
			return false;
		}
	}

	/********************************
	 * Method Name : click_EnabledObject Purpose : This method will click only on
	 * the Enabled element. Author : Suganthi Reviewer : Date of Creation : Date of
	 * modification : ******************************
	 */
	public boolean click_EnabledObject(WebDriver oDriver, String strObjectName) {
		WebElement oEle = null;
		try {
			oEle = createAndGetObject(oDriver, strObjectName);
			if (oEle.isDisplayed()) {
				highlight(oDriver, oEle);
				oEle.isEnabled();
				if (oEle.isEnabled()) {
					oEle.click();
				} else {
					System.out.println("oEle is disabled:");
				}

				appInd.writeLog("Pass", "The element '" + strObjectName + "' was clicked successful");
				return true;
			} else {
				appInd.writeLog("Fail", "The element '" + strObjectName + "' was not displayed");
				return false;
			}
		} catch (Exception e) {
			appInd.writeLog("Fail", "Exception while executing 'clickObject' method. " + e.getMessage());
			return false;
		}
	}

	public JavascriptExecutor javascriptExecuter() {
		JavascriptExecutor jse = (JavascriptExecutor)oBrowser;
		return jse;

	}
	
	  public static void takeSnapShot(WebDriver oDriver,String fileWithPath) throws Exception{

	        //Convert web driver object to TakeScreenshot

	        TakesScreenshot scrShot =((TakesScreenshot)oDriver);

	        //Call getScreenshotAs method to create image file

	                File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);

	            //Move image file to new destination

	                File DestFile=new File(fileWithPath);

	                //Copy file at destination

	                FileUtils.copyFile(SrcFile, DestFile);

	    }

	  public boolean writecountexcel(Map<Integer, String> outputMap,String strTCID ,String strModulename)
	  {
	  		String strresultvalue = null;
	  		String strvalue=null;
	  		String strTDresult = null;
	          String objarr[] = null;
	          String strstatus=null;
	          String strTCName=null;
	          Map <String,String> otestdataMapo= new HashMap<String,String>();
	          String strTCIDSheet=null;
	          String sheetName=null;
	          String testDataSheetName="TestData";
	          String respectivemodulepath=null;
	          String testdatatype=null;
	          boolean result= false;
	          
	              
	          try {        	
	          	respectivemodulepath 	= System.getProperty("user.dir")+ "\\Results\\Reports\\Excel_Report\\" + strModulename + ".xlsx";
	  		    System.out.println("file read");    
	          	//Reading the Master test data Map 
	          	// Reading all the respective module test scenarios classes and their methods and map it with TestCase id.							
	  			otestdataMapo = datatable.getInputTestData(strrespectivemodulepath, testDataSheetName);
	  			   System.out.println("file read1");  
	  			int pRows = datatable.getRowCount(respectivemodulepath, testDataSheetName);
	  			for (int TD_i = 1; TD_i < pRows; TD_i++) {

	  				// Reading TestCase id from the TestData sheet in Module excel file
	  				strTCIDSheet = datatable.getCellData(respectivemodulepath, testDataSheetName,"TestCaseID", TD_i + 1).trim();

	  				if (strTCIDSheet != null && !strTCIDSheet.trim().isEmpty()) {
	  					 if (strTCID.equalsIgnoreCase(strTCIDSheet)) {
	  						 testdatatype=datatable.getCellData(strrespectivemodulepath,strTestdataSheetName, "TestDataType", TD_i + 1).trim();
	  						 if(testdatatype.equalsIgnoreCase("Output"))
	  						 {
	  					       sheetName = datatable.getCellData(strrespectivemodulepath,strTestdataSheetName, "Param_1", TD_i + 1).trim();
	                              if(sheetName!=null)
	                              {
	                              	for(int i=1;i<=outputMap.size();i++)
	                              	{
	                              		strvalue=outputMap.get(i);
	                              		objarr=strvalue.split("#");
	                              		System.out.println(objarr[0]);
	                              		  
	                              			  datatable.setCellData(respectivemodulepath, sheetName, "SystemName", i+1, objarr[0], "NA","");
	                              			  datatable.setCellData(respectivemodulepath, sheetName, "TotalCount", i+1, objarr[1], "NA","");
	                              			  datatable.setCellData(respectivemodulepath, sheetName, "DisplayedCount", i+1, objarr[2], "NA","");
	                              			  if(objarr[3].equalsIgnoreCase("Pass"))
	                              			  datatable.setCellData(respectivemodulepath, sheetName, "Result", i+1, objarr[3], "Green","");
	                              			  else
	                              				  datatable.setCellData(respectivemodulepath, sheetName, "Result", i+1, objarr[3], "Red","");
	                              	  	}
	                              }
	  				}
	  						 result=true;
	  						 break;
	  			}	
	  						
	  				}				
	          }
	          }
	  		 catch (Exception e) {
	  				appInd.writeLog("Fail", "Exception while executing 'writeResult' method. " + e.getMessage());
	  				result=false;
	  			}
	          finally {
	          	return result;
	          }
	  	
	  }

	   public boolean writeperformancetimeexcel(Map<Integer, String> outputMap,String strTCID ,String strModulename)
	  {
	                  String strresultvalue = null;
	                  String strvalue=null;
	                  String strTDresult = null;
	          String objarr[] = null;
	          String strstatus=null;
	          String strTCName=null;
	          Map <String,String> otestdataMapo= new HashMap<String,String>();
	          String strTCIDSheet=null;
	          String sheetName=null;
	          String testDataSheetName="TestData";
	          String respectivemodulepath=null;
	          String testdatatype=null;
	          boolean result= false;
	          
	              
	          try {                
	                  respectivemodulepath         = System.getProperty("user.dir")+ "\\Results\\Reports\\Excel_Report\\" + strModulename + ".xlsx";
	                      System.out.println("file read");    
	                  //Reading the Master test data Map 
	                  // Reading all the respective module test scenarios classes and their methods and map it with TestCase id.                                                        
	                          otestdataMapo = datatable.getInputTestData(strrespectivemodulepath, testDataSheetName);
	                             System.out.println("file read1");  
	                          int pRows = datatable.getRowCount(respectivemodulepath, testDataSheetName);
	                          for (int TD_i = 1; TD_i < pRows; TD_i++) {

	                                  // Reading TestCase id from the TestData sheet in Module excel file
	                                  strTCIDSheet = datatable.getCellData(respectivemodulepath, testDataSheetName,"TestCaseID", TD_i + 1).trim();

	                                  if (strTCIDSheet != null && !strTCIDSheet.trim().isEmpty()) {
	                                           if (strTCID.equalsIgnoreCase(strTCIDSheet)) {
	                                                   testdatatype=datatable.getCellData(strrespectivemodulepath,strTestdataSheetName, "TestDataType", TD_i + 1).trim();
	                                                   if(testdatatype.equalsIgnoreCase("Output"))
	                                                   {
	                                                 sheetName = datatable.getCellData(strrespectivemodulepath,strTestdataSheetName, "Param_1", TD_i + 1).trim();
	                              if(sheetName!=null)
	                              {
	                                      for(int i=1;i<=outputMap.size();i++)
	                                      {
	                                              strvalue=outputMap.get(i);
	                                              objarr=strvalue.split("#");
	                                                
	                                                        datatable.setCellData(respectivemodulepath, sheetName, "SystemName", i+1, objarr[0], "NA","");
	                                                        datatable.setCellData(respectivemodulepath, sheetName, "Time", i+1, objarr[1], "NA","");
	                                                        datatable.setCellData(respectivemodulepath, sheetName, "RecordCount", i+1, objarr[2], "NA","");
	                                                      if(objarr[3].equalsIgnoreCase("Pass"))
	                                                        datatable.setCellData(respectivemodulepath, sheetName, "Result", i+1, objarr[3], "Green","");
	                                                        else
	                                                                datatable.setCellData(respectivemodulepath, sheetName, "Result", i+1, objarr[3], "Red","");
	                                                }
	                              }
	                                  }
	                                                   result=true;
	                                                   break;
	                          }        
	                                                  
	                                  }                                
	          }
	          }
	                   catch (Exception e) {
	                                  appInd.writeLog("Fail", "Exception while executing 'writeResult' method. " + e.getMessage());
	                                  result=false;
	                          }
	          finally {
	                  return result;
	          }
	          
	  }


	   /********************************
		 * Method Name : writeSavedFilterResult 
		 * Purpose : write Saved Filter Result in output excel
		 * Author : Rajashree
		 * Reviewer : 
		 * Date of Creation :
		 * Date of modification : 
		 * ******************************
		 */
	  public boolean writeSavedFilterResult(Map<Integer, String> outputMap,String strTCID ,String strModulename)
	  {
		  String strresultvalue = null;
		  String strvalue=null;
		  String strTDresult = null;
		  String objarr[] = null;
		  String strstatus=null;
		  String strTCName=null;
		  Map <String,String> otestdataMapo= new HashMap<String,String>();
		  String strTCIDSheet=null;
		  String sheetName=null;
		  String testDataSheetName="TestData";
		  String respectivemodulepath=null;
		  String testdatatype=null;
		  boolean result= false;


		  try {                
			  respectivemodulepath         = System.getProperty("user.dir")+ "\\Results\\Reports\\Excel_Report\\" + strModulename + ".xlsx";
			  System.out.println("file read");    
			  //Reading the Master test data Map 
			  // Reading all the respective module test scenarios classes and their methods and map it with TestCase id.                                                        
			  otestdataMapo = datatable.getInputTestData(strrespectivemodulepath, testDataSheetName);
			  System.out.println("file read1");  
			  int pRows = datatable.getRowCount(respectivemodulepath, testDataSheetName);
			  for (int TD_i = 1; TD_i < pRows; TD_i++) {

				  // Reading TestCase id from the TestData sheet in Module excel file
				  strTCIDSheet = datatable.getCellData(respectivemodulepath, testDataSheetName,"TestCaseID", TD_i + 1).trim();

				  if (strTCIDSheet != null && !strTCIDSheet.trim().isEmpty()) {
					  if (strTCID.equalsIgnoreCase(strTCIDSheet)) {
						  testdatatype=datatable.getCellData(strrespectivemodulepath,strTestdataSheetName, "TestDataType", TD_i + 1).trim();
						  if(testdatatype.equalsIgnoreCase("AnomalyOutput"))
						  {
							  sheetName = datatable.getCellData(strrespectivemodulepath,strTestdataSheetName, "Param_3", TD_i + 1).trim();
							  if(sheetName!=null)
							  {
								  for(int i=1;i<=outputMap.size();i++)
								  {
									  strvalue=outputMap.get(i);
									  objarr=strvalue.split("#");

									  datatable.setCellData(respectivemodulepath, sheetName, "SystemName", i+1, objarr[0], "NA","");
									  datatable.setCellData(respectivemodulepath, sheetName, "SavedFilterName", i+1, objarr[1], "NA","");
									  if(objarr[2].equalsIgnoreCase("Pass"))
										  datatable.setCellData(respectivemodulepath, sheetName, "Result", i+1, objarr[2], "Green","");
									  else
										  datatable.setCellData(respectivemodulepath, sheetName, "Result", i+1, objarr[2], "Red","");
								  }
							  }
						  }
						  result=true;
						  break;
					  }        

				  }                                
			  }
		  }
		  catch (Exception e) {
			  appInd.writeLog("Fail", "Exception while executing 'writeResult' method. " + e.getMessage());
			  result=false;
		  }
		  finally {
			  return result;
		  }

	  }
	  
	  /********************************
		 * Method Name : writeChangeDetectionSavedFilterResult 
		 * Purpose : write Saved Filter Result in output excel
		 * Author : Rajashree
		 * Reviewer : 
		 * Date of Creation :
		 * Date of modification : 
		 * ******************************
		 */
	  public boolean writeChangeDetectionSavedFilterResult(Map<Integer, String> outputMap,String strTCID ,String strModulename)
	  {
		  String strresultvalue = null;
		  String strvalue=null;
		  String strTDresult = null;
		  String objarr[] = null;
		  String strstatus=null;
		  String strTCName=null;
		  Map <String,String> otestdataMapo= new HashMap<String,String>();
		  String strTCIDSheet=null;
		  String sheetName=null;
		  String testDataSheetName="TestData";
		  String respectivemodulepath=null;
		  String testdatatype=null;
		  boolean result= false;


		  try {                
			  respectivemodulepath         = System.getProperty("user.dir")+ "\\Results\\Reports\\Excel_Report\\" + strModulename + ".xlsx";
			  System.out.println("file read");    
			  //Reading the Master test data Map 
			  // Reading all the respective module test scenarios classes and their methods and map it with TestCase id.                                                        
			  otestdataMapo = datatable.getInputTestData(strrespectivemodulepath, testDataSheetName);
			  System.out.println("file read1");  
			  int pRows = datatable.getRowCount(respectivemodulepath, testDataSheetName);
			  for (int TD_i = 1; TD_i < pRows; TD_i++) {

				  // Reading TestCase id from the TestData sheet in Module excel file
				  strTCIDSheet = datatable.getCellData(respectivemodulepath, testDataSheetName,"TestCaseID", TD_i + 1).trim();

				  if (strTCIDSheet != null && !strTCIDSheet.trim().isEmpty()) {
					  if (strTCID.equalsIgnoreCase(strTCIDSheet)) {
						  testdatatype=datatable.getCellData(strrespectivemodulepath,strTestdataSheetName, "TestDataType", TD_i + 1).trim();
						  if(testdatatype.equalsIgnoreCase("CDOutput"))
						  {
							  sheetName = datatable.getCellData(strrespectivemodulepath,strTestdataSheetName, "Param_1", TD_i + 1).trim();
							  if(sheetName!=null)
							  {
								  for(int i=1;i<=outputMap.size();i++)
								  {
									  strvalue=outputMap.get(i);
									  objarr=strvalue.split("#");

									  datatable.setCellData(respectivemodulepath, sheetName, "SystemName", i+1, objarr[0], "NA","");
									  datatable.setCellData(respectivemodulepath, sheetName, "SavedFilterName", i+1, objarr[1], "NA","");
									  if(objarr[2].equalsIgnoreCase("Pass"))
										  datatable.setCellData(respectivemodulepath, sheetName, "Result", i+1, objarr[2], "Green","");
									  else
										  datatable.setCellData(respectivemodulepath, sheetName, "Result", i+1, objarr[2], "Red","");
								  }
							  }
						  }
						  result=true;
						  break;
					  }        

				  }                                
			  }
		  }
		  catch (Exception e) {
			  appInd.writeLog("Fail", "Exception while executing 'writeResult' method. " + e.getMessage());
			  result=false;
		  }
		  finally {
			  return result;
		  }

	  }

	  /********************************
		 * Method Name : clickAndSaveFileIE 
		 * Purpose : save file in IE Browser
		 * Author : Rajashree
		 * Reviewer : 
		 * Date of Creation :
		 * Date of modification : 
		 * ******************************
		 */
	  
	  public void clickAndSaveFileIE(WebElement element) throws InterruptedException{
		  try {
			  Robot robot = new Robot();
			  //get the focus on the element..don't use click since it stalls the driver          
			  element.sendKeys("");
			  //simulate pressing enter            
			  robot.keyPress(KeyEvent.VK_ENTER);
			  robot.keyRelease(KeyEvent.VK_ENTER);
			  //wait for the modal dialog to open            
			  //Thread.sleep(420000);
			  Thread.sleep(180000);
			//Save the file in default location
			  robot.keyPress(KeyEvent.VK_ALT);
			  robot.keyPress(KeyEvent.VK_S);
			  robot.keyRelease(KeyEvent.VK_S); 
			  robot.keyRelease(KeyEvent.VK_ALT);
			  Thread.sleep(10000);
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
	  }
	 	  /********************************
		 * Method Name : getWindowHandles 
		 * Purpose : get all the window Ids
		 * Author : Rajashree
		 * Reviewer : 
		 * Date of Creation :
		 * Date of modification : 
		 * ******************************
		 */
	  public Set<String> getWindowHandles(){
			return oBrowser.getWindowHandles();
		}
	  /********************************
		 * Method Name : switchToWindow 
		 * Purpose : switch to the window based on the index
		 * Author : Rajashree
		 * Reviewer : 
		 * Date of Creation :
		 * Date of modification : 
		 * ******************************
		 */
	  public boolean switchToWindow(int index) {
		  boolean status = false;
		  try {
			LinkedList<String> windowIds = new LinkedList<String>(getWindowHandles());
			  if(index<0 || index>windowIds.size()) {
				  status = false;
				  //throw new IllegalArgumentException("Invalid Index : "+index);
			  }
			  oBrowser.switchTo().window(windowIds.get(index));
			  status = true;
			  Reporter.log("index is : "+index);
			  //System.out.println("index is : "+index);
		} catch (Exception e) {
			status=false;
			Reporter.log("Error in switchToWindow method");
			//System.out.println("Error in switchToWindow method");
			e.printStackTrace();
		}finally {
			return status;
		}
		
	}
	  /********************************
		 * Method Name : switchToParentWindow 
		 * Purpose : switch to the parent window
		 * Author : Rajashree
		 * Reviewer : 
		 * Date of Creation :
		 * Date of modification : 
		 * ******************************
		 */
	public boolean switchToParentWindow() {
		boolean status = false;
		try {
			LinkedList<String> windowIds = new LinkedList<String>(getWindowHandles());
			oBrowser.switchTo().window(windowIds.get(0));
			status=true;
			Reporter.log("switched to the parent window");
			//System.out.println("switched to the parent window");
		} catch (Exception e) {
			status=false;
			Reporter.log("Error in switchToParentWindow method");
			//System.out.println("Error in switchToParentWindow method");
			e.printStackTrace();
		}finally {
			return status;
		}
	}
	/********************************
	 * Method Name : ifElementsPresent 
	 * Purpose : switch to the parent window by closing all child windows
	 * Author : Rajashree
	 * Reviewer : 
	 * Date of Creation :
	 * Date of modification : 
	 * ******************************
	 */
	public boolean switchToParentAndChildClose() {
		boolean status = false;
		try {
			LinkedList<String> windowIds = new LinkedList<String>(getWindowHandles());
			for(int i=1;i<windowIds.size();i++) {
				oBrowser.switchTo().window(windowIds.get(i));
				oBrowser.close();
				status=true;
			}
			boolean flag =switchToParentWindow();
			if(flag==true) {
				status=true;
			}else {
				status=false;
			}
		} catch (Exception e) {
			status = false;
			Reporter.log("Error in switchToParentAndChildClose method");
			//System.out.println("Error in switchToParentAndChildClose method");
			e.printStackTrace();
		}finally {
			return status;
		}
	}
	  
	  public void printFileInChrome() throws InterruptedException{
		  try {
			  Robot robot = new Robot();
			  robot.keyPress(KeyEvent.VK_TAB);
			  robot.keyRelease(KeyEvent.VK_TAB);
			  
			  Thread.sleep(2000);
			  
			  robot.keyPress(KeyEvent.VK_TAB);
			  robot.keyRelease(KeyEvent.VK_TAB);
			  
			  Thread.sleep(2000);
			         
			  robot.keyPress(KeyEvent.VK_ENTER);
			  robot.keyRelease(KeyEvent.VK_ENTER);
			  
			  Thread.sleep(2000);
			  
			  robot.keyPress(KeyEvent.VK_TAB);
			  robot.keyRelease(KeyEvent.VK_TAB);
			  
			  Thread.sleep(2000);
			  
			  robot.keyPress(KeyEvent.VK_TAB);
			  robot.keyRelease(KeyEvent.VK_TAB);
			  
			  Thread.sleep(2000);
			  
			  /*robot.keyPress(KeyEvent.VK_TAB);
			  robot.keyRelease(KeyEvent.VK_TAB);
			  
			  Thread.sleep(2000);*/
			  
			  robot.keyPress(KeyEvent.VK_ENTER);
			  robot.keyRelease(KeyEvent.VK_ENTER);
			  
			  Thread.sleep(2000);
			  
			  robot.keyPress(KeyEvent.VK_TAB);
			  robot.keyRelease(KeyEvent.VK_TAB);
			  
			  Thread.sleep(2000);
			  
			  robot.keyPress(KeyEvent.VK_TAB);
			  robot.keyRelease(KeyEvent.VK_TAB);
			  
			  Thread.sleep(2000);
			  
			  robot.keyPress(KeyEvent.VK_TAB);
			  robot.keyRelease(KeyEvent.VK_TAB);
			  
			  Thread.sleep(2000);
			  
			  robot.keyPress(KeyEvent.VK_TAB);
			  robot.keyRelease(KeyEvent.VK_TAB);
			  
			  Thread.sleep(2000);
			  
			  robot.keyPress(KeyEvent.VK_ENTER);
			  robot.keyRelease(KeyEvent.VK_ENTER);
			  
			  Thread.sleep(2000);
			  
			  robot.keyPress(KeyEvent.VK_ENTER);
			  robot.keyRelease(KeyEvent.VK_ENTER);
			  
			  Thread.sleep(2000);
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
	  }
	  
	  public void printFileInIE_New() throws InterruptedException{
		  try {
			  Robot robot = new Robot();
			  robot.keyPress(KeyEvent.VK_LEFT);
			  robot.keyRelease(KeyEvent.VK_LEFT);
			  
			  Thread.sleep(2000);
			  
			  robot.keyPress(KeyEvent.VK_DOWN);
			  robot.keyRelease(KeyEvent.VK_DOWN);
			  
			  Thread.sleep(2000);
			         
			  robot.keyPress(KeyEvent.VK_DOWN);
			  robot.keyRelease(KeyEvent.VK_DOWN);
			  
			  Thread.sleep(2000);
			  
//			  robot.keyPress(KeyEvent.VK_ENTER);
//			  robot.keyRelease(KeyEvent.VK_ENTER);
//			 	  
//			  Thread.sleep(2000);
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
	  }
	  
	  public void printFileInIE() throws InterruptedException{
		  try {
			  Robot robot = new Robot();
			  
			  robot.keyPress(KeyEvent.VK_ENTER);
			  robot.keyRelease(KeyEvent.VK_ENTER);
			 	  
			  Thread.sleep(2000);
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
	  }
	  
	  
	  /********************************
		 * Method Name : createAndGetObject Purpose : Author : Reviewer : Date of
		 * Creation : Date of modification : ******************************
		 */
		public List<WebElement> createAndGetObjectList(WebDriver oDriver, String strObjectName) {
			List<WebElement> oEle = null;
			String strObjDesc = null;
			try {
				strObjDesc = objMap.get(strObjectName);
				String objArr[] = strObjDesc.split("#");
				switch (objArr[0].toLowerCase()) {
				case "id":
					oEle = oDriver.findElements(By.id(objArr[1]));
					break;
				case "name":
					oEle = oDriver.findElements(By.name(objArr[1]));
					break;
				case "xpath":
					
					if(appInd.waitFor(oBrowser, strObjectName, "elements", null, 25))
					{
						oEle = oDriver.findElements(By.xpath(objArr[1]));
					}
					break;
				case "cssselector":
					oEle = oDriver.findElements(By.cssSelector(objArr[1]));
					break;
				case "linktext":
					oEle = oDriver.findElements(By.linkText(objArr[1]));
					break;
				case "className":
					oEle = oDriver.findElements(By.className(objArr[1]));
					break;
				default:
					appInd.writeLog("Fail", "Invalid locator name '" + objArr[0] + "'");
					return null;
				}
				return oEle;
			} catch (Exception e) {
				appInd.writeLog("Exception", "Exception while executing 'createAndGetObject' method. " + e.getMessage());
				return null;
			}
		}
		
		/********************************
		 * Method Name : ifElementsPresent 
		 * Purpose : check whether list of elements are present or not
		 * Author : Rajashree
		 * Reviewer : 
		 * Date of Creation :
		 * Date of modification : 
		 * ******************************
		 */
		public boolean ifElementsPresent(WebDriver oDriver, String strObjectName) {
			List<WebElement> oEle = null;
			try {
				oEle = createAndGetObjectList(oDriver, strObjectName);
				boolean isPresent = oEle.size()>0;
				if (isPresent==true) {
					appInd.writeLog("Pass","Element is present");
					return true;
				} else {
					appInd.writeLog("Fail", "The element '" + strObjectName + "' was not displayed");
					return false;
				}
			} catch (Exception e) {
				appInd.writeLog("Fail", "Exception while executing 'ifElementsPresent' method. " + e.getMessage());
				return false;
			}
		}
		/********************************
		 * Method Name : isFileExist 
		 * Purpose : check whether file is exist or not
		 * Author : Rajashree
		 * Reviewer : 
		 * Date of Creation :
		 * Date of modification : 
		 * ******************************
		 */
		public boolean isFileExist(WebDriver oDriver, String filepath, String fileName, String filetype ) {
			boolean flag = false;
			try {
				
				File file = new File(filepath+fileName+"."+filetype);

				  if(file.exists()){
					  flag = true;
					  //System.out.println("File is downloaded successfully");
					  appInd.writeLog("pass", "File is downloaded successfully");
				  }else{
					  flag = false;
					 // System.out.println("File is not downloaded successfully");
					  appInd.writeLog("fail", "File is not downloaded successfully");
					  //appInd.takeSnapShot(oDriver, System.getProperty("user.dir")+ "\\Results\\snapshot\\Engineering_Anomaly_Failed_Snapshot\\DownloadFile.png");
				  }
				
			}catch(Exception e) {
				appInd.writeLog("Exception","Exception while executing 'isFileExist' method. " + e.getMessage());
				flag = false;
			}finally {
				return flag;
			}
			
		}
		
		/********************************
		 * Method Name : isElementsPresent 
		 * Purpose : check whether element is present or not
		 * Author : Rajashree
		 * Reviewer : 
		 * Date of Creation :
		 * Date of modification : 
		 * ******************************
		 */

		public boolean isElementsPresent(WebDriver oDriver,String xpath) {
			  List<WebElement> elementList = oDriver.findElements(By.xpath(xpath));
			  boolean isPresent = elementList.size()>0;
			  if(isPresent==true) {
				  return true;
			  }else { 
				  return false;
			  }
			  
		  }
		/********************************
		 * Method Name : fetchTodayDate 
		 * Purpose : fetch today's date with current time
		 * Author : Rajashree
		 * Reviewer : 
		 * Date of Creation :
		 * Date of modification : 
		 * ******************************
		 */
		public String fetchTodayDate() {
			Date date = new Date();
			 SimpleDateFormat dateFormat = new SimpleDateFormat( "MM/dd/yyyy HH:mm:ss" );   
			 Calendar cal = Calendar.getInstance();    
			 cal.setTime( date);    
			 String today = dateFormat.format(cal.getTime());
			return today; 
		}
		/********************************
		 * Method Name : fetchTomorrowDate 
		 * Purpose : fetch Tomorrow's date with current time
		 * Author : Rajashree
		 * Reviewer : 
		 * Date of Creation :
		 * Date of modification : 
		 * ******************************
		 */
		public String fetchTomorrowDate() {
			Date date = new Date();
			 SimpleDateFormat dateFormat = new SimpleDateFormat( "MM/dd/yyyy HH:mm:ss" );   
			 Calendar cal = Calendar.getInstance();    
			 cal.setTime( date);    
			 cal.add( Calendar.DATE, 1 );
			 String tomorrow=dateFormat.format(cal.getTime());
			return tomorrow; 
		}
		/********************************
		 * Method Name : deleteExistFile 
		 * Purpose : Delete all the exist files in "filepath" folder which contains name "fileName" and "filetype"
		 * Author : Rajashee M. Parida
		 * Reviewer : 
		 * Date of Creation :
		 * Date of modification : 
		 * ******************************
		 */
		public boolean deleteExistFile(String filepath, String fileName, String filetype ) {
			boolean flag = false;
			try {
				int count =new File(filepath).listFiles().length;
				File file = new File(filepath+fileName+"."+filetype);
				
				File folder = new File(filepath);
				File[] listOfFiles = folder.listFiles();
								
				for(int i=0;i<count;i++) {
					if(listOfFiles[i].getName().contains(fileName) && listOfFiles[i].getName().contains(filetype)) {
						listOfFiles[i].delete();
						  flag = true;
						  if(flag==true) {
							 // System.out.println("File is deleted successfully");
							  appInd.writeLog("pass", "File is deleted successfully");
						  }else {
							  flag = false;
							 // System.out.println("File is not deleted successfully");
							  appInd.writeLog("fail", "File is not deleted successfully");
						  }
					}else{
						  //flag = false;
						 // System.out.println("File is not exist");
						  appInd.writeLog("fail", "File is not exist");
					  }
					
				}
				

			}catch(Exception e) {
				//e.printStackTrace();
				appInd.writeLog("Exception","Exception while executing 'isFileExist' method. " + e.getMessage());
				//flag = false;
			}finally {
				return flag;
			}
			
		}  
		
		public void waitForElementToBeClick(WebDriver oDriver,WebElement element,long timeout) {
			try {
				WebDriverWait wait = new WebDriverWait(oDriver, timeout);
				wait.until(ExpectedConditions.elementToBeClickable(element));
			} catch (Exception e) {
				e.printStackTrace();
				Reporter.log("Error : Error in waitForElementToBeClick method : "+e);
			}
		}
		
		
		
		
		
		
		/********************************
		 * Method Name : selectItem 
		 * Purpose : This method will select the item in which we enter.
		 * Author : Maruthiraja BN
		 * Reviewer : 
		 * Date of Creation :15-08-2019
		 * Date of modification : 
		 * ******************************
		 * */
		
		public boolean selectItem(WebDriver obrowser,String strObjectName)
		{
			WebElement ole=null;
			boolean Blnstatus=false;
			try
			{
				ole=createAndGetObject(oBrowser, strObjectName);
				ole.sendKeys(Keys.ENTER);
				Blnstatus=true;
				
			}
			catch(Exception e)
			{
				appInd.writeLog("Exception", "Exception while executing the 'selectItem' method "+e.getMessage());
			}
			finally
			{
				return Blnstatus;
			}
			
		}
		
		
		
		
		
	  
    }

	

// close of Utility Class
