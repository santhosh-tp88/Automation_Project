	package org.honeywell.Trace.testcase_scripts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.honeywell.Trace.common_methods.GenricApplicationMethods;
import org.honeywell.Trace.driver.DriverScript;
import org.honeywell.Trace.utility.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Change_Detection {

	// Assign of created IE driver browser object
	WebDriver obrowser = null;
	GenricApplicationMethods oGenericAppmethods = new GenricApplicationMethods();
	DriverScript oDriver = new DriverScript();
	Utility appInd = new Utility();
	String strTCID = oDriver.getTestcaseId();
	String strStatus = null;

	int count;
	long start;
	long end;
	long totalTime;
	String newTotalTime;
	String filterName = "CDFilter";
	String filterDescription = "CDFilterDesc";	


@SuppressWarnings({ "unchecked", "rawtypes" })
public Map<String, HashMap> TC_ChangeDetection_SaveFilter() {
	Map<String, HashMap> oinputMap = new HashMap<String, HashMap>();
	Map<String, String> oinpuTDtMap = new HashMap<String, String>();
	Map<Integer, String> outputMap = new HashMap<Integer, String>();
	double pageLoadTime_Seconds=0;
	double totaltime;
	String strProjectName = null;
	String	strcommonCountvalue=null;
	String systemName = null;
	String strcurrentTD = null;
	try {
		System.out.println("**************Change_Detection***********");
		appInd.writeLog("#", "****TC_ChangeDetection_SaveFilter:- started*****");
		obrowser = oDriver.getWebDriver();
		boolean bolstatus = false;
		String strExecutionsts = null;
		oinputMap = appInd.getInputData(strTCID);
		System.out.println("oinputMap.tostring::" +oinputMap.toString());
		for (int TD = 1; TD <= oinputMap.size(); TD++) {
			oinpuTDtMap = oinputMap.get(String.valueOf(TD));
			if ((obrowser != null)) {
				// Read the Execution Status
				strExecutionsts = oinpuTDtMap.get("Executestatus");
				if (strExecutionsts.equalsIgnoreCase("yes")) {
					// ########################################################################
								// click on menu bar and click on Query option
					
					By byClickOnMenuBar = appInd.createAndGetByObject("Menu_Bar");
					obrowser.findElement(byClickOnMenuBar).sendKeys(Keys.ENTER);
					Thread.sleep(1000);
					obrowser.findElement(By.linkText("Dashboard")).sendKeys(Keys.ENTER);
					Thread.sleep(5000);
					obrowser.findElement(byClickOnMenuBar).sendKeys(Keys.ENTER);
					Thread.sleep(1000);
					obrowser.findElement(By.linkText("Change Detection")).sendKeys(Keys.ENTER);
					Thread.sleep(10000);
					
					// ########################################################################
					  strProjectName  = oinpuTDtMap.get("Param_1"); 
					  System.out.println(strProjectName);
					  String param9= oinpuTDtMap.get("Param_9");
					  System.out.println("param9::" +param9);
					  
					    By select_DropDown = appInd.createAndGetByObject("Change_Detection_Select_System_Dropdown");
						WebElement select_System_DropDown = obrowser.findElement(select_DropDown);
						((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",select_System_DropDown);
						Thread.sleep(2000);
						
						By systems = appInd.createAndGetByObject("Change_Detection_SystemNames");
						List<WebElement> system_elements = obrowser.findElements(systems);
						
						for(int k=0;k<system_elements.size();k++) {
							try {
								//System.out.println("System : "+systemName);
								//system_elements.get(i).click();
								system_elements = obrowser.findElements(systems);
								systemName = system_elements.get(k).getText(); 
								((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",system_elements.get(k));
								Thread.sleep(4000);								
								
								
//								By checkBox_List = appInd.createAndGetByObject("AnomalyName_CheckBox_List");
//								List<WebElement> anomalyName_CheckBox_List = obrowser.findElements(checkBox_List);
//								
//								for(int i=0;i<2;i++) {
//									((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",anomalyName_CheckBox_List.get(i));
//									Thread.sleep(5000);
//								}
								
								
								By savedFilter_image = appInd.createAndGetByObject("ChangeDetection_SavedFilter_image");
								WebElement changeDetection_SavedFilter_image = obrowser.findElement(savedFilter_image);
								((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",changeDetection_SavedFilter_image);
								Thread.sleep(8000);
																
								By savedFilterName_DeleteButtonList = appInd.createAndGetByObject("ChangeDetection_SavedFilterName_DeleteButtonList");
								List<WebElement> changeDetection_SavedFilterName_DeleteButtonList = obrowser.findElements(savedFilterName_DeleteButtonList);
														
								if(changeDetection_SavedFilterName_DeleteButtonList.size()>0) {
									for(int i=0;i<changeDetection_SavedFilterName_DeleteButtonList.size();i++){
										((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",changeDetection_SavedFilterName_DeleteButtonList.get(i));
										System.out.println("Saved filter is deleted");
										Thread.sleep(5000);
									}
									
								}
								
								((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",changeDetection_SavedFilter_image);
								Thread.sleep(5000);
								
								By filter_image = appInd.createAndGetByObject("ChangeDetection_Filter_Image");
								WebElement changeDetection_Filter_Image = obrowser.findElement(filter_image);
								((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",changeDetection_Filter_Image);
								Thread.sleep(5000);
								
								appInd.clearAndSetObject(obrowser, "ChangeDetection_Filter_Name_EditBox", filterName+k);
								
								appInd.clearAndSetObject(obrowser, "ChangeDetection_Filter_Description_EditBox", filterDescription);
								
								By saveFilter_Button = appInd.createAndGetByObject("ChangeDetection_SaveFilter_Button");
								WebElement changeDetection_SaveFilter_Button = obrowser.findElement(saveFilter_Button);
								((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",changeDetection_SaveFilter_Button);
								Thread.sleep(7000);
								
								boolean flag1 = appInd.ifElementsPresent(obrowser, "Popup_Ok_Button");
								
								if(flag1==true) {
									 strStatus = "false";
									 appInd.writeLog("Fail", "Popup is coming");
									 appInd.takeSnapShot(obrowser, System.getProperty("user.dir")+ "\\Results\\snapshot\\ChangeDetection\\SaveFilter.png");
								}else {
									((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",changeDetection_SavedFilter_image);
									Thread.sleep(7000);
									
									By filter_Name_List = appInd.createAndGetByObject("ChangeDetection_SavedFilterName_List");
									List<WebElement> savedFilter_Name_List = obrowser.findElements(filter_Name_List);
									String mapString= "";
									for(int j=0;j<savedFilter_Name_List.size();j++) {
										 mapString= systemName+"#"+filterName+k+"#";
										 System.out.println(savedFilter_Name_List.get(j).getText());
										 System.out.println("=====================");
										
										 //if(savedFilter_Name_List.get(j).getText().trim().equalsIgnoreCase(oinpuTDtMap.get("Param_1"))) {
										 if(savedFilter_Name_List.get(j).getText().contains(filterName.trim())) {
											 strStatus = "true";
											 mapString+="Pass";
											 appInd.writeLog("Pass", "Fiter Saved Successfully");
											 System.out.println("Fiter Saved Successfully");
											 Thread.sleep(2000);
											 System.out.println("strStatus:::"+ strStatus);
											 //outputMap.put(outputMap.size()+1, mapString);  
											 Thread.sleep(5000);
											// ((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",select_System_DropDown);
											 Thread.sleep(5000);
											 break;
										 }
										 
										 strStatus = "false";
				    				     mapString+="Fail";	
				    				     //outputMap.put(outputMap.size()+1, mapString);
				    				     System.out.println("Fiter is not Saved Successfully");
				    				     			    				     			    				    
									}
									 outputMap.put(outputMap.size()+1, mapString);
									((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",select_System_DropDown);
								}
								
								
								
							}catch(Exception e) {
								//e.printStackTrace();
								//break;
								strStatus += false;
								appInd.writeLog("Fail", "Fiter is not Saved Successfully");	
								appInd.takeSnapShot(obrowser, System.getProperty("user.dir")+ "\\Results\\snapshot\\ChangeDetection\\SaveFilter.png");
							}
						}			
					
					// ########################################################################
					if (strStatus.contains("false")) {
						appInd.writeLog("Fail", "'TC_ChangeDetection_SaveFilter' script was failed");
						bolstatus = false;
						strStatus = null;
						appInd.takeSnapShot(obrowser, System.getProperty("user.dir")+ "\\Results\\snapshot\\ChangeDetection\\SaveFilter.png");
					} else if (strStatus.contains("true")) {
						appInd.writeLog("Pass", "'TC_ChangeDetection_SaveFilter' script was Successful");
						bolstatus = true;
						strStatus = null;
					}
				}
			} else {
				appInd.writeLog("Fail", "Failed to launch the IE browser");
				bolstatus = false;
			}
			appInd.writeChangeDetectionSavedFilterResult(outputMap, DriverScript.strTCID, DriverScript.strModulename);
			// write the result into Map
			if ((bolstatus == false) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Fail");
				oinpuTDtMap.put("countvalue", newTotalTime);
			} else if ((bolstatus == false) && !(strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Skip");
				oinpuTDtMap.put("countvalue", newTotalTime);
			} else if ((bolstatus == true) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Pass");
				oinpuTDtMap.put("countvalue", newTotalTime);
			} else {
				oinpuTDtMap.put("result", "Skip");
				oinpuTDtMap.put("countvalue", newTotalTime);
			}
			oinputMap.put(String.valueOf(TD), (HashMap) oinpuTDtMap);
			oinpuTDtMap = null;
		} // for loop
		System.out.println("oinputMap.toString :::::::::::" +oinputMap);
		return oinputMap;
	} catch (Exception e) {
		e.printStackTrace();
		appInd.writeLog("Exception",
				"Exception while executing 'TC_ChangeDetection_SaveFilter' method. " + e.getMessage());
		oinpuTDtMap.put("result", "Fail");
		oinputMap.put("result", (HashMap) oinpuTDtMap);
		return oinputMap;
	}	
	
}
	

/********************************
 * Method Name : TC_CD_Filter_by_Acknowledged
 * Purpose : this will filter the Acknowledged defects in change Detection
 * Author : Mahesh TK 
 * Reviewer : 
 * Date of Creation : 20-Feb-2019 
 * Date of modification :
 * ******************************
 */

	
@SuppressWarnings({ "unchecked", "rawtypes", "finally", "static-access" })
public Map<String, HashMap> TC_CD_Filter_by_Acknowledged() {
	Map<String, HashMap> oinputMap = new HashMap<String, HashMap>();
	Map<String, String> oinpuTDtMap = new HashMap<String, String>();
	String strcurrentTD = null;
	String strcommonCountvalue = null;
	String strcommonTime = null;
	try {
		appInd.writeLog("#", "****TC_CD_Filter_by_Acknowledged:- started*****");
		obrowser = oDriver.getWebDriver();
		boolean bolstatus = false;
		String strExecutionsts = null;
		oinputMap = appInd.getInputData(strTCID);
		for (int TD = 1; TD <= oinputMap.size(); TD++) {
			oinpuTDtMap = oinputMap.get(String.valueOf(TD));

			if ((obrowser != null)) {
				// Read the Execution Status
				strExecutionsts = oinpuTDtMap.get("Executestatus");
				if (strExecutionsts.equalsIgnoreCase("yes")) {
					// ########################################################################	
					
					//click on the Change_Detection from the menu 
					strStatus +=oGenericAppmethods.menu_changeDetection_Navigation(obrowser);
					
					//Select the System 
					if (!(oinpuTDtMap.get("Param_1").equalsIgnoreCase("NA")))strStatus +=appInd.selectDropdownvalue(obrowser, "CD_Parentsystem",oinpuTDtMap.get("Param_1"),"visibletext");
					
					if (!strStatus.contains("false")){
					//Select the SubSystem 
						if (!(oinpuTDtMap.get("Param_2").equalsIgnoreCase("NA")))strStatus +=appInd.selectDropdownvalue(obrowser, "CD_Subsystem",oinpuTDtMap.get("Param_2"),"visibletext");					
						
						if (!strStatus.contains("false")){
							
							//perform operation in the filter popup
							 strStatus +=oGenericAppmethods.changeDetection_filters(obrowser,"acknowledged","none");
							 
							//validation part
							 strStatus += oGenericAppmethods.CD_filtervalidation_countbased(obrowser,"all") ;						
							 
						}else {appInd.writeLog("#","Unable to find SubSystem :"+oinpuTDtMap.get("Param_2")+" in 'TC_CD_Filter_by_Acknowledged' method.");}
						
					}else {appInd.writeLog("#","Unable to find System :"+oinpuTDtMap.get("Param_1")+" in 'TC_CD_Filter_by_Acknowledged' method.");}			
												
					
					
					// ########################################################################
					if (strStatus.contains("false")) {
						String s=oinpuTDtMap.get("Param_1");
						appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
								+ "\\Results\\snapshot\\ChangeDetection\\TC_CD_Filter_by_Acknowledged_Fail_snapshot.png");
						appInd.writeLog("Fail", "'TC_CD_Filter_by_Acknowledged' script was failed");
						System.out.println("'TC_CD_Filter_by_Acknowledged' script was failed");
						bolstatus = false;
						strStatus = null;
					} else if (strStatus.contains("true")) {
						appInd.writeLog("Pass", "'TC_Filter_by_Acknowledged' script was Successful");
						System.out.println("'TC_CD_Filter_by_Acknowledged' script was Successful/Passed");
						bolstatus = true;
						strStatus = null;
					}
				}
			} else {
				appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
						+ "\\Results\\snapshot\\ChangeDetection\\Failed_to_launch_IE_browser_snapshot.png");
				appInd.writeLog("Fail", "Failed to launch the IE browser");
				bolstatus = false;
			}
			// write the result into Map
			if ((bolstatus == false) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Fail");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);

			} else if ((bolstatus == false) && !(strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Skip");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			} else if ((bolstatus == true) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Pass");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			} else {
				oinpuTDtMap.put("result", "Skip");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			}
			strcurrentTD = String.valueOf(TD);
			oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
			oinpuTDtMap = null;
			strcommonCountvalue = null;
		} // for loop
		
		
		
	} catch (Exception e) {
		appInd.writeLog("Exception", "Exception while executing 'TC_CD_Filter_by_Acknowledged' method. " + e.getMessage());
		oinpuTDtMap.put("result", "Fail");
		oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
		return oinputMap;
	} finally 
	{
		System.out.println("TC_CD_Filter_by_Acknowledged-end");
		return oinputMap;
	}
}


/********************************
 * Method Name : TC_CD_Filter_by_Unacknowledged
 * Purpose : this will filter the Unacknowledged defects in change Detection
 * Author : Mahesh TK 
 * Reviewer : 
 * Date of Creation : 20-Feb-2019 
 * Date of modification :
 * ******************************
 */

	
@SuppressWarnings({ "unchecked", "rawtypes", "finally", "static-access" })
public Map<String, HashMap> TC_CD_Filter_by_Unacknowledged() {
	Map<String, HashMap> oinputMap = new HashMap<String, HashMap>();
	Map<String, String> oinpuTDtMap = new HashMap<String, String>();
	String strcurrentTD = null;
	String strcommonCountvalue = null;
	String strcommonTime = null;
	try {
		appInd.writeLog("#", "****TC_CD_Filter_by_Unacknowledged:- started*****");
		obrowser = oDriver.getWebDriver();
		boolean bolstatus = false;
		String strExecutionsts = null;
		oinputMap = appInd.getInputData(strTCID);
		for (int TD = 1; TD <= oinputMap.size(); TD++) {
			oinpuTDtMap = oinputMap.get(String.valueOf(TD));

			if ((obrowser != null)) {
				// Read the Execution Status
				strExecutionsts = oinpuTDtMap.get("Executestatus");
				if (strExecutionsts.equalsIgnoreCase("yes")) {
					// ########################################################################	
					

					//click on the Change_Detection from the menu 
					strStatus +=oGenericAppmethods.menu_changeDetection_Navigation(obrowser);
					
					//Select the System 
					if (!(oinpuTDtMap.get("Param_1").equalsIgnoreCase("NA")))strStatus +=appInd.selectDropdownvalue(obrowser, "CD_Parentsystem",oinpuTDtMap.get("Param_1"),"visibletext");
					
					if (!strStatus.contains("false")){
					//Select the SubSystem 
						if (!(oinpuTDtMap.get("Param_2").equalsIgnoreCase("NA")))strStatus +=appInd.selectDropdownvalue(obrowser, "CD_Subsystem",oinpuTDtMap.get("Param_2"),"visibletext");					
						
						if (!strStatus.contains("false")){
							
							//perform operation in the filter popup
							 strStatus +=oGenericAppmethods.changeDetection_filters(obrowser,"unacknowledged","none");
							 
							//validation part
							 strStatus += oGenericAppmethods.CD_filtervalidation_countbased(obrowser,"unacknowledged") ;						
							 
						}else {appInd.writeLog("#","Unable to find SubSystem :"+oinpuTDtMap.get("Param_2")+" in 'TC_CD_Filter_by_Unacknowledged' method.");}
						
					}else {appInd.writeLog("#","Unable to find System :"+oinpuTDtMap.get("Param_1")+" in 'TC_CD_Filter_by_Unacknowledged' method.");}			
									
					
					
					
					// ########################################################################
					if (strStatus.contains("false")) {
						String s=oinpuTDtMap.get("Param_1");
						appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
								+ "\\Results\\snapshot\\ChangeDetection\\TC_CD_Filter_by_Unacknowledged_Fail_snapshot.png");
						appInd.writeLog("Fail", "'TC_CD_Filter_by_Acknowledged' script was failed");
						System.out.println("'TC_CD_Filter_by_Acknowledged' script was failed");
						bolstatus = false;
						strStatus = null;
					} else if (strStatus.contains("true")) {
						appInd.writeLog("Pass", "'TC_CD_Filter_by_Unacknowledged' script was Successful");
						System.out.println("'TC_CD_Filter_by_Unacknowledged' script was Successful/Passed");
						bolstatus = true;
						strStatus = null;
					}
				}
			} else {
				appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
						+ "\\Results\\snapshot\\ChangeDetection\\Failed_to_launch_IE_browser_snapshot.png");
				appInd.writeLog("Fail", "Failed to launch the IE browser");
				bolstatus = false;
			}
			// write the result into Map
			if ((bolstatus == false) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Fail");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);

			} else if ((bolstatus == false) && !(strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Skip");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			} else if ((bolstatus == true) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Pass");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			} else {
				oinpuTDtMap.put("result", "Skip");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			}
			strcurrentTD = String.valueOf(TD);
			oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
			oinpuTDtMap = null;
			strcommonCountvalue = null;
		} // for loop
		
		
		
	} catch (Exception e) {
		appInd.writeLog("Exception", "Exception while executing 'TC_CD_Filter_by_Unacknowledged' method. " + e.getMessage());
		oinpuTDtMap.put("result", "Fail");
		oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
		return oinputMap;
	} finally 
	{
		System.out.println("TC_CD_Filter_by_Unacknowledged-end");
		return oinputMap;
	}
}
	

/********************************
 * Method Name : TC_CD_Filter_by_Suppressed
 * Purpose : this will filter the Suppressed defects in change Detection
 * Author : Mahesh TK 
 * Reviewer : 
 * Date of Creation : 20-Feb-2019 
 * Date of modification :
 * ******************************
 */

	
@SuppressWarnings({ "unchecked", "rawtypes", "finally", "static-access" })
public Map<String, HashMap> TC_CD_Filter_by_Suppressed() {
	Map<String, HashMap> oinputMap = new HashMap<String, HashMap>();
	Map<String, String> oinpuTDtMap = new HashMap<String, String>();
	String strcurrentTD = null;
	String strcommonCountvalue = null;
	String strcommonTime = null;
	try {
		appInd.writeLog("#", "****TC_CD_Filter_by_Suppressed:- started*****");
		obrowser = oDriver.getWebDriver();
		boolean bolstatus = false;
		String strExecutionsts = null;
		oinputMap = appInd.getInputData(strTCID);
		for (int TD = 1; TD <= oinputMap.size(); TD++) {
			oinpuTDtMap = oinputMap.get(String.valueOf(TD));

			if ((obrowser != null)) {
				// Read the Execution Status
				strExecutionsts = oinpuTDtMap.get("Executestatus");
				if (strExecutionsts.equalsIgnoreCase("yes")) {
					// ########################################################################	
					

					//click on the Change_Detection from the menu 
					strStatus +=oGenericAppmethods.menu_changeDetection_Navigation(obrowser);
					
					//Select the System 
					if (!(oinpuTDtMap.get("Param_1").equalsIgnoreCase("NA")))strStatus +=appInd.selectDropdownvalue(obrowser, "CD_Parentsystem",oinpuTDtMap.get("Param_1"),"visibletext");
					
					if (!strStatus.contains("false")){
					//Select the SubSystem 
						if (!(oinpuTDtMap.get("Param_2").equalsIgnoreCase("NA")))strStatus +=appInd.selectDropdownvalue(obrowser, "CD_Subsystem",oinpuTDtMap.get("Param_2"),"visibletext");					
						
						if (!strStatus.contains("false")){
							
							//perform operation in the filter popup
							 strStatus +=oGenericAppmethods.changeDetection_filters(obrowser,"suppressed","none");
							 
							//validation part
							 strStatus += oGenericAppmethods.CD_filtervalidation_countbased(obrowser,"suppressed") ;	
							
							 
						}else {appInd.writeLog("#","Unable to find SubSystem :"+oinpuTDtMap.get("Param_2")+" in 'TC_CD_Filter_by_Suppressed' method.");}
						
					}else {appInd.writeLog("#","Unable to find System :"+oinpuTDtMap.get("Param_1")+" in 'TC_CD_Filter_by_Suppressed' method.");}			
									
					
									
					
					
					// ########################################################################
					if (strStatus.contains("false")) {
						String s=oinpuTDtMap.get("Param_1");
						appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
								+ "\\Results\\snapshot\\ChangeDetection\\TC_CD_Filter_by_Suppressed_Fail_snapshot.png");
						appInd.writeLog("Fail", "'TC_CD_Filter_by_Suppressed' script was failed");
						System.out.println("'TC_CD_Filter_by_Suppressed' script was failed");
						bolstatus = false;
						strStatus = null;
					} else if (strStatus.contains("true")) {
						appInd.writeLog("Pass", "'TC_CD_Filter_by_Suppressed' script was Successful");
						System.out.println("'TC_CD_Filter_by_Suppressed' script was Successful/Passed");
						bolstatus = true;
						strStatus = null;
					}
				}
			} else {
				appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
						+ "\\Results\\snapshot\\ChangeDetection\\Failed_to_launch_IE_browser_snapshot.png");
				appInd.writeLog("Fail", "Failed to launch the IE browser");
				bolstatus = false;
			}
			// write the result into Map
			if ((bolstatus == false) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Fail");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);

			} else if ((bolstatus == false) && !(strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Skip");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			} else if ((bolstatus == true) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Pass");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			} else {
				oinpuTDtMap.put("result", "Skip");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			}
			strcurrentTD = String.valueOf(TD);
			oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
			oinpuTDtMap = null;
			strcommonCountvalue = null;
		} // for loop
		
		
		
	} catch (Exception e) {
		appInd.writeLog("Exception", "Exception while executing 'TC_CD_Filter_by_Suppressed' method. " + e.getMessage());
		oinpuTDtMap.put("result", "Fail");
		oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
		return oinputMap;
	} finally 
	{
		System.out.println("TC_CD_Filter_by_Suppressed-End");
		return oinputMap;
	}
}


/********************************
 * Method Name : TC_CD_Filter_by_Unsuppressed
 * Purpose : this will filter the Unsuppressed defects in change Detection
 * Author : Mahesh TK 
 * Reviewer : 
 * Date of Creation : 20-Feb-2019 
 * Date of modification :
 * ******************************
 */

	
@SuppressWarnings({ "unchecked", "rawtypes", "finally", "static-access" })
public Map<String, HashMap> TC_CD_Filter_by_Unsuppressed() {
	Map<String, HashMap> oinputMap = new HashMap<String, HashMap>();
	Map<String, String> oinpuTDtMap = new HashMap<String, String>();
	String strcurrentTD = null;
	String strcommonCountvalue = null;
	String strcommonTime = null;
	try {
		appInd.writeLog("#", "****TC_CD_Filter_by_Unsuppressed:- started*****");
		obrowser = oDriver.getWebDriver();
		boolean bolstatus = false;
		String strExecutionsts = null;
		oinputMap = appInd.getInputData(strTCID);
		for (int TD = 1; TD <= oinputMap.size(); TD++) {
			oinpuTDtMap = oinputMap.get(String.valueOf(TD));

			if ((obrowser != null)) {
				// Read the Execution Status
				strExecutionsts = oinpuTDtMap.get("Executestatus");
				if (strExecutionsts.equalsIgnoreCase("yes")) {
					// ########################################################################	
					

					//click on the Change_Detection from the menu 
					strStatus +=oGenericAppmethods.menu_changeDetection_Navigation(obrowser);
					
					//Select the System 
					if (!(oinpuTDtMap.get("Param_1").equalsIgnoreCase("NA")))strStatus +=appInd.selectDropdownvalue(obrowser, "CD_Parentsystem",oinpuTDtMap.get("Param_1"),"visibletext");
					
					if (!strStatus.contains("false")){
					//Select the SubSystem 
						if (!(oinpuTDtMap.get("Param_2").equalsIgnoreCase("NA")))strStatus +=appInd.selectDropdownvalue(obrowser, "CD_Subsystem",oinpuTDtMap.get("Param_2"),"visibletext");					
						
						if (!strStatus.contains("false")){
							
							//perform operation in the filter popup
							 strStatus +=oGenericAppmethods.changeDetection_filters(obrowser,"unsuppressed","none");
							 
							//validation part
							 strStatus += oGenericAppmethods.CD_filtervalidation_countbased(obrowser,"all") ;					
																			
							
							 
						}else {appInd.writeLog("#","Unable to find SubSystem :"+oinpuTDtMap.get("Param_2")+" in 'TC_CD_Filter_by_Unsuppressed' method.");}
						
					}else {appInd.writeLog("#","Unable to find System :"+oinpuTDtMap.get("Param_1")+" in 'TC_CD_Filter_by_Unsuppressed' method.");}			
									
					
					
					// ########################################################################
					if (strStatus.contains("false")) {
						String s=oinpuTDtMap.get("Param_1");
						appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
								+ "\\Results\\snapshot\\ChangeDetection\\TC_CD_Filter_by_Unsuppressed_Fail_snapshot.png");
						appInd.writeLog("Fail", "'TC_CD_Filter_by_Unsuppressed' script was failed");
						System.out.println("'TC_CD_Filter_by_Unsuppressed' script was failed");
						bolstatus = false;
						strStatus = null;
					} else if (strStatus.contains("true")) {
						appInd.writeLog("Pass", "'TC_CD_Filter_by_Unsuppressed' script was Successful");
						System.out.println("'TC_CD_Filter_by_Unsuppressed' script was Successful/Passed");
						bolstatus = true;
						strStatus = null;
					}
				}
			} else {
				appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
						+ "\\Results\\snapshot\\ChangeDetection\\Failed_to_launch_IE_browser_snapshot.png");
				appInd.writeLog("Fail", "Failed to launch the IE browser");
				bolstatus = false;
			}
			// write the result into Map
			if ((bolstatus == false) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Fail");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);

			} else if ((bolstatus == false) && !(strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Skip");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			} else if ((bolstatus == true) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Pass");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			} else {
				oinpuTDtMap.put("result", "Skip");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			}
			strcurrentTD = String.valueOf(TD);
			oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
			oinpuTDtMap = null;
			strcommonCountvalue = null;
		} // for loop
		
		
		
	} catch (Exception e) {
		appInd.writeLog("Exception", "Exception while executing 'TC_CD_Filter_by_Unsuppressed' method. " + e.getMessage());
		oinpuTDtMap.put("result", "Fail");
		oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
		return oinputMap;
	} finally 
	{
		System.out.println("TC_CD_Filter_by_Unsuppressed-end");
		return oinputMap;
	}
}

	

/********************************
 * Method Name : TC_CD_Filter_by_Assigned
 * Purpose : this will filter the Assigned defects in change Detection
 * Author : Mahesh TK 
 * Reviewer : 
 * Date of Creation : 20-Feb-2019 
 * Date of modification :
 * ******************************
 */

	
@SuppressWarnings({ "unchecked", "rawtypes", "finally", "static-access" })
public Map<String, HashMap> TC_CD_Filter_by_Assigned() {
	Map<String, HashMap> oinputMap = new HashMap<String, HashMap>();
	Map<String, String> oinpuTDtMap = new HashMap<String, String>();
	String strcurrentTD = null;
	String strcommonCountvalue = null;
	String strcommonTime = null;
	try {
		appInd.writeLog("#", "****TC_CD_Filter_by_Assigned:- started*****");
		obrowser = oDriver.getWebDriver();
		boolean bolstatus = false;
		String strExecutionsts = null;
		oinputMap = appInd.getInputData(strTCID);
		for (int TD = 1; TD <= oinputMap.size(); TD++) {
			oinpuTDtMap = oinputMap.get(String.valueOf(TD));

			if ((obrowser != null)) {
				// Read the Execution Status
				strExecutionsts = oinpuTDtMap.get("Executestatus");
				if (strExecutionsts.equalsIgnoreCase("yes")) {
					// ########################################################################	
					

					//click on the Change_Detection from the menu 
					strStatus +=oGenericAppmethods.menu_changeDetection_Navigation(obrowser);
					
					//Select the System 
					if (!(oinpuTDtMap.get("Param_1").equalsIgnoreCase("NA")))strStatus +=appInd.selectDropdownvalue(obrowser, "CD_Parentsystem",oinpuTDtMap.get("Param_1"),"visibletext");
					
					if (!strStatus.contains("false")){
					//Select the SubSystem 
						if (!(oinpuTDtMap.get("Param_2").equalsIgnoreCase("NA")))strStatus +=appInd.selectDropdownvalue(obrowser, "CD_Subsystem",oinpuTDtMap.get("Param_2"),"visibletext");					
						
						if (!strStatus.contains("false")){
							
							//perform operation in the filter popup
							 strStatus +=oGenericAppmethods.changeDetection_filters(obrowser,"assigned","none");
							 
							//validation part
							 strStatus += oGenericAppmethods.CD_filtervalidation_countbased(obrowser,"all") ;												
							
							 
						}else {appInd.writeLog("#","Unable to find SubSystem :"+oinpuTDtMap.get("Param_2")+" in 'TC_CD_Filter_by_Assigned' method.");}
						
					}else {appInd.writeLog("#","Unable to find System :"+oinpuTDtMap.get("Param_1")+" in 'TC_CD_Filter_by_Assigned' method.");}			
									
					
									
					
					
					// ########################################################################
					if (strStatus.contains("false")) {
						String s=oinpuTDtMap.get("Param_1");
						appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
								+ "\\Results\\snapshot\\ChangeDetection\\TC_CD_Filter_by_Assigned_Fail_snapshot.png");
						appInd.writeLog("Fail", "'TC_CD_Filter_by_Assigned' script was failed");
						System.out.println("'TC_CD_Filter_by_Assigned' script was failed");
						bolstatus = false;
						strStatus = null;
					} else if (strStatus.contains("true")) {
						appInd.writeLog("Pass", "'TC_CD_Filter_by_Assigned' script was Successful");
						System.out.println("'TC_CD_Filter_by_Assigned' script was Successful/Passed");
						bolstatus = true;
						strStatus = null;
					}
				}
			} else {
				appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
						+ "\\Results\\snapshot\\ChangeDetection\\Failed_to_launch_IE_browser_snapshot.png");
				appInd.writeLog("Fail", "Failed to launch the IE browser");
				bolstatus = false;
			}
			// write the result into Map
			if ((bolstatus == false) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Fail");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);

			} else if ((bolstatus == false) && !(strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Skip");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			} else if ((bolstatus == true) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Pass");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			} else {
				oinpuTDtMap.put("result", "Skip");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			}
			strcurrentTD = String.valueOf(TD);
			oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
			oinpuTDtMap = null;
			strcommonCountvalue = null;
		} // for loop
		
		
		
	} catch (Exception e) {
		appInd.writeLog("Exception", "Exception while executing 'TC_CD_Filter_by_Assigned' method. " + e.getMessage());
		oinpuTDtMap.put("result", "Fail");
		oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
		return oinputMap;
	} finally 
	{
		System.out.println("TC_CD_Filter_by_Assigned-End");
		return oinputMap;
	}
}


/********************************
 * Method Name : TC_CD_Filter_by_Unassigned
 * Purpose : this will filter the Unassigned defects in change Detection
 * Author : Mahesh TK 
 * Reviewer : 
 * Date of Creation : 20-Feb-2019 
 * Date of modification :
 * ******************************
 */

	
@SuppressWarnings({ "unchecked", "rawtypes", "finally", "static-access" })
public Map<String, HashMap> TC_CD_Filter_by_Unassigned() {
	Map<String, HashMap> oinputMap = new HashMap<String, HashMap>();
	Map<String, String> oinpuTDtMap = new HashMap<String, String>();
	String strcurrentTD = null;
	String strcommonCountvalue = null;
	String strcommonTime = null;
	try {
		appInd.writeLog("#", "****TC_CD_Filter_by_Unassigned:- started*****");
		obrowser = oDriver.getWebDriver();
		boolean bolstatus = false;
		String strExecutionsts = null;
		oinputMap = appInd.getInputData(strTCID);
		for (int TD = 1; TD <= oinputMap.size(); TD++) {
			oinpuTDtMap = oinputMap.get(String.valueOf(TD));

			if ((obrowser != null)) {
				// Read the Execution Status
				strExecutionsts = oinpuTDtMap.get("Executestatus");
				if (strExecutionsts.equalsIgnoreCase("yes")) {
					// ########################################################################	
					
					//click on the Change_Detection from the menu 
					strStatus +=oGenericAppmethods.menu_changeDetection_Navigation(obrowser);
					
					//Select the System 
					if (!(oinpuTDtMap.get("Param_1").equalsIgnoreCase("NA")))strStatus +=appInd.selectDropdownvalue(obrowser, "CD_Parentsystem",oinpuTDtMap.get("Param_1"),"visibletext");
					
					if (!strStatus.contains("false")){
					//Select the SubSystem 
						if (!(oinpuTDtMap.get("Param_2").equalsIgnoreCase("NA")))strStatus +=appInd.selectDropdownvalue(obrowser, "CD_Subsystem",oinpuTDtMap.get("Param_2"),"visibletext");					
						
						if (!strStatus.contains("false")){
							
							//perform operation in the filter popup
							 strStatus +=oGenericAppmethods.changeDetection_filters(obrowser,"unassigned","none");
							 
							//validation part
							 strStatus += oGenericAppmethods.CD_filtervalidation_countbased(obrowser,"all") ;											
							
							 
						}else {appInd.writeLog("#","Unable to find SubSystem :"+oinpuTDtMap.get("Param_2")+" in 'TC_CD_Filter_by_Unassigned' method.");}
						
					}else {appInd.writeLog("#","Unable to find System :"+oinpuTDtMap.get("Param_1")+" in 'TC_CD_Filter_by_Unassigned' method.");}			
									
					
					// ########################################################################
					if (strStatus.contains("false")) {
						String s=oinpuTDtMap.get("Param_1");
						appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
								+ "\\Results\\snapshot\\ChangeDetection\\TC_CD_Filter_by_Unassigned_snapshot.png");
						appInd.writeLog("Fail", "'TC_CD_Filter_by_Unassigned' script was failed");
						System.out.println("'TC_CD_Filter_by_Unassigned' script was failed");
						bolstatus = false;
						strStatus = null;
					} else if (strStatus.contains("true")) {
						appInd.writeLog("Pass", "'TC_CD_Filter_by_Unassigned' script was Successful");
						System.out.println("'TC_CD_Filter_by_Unassigned' script was Successful/Passed");
						bolstatus = true;
						strStatus = null;
					}
				}
			} else {
				appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
						+ "\\Results\\snapshot\\ChangeDetection\\Failed_to_launch_IE_browser_snapshot.png");
				appInd.writeLog("Fail", "Failed to launch the IE browser");
				bolstatus = false;
			}
			// write the result into Map
			if ((bolstatus == false) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Fail");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);

			} else if ((bolstatus == false) && !(strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Skip");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			} else if ((bolstatus == true) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Pass");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			} else {
				oinpuTDtMap.put("result", "Skip");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			}
			strcurrentTD = String.valueOf(TD);
			oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
			oinpuTDtMap = null;
			strcommonCountvalue = null;
		} // for loop
		
		
		
	} catch (Exception e) {
		appInd.writeLog("Exception", "Exception while executing 'TC_CD_Filter_by_Unassigned' method. " + e.getMessage());
		oinpuTDtMap.put("result", "Fail");
		oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
		return oinputMap;
	} finally 
	{
		System.out.println("TC_CD_Filter_by_Unassigned-end");
		return oinputMap;
	}
}

	

/********************************
 * Method Name : TC_CD_Filter_by_Added
 * Purpose : this will filter the Added defects in change Detection
 * Author : Mahesh TK 
 * Reviewer : 
 * Date of Creation : 20-Feb-2019 
 * Date of modification :
 * ******************************
 */

	
@SuppressWarnings({ "unchecked", "rawtypes", "finally", "static-access" })
public Map<String, HashMap> TC_CD_Filter_by_Added() {
	Map<String, HashMap> oinputMap = new HashMap<String, HashMap>();
	Map<String, String> oinpuTDtMap = new HashMap<String, String>();
	String strcurrentTD = null;
	String strcommonCountvalue = null;
	String strcommonTime = null;
	try {
		appInd.writeLog("#", "****TC_CD_Filter_by_Added:- started*****");
		obrowser = oDriver.getWebDriver();
		boolean bolstatus = false;
		String strExecutionsts = null;
		oinputMap = appInd.getInputData(strTCID);
		for (int TD = 1; TD <= oinputMap.size(); TD++) {
			oinpuTDtMap = oinputMap.get(String.valueOf(TD));

			if ((obrowser != null)) {
				// Read the Execution Status
				strExecutionsts = oinpuTDtMap.get("Executestatus");
				if (strExecutionsts.equalsIgnoreCase("yes")) {
					// ########################################################################	
					
					//click on the Change_Detection from the menu 
					strStatus +=oGenericAppmethods.menu_changeDetection_Navigation(obrowser);
					
					//Select the System 
					if (!(oinpuTDtMap.get("Param_1").equalsIgnoreCase("NA")))strStatus +=appInd.selectDropdownvalue(obrowser, "CD_Parentsystem",oinpuTDtMap.get("Param_1"),"visibletext");
					
					if (!strStatus.contains("false")){
					//Select the SubSystem 
						if (!(oinpuTDtMap.get("Param_2").equalsIgnoreCase("NA")))strStatus +=appInd.selectDropdownvalue(obrowser, "CD_Subsystem",oinpuTDtMap.get("Param_2"),"visibletext");					
						
						if (!strStatus.contains("false")){
							
							//perform operation in the filter popup
							 strStatus +=oGenericAppmethods.changeDetection_filters(obrowser,"added","none");
							 
							//validation part
							 strStatus += oGenericAppmethods.CD_filtervalidation_countbased(obrowser,"all") ;													
							
							 
						}else {appInd.writeLog("#","Unable to find SubSystem :"+oinpuTDtMap.get("Param_2")+" in 'TC_CD_Filter_by_Added' method.");}
						
					}else {appInd.writeLog("#","Unable to find System :"+oinpuTDtMap.get("Param_1")+" in 'TC_CD_Filter_by_Added' method.");}			
					
				
									
					
					
					// ########################################################################
					if (strStatus.contains("false")) {
						String s=oinpuTDtMap.get("Param_1");
						appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
								+ "\\Results\\snapshot\\ChangeDetection\\TC_CD_Filter_by_Added_Fail_snapshot.png");
						appInd.writeLog("Fail", "'TC_CD_Filter_by_Added' script was failed");
						System.out.println("'TC_CD_Filter_by_Added' script was failed");
						bolstatus = false;
						strStatus = null;
					} else if (strStatus.contains("true")) {
						appInd.writeLog("Pass", "'TC_CD_Filter_by_Added' script was Successful");
						System.out.println("'TC_CD_Filter_by_Added' script was Successful/Passed");
						bolstatus = true;
						strStatus = null;
					}
				}
			} else {
				appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
						+ "\\Results\\snapshot\\ChangeDetection\\Failed_to_launch_IE_browser_snapshot.png");
				appInd.writeLog("Fail", "Failed to launch the IE browser");
				bolstatus = false;
			}
			// write the result into Map
			if ((bolstatus == false) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Fail");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);

			} else if ((bolstatus == false) && !(strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Skip");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			} else if ((bolstatus == true) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Pass");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			} else {
				oinpuTDtMap.put("result", "Skip");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			}
			strcurrentTD = String.valueOf(TD);
			oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
			oinpuTDtMap = null;
			strcommonCountvalue = null;
		} // for loop
		
		
		
	} catch (Exception e) {
		appInd.writeLog("Exception", "Exception while executing 'TC_CD_Filter_by_Added' method. " + e.getMessage());
		oinpuTDtMap.put("result", "Fail");
		oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
		return oinputMap;
	} finally 
	{
		System.out.println("TC_CD_Filter_by_Added-End");
		return oinputMap;
	}
}

	

/********************************
 * Method Name : TC_CD_Filter_by_Modified
 * Purpose : this will filter the Modified defects in change Detection
 * Author : Mahesh TK 
 * Reviewer : 
 * Date of Creation : 20-Feb-2019 
 * Date of modification :
 * ******************************
 */

	
@SuppressWarnings({ "unchecked", "rawtypes", "finally", "static-access" })
public Map<String, HashMap> TC_CD_Filter_by_Modified() {
	Map<String, HashMap> oinputMap = new HashMap<String, HashMap>();
	Map<String, String> oinpuTDtMap = new HashMap<String, String>();
	String strcurrentTD = null;
	String strcommonCountvalue = null;
	String strcommonTime = null;
	try {
		appInd.writeLog("#", "****TC_CD_Filter_by_Modified:- started*****");
		obrowser = oDriver.getWebDriver();
		boolean bolstatus = false;
		String strExecutionsts = null;
		oinputMap = appInd.getInputData(strTCID);
		for (int TD = 1; TD <= oinputMap.size(); TD++) {
			oinpuTDtMap = oinputMap.get(String.valueOf(TD));

			if ((obrowser != null)) {
				// Read the Execution Status
				strExecutionsts = oinpuTDtMap.get("Executestatus");
				if (strExecutionsts.equalsIgnoreCase("yes")) {
					// ########################################################################	
					
					//click on the Change_Detection from the menu 
					strStatus +=oGenericAppmethods.menu_changeDetection_Navigation(obrowser);
					
					//Select the System 
					if (!(oinpuTDtMap.get("Param_1").equalsIgnoreCase("NA")))strStatus +=appInd.selectDropdownvalue(obrowser, "CD_Parentsystem",oinpuTDtMap.get("Param_1"),"visibletext");
					
					if (!strStatus.contains("false")){
					//Select the SubSystem 
						if (!(oinpuTDtMap.get("Param_2").equalsIgnoreCase("NA")))strStatus +=appInd.selectDropdownvalue(obrowser, "CD_Subsystem",oinpuTDtMap.get("Param_2"),"visibletext");					
						
						if (!strStatus.contains("false")){
							
							//perform operation in the filter popup
							 strStatus +=oGenericAppmethods.changeDetection_filters(obrowser,"modified","none");
							 
							//validation part
							 strStatus += oGenericAppmethods.CD_filtervalidation_countbased(obrowser,"all") ;													
							
							 
						}else {appInd.writeLog("#","Unable to find SubSystem :"+oinpuTDtMap.get("Param_2")+" in 'TC_CD_Filter_by_Modified' method.");}
						
					}else {appInd.writeLog("#","Unable to find System :"+oinpuTDtMap.get("Param_1")+" in 'TC_CD_Filter_by_Modified' method.");}			
					
				
									
					
					
					// ########################################################################
					if (strStatus.contains("false")) {
						String s=oinpuTDtMap.get("Param_1");
						appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
								+ "\\Results\\snapshot\\ChangeDetection\\TC_CD_Filter_by_Modified_Fail_snapshot.png");
						appInd.writeLog("Fail", "'TC_CD_Filter_by_Modified' script was failed");
						System.out.println("'TC_CD_Filter_by_Modified' script was failed");
						bolstatus = false;
						strStatus = null;
					} else if (strStatus.contains("true")) {
						appInd.writeLog("Pass", "'TC_CD_Filter_by_Modified' script was Successful");
						System.out.println("'TC_CD_Filter_by_Modified' script was Successful/Passed");
						bolstatus = true;
						strStatus = null;
					}
				}
			} else {
				appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
						+ "\\Results\\snapshot\\ChangeDetection\\Failed_to_launch_IE_browser_snapshot.png");
				appInd.writeLog("Fail", "Failed to launch the IE browser");
				bolstatus = false;
			}
			// write the result into Map
			if ((bolstatus == false) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Fail");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);

			} else if ((bolstatus == false) && !(strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Skip");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			} else if ((bolstatus == true) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Pass");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			} else {
				oinpuTDtMap.put("result", "Skip");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			}
			strcurrentTD = String.valueOf(TD);
			oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
			oinpuTDtMap = null;
			strcommonCountvalue = null;
		} // for loop
		
		
		
	} catch (Exception e) {
		appInd.writeLog("Exception", "Exception while executing 'TC_CD_Filter_by_Modified' method. " + e.getMessage());
		oinpuTDtMap.put("result", "Fail");
		oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
		return oinputMap;
	} finally 
	{
		System.out.println("TC_CD_Filter_by_Modified-end");
		return oinputMap;
	}
}

	

/********************************
 * Method Name : TC_CD_Filter_by_Deleted
 * Purpose : this will filter the Deleted defects in change Detection
 * Author : Mahesh TK 
 * Reviewer : 
 * Date of Creation : 20-Feb-2019 
 * Date of modification :
 * ******************************
 */

	
@SuppressWarnings({ "unchecked", "rawtypes", "finally", "static-access" })
public Map<String, HashMap> TC_CD_Filter_by_Deleted() {
	Map<String, HashMap> oinputMap = new HashMap<String, HashMap>();
	Map<String, String> oinpuTDtMap = new HashMap<String, String>();
	String strcurrentTD = null;
	String strcommonCountvalue = null;
	String strcommonTime = null;
	try {
		appInd.writeLog("#", "****TC_CD_Filter_by_Deleted:- started*****");
		obrowser = oDriver.getWebDriver();
		boolean bolstatus = false;
		String strExecutionsts = null;
		oinputMap = appInd.getInputData(strTCID);
		for (int TD = 1; TD <= oinputMap.size(); TD++) {
			oinpuTDtMap = oinputMap.get(String.valueOf(TD));

			if ((obrowser != null)) {
				// Read the Execution Status
				strExecutionsts = oinpuTDtMap.get("Executestatus");
				if (strExecutionsts.equalsIgnoreCase("yes")) {
					// ########################################################################	
					
					//click on the Change_Detection from the menu 
					strStatus +=oGenericAppmethods.menu_changeDetection_Navigation(obrowser);
					
					//Select the System 
					if (!(oinpuTDtMap.get("Param_1").equalsIgnoreCase("NA")))strStatus +=appInd.selectDropdownvalue(obrowser, "CD_Parentsystem",oinpuTDtMap.get("Param_1"),"visibletext");
					
					if (!strStatus.contains("false")){
					//Select the SubSystem 
						if (!(oinpuTDtMap.get("Param_2").equalsIgnoreCase("NA")))strStatus +=appInd.selectDropdownvalue(obrowser, "CD_Subsystem",oinpuTDtMap.get("Param_2"),"visibletext");					
						
						if (!strStatus.contains("false")){
							
							//perform operation in the filter popup
							 strStatus +=oGenericAppmethods.changeDetection_filters(obrowser,"deleted","none");
							 
							//validation part
							 strStatus += oGenericAppmethods.CD_filtervalidation_countbased(obrowser,"all") ;													
							
							 
						}else {appInd.writeLog("#","Unable to find SubSystem :"+oinpuTDtMap.get("Param_2")+" in 'TC_CD_Filter_by_Deleted' method.");}
						
					}else {appInd.writeLog("#","Unable to find System :"+oinpuTDtMap.get("Param_1")+" in 'TC_CD_Filter_by_Deleted' method.");}			
					
					
									
					
					
					// ########################################################################
					if (strStatus.contains("false")) {
						String s=oinpuTDtMap.get("Param_1");
						appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
								+ "\\Results\\snapshot\\ChangeDetection\\TC_CD_Filter_by_Deleted_Fail_snapshot.png");
						appInd.writeLog("Fail", "'TC_CD_Filter_by_Deleted' script was failed");
						System.out.println("'TC_CD_Filter_by_Deleted' script was failed");
						bolstatus = false;
						strStatus = null;
					} else if (strStatus.contains("true")) {
						appInd.writeLog("Pass", "'TC_CD_Filter_by_Deleted' script was Successful");
						System.out.println("'TC_CD_Filter_by_Deleted' script was Successful/Passed");
						bolstatus = true;
						strStatus = null;
					}
				}
			} else {
				appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
						+ "\\Results\\snapshot\\ChangeDetection\\Failed_to_launch_IE_browser_snapshot.png");
				appInd.writeLog("Fail", "Failed to launch the IE browser");
				bolstatus = false;
			}
			// write the result into Map
			if ((bolstatus == false) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Fail");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);

			} else if ((bolstatus == false) && !(strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Skip");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			} else if ((bolstatus == true) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Pass");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			} else {
				oinpuTDtMap.put("result", "Skip");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			}
			strcurrentTD = String.valueOf(TD);
			oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
			oinpuTDtMap = null;
			strcommonCountvalue = null;
		} // for loop
		
		
		
	} catch (Exception e) {
		appInd.writeLog("Exception", "Exception while executing 'TC_CD_Filter_by_Deleted' method. " + e.getMessage());
		oinpuTDtMap.put("result", "Fail");
		oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
		return oinputMap;
	} finally 
	{
		System.out.println("TC_CD_Filter_by_Deleted-End");
		return oinputMap;
	}
}


/********************************
 * Method Name : TC_CD_Filter_by_Last_3_month
 * Purpose : this will filter the Last_3_month defects in change Detection
 * Author : Mahesh TK 
 * Reviewer : 
 * Date of Creation : 20-Feb-2019 
 * Date of modification :
 * ******************************
 */

	
@SuppressWarnings({ "unchecked", "rawtypes", "finally", "static-access" })
public Map<String, HashMap> TC_CD_Filter_by_Last_3_month() {
	Map<String, HashMap> oinputMap = new HashMap<String, HashMap>();
	Map<String, String> oinpuTDtMap = new HashMap<String, String>();
	String strcurrentTD = null;
	String strcommonCountvalue = null;
	String strcommonTime = null;
	try {
		appInd.writeLog("#", "****TC_CD_Filter_by_Last_3_month:- started*****");
		obrowser = oDriver.getWebDriver();
		boolean bolstatus = false;
		String strExecutionsts = null;
		oinputMap = appInd.getInputData(strTCID);
		for (int TD = 1; TD <= oinputMap.size(); TD++) {
			oinpuTDtMap = oinputMap.get(String.valueOf(TD));

			if ((obrowser != null)) {
				// Read the Execution Status
				strExecutionsts = oinpuTDtMap.get("Executestatus");
				if (strExecutionsts.equalsIgnoreCase("yes")) {
					// ########################################################################	
					
					//click on the Change_Detection from the menu 
					strStatus +=oGenericAppmethods.menu_changeDetection_Navigation(obrowser);
					
					//Select the System 
					if (!(oinpuTDtMap.get("Param_1").equalsIgnoreCase("NA")))strStatus +=appInd.selectDropdownvalue(obrowser, "CD_Parentsystem",oinpuTDtMap.get("Param_1"),"visibletext");
					
					if (!strStatus.contains("false")){
					//Select the SubSystem 
						if (!(oinpuTDtMap.get("Param_2").equalsIgnoreCase("NA")))strStatus +=appInd.selectDropdownvalue(obrowser, "CD_Subsystem",oinpuTDtMap.get("Param_2"),"visibletext");					
						
						if (!strStatus.contains("false")){
							
							//perform operation in the filter popup
							 strStatus +=oGenericAppmethods.changeDetection_filters(obrowser,"last3month","none");
							 
							//validation part
							 strStatus += oGenericAppmethods.CD_filtervalidation_countbased(obrowser,"all") ;												
							
							 
						}else {appInd.writeLog("#","Unable to find SubSystem :"+oinpuTDtMap.get("Param_2")+" in 'TC_CD_Filter_by_Last_3_month' method.");}
						
					}else {appInd.writeLog("#","Unable to find System :"+oinpuTDtMap.get("Param_1")+" in 'TC_CD_Filter_by_Last_3_month' method.");}			
					
					
					
					
					// ########################################################################
					if (strStatus.contains("false")) {
						String s=oinpuTDtMap.get("Param_1");
						appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
								+ "\\Results\\snapshot\\ChangeDetection\\TC_CD_Filter_by_Last_3_month_Fail_snapshot.png");
						appInd.writeLog("Fail", "'TC_CD_Filter_by_Last_3_month' script was failed");
						System.out.println("'TC_CD_Filter_by_Last_3_month' script was failed");
						bolstatus = false;
						strStatus = null;
					} else if (strStatus.contains("true")) {
						appInd.writeLog("Pass", "'TC_CD_Filter_by_Last_3_month' script was Successful");
						System.out.println("'TC_CD_Filter_by_Last_3_month' script was Successful/Passed");
						bolstatus = true;
						strStatus = null;
					}
				}
			} else {
				appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
						+ "\\Results\\snapshot\\ChangeDetection\\Failed_to_launch_IE_browser_snapshot.png");
				appInd.writeLog("Fail", "Failed to launch the IE browser");
				bolstatus = false;
			}
			// write the result into Map
			if ((bolstatus == false) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Fail");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);

			} else if ((bolstatus == false) && !(strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Skip");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			} else if ((bolstatus == true) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Pass");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			} else {
				oinpuTDtMap.put("result", "Skip");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			}
			strcurrentTD = String.valueOf(TD);
			oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
			oinpuTDtMap = null;
			strcommonCountvalue = null;
		} // for loop
		
		
		
	} catch (Exception e) {
		appInd.writeLog("Exception", "Exception while executing 'TC_CD_Filter_by_Last_3_month' method. " + e.getMessage());
		oinpuTDtMap.put("result", "Fail");
		oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
		return oinputMap;
	} finally 
	{
		System.out.println("TC_CD_Filter_by_Last_3_month-end");
		return oinputMap;
	}
}

	

