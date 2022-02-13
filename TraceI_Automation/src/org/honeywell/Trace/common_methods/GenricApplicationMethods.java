package org.honeywell.Trace.common_methods;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.honeywell.Trace.driver.DriverScript;
import org.honeywell.Trace.utility.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import bsh.util.JConsole.BlockingPipedInputStream;
import net.bytebuddy.jar.asm.commons.TryCatchBlockSorter;

public class GenricApplicationMethods extends DriverScript {
	Utility oUtility = new Utility();
	DriverScript oDriver = new DriverScript();
	public int commonCountRecord;
	public String strcount=null; //added by archana

	/********************************
	 * Method Name : launchBrowser purpose : method is used to launch the browser
	 * Author : Mahesh TK Creation Date : Reviewed By :
	 * ******************************
	 */
	public WebDriver launchBrowser(String sBrowser) {
		WebDriver oDriver = null;
		try {

			if (sBrowser.equalsIgnoreCase("ie")) {
				System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir") + "\\Library\\Browsers_Driver\\IEDriverServer.exe");
				System.setProperty("webdriver.load.strategy", "unstable");
				oDriver = new InternetExplorerDriver();
			}

			else if (sBrowser.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "\\Library\\Browsers_Driver\\chromedriver.exe");
				ChromeOptions options = new ChromeOptions();
				options.setExperimentalOption("useAutomationExtension", false);
				oDriver = new ChromeDriver(options);

				// oDriver = new ChromeDriver(options);
				// oDriver =new ChromeDriver();

			} else {
				oUtility.writeLog("Fail", "launch the browser: '" + sBrowser + "' ,Invalid browser name '" + sBrowser
						+ "' was mentioned");
			}

			if (oDriver != null) {
				oUtility.writeLog("Pass",
						"Executing 'launchBrowser' method,The browser '" + sBrowser + "' has launched successful");
				oDriver.manage().window().maximize();
				return oDriver;
			} else {
				oUtility.writeLog("Fail",
						"Executing 'launchBrowser' method, Failed to launch the browser '" + sBrowser + "'");
				return null;
			}

		} catch (Exception e) {
			oUtility.writeLog("Fail",
					"Executing 'launchBrowser' method, Exception while executing 'launchBrowser' method. "
							+ e.getMessage());
			return null;
		}
	}
	
	

	/********************************
	 * Method Name : navigateURL Purpose : This method will launch the provided URL
	 * Author : Mahesh TK Reviewer : Date of Creation : Date of modification :
	 * ******************************
	 */
	public boolean navigateURL(String strURL) {
		// System.out.println("navigateURL:- started" ); //Self testcode review
		boolean bolstatus = false;
			// Assign of created IE driver browser object
			oBrowser = oDriver.getWebDriver();
			if (oBrowser != null) {
				oBrowser.navigate().to(strURL);
				// appInd. clickObject(oBrowser, "Login_browser");
				if(System.getProperty("BROWSER").equalsIgnoreCase("ie")) {
					if (oBrowser.findElement(By.id("overridelink")) != null) {
						oBrowser.findElement(By.id("overridelink")).click();			
					}
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					bolstatus = true;
				}else if(System.getProperty("BROWSER").equalsIgnoreCase("chrome")){
					oBrowser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					bolstatus = true;
				}
			
		} else {
			System.out.println("obrowser is null");
			bolstatus = false;
		}
		return bolstatus;

	}

	// common method for spares R,U,F
	/********************************
	 * Method Name : commonSparesChannel Purpose : This method will count the record
	 * of Free,Reserved,Used channel Author : Vijay Kumar Maurya Reviewer : Date of
	 * Creation :11/26/2018 Date of modification : ******************************
	 */
	/*
	 * public boolean commonSparesChannel(String strchanneltype) { boolean flag =
	 * false; String strStatus = null; int totalcount = 0; int channelcount = 0; int
	 * totalcommonFRUchannelCountValue = 0; int totalchannelCountValue = 0; String
	 * statusValue = null; try { try { WebElement div =
	 * oBrowser.findElement(By.xpath(
	 * "//*[@id='defectMainTableRows']/tr[1]/td[8]/span[1]")); }catch(Exception e) {
	 * appInd.writeLog("#", "NO RECORD FOUND"); flag = false; return flag; }
	 * 
	 * for (int i = 1;; i++) { EventFiringWebDriver eventFiringWebDriver = new
	 * EventFiringWebDriver(oBrowser); Object eventObj = eventFiringWebDriver
	 * .executeScript(
	 * "document.querySelector('.scrollArea.ng-scope').scrollTop=1000000000"); if (i
	 * == 200) { break; } } By totalchannelCount =
	 * appInd.createAndGetByObject("Total_Channel_Count"); totalchannelCountValue =
	 * Integer.parseInt(oBrowser.findElement(totalchannelCount).getText()); if
	 * (strchanneltype.equalsIgnoreCase("U")) { By uchannelCount =
	 * appInd.createAndGetByObject("U_Channel_Count");
	 * totalcommonFRUchannelCountValue =
	 * Integer.parseInt(oBrowser.findElement(uchannelCount).getText());
	 * System.out.println("UtotalcommonFRUchannelCountValue::"
	 * +totalcommonFRUchannelCountValue); } else if
	 * (strchanneltype.equalsIgnoreCase("F")) { By fchannelCount =
	 * appInd.createAndGetByObject("F_Channel_Count");
	 * totalcommonFRUchannelCountValue =
	 * Integer.parseInt(oBrowser.findElement(fchannelCount).getText());
	 * System.out.println("FtotalcommonFRUchannelCountValue::"
	 * +totalcommonFRUchannelCountValue); } else { By rchannelCount =
	 * appInd.createAndGetByObject("R_Channel_Count");
	 * totalcommonFRUchannelCountValue =
	 * Integer.parseInt(oBrowser.findElement(rchannelCount).getText());
	 * System.out.println("RtotalcommonFRUchannelCountValue::"
	 * +totalcommonFRUchannelCountValue); } for (int row = 1;; row++) { try {
	 * statusValue = oBrowser
	 * .findElement(By.xpath("//*[@id='defectMainTableRows']/tr[" + row +
	 * "]/td[8]/span[1]")) .getText(); System.out.println("statusValue:::"
	 * +statusValue); }catch(Exception e) { break; }
	 * 
	 * if (statusValue != null) { totalcount++; System.out.println("totalcount::"
	 * +totalcount); } if ((statusValue.equalsIgnoreCase(strchanneltype))) {
	 * channelcount++; System.out.println("channelcount::" + channelcount);
	 * appInd.writeLog("#", "Channelcount" + channelcount); } if
	 * (!statusValue.equalsIgnoreCase(strchanneltype)) { strStatus += false; } else
	 * { strStatus += true; } } if (totalcommonFRUchannelCountValue == channelcount)
	 * { commonCountRecord = channelcount; strStatus += true; } else { strStatus +=
	 * false; appInd.writeLog("Fail", "'sparesChannels' script was failed"); } if
	 * (strStatus.contains("false")) { flag = false; } else { flag = true; } } catch
	 * (Exception e) { e.printStackTrace(); } finally { return flag; } }
	 */
	// ============================
