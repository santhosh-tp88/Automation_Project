
package org.honeywell.Trace.testcase_scripts;

import java.util.HashMap;
import java.util.Map;

import org.honeywell.Trace.common_methods.GenricApplicationMethods;
import org.honeywell.Trace.driver.DriverScript;
import org.honeywell.Trace.utility.Utility;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ActiTime 
{

	
	
	WebDriver obrowser = null;
	GenricApplicationMethods oGenericAppmethods = new GenricApplicationMethods();
	DriverScript oDriver = new DriverScript();
	Utility appInd = new Utility();
	String strTCID = oDriver.getTestcaseId();
	String strStatus = null;

	
	
	/********************************
	 * Method Name : TC_CD_ActiTime_login
	 * Purpose : login to ActiTime
	 * Author : Santhosh TP
	 * Reviewer : 
	 * Date of Creation : 19-Aug-2019 
	 * Date of modification :
	 * ******************************
	 */

		
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, HashMap> TC_CD_ActiTime_login() {
		Map<String, HashMap> oinputMap = new HashMap<String, HashMap>();
		Map<String, String> oinpuTDtMap = new HashMap<String, String>();
		String strcurrentTD = null;
		try {
			appInd.writeLog("#", "****TC_CD_ActiTime_login:- started*****");
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
						
						String Username=oinpuTDtMap.get("Param_1");
						String Password=oinpuTDtMap.get("Param_2");
						
						
						strStatus +=appInd.setObject(obrowser, "Acti_Uname", Username);
						Thread.sleep(3000);
						strStatus +=appInd.setObject(obrowser, "Acti_Pwd", Password);
						Thread.sleep(3000);
						
						strStatus +=appInd.clickObject(obrowser, "Submit");
						Thread.sleep(5000);
						
						
						WebElement ole=appInd.createAndGetObject(obrowser, "Short_Window");
						boolean bln=ole.isDisplayed();
						if(bln)
						{
							appInd.createAndGetObject(obrowser, "Close_Window").click();
						}
						
						
						strStatus +=appInd.clickObject(obrowser, "Click_Users");
						Thread.sleep(5000);
						
						
						
									
						// ########################################################################
						if (strStatus.contains("false")) {
							appInd.writeLog("Fail", "'TC_CD_ActiTime_login' script was failed");
							System.out.println("'TC_CD_ActiTime_login' script was failed");
							appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
									+ "\\Results\\snapshot\\ActiTime\\TC_CD_ActiTime_login.png");
							
							bolstatus = false;
							strStatus = null;
						} else if (strStatus.contains("true")) {
							appInd.writeLog("Pass", "'TC_CD_ActiTime_login' script was Successful");
							System.out.println("'TC_CD_ActiTime_login' script was Successful");
							bolstatus = true;
							strStatus = null;
						}
					}
				} else {
					appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
							+ "\\Results\\snapshot\\ChangeDetection\\Failed_to_launch_Chrome_browser_snapshot.png");
					appInd.writeLog("Fail", "Failed to launch the Chrome browser");
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
			} // for loop
			return oinputMap;
			
			
		} catch (Exception e) {
			appInd.writeLog("Exception", "Exception while executing 'TC_CD_ActiTime_login' method. " + e.getMessage());
			oinpuTDtMap.put("result", "Fail");
			oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
			return oinputMap;
		} finally {
			System.out.println("end");
		}
	}
	
	
	
	/********************************
	 * Method Name : TC_CD_Create_User
	 * Purpose : To Create Users
	 * Author : Santhosh TP
	 * Reviewer : 
	 * Date of Creation : 19-Aug-2019 
	 * Date of modification :
	 * ******************************
	 */

		
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, HashMap> TC_CD_Create_User() {
		Map<String, HashMap> oinputMap = new HashMap<String, HashMap>();
		Map<String, String> oinpuTDtMap = new HashMap<String, String>();
		String strcurrentTD = null;
		try {
			appInd.writeLog("#", "****TC_CD_Create_User:- started*****");
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
						
						
						WebElement ole=appInd.createAndGetObject(obrowser, "Short_Window");
						boolean bln=ole.isDisplayed();
						if(bln)
						{
							appInd.createAndGetObject(obrowser, "Close_Window").click();
						}
						
						
						strStatus +=appInd.clickObject(obrowser, "Click_Users");
						Thread.sleep(5000);
						
						
						
									
						// ########################################################################
						if (strStatus.contains("false")) {
							appInd.writeLog("Fail", "'TC_CD_Create_User' script was failed");
							System.out.println("'TC_CD_Create_User' script was failed");
							appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
									+ "\\Results\\snapshot\\ActiTime\\TC_CD_Create_User.png");
							
							bolstatus = false;
							strStatus = null;
						} else if (strStatus.contains("true")) {
							appInd.writeLog("Pass", "'TC_CD_Create_User' script was Successful");
							System.out.println("'TC_CD_Create_User' script was Successful");
							bolstatus = true;
							strStatus = null;
						}
					}
				} else {
					appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
							+ "\\Results\\snapshot\\ChangeDetection\\Failed_to_launch_Chrome_browser_snapshot.png");
					appInd.writeLog("Fail", "Failed to launch the Chrome browser");
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
			} // for loop
			return oinputMap;
			
			
		} catch (Exception e) {
			appInd.writeLog("Exception", "Exception while executing 'TC_CD_Create_User' method. " + e.getMessage());
			oinpuTDtMap.put("result", "Fail");
			oinputMap.put(strcurrentTD, (HashMap) oinpuTDtMap);
			return oinputMap;
		} finally {
			System.out.println("end");
		}
	}

	
	
	

	
}