/********************************
 * Method Name : TC_CD_Filter_by_Last_6_month
 * Purpose : this will filter the Last_6_month defects in change Detection
 * Author : Mahesh TK 
 * Reviewer : 
 * Date of Creation : 20-Feb-2019 
 * Date of modification :
 * ******************************
 */

	
@SuppressWarnings({ "unchecked", "rawtypes", "finally", "static-access" })
public Map<String, HashMap> TC_CD_Filter_by_Last_6_month() {
	Map<String, HashMap> oinputMap = new HashMap<String, HashMap>();
	Map<String, String> oinpuTDtMap = new HashMap<String, String>();
	String strcurrentTD = null;
	String strcommonCountvalue = null;
	String strcommonTime = null;
	try {
		appInd.writeLog("#", "****TC_CD_Filter_by_Last_6_month:- started*****");
		obrowser = oDriver.getWebDriver();
		boolean bolstatus = false;
		String strExecutionsts = null;
		oinputMap = appInd.getInputData(strTCID);
		for (int TD = 1; TD <= oinputMap.size(); TD++) {
			oinpuTDtMap = oinputMap.get(String.valueOf(TD));

			if ((obrowser != null)) {
				// Read the Execution Status
				strExecutionsts = oinpuTDtMap.get("Executestatus");
				if (strExecutionsts.equalsIgnoreCase("yes")) {
					// ########################################################################	
					

					//click on the Change_Detection from the menu 
					strStatus +=oGenericAppmethods.menu_changeDetection_Navigation(obrowser);
					
					//Select the System 
					if (!(oinpuTDtMap.get("Param_1").equalsIgnoreCase("NA")))strStatus +=appInd.selectDropdownvalue(obrowser, "CD_Parentsystem",oinpuTDtMap.get("Param_1"),"visibletext");
					
					if (!strStatus.contains("false")){
					//Select the SubSystem 
						if (!(oinpuTDtMap.get("Param_2").equalsIgnoreCase("NA")))strStatus +=appInd.selectDropdownvalue(obrowser, "CD_Subsystem",oinpuTDtMap.get("Param_2"),"visibletext");					
						
						if (!strStatus.contains("false")){
							
							//perform operation in the filter popup
							 strStatus +=oGenericAppmethods.changeDetection_filters(obrowser,"last6month","none");
							 
							//validation part
							 strStatus += oGenericAppmethods.CD_filtervalidation_countbased(obrowser,"all") ;					
							
							 
						}else {appInd.writeLog("#","Unable to find SubSystem :"+oinpuTDtMap.get("Param_2")+" in 'TC_CD_Filter_by_Last_6_month' method.");}
						
					}else {appInd.writeLog("#","Unable to find System :"+oinpuTDtMap.get("Param_1")+" in 'TC_CD_Filter_by_Last_6_month' method.");}			
					
									
					
					
					// ########################################################################
					if (strStatus.contains("false")) {
						String s=oinpuTDtMap.get("Param_1");
						appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
								+ "\\Results\\snapshot\\ChangeDetection\\TC_CD_Filter_by_Last_6_month_Fail_snapshot.png");
						appInd.writeLog("Fail", "'TC_CD_Filter_by_Last_6_month' script was failed");
						System.out.println("'TC_CD_Filter_by_Last_6_month' script was failed");
						bolstatus = false;
						strStatus = null;
					} else if (strStatus.contains("true")) {
						appInd.writeLog("Pass", "'TC_CD_Filter_by_Last_6_month' script was Successful");
						System.out.println("'TC_CD_Filter_by_Last_6_month' script was Successful/Passed");
						bolstatus = true;
						strStatus = null;
					}
				}
			} else {
				appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
						+ "\\Results\\snapshot\\ChangeDetection\\Failed_to_launch_IE_browser_snapshot.png");
				appInd.writeLog("Fail", "Failed to launch the IE browser");
				bolstatus = false;
			}
			// write the result into Map
			if ((bolstatus == false) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Fail");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);

			} else if ((bolstatus == false) && !(strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Skip");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			} else if ((bolstatus == true) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Pass");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			} else {
				oinpuTDtMap.put("result", "Skip");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			}
			strcurrentTD = String.valueOf(TD);
			oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
			oinpuTDtMap = null;
			strcommonCountvalue = null;
		} // for loop
		
		
		
	} catch (Exception e) {
		appInd.writeLog("Exception", "Exception while executing 'TC_CD_Filter_by_Last_6_month' method. " + e.getMessage());
		oinpuTDtMap.put("result", "Fail");
		oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
		return oinputMap;
	} finally 
	{
		System.out.println("TC_CD_Filter_by_Last_6_month-End");
		return oinputMap;
	}
}