//	public boolean commonSparesChannel(String strchanneltype) {
//		boolean flag = true;
//		String strStatus = null;
//		int totalCount = 0;
//		int channelcount = 0;
//		int totalcommonFRUchannelCountValue = 0;
//		int totalchannelCountValue = 0;
//		String statusValue = null;
//		try {
//			try {
//				WebElement div = oBrowser.findElement(By.xpath("//*[@id='defectMainTableRows']/tr[1]/td[8]/span[1]"));
//				
//				for (int i = 1;; i++) {
//					EventFiringWebDriver eventFiringWebDriver = new EventFiringWebDriver(oBrowser);
//					Object eventObj = eventFiringWebDriver
//							.executeScript("document.querySelector('.scrollArea.ng-scope').scrollTop=100000000000");
//					if (i == 500) {
//						break;
//					}
//				}
//				if (strchanneltype.equalsIgnoreCase("U")) {
//					By uchannelCount = appInd.createAndGetByObject("U_Channel_Count");
//					totalcommonFRUchannelCountValue = Integer.parseInt(oBrowser.findElement(uchannelCount).getText());
//					System.out.println("UtotalcommonFRUchannelCountValue::" + totalcommonFRUchannelCountValue);
//				} else if (strchanneltype.equalsIgnoreCase("F")) {
//					By fchannelCount = appInd.createAndGetByObject("F_Channel_Count");
//					totalcommonFRUchannelCountValue = Integer.parseInt(oBrowser.findElement(fchannelCount).getText());
//					System.out.println("FtotalcommonFRUchannelCountValue::" + totalcommonFRUchannelCountValue);
//				} else {
//					By rchannelCount = appInd.createAndGetByObject("R_Channel_Count");
//					totalcommonFRUchannelCountValue = Integer.parseInt(oBrowser.findElement(rchannelCount).getText());
//					System.out.println("RtotalcommonFRUchannelCountValue::" + totalcommonFRUchannelCountValue);
//				}
//				List<WebElement> rows = oBrowser
//						.findElements(By.xpath("//*[@id='defectMainTableRows']/tr/td[8]/span[1]"));
//				
//				System.out.println("No of rows are : " + rows.size());
//				totalCount = rows.size();
//				/*****************************putting value in string to print in excel--Archana************************/
//				
//				strcount+=Integer.toString(totalcommonFRUchannelCountValue)+"#"+Integer.toString(totalCount);
//				/*****************************putting value in string to print in excel--Archana end ************************/
//				for (WebElement rowElement : rows) {
//					if (!rowElement.getText().equalsIgnoreCase(strchanneltype)) {
//						strStatus += false;
//						break;
//					} else {
//						System.out.println("rowElement.getText()::" +rowElement.getText());
//						strStatus += true;
//					}
//
//				}
//			} catch (Exception e) {
//				/*****************************putting value in string to print in excel--Archana************************/
//				strcount+="0"+"#"+"0";
//				/*****************************putting value in string to print in excel--Archana end ************************/
//				appInd.writeLog("#", "NO RECORD FOUND");
//				//flag = true;
//				//return flag;
//			}
//		
//			if (totalcommonFRUchannelCountValue == totalCount) {
//				System.out.println("totalCount::" +totalCount);
//				System.out.println("totalcommonFRUchannelCountValue::" +totalcommonFRUchannelCountValue);
//				strStatus += true;
//				strcount+="#"+"Pass";
//			} else {
//				strStatus += false;
//				strcount+="#"+"Fail";
//				appInd.writeLog("Fail", "'sparesChannels' script was failed");
//			}
//			if (strStatus.contains("false")) {
//				flag = false;
//			} else {
//				flag = true;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return flag;
//	}
	
	public boolean commonSparesChannel(String strchanneltype) throws Exception {
		boolean flag = true;
		String strStatus = null;
		int totalCount = 0;
		int channelcount = 0;
		int totalcommonFRUchannelCountValue = 0;
		int totalchannelCountValue = 0;
		String statusValue = null;
		
		try {
			WebElement div = oBrowser.findElement(By.xpath("//*[@id='defectMainTableRows']/tr[1]/td[8]/span[1]"));
				
		try {
			Thread.sleep(1000);
			WebElement ele = oBrowser.findElement(By.xpath("//p[contains(text(),'Showing Results')]/span[@class='ng-binding']"));
			String str = ele.getText();
			System.out.println("str : "+str);
			String objArr[]  = str.split("/");
			System.out.println("Array lenght : "+objArr.length);
			String str1 = objArr[0].trim();
			System.out.println("str1 : "+str1);
			int recordShown = Integer.parseInt(str1);
			System.out.println("RecordShown : "+recordShown);
			String str2 = objArr[1].trim();
			System.out.println("str2 : "+str2);
			int totalRecord = Integer.parseInt(str2);
			System.out.println("TotalRecord : "+totalRecord);
			int noOfTimesToScroll = (totalRecord/recordShown);
			System.out.println("noOfTimesToScroll "+noOfTimesToScroll);
			
			int remainder = totalRecord%recordShown;
			
			if(totalRecord<50) {
				String[] statusArray = new String[totalRecord];
				for(int j=1;j<=statusArray.length;j++) {
					System.out.println("j : "+j);
					statusArray[j-1] = oBrowser.findElement(By.xpath("//tbody[@id='defectMainTableRows']/tr["+j+"]/descendant::span[contains(@class,'ng-binding')]")).getText();
					if(statusArray[j-1].trim().equalsIgnoreCase(strchanneltype)) {
						strStatus += true;
						appInd.writeLog("Pass", "commonSparesChannel_New is passed");
					}else {
						strStatus = "false";
						appInd.writeLog("Fail", "commonSparesChannel_New is failed");
					}
				}
			}
			
			
			for(int i= recordShown;i<totalRecord;) {
				String[] statusArray = new String[50];
				for(int j=i;j>i-recordShown;j--) {
					System.out.println("j : "+j);
					appInd.writeLog("Pass", "j : "+j);
					statusArray[i-j] = oBrowser.findElement(By.xpath("//tbody[@id='defectMainTableRows']/tr["+j+"]/descendant::span[contains(@class,'ng-binding')]")).getText();
					if(statusArray[i-j].trim().equalsIgnoreCase(strchanneltype)) {
						strStatus += true;
						appInd.writeLog("Pass", "commonSparesChannel_New is passed");
					}else {
						strStatus = "false";
						appInd.writeLog("Fail", "commonSparesChannel_New is failed");
						appInd.takeSnapShot(oBrowser, System.getProperty("user.dir")+ "\\Results\\snapshot\\Spares\\mismatch.png");
					}
				}
				WebElement element = oBrowser.findElement(By.xpath("//tbody[@id='defectMainTableRows']/tr["+i+"]/td[3]"));
				((JavascriptExecutor) oBrowser).executeScript("arguments[0].scrollIntoView(true);",element);
				Thread.sleep(2000);
				i=i+recordShown;
				System.out.println("Size : "+i);
				appInd.writeLog("Pass", "Size : "+i);
			}
			
			if(remainder<=recordShown-1) {
				String[] statusArray = new String[remainder];
				for(int j=totalRecord;j>totalRecord-remainder;j--) {
					System.out.println("j : "+j);
					appInd.writeLog("Pass", "j : "+j);
					statusArray[totalRecord-j] = oBrowser.findElement(By.xpath("//tbody[@id='defectMainTableRows']/tr["+j+"]/descendant::span[contains(@class,'ng-binding')]")).getText();
					if(statusArray[totalRecord-j].trim().equalsIgnoreCase(strchanneltype)) {
						strStatus += true;
						appInd.writeLog("Pass", "commonSparesChannel_New is passed");
					}else {
						strStatus = "false";
						appInd.writeLog("Fail", "commonSparesChannel_New is failed");
						appInd.takeSnapShot(oBrowser, System.getProperty("user.dir")+ "\\Results\\snapshot\\Spares\\mismatch.png");
					}
				}
			}
			
			WebElement elen = oBrowser.findElement(By.xpath("//p[contains(text(),'Showing Results')]/span[@class='ng-binding']"));
			String strn = elen.getText();
			String objArrn[]  = strn.split("/");
			String str1n = objArrn[0].trim();
			int recordShownn = Integer.parseInt(str1n);
			String str2n = objArrn[1].trim();
			int totalRecordn = Integer.parseInt(str2n);
			
			/*****************************putting value in string to print in excel--Archana************************/
			
			strcount+=Integer.toString(totalRecordn)+"#"+Integer.toString(recordShownn);
			/*****************************putting value in string to print in excel--Archana end ************************/
			
			if(recordShownn==totalRecordn) {
				strStatus = "true";
				appInd.writeLog("Pass", "Channel count is passed");
				System.out.println("UtotalcommonFRUchannelCountValue::" + totalRecordn);
				strcount+="#"+"Pass";
			}else {
				strStatus = "false";
				appInd.writeLog("Fail", "Channel count is failed");
				appInd.takeSnapShot(oBrowser, System.getProperty("user.dir")+ "\\Results\\snapshot\\Spares\\count.png");
				strcount+="#"+"Fail";
			}
			
			if(strStatus.contains("false")) {
				strStatus = "false";
				appInd.writeLog("Fail", "commonSparesChannel_New is failed");
				flag=false;
			}else {
				flag=true;
			}
		}catch(Exception e) {
			e.printStackTrace();
			strStatus = "false";
			appInd.writeLog("Fail", "commonSparesChannel_New is failed");
			appInd.takeSnapShot(oBrowser, System.getProperty("user.dir")+ "\\Results\\snapshot\\Spares\\count.png");
			
		}
		
		}catch(Exception e1){
			strStatus += true;
			flag = true;
			/*****************************putting value in string to print in excel--Archana************************/
			strcount+="0"+"#"+"0"+"#"+"Pass";
			/*****************************putting value in string to print in excel--Archana end ************************/
			appInd.writeLog("#", "NO RECORD FOUND");
		}finally {
			return flag;
		}
		
	}


	// ======================
	public boolean commonSparesChannelForAll() {
		boolean flag = false;
		String strStatus = null;
		int totalcount = 0;
		int channelcount = 0;
		int totalchannelCountValue = 0;
		String statusValue = null;
		try {
			try {
				WebElement div = oBrowser.findElement(By.xpath("//*[@id='defectMainTableRows']/tr[1]/td[8]/span[1]"));
				for (int i = 1;; i++) {
					EventFiringWebDriver eventFiringWebDriver = new EventFiringWebDriver(oBrowser);
					Object eventObj = eventFiringWebDriver
							.executeScript("document.querySelector('.scrollArea.ng-scope').scrollTop=1000000000");
					if (i == 200) {
						break;
					}
				}
				By totalchannelCount = appInd.createAndGetByObject("Total_Channel_Count");
				totalchannelCountValue = Integer.parseInt(oBrowser.findElement(totalchannelCount).getText());
	/*			for (int row = 1;; row++) {
					try {
						statusValue = oBrowser
								.findElement(By.xpath("//*[@id='defectMainTableRows']/tr[" + row + "]/td[8]/span[1]"))
								.getText();
						System.out.println("statusValue::" +statusValue);
					} catch (Exception e) {
						break;
					}*/
					
				List<WebElement> rows = oBrowser
						.findElements(By.xpath("//*[@id='defectMainTableRows']/tr/td[8]/span[1]"));

				System.out.println("No of rows are : " + rows.size());
				totalcount = rows.size();
				
				
				/*****************************putting value in string to print in excel--Archana************************/
				
				strcount+=Integer.toString(totalchannelCountValue)+"#"+Integer.toString(totalcount);
				/*****************************putting value in string to print in excel--Archana end ************************/
				
			} catch (Exception e) {
				/*****************************putting value in string to print in excel--Archana************************/
				strcount+="0"+"#"+"0";
				/*****************************putting value in string to print in excel--Archana end ************************/
				appInd.writeLog("#", "NO RECORD FOUND");
				System.out.println("********NO RECORD FOUND********");
				//flag = false;
				//return flag;
			}

			if (totalchannelCountValue == totalcount) {
				System.out.println("totalchannelCountValue::" +totalchannelCountValue);
				System.out.println("totalcount::" +totalcount);
				strStatus += true;
				strcount+="#"+"Pass";
			} else {
				strStatus += false;
				strcount+="#"+"Fail";
				appInd.writeLog("Fail", "'sparesChannels' script was failed");
			}
			if (strStatus.contains("false")) {
				flag = false;
			} else {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return flag;
		}
	}

	/********************************
	 * Method Name : commonSparesChannelType Purpose : This method will count the
	 * channels type Author : Vijay Kumar Maurya Reviewer : Date of Creation
	 * :11/28/2018 Date of modification : ******************************
	 */
	public boolean commonSparesChannelType(String strchanneltype) {
		boolean flag = false;
		String strStatus = null;
		int totalCount = 0;
		int channelcount = 0;
		int totalchannelCountValue = 0;
		String statusValue = null;
		try {
			try {
				System.out.println("strchanneltype::" +strchanneltype);
				WebElement div = oBrowser.findElement(By.xpath("//*[@id='defectMainTableRows']/tr[1]/td[6]"));
				
				for (int i = 1;; i++) {
					EventFiringWebDriver eventFiringWebDriver = new EventFiringWebDriver(oBrowser);
					Object eventObj = eventFiringWebDriver
							.executeScript("document.querySelector('.scrollArea.ng-scope').scrollTop=1000000000");
					if (i == 200) {
						break;
					}
				}
				By totalchannelCount = appInd.createAndGetByObject("Total_Channel_Count");
				totalchannelCountValue = Integer.parseInt(oBrowser.findElement(totalchannelCount).getText());
				System.out.println("totalchannelCountValue::" + totalchannelCountValue);
				
				
				//================
				List<WebElement> rows = oBrowser
						.findElements(By.xpath("//*[@id='defectMainTableRows']/tr/td[6]"));

				System.out.println("No of rows are : " + rows.size());
				totalCount = rows.size();
				
				/*****************************putting value in string to print in excel--Archana************************/
				
				strcount+=Integer.toString(totalchannelCountValue)+"#"+Integer.toString(totalCount);
				/*****************************putting value in string to print in excel--Archana end ************************/
				for (WebElement rowElement : rows) {
					if (!rowElement.getText().equalsIgnoreCase(strchanneltype)) {
						strStatus += false;
						break;
					} else {
						System.out.println("rowElement.getText()::" +rowElement.getText());
						channelcount++;
						strStatus += true;
					}

				}

			} catch (Exception e) {
						/*****************************putting value in string to print in excel--Archana************************/
				strcount+="0"+"#"+"0";
				/*****************************putting value in string to print in excel--Archana end ************************/
				appInd.writeLog("#", "NO RECORD FOUND");
				System.out.println("********NO RECORD FOUND********");
				//flag = false;
				//return flag;
			}
		//==========			
			

			if (totalchannelCountValue == channelcount) {
				System.out.println("totalchannelCountValue::"+ totalchannelCountValue);
				System.out.println("channelcount::"+ channelcount);
				strStatus += true;
				strcount+="#"+"Pass";
			} else {
				strStatus += false;
				strcount+="#"+"Fail";
				appInd.writeLog("Fail", "'sparesChannels' script was failed");
			}
			if (strStatus.contains("false")) {
				flag = false;
			} else {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return flag;
		}
	}
	public boolean waitForJSandJQueryToLoad(WebDriver oDriver) {

	    WebDriverWait wait = new WebDriverWait(oDriver, 30);

	    // wait for jQuery to load
	    ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
	      @Override
	      public Boolean apply(WebDriver oDriver) {
	        try {
	          return ((Long)((JavascriptExecutor)oDriver).executeScript("return jQuery.active") == 0);
	        }
	        catch (Exception e) {
	          // no jQuery present
	          return true;
	        }
	      }
	    };

	    // wait for Javascript to load
	    ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
	      @Override
	      public Boolean apply(WebDriver driver) {
	        return ((JavascriptExecutor)oDriver).executeScript("return document.readyState")
	        .toString().equals("complete");
	      }
	    };

	  return wait.until(jQueryLoad) && wait.until(jsLoad);
	}
	
	public boolean navigateToReportsModule(WebDriver obrowser) 
	{
		
		boolean bolstatus = false;
		String strpagetittle=null;
		
		try {
			
				//check the page title name 
				strpagetittle =obrowser.findElement(appInd.createAndGetByObject("Pagetittle")).getText();
				
				if (!(strpagetittle.equalsIgnoreCase("Reports")))
				{
					//click on the menu icon
					By byClickOnMenuBar = appInd.createAndGetByObject("Menu_Bar");
					obrowser.findElement(byClickOnMenuBar).sendKeys(Keys.ENTER);
					Thread.sleep(250);
								
					//click on the Reports
					obrowser.findElement(By.linkText("Reports")).sendKeys(Keys.ENTER);
					Thread.sleep(10000);
					bolstatus = true;
				}
			}
		catch(Exception e) {
			appInd.writeLog("Exception","Exception while executing 'navigateToReportsModule' method. " + e.getMessage());
			bolstatus = false;
			}
		finally {
		 	return bolstatus;
			}	
	}
	
	public boolean exportReportAs(WebDriver obrowser,String FileType, String reportName, String waitTimeInSecond) {
		boolean status = false;
		try {
//			//List<WebElement> elements = obrowser.findElements(By.xpath("//a[contains(text(),'"+oinpuTDtMap.get("Param_1")+"')]/ancestor::div[@class='title']/following-sibling::div[@class='reports']/descendant::a[@id='start-collection']"));
//			boolean flag2 = appInd.ifElementsPresent(obrowser, "Report_Name_List");
//			if(flag2==true) {
//				By Report_Name_List = appInd.createAndGetByObject("Report_Name_List");
//				List<WebElement> report_Name_List = obrowser.findElements(Report_Name_List);
//				for(int i=0;i<report_Name_List.size();i++) {
//					if(report_Name_List.get(i).getAttribute("id").contains(reportName)) {
//					    ((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",report_Name_List.get(i));
//					    status = true;
//						appInd.writeLog("pass", "Clicked on "+reportName);
//						Thread.sleep(20000);
//						break;
//					}
//					
//				}
//				if(status==false) {
//					appInd.writeLog("fail", reportName+" Report is not Present");
//					//appInd.takeSnapShot(obrowser, System.getProperty("user.dir")+ "\\Results\\snapshot\\Reporting_Failed_Snapshot\\ExportReportAs"+reportSaveAsFileName+".png");
//				}
//			}
//			
			boolean flag3 = appInd.switchToWindow(1);
			
			if(flag3==true) {
				boolean flag = appInd.ifElementsPresent(obrowser, "Report_Export_Icon");
				if(flag==true) {
					
					By Report_Export_Icon = appInd.createAndGetByObject("Report_Export_Icon");
					WebElement report_Export_Icon = obrowser.findElement(Report_Export_Icon);
					((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",report_Export_Icon);
					appInd.writeLog("pass", "Clicked on Report Export Icon");
					Thread.sleep(4000);
					
					boolean flag1 = appInd.ifElementsPresent(obrowser, "SaveAsFile_List");
					if(flag1==true) {
						By SaveAsFile_List = appInd.createAndGetByObject("SaveAsFile_List");
						List<WebElement> saveAsFile_List = obrowser.findElements(SaveAsFile_List);
						for(int i=0;i<saveAsFile_List.size();i++) {
							if(saveAsFile_List.get(i).getText().contains(FileType)) {
							    ((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",saveAsFile_List.get(i));
							    status = true;
								appInd.writeLog("pass", "Clicked on "+FileType);
								long time = Long.parseLong(waitTimeInSecond);
								long newTime = time*1000;
								Thread.sleep(newTime);
								break;
							}
							
						}
						if(status==false) {
							appInd.writeLog("fail", "Report Export File Name is not present ");
							//appInd.takeSnapShot(obrowser, System.getProperty("user.dir")+ "\\Results\\snapshot\\Reporting_Failed_Snapshot\\ExportReportAs"+reportSaveAsFileName+".png");
						}
					}else {
						status=false;
						appInd.writeLog("fail", "Report Export File Name List are not displayed ");
						//appInd.takeSnapShot(obrowser, System.getProperty("user.dir")+ "\\Results\\snapshot\\Reporting_Failed_Snapshot\\ExportReportAs"+reportSaveAsFileName+".png");
					}
					
				}else {
					status=false;
					appInd.writeLog("fail", "Report Export Icon is not displayed ");
					//appInd.takeSnapShot(obrowser, System.getProperty("user.dir")+ "\\Results\\snapshot\\Reporting_Failed_Snapshot\\ExportReportAs"+reportSaveAsFileName+".png");
				}
			}else {
				status=false;
				appInd.writeLog("fail", "unable to switch to next window ");
				//appInd.takeSnapShot(obrowser, System.getProperty("user.dir")+ "\\Results\\snapshot\\Reporting_Failed_Snapshot\\ExportReportAs"+reportSaveAsFileName+".png");
			}
	
			
		}catch(Exception e) {
			appInd.writeLog("Exception","Exception while executing 'exportReportAs' method. " + e.getMessage());
			status = false;
		}finally {
			return status;
		}
		
		
	}
	 
	public boolean deleteReports(WebDriver obrowser,String reportPackageName, String noOfReportsTobeDeleted, String singleOrMultiple) {
		boolean status = false;
		try {
			By Report_PackageName_List = appInd.createAndGetByObject("Report_PackageName_List");
			List<WebElement> report_PackageName_List = obrowser.findElements(Report_PackageName_List);
			for(int i= 0;i<report_PackageName_List.size();i++) {
				report_PackageName_List = obrowser.findElements(Report_PackageName_List);
				if(report_PackageName_List.get(i).getAttribute("id").equalsIgnoreCase(reportPackageName)) {
					//System.out.println("============");
					status = true;
					break;
				}else {
					status = false;
					((JavascriptExecutor) obrowser).executeScript("arguments[0].scrollIntoView(true);",report_PackageName_List.get(i));
				}
			}
			if(status==false) {
				status=false;
				System.out.println(reportPackageName+" package name is not present");
				appInd.writeLog("fail", reportPackageName+" package name is not present");
				appInd.takeSnapShot(obrowser, System.getProperty("user.dir")+ "\\Results\\snapshot\\Reporting_Failed_Snapshot\\Delete"+singleOrMultiple+"Report.png");
			}
			boolean eleStatus = appInd.isElementsPresent(obrowser, "//a[@id='"+reportPackageName+"']/ancestor::div[@class='title']/following-sibling::div[@class='reports']/descendant::a[@class='view-all']");
			if(eleStatus==true) {
				WebElement view_All_Button = obrowser.findElement(By.xpath("//a[@id='"+reportPackageName+"']/ancestor::div[@class='title']/following-sibling::div[@class='reports']/descendant::a[@class='view-all']"));
				((JavascriptExecutor) obrowser).executeScript("arguments[0].scrollIntoView(true);",view_All_Button);
				Thread.sleep(5000);
				((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",view_All_Button);
				status=true;
				System.out.println("Clicked on View All Button");
				appInd.writeLog("pass", "Clicked on View All Button");
			}else {
				status=false;
				System.out.println("unable to click on view-all button");
				appInd.writeLog("fail", "unable to click on view-all button");
				appInd.takeSnapShot(obrowser, System.getProperty("user.dir")+ "\\Results\\snapshot\\Reporting_Failed_Snapshot\\Delete"+singleOrMultiple+"Report.png");
			}
			List<WebElement> report_Checkbox_List = obrowser.findElements(By.xpath("//a[@id='"+reportPackageName+"']/ancestor::div[@class='title']/following-sibling::div[@class='reports']/div[@id='collapseButtonId' and contains(@style,'display: block')]/preceding-sibling::div[@class='list']/descendant::span[contains(@class,'checkbox')]"));
			int report_Checkbox_List_oldSize = report_Checkbox_List.size();
			int size = Integer.parseInt(noOfReportsTobeDeleted);
		    for(int i=0;i<size;i++) {
		    	if(report_Checkbox_List.size()>=size) {
		    		((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",report_Checkbox_List.get(i));
			    	status=true;
			    	System.out.println("Clicked on Report checkbox");
			    	appInd.writeLog("pass", "Clicked on Report checkbox");
		    	}else {
		    		status=false;
		    		System.out.println(noOfReportsTobeDeleted+" no of reports are not present in the package");
		    		appInd.writeLog("fail", noOfReportsTobeDeleted+" no of reports are not present in the package");
		    		appInd.takeSnapShot(obrowser, System.getProperty("user.dir")+ "\\Results\\snapshot\\Reporting_Failed_Snapshot\\Delete"+singleOrMultiple+"Report.png");
		    	}
		    	
		    }
		    boolean delBtnStatus = appInd.isElementsPresent(obrowser, "//div[@id='collapseButtonId' and contains(@style,'display: block')]/ancestor::div[@class='reports']/preceding-sibling::div[@class='title']/a[@id='"+reportPackageName+"']/following-sibling::a[text()='Delete Report']");
		    if(delBtnStatus==true) {
				WebElement delete_Button = obrowser.findElement(By.xpath("//div[@id='collapseButtonId' and contains(@style,'display: block')]/ancestor::div[@class='reports']/preceding-sibling::div[@class='title']/a[@id='"+reportPackageName+"']/following-sibling::a[text()='Delete Report']"));
				((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",delete_Button);
				status=true;
				System.out.println("Clicked on Delete Report Button");
				appInd.writeLog("pass", "Clicked on Delete Report Button");
			}else {
				status=false;
				System.out.println("unable to click on Delete Report button");
				appInd.writeLog("fail", "unable to click on Delete Reportbutton");
				appInd.takeSnapShot(obrowser, System.getProperty("user.dir")+ "\\Results\\snapshot\\Reporting_Failed_Snapshot\\Delete"+singleOrMultiple+"Report.png");
			}
		    boolean flag = appInd.ifElementsPresent(obrowser, "Report_Delete_Yes_Button");
		    boolean flag1 = appInd.ifElementsPresent(obrowser, "Popup_Ok_Button");		
			//System.out.println(flag);
			if(flag==true) {
				System.out.println("Report_Delete_Yes_Buttons is displayed");
				status=true;
				appInd.writeLog("pass", "Report_Delete_Yes_Buttons is displayed");
																		
				By Report_Delete_Yes_Button = appInd.createAndGetByObject("Report_Delete_Yes_Button");
				WebElement report_Delete_Yes_Button = obrowser.findElement(Report_Delete_Yes_Button);
				((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",report_Delete_Yes_Button);
				
				System.out.println("clicked on delete Report YES button");
				Thread.sleep(5000);
				
				report_Checkbox_List = obrowser.findElements(By.xpath("//a[@id='"+reportPackageName+"']/ancestor::div[@class='title']/following-sibling::div[@class='reports']/div[@id='collapseButtonId' and contains(@style,'display: block')]/preceding-sibling::div[@class='list']/descendant::span[contains(@class,'checkbox')]"));
				int report_Checkbox_List_newSize = report_Checkbox_List.size();
				if(report_Checkbox_List_newSize==report_Checkbox_List_oldSize-size) {
					status=true;
					System.out.println("Reports are deleted successfully");
					appInd.writeLog("pass", "Reports are deleted successfully");
				}else {
					System.out.println("Reports are not deleted successfully");
					status=false;
					appInd.writeLog("fail", "Reports are not deleted successfully");
				}
			}else {
				if(flag1==true) {
					status = false;
					appInd.writeLog("fail", "Reports are not deleted successfully");
					appInd.takeSnapShot(obrowser, System.getProperty("user.dir")+ "\\Results\\snapshot\\Reporting_Failed_Snapshot\\Delete"+singleOrMultiple+"Report.png");
					
					By Popup_Ok_Button = appInd.createAndGetByObject("Popup_Ok_Button");
					WebElement popup_Ok_Button = obrowser.findElement(Popup_Ok_Button);
					((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",popup_Ok_Button);
				}else {
					System.out.println("Reports are not deleted successfully");
					status=false;
					appInd.writeLog("fail", "Reports are not deleted successfully");
					appInd.takeSnapShot(obrowser, System.getProperty("user.dir")+ "\\Results\\snapshot\\Reporting_Failed_Snapshot\\Delete"+singleOrMultiple+"Report.png");
				}
				
			}
		}catch(Exception e) {
			appInd.writeLog("Exception","Exception while executing 'deleteReports' method. " + e.getMessage());
			status = false;
		}finally {
			return status;
		}
		
		
	}
	
	public boolean scheduleReport(WebDriver obrowser,String reportPackageName) {
		boolean status = false;
		try {
			By Report_PackageName_List = appInd.createAndGetByObject("Report_PackageName_List");
			List<WebElement> report_PackageName_List = obrowser.findElements(Report_PackageName_List);
			if(report_PackageName_List.size()>0) {
				for(int i=0;i<report_PackageName_List.size();i++) {
					if(report_PackageName_List.get(i).getAttribute("id").equalsIgnoreCase(reportPackageName)) {
						((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",report_PackageName_List.get(i));
						boolean result=	appInd.waitFor(obrowser, "PageLoading_Icon", "invisible", "",600);
						System.out.println("Clicked on "+report_PackageName_List.get(i).getAttribute("id")+" report package");
						appInd.writeLog("pass", "Clicked on "+report_PackageName_List.get(i).getAttribute("id")+" report package");
						status = true;
						Thread.sleep(9000);
						break;
					}
					status = false;
				}
//				if(status == false) {
//					status = false;
//					System.out.println("unable to Click on "+report_PackageName_List.get(i).getAttribute("id")+" report package");
//					appInd.writeLog("pass", "unable to Click on "+report_PackageName_List.get(i).getAttribute("id")+" report package");
//				}
			}
			
			By Schedule_CheckBox = appInd.createAndGetByObject("Schedule_CheckBox");
			WebElement schedule_CheckBox = obrowser.findElement(Schedule_CheckBox);
			
			if(schedule_CheckBox.getAttribute("class").contains("unselect")) {
				System.out.println("Checkbox is not selected");
				((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",schedule_CheckBox);
				System.out.println("CheckBox is selected");
			}else {
				System.out.println("CheckBox is selected");
				
			}						
			
			boolean flag = appInd.ifElementsPresent(obrowser, "Schedule_Tab");
			if(flag==true) {
				By Schedule_Tab = appInd.createAndGetByObject("Schedule_Tab");
				WebElement schedule_Tab = obrowser.findElement(Schedule_Tab);
				((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",schedule_Tab);
				System.out.println("Clicked on Schedule Tab");
				status = true;
				appInd.writeLog("pass", "Clicked on Schedule Tab");
			}else {
				status = false;
				System.out.println("Unable to click on Schedule Tab");
				appInd.writeLog("pass", "Unable to Click on Schedule Tab");
			}
			
			boolean flag1 = appInd.ifElementsPresent(obrowser, "Schedule_StartDate_Button");
			if(flag1==true) {
				By Schedule_StartDate_Button = appInd.createAndGetByObject("Schedule_StartDate_Button");
				WebElement schedule_StartDate_Button = obrowser.findElement(Schedule_StartDate_Button);
				((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",schedule_StartDate_Button);
				System.out.println("Clicked on schedule_StartDate_Button");
				status = true;
				appInd.writeLog("pass", "Clicked on schedule_StartDate_Button");
			}else {
				status = false;
				System.out.println("Unable to click on schedule_StartDate_Button");
				appInd.writeLog("pass", "Unable to Click on schedule_StartDate_Button");
			}
			
		}catch(Exception e) {
			appInd.writeLog("Exception","Exception while executing 'scheduleReport' method. " + e.getMessage());
			status = false;
		}finally {
			return status;
		}
		
		
	}


	public String getMonth(WebDriver obrowser, String reportPackageName) {
				
		List<WebElement> report_Name_List = obrowser.findElements(By.xpath("//a[@id='"+reportPackageName+"']/ancestor::div[@class='title']/following-sibling::div[@class='reports']/descendant::div[@class='list']/descendant::a"));
		String str = report_Name_List.get(0).getText();
		String[] reportStr = str.split(" ");
		if(report_Name_List.size()>0) {
			str = report_Name_List.get(0).getText();
			reportStr = str.split(" ");
			for(int i=0;i<reportStr.length;i++) {
				System.out.println(reportStr[i]);
			}
			
		}
		
		if(reportStr[2].equalsIgnoreCase("jan")) {
			return "01";
		}else if(reportStr[2].equalsIgnoreCase("feb")) {
			return "02";
		}else if(reportStr[2].equalsIgnoreCase("mar")) {
			return "03";
		}else if(reportStr[2].equalsIgnoreCase("apr")) {
			return "04";
		}else if(reportStr[2].equalsIgnoreCase("may")) {
			return "05";
		}else if(reportStr[2].equalsIgnoreCase("jun")) {
			return "06";
		}else if(reportStr[2].equalsIgnoreCase("jul")) {
			return "07";
		}else if(reportStr[2].equalsIgnoreCase("aug")) {
			return "08";
		}else if(reportStr[2].equalsIgnoreCase("sep")) {
			return "09";
		}else if(reportStr[2].equalsIgnoreCase("oct")) {
			return "10";
		}else if(reportStr[2].equalsIgnoreCase("nov")) {
			return "11";
		}else if(reportStr[2].equalsIgnoreCase("dec")) {
			return "12";
		}
		return reportStr[2];
		
	}



	/********************************
	 * Method Name : menu_EngeeringAnomoly_Navigation 
	 * Purpose : This method will launch the Engineering Anomoly page
	 * Author : Mahesh TK 
	 * Reviewer : 
	 * Date of Creation : 23-Jan-19
	 * Date of modification :
	 * ******************************
	 */
	public boolean menu_EngeeringAnomoly_Navigation(WebDriver obrowser) 
	{
		// System.out.println("menu_EngeeringAnomoly_Navigation:- started" ); //Self testcode review
		boolean bolstatus = false;
		String strpagetittle=null;
		
		try {
			
				//check the page title name 
				strpagetittle =obrowser.findElement(appInd.createAndGetByObject("Pagetittle")).getText();
				
				if (!(strpagetittle.equalsIgnoreCase("Engineering Anomaly")))
				{
					//click on the menu icon
					By byClickOnMenuBar = appInd.createAndGetByObject("Menu_Bar");
					obrowser.findElement(byClickOnMenuBar).sendKeys(Keys.ENTER);
					Thread.sleep(250);
								
					//click on the Engineering Anomaly
					obrowser.findElement(By.linkText("Engineering Anomaly")).sendKeys(Keys.ENTER);
					Thread.sleep(10000);
					
				}
				bolstatus = true;
			}
		catch(Exception e) {
			appInd.writeLog("Exception","Exception while executing 'menu_EngeeringAnomoly_Navigation' method. " + e.getMessage());
			bolstatus = false;
			}
		finally {
		 	return bolstatus;
			}	
	}
	

	/********************************
	 * Method Name : webTable_Operation 
	 * Purpose : This method will perform the operation on the webtable 
	 * Author : Mahesh TK 
	 * Reviewer : 
	 * Date of Creation : 23-Jan-19
	 * Date of modification :
	 * ******************************
	 */
	@SuppressWarnings("finally")
	public String webTable_Operation(WebDriver obrowser,String strtableLogicalName,String stroperation,int rownum ,int columnnum, String strvalue ) 
	{
		// System.out.println("webTable_Operation:- started" ); //Self testcode review
		boolean bolget_innertextstatus = false;
		boolean bolsetstatus = false;
		boolean bolclickstatus = false;
		boolean bolget_attributeValuestatus = false;
		int nrowcount = 0;
		int ncolumncount = 0;
		String strretunvalue = null;
		
		try {
			
				//get the webtable element 
				WebElement webtableelement=obrowser.findElement(appInd.createAndGetByObject(strtableLogicalName));
				
				if (webtableelement != null) 
				{
					// get webtable row count
					List<WebElement> rows=webtableelement.findElements(By.tagName("tr"));
					nrowcount=rows.size();  //including Headers
					
					if (nrowcount>0) {
						//get the webtable column count
						List<WebElement> columns=rows.get(1).findElements(By.tagName("td"));
						ncolumncount=columns.size();
						
						//iterate the webtable based on the stroperation
						switch(stroperation.toLowerCase())
						 {    
						    case "get_rowcount": 
						    	strretunvalue= String.valueOf(nrowcount) ;
						    	break; 
						    	
						    case "get_columncount": 
						    	strretunvalue= String.valueOf(ncolumncount) ;
						    	break;   
						    	
						    case "get_innertext": 
						    	bolget_innertextstatus = true ;
						    	break;    
						    	
						    case "get_attributevalues": 
						    	bolget_attributeValuestatus = true ;
						    	break; 
						    	
						    case "set_value": 
						    	bolsetstatus = true; 
						    	break;      
						    	
						    case "click": 
						    	bolclickstatus = true;
						    	break;  
						 }
						
						//return the value based on the stroperation 
						
						if (bolget_innertextstatus)
						{
							columns=rows.get(rownum-1).findElements(By.tagName("td"));
							strretunvalue = columns.get(columnnum-1).getText();
							
						}
						else if (bolsetstatus)
						{
							columns=rows.get(rownum-1).findElements(By.tagName("input"));
							columns.get(columnnum-1).sendKeys(strvalue);
							strretunvalue ="true" ;
						}
						else if (bolget_attributeValuestatus)
						{
							columns=rows.get(rownum-1).findElements(By.tagName("span"));							
							strretunvalue=columns.get(columnnum-1).getAttribute(strvalue);											
						}
						else if(bolclickstatus)
						{
							columns=rows.get(rownum-1).findElements(By.tagName("input"));	
							WebElement elechkbx = columns.get(columnnum-1);						 
							 if (elechkbx != null) 
							 {
								 // checking for the checkbox status
								 if (! (elechkbx.isSelected()))
								 {
									 ((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",elechkbx);
								 }
								 strretunvalue ="true" ;
							 }						
						}			
					}
				}					
			
			}
		catch(Exception e) 
		{
			appInd.writeLog("Exception","Exception while executing 'webTable_Operation' method. " + e.getMessage());
			strretunvalue =null ;					
		}
		finally 
		{
		 	return strretunvalue;
		}	
	}
	
	
	/********************************
	 * Method Name : Engr_anomoly_filters 
	 * Purpose : This method will launch the Engineering Anomoly page
	 * Author : Mahesh TK 
	 * Reviewer : 
	 * Date of Creation : 23-Jan-19
	 * Date of modification :
	 * ******************************/


	public void Engr_default_filters(WebDriver obrowser) 
	{
		String strStatus=null;
		// System.out.println("menu_EngeeringAnomoly_Navigation:- started" ); //Self testcode review		
		try {
				
								
					//click on the Filter icon  			
					strStatus += appInd.clickObject_js(obrowser, "EngrAnomoly_Icon_Filter");
					Thread.sleep(250);
					
					//click on the Reset button to default 
					strStatus += appInd.clickObject_js(obrowser, "EngrAnomoly_button_Reset");
					
					
					// click on the Filter button
						strStatus += appInd.clickObject_js(obrowser, "EngrAnomoly_button_Filter");
						

						delay(obrowser,"ajax_loading");
			}catch(Exception e) {
				appInd.writeLog("Exception","Exception while executing 'Engr_default_filters' method. " + e.getMessage());
				
				}

	}
	
	
	/********************************
	 * Method Name : Engr_anomoly_filters 
	 * Purpose : This method will launch the Engineering Anomoly page
	 * Author : Mahesh TK 
	 * Reviewer : 
	 * Date of Creation : 23-Jan-19
	 * Date of modification :
	 * ******************************/


	public boolean Engr_anomoly_filters(WebDriver obrowser,String strCheckboxName,String strAnomalyName) 
	{
		// System.out.println("menu_EngeeringAnomoly_Navigation:- started" ); //Self testcode review
		boolean bolstatus = false;
		String strLogicalName=null;
		int nrowcount = 0;
		int ncolumncount = 0;
		String strStatus = null;
		int ntotrowcount=0;
		
		
		
		try {
				
								
					//click on the Filter icon  			
					strStatus += appInd.clickObject_js(obrowser, "EngrAnomoly_Icon_Filter");
					Thread.sleep(250);
					
					//click on the Reset button to default 
					strStatus += appInd.clickObject_js(obrowser, "EngrAnomoly_button_Reset");
					Thread.sleep(200);
					
					//Select the Anomoly name in the table 				
						//get the rowcount of webtable
						nrowcount =Integer.parseInt( webTable_Operation(obrowser,"EngrAnomoly_Table_filterAnomoly","get_rowcount",0 ,0," "));
						
						//get the column count of webtable
						ncolumncount =Integer.parseInt( webTable_Operation(obrowser,"EngrAnomoly_Table_filterAnomoly","get_columncount",0 ,0," "));
						
						
						if(nrowcount>0) 
						{
							//anaomaly Setting table operation 
							switch(strAnomalyName.toLowerCase())
							 {  			    							    	
							    case "default": 
							    	ntotrowcount=1 ;
							    	break;   							    	
							    	
							    case "all": 
							    	ntotrowcount = nrowcount ;
							    	break; 
							    	
							    case "none": 
							    	ntotrowcount = -1 ;
							    	break; 
							    	 
							    default:
							    	ntotrowcount = nrowcount ;
							    	    
						      } 
						
							for(int nrowindex=1; nrowindex <= ntotrowcount;nrowindex ++ ) 
							{
								
								if((strAnomalyName.equalsIgnoreCase("default")) ||(strAnomalyName.equalsIgnoreCase("all"))) 
								{
									webTable_Operation(obrowser,"EngrAnomoly_Table_filterAnomoly","click",nrowindex ,1," ");
								}else 
								{
								
									String stractualanomaly =webTable_Operation(obrowser,"EngrAnomoly_Table_filterAnomoly","get_innertext",nrowindex ,1," ");
									if(stractualanomaly.equalsIgnoreCase(strAnomalyName))
									{
										webTable_Operation(obrowser,"EngrAnomoly_Table_filterAnomoly","click",nrowindex ,1," ");
										break;
									}
								}
							}			
									
					    }
						
					
						
						
					//Perform the checkbox operation on provided checkbox name 	
						
					//split the strChkbxName if multiple name provied  
						String strChkbxName[]=strCheckboxName.split(";");
						for (String  strName : strChkbxName) 
						{ 
						    if (strName != null) 
						    {
									switch(strName.toLowerCase())
									 {    
									    case "acknowledged": 
									    	strLogicalName= "EngrAnomoly_Chkbx_Acknowledged" ;
									    	break; 
									    	
									    case "unacknowledged": 
									    	strLogicalName= "EngrAnomoly_Chkbx_Unacknowledged" ;
									    	break;   
									    	
									    case "assigned": 
									    	strLogicalName= "EngrAnomoly_Chkbx_Assigned" ;
									    	break;    
									    	
									    case "unassigned": 
									    	strLogicalName= "EngrAnomoly_Chkbx_UnAssigned" ; 
									    	break;      
									    	
									    case "resolved": 
									    	strLogicalName= "EngrAnomoly_Chkbx_Resolved";
									    	break;      
									    	
									    case "suppressed": 
									    	strLogicalName= "EngrAnomoly_Chkbx_Suppressed";
									    	break;    
									    	
									    case "unsuppressed": 
									    	strLogicalName= "EngrAnomoly_Chkbx_UnSuppressed";
									    	break;    
									    	
									    case "high": 
									    	strLogicalName= "EngrAnomoly_Chkbx_High" ;
									    	break;  
									    	
									    case "medium": 
									    	strLogicalName= "EngrAnomoly_Chkbx_Medium";
									    	break;    
									    	
									    case "low": 
									    	strLogicalName= "EngrAnomoly_Chkbx_Low";
									    	break;		
									    case "anomalyname": 
									    	//by default filtering by first anomaly name 
									    	break;
									    	
									    default:
									    	System.out.println("Not Found");    
									    }    
									
									//click on the checkbox based on the status
									 if (strLogicalName != null) 
									 {
										 // Getting the check box webelemnt 
										 WebElement elechkbx = obrowser.findElement(appInd.createAndGetByObject(strLogicalName));
										 
										 if (elechkbx != null) 
										 {
											 // checking for the checkbox status
											 if (! (elechkbx.isSelected()))
											 {
												 ((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",elechkbx);
												 Thread.sleep(200);
											 }
										 }
										 
									  }
						    }// null check condition	 
						} //for loop split checkbox name 	
						
						
					// click on the Filter button
						strStatus += appInd.clickObject_js(obrowser, "EngrAnomoly_button_Filter");
						
						
						
					// validating the result	
						if (!strStatus.contains("false")){
							bolstatus = true;
						} 
						
			}
		catch(Exception e) {
			appInd.writeLog("Exception","Exception while executing 'Engr_anomoly_filters' method. " + e.getMessage());
			bolstatus = false;
			}
		finally {
		 	return bolstatus;
			}
	}

	/********************************
	 * Method Name : EngeeringAnomoly_filtervalidation 
	 * Purpose : This method will vallidate the upon performing the filer in filer popup
	 * Author : Mahesh TK 
	 * Reviewer : 
	 * Date of Creation : 24-Jan-19
	 * Date of modification :
	 * ******************************
	 */
	public boolean EngeeringAnomoly_filtervalidation(WebDriver obrowser,String strvalidationType,int nColumnnum, String strExceptedvalue,String strvalue) 
	{
		// System.out.println("EngeeringAnomoly_filtervalidation:- started" ); //Self testcode review
		boolean bolstatus = false;		
		int nrowcount = 0;		
		String strresStatus = null;
		String strActualvalue =null;		
		try {		
			
			// checking for the grid table exists
			WebElement eleSR = obrowser.findElement(appInd.createAndGetByObject("EngrAnomoly_Label_showresult"));
			if (eleSR!=null) 
			{					
					//get the rowcount of grid  exculding header 
					nrowcount =Integer.parseInt( MainGrid_Operation(obrowser,"get_rowcount",0 ,0," "));
					appInd.writeLog("#","Rowcount: " +nrowcount );
										
					for(int nrowindex=1; nrowindex < nrowcount+1;nrowindex ++ ) //note index started from the 1 because 0 row is header
					{
						strActualvalue= MainGrid_Operation(obrowser,strvalidationType,nrowindex ,10,strvalue);
						System.out.println("Actual value: "+strActualvalue+" Expected value: "+strExceptedvalue + " :-> row id ->:" +nrowindex);
						if (strActualvalue != null)
						{
							if(strExceptedvalue.contains("nonhyphen"))  // checking for non hyphen values (in assigned anomaly vallidation)
							{
								if (!(strActualvalue.equalsIgnoreCase("-"))) strresStatus += "pass";
								else strresStatus += "fail";								
							}
							else if(strExceptedvalue.equalsIgnoreCase("contains"))  // checking for high/medium/low values (in high/medium/low anomaly vallidation)
							{
								if ((strActualvalue.toLowerCase()).contains(strvalue)) strresStatus += "pass";
								else strresStatus += "fail";								
							}
							else if(strExceptedvalue.contains("acknowledged")) //vallidation for acknowledged, unacknowledged and ressolved anomaly
							{
								String spltarr[] =strActualvalue.split("/"); //validating the link(image name)
								if(strExceptedvalue.equalsIgnoreCase("acknowledged")) 
								{
									
									if(spltarr[(spltarr.length-1)].equalsIgnoreCase("i_Acknowledged.svg")) strresStatus += "pass";
									else strresStatus += "fail";
								}
								else if(strExceptedvalue.equalsIgnoreCase("unacknowledged")) 
								{
									
									if(spltarr[(spltarr.length-1)].equalsIgnoreCase("i_Unacknowledged.svg")) strresStatus += "pass";
									else strresStatus += "fail";
								}
							}
							
							else {  // checking for hyphen values (in unassigned anomaly vallidation)
									if ((strActualvalue.toLowerCase()).contentEquals((strExceptedvalue.toLowerCase())))  strresStatus += "pass";
									else strresStatus += "fail";	
							}
							
						}else 
						{
							if(strExceptedvalue.toLowerCase().contains("nullcheck")) strresStatus += "pass";								
							else strresStatus += "fail";
						}					
					}
					
					
					// validating the result	
					if (!(strresStatus.contains("fail")))
					{
						bolstatus = true;				
					}	
					
				}
			}
		catch(Exception e) {
			if (appInd.isElementPresent(obrowser, "EngrAnomoly_Tablehide_String")) 
			{
				bolstatus =true;
				appInd.writeLog("#","Grid Table is displayed but 'no anaomly' displayed message in Engineering anomaly page");							
				
			}else {
			appInd.writeLog("Exception","Exception while executing 'EngeeringAnomoly_filtervalidation' method. " + e.getMessage());
			bolstatus = false;	}		
			}
		finally {
		 	return bolstatus;
			}	
	}

	/********************************
	 * Method Name : MainGrid_Operation 
	 * Purpose : This method will perform the operation on the MainGrid_Operation 
	 * Author : Mahesh TK 
	 * Reviewer : 
	 * Date of Creation : 29-Jan-19
	 * Date of modification :
	 * E.g. 1.Operation(stroperation) :'get_attributevalues' 
	 *  				WebDriver <obrowser>,String <'get_attributevalues'>,int <respective row>
	 *  					 ,int <respective column>, String <tagname:attributteName> Note:row number starts from header i.e row =0 for header
	 *  	2.Operation(stroperation) :'click' in Engineering anomaly page checkbox lies in 1st column for this to check the check box in particular row send column as '1' 
	 *  				WebDriver <obrowser>,String <'click'>,int <respective row>
	 *  					 ,int <0>, String <""> 
	 *  	3.Operation(stroperation) :'get_rowcount' Note it returns rowcount exculding header.
	 *  				WebDriver <obrowser>,String <'click'>,int <0>
	 *  					 ,int <0>, String <""> 
	 * columnsname-> Column num: checkbox->1, A/U->2, Anomaly name->3, Suppression->4, AnomalyType->5, Description->6, Anomaly idetified->7, Assigned to->8,
	 *  							  Work Order->9, Comments->10
	 * ******************************
	 */
	@SuppressWarnings("finally")
	public String MainGrid_Operation(WebDriver obrowser,String stroperation,int rownum ,int columnnum, String strvalue ) 
	{
		// System.out.println("webTable_Operation:- started" ); //Self testcode review
		boolean bolget_innertextstatus = false;		
		boolean bolclickstatus = false;
		boolean bolget_attributeValuestatus = false;
		int ntotalrecords =0;
		int ntabrow=0;
		String strretunvalue = null;
		WebElement eletemp =null;
		String strresults =null;
		
		
		try {
			// checking for the grid table exists
			WebElement ele = obrowser.findElement(appInd.createAndGetByObject("EngrAnomoly_Label_showresult"));
		if (ele!=null) 
		{		
			strresults =ele.getText();
			
			//Extract the total records
			if(strresults!=null) {
			String strsplitarr[] =strresults.split("/");
			ntotalrecords = Integer.parseInt(strsplitarr[1]);					
			}
				//get the mainGridtable element 
				WebElement webtableelement=obrowser.findElement(appInd.createAndGetByObject("EngrAnomoly_Table_MainGrid"));
								
				if (webtableelement != null) 
				{				
					//iterate the mainGridtable based on the stroperation
						switch(stroperation.toLowerCase())
						 {    
						    case "get_rowcount":
						    	//get the row count based on the checkbox															
						    	strretunvalue= String.valueOf(ntotalrecords) ;
						    	break; 
						    	
						    case "get_columncount": 
						    	 //get the column count based onthe  first row					
								List<WebElement> eletable = obrowser.findElements(By.xpath("//*[contains(@id,'"+String.valueOf(0)+"-uiGrid-')]"));								 
						    	strretunvalue= String.valueOf(eletable.size()) ;
						    	break;   
						    	
						    case "get_innertext": 
						    	bolget_innertextstatus = true ;
						    	break;    
						    	
						    case "get_attributevalues": 
						    	bolget_attributeValuestatus = true ;
						    	break; 						    	
						   						    	
						    case "click": 
						    	bolclickstatus = true;
						    	break;  
						 }
						
						if (!((stroperation.contentEquals("get_rowcount")) || (stroperation.contentEquals("get_columncount"))))
						{
							
							
							if ((rownum > 15) && (rownum < ntotalrecords ))
								
							{
								int cntmoduls =rownum % 15;	
								
								 //load the elemnts into the grid     
								if (cntmoduls == 1 || cntmoduls ==6|| cntmoduls==11) {					
									
									String temp="10";
									 WebElement Element = obrowser.findElement(By.xpath("(//*[contains(@id,'"+String.valueOf(temp)+"-uiGrid-')]/div)[1]"));

								     //This will scroll the page till the element is found			        
									 JavascriptExecutor js = (JavascriptExecutor) obrowser;
									 js.executeScript("arguments[0].scrollIntoView();", Element);
									 if (ntotalrecords>100) {delay(obrowser,"ajax_loading");}
									
								} 
									//assign the new counter pushing 5 records default
																	
									ntabrow =(rownum)-(rownum % 15);
									if (ntabrow>15)
									{
										ntabrow=0;
									}
									System.out.println("rowID:--"+rownum + "New value:--" +ntabrow  );							
							}else
							{
								ntabrow =rownum;
							}
							
							//get the row element 0th row not present
							if (ntabrow !=0) {
							String strxpath ="(//*[contains(@id,'-"+String.valueOf(ntabrow-1)+"-uiGrid-')])["+columnnum+"]";
							 eletemp = obrowser.findElement(By.xpath(strxpath));		
							}				
						
						//return the value based on the stroperation 
						
						if (bolget_innertextstatus)
						{
							//strretunvalue = rowele.get(columnnum-1).getText(); eletemp
							strretunvalue = eletemp.getText(); 
						}
						
						else if (bolget_attributeValuestatus)
						{
							String strspltarr[] =strvalue.split(":");														
							WebElement spanele =eletemp.findElement(By.tagName(strspltarr[0]));
							strretunvalue=spanele.getAttribute(strspltarr[1]);											
						}
						else if(bolclickstatus) 
						{
							List<WebElement> rowchkele = obrowser.findElements(By.xpath("//*[@role='checkbox']"));  
                                            //WebElement echkbx =eletemp.findElement(By.xpath("//*[@role=\"checkbox\"]"));                                 
                                            WebElement echkbx=rowchkele.get(rownum-1);							
							if (echkbx != null) 
							 {
								 // checking for the checkbox status
								 if (! (echkbx.isSelected()))
								 {
									 ((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",echkbx);
								 }
							 }
							strretunvalue ="true";							
						}	
					}
				
				}
				else {											
							strretunvalue ="fail";
						appInd.writeLog("#","Grid Table not displayed in Engineering anomaly");
						
					}
				//System.out.println("row id: "+rownum +" actual value: "+strretunvalue);
		 			
			}
		}
		catch(Exception e) 
		{
			if (appInd.isElementPresent(obrowser, "EngrAnomoly_Tablehide_String")) 
			{
				strretunvalue ="-1";
				appInd.writeLog("#","Grid Table is displayed but 'no anaomly' displayed message in Engineering anomaly page");							
				
			}else {
			appInd.writeLog("Exception","Exception while executing 'MainGrid_Operation' method. " + e.getMessage());					
			strretunvalue =null ;	}
			
		}
		finally 
		{
		 	return strretunvalue;
		}	
	}
	

	/********************************
	 * Method Name : EA_anamoly_settings 
	 * Purpose : This method will set the availability and pririotity based on the Anomaly name
	 * Author : Mahesh TK 
	 * Reviewer : 
	 * Date of Creation : 5-Feb-19
	 * Date of modification :
	 * ******************************
	 * note 1: stroperationType :set_anomalyName               strAnomalyName:"none"          stroperation:" new anamolyName"
	 * note 2: stroperationType :get_anomalyName_priority      strAnomalyName:"none"          stroperation:"High/Medium/low"
	 * note 3: stroperationType :set_availablity               strAnomalyName:"default/all"   stroperation:"enable/disable"
	 * note 4: stroperationType :set_priority                  strAnomalyName:"default/all"   stroperation:"High/Medium/low"
	 * note 5: stroperationType :get_anomalyName_availability  strAnomalyName:"none"          stroperation:"High/Medium/low"
	 */
	public String EA_anamoly_settings(WebDriver obrowser,String stroperationType,String stroperation,String strAnomalyName) 
	{
		// System.out.println("EA_anamoly_settings:- started" ); //Self testcode review
		boolean bolstatus = false;		
		int nrowcount = 0;
		int nAnmnrowcount = 0;
		String strstatus = null;
		String strsetlogicalname =null;
		String strradiobtnname = null;
		String strAnamolyName =null;
		try {	
			// checking for the grid table exists
			WebElement eleSR = obrowser.findElement(appInd.createAndGetByObject("EngrAnomoly_Label_showresult"));
			if (eleSR!=null) 
			{
			
			//Click on the Custom Anamolly button
			strstatus +=appInd.clickObject_js(obrowser, "EngrAnomoly_button_customAnamoly");
			Thread.sleep(501);
			
			//Select the Anamoly setting tab
			strstatus +=appInd.clickObject_js(obrowser, "EngrAnomoly_tab_Anamolysetting");
			Thread.sleep(500);
			delay(obrowser,"ajax_loading");
			//select the Anamaly Name from the table	note:default click on the 1st anaomaly name			
			nrowcount =Integer.parseInt( webTable_Operation(obrowser,"AnamolySettingTable","get_rowcount",0 ,0," "));	
				
				if(nrowcount>0) 
				{
					//anaomaly Setting table operation 
					switch(strAnomalyName.toLowerCase())
					 {  			    							    	
					    case "default": 
					    	nAnmnrowcount=1 ;
					    	break;   							    	
					    	
					    case "all": 
					    	nAnmnrowcount = nAnmnrowcount+1 ;
					    	break; 
					    	
					    case "none": 
					    	nAnmnrowcount=0;
					    	break; 
					    default:
					    	System.out.println("Not Found");    
				      } 
				
					for(int nrowindex=1; nrowindex <= nAnmnrowcount;nrowindex ++ ) 
					{
						 webTable_Operation(obrowser,"AnamolySettingTable","click",nrowindex+1 ,1," ");
						 if((strAnomalyName.equalsIgnoreCase("default"))) 
						 {
							 strAnamolyName=webTable_Operation(obrowser,"AnamolySettingTable","get_innertext",nrowindex+1 ,1," ");
							 Thread.sleep(500);
						 }
					}		
			    }
			
			//perform the operation 			
			if(stroperationType.equalsIgnoreCase("set_availablity")) 
				{
					//click on the Set availablity button				
				strstatus +=appInd.clickObject_js(obrowser, "EngrAnomoly_button_setAvailblity");				
				
				switch(stroperation.toLowerCase())
				 {    
				    							    	
				    case "enable": 
				    	strradiobtnname ="AnamolySetting_Radiobtn_enable" ;
				    	break;   							    	
				    	
				    case "disable": 
				    	strradiobtnname ="AnamolySetting_Radiobtn_disable" ;
				    	break;   
				    	
				    default:
				    	System.out.println("Not Found");    
				    } 
				strsetlogicalname="EngrAnomoly_button_SetAvail";
	
			}else if(stroperationType.equalsIgnoreCase("set_priority")) 
			{
				//click on the Set Priority button				
				strstatus +=appInd.clickObject_js(obrowser, "EngrAnomoly_button_setPriority");
				
				switch(stroperation.toLowerCase())
				 	{    	
				    							    	
				    case "high": 
				    	strradiobtnname ="AnamolySetting_Radiobtn_high" ;
				    	break;      							    	
				    	
				    case "medium": 
				    	strradiobtnname ="AnamolySetting_Radiobtn_medium" ;
				    	break;    
				    	
				    case "low": 
				    	strradiobtnname ="AnamolySetting_Radiobtn_low" ;
				    	break;    
				    	
				    default:
				    	System.out.println("Not Found");    
				    }
				strsetlogicalname="EngrAnomoly_button_Setpriority";
				
			}else if(stroperationType.equalsIgnoreCase("set_anomalyName")) 
			{
				if(nrowcount>0) {
					for(int nrowindex=1; nrowindex <= nrowcount-1;nrowindex ++ ) 
					{
					String 	strcurrAnamolyName=webTable_Operation(obrowser,"AnamolySettingTable","get_innertext",nrowindex+1 ,1," "); 
						if((strcurrAnamolyName != null)&&(strcurrAnamolyName.equalsIgnoreCase(stroperation))) 
						 {							 
							 webTable_Operation(obrowser,"AnamolySettingTable","click",nrowindex+1 ,1," ");
							 Thread.sleep(500);
							 break;
							 
						 }
					}
				}
			}else if(stroperationType.equalsIgnoreCase("get_anomalyName_priority")) 
			{
				if(nrowcount>0) 
				{
					for(int nrowindex=1; nrowindex <= nrowcount-1;nrowindex ++ ) 
					{
						//get the anaomaly name based on the Priority(stroperation) (High/Medium/low)
					String 	strpriority=webTable_Operation(obrowser,"AnamolySettingTable","get_innertext",nrowindex+1 ,2," "); 
						if((strpriority != null)&&(strpriority.equalsIgnoreCase(stroperation))) 
						 {							 
							strAnamolyName= webTable_Operation(obrowser,"AnamolySettingTable","get_innertext",nrowindex+1 ,1," ");
							Thread.sleep(250);
							break;
						 }
					}
				}
			}else if(stroperationType.equalsIgnoreCase("get_anomalyName_availability")) 
			{
				if(nrowcount>0) 
				{
					for(int nrowindex=1; nrowindex <= nrowcount-1;nrowindex ++ ) 
					{
						//get the anaomaly name based on the Priority(stroperation) (Enable-True/disable-false)
					String 	strpriority=webTable_Operation(obrowser,"AnamolySettingTable","get_innertext",nrowindex+1 ,3," "); 
						if((strpriority != null)&&(strpriority.equalsIgnoreCase(stroperation))) 
						 {							 
							strAnamolyName= webTable_Operation(obrowser,"AnamolySettingTable","get_innertext",nrowindex+1 ,1," ");
							Thread.sleep(250);
							break;
						 }
					}
				}
			}
			
			if((stroperationType.equalsIgnoreCase("set_availablity")) || (stroperationType.equalsIgnoreCase("set_priority")) ){
			//click on the radiobutton based on the status
			 if (strradiobtnname != null) 
			 {
				 // Getting the radio button webelemnt 
				 WebElement element = obrowser.findElement(appInd.createAndGetByObject(strradiobtnname));
				 
				 if (element != null) 
				 {
					 // checking for the radio button status
					 if (! (element.isSelected()))
					 {
						 ((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",element);
						 Thread.sleep(500);
					 }
				 }else {	strstatus +="fail";}
				 
				 
			  }	else {	strstatus +="fail";}
			
			 
			//click on the set button
			strstatus +=appInd.clickObject_js(obrowser, strsetlogicalname);
			Thread.sleep(500);
			}
			//click on close button 
			strstatus +=appInd.clickObject_js(obrowser, "EngrAnomoly_button_anomlySettclose");
			
						
					// validating the result	
					if (!(strstatus.contains("fail")))
					{
						bolstatus = true;	
						
					}	
			}
		}
		catch(Exception e) {
			if (appInd.isElementPresent(obrowser, "EngrAnomoly_Tablehide_String")) 
			{
				bolstatus =false;
				appInd.writeLog("#","Grid Table is displayed but 'no anaomly' displayed message in Engineering anomaly page");							
				
			}else {
			appInd.writeLog("Exception","Exception while executing 'EA_anamoly_settings' method. " + e.getMessage());
			bolstatus = false;	}		
			}
		finally {
		 	 if (bolstatus) { return "true:"+strAnamolyName;}
		 	 else { return "fail:"+strAnamolyName;}		 	 	
			}	
	}


	/********************************
	 * Method Name : EA_anamoly_settings_validation 
	 * Purpose : This method will validate the availability and pririotity based on the Anomaly name
	 * Author : Mahesh TK 
	 * Reviewer : 
	 * Date of Creation : 5-Feb-19
	 * Date of modification :
	 * ******************************
	 */
	public boolean EA_anamoly_settings_validation(WebDriver obrowser,String stroperationType,String strvalidationvalue,String strexpectedval) 
	{
		// System.out.println("EA_anamoly_settings_validation:- started" ); //Self testcode review
		boolean bolstatus = false;		
		int nrowcount = 0;		
		String strAnamolyName =null;
		String strStatus =null;
		String strLogicalName =null;
		String stranomalystatus =null;
		
		try {
			// checking for the grid table exists
		WebElement eleSR = obrowser.findElement(appInd.createAndGetByObject("EngrAnomoly_Label_showresult"));
		if (eleSR!=null) 
		{
			
			//click on the Filter icon  			
			strStatus += appInd.clickObject_js(obrowser, "EngrAnomoly_Icon_Filter");
			Thread.sleep(250);
			
			//click on the Reset button to default 
			strStatus += appInd.clickObject_js(obrowser, "EngrAnomoly_button_Reset");
			Thread.sleep(200);	
			
			//get the rowcount of webtable in filter popup
			nrowcount =Integer.parseInt( webTable_Operation(obrowser,"EngrAnomoly_Table_filterAnomoly","get_rowcount",0 ,0," "));
			
			
			//perform the validation based on stroperationType		
			if(stroperationType.equalsIgnoreCase("set_availablity")) 
			{		
				//iterate the webtable in filter popup
				for(int nrowindex=1; nrowindex <= nrowcount;nrowindex ++ ) 
				{
					strAnamolyName=webTable_Operation(obrowser,"EngrAnomoly_Table_filterAnomoly","get_innertext",nrowindex ,1," ");
					
					if(strAnamolyName.equalsIgnoreCase(strexpectedval)) 
					{
						stranomalystatus +="true";
						break;
					}
					else {stranomalystatus +="false";}					
				}
				
				if (strvalidationvalue.equalsIgnoreCase("enable"))
				{
					if (stranomalystatus.contains("true")){strStatus +="true";}
					else {strStatus +="false";}
				}else if (strvalidationvalue.equalsIgnoreCase("disable")) 
				{
					if (!(stranomalystatus.contains("true"))){strStatus +="true";}  
					else {strStatus +="false";}	
				}
				
				//click on the Cancel button
				strStatus += appInd.clickObject_js(obrowser, "EngrAnomoly_button_Cancel");
				
			}else if(stroperationType.equalsIgnoreCase("set_priority")) 
			{							
				if (strvalidationvalue != null) 
				    {
							switch(strvalidationvalue.toLowerCase())
							 {  							    	
							    case "high": 
							    	strLogicalName= "EngrAnomoly_Chkbx_High" ;
							    	break;  
							    	
							    case "medium": 
							    	strLogicalName= "EngrAnomoly_Chkbx_Medium";
							    	break;    
							    	
							    case "low": 
							    	strLogicalName= "EngrAnomoly_Chkbx_Low";
							    	break;		
							    	
							    default:
							    	System.out.println("Not Found");    
							    }    
							
							
							//click on the checkbox based on the status
							 if (strLogicalName != null) 
							 {
								 // Getting the check box webelemnt 
								 WebElement elechkbx = obrowser.findElement(appInd.createAndGetByObject(strLogicalName));
								 
								 if (elechkbx != null) 
								 {
									 // checking for the checkbox status
									 if (! (elechkbx.isSelected()))
									 {
										 ((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",elechkbx);
									 }
								 }
								 
							  }
				    }// null check condition
				
				//selecting the Anamoly name  and validate the columnname 
				for(int nrowindex=1; nrowindex <= nrowcount;nrowindex ++ ) 
				{
					strAnamolyName=webTable_Operation(obrowser,"EngrAnomoly_Table_filterAnomoly","get_innertext",nrowindex ,1," ");
					
					if(strAnamolyName.equalsIgnoreCase(strexpectedval)) 
					{
						strAnamolyName=webTable_Operation(obrowser,"EngrAnomoly_Table_filterAnomoly","click",nrowindex ,1," ");
						strStatus +="true";
						break;
					}								
				}
				
				//click on the Filter button
				strStatus += appInd.clickObject_js(obrowser, "EngrAnomoly_button_Filter");
				delay(obrowser,"ajax_loading");
			
				//click onthe all anomolies to reset. 
			String sts=	Anomalycount(obrowser,"allanomalies","click");
			if (!sts.equalsIgnoreCase("-1")) {
				delay(obrowser,"ajax_loading");
				//validate the Anomaly type
				
				nrowcount =Integer.parseInt( MainGrid_Operation(obrowser,"get_rowcount",0 ,0," "));				
				if (nrowcount>=1) 
				{
					if(nrowcount>14)nrowcount =14; //checking for 1st 10 records
					
					for(int nrowindex=1; nrowindex < nrowcount+1;nrowindex ++ ) //note index started from the 1 because 0 row is header
					{
						String strActualvalue= MainGrid_Operation(obrowser,"get_innertext",nrowindex ,5,"");
						System.out.println("Actual value: "+strActualvalue+" Expected value: "+strvalidationvalue + " :-> row id ->:" +nrowindex);
						if (strActualvalue != null)
						{
							 // checking for high/medium/low values (in high/medium/low anomaly vallidation)						
								if ((strActualvalue.toLowerCase()).contains(strvalidationvalue)) { strStatus += "true";}
								else {strStatus += "fail";}					
	
						}
					}
				}
			}else {strStatus += "fail";}	
			}//end of set priority
			
			// validating the result	
			if (!(strStatus.contains("fail")))
			{
				bolstatus = true;	
				
			}	
			}
		}
		catch(Exception e) {
			if (appInd.isElementPresent(obrowser, "EngrAnomoly_Tablehide_String")) 
			{
				bolstatus =false;
				appInd.writeLog("#","Grid Table is displayed but 'no anaomly' displayed message in Engineering anomaly page");							
				
			}else {
			appInd.writeLog("Exception","Exception while executing 'EA_anamoly_settings_validation' method. " + e.getMessage());
			bolstatus = false;	}		
			}
		finally {
			
			return bolstatus;	
		 	
			}	
	}

/********************************
	* Method Name : Engr_anomoly_Actions 
	* Purpose : This method will perform the actions 
	* Author : Mahesh TK 
	* Reviewer : 
	* Date of Creation : 23-Jan-19
	* Date of modification :
	* ******************************/


	public boolean Engr_anomoly_Actions(WebDriver obrowser,String strAction) 
	{
		// System.out.println("menu_EngeeringAnomoly_Navigation:- started" ); //Self testcode review
		boolean bolstatus = false;		
		String strfilterActionName=null;
		int nrowcount = 0;		
		String strStatus = null;				
		boolean bolnullstatus=false;
		String strlogicaldrpdwn=null;
		boolean bolfilterstatus =false;
		
		
		try {
			// checking for the grid table exists
			WebElement eleSR = obrowser.findElement(appInd.createAndGetByObject("EngrAnomoly_Label_showresult"));
			if (eleSR!=null) 
			{
			
			//Perform the Action operation on provided ActionName		 
		    if (strAction != null) 
		    {
					switch(strAction.toLowerCase())
					{				  	
					    case "assign":					    								
							strfilterActionName="Unassigned";
							strlogicaldrpdwn="Action_assign";
						    	break; 
						    	
					    case "unassign": 
					    	
					    	strfilterActionName="assigned";
					    	strlogicaldrpdwn="Action_Unassign";
						    	break; 		   		    	
						    	
					    case "unresolve": 
					    	
					    	strfilterActionName="none";
					    	bolfilterstatus=true;
					    	strlogicaldrpdwn="Action_resolve";
						    	break; 
						    	
					    case "resolve":					    	
					    	strfilterActionName="resolved";
					    	strlogicaldrpdwn="Action_Unresolve";
						    	break; 
					    	
					    case "suppress": 
					    	
					    	strfilterActionName="unsuppressed";
					    	strlogicaldrpdwn="Action_Suppress";
						    	break;    
					    	
					    case "unsuppress": 
					    	
					    	strfilterActionName="suppressed";
					    	strlogicaldrpdwn="Action_UnSuppress";
						    	break; 									   
					    	
					    default:									    	
					    	
					    	bolnullstatus = true;
					    	System.out.println("Not Found");    
					    }    
					String sts=	null;
					if (bolfilterstatus != true) 
					{
						//Filters based on the provided Action name			
						strStatus += Engr_anomoly_filters(obrowser,strfilterActionName,"none");
						Thread.sleep(250);
						
						//click on the anamoly above the grid  to default 
						sts = Anomalycount(obrowser,"allanomalies","click");
					}						
					else {sts = Anomalycount(obrowser,"unresolved","click");}
					delay(obrowser,"ajax_loading");
				if(!sts.equalsIgnoreCase("-1"))	{
					//Select the 1channels from the Grid
					//get the row count 
					nrowcount =Integer.parseInt(MainGrid_Operation(obrowser,"get_rowcount",0 ,0, "" ));
					// check the acknowledged
					if(nrowcount > 0)
						nrowcount=1;
					for (int i=1 ;i<=nrowcount;i++) 
					{
						MainGrid_Operation(obrowser,"click",i ,1, "" );							
					}
					if (!bolnullstatus && nrowcount > 0)
					{
						// select the Dropdown the values						
							WebElement ele =obrowser.findElement(appInd.createAndGetByObject(strlogicaldrpdwn));
							if(ele !=null) {((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",ele);}
							 		
						//action perform based on the action type								
						if(strAction.equalsIgnoreCase("assign")) 
						{
							// select the  1st user name by default						
							WebElement userele =obrowser.findElement(appInd.createAndGetByObject("Action_UserName"));
							if(ele !=null) {((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",userele);}
						}
						
						//clear on the comment window
						obrowser.findElement(By.xpath("//*[@id=\"formDefectAssignment\"]/div[2]/textarea")).clear();						
				
						//set the comments 
						strStatus +=appInd.setObject(obrowser, "Comment_Unacknowledged", (strAction +"_coment"));
						Thread.sleep(350);
						
						//click on the button in action window upon setting comments
						strStatus +=appInd.clickObject_js(obrowser, "EngrAnomoly_button_actionwnd");
					}
					
				}else { strStatus +="false"; } // null check condition							
		    }		
					// validating the result	
						if (!strStatus.contains("false")){
							bolstatus = true;
						} 
		}
			}
		catch(Exception e) {
			if (appInd.isElementPresent(obrowser, "EngrAnomoly_Tablehide_String")) 
			{
				bolstatus =false;
				appInd.writeLog("#","Grid Table is displayed but 'no anaomly' displayed message in Engineering anomaly page");							
				
			}else {
			appInd.writeLog("Exception","Exception while executing 'Engr_anomoly_Actions' method. " + e.getMessage());
			bolstatus = false;}
			}
		finally {
			return bolstatus;
			}	
	}


	

	
	
	

	/********************************
	 * Method Name : EngeeringAnomoly_filtervalidation_countbased 
	 * Purpose : This method will vallidate the upon performing the filer in filer popup
	 * Author : Mahesh TK 
	 * Reviewer : 
	 * Date of Creation : 24-Jan-19
	 * Date of modification :
	 * ******************************
	 */
	public boolean EngeeringAnomoly_filtervalidation_countbased(WebDriver obrowser,String strName) 
	{
		// System.out.println("EngeeringAnomoly_filtervalidation:- started" ); //Self testcode review
		boolean bolstatus = false;		
		int nrowcount = 0;		
		String strdisplycnt = null;
		String strstatus = null;
		try {		
				
							
			// checking for the grid table exists
			WebElement ele = obrowser.findElement(appInd.createAndGetByObject("EngrAnomoly_Label_showresult"));
			if (ele!=null) 
			{
					//get the rowcount of grid  exculding header 
					nrowcount =Integer.parseInt( MainGrid_count(obrowser,"get_totalrowcount"));
					appInd.writeLog("#","Rowcount: " +nrowcount );
					
					//get the displayed count 
					if (strName != null) 
				    {
							switch(strName.toLowerCase())
							 {    
							    							    	
							    case "unassigned": 
							    	strdisplycnt= Anomalycount(obrowser,"unassigned","get_count");
							    	Thread.sleep(500);
							    	Anomalycount(obrowser,"unassigned","click");
							    	break;   							    	
							    	
							    case "suppressed": 
							    	strdisplycnt= Anomalycount(obrowser,"suppressed","get_count");
							    	Thread.sleep(500);
							    	Anomalycount(obrowser,"suppressed","click");
							    	break;  							    	
							    	
							    case "high": 
							    	strdisplycnt= Anomalycount(obrowser,"highpriority","get_count");
							    	Thread.sleep(500);
							    	Anomalycount(obrowser,"highpriority","click");
							    	break; 							    	
							    	
							    case "all": 
							    	strdisplycnt= Anomalycount(obrowser,"allanomalies","get_count");
							    	Thread.sleep(500);
							    	Anomalycount(obrowser,"allanomalies","click");
							    	break;		
							    	
							    default:
							    	System.out.println("Not Found");    
							    }    
					
					if(strdisplycnt!=null) {
						//Compare with total anomaly  in showing result with dispaly counts above the grid
						if (nrowcount != (Integer.parseInt( strdisplycnt))) 
						{
							strstatus +="fail";
							appInd.writeLog("#", "Mismatch display count Expected Totalcount: "+nrowcount + "Actual count displayed : "+strdisplycnt);
						}else
						{
							strstatus +="pass";
						}
						
					}
					
					//load all the records
					if (MainGrid_count(obrowser,"allrecordsload")!=null) {strstatus +="pass";} 
					else strstatus +="fail";
					
					
					//check the count display count upon loading all the records				
						//Compare with total anomaly  in showing result with dispaly counts above the grid
						if (nrowcount != (Integer.parseInt( MainGrid_count(obrowser,"get_currentrowcount"))))
						{
							strstatus +="fail";
							appInd.writeLog("#", "Mismatch display count Expected Totalcount: "+nrowcount + "Actual count displayed : "+strdisplycnt);
						}else
						{
							strstatus +="pass";
						}					
					
					// validating the result	
					if (!(strstatus.contains("fail")))
					{
						bolstatus = true;	
						
					}	
					
				}
			}
		}
		catch(Exception e) {
			if (appInd.isElementPresent(obrowser, "EngrAnomoly_Tablehide_String")) 
			{
				bolstatus =false;
				appInd.writeLog("#","Grid Table is displayed but 'no anaomly' displayed message in Engineering anomaly page");							
				
			}else {
			appInd.writeLog("Exception","Exception while executing 'EngeeringAnomoly_filtervalidation_countbased' method. " + e.getMessage());
			bolstatus = false;	}		
			}
		finally {
		 	return bolstatus;
			}	
	}


	/********************************
	 * Method Name : MainGrid_count 
	 * Purpose : This method will get the count from the Engineering main page upon moving to the last row
	 * Author : Mahesh TK 
	 * Reviewer : 
	 * Date of Creation : 4-Feb-19
	 * Date of modification :
	 * ******************************
	 */
	@SuppressWarnings("finally")
	public String MainGrid_count(WebDriver obrowser,String stroperation) 
	{
		// System.out.println("MainGri_count:- started" ); //Self testcode review
		int ntotalrecords =0;
		String strretunvalue = null;		
		boolean bolget_allstatus = false;
		try {
			// checking for the grid table exists
			WebElement ele = obrowser.findElement(appInd.createAndGetByObject("EngrAnomoly_Label_showresult"));
			if (ele!=null) 
			{			
			String strresults =ele.getText();
			
			//Extract the total records
			String strsplitarr[] =strresults.split("/");
			ntotalrecords = Integer.parseInt(strsplitarr[1]);		   			
	       		
				//get the mainGridtable element 
				WebElement webtableelement=obrowser.findElement(appInd.createAndGetByObject("EngrAnomoly_Table_MainGrid"));
				
				
				if (webtableelement != null) 
				{				
					//iterate the mainGridtable based on the stroperation
						switch(stroperation.toLowerCase())
						 {    
						    case "get_totalrowcount":
						    	//get the row count based on the checkbox															
						    	strretunvalue= String.valueOf(ntotalrecords) ;
						    	break; 
						    	
						    case "get_currentrowcount": 
						    	 //get the current column count																 
						    	strretunvalue= String.valueOf(strsplitarr[0]) ;
						    	break;   
						    	
						    case "allrecordsload": 
						    	bolget_allstatus = true ;
						    	break; 						 
						 }
						
					if (!((stroperation.contentEquals("get_rowcount")) || (stroperation.contentEquals("get_columncount"))) && (bolget_allstatus != false))
					{	
						int ntotclicks=(ntotalrecords/5);
						for (int rownum=0;rownum < ntotclicks ;rownum++)
						{	
							if(ntotalrecords>10) {
								String temp="10";
								WebElement Element = obrowser.findElement(By.xpath("(//*[contains(@id,'"+String.valueOf(temp)+"-uiGrid-')]/div)[1]"));	
							     //This will scroll the page till the element is found			        
								if(Element != null)
								{
									 JavascriptExecutor js = (JavascriptExecutor) obrowser;
									 js.executeScript("arguments[0].scrollIntoView();", Element);	
									 if (ntotalrecords>100) {delay(obrowser,"ajax_loading");}
								}
							}
						}
						strretunvalue="pass" ;
					}			
				}
				else {appInd.writeLog("#","Grid Table not displayed in Engineering anomaly");}
				
		}
			}
		catch(Exception e) 
		{
			if (appInd.isElementPresent(obrowser, "EngrAnomoly_Tablehide_String")) 
			{
				strretunvalue ="-1";
				appInd.writeLog("#","Grid Table is displayed but 'no anaomly' displayed message in Engineering anomaly page");							
				
			}else {
			appInd.writeLog("Exception","Exception while executing 'MainGrid_count' method. " + e.getMessage());					
			strretunvalue =null ;	}			
		}
		finally 
		{
		 	return strretunvalue;
		}	
	}
	



	/********************************
	 * Method Name : Anomalycount 
	 * Purpose : This method will get the anomoly count from the Engineering main page
	 * Author : Mahesh TK 
	 * Reviewer : 
	 * Date of Creation : 4-Feb-19
	 * Date of modification :
	 * ******************************
	 */
	public String Anomalycount(WebDriver obrowser,String stractions,String stropration) 
	{
		// System.out.println("Anomalycount:- started" ); //Self testcode review
		
		String strretunvalue="0";
		String strlogicalName=null;		
		try {
			
			//get the TotalResult count in label Showing Results
			WebElement eleshwres = obrowser.findElement(appInd.createAndGetByObject("EngrAnomoly_Label_showresult"));
			if (eleshwres!=null) 
			{
				//get the action count
			switch(stractions.toLowerCase())
			 {    
			    case "allanomalies":
			    	strlogicalName="EngrAnomoly_Label_allanomaly";			    	
			    	break; 
			    	
			    case "highpriority": 
			    	strlogicalName="EngrAnomoly_Label_highpriority";			    	
			    	break; 
			    	
			    case "unresolved": 
			    	strlogicalName="EngrAnomoly_Label_Unresolved";			    	
			    	break; 

			    case "unassigned": 
			    	strlogicalName="EngrAnomoly_Label_unassigned";			    	
			    	break; 
		    	
			    case "suppressed": 
			    	strlogicalName="EngrAnomoly_Label_suppressed";			    	
			    	break; 
  
			 }
		
		
		
			if (stropration.equalsIgnoreCase("get_count")) 
			{ 			
				
				// get the count value from the above the grid
				WebElement ele=obrowser.findElement(appInd.createAndGetByObject(strlogicalName));
				delay(obrowser,"ajax_loading");
				String	strvalue=ele.getText();
				delay(obrowser,"ajax_loading");				
				//split and get the count
				if (strvalue!=null)
				{
					String strspltval[] =strvalue.split("\n");				
					if(strspltval[0] != null) {strretunvalue =strspltval[0] ;}
				}
			
			}
			else {
				appInd.clickObject_js(obrowser,strlogicalName);
				delay(obrowser,"ajax_loading");
					}		
			}
			}
		catch(Exception e) {
			if (appInd.isElementPresent(obrowser, "EngrAnomoly_Tablehide_String")) 
			{
				strretunvalue ="-1";
				appInd.writeLog("#","Grid Table is displayed but 'no anaomly' displayed message in Engineering anomaly page");							
				
			}else {
			appInd.writeLog("Exception","Exception while executing 'Anomalycount' method. " + e.getMessage());}
			
			}
		finally {
		 	return strretunvalue;
			}	
	}
	
	
	public void delay(WebDriver obrowser,String strlogicalName) 
	{		
		try
		{			
 			appInd.waitFor(obrowser, strlogicalName, "element", "",5000);
 			waitForJSandJQueryToLoad(obrowser);			
		} catch (Exception e) {appInd.writeLog("Exception","Exception while executing 'delay' method. " + e.getMessage());}
		
	}
	

/********************************
	 * Method Name : Engr_StatisticsCount 
	 * Purpose : This method will verify the anaomly count
	 * Author : Mahesh TK 
	 * Reviewer : 
	 * Date of Creation : 6-Feb-19
	 * Date of modification :
	 * note1: strcountType: increase  strActionType:suppressed/unassigned/unresolved/high/medium/all_defects
	 * note1: strcountType: decrease  strActionType:suppressed/unassigned/unresolved/high/medium/all_defects
	 * ******************************/


	public boolean Engr_StatisticsCount(WebDriver obrowser,String strcountType,String strActionType) 
	{
		// System.out.println("Engr_StatisticsCount:- started" ); //Self testcode review
		boolean bolstatus = false;
		boolean bolfileterstatus = false;
		String strAnamolycountparam =null;
		boolean bolcustomAmystatus = false;
		boolean bolActionstatus = false;
		int nbeforecont =0;
		int ndiffcont=0;
		int naftercont =0;
		String strStatus = null;
		strcount=null;
		
		
				
		try {
			//get the TotalResult count in label Showing Results
			WebElement eleshwres = obrowser.findElement(appInd.createAndGetByObject("EngrAnomoly_Label_showresult"));
			if (eleshwres!=null) 
			{
			
			
		    if (strActionType != null) 
		    {
					switch(strActionType.toLowerCase())
					 {    
					    case "suppressed": 
					    	bolfileterstatus = true;
					    	strAnamolycountparam ="suppressed";
					    	bolActionstatus=true;
					    	break; 				   
					    	
					    case "unassigned": 
					    	bolfileterstatus = true;
					    	strAnamolycountparam ="unassigned";
					    	bolActionstatus=true;
					    	break;      
					    	
					    case "unresolved":
					    	strAnamolycountparam ="unresolved";
					    	bolActionstatus=true;
					    	break;				   
					    	
					    case "high": 
					    	bolfileterstatus = true;
					    	strAnamolycountparam ="highpriority";
					    	bolcustomAmystatus=true;
					    	break;  
					    	
					    case "medium": 
					    	bolfileterstatus = true;
					    	strAnamolycountparam ="allanomalies";
					    	break;    
					    	
					    case "all_defects":	
					    	strAnamolycountparam ="allanomalies";
					    	bolcustomAmystatus=true;
					    	break;		
					    	
					    default:
					    	System.out.println("None of these action selected");   
					    	
					    } 
					
					//filter based on the strActionType	
					if(bolfileterstatus) 
					{										
						strStatus += Engr_anomoly_filters(obrowser,strActionType,"none") ;
						delay(obrowser,"ajax_loading");
					}
					
					//refresh by clicking on above the grid "All anamolies"	and check the grid existing status
					
			    String strextsts =	Anomalycount(obrowser,"allanomalies","click");
			    delay(obrowser,"ajax_loading");
			    if(!strextsts.equalsIgnoreCase("-1"))	{
			    	//get the count before performing operation 
			    	String strbeforecount=(Anomalycount(obrowser,strAnamolycountparam,"get_count"));
			    	if ((strbeforecount != null)||(strbeforecount != "true")||(strbeforecount != "false") ) { nbeforecont=Integer.parseInt(strbeforecount);}
					
					//perform the operation and validation based on the strcountType(increase /decrease)
					
			        if(strcountType.equalsIgnoreCase("increase")) 
			        {
			        	if (bolcustomAmystatus) 
			        	{
							if(strActionType.equalsIgnoreCase("high"))
							{
								//getthe anomaly name having priority Medium
								String strAname =EA_anamoly_settings(obrowser,"get_anomalyName_priority","medium","none");								
								//get the anamoly name
								String spltarr[] =strAname.split(":");								
								//set the anamaly name
								if(spltarr[1]!=null) {EA_anamoly_settings(obrowser,"set_anomalyName",spltarr[1],"none");}
								//change it to high priority
								EA_anamoly_settings(obrowser,"set_priority","high","none");
								delay(obrowser,"ajax_loading");
								//get the count
								String straftercount=(Anomalycount(obrowser,strAnamolycountparam,"get_count"));
								if ((straftercount != null)||(straftercount != "true")||(straftercount != "false") ) { naftercont=Integer.parseInt(straftercount);}
								
							}else if(strActionType.equalsIgnoreCase("all_defects")) 
							{	
								//getthe anomaly name having Enabled false
								String strAname =EA_anamoly_settings(obrowser,"get_anomalyName_availability","false","none");								
								//get the anamoly name
								String spltarr[] =strAname.split(":");								
								//set the anamaly name
								if(spltarr[1]!=null) {EA_anamoly_settings(obrowser,"set_anomalyName",spltarr[1],"none");}
								//change it to true-enable
								EA_anamoly_settings(obrowser,"set_availablity","enable","none");
								delay(obrowser,"ajax_loading");
								//get the count
								String straftercount=(Anomalycount(obrowser,strAnamolycountparam,"get_count"));
								if ((straftercount != null)||(straftercount != "true")||(straftercount != "false") ) { naftercont=Integer.parseInt(straftercount);}							
							}
							
							//validation							
							ndiffcont = naftercont-nbeforecont;
							strcount = "Before_Count: "+String.valueOf(nbeforecont) +" / " +"After count: "+ String.valueOf(naftercont);
							if (ndiffcont>0) {strStatus +="true";}
							else  {								
								if((nbeforecont!=0 && naftercont != 0)) {strStatus +="false";}
								else {strStatus +="true";}								
								}
							
						}else if(bolActionstatus) 
						{
							if(strActionType.equalsIgnoreCase("suppressed"))
							{
								Engr_anomoly_Actions(obrowser,"suppress") ;
								strStatus += Engr_anomoly_filters(obrowser,strActionType,"none") ;
								delay(obrowser,"ajax_loading");
								String straftercount=(Anomalycount(obrowser,strAnamolycountparam,"get_count"));
								if ((straftercount != null)||(straftercount != "true")||(straftercount != "false") ) { naftercont=Integer.parseInt(straftercount);}
							}
							else if(strActionType.equalsIgnoreCase("unassigned")) 
							{
								Engr_anomoly_Actions(obrowser,"unassign") ;								
								strStatus += Engr_anomoly_filters(obrowser,strActionType,"none") ;
								delay(obrowser,"ajax_loading");
								String straftercountuA=(Anomalycount(obrowser,strAnamolycountparam,"get_count"));
								if ((straftercountuA != null)||(straftercountuA != "true")||(straftercountuA != "false") ) { naftercont=Integer.parseInt(straftercountuA);}
							}
							else if(strActionType.equalsIgnoreCase("unresolved")) 
							{
								
								Engr_anomoly_Actions(obrowser,"resolve") ;								
								strStatus += Engr_anomoly_filters(obrowser,"none","none") ;
								delay(obrowser,"ajax_loading");
								String straftercountunresolved=(Anomalycount(obrowser,strAnamolycountparam,"get_count"));
								if ((straftercountunresolved != null)||(straftercountunresolved != "true")||(straftercountunresolved != "false") ) { naftercont=Integer.parseInt(straftercountunresolved);}
							}
							//validation
							ndiffcont = naftercont-nbeforecont;
							strcount = "Before_Count: "+String.valueOf(nbeforecont) +" / " +"After count: "+ String.valueOf(naftercont);
							if (ndiffcont>0) {strStatus +="true";}
							else  {								
								if((nbeforecont!=0 && naftercont != 0)) {strStatus +="false";}
								else {strStatus +="true";}								
								}
						}     	
			        	
			        }else if(strcountType.equalsIgnoreCase("decrease")) 
			        {
			        	if (bolcustomAmystatus) {
							
			        		if(strActionType.equalsIgnoreCase("high"))
							{
								//getthe anomaly name having priority high
								String strAname =EA_anamoly_settings(obrowser,"get_anomalyName_priority","high","none");								
								//get the anamoly name
								delay(obrowser,"ajax_loading");
								String spltarr[] =strAname.split(":");								
								//set the anamaly name
								if(spltarr[1]!=null) {EA_anamoly_settings(obrowser,"set_anomalyName",spltarr[1],"none");}
								delay(obrowser,"ajax_loading");
								//change it to medium priority
								EA_anamoly_settings(obrowser,"set_priority","medium","none");
								delay(obrowser,"ajax_loading");
								//get the count
								String straftercounthigh=(Anomalycount(obrowser,strAnamolycountparam,"get_count"));
								if ((straftercounthigh != null)||(straftercounthigh != "true")||(straftercounthigh != "false") ) { naftercont=Integer.parseInt(straftercounthigh);}
																
							}else if(strActionType.equalsIgnoreCase("all_defects")) 
							{	
								//getthe anomaly name having Enabled true
								String strAname =EA_anamoly_settings(obrowser,"get_anomalyName_availability","true","none");								
								//get the anamoly name
								String spltarr[] =strAname.split(":");								
								//set the anamaly name
								if(spltarr[1]!=null) {EA_anamoly_settings(obrowser,"set_anomalyName",spltarr[1],"none");}
								//change it to false-disable
								EA_anamoly_settings(obrowser,"set_availablity","disable","none");
								delay(obrowser,"ajax_loading");
								//get the count
								String straftercountaldef=(Anomalycount(obrowser,strAnamolycountparam,"get_count"));
								if ((straftercountaldef != null)||(straftercountaldef != "true")||(straftercountaldef != "false") ) { naftercont=Integer.parseInt(straftercountaldef);}						
							}
			        		
			        		//validation
							//ndiffcont = naftercont-nbeforecont;
			        		ndiffcont =nbeforecont- naftercont;
			        		strcount = "Before_Count: "+String.valueOf(nbeforecont) +" / " +"After count: "+ String.valueOf(naftercont);
							if (ndiffcont>0) {strStatus +="true";}
							else  {								
								if((nbeforecont!=0 && naftercont != 0)) {strStatus +="false";}
								else {strStatus +="true";}								
								}

						}else if(bolActionstatus)
						{
							if(strActionType.equalsIgnoreCase("suppressed"))
							{
								Engr_anomoly_Actions(obrowser,"unsuppress") ;								
								strStatus += Engr_anomoly_filters(obrowser,strActionType,"none") ;
								delay(obrowser,"ajax_loading");
								String straftercountunsuppress=(Anomalycount(obrowser,strAnamolycountparam,"get_count"));
								if ((straftercountunsuppress != null)||(straftercountunsuppress != "true")||(straftercountunsuppress != "false") ) { naftercont=Integer.parseInt(straftercountunsuppress);}		
							}
							else if(strActionType.equalsIgnoreCase("unassigned")) 
							{
								Engr_anomoly_Actions(obrowser,"assign") ;
								delay(obrowser,"ajax_loading");
								strStatus += Engr_anomoly_filters(obrowser,strActionType,"none") ;
								String straftercountA=(Anomalycount(obrowser,strAnamolycountparam,"get_count"));
								if ((straftercountA != null)||(straftercountA != "true")||(straftercountA != "false") ) { naftercont=Integer.parseInt(straftercountA);}		
							}
							else if(strActionType.equalsIgnoreCase("unresolved")) 
							{
								
								Engr_anomoly_Actions(obrowser,"unresolve") ;
								delay(obrowser,"ajax_loading");
								String straftercountresolved=(Anomalycount(obrowser,strAnamolycountparam,"get_count"));
								if ((straftercountresolved != null)||(straftercountresolved != "true")||(straftercountresolved != "false") ) { naftercont=Integer.parseInt(straftercountresolved);}		
							}
							
							//validation
							ndiffcont =nbeforecont- naftercont;
							strcount = "Before_Count: "+String.valueOf(nbeforecont) +" / " +"After count: "+ String.valueOf(naftercont);
							if (ndiffcont>0) {strStatus +="true";}
							else  {								
								if((nbeforecont!=0 && naftercont != 0)) {strStatus +="false";}
								else {strStatus +="true";}								
								}
													
						}
						
			        }    
			    } else {strStatus +="false";}	
			    	
		    }// null check condition	 
		// validating the result	
		if (!strStatus.contains("false")){
			bolstatus = true;
			
		} 
		}					
		}
		catch(Exception e) {
			if (appInd.isElementPresent(obrowser, "EngrAnomoly_Tablehide_String")) 
			{
				bolstatus =false;
				appInd.writeLog("#","Grid Table is displayed but 'no anaomly' displayed message in Engineering anomaly page");							
				
			}else {
			appInd.writeLog("Exception","Exception while executing 'Engr_StatisticsCount' method. " + e.getMessage());
			bolstatus = false;}			
			}
		finally {
		 	return bolstatus;
			}
	}
	
	

	/********************************
	 * Method Name : menu_changeDetection_Navigation 
	 * Purpose : This method will launch the Change Detection page
	 * Author : Mahesh TK 
	 * Reviewer : 
	 * Date of Creation : 20-Feb-19
	 * Date of modification :
	 * ******************************
	 */
	public boolean menu_changeDetection_Navigation(WebDriver obrowser) 
	{
		// System.out.println("menu_changeDetection_Navigation:- started" ); //Self testcode review
		boolean bolstatus = false;
		String strpagetittle=null;
		
		try {
			
				//check the page title name 
				strpagetittle =obrowser.findElement(appInd.createAndGetByObject("Pagetittle")).getText();
				
				if (!(strpagetittle.equalsIgnoreCase("Change Detection")))
				{
					//click on the menu icon
					By byClickOnMenuBar = appInd.createAndGetByObject("Menu_Bar");
					obrowser.findElement(byClickOnMenuBar).sendKeys(Keys.ENTER);
					Thread.sleep(250);
								
					//click on the change detection
					obrowser.findElement(By.linkText("Change Detection")).sendKeys(Keys.ENTER);
					Thread.sleep(5000);
					
				}
				bolstatus = true;
				Thread.sleep(2000);
			}
		catch(Exception e) {
			appInd.writeLog("Exception","Exception while executing 'menu_changeDetection_Navigation' method. " + e.getMessage());
			bolstatus = false;
			}
		finally {
		 	return bolstatus;
			}	
	}
	

	/********************************
	 * Method Name : CD_FiltercheckboxOperation 
	 * Purpose : This method will perform the check box operation in the 'Type' and 'Status' Section 
	 * Author : Mahesh TK 
	 * Reviewer : 
	 * Date of Creation : 20-feb-19
	 * Date of modification :
	 * ******************************/


	public void CD_FiltercheckboxOperation(WebDriver obrowser,String strLogicalNames, String strOperation ) 
	{
		
		try {
				// get the webelements 
			
			//click on the checkbox based on the status
			 if (strLogicalNames != null) 
			 {
				String  strsplitarr[] = strLogicalNames.split(":");
				
				for (String strLogicalName:strsplitarr)
				{				 
						 // Getting the check box webelemnt 
						 WebElement elechkbx = obrowser.findElement(appInd.createAndGetByObject(strLogicalName));
						 
						 if (elechkbx != null) 
						 {
							 // checking for the checkbox status
							 if (! (elechkbx.isSelected()) && strOperation.equalsIgnoreCase("check") )
							 {
								 ((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",elechkbx);
							 }
							 else if ((!elechkbx.isSelected()) && strOperation.equalsIgnoreCase("uncheck") )
							 {
								 ((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",elechkbx);
							 }
							 Thread.sleep(250);
						 }				 
				}
				 
			  }		
			
		} catch (Exception e) {
			appInd.writeLog("Exception","Exception while executing 'CD_FiltercheckboxOperation' method. " + e.getMessage());
			
		}
	}
	
	
	/********************************
	 * Method Name : changeDetection_filters 
	 * Purpose : This method will perform the filter operation in the change detection page
	 * Author : Mahesh TK 
	 * Reviewer : 
	 * Date of Creation : 20-Feb-19
	 * Date of modification :
	 * ******************************/


	public boolean changeDetection_filters(WebDriver obrowser,String strCheckboxName,String strvalue) 
	{
		// System.out.println("changeDetection_filters:- started" ); //Self testcode review
		boolean bolstatus = false;
		boolean bolfromoperationstatus =false;
		boolean bolmultiSnapshotoperation =false;
		String strLogicalName=null;	
		String strStatus = null;		
		
		try {								
					//click on the Filter icon  			
					strStatus += appInd.clickObject_js(obrowser, "CD_icon_Filter");
					Thread.sleep(300);
					
					//click on the Reset Button
					strStatus += appInd.clickObject_js(obrowser, "CD_button_reset");
					Thread.sleep(300);
					
					// Reset to default 
					if (!(strCheckboxName.split(":")[0].equalsIgnoreCase("none")))
					{
						CD_FiltercheckboxOperation(obrowser,"CD_chkbox_Added:CD_chkbox_Modified:CD_chkbox_Deleted:CD_chkbox_Acknowledged"
								+ ":CD_chkbox_Unacknowledged:CD_chkbox_suppressed:CD_chkbox_unsuppressed"
								+ ":CD_chkbox_Assigned:CD_chkbox_Unassigned","uncheck");	
					}
							
						
					//Perform the checkbox operation on provided checkbox name						
					//split the strChkbxName if multiple name provied  
						String strChkbxName[]=strCheckboxName.split(":");
						for (String  strName : strChkbxName) 
						{ 
						    if (strName != null) 
						    {
									switch(strName.toLowerCase())
									 {    
									    case "acknowledged": 
									    	strLogicalName= "CD_chkbox_Acknowledged" ;
									    	break; 
									    	
									    case "unacknowledged": 
									    	strLogicalName= "CD_chkbox_Unacknowledged" ;
									    	break;   
									    	
									    case "assigned": 
									    	strLogicalName= "CD_chkbox_Assigned" ;
									    	break;    
									    	
									    case "unassigned": 
									    	strLogicalName= "CD_chkbox_Unassigned" ; 
									    	break;  							      
									    	
									    case "suppressed": 
									    	strLogicalName= "CD_chkbox_suppressed";
									    	break;    
									    	
									    case "unsuppressed": 
									    	strLogicalName= "CD_chkbox_unsuppressed";
									    	break;    
									    	
									    case "added": 
									    	strLogicalName= "CD_chkbox_Added" ;
									    	break;  
									    	
									    case "modified": 
									    	strLogicalName= "CD_chkbox_Modified";
									    	break;    
									    	
									    case "deleted": 
									    	strLogicalName= "CD_chkbox_Deleted";
									    	break;
									    	
									    case "last3month": 
									    	strLogicalName= "CD_Radiobtn_last3mnoth";
									    	break;
									    	
									    case "last6month": 
									    	strLogicalName= "CD_Radiobtn_last6mnoth";
									    	break;
									    
									    case "last1year": 
									    	strLogicalName= "CD_Radiobtn_1year";
									    	break;
									    	
									    case "from": 
									    	strLogicalName= "CD_Radiobtn_From";
									    	bolfromoperationstatus = true;
									    	break;
									    
									    case "multiplesnapshots": 
									    	strLogicalName= "CD_Radiobtn_multiple";
									    	bolmultiSnapshotoperation = true;
									    	break;

									    default:
									    	System.out.println("None of these selected");    
									    } 	
									
									//click on the check box
									CD_FiltercheckboxOperation(obrowser,strLogicalName,"check");
									
									if (bolfromoperationstatus)
									{
										if(strvalue !=null) {
											String strdates[]=strvalue.split(":");
											
											//click on the date field to open calander
											appInd.clickObject_js(obrowser, "CD_Fromdate_btn");
											
											//set the from date
											calender(obrowser, strdates[0]);
											Thread.sleep(300);										

											//click on the date field to open calander
											appInd.clickObject_js(obrowser, "CD_Todate_btn");
											
											//set the To date
											calender(obrowser, strdates[1]);
											Thread.sleep(300);
											
											strStatus +="true";											
											
										}else strStatus +="false";
									}
									
								
									//Multiple operations 
									if (bolmultiSnapshotoperation)
									{
										if(strvalue !=null) {
											
											String strchkbxsdates[]=strvalue.split(":");											
											
											//reset all checkbox 
											if(!(strchkbxsdates[0].equalsIgnoreCase("selectall"))) 
											{
												WebElement selallele =obrowser.findElement(appInd.createAndGetByObject("CD_Chkbx_SelectAll")); 
												if (selallele != null)
												{
													if (! (selallele.isSelected())) 
													{	
														for(int i=0;i<2;i++) {															
														((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",selallele);
														Thread.sleep(300);}
													}
												}
											}		
											
												String strpassstatus =null;
												
												for(String strchkbxdate :strchkbxsdates){
													boolean bolcheck =false;
													List<WebElement> chkbxele = obrowser.findElements(appInd.createAndGetByObject("CD_Chk_snapshots"));  
						                            for(WebElement element :chkbxele) 
						                            {
						                            	String stractualval =element.getText();
						                            	Thread.sleep(200);
						                            	if (stractualval.startsWith(strchkbxdate))
						                            	{
						                            		if (element != null){if (! (element.isSelected()))((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",element);}						                            		
						                            		Thread.sleep(300);						                            		
						                            		bolcheck =true;
						                            	}
						                            	
						                            }
						                            if(bolcheck)strpassstatus+= "true";
						                            else strpassstatus+= "false";
												}
												if (!strpassstatus.contains("false"))strStatus +="true";
												else {
													strStatus +="false";
													appInd.writeLog("#","Provided snapshot check box not Found");
													System.out.println("Provided snapshot check box not Found");
												}
												
										}
									}								
									
						    }else{strStatus +="false";}// null check condition	 
						} //for loop split checkbox name 	
						
						
					// click on the Filter button
						strStatus += appInd.clickObject_js(obrowser, "CD_button_Filter");
						Thread.sleep(300);
						
						
					// validating the result	
						if (!strStatus.contains("false")) {bolstatus = true;}
						
						
			}
		catch(Exception e) {
			appInd.writeLog("Exception","Exception while executing 'changeDetection_filters' method. " + e.getMessage());
			bolstatus = false;
			}
		finally {
		 	return bolstatus;
			}
	}

	
	/********************************
	 * Method Name : CD_filtervalidation_countbased 
	 * Purpose : This method will vallidate the upon performing the filer operation of change detection in filer popup
	 * Author : Mahesh TK 
	 * Reviewer : 
	 * Date of Creation : 24-Jan-19
	 * Date of modification :
	 * ******************************
	 */
	@SuppressWarnings("finally")
	public boolean CD_filtervalidation_countbased(WebDriver obrowser,String strName) 
	{
		// System.out.println("CD_filtervalidation_countbased:- started" ); //Self testcode review
		boolean bolstatus = false;		
		int nrowcount = 0;		
		String strdisplycnt = null;
		String strstatus = null;
		
		try {		
							
			// checking for the grid table exists
			WebElement ele = obrowser.findElement(appInd.createAndGetByObject("CD_Label_showresult"));
			if (ele!=null) 
			{
					//get the rowcount of grid  exculding header 
					nrowcount =Integer.parseInt( CDGrid_count(obrowser,"get_totalrowcount"));
					appInd.writeLog("#","Rowcount: " +nrowcount );
					
					//get the displayed count 
					if (strName != null) 
				    {
							switch(strName.toLowerCase())
							 {    
							    							    	
							    case "acknowledged": 
							    	strdisplycnt= Changedetectioncount(obrowser,"acknowledged","get_count");
							    	Thread.sleep(500);
							    	Changedetectioncount(obrowser,"acknowledged","click");
							    	break;   							    	
							    	
							    case "suppressed": 
							    	strdisplycnt= Changedetectioncount(obrowser,"suppressed","get_count");
							    	Thread.sleep(500);
							    	Changedetectioncount(obrowser,"suppressed","click");
							    	break;  							    	
							    	
							    case "unacknowledged": 
							    	strdisplycnt= Changedetectioncount(obrowser,"unacknowledged","get_count");
							    	Thread.sleep(500);
							    	Changedetectioncount(obrowser,"unacknowledged","click");
							    	break; 							    	
							    	
							    case "all": 
							    	strdisplycnt= Changedetectioncount(obrowser,"allchanges","get_count");
							    	Thread.sleep(500);
							    	Changedetectioncount(obrowser,"allchanges","click");
							    	break;		
							    	
							    default:
							    	System.out.println("Not Found");    
							    }    
					
					if(strdisplycnt!=null) {
						//Compare with total change detection count  in showing result with dispaly counts above the grid
						if (nrowcount != (Integer.parseInt( strdisplycnt))){
							strstatus +="fail";
							appInd.writeLog("#", "Mismatch display count Expected Totalcount: "+nrowcount + "Actual count displayed : "+strdisplycnt);}
						else strstatus +="pass";}
					
					
					//load all the records
					if (CDGrid_count(obrowser,"allrecordsload")!=null) {strstatus +="pass";} 
					else strstatus +="fail";
					
					
					//check the count display count upon loading all the records also Compare with total anomaly  in showing result with dispaly counts above the grid.						
						if (nrowcount != (Integer.parseInt( CDGrid_count(obrowser,"get_currentrowcount")))){
							strstatus +="fail";
							appInd.writeLog("#", "Mismatch display count Expected Totalcount: "+nrowcount + "Actual count displayed : "+strdisplycnt);}
						else{strstatus +="pass";}					
					
					// validating the result	
					if (!(strstatus.contains("fail"))){bolstatus = true;}	
					
				}
			}
		}
		catch(Exception e) {
			if (appInd.isElementPresent(obrowser, "CD_Tablehide_String")) 
			{
				bolstatus =true;
				appInd.writeLog("#","Grid Table is displayed but 'No change detection' displayed message in Change detection page");							
				
			}else {
			appInd.writeLog("Exception","Exception while executing 'CD_filtervalidation_countbased' method. " + e.getMessage());
			bolstatus = false;	}		
			}
		finally {return bolstatus;}	
	}



	/********************************
	 * Method Name : Changedetectioncount 
	 * Purpose : This method will get the CD count from the change Detection main page
	 * Author : Mahesh TK 
	 * Reviewer : 
	 * Date of Creation : 20-Feb-19
	 * Date of modification :
	 * ******************************
	 */
	public String Changedetectioncount(WebDriver obrowser,String stractions,String stropration) 
	{
		// System.out.println("Changedetectioncount:- started" ); //Self testcode review
		
		String strretunvalue="0";
		String strlogicalName=null;
		
		try {			
				//get the TotalResult count in label Showing Results
				WebElement eleshwres = obrowser.findElement(appInd.createAndGetByObject("CD_Label_showresult"));
				if (eleshwres!=null) 
				{
					//get the action count
					switch(stractions.toLowerCase())
					 {    
					    case "allchanges":
					    	strlogicalName="CD_Label_allchanges";			    	
					    	break; 
					    	
					    case "unacknowledged": 
					    	strlogicalName="CD_Label_unacknowledged";			    	
					    	break; 
					    	
					    case "acknowledged": 
					    	strlogicalName="CD_Label_acknowledged";			    	
					    	break; 
		
					    case "suppressed": 
					    	strlogicalName="CD_Label_suppressed";	
					    	break; 	 
					 }
				
			
			
					if (stropration.equalsIgnoreCase("get_count")) 
					{ 			
						
						// get the count value from the above the grid
						WebElement ele=obrowser.findElement(appInd.createAndGetByObject(strlogicalName));
						delay(obrowser,"CD_Ajax_Loading_delay");
						String	strvalue=ele.getText();
										
						//split and get the count
						if (strvalue!=null)
						{
							String strspltval[] =strvalue.split("\n");				
							if(strspltval[0] != null) {strretunvalue =strspltval[0] ;}
						}
					
					}
					else 
					{
						appInd.clickObject_js(obrowser,strlogicalName);
						delay(obrowser,"CD_Ajax_Loading_delay");
					}		
				}
			}
		catch(Exception e) 
		{
			if (appInd.isElementPresent(obrowser, "CD_Tablehide_String")) 
			{
				strretunvalue ="-1";
				appInd.writeLog("#","Grid Table is displayed but 'no change detection' displayed message in Change detection page");				
			}else {	appInd.writeLog("Exception","Exception while executing 'Changedetectioncount' method. " + e.getMessage());}			
		}
		finally {return strretunvalue;}	
	}
	



	/********************************
	 * Method Name : CDGrid_count 
	 * Purpose : This method will get the count from the Change detection main page upon moving to the last row
	 * Author : Mahesh TK 
	 * Reviewer : 
	 * Date of Creation : 20-Feb-19
	 * Date of modification :
	 * ******************************
	 */
	@SuppressWarnings("finally")
	public String CDGrid_count(WebDriver obrowser,String stroperation) 
	{
		// System.out.println("CDGrid_count:- started" ); //Self testcode review
		int ntotalrecords =0;
		String strretunvalue = null;
		String strresults=null;
		boolean bolget_allstatus = false;
		
		try {
				// checking for the grid table exists
				WebElement ele = obrowser.findElement(appInd.createAndGetByObject("CD_Label_showresult"));
				if (ele!=null) 
				{			
					delay(obrowser,"CD_Ajax_Loading_delay");
					WebElement SRele = obrowser.findElement(appInd.createAndGetByObject("CD_Label_count"));
					if (SRele!=null)  { strresults =SRele.getText();}
						
						//Extract the total records
						String strsplitarr[] =strresults.split("/");
						ntotalrecords = Integer.parseInt(strsplitarr[1]);		   			
				       		
							//get the mainGridtable element 
							WebElement webtableelement=obrowser.findElement(appInd.createAndGetByObject("CD_Table_MainGrid"));						
							
							if (webtableelement != null) 
							{				
								//iterate the mainGridtable based on the stroperation
									switch(stroperation.toLowerCase())
									 {    
									    case "get_totalrowcount":
									    	//get the row count based on the checkbox															
									    	strretunvalue= String.valueOf(ntotalrecords) ;
									    	Thread.sleep(300);
									    	break; 
									    	
									    case "get_currentrowcount": 
									    	 //get the current column count																 
									    	strretunvalue= String.valueOf(strsplitarr[0]) ;
									    	Thread.sleep(300);
									    	break;   
									    	
									    case "allrecordsload": 
									    	bolget_allstatus = true ;
									    	break; 						 
									 }
									
									if (!((stroperation.contentEquals("get_rowcount")) || (stroperation.contentEquals("get_columncount"))) && (bolget_allstatus != false))
									{	
										int ntotclicks=(ntotalrecords/5);
										for (int rownum=0;rownum < ntotclicks ;rownum++)
										{	
											if(ntotalrecords>10) 
											{
												String temp="10";
												WebElement Element = obrowser.findElement(By.xpath("(//*[contains(@id,'"+String.valueOf(temp)+"-uiGrid-')]/div)[1]"));	
											     //This will scroll the page till the element is found			        
												if(Element != null)
												{
													 JavascriptExecutor js = (JavascriptExecutor) obrowser;
													 js.executeScript("arguments[0].scrollIntoView();", Element);	
													 if (ntotalrecords>50) {delay(obrowser,"CD_Ajax_Loading_delay");}
												}
											}
										}
										strretunvalue="pass" ;
										Thread.sleep(300);
									}			
							}else {appInd.writeLog("#","Grid Table not displayed in Change Detection");}						
					}
			}
		catch(Exception e) 
		{
			if (appInd.isElementPresent(obrowser, "CD_Tablehide_String")) 
			{
				strretunvalue ="-1";
				appInd.writeLog("#","Grid Table is displayed but 'no change detection' displayed message in Change detection page");				
			}else {
				appInd.writeLog("Exception","Exception while executing 'CDGrid_count' method. " + e.getMessage());					
				strretunvalue =null;}			
		}
		finally {return strretunvalue;}	
	}
	
	/********************************
	 * Method Name : change_detection__selectsystem 
	 * Purpose : This method will perform the clicking the dropDeon and selecting the System name
	 * Author : Maruthiraja BN 
	 * Reviewer : 
	 * Date of Creation : 21-Feb-19
	 * Date of modification :
	 * ******************************/
	
	
	
	public boolean change_detection__selectsystem(WebDriver obrowser,String sysName) 
	{
		// System.out.println("menu_EngeeringAnomoly_Navigation:- started" ); //Self testcode review
		boolean bolstatus = false;
		try 
		{
				
			By byCD_Dropdwn_System = appInd.createAndGetByObject("CD_Dropdwn_System");
			WebElement elementbyCD_Dropdwn_System = obrowser.findElement(byCD_Dropdwn_System);
			((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",
					elementbyCD_Dropdwn_System);
			
			List <WebElement> system= obrowser.findElements(By.xpath("//html/body/div[4]/div[1]/div/div/form/div[1]/div/div/ul/li/a"));
			System.out.println(system.size());
	
			String dropDown=null;
			int row;
			for (row = 1;row<=system.size(); row++) 
			{
				try 
				{
					dropDown = system.get(row).getText();
					if(dropDown.equalsIgnoreCase(sysName))

					{							
						WebElement element =system.get(row);
						((JavascriptExecutor) obrowser).executeScript("arguments[0].click();", element);
						bolstatus=true;
						break;
					}
	
				}
				catch(Exception e)
				{
					bolstatus =false;
					appInd.takeSnapShot(obrowser, System.getProperty("user.dir")
					+ "\\Results\\snapshot\\ChangeDetection\\filterselect.png");
					appInd.writeLog("Fail", "'filterselect' script was failed");
				}
			}	
				if(row>system.size())
				{
				bolstatus=false;
				}
	
	
		}
		catch(Exception e) {
			appInd.writeLog("Exception","Exception while executing 'change_detection__selectsystem' method. " + e.getMessage());
			bolstatus = false;
			}
		finally {
		 	return bolstatus;
			}
	}
	
	/********************************
	 * Method Name : changeDetection_filters 
	 * Purpose : This method will perform the Save filter
	 * Author : Maruthiraja BN 
	 * Reviewer : 
	 * Date of Creation : 21-Feb-19
	 * Date of modification :
	 * ******************************/
	
	public boolean changeDetectionsavefilter(String strSave_Filter,String strFilter_Name,String strDescription)
	{
		boolean bolstatus = false;
		String strStatus=null;
		try
		{
			switch(strSave_Filter.toLowerCase())
			{
			case "public":
				// it will cilck the public Radio button
				strStatus +=appInd.clickObject_js(oBrowser, "Save_Filter_Public"); 
				Thread.sleep(2000);
				break;
				
			case "private":
				//it will click the private Radio button
				strStatus +=appInd.clickObject_js(oBrowser, "Save_Filter_Private"); 
				break;
				
				default:
					appInd.writeLog("Fail","Invalid Save Filter"); 
			}
			
			strStatus +=appInd.click_and_clearObject(oBrowser, "Filter_Name");
			strStatus +=appInd.setObject(oBrowser, "Filter_Name", strFilter_Name);
			
			strStatus +=appInd.click_and_clearObject(oBrowser, "Filter_Description");
			strStatus += appInd.setObject(oBrowser, "Filter_Description", strDescription);
			Thread.sleep(2000);
			
			//click on the save filter button
			strStatus +=appInd.clickObject_js(oBrowser, "Save_Filter_Click_button"); 
			Thread.sleep(2000);
			
			
			  
			
			//**********************************************************************************
			try
			{
		
				By byCD_Dropdwn_System = appInd.createAndGetByObject("popup_Ok_CD_Button");
				WebElement ole = oBrowser.findElement(byCD_Dropdwn_System);
				
				if(ole.isDisplayed())
				{	
					strStatus += "false";
					bolstatus=false;
				}
				else
				{
					strStatus +=validation_Saved_Filters(strFilter_Name, strSave_Filter);
					Thread.sleep(3000);
				}
				Thread.sleep(2000);
			}
			catch(Exception e)	
			{
					//It will click the logout button
					appInd.clickObject_js(oBrowser, "Logout"); 
					bolstatus=true;
			}
		
			
			//****************************************************************************************
			
		}
		catch(Exception e1)
		{
			appInd.writeLog("Exception","Exception while executing 'changeDetection_filters' method. "+e1.getMessage());
	
		}
		
		finally
		{
			return bolstatus;
		}
	}
	/********************************
	 * Method Name : validation_Saved_Filters 
	 * Purpose : This method will perform the Save filter
	 * Author : Maruthiraja BN 
	 * Reviewer : 
	 * Date of Creation : 22-Feb-19
	 * Date of modification :
	 * ******************************/

	public boolean validation_Saved_Filters(String strFilter_Name,String FilterType)
	{
		String strStatus=null;
		boolean bolstatus=false;
		try
		{
			// click on the saved Filter
			strStatus +=appInd.clickObject_js(oBrowser, "Saved_Filter_click");  
			
			WebElement ole=oBrowser.findElement(By.xpath("//div//ul//li//label//span[text()='"+strFilter_Name+"']"));
			
			if(ole.isDisplayed())
			{
				System.out.println(strFilter_Name+" Saved Filter Successfully");
			}
			
			if(!strStatus.contains("false"))
			{
				bolstatus=true;
			}
			Thread.sleep(2000);
		}
		catch(Exception e)
		{
			if(FilterType.equalsIgnoreCase("private"))
			{
				System.out.println(strFilter_Name+" Saved Filter as private  Successfully");
				bolstatus=true;
			}
			else
			{
				bolstatus=false;
			}
			
		}
		finally
		{
			// click on the saved Filter
			strStatus +=appInd.clickObject_js(oBrowser, "Saved_Filter_click");
			return bolstatus;
		}
	}
	
	
	//Change detection actions method//
	
	
	/********************************
	* Method Name : Change_Detection_Actions 
	* Purpose : This method will perform the actions 
	* Author : Mahesh TK 
	* Reviewer : 
	* Date of Creation : 23-Jan-19
	* Date of modification :
	* ******************************/
	public boolean Change_Detection_Actions(WebDriver obrowser,String strAction,String date,String Title) 
	{
		// System.out.println("menu_EngeeringAnomoly_Navigation:- started" ); //Self testcode review
		boolean bolstatus = false;		
		String strfilterActionName=null;
		int nrowcount = 0;		
		String strStatus = null;				
		boolean bolnullstatus=false;
		String strlogicaldrpdwn=null;
		boolean bolfilterstatus =false;
		String strlogicalcomment =null;
		String strlogicalbtnname =null;
		
		
		try {
			// checking for the grid table exists
			WebElement eleSR = obrowser.findElement(appInd.createAndGetByObject("CD_Label_showresult"));
			if (eleSR!=null) 
			{
			
			//Perform the Action operation on provided ActionName		 
		    if (strAction != null) 
		    {
					switch(strAction.toLowerCase())
					{	
					    case "acknowledge":
						strlogicaldrpdwn="Action_Acknowledge";
						strlogicalcomment="Comment_acknowledge";
						strlogicalbtnname="AcknowledgeBtn";
				    	break; 
						
					    case "unacknowledge":
							strlogicaldrpdwn="Action_Unacknowledge";
							strlogicalcomment="Comment_unacknowledge";
							strlogicalbtnname="UnacknowledgeBtn";
					    	break;
				    	
				    	
					    case "assign":					    								
							//strfilterActionName="Unassigned";
							strlogicaldrpdwn="Action_AssignCR";
							strlogicalcomment="Comment_acknowledge";
							strlogicalbtnname="AssignBtn";
						    	break; 
						    	
					    case "unassign": 
					    	
					    //	strfilterActionName="assigned";
					    	strlogicaldrpdwn="Action_UnassignCR";
					    	strlogicalcomment="Comment_unassign";
					    	strlogicalbtnname="UnassignBtn";
						    	break; 		   		    	
						    	
					  
					    case "suppress": 
					    	
					    	//strfilterActionName="unsuppressed";
					    	strlogicaldrpdwn="Action_Suppress";
					    	strlogicalcomment="Comment_acknowledge";
					    	strlogicalbtnname="SuppressBtn";
						    	break;    
					    	
					    case "unsuppress": 
					    	
					    	//strfilterActionName="suppressed";
					    	strlogicaldrpdwn="Action_Unsuppress";
					    	strlogicalcomment="Comment_unsuppress";
					    	strlogicalbtnname="UnsuppressBtn";
						    	break; 									   
					    	
					    default:									    	
					    	
					    	bolnullstatus = true;
					    	System.out.println("Not Found");    
					    }    
					//String sts=	null;
				//	if (bolfilterstatus != true) 
					//{
						//Filters based on the provided Action name			
						//strStatus += Engr_anomoly_filters(obrowser,strfilterActionName,"none");
						//Thread.sleep(250);
						
						//click on the anamoly above the grid  to default 
						//sts = Anomalycount(obrowser,"allanomalies","click");
				//	}						
					//else {sts = Anomalycount(obrowser,"unresolved","click");}
					delay(obrowser,"ajax_loading");
			//	if(!sts.equalsIgnoreCase("-1"))	{
					//Select the 1channels from the Grid
					//get the row count 
					nrowcount =Integer.parseInt(MainGrid_Operation_CD(obrowser,"get_rowcount",0 ,0, "" ));
					// check the acknowledged
					if(nrowcount > 0)
						nrowcount=2;
					for (int i=1 ;i<=nrowcount;i++) 
					{
						MainGrid_Operation_CD(obrowser,"click",i ,1, "" );							
					}
					if (!bolnullstatus && nrowcount > 0)
					{
						By byClick_CDACtion = appInd.createAndGetByObject("Click_CDACtion");
						//Thread.sleep(3000);
						WebElement elementto = obrowser.findElement(byClick_CDACtion);
						((JavascriptExecutor) obrowser).executeScript("arguments[0].click();", elementto);
						Thread.sleep(3000);
							
						//	appInd.clickObject(obrowser, "Click_CDACtion");
						
							WebElement ele =obrowser.findElement(appInd.createAndGetByObject(strlogicaldrpdwn));
							if(ele !=null) {((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",ele);}
							 		
						//action perform based on the action type								
						if(strAction.equalsIgnoreCase("assign")) 
						{
							// select the  1st user name by default						
							WebElement userele =obrowser.findElement(appInd.createAndGetByObject("Action_UserName"));
							if(ele !=null) {((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",userele);}
						}
						
						else if(strAction.equalsIgnoreCase("suppress")) 
						{
							//Click and enter suppress title
                         By byCD_createTitlesuppress = appInd.createAndGetByObject("CD_createTitlesuppress");
                     	WebElement element = obrowser.findElement(byCD_createTitlesuppress);
						((JavascriptExecutor) obrowser).executeScript("arguments[0].click();", element);
						Thread.sleep(5000);
						

						appInd.setObject(obrowser, "CD_createTitlesuppress", Title);
						Thread.sleep(3000);
						
						//Click and select From date
						By byCDclick_FromDate = appInd.createAndGetByObject("CDclick_FromDate");
                        element = obrowser.findElement(byCDclick_FromDate);
						((JavascriptExecutor) obrowser).executeScript("arguments[0].click();", element);
						Thread.sleep(5000);
						
						
						
					By byCD_SuppressFromDate = appInd.createAndGetByObject("CD_SuppressFromDate");
							List<WebElement> CD_SuppressFromDate = obrowser.findElements(byCD_SuppressFromDate);
							
							((JavascriptExecutor) obrowser).executeScript("arguments[0].click();", CD_SuppressFromDate.get(CD_SuppressFromDate.size()-1));

							//Select To date
							By byCD_Suppress_Todate = appInd.createAndGetByObject("CD_Suppress_Todate");
	                        element = obrowser.findElement(byCD_Suppress_Todate);
							((JavascriptExecutor) obrowser).executeScript("arguments[0].click();", element);
							Thread.sleep(5000);
							
							//Calender method
							calender(oBrowser, date);
							
						
						//Enter suppress comment
							By byCD_SuppressComment = appInd.createAndGetByObject("CD_SuppressComment");
	                        element = obrowser.findElement(byCD_SuppressComment);
							((JavascriptExecutor) obrowser).executeScript("arguments[0].click();", element);
							Thread.sleep(3000);
						
							appInd.setObject(obrowser, "CD_SuppressComment", strAction);
							
							//Click on Suppress done button
							By bySuppress_CD_OK = appInd.createAndGetByObject("Suppress_CD_OK");
	                        element = obrowser.findElement(bySuppress_CD_OK);
							((JavascriptExecutor) obrowser).executeScript("arguments[0].click();", element);
							Thread.sleep(5000);
							
							
							
							
						}
						
						else
						{	
						//clear on the comment window
						//obrowser.findElement(By.xpath("//*[@id=\"formDefectAssignment\"]/div[2]/textarea")).clear();						
				
						//set the comments 
						strStatus +=appInd.setObject(obrowser, strlogicalcomment, strAction);
						Thread.sleep(350);
						
						//click on the button in action window upon setting comments
						strStatus +=appInd.clickObject_js(obrowser, strlogicalbtnname);
						}	
					}
					
				else { strStatus +="false"; } // null check condition							
		    }		
					// validating the result	
						if (!strStatus.contains("false")){
							bolstatus = true;
						} 
		}
			}
		catch(Exception e) {
			if (appInd.isElementPresent(obrowser, "EngrAnomoly_Tablehide_String")) 
			{
				bolstatus =false;
				appInd.writeLog("#","Grid Table is displayed but 'no anaomly' displayed message in Engineering anomaly page");							
				
			}else {
			appInd.writeLog("Exception","Exception while executing 'Change_Detection_Actions' method. " + e.getMessage());
			bolstatus = false;}
			}
		finally {
			return bolstatus;
			}	
	}

	public String MainGrid_Operation_CD(WebDriver obrowser,String stroperation,int rownum ,int columnnum, String strvalue ) 
	{
		// System.out.println("webTable_Operation:- started" ); //Self testcode review
		boolean bolget_innertextstatus = false;		
		boolean bolclickstatus = false;
		boolean bolget_attributeValuestatus = false;
		int ntotalrecords =0;
		int ntabrow=0;
		String strretunvalue = null;
		WebElement eletemp =null;
		String strresults =null;
		
		
		try {
			// checking for the grid table exists
			WebElement ele = obrowser.findElement(appInd.createAndGetByObject("CD_Label_count"));
		if (ele!=null) 
		{		
			strresults =ele.getText();
			
			//Extract the total records
			if(strresults!=null) {
			String strsplitarr[] =strresults.split("/");
			ntotalrecords = Integer.parseInt(strsplitarr[1]);					
			}
				//get the mainGridtable element 
				WebElement webtableelement=obrowser.findElement(appInd.createAndGetByObject("CD_Label_showresult"));
								
				if (webtableelement != null) 
				{				
					//iterate the mainGridtable based on the stroperation
						switch(stroperation.toLowerCase())
						 {    
						    case "get_rowcount":
						    	//get the row count based on the checkbox															
						    	strretunvalue= String.valueOf(ntotalrecords) ;
						    	break; 
						    	
						    case "get_columncount": 
						    	 //get the column count based onthe  first row					
								List<WebElement> eletable = obrowser.findElements(By.xpath("//*[contains(@id,'"+String.valueOf(0)+"-uiGrid-')]"));								 
						    	strretunvalue= String.valueOf(eletable.size()) ;
						    	break;   
						    	
						    case "get_innertext": 
						    	bolget_innertextstatus = true ;
						    	break;    
						    	
						    case "get_attributevalues": 
						    	bolget_attributeValuestatus = true ;
						    	break; 						    	
						   						    	
						    case "click": 
						    	bolclickstatus = true;
						    	break;  
						 }
						
						if (!((stroperation.contentEquals("get_rowcount")) || (stroperation.contentEquals("get_columncount"))))
						{
							
							
							if ((rownum > 15) && (rownum < ntotalrecords ))
								
							{
								int cntmoduls =rownum % 15;	
								
								 //load the elemnts into the grid     
								if (cntmoduls == 1 || cntmoduls ==6|| cntmoduls==11) {					
									
									String temp="10";
									 WebElement Element = obrowser.findElement(By.xpath("(//*[contains(@id,'"+String.valueOf(temp)+"-uiGrid-')]/div)[1]"));

								     //This will scroll the page till the element is found			        
									 JavascriptExecutor js = (JavascriptExecutor) obrowser;
									 js.executeScript("arguments[0].scrollIntoView();", Element);
									 if (ntotalrecords>100) {delay(obrowser,"ajax_loading");}
									
								} 
									//assign the new counter pushing 5 records default
																	
									ntabrow =(rownum)-(rownum % 15);
									if (ntabrow>15)
									{
										ntabrow=0;
									}
									System.out.println("rowID:--"+rownum + "New value:--" +ntabrow  );							
							}else
							{
								ntabrow =rownum;
							}
							
							//get the row element 0th row not present
							if (ntabrow !=0) {
							String strxpath ="(//*[contains(@id,'-"+String.valueOf(ntabrow-1)+"-uiGrid-')])["+columnnum+"]";
							 eletemp = obrowser.findElement(By.xpath(strxpath));		
							}				
						
						//return the value based on the stroperation 
						
						if (bolget_innertextstatus)
						{
							//strretunvalue = rowele.get(columnnum-1).getText(); eletemp
							strretunvalue = eletemp.getText(); 
						}
						
						else if (bolget_attributeValuestatus)
						{
							String strspltarr[] =strvalue.split(":");														
							WebElement spanele =eletemp.findElement(By.tagName(strspltarr[0]));
							strretunvalue=spanele.getAttribute(strspltarr[1]);											
						}
						else if(bolclickstatus) 
						{
							List<WebElement> rowchkele = obrowser.findElements(By.xpath("//*[@role='checkbox']"));  
                                            //WebElement echkbx =eletemp.findElement(By.xpath("//*[@role=\"checkbox\"]"));                                 
                                            WebElement echkbx=rowchkele.get(rownum-1);							
							if (echkbx != null) 
							 {
								 // checking for the checkbox status
								 if (! (echkbx.isSelected()))
								 {
									 ((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",echkbx);
								 }
							 }
							strretunvalue ="true";							
						}	
					}
				
				}
				else {											
							strretunvalue ="fail";
						appInd.writeLog("#","Grid Table not displayed in Engineering anomaly");
						
					}
				//System.out.println("row id: "+rownum +" actual value: "+strretunvalue);
		 			
			}
		}
		catch(Exception e) 
		{
			if (appInd.isElementPresent(obrowser, "EngrAnomoly_Tablehide_String")) 
			{
				strretunvalue ="-1";
				appInd.writeLog("#","Grid Table is displayed but 'no anaomly' displayed message in Engineering anomaly page");							
				
			}else {
			appInd.writeLog("Exception","Exception while executing 'MainGrid_Operation' method. " + e.getMessage());					
			strretunvalue =null ;	}
			
		}
		finally 
		{
		 	return strretunvalue;
		}	
	}	
	
	
	
	public boolean change_detection__filters(WebDriver obrowser,String strCheckboxName) 
	{
		// System.out.println("menu_EngeeringAnomoly_Navigation:- started" ); //Self testcode review
		boolean bolstatus = false;
		String strLogicalName=null;
		String strStatus = null;
		String arr[]= {"CD_chkbox_Added","CD_chkbox_Modified","CD_chkbox_Deleted","CD_chkbox_Acknowledged","CD_chkbox_Unacknowledged","CD_chkbox_suppressed","CD_chkbox_unsuppressed","CD_chkbox_Assigned","CD_chkbox_Unassigned"};
		
		
		try {
				
								
					//click on the Filter icon  			
					strStatus += appInd.clickObject_js(obrowser, "CD_icon_Filter");
					Thread.sleep(250);
					
					//click on the Reset button to default 
					strStatus += appInd.clickObject_js(obrowser, "CD_button_reset");
					Thread.sleep(200);
					
							
					
						
						
					//Perform the checkbox operation on provided checkbox name 	
						
					//split the strChkbxName if multiple name provied  
						String strChkbxName[]=strCheckboxName.split(";");
						for (String  strName : strChkbxName) 
						{ 
						    if (strName != null) 
						    {
									switch(strName.toLowerCase())
									 {    
									    case "added": 
									    	strLogicalName= "CD_chkbox_Added" ;
									    	break; 
									    	
									    case "modified": 
									    	strLogicalName= "CD_chkbox_Modified" ;
									    	break;   
									    	
									    case "deleted": 
									    	strLogicalName= "CD_chkbox_Deleted" ;
									    	break;    
									    	
									    case "acknowledged": 
									    	strLogicalName= "CD_chkbox_Acknowledged" ; 
									    	break;      
									    	
									    case "unacknowledged": 
									    	strLogicalName= "CD_chkbox_Unacknowledged";
									    	break;      
									    	
									    case "suppressed": 
									    	strLogicalName= "CD_chkbox_suppressed";
									    	break;    
									    	
									    case "unsuppressed": 
									    	strLogicalName= "CD_chkbox_unsuppressed";
									    	break;    
									    	
									    case "assigned": 
									    	strLogicalName= "CD_chkbox_Assigned" ;
									    	break;  
									    	
									    case "unassigned": 
									    	strLogicalName= "CD_chkbox_Unassigned";
									    	break;    
									    	
									  									    	
									    default:
									    	System.out.println("Not Found");    
									    }    
									for(int i=0;i<arr.length;i++)
									{
									//click on the checkbox based on the status
									 if ((strLogicalName != null)&&(!(arr[i].equalsIgnoreCase(strLogicalName))) ) 
									 {
										 // Getting the check box webelemnt 
										 WebElement elechkbx = obrowser.findElement(appInd.createAndGetByObject(arr[i]));
										 
										 if (elechkbx != null) 
										 {
											 // checking for the checkbox status
											 if (! (elechkbx.isSelected()))
											 {
												 ((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",elechkbx);
												 Thread.sleep(200);
											 }
										 }
										 
									  }
									}//for loop click
						    }// null check condition	 
						} //for loop split checkbox name 	
						
						
					// click on the Filter button
						strStatus += appInd.clickObject_js(obrowser, "CD_button_Filter");
						
						
						
					// validating the result	
						if (!strStatus.contains("false")){
							bolstatus = true;
						} 
						
			}
		catch(Exception e) {
			appInd.writeLog("Exception","Exception while executing 'Change_detection_filters' method. " + e.getMessage());
			bolstatus = false;
			}
		finally {
		 	return bolstatus;
			}
	}
		public boolean change_detection_filters(WebDriver obrowser,String strCheckboxName) 
	{
		// System.out.println("menu_EngeeringAnomoly_Navigation:- started" ); //Self testcode review
		boolean bolstatus = false;
		String strLogicalName=null;
		int nrowcount = 0;
		int ncolumncount = 0;
		String strStatus = null;
		int ntotrowcount=0;
		String arr[]= {"CD_chkbox_Added","CD_chkbox_Modified","CD_chkbox_Deleted","CD_chkbox_Acknowledged","CD_chkbox_Unacknowledged","CD_chkbox_suppressed","CD_chkbox_unsuppressed","CD_chkbox_Assigned","CD_chkbox_Unassigned"};
		
		
		try {			
								
					//click on the Filter icon  			
					strStatus += appInd.clickObject_js(obrowser, "CD_icon_Filter");
					Thread.sleep(250);
					
					//click on the Reset button to default 
					strStatus += appInd.clickObject_js(obrowser, "CD_button_reset");
					Thread.sleep(200);
					
							
						
						
					//Perform the checkbox operation on provided checkbox name 	
						
					//split the strChkbxName if multiple name provied  
						String strChkbxName[]=strCheckboxName.split(";");
						for (String  strName : strChkbxName) 
						{ 
						    if (strName != null) 
						    {
									switch(strName.toLowerCase())
									 {    
									    case "added": 
									    	strLogicalName= "CD_chkbox_Added" ;
									    	break; 
									    	
									    case "modified": 
									    	strLogicalName= "CD_chkbox_Modified" ;
									    	break;   
									    	
									    case "deleted": 
									    	strLogicalName= "CD_chkbox_Deleted" ;
									    	break;    
									    	
									    case "acknowledged": 
									    	strLogicalName= "CD_chkbox_Acknowledged" ; 
									    	break;      
									    	
									    case "unacknowledged": 
									    	strLogicalName= "CD_chkbox_Unacknowledged";
									    	break;      
									    	
									    case "suppressed": 
									    	strLogicalName= "CD_chkbox_suppressed";
									    	break;    
									    	
									    case "unsuppressed": 
									    	strLogicalName= "CD_chkbox_unsuppressed";
									    	break;    
									    	
									    case "assigned": 
									    	strLogicalName= "CD_chkbox_Assigned" ;
									    	break;  
									    	
									    case "unassigned": 
									    	strLogicalName= "CD_chkbox_Unassigned";
									    	break;   
									    case "threemonths": 
									    	strLogicalName= "CD_Radiobtn_last3mnoth";
									    	break;    
									    	
									    case "sixmonths": 
									    	strLogicalName= "CD_Radiobtn_last6mnoth" ;
									    	break;  
									    	
									    case "oneyear": 
									    	strLogicalName= "CD_Radiobtn_1year";
									    	break;   
									    case "multiple": 
									    	strLogicalName= "CD_Radiobtn_multiple";
									    	break;
									    case "from": 
									    	strLogicalName= "CD_Radiobtn_From";
									    	break;   
									   								    									  									    	
									    default:
									    	System.out.println("Not Found");    
									    } 
									
									for(int i=0;i<arr.length;i++)
									{
									//click on the checkbox based on the status
									 if ((strLogicalName != null)&&(!(arr[i].equalsIgnoreCase(strLogicalName))) ) 
									 {
										 // Getting the check box webelemnt 
										 WebElement elechkbx = obrowser.findElement(appInd.createAndGetByObject(arr[i]));
										 
										 if (elechkbx != null) 
										 {
											 // checking for the checkbox status
											 if (! (elechkbx.isSelected()))
											 {
												 ((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",elechkbx);
												 Thread.sleep(200);
											 }
										 }
										 
									  }
									}//for loop click
									
								if (strName.equalsIgnoreCase("threemonths")||strName.equalsIgnoreCase("sixmonths")||strName.equalsIgnoreCase("oneyear")||strName.equalsIgnoreCase("CD_Radiobtn_multiple")){
									{
										WebElement elechkbx = obrowser.findElement(appInd.createAndGetByObject(strLogicalName));
										 
										 if (elechkbx != null) 
										 {
											 // checking for the checkbox status
											 if (! (elechkbx.isSelected()))
											 {
												 ((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",elechkbx);
												 Thread.sleep(200);
											 }
										 }
										
									}
								}
									else if(strName.equalsIgnoreCase("from"))
									{ WebElement elechkbx = obrowser.findElement(appInd.createAndGetByObject(strLogicalName));
									 
									 if (elechkbx != null) 
									 {
										 // checking for the checkbox status
										 if (! (elechkbx.isSelected()))
										 {
											 ((JavascriptExecutor) obrowser).executeScript("arguments[0].click();",elechkbx);
											 Thread.sleep(200);
										 }
										 strStatus += appInd.clickObject_js(obrowser, "CD_calender_dropwn"); 
										 calender( obrowser,"23-Jan-2018"); 
										 strStatus += appInd.clickObject_js(obrowser, "CD_from_to"); 
										 calender( obrowser,"23-Jan-2019"); 
									 }
									 
									}
								
						    }// null check condition	 
						} //for loop split checkbox name 	
						
						
					// click on the Filter button
						strStatus += appInd.clickObject_js(obrowser, "CD_button_Filter");
						
						
						
					// validating the result	
						if (!strStatus.contains("false")){
							bolstatus = true;
						} 
						
			}
		catch(Exception e) {
			appInd.writeLog("Exception","Exception while executing 'Engr_anomoly_filters' method. " + e.getMessage());
			bolstatus = false;
			}
		finally {
		 	return bolstatus;
			}
	}
	@SuppressWarnings("finally")
	public boolean calender(WebDriver obrowser,String date) 
	{
		// System.out.println("calender:- started" ); //Self testcode review
		String strStatus=null;
		boolean bolstatus = false;
		String cal =null;
		
		try {		
			
			//Splitting the MM/DD/YYYY
			String datesplit[]=date.split("/");
			   String   dd=datesplit[1];
			    String mm=datesplit[0];
			    int yy=Integer.parseInt(datesplit[2]);
			    switch(mm)
			    {
			    
			    case "01":
	    	         mm="Jan";
	    	         break;
			    case "02":
	    	         mm="Feb";
	    	         break;
			    case "03":
	    	         mm="March";
	    	         break;
			    case "04":
	    	         mm="April";
	    	         break;
			    case "05":
	    	         mm="May";
	    	         break;
			    case "06":
	    	         mm="June";
	    	         break;
			    case "07":
	    	         mm="July";
	    	         break;
			    case "08":
	    	         mm="August";
	    	         break;	
			    case "09":
	    	         mm="September";
	    	         break;	
			    case "10":
	    	         mm="October";
	    	         break;	
			    case "11":
	    	         mm="November";
	    	         break;	
			    case "12":
	    	         mm="December";
	    	         break;	    	        
			    	         
			    }
			
			    
			    //Getting year 
			    WebElement yearMonth = obrowser.findElement(appInd.createAndGetByObject("CD_Calender_year"));
			    if(yearMonth!=null)  cal=yearMonth.getText();
			    strStatus += appInd.clickObject_js(obrowser, "CD_Calender_year");
			   
			   //Split the year 
			    String arr[]=cal.split("-");
			    int  nyear =Integer.parseInt(arr[0]);
			    
			    if(yy!=nyear)
			    {//strStatus += appInd.clickObject_js(obrowser, "CD_Calender_year");
				    if(yy<nyear)
				    {
				    	while(yy!=nyear)
				    	{
				    		appInd.clickObject_js(obrowser, "CD_Calender_back");
				    		 yearMonth = obrowser.findElement(appInd.createAndGetByObject("CD_Calender_year"));
							    cal=yearMonth.getText();
							    String arr1[]=cal.split("-");
							    nyear =Integer.parseInt(arr1[0]);
				    	}
				    }else
				    { 
				    	while(yy!=nyear)
				    	{
				    	 appInd.clickObject_js(obrowser, "CD_Calender_forward");
			    		 yearMonth = obrowser.findElement(appInd.createAndGetByObject("CD_Calender_year"));
						    cal=yearMonth.getText();
						    String arr1[]=cal.split("-");
						    nyear =Integer.parseInt(arr1[0]);
				    	}			     
				    }
			    }//end of year
			    
			    //Get the Month 
			    List<WebElement> Month = obrowser.findElements(appInd.createAndGetByObject("CD_Calender_Month"));
				for (WebElement rowElement : Month) 
				{
					if (rowElement.getText().equalsIgnoreCase(mm)) {
						((JavascriptExecutor) obrowser).executeScript("arguments[0].click();", rowElement);
						strStatus +=true;
						break;
						}	

				}
				
				//Get the Date
				 List<WebElement>Date = obrowser.findElements(appInd.createAndGetByObject("CD_Calender_date"));
					for (WebElement rowElement : Date) 
					{
						if (rowElement.getText().equalsIgnoreCase(dd)) {
							((JavascriptExecutor) obrowser).executeScript("arguments[0].click();", rowElement);
							((JavascriptExecutor) obrowser).executeScript("arguments[0].click();", rowElement);
							strStatus +=true;
							break;
							
						}					

					}
					
					// validating the result	
					if (!strStatus.contains("false")){
						bolstatus = true;
					}
			}
		catch(Exception e) {
			appInd.writeLog("Exception","Exception while executing 'Calender' method. " + e.getMessage());
			bolstatus = false;
			}
		finally {return bolstatus;}
	}
	
	
	
	
	
//Facebook generic methods---------------------------------------------------------------------------------------
	
	
	
	/********************************
	 * Method Name : TC_CD_Facebook_login
	 * Purpose : Facebook login
	 * Author : Maruthiraja BN
	 * Reviewer : 
	 * Date of Creation : 10-Aug-2019 
	 * Date of modification :
	 * ******************************
	 */
	
	
public boolean login_Validation(WebDriver obrowser)
{
	boolean BlnStatus=false;
	try
	{
		appInd.isElementPresent(obrowser, "Login_Check");
		BlnStatus=true;
	}
	catch(Exception e)
	{
		appInd.writeLog("Exception", "The  login validation is failed"+e.getMessage());
	}
	finally
	{
		return BlnStatus;
	}
}
	
	
	
	
	
	
	
	
	
	
} //close of the GenricApplicationMethods