/********************************
 * Method Name : TC_CD_Filter_by_Last_1_Year
 * Purpose : this will filter the Last_1_Year defects in change Detection
 * Author : Mahesh TK 
 * Reviewer : 
 * Date of Creation : 20-Feb-2019 
 * Date of modification :
 * ******************************
 */

	
@SuppressWarnings({ "unchecked", "rawtypes", "finally", "static-access" })
public Map<String, HashMap> TC_CD_Filter_by_Last_1_Year() {
	Map<String, HashMap> oinputMap = new HashMap<String, HashMap>();
	Map<String, String> oinpuTDtMap = new HashMap<String, String>();
	String strcurrentTD = null;
	String strcommonCountvalue = null;
	String strcommonTime = null;
	try {
		appInd.writeLog("#", "****TC_CD_Filter_by_Last_1_Year:- started*****");
		obrowser = oDriver.getWebDriver();
		boolean bolstatus = false;
		String strExecutionsts = null;
		oinputMap = appInd.getInputData(strTCID);
		for (int TD = 1; TD <= oinputMap.size(); TD++) {
			oinpuTDtMap = oinputMap.get(String.valueOf(TD));

			if ((obrowser != null)) {
				// Read the Execution Status
				strExecutionsts = oinpuTDtMap.get("Executestatus");
				if (strExecutionsts.equalsIgnoreCase("yes")) {
					// ########################################################################	
					

					//click on the Change_Detection from the menu 
					strStatus +=oGenericAppmethods.menu_changeDetection_Navigation(obrowser);
					
					//Select the System 
					if (!(oinpuTDtMap.get("Param_1").equalsIgnoreCase("NA")))strStatus +=appInd.selectDropdownvalue(obrowser, "CD_Parentsystem",oinpuTDtMap.get("Param_1"),"visibletext");
					
					if (!strStatus.contains("false")){
					//Select the SubSystem 
						if (!(oinpuTDtMap.get("Param_2").equalsIgnoreCase("NA")))strStatus +=appInd.selectDropdownvalue(obrowser, "CD_Subsystem",oinpuTDtMap.get("Param_2"),"visibletext");					
						
						if (!strStatus.contains("false")){
							
							//perform operation in the filter popup
							 strStatus +=oGenericAppmethods.changeDetection_filters(obrowser,"last1year","none");
							 
							//validation part
							 strStatus += oGenericAppmethods.CD_filtervalidation_countbased(obrowser,"all") ;					
							
							 
						}else {appInd.writeLog("#","Unable to find SubSystem :"+oinpuTDtMap.get("Param_2")+" in 'TC_CD_Filter_by_Last_1_Year' method.");}
						
					}else {appInd.writeLog("#","Unable to find System :"+oinpuTDtMap.get("Param_1")+" in 'TC_CD_Filter_by_Last_1_Year' method.");}			
					
				
					
					
					// ########################################################################
					if (strStatus.contains("false")) {
						String s=oinpuTDtMap.get("Param_1");
						appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
								+ "\\Results\\snapshot\\ChangeDetection\\TC_CD_Filter_by_Last_1_Year_Fail_snapshot.png");
						appInd.writeLog("Fail", "'TC_CD_Filter_by_Last_1_Year' script was failed");
						System.out.println("'TC_CD_Filter_by_Last_1_Year' script was failed");
						bolstatus = false;
						strStatus = null;
					} else if (strStatus.contains("true")) {
						appInd.writeLog("Pass", "'TC_CD_Filter_by_Last_1_Year' script was Successful");
						System.out.println("'TC_CD_Filter_by_Last_1_Year' script was Successful/Passed");
						bolstatus = true;
						strStatus = null;
					}
				}
			} else {
				appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
						+ "\\Results\\snapshot\\ChangeDetection\\Failed_to_launch_IE_browser_snapshot.png");
				appInd.writeLog("Fail", "Failed to launch the IE browser");
				bolstatus = false;
			}
			// write the result into Map
			if ((bolstatus == false) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Fail");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);

			} else if ((bolstatus == false) && !(strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Skip");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			} else if ((bolstatus == true) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Pass");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			} else {
				oinpuTDtMap.put("result", "Skip");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			}
			strcurrentTD = String.valueOf(TD);
			oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
			oinpuTDtMap = null;
			strcommonCountvalue = null;
		} // for loop
		
		
		
	} catch (Exception e) {
		appInd.writeLog("Exception", "Exception while executing 'TC_CD_Filter_by_Last_1_Year' method. " + e.getMessage());
		oinpuTDtMap.put("result", "Fail");
		oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
		return oinputMap;
	} finally 
	{
		System.out.println("TC_CD_Filter_by_Last_1_Year-end");
		return oinputMap;
	}
}

	

/********************************
 * Method Name : TC_CD_Filter_by_MultipleSnapshot
 * Purpose : this will filter the MultipleSnapshot defects in change Detection
 * Author : Mahesh TK 
 * Reviewer : 
 * Date of Creation : 20-Feb-2019 
 * Date of modification :
 * ******************************
 */

	
@SuppressWarnings({ "unchecked", "rawtypes", "finally", "static-access" })
public Map<String, HashMap> TC_CD_Filter_by_MultipleSnapshot() {
	Map<String, HashMap> oinputMap = new HashMap<String, HashMap>();
	Map<String, String> oinpuTDtMap = new HashMap<String, String>();
	String strcurrentTD = null;
	String strcommonCountvalue = null;
	String strcommonTime = null;
	try {
		appInd.writeLog("#", "****TC_CD_Filter_by_MultipleSnapshot:- started*****");
		obrowser = oDriver.getWebDriver();
		boolean bolstatus = false;
		String strExecutionsts = null;
		oinputMap = appInd.getInputData(strTCID);
		for (int TD = 1; TD <= oinputMap.size(); TD++) {
			oinpuTDtMap = oinputMap.get(String.valueOf(TD));

			if ((obrowser != null)) {
				// Read the Execution Status
				strExecutionsts = oinpuTDtMap.get("Executestatus");
				if (strExecutionsts.equalsIgnoreCase("yes")) {
					// ########################################################################					
					 
						//click on the Change_Detection from the menu 
						strStatus +=oGenericAppmethods.menu_changeDetection_Navigation(obrowser);
						
						//Select the System 
						if (!(oinpuTDtMap.get("Param_1").equalsIgnoreCase("NA")))strStatus +=appInd.selectDropdownvalue(obrowser, "CD_Parentsystem",oinpuTDtMap.get("Param_1"),"visibletext");
						
						if (!strStatus.contains("false")){
						//Select the SubSystem 
							if (!(oinpuTDtMap.get("Param_2").equalsIgnoreCase("NA")))strStatus +=appInd.selectDropdownvalue(obrowser, "CD_Subsystem",oinpuTDtMap.get("Param_2"),"visibletext");					
							
							if (!strStatus.contains("false")){
								
								//perform operation in the filter popup
								 strStatus +=oGenericAppmethods.changeDetection_filters(obrowser,"multiplesnapshots",oinpuTDtMap.get("Param_3"));
								
								//validation part
								 strStatus += oGenericAppmethods.CD_filtervalidation_countbased(obrowser,"all") ;
								 
							}else {appInd.writeLog("#","Unable to find SubSystem :"+oinpuTDtMap.get("Param_2")+" in 'TC_CD_Filter_by_MultipleSnapshot' method.");}
							
						}else {appInd.writeLog("#","Unable to find System :"+oinpuTDtMap.get("Param_1")+" in 'TC_CD_Filter_by_MultipleSnapshot' method.");}			
						
					
					
					// ########################################################################
					if (strStatus.contains("false")) {
						String s=oinpuTDtMap.get("Param_1");
						appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
								+ "\\Results\\snapshot\\ChangeDetection\\TC_CD_Filter_by_MultipleSnapshot_Fail_snapshot.png");
						appInd.writeLog("Fail", "'TC_CD_Filter_by_MultipleSnapshot' script was failed");
						System.out.println("'TC_CD_Filter_by_MultipleSnapshot' script was failed");
						bolstatus = false;
						strStatus = null;
					} else if (strStatus.contains("true")) {
						appInd.writeLog("Pass", "'TC_CD_Filter_by_MultipleSnapshot' script was Successful");
						System.out.println("'TC_CD_Filter_by_MultipleSnapshot' script was Successful/Passed");
						bolstatus = true;
						strStatus = null;
					}
				}
			} else {
				appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
						+ "\\Results\\snapshot\\ChangeDetection\\Failed_to_launch_IE_browser_snapshot.png");
				appInd.writeLog("Fail", "Failed to launch the IE browser");
				bolstatus = false;
			}
			// write the result into Map
			if ((bolstatus == false) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Fail");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);

			} else if ((bolstatus == false) && !(strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Skip");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			} else if ((bolstatus == true) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Pass");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			} else {
				oinpuTDtMap.put("result", "Skip");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			}
			strcurrentTD = String.valueOf(TD);
			oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
			oinpuTDtMap = null;
			strcommonCountvalue = null;
		} // for loop
		
		
		
	} catch (Exception e) {
		appInd.writeLog("Exception", "Exception while executing 'TC_CD_Filter_by_MultipleSnapshot' method. " + e.getMessage());
		oinpuTDtMap.put("result", "Fail");
		oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
		return oinputMap;
	} finally 
	{
		System.out.println("TC_CD_Filter_by_MultipleSnapshot-End");
		return oinputMap;
	}
}


/********************************
 * Method Name : TC_CD_Filter_by_From
 * Purpose : this will filter the From provided date to end provided date defects in change Detection
 * Author : Mahesh TK 
 * Reviewer : 
 * Date of Creation : 20-Feb-2019 
 * Date of modification :
 * ******************************
 */

	
@SuppressWarnings({ "unchecked", "rawtypes", "finally", "static-access" })
public Map<String, HashMap> TC_CD_Filter_by_From() {
	Map<String, HashMap> oinputMap = new HashMap<String, HashMap>();
	Map<String, String> oinpuTDtMap = new HashMap<String, String>();
	String strcurrentTD = null;
	String strcommonCountvalue = null;
	String strcommonTime = null;
	try {
		appInd.writeLog("#", "****TC_CD_Filter_by_From:- started*****");
		obrowser = oDriver.getWebDriver();
		boolean bolstatus = false;
		String strExecutionsts = null;
		oinputMap = appInd.getInputData(strTCID);
		for (int TD = 1; TD <= oinputMap.size(); TD++) {
			oinpuTDtMap = oinputMap.get(String.valueOf(TD));

			if ((obrowser != null)) {
				// Read the Execution Status
				strExecutionsts = oinpuTDtMap.get("Executestatus");
				if (strExecutionsts.equalsIgnoreCase("yes")) {
					// ########################################################################	
					
					//click on the Change_Detection from the menu 
					strStatus +=oGenericAppmethods.menu_changeDetection_Navigation(obrowser);
					
					//Select the System 
					if (!(oinpuTDtMap.get("Param_1").equalsIgnoreCase("NA")))strStatus +=appInd.selectDropdownvalue(obrowser, "CD_Parentsystem",oinpuTDtMap.get("Param_1"),"visibletext");
					
					if (!strStatus.contains("false")){
					//Select the SubSystem 
						if (!(oinpuTDtMap.get("Param_2").equalsIgnoreCase("NA")))strStatus +=appInd.selectDropdownvalue(obrowser, "CD_Subsystem",oinpuTDtMap.get("Param_2"),"visibletext");					
						
						if (!strStatus.contains("false")){
						//perform operation in the filter popup
						 strStatus +=oGenericAppmethods.changeDetection_filters(obrowser,"from",oinpuTDtMap.get("Param_3"));
						 
						//validation part
						 strStatus += oGenericAppmethods.CD_filtervalidation_countbased(obrowser,"all");
						}else {appInd.writeLog("#","Unable to find SubSystem :"+oinpuTDtMap.get("Param_2")+" in 'TC_CD_Filter_by_From' method.");}
						
					}else {appInd.writeLog("#","Unable to find System :"+oinpuTDtMap.get("Param_1")+" in 'TC_CD_Filter_by_From' method.");}			
					
					
					// ########################################################################
					if (strStatus.contains("false")) {
						String s=oinpuTDtMap.get("Param_1");
						appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
								+ "\\Results\\snapshot\\ChangeDetection\\TC_CD_Filter_by_From_Fail_snapshot.png");
						appInd.writeLog("Fail", "'TC_CD_Filter_by_From' script was failed");
						System.out.println("'TC_CD_Filter_by_From' script was failed");
						bolstatus = false;
						strStatus = null;
					} else if (strStatus.contains("true")) {
						appInd.writeLog("Pass", "'TC_CD_Filter_by_From' script was Successful");
						System.out.println("'TC_CD_Filter_by_From' script was Successful/Passed");
						bolstatus = true;
						strStatus = null;
					}
				}
			} else {
				appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
						+ "\\Results\\snapshot\\ChangeDetection\\Failed_to_launch_IE_browser_snapshot.png");
				appInd.writeLog("Fail", "Failed to launch the IE browser");
				bolstatus = false;
			}
			// write the result into Map
			if ((bolstatus == false) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Fail");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);

			} else if ((bolstatus == false) && !(strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Skip");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			} else if ((bolstatus == true) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Pass");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			} else {
				oinpuTDtMap.put("result", "Skip");
				oinpuTDtMap.put("countvalue", strcommonCountvalue);
			}
			strcurrentTD = String.valueOf(TD);
			oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
			oinpuTDtMap = null;
			strcommonCountvalue = null;
		} // for loop
		
		
		
	} catch (Exception e) {
		appInd.writeLog("Exception", "Exception while executing 'TC_CD_Filter_by_From' method. " + e.getMessage());
		oinpuTDtMap.put("result", "Fail");
		oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
		return oinputMap;
	} finally 
	{
		System.out.println("TC_CD_Filter_by_From-end");
		return oinputMap;
	}
}

/********************************
 * Method Name : TC_CD_Save_filter_Public
 * Purpose : this will save the filter with Description.
 * Author : Maruthiraja BN
 * Reviewer : 
 * Date of Creation : 22-Feb-2019 
 * Date of modification :
 * ******************************
 */

	
@SuppressWarnings({ "unchecked", "rawtypes" })
public Map<String, HashMap> TC_CD_Save_filter_Public() {
	Map<String, HashMap> oinputMap = new HashMap<String, HashMap>();
	Map<String, String> oinpuTDtMap = new HashMap<String, String>();
	String strcurrentTD = null;
	String strcommonCountvalue = null;
	String strcommonTime = null;
	try {
		appInd.writeLog("#", "****TC_CD_Save_filter:- started*****");
		obrowser = oDriver.getWebDriver();
		boolean bolstatus = false;
		String strExecutionsts = null;
		oinputMap = appInd.getInputData(strTCID);
		for (int TD = 1; TD <= oinputMap.size(); TD++) {
			oinpuTDtMap = oinputMap.get(String.valueOf(TD));

			if ((obrowser != null)) {
				// Read the Execution Status
				strExecutionsts = oinpuTDtMap.get("Executestatus");
				if (strExecutionsts.equalsIgnoreCase("yes")) {
					// ########################################################################	
					
					try 
					{
						String strSystemName=oinpuTDtMap.get("Param_1");
						String strSave_Filter = "public";
						String strFilter_Name = oinpuTDtMap.get("Param_2");
						String strDescription = oinpuTDtMap.get("Param_3");
						
						By byClickOnMenuBar = appInd.createAndGetByObject("Menu_Bar");
						obrowser.findElement(byClickOnMenuBar).sendKeys(Keys.ENTER);
						Thread.sleep(1000);
						obrowser.findElement(By.linkText("Dashboard")).sendKeys(Keys.ENTER);
						Thread.sleep(5000);
		
				
						Thread.sleep(2000);
						
						//click on the Change_Detection from the menu 
						strStatus +=oGenericAppmethods.menu_changeDetection_Navigation(obrowser);
						
						//it will click on the DropDown and Select the System
						strStatus +=oGenericAppmethods.change_detection__selectsystem(obrowser,strSystemName) ;
						Thread.sleep(4000);
						
						WebElement filter_icon=appInd.createAndGetObject(obrowser, "Filter_icon_Click");
						
						//click on the filter icon
						((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",filter_icon);  
						Thread.sleep(2000);
						
						//It will set the filter name
						strStatus +=oGenericAppmethods.changeDetectionsavefilter(strSave_Filter,strFilter_Name,strDescription );
						Thread.sleep(2000);
						
						//*************************************************************************************************
						//Login to TraceUser
						if(!(strStatus.contains("false")))
						{	
						strStatus += String.valueOf(
								appInd.clearAndSetObject(obrowser, "Login_txtbx_Username", oinpuTDtMap.get("Param_4")));
						// Set the Password value
						strStatus += String.valueOf(
								appInd.clearAndSetObject(obrowser, "Login_txtbx_Password", oinpuTDtMap.get("Param_5")));
						// click on the ok button
						By byclickOnLogin_btn_OK = appInd.createAndGetByObject("Login_btn_OK");
						WebElement element = obrowser.findElement(byclickOnLogin_btn_OK);
						((JavascriptExecutor) obrowser).executeScript("arguments[0].click();", element);
						Thread.sleep(2000);
						//it will handle the popup
						appInd.clickObject_js(obrowser, "popup_Ok_CD_Button");
						Thread.sleep(15000);
						
						//*************************************************************************************************
		
						//click on the Change_Detection from the menu
						strStatus +=oGenericAppmethods.menu_changeDetection_Navigation(obrowser);
						
						//it will click on the DropDown and Select the System
						strStatus +=oGenericAppmethods.change_detection__selectsystem(obrowser,strSystemName) ;
						
						if(!strStatus.contains("false"))
						{
							//validating the Save filter
							strStatus +=oGenericAppmethods.validation_Saved_Filters(strFilter_Name,strSave_Filter);  	
						}
							
					}
						else
						{
							//it will take the screenShot for defect page
							appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
										+ "\\Results\\snapshot\\ChangeDetection\\FilterNameAsPublic.png"); 
									Thread.sleep(5000);
							appInd.clickObject_js(obrowser, "popup_Ok_CD_Button"); 
						}
					}		
					catch (Exception e) 
					{
						e.printStackTrace();
					}
					
				
					// ########################################################################
					if (strStatus.contains("false")) {
						appInd.writeLog("Fail", "'TC_CD_Save_filter' script was failed");
						System.out.println("'TC_CD_Save_filter' script was failed");
						bolstatus = false;
						strStatus = null;
					} else if (strStatus.contains("true")) {
						appInd.writeLog("Pass", "'TC_CD_Save_filter' script was Successful");
						System.out.println("'TC_CD_Save_filter' script was Successful");
						bolstatus = true;
						strStatus = null;
					}
				}
			} else {
				appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
						+ "\\Results\\snapshot\\ChangeDetection\\Failed_to_launch_IE_browser_snapshot.png");
				appInd.writeLog("Fail", "Failed to launch the IE browser");
				bolstatus = false;
			}
			// write the result into Map
			if ((bolstatus == false) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Fail");
				//oinpuTDtMap.put("countvalue", strcommonCountvalue);

			} else if ((bolstatus == false) && !(strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Skip");
				//oinpuTDtMap.put("countvalue", strcommonCountvalue);
			} else if ((bolstatus == true) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Pass");
				//oinpuTDtMap.put("countvalue", strcommonCountvalue);
			} else {
				oinpuTDtMap.put("result", "Skip");
				//oinpuTDtMap.put("countvalue", strcommonCountvalue);
			}
			strcurrentTD = String.valueOf(TD);
			oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
			oinpuTDtMap = null;
			strcommonCountvalue = null;
		} // for loop
		return oinputMap;
		
		
	} catch (Exception e) {
		appInd.writeLog("Exception", "Exception while executing 'TC_CD_Save_filter' method. " + e.getMessage());
		oinpuTDtMap.put("result", "Fail");
		oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
		return oinputMap;
	} finally {
		System.out.println("end");
	}
}


/********************************
 * Method Name : TC_CD_Save_filter_Private
 * Purpose : this will save the filter with Description.
 * Author : Maruthiraja BN
 * Reviewer : 
 * Date of Creation : 22-Feb-2019 
 * Date of modification :
 * ******************************
 */

@SuppressWarnings({ "unchecked", "rawtypes" })
public Map<String, HashMap> TC_CD_Save_filter_Private() {
	Map<String, HashMap> oinputMap = new HashMap<String, HashMap>();
	Map<String, String> oinpuTDtMap = new HashMap<String, String>();
	String strcurrentTD = null;
	String strcommonCountvalue = null;
	String strcommonTime = null;
	try {
		appInd.writeLog("#", "****TC_CD_Save_filter:- started*****");
		obrowser = oDriver.getWebDriver();
		boolean bolstatus = false;
		String strExecutionsts = null;
		oinputMap = appInd.getInputData(strTCID);
		for (int TD = 1; TD <= oinputMap.size(); TD++) {
			oinpuTDtMap = oinputMap.get(String.valueOf(TD));

			if ((obrowser != null)) {
				// Read the Execution Status
				strExecutionsts = oinpuTDtMap.get("Executestatus");
				if (strExecutionsts.equalsIgnoreCase("yes")) {
					// ########################################################################	
					
					try {
						String strSystemName=oinpuTDtMap.get("Param_1");
						String strSave_Filter = "Private";
						String strFilter_Name = oinpuTDtMap.get("Param_2");
						String strDescription = oinpuTDtMap.get("Param_3");
		
						By byClickOnMenuBar = appInd.createAndGetByObject("Menu_Bar");
						obrowser.findElement(byClickOnMenuBar).sendKeys(Keys.ENTER);
						Thread.sleep(1000);
						obrowser.findElement(By.linkText("Dashboard")).sendKeys(Keys.ENTER);
						Thread.sleep(5000);
						
						//click on the Change_Detection from the menu 
						strStatus +=oGenericAppmethods.menu_changeDetection_Navigation(obrowser); 
						Thread.sleep(2000);
					
						//it will click on the DropDown and Select the System
						strStatus +=oGenericAppmethods.change_detection__selectsystem(obrowser,strSystemName) ;
						Thread.sleep(4000);
						
						WebElement filter_icon=appInd.createAndGetObject(obrowser, "Filter_icon_Click");
						
						//click on the filter icon
						((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",filter_icon);  
						Thread.sleep(2000);
						
						//It will set the filter name
						strStatus += oGenericAppmethods.changeDetectionsavefilter(strSave_Filter,strFilter_Name,strDescription ); 
						Thread.sleep(2000);
						
						//*************************************************************************************************
						//LogIn to TraceUser
						if(!(strStatus.contains("false")))
						{
						strStatus += String.valueOf(
								appInd.clearAndSetObject(obrowser, "Login_txtbx_Username", oinpuTDtMap.get("Param_4")));
						// Set the Password value
						strStatus += String.valueOf(
								appInd.clearAndSetObject(obrowser, "Login_txtbx_Password", oinpuTDtMap.get("Param_5")));
						// click on the ok button
						By byclickOnLogin_btn_OK = appInd.createAndGetByObject("Login_btn_OK");
						WebElement element = obrowser.findElement(byclickOnLogin_btn_OK);
						((JavascriptExecutor) obrowser).executeScript("arguments[0].click();", element);
						Thread.sleep(2000);
						//it will handle the popup
						appInd.clickObject_js(obrowser, "popup_Ok_CD_Button");
						Thread.sleep(15000);
						//*************************************************************************************************
						
						//click on the Change_Detection from the menu 
						strStatus +=oGenericAppmethods.menu_changeDetection_Navigation(obrowser);
						
						//it will click on the DropDown and Select the System
						strStatus +=oGenericAppmethods.change_detection__selectsystem(obrowser,strSystemName) ;
						
					
						if(!strStatus.contains("false"))
						{
							//validating the Save filter
							strStatus +=oGenericAppmethods.validation_Saved_Filters(strFilter_Name,strSave_Filter); 	  	
						}	
						
					}
					else
					{
							appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
										+ "\\Results\\snapshot\\ChangeDetection\\FilterNameAsPrivate.png");
							Thread.sleep(5000);
							
							appInd.clickObject_js(obrowser, "popup_Ok_CD_Button"); 
					}
						
					}
					catch (Exception e) 
					{
						e.printStackTrace();
					}
					
				
					// ########################################################################
					if (strStatus.contains("false")) {
						appInd.writeLog("Fail", "'TC_CD_Save_filter' script was failed");
						System.out.println("'TC_CD_Save_filter' script was failed");
						bolstatus = false;
						strStatus = null;
					} else if (strStatus.contains("true")) {
						appInd.writeLog("Pass", "'TC_CD_Save_filter' script was Successful");
						System.out.println("'TC_CD_Save_filter' script was Successful");
						bolstatus = true;
						strStatus = null;
					}
				}
			} else {
				appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
						+ "\\Results\\snapshot\\ChangeDetection\\Failed_to_launch_IE_browser_snapshot.png");
				appInd.writeLog("Fail", "Failed to launch the IE browser");
				bolstatus = false;
			}
			// write the result into Map
			if ((bolstatus == false) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Fail");
				//oinpuTDtMap.put("countvalue", strcommonCountvalue);

			} else if ((bolstatus == false) && !(strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Skip");
				//oinpuTDtMap.put("countvalue", strcommonCountvalue);
			} else if ((bolstatus == true) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Pass");
				//oinpuTDtMap.put("countvalue", strcommonCountvalue);
			} else {
				oinpuTDtMap.put("result", "Skip");
				//oinpuTDtMap.put("countvalue", strcommonCountvalue);
			}
			strcurrentTD = String.valueOf(TD);
			oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
			oinpuTDtMap = null;
			strcommonCountvalue = null;
		} // for loop
		return oinputMap;
		
		
	} catch (Exception e) {
		appInd.writeLog("Exception", "Exception while executing 'TC_CD_Save_filter' method. " + e.getMessage());
		oinpuTDtMap.put("result", "Fail");
		oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
		return oinputMap;
	} finally {
		System.out.println("end");
	}
}
//*********Actions********//

/********************************
 * Method Name : TC_CD_ActionAcknowledge
 * Purpose : this will filter the Acknowledged defects in change Detection
 * Author : Mahesh TK 
 * Reviewer : 
 * Date of Creation : 20-feb-2019 
 * Date ofmodification :
 * ******************************
 */

	
@SuppressWarnings({ "unchecked", "rawtypes" })
public Map<String, HashMap> TC_CD_ActionAcknowledge() {
	Map<String, HashMap> oinputMap = new HashMap<String, HashMap>();
	Map<String, String> oinpuTDtMap = new HashMap<String, String>();
	String strcurrentTD = null;
	String strcommonCountvalue = null;
	String strcommonTime = null;
	try {
		appInd.writeLog("#", "****TC_CD_ActionAcknowledge:- started*****");
		obrowser = oDriver.getWebDriver();
		boolean bolstatus = false;
		String strExecutionsts = null;
		oinputMap = appInd.getInputData(strTCID);
		for (int TD = 1; TD <= oinputMap.size(); TD++) {
			oinpuTDtMap = oinputMap.get(String.valueOf(TD));

			if ((obrowser != null)) {
				// Read the Execution Status
				strExecutionsts = oinpuTDtMap.get("Executestatus");
				if (strExecutionsts.equalsIgnoreCase("yes")) {
					// ########################################################################	
					
					//click on the Engineering Anomaly from the menu 
					strStatus +=oGenericAppmethods.menu_changeDetection_Navigation(obrowser);			
					
					//Select the System from  "
				
					if (oGenericAppmethods.change_detection__selectsystem(obrowser,oinpuTDtMap.get("Param_1")))
					{
					
					
					//select filter unacknowledge
					oGenericAppmethods.change_detection__filters(obrowser,"unacknowledged") ;
					
					//perform operation 
					strStatus+= oGenericAppmethods.Change_Detection_Actions(obrowser,"acknowledge","","") ;
					
					
					
				appInd.waitFor(obrowser, "ajax_loading", "invisible", "", 600);	
				int count=0;
			
				//Validation
				try {
				for (int i=1 ;i<=2;i++) {
				String text=	oGenericAppmethods.MainGrid_Operation_CD(obrowser,"get_innertext",i ,9, "" );
					System.out.println(text);		
										
					if(text.equalsIgnoreCase("acknowledge")) {
						count++;
						System.out.println(count);
						
					}
					else if(text==null)	
						break;
					Thread.sleep(5000);
				}
				}
				catch(Exception e)
				{
					
				}
				if(count>=2)
				{
					strStatus = "true";
				System.out.println("pass");
				}
				else
				{
					strStatus = "false";
					System.out.println("Fail");
					
					
				}
				//==========================
					
					//get the counts
			//		strcommonCountvalue=oGenericAppmethods.strcount;
					
					// ########################################################################
					if (strStatus.contains("false")) {
						String s=oinpuTDtMap.get("Param_1");
						appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
								+ "\\Results\\snapshot\\ChangeDetection\\TC_CD_ActionAcknowledge_Fail_snapshot.png");
						appInd.writeLog("Fail", "'TC_CD_ActionAcknowledge' script was failed");
						bolstatus = false;
						strStatus = null;
					} else if (strStatus.contains("true")) {
						appInd.writeLog("Pass", "'TC_CD_ActionAcknowledge' script was Successful");
						bolstatus = true;
						strStatus = null;
					}
			}
				}
			} else {
				appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
						+ "\\Results\\snapshot\\ChangeDetection\\Failed_to_launch_IE_browser_snapshot.png");
				appInd.writeLog("Fail", "Failed to launch the IE browser");
				bolstatus = false;
			}
			
			// write the result into Map
			if ((bolstatus == false) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Fail");
			

			} else if ((bolstatus == false) && !(strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Skip");
				
			} else if ((bolstatus == true) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Pass");
			
			} else {
				oinpuTDtMap.put("result", "Skip");
				
			}
			strcurrentTD = String.valueOf(TD);
			oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
			oinpuTDtMap = null;
			strcommonCountvalue = null;
		} // for loop
		return oinputMap;
		
		
	} catch (Exception e) {
		appInd.writeLog("Exception", "Exception while executing 'TC_CD_ActionAcknowledge' method. " + e.getMessage());
		oinpuTDtMap.put("result", "Fail");
		oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
		return oinputMap;
	} finally {
		System.out.println("end");
	}
}


/********************************
 * Method Name : TC_CD_Deletefilter
 * Purpose : this will filter the Acknowledged defects in change Detection
 * Author : Mahesh TK 
 * Reviewer : 
 * Date of Creation : 20-feb-2019 
 * Date ofmodification :
 * ******************************
 */

	
@SuppressWarnings({ "unchecked", "rawtypes" })
public Map<String, HashMap> TC_CD_Deletefilter() {
	Map<String, HashMap> oinputMap = new HashMap<String, HashMap>();
	Map<String, String> oinpuTDtMap = new HashMap<String, String>();
	String strcurrentTD = null;
	String strcommonCountvalue = null;
	String strcommonTime = null;
	try {
		appInd.writeLog("#", "****TC_CD_Deletefilter:- started*****");
		obrowser = oDriver.getWebDriver();
		boolean bolstatus = false;
		String strExecutionsts = null;
		oinputMap = appInd.getInputData(strTCID);
		for (int TD = 1; TD <= oinputMap.size(); TD++) {
			oinpuTDtMap = oinputMap.get(String.valueOf(TD));

			if ((obrowser != null)) {
				// Read the Execution Status
				strExecutionsts = oinpuTDtMap.get("Executestatus");
				if (strExecutionsts.equalsIgnoreCase("yes")) {
					// ########################################################################	
					By byClickOnMenuBar = appInd.createAndGetByObject("Menu_Bar");
					obrowser.findElement(byClickOnMenuBar).sendKeys(Keys.ENTER);
					Thread.sleep(1000);
					obrowser.findElement(By.linkText("Dashboard")).sendKeys(Keys.ENTER);
					Thread.sleep(5000);
					//click on the Engineering Anomaly from the menu 
					strStatus +=oGenericAppmethods.menu_changeDetection_Navigation(obrowser);			
					
					//Select the System from  "
					if (oGenericAppmethods.change_detection__selectsystem(obrowser,oinpuTDtMap.get("Param_1")))
					{
					
					try {
						//Filter based on the Acknowledged action
						By byCD_SavedFilterList = appInd.createAndGetByObject("CD_SavedFilterList");
						Thread.sleep(3000);
						WebElement element9 = obrowser.findElement(byCD_SavedFilterList);
						((JavascriptExecutor) obrowser).executeScript("arguments[0]. click();", element9);
						
						if(appInd.isElementPresent(obrowser, "CD_savefilterelement"))
						{
							
							
					    By byCD_Clickdelete = appInd.createAndGetByObject("CD_Clickdelete");
						Thread.sleep(3000);
						WebElement element4 = obrowser.findElement(byCD_Clickdelete);
						((JavascriptExecutor) obrowser).executeScript("arguments[0]. click();", element4);
						Thread.sleep(5000);
						
						
						//#######################################################################################
						
						}
						else {
							strStatus += false;
							Thread.sleep(3000);
							appInd.writeLog("Fail", "Fiter is not deleted");	
							//appInd.takeSnapShot(obrowser, System.getProperty("user.dir"));
							appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
									+ "\\Results\\snapshot\\ChangeDetection\\TC_CD_Deletefilter.png");
						}
						}catch(Exception e) {
							
						
						}

					// ########################################################################
					if (strStatus.contains("false")) {
						String s=oinpuTDtMap.get("Param_1");
						appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
								+ "\\Results\\snapshot\\ChangeDetection\\TC_CD_Deletefilter_Fail_snapshot.png");
						appInd.writeLog("Fail", "'TC_CD_Deletefilter' script was failed");
						bolstatus = false;
						strStatus = null;
					} else if (strStatus.contains("true")) {
						appInd.writeLog("Pass", "'TC_CD_Deletefilter' script was Successful");
						bolstatus = true;
						strStatus = null;
					}
				}
				}
			} else {
				appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
						+ "\\Results\\snapshot\\ChangeDetection\\Failed_to_launch_IE_browser_snapshot.png");
				appInd.writeLog("Fail", "Failed to launch the IE browser");
				bolstatus = false;
			}
			
			// write the result into Map
			if ((bolstatus == false) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Fail");
			

			} else if ((bolstatus == false) && !(strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Skip");
				
			} else if ((bolstatus == true) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Pass");
			
			} else {
				oinpuTDtMap.put("result", "Skip");
				
			}
			strcurrentTD = String.valueOf(TD);
			oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
			oinpuTDtMap = null;
			strcommonCountvalue = null;
		} // for loop
		return oinputMap;
		
		
	} catch (Exception e) {
		appInd.writeLog("Exception", "Exception while executing 'TC_CD_Deletefilter' method. " + e.getMessage());
		oinpuTDtMap.put("result", "Fail");
		oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
		return oinputMap;
	} finally {
		System.out.println("end");
	}
}


/********************************
 * Method Name : TC_CD_Editfilter
 * Purpose : this will filter the Acknowledged defects in change Detection
 * Author : Mahesh TK 
 * Reviewer : 
 * Date of Creation : 20-feb-2019 
 * Date ofmodification :
 * ******************************
 */

	
@SuppressWarnings({ "unchecked", "rawtypes" })
public Map<String, HashMap> TC_CD_Editfilter() {
	Map<String, HashMap> oinputMap = new HashMap<String, HashMap>();
	Map<String, String> oinpuTDtMap = new HashMap<String, String>();
	String strcurrentTD = null;
	String strcommonCountvalue = null;
	String strcommonTime = null;
	try {
		appInd.writeLog("#", "****TC_CD_Editfilter:- started*****");
		obrowser = oDriver.getWebDriver();
		boolean bolstatus = false;
		String strExecutionsts = null;
		oinputMap = appInd.getInputData(strTCID);
		for (int TD = 1; TD <= oinputMap.size(); TD++) {
			oinpuTDtMap = oinputMap.get(String.valueOf(TD));

			if ((obrowser != null)) {
				// Read the Execution Status
				strExecutionsts = oinpuTDtMap.get("Executestatus");
				if (strExecutionsts.equalsIgnoreCase("yes")) {
					// ########################################################################	
					By byClickOnMenuBar = appInd.createAndGetByObject("Menu_Bar");
					obrowser.findElement(byClickOnMenuBar).sendKeys(Keys.ENTER);
					Thread.sleep(1000);
					obrowser.findElement(By.linkText("Dashboard")).sendKeys(Keys.ENTER);
					Thread.sleep(5000);
					//click on the Engineering Anomaly from the menu 
					strStatus +=oGenericAppmethods.menu_changeDetection_Navigation(obrowser);
					//Select the System from  "
					if (oGenericAppmethods.change_detection__selectsystem(obrowser,oinpuTDtMap.get("Param_1")))
					{
					try {
						//Filter based on the Acknowledged action
						By byCD_SavedFilterList = appInd.createAndGetByObject("CD_SavedFilterList");
						Thread.sleep(3000);
						WebElement element9 = obrowser.findElement(byCD_SavedFilterList);
						((JavascriptExecutor) obrowser).executeScript("arguments[0]. click();", element9);
						
						//Edit filter
						if(appInd.isElementPresent(obrowser, "CD_savefilterelement"))
						{
							//Click on Edit
							  By byCD_Edit = appInd.createAndGetByObject("CD_Edit");
								Thread.sleep(3000);
								WebElement element4 = obrowser.findElement(byCD_Edit);
								((JavascriptExecutor) obrowser).executeScript("arguments[0]. click();", element4);
								Thread.sleep(5000);
								
								obrowser.findElement(By.xpath("//*[@id=\"fromFilter\"]/div[1]/div[2]/div[3]/input")).clear();
							
								
								//Enter Description
								  By byClickDescription = appInd.createAndGetByObject("ClickDescription");
									Thread.sleep(3000);
									WebElement element = obrowser.findElement(byClickDescription);
									((JavascriptExecutor) obrowser).executeScript("arguments[0]. click();", element);
									Thread.sleep(5000);
								
								appInd.setObject(obrowser, "ClickDescription", (oinpuTDtMap.get("Param_2")));
								
								//Click Save filter
								By byCD_Savefilter = appInd.createAndGetByObject("CD_Savefilter");
								Thread.sleep(3000);
								WebElement element1 = obrowser.findElement(byCD_Savefilter);
								((JavascriptExecutor) obrowser).executeScript("arguments[0]. click();", element1);
								Thread.sleep(5000);
								
								//Saved filter list
								 byCD_SavedFilterList = appInd.createAndGetByObject("CD_SavedFilterList");
								Thread.sleep(3000);
								 element9 = obrowser.findElement(byCD_SavedFilterList);
								((JavascriptExecutor) obrowser).executeScript("arguments[0]. click();", element9);
						
								 
								
								
						//Check the saved filter list
						By byCD_ValidateEdit = appInd.createAndGetByObject("CD_ValidateEdit");
						Thread.sleep(2000);
						List<WebElement> CD_ValidateEdit = obrowser.findElements(byCD_ValidateEdit);
						for(int j=0;j<CD_ValidateEdit.size();j++) {
							if(CD_ValidateEdit.get(j).getText().contains(oinpuTDtMap.get("Param_2"))) {
								strStatus = "true";
								 System.out.println("strStatus:::"+ strStatus);
								 break;
								 
								 
							 }
							strStatus = "false";
						}	
						}
						
						else {
							strStatus = "false";
						}
						if(strStatus.contains("false")) {
							strStatus = "false";
						}
						else {
							strStatus = "true";

						}
						}catch(Exception e) {
							e.printStackTrace();
							strStatus += false;
							appInd.writeLog("Fail", "Fiter is not Saved Successfully");	
							//appInd.takeSnapShot(obrowser, System.getProperty("user.dir"));
							appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
									+ "\\Results\\snapshot\\Engineering_Anomaly\\TC_Filter_Edit.png");
							
					
						
						}
					
					
						
					// ########################################################################
					if (strStatus.contains("false")) {
						String s=oinpuTDtMap.get("Param_1");
						appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
								+ "\\Results\\snapshot\\ChangeDetection\\TC_CD_Editfilter_Fail_snapshot.png");
						appInd.writeLog("Fail", "'TC_CD_Editfilter' script was failed");
						bolstatus = false;
						strStatus = null;
					} else if (strStatus.contains("true")) {
						appInd.writeLog("Pass", "'TC_CD_Editfilter' script was Successful");
						bolstatus = true;
						strStatus = null;
					}
				}
				}
			} else {
				appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
						+ "\\Results\\snapshot\\ChangeDetection\\Failed_to_launch_IE_browser_snapshot.png");
				appInd.writeLog("Fail", "Failed to launch the IE browser");
				bolstatus = false;
			}
			
			// write the result into Map
			if ((bolstatus == false) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Fail");
			

			} else if ((bolstatus == false) && !(strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Skip");
				
			} else if ((bolstatus == true) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Pass");
			
			} else {
				oinpuTDtMap.put("result", "Skip");
				
			}
			strcurrentTD = String.valueOf(TD);
			oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
			oinpuTDtMap = null;
			strcommonCountvalue = null;
		} // for loop
		return oinputMap;
		
		
	} catch (Exception e) {
		appInd.writeLog("Exception", "Exception while executing 'TC_CD_Editfilter' method. " + e.getMessage());
		oinpuTDtMap.put("result", "Fail");
		oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
		return oinputMap;
	} finally {
		System.out.println("end");
	}
}
/********************************
 * Method Name : TC_CD_ActionSuppress
 * Purpose : this will filter the Acknowledged defects in change Detection
 * Author : Mahesh TK 
 * Reviewer : 
 * Date of Creation : 20-feb-2019 
 * Date ofmodification :
 * ******************************
 */

	
@SuppressWarnings({ "unchecked", "rawtypes" })
public Map<String, HashMap> TC_CD_ActionSuppress() {
	Map<String, HashMap> oinputMap = new HashMap<String, HashMap>();
	Map<String, String> oinpuTDtMap = new HashMap<String, String>();
	String strcurrentTD = null;
	String strcommonCountvalue = null;
	String strcommonTime = null;
	try {
		appInd.writeLog("#", "****TC_CD_ActionSuppress:- started*****");
		obrowser = oDriver.getWebDriver();
		boolean bolstatus = false;
		String strExecutionsts = null;
		oinputMap = appInd.getInputData(strTCID);
		for (int TD = 1; TD <= oinputMap.size(); TD++) {
			oinpuTDtMap = oinputMap.get(String.valueOf(TD));

			if ((obrowser != null)) {
				// Read the Execution Status
				strExecutionsts = oinpuTDtMap.get("Executestatus");
				if (strExecutionsts.equalsIgnoreCase("yes")) {
					// ########################################################################	
					By byClickOnMenuBar = appInd.createAndGetByObject("Menu_Bar");
					obrowser.findElement(byClickOnMenuBar).sendKeys(Keys.ENTER);
					Thread.sleep(1000);
					obrowser.findElement(By.linkText("Dashboard")).sendKeys(Keys.ENTER);
					Thread.sleep(5000);
					//click on the Engineering Anomaly from the menu 
					strStatus +=oGenericAppmethods.menu_changeDetection_Navigation(obrowser);
					
					String todayDate = appInd.fetchTodayDate();
					String[] dateArray = todayDate.split(" ");
					System.out.println(dateArray[0]);
					System.out.println(dateArray[1]);
					
					
					if (oGenericAppmethods.change_detection__selectsystem(obrowser,oinpuTDtMap.get("Param_1")))
					{
						strStatus+= oGenericAppmethods.Change_Detection_Actions(obrowser,"suppress", dateArray[0],oinpuTDtMap.get("Param_2"));
						
						Thread.sleep(3000);
						//Handling Popup Okay button 
						try {
							
							By CD_Abortcollectionconfirm = appInd
							.createAndGetByObject("CD_Abortcollectionconfirm");
							WebElement elementbyCD_Abortcollectionconfirm = obrowser
							.findElement(CD_Abortcollectionconfirm);

							if (elementbyCD_Abortcollectionconfirm != null) {
							strStatus += "false";
							appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
									+ "\\Results\\snapshot\\ChangeDetection\\TC_CD_ActionSuppress_Fail_systemalreadyExistsnapshot.png");
							Thread.sleep(3000);
							((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",
									elementbyCD_Abortcollectionconfirm);
							
							By byCD_SuppressionClose = appInd.createAndGetByObject("CD_SuppressionClose");
			                   WebElement element = obrowser.findElement(byCD_SuppressionClose);
								((JavascriptExecutor) obrowser).executeScript("arguments[0].click();", element);
								Thread.sleep(3000);
							
							}
						} catch (Exception e) {
						
						//Validation
					try {
					By byClick_SuppressionList = appInd.createAndGetByObject("Click_SuppressionList");
                   WebElement element = obrowser.findElement(byClick_SuppressionList);
					((JavascriptExecutor) obrowser).executeScript("arguments[0].click();", element);
					Thread.sleep(3000);
					
					By ListForValidation=appInd.createAndGetByObject("ListForValidation");
							List<WebElement> ListForValidation1 = obrowser.findElements(ListForValidation);
							
							
							
							for(int j=0;j<ListForValidation1.size();j++) {
								 System.out.println("strStatus:::"+ ListForValidation1.get(j).getText());
								if(ListForValidation1.get(j).getText().contains(oinpuTDtMap.get("Param_2"))) {
									strStatus = "true";
									 System.out.println("strStatus:::"+ strStatus);
									 break;
								 }
								
						
							}
							
					}catch(Exception e1) {
								strStatus += "false";
								appInd.writeLog("Fail", "Fiter is not Saved Successfully");	
								//appInd.takeSnapShot(obrowser, System.getProperty("user.dir"));
								appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
										+ "\\Results\\snapshot\\ChangeDetection\\TC_CD_ActionSuppress.png");
								
								
							}
							
						Thread.sleep(3000);
						By byCD_SuppressionClose = appInd.createAndGetByObject("CD_SuppressionClose");
		                   WebElement element = obrowser.findElement(byCD_SuppressionClose);
							((JavascriptExecutor) obrowser).executeScript("arguments[0].click();", element);
							Thread.sleep(3000);
				
				
				// ########################################################################
					if (strStatus.contains("false")) {
						String s=oinpuTDtMap.get("Param_1");
						appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
								+ "\\Results\\snapshot\\ChangeDetection\\TC_CD_ActionSuppress_Fail_snapshot.png");
						appInd.writeLog("Fail", "'TC_CD_ActionSuppress' script was failed");
						bolstatus = false;
						strStatus = null;
					} else if (strStatus.contains("true")) {
						appInd.writeLog("Pass", "'TC_CD_ActionSuppress' script was Successful");
						bolstatus = true;
						strStatus = null;
					}
					
				}
					}
					}
							
				
			} else {
				appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
						+ "\\Results\\snapshot\\ChangeDetection\\Failed_to_launch_IE_browser_snapshot.png");
				appInd.writeLog("Fail", "Failed to launch the IE browser");
				bolstatus = false;
			}
			
			// write the result into Map
			if ((bolstatus == false) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Fail");
			

			} else if ((bolstatus == false) && !(strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Skip");
				
			} else if ((bolstatus == true) && (strExecutionsts.equalsIgnoreCase("yes"))) {
				oinpuTDtMap.put("result", "Pass");
			
			} else {
				oinpuTDtMap.put("result", "Skip");
				
			}
			strcurrentTD = String.valueOf(TD);
			oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
			oinpuTDtMap = null;
			strcommonCountvalue = null;
		} // for loop
		return oinputMap;
		
		
	} catch (Exception e) {
		appInd.writeLog("Exception", "Exception while executing 'TC_CD_ActionSuppress' method. " + e.getMessage());
		oinpuTDtMap.put("result", "Fail");
		oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
		return oinputMap;
	} finally {
		System.out.println("end");
	}
}				


/********************************
 * Method Name : TC_CD_ActionUnacknowledge
 * Purpose : this will filter the Acknowledged defects in change Detection
 * Author : Mahesh TK 
 * Reviewer : 
 * Date of Creation : 20-feb-2019 
 * Date ofmodification :
 * ******************************
 */

	
@SuppressWarnings({ "unchecked", "rawtypes" })
public Map<String, HashMap> TC_CD_ActionUnacknowledge() {
	Map<String, HashMap> oinputMap = new HashMap<String, HashMap>();
	Map<String, String> oinpuTDtMap = new HashMap<String, String>();
	String strcurrentTD = null;
	String strcommonCountvalue = null;
	String strcommonTime = null;
	try {
		appInd.writeLog("#", "****TC_CD_ActionUnacknowledge:- started*****");
		obrowser = oDriver.getWebDriver();
		boolean bolstatus = false;
		String strExecutionsts = null;
		oinputMap = appInd.getInputData(strTCID);
		for (int TD = 1; TD <= oinputMap.size(); TD++) {
			oinpuTDtMap = oinputMap.get(String.valueOf(TD));

			if ((obrowser != null)) {
				// Read the Execution Status
				strExecutionsts = oinpuTDtMap.get("Executestatus");
				if (strExecutionsts.equalsIgnoreCase("yes")) {
					// ########################################################################	
					By byClickOnMenuBar = appInd.createAndGetByObject("Menu_Bar");
					obrowser.findElement(byClickOnMenuBar).sendKeys(Keys.ENTER);
					Thread.sleep(1000);
					obrowser.findElement(By.linkText("Dashboard")).sendKeys(Keys.ENTER);
					Thread.sleep(5000);
					//click on the Engineering Anomaly from the menu 
					strStatus +=oGenericAppmethods.menu_changeDetection_Navigation(obrowser);			
					
					//Select the System from  "
					
					if (oGenericAppmethods.change_detection__selectsystem(obrowser,oinpuTDtMap.get("Param_1")))
					{
					//select from filter 
					//select filter unacknowledge
					oGenericAppmethods.change_detection__filters(obrowser,"acknowledged") ;
					//oGenericAppmethods.Engr_default_filters(obrowser);
					
					//perform operation 
					strStatus+= oGenericAppmethods.Change_Detection_Actions(obrowser,"unacknowledge","","") ;
					
					
					appInd.waitFor(obrowser, "ajax_loading", "invisible", "", 600);	
					int count=0;
				
					//Validation
					try {
					for (int i=1 ;i<=2;i++) {
					String text=	oGenericAppmethods.MainGrid_Operation_CD(obrowser,"get_innertext",i ,9, "" );
						System.out.println(text);		
											
						if(text.equalsIgnoreCase("unacknowledge")) {
							count++;
							System.out.println(count);
							
						}
						else if(text==null)	{
							break;
						}
							
						Thread.sleep(5000);
						
					}
					
					if(count>=2)
					{
						strStatus = "true";
					System.out.println("pass");
					}
					else
					{
						strStatus = "false";
						System.out.println("Fail");
						
						
					}

					}
					catch(Exception e)
					{
						
					}
										//==========================
						
						//get the counts
				//		strcommonCountvalue=oGenericAppmethods.strcount;
						
						// ########################################################################
						if (strStatus.contains("false")) {
							String s=oinpuTDtMap.get("Param_1");
							appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
									+ "\\Results\\snapshot\\ChangeDetection\\TC_CD_ActionUnacknowledge_Fail_snapshot.png");
							appInd.writeLog("Fail", "'TC_CD_ActionUnacknowledge' script was failed");
							bolstatus = false;
							strStatus = null;
						} else if (strStatus.contains("true")) {
							appInd.writeLog("Pass", "'TC_CD_ActionUnacknowledge' script was Successful");
							bolstatus = true;
							strStatus = null;
						}
			}
					}
				} else {
					appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
							+ "\\Results\\snapshot\\ChangeDetection\\Failed_to_launch_IE_browser_snapshot.png");
					appInd.writeLog("Fail", "Failed to launch the IE browser");
					bolstatus = false;
				}
				
				// write the result into Map
				if ((bolstatus == false) && (strExecutionsts.equalsIgnoreCase("yes"))) {
					oinpuTDtMap.put("result", "Fail");
				

				} else if ((bolstatus == false) && !(strExecutionsts.equalsIgnoreCase("yes"))) {
					oinpuTDtMap.put("result", "Skip");
					
				} else if ((bolstatus == true) && (strExecutionsts.equalsIgnoreCase("yes"))) {
					oinpuTDtMap.put("result", "Pass");
				
				} else {
					oinpuTDtMap.put("result", "Skip");
					
				}
				strcurrentTD = String.valueOf(TD);
				oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
				oinpuTDtMap = null;
				strcommonCountvalue = null;
			} // for loop
			return oinputMap;
			
			
		} catch (Exception e) {
			appInd.writeLog("Exception", "Exception while executing 'TC_CD_ActionUnacknowledge' method. " + e.getMessage());
			oinpuTDtMap.put("result", "Fail");
			oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
			return oinputMap;
		} finally {
			System.out.println("end");
		}
	}

/********************************
 * Method Name : TC_CD_ActionUnassignCR
 * Purpose : this will filter the Acknowledged defects in change Detection
 * Author : Mahesh TK 
 * Reviewer : 
 * Date of Creation : 20-feb-2019 
 * Date ofmodification :
 * ******************************
 */

	
@SuppressWarnings({ "unchecked", "rawtypes" })
public Map<String, HashMap> TC_CD_ActionUnassignCR() {
	Map<String, HashMap> oinputMap = new HashMap<String, HashMap>();
	Map<String, String> oinpuTDtMap = new HashMap<String, String>();
	String strcurrentTD = null;
	String strcommonCountvalue = null;
	String strcommonTime = null;
	try {
		appInd.writeLog("#", "****TC_CD_ActionUnassignCR:- started*****");
		obrowser = oDriver.getWebDriver();
		boolean bolstatus = false;
		String strExecutionsts = null;
		oinputMap = appInd.getInputData(strTCID);
		for (int TD = 1; TD <= oinputMap.size(); TD++) {
			oinpuTDtMap = oinputMap.get(String.valueOf(TD));

			if ((obrowser != null)) {
				// Read the Execution Status
				strExecutionsts = oinpuTDtMap.get("Executestatus");
				if (strExecutionsts.equalsIgnoreCase("yes")) {
					// ########################################################################	
					By byClickOnMenuBar = appInd.createAndGetByObject("Menu_Bar");
					obrowser.findElement(byClickOnMenuBar).sendKeys(Keys.ENTER);
					Thread.sleep(1000);
					obrowser.findElement(By.linkText("Dashboard")).sendKeys(Keys.ENTER);
					Thread.sleep(5000);
					//click on the Engineering Anomaly from the menu 
					strStatus +=oGenericAppmethods.menu_changeDetection_Navigation(obrowser);			
					
					//Select the System from  "
					if (oGenericAppmethods.change_detection__selectsystem(obrowser,oinpuTDtMap.get("Param_1")))
					{
					
					//Defaulting the anomaly grid 
					//oGenericAppmethods.Engr_default_filters(obrowser);
					
					//perform operation 
					strStatus+= oGenericAppmethods.Change_Detection_Actions(obrowser,"unassign","","") ;
					
					
					appInd.waitFor(obrowser, "ajax_loading", "invisible", "", 600);	
					int count=0;
				
					//Validation
					try {
					for (int i=1 ;i<=2;i++) {
					String text=	oGenericAppmethods.MainGrid_Operation_CD(obrowser,"get_innertext",i ,9, "" );
						System.out.println(text);		
											
						if(text.equalsIgnoreCase("unassign")) {
							count++;
							System.out.println(count);
							
						}
						else if(text==null)	{
							break;
						}
							
						Thread.sleep(5000);
						
					}
					
					if(count>=2)
					{
						strStatus = "true";
					System.out.println("pass");
					}
					else
					{
						strStatus = "false";
						System.out.println("Fail");
						
						
					}

					}
					catch(Exception e)
					{
						
					}
										//==========================
						
						//get the counts
				//		strcommonCountvalue=oGenericAppmethods.strcount;
						
						// ########################################################################
						if (strStatus.contains("false")) {
							String s=oinpuTDtMap.get("Param_1");
							appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
									+ "\\Results\\snapshot\\ChangeDetection\\TC_CD_ActionUnassignCR_Fail_snapshot.png");
							appInd.writeLog("Fail", "'TC_CD_ActionUnassignCR' script was failed");
							bolstatus = false;
							strStatus = null;
						} else if (strStatus.contains("true")) {
							appInd.writeLog("Pass", "'TC_CD_ActionUnassignCR' script was Successful");
							bolstatus = true;
							strStatus = null;
						}
					}
				}
				} else {
					appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
							+ "\\Results\\snapshot\\ChangeDetection\\Failed_to_launch_IE_browser_snapshot.png");
					appInd.writeLog("Fail", "Failed to launch the IE browser");
					bolstatus = false;
				}
				
				// write the result into Map
				if ((bolstatus == false) && (strExecutionsts.equalsIgnoreCase("yes"))) {
					oinpuTDtMap.put("result", "Fail");
				

				} else if ((bolstatus == false) && !(strExecutionsts.equalsIgnoreCase("yes"))) {
					oinpuTDtMap.put("result", "Skip");
					
				} else if ((bolstatus == true) && (strExecutionsts.equalsIgnoreCase("yes"))) {
					oinpuTDtMap.put("result", "Pass");
				
				} else {
					oinpuTDtMap.put("result", "Skip");
					
				}
				strcurrentTD = String.valueOf(TD);
				oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
				oinpuTDtMap = null;
				strcommonCountvalue = null;
			} // for loop
			return oinputMap;
			
			
		} catch (Exception e) {
			appInd.writeLog("Exception", "Exception while executing 'TC_CD_ActionUnassignCR' method. " + e.getMessage());
			oinpuTDtMap.put("result", "Fail");
			oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
			return oinputMap;
		} finally {
			System.out.println("end");
		}
	}
/********************************
 * Method Name : TC_CD_ActionUnsuppress
 * Purpose : this will filter the Acknowledged defects in change Detection
 * Author : Mahesh TK 
 * Reviewer : 
 * Date of Creation : 20-feb-2019 
 * Date ofmodification :
 * ******************************
 */

	
@SuppressWarnings({ "unchecked", "rawtypes" })
public Map<String, HashMap> TC_CD_ActionUnsuppress() {
	Map<String, HashMap> oinputMap = new HashMap<String, HashMap>();
	Map<String, String> oinpuTDtMap = new HashMap<String, String>();
	String strcurrentTD = null;
	String strcommonCountvalue = null;
	String strcommonTime = null;
	try {
		appInd.writeLog("#", "****TC_CD_ActionUnsuppress:- started*****");
		obrowser = oDriver.getWebDriver();
		boolean bolstatus = false;
		String strExecutionsts = null;
		oinputMap = appInd.getInputData(strTCID);
		for (int TD = 1; TD <= oinputMap.size(); TD++) {
			oinpuTDtMap = oinputMap.get(String.valueOf(TD));

			if ((obrowser != null)) {
				// Read the Execution Status
				strExecutionsts = oinpuTDtMap.get("Executestatus");
				if (strExecutionsts.equalsIgnoreCase("yes")) {
					// ########################################################################	
					By byClickOnMenuBar = appInd.createAndGetByObject("Menu_Bar");
					obrowser.findElement(byClickOnMenuBar).sendKeys(Keys.ENTER);
					Thread.sleep(1000);
					obrowser.findElement(By.linkText("Dashboard")).sendKeys(Keys.ENTER);
					Thread.sleep(5000);
					//click on the Engineering Anomaly from the menu 
					strStatus +=oGenericAppmethods.menu_changeDetection_Navigation(obrowser);			
					
					if (oGenericAppmethods.change_detection__selectsystem(obrowser,oinpuTDtMap.get("Param_1")))
					{
					
					//Defaulting the anomaly grid 
					//oGenericAppmethods.Engr_default_filters(obrowser);
					
					//perform operation 
					strStatus+= oGenericAppmethods.Change_Detection_Actions(obrowser,"unsuppress","","") ;
					
					
					appInd.waitFor(obrowser, "ajax_loading", "invisible", "", 600);	
					int count=0;
				
					//validation
					try {
					for (int i=1 ;i<=2;i++) {
					String text=	oGenericAppmethods.MainGrid_Operation_CD(obrowser,"get_innertext",i ,9, "" );
						System.out.println(text);		
											
						
						
						
					}

					}
					catch(Exception e)
					{
						
					}
										//==========================
						
						//get the counts
				//		strcommonCountvalue=oGenericAppmethods.strcount;
						
						// ########################################################################
						if (strStatus.contains("false")) {
							String s=oinpuTDtMap.get("Param_1");
							appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
									+ "\\Results\\snapshot\\ChangeDetection\\TC_CD_ActionUnsuppress_Fail_snapshot.png");
							appInd.writeLog("Fail", "'TC_CD_ActionUnsuppress' script was failed");
							bolstatus = false;
							strStatus = null;
						} else if (strStatus.contains("true")) {
							appInd.writeLog("Pass", "'TC_CD_ActionUnsuppress' script was Successful");
							bolstatus = true;
							strStatus = null;
						}
					}
				}
				} else {
					appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
							+ "\\Results\\snapshot\\ChangeDetection\\Failed_to_launch_IE_browser_snapshot.png");
					appInd.writeLog("Fail", "Failed to launch the IE browser");
					bolstatus = false;
				}
				
				// write the result into Map
				if ((bolstatus == false) && (strExecutionsts.equalsIgnoreCase("yes"))) {
					oinpuTDtMap.put("result", "Fail");
				

				} else if ((bolstatus == false) && !(strExecutionsts.equalsIgnoreCase("yes"))) {
					oinpuTDtMap.put("result", "Skip");
					
				} else if ((bolstatus == true) && (strExecutionsts.equalsIgnoreCase("yes"))) {
					oinpuTDtMap.put("result", "Pass");
				
				} else {
					oinpuTDtMap.put("result", "Skip");
					
				}
				strcurrentTD = String.valueOf(TD);
				oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
				oinpuTDtMap = null;
				strcommonCountvalue = null;
			} // for loop
			return oinputMap;
			
			
		} catch (Exception e) {
			appInd.writeLog("Exception", "Exception while executing 'TC_CD_ActionUnsuppress' method. " + e.getMessage());
			oinpuTDtMap.put("result", "Fail");
			oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
			return oinputMap;
		} finally {
			System.out.println("end");
		}
	}


} //close of Change_Detection
