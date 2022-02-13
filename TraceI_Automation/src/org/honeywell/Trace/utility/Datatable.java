package org.honeywell.Trace.utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.honeywell.Trace.driver.DriverScript;



public class Datatable extends DriverScript
{
	
	Utility oUtility = new Utility();

	
	
		/********************************
		 * Method Name			: getRowCount
		 * Purpose				: 
		 * Author				:
		 * Reviewer 			:
		 * Date of Creation		:
		 * Date of modification :
		 * ******************************
		 */
		public int getRowCount(String strFilePath, String strSheetName)
		{
			FileInputStream fin = null;
			Workbook wb = null;
			Sheet sh = null;
			int rowCount = 0;		
			try {
				fin = new FileInputStream(strFilePath);
				wb = new XSSFWorkbook(fin);
				for (int i = 0; i < wb.getNumberOfSheets(); i++)
				{
				    if( wb.getSheetName(i).equalsIgnoreCase(strSheetName)) {
				    	sh = wb.getSheet(strSheetName);
				    	break;
				    }				    
				}		
				
				if(sh==null) return -1;
				
				rowCount = sh.getPhysicalNumberOfRows();
				return rowCount;
			}catch(Exception e)
			{
				oUtility.writeLog("Fail","Exception while executing 'getRowCount' method. "+e.getMessage());
				return -1;
			}
			finally {
				try {
					fin.close();
					fin=null;
					sh=null;
					wb.close();
					wb=null;
				}catch(Exception e)
				{
					oUtility.writeLog("Fail","Exception while executing 'getRowCount' method. "+e.getMessage());
					return -1;
				}
			}
		}
		
		
		
		/********************************
		 * Method Name			: getCellData
		 * Purpose				: 
		 * Author				:
		 * Reviewer 			:
		 * Date of Creation		:
		 * Date of modification :
		 * ******************************
		 */
		public String getCellData(String strFilePath, String strSheetName, String strColName, int rowNum)
		{
			FileInputStream fin = null;
			Workbook wb = null;
			Sheet sh = null;
			Row row = null;
			Cell cell = null;
			int colNum = 0;
			String strData = null;
			try {
				fin = new FileInputStream(strFilePath);
				wb = new XSSFWorkbook(fin);
				//sh = wb.getSheet(strSheetName);
				for (int i = 0; i < wb.getNumberOfSheets(); i++)
				{
				    if( wb.getSheetName(i).equalsIgnoreCase(strSheetName)) {
				    	sh = wb.getSheet(strSheetName);
				    	break;
				    }				    
				}	
				
				if(sh==null) return null;
				
				//Findout the column number based on the given column name
				row = sh.getRow(1);
				for(int c=0;c<row.getPhysicalNumberOfCells();c++)
				{
					cell = row.getCell(c);
					if(cell.getStringCellValue().trim().equalsIgnoreCase(strColName))
					{
						colNum = c;
						break;
					}
				}
				
				row = sh.getRow(rowNum);
				cell = row.getCell(colNum);
				if(cell==null||cell.getCellTypeEnum()==cell.getCellTypeEnum().BLANK)
				{
					strData = "";
				}
				else if(cell.getCellTypeEnum()==cell.getCellTypeEnum().BOOLEAN)
				{
					strData = String.valueOf(cell.getBooleanCellValue());
				}
				else if(cell.getCellTypeEnum()==cell.getCellTypeEnum().STRING)
				{
					strData = cell.getStringCellValue();
				}
				else if(cell.getCellTypeEnum()==cell.getCellTypeEnum().NUMERIC)
				{
					if(HSSFDateUtil.isCellDateFormatted(cell))
					{
						double dt = cell.getNumericCellValue();
						Calendar cal = Calendar.getInstance();
						cal.setTime(HSSFDateUtil.getJavaDate(dt));
						strData = String.valueOf(cal.get(Calendar.DAY_OF_MONTH))+"/"+String.valueOf((cal.get(Calendar.MONTH)+1))+"/"+String.valueOf(cal.get(Calendar.YEAR));
					}else {
						strData = String.valueOf(cell.getNumericCellValue());
						
					}
				}
				return strData;
			}catch(Exception e)
			{
				oUtility.writeLog("Fail","Exception while executing 'getCellData' method. "+e.getMessage());
				return null;
			}
			finally {
				try {
					fin.close();
					fin=null;
					cell=null;
					row=null;
					sh=null;
					wb.close();
					wb=null;
				}catch(Exception e)
				{
					oUtility.writeLog("Fail","Exception while executing 'getCellData' method. "+e.getMessage());
					return null;
				}
			}
		}
		
		
		
		/********************************
		 * Method Name			: setCellData
		 * Purpose				: 
		 * Author				:
		 * Reviewer 			:
		 * Date of Creation		:
		 * Date of modification :
		 * ******************************
		 */
	/*		public boolean setCellData(String strFilePath, String strSheetName, String strColName, int rowNum, String strData,String strbackgroundcolor)
		{
			FileInputStream fin = null;
			FileOutputStream fout = null;
			Workbook wb = null;
			Sheet sh = null;
			Row row = null;
			Cell cell = null;
			int colNum = 0;
			int colNumCount = 0;

			try {
				fin = new FileInputStream(strFilePath);
				wb = new XSSFWorkbook(fin);
				//sh = wb.getSheet(strSheetName);
				
				for (int i = 0; i < wb.getNumberOfSheets(); i++)
				{
				    if( wb.getSheetName(i).equalsIgnoreCase(strSheetName)) {
				    	sh = wb.getSheet(strSheetName);
				    	break;
				    }				    
				}
				
				if(sh==null) return false;
				//Find out the column number based on the given column name
				row = sh.getRow(1);
				for(int c=0;c<row.getPhysicalNumberOfCells();c++)
				{
					cell = row.getCell(c);
					if(cell.getStringCellValue().trim().equalsIgnoreCase(strColName))
					{
						colNum = c;
						break;
					}
				}

				row = sh.getRow(rowNum);
				if(row==null) row = sh.createRow(rowNum);
				cell = row.getCell(colNum);
				if(cell==null) cell = row.createCell(colNum);
				cell.setCellValue(strData);
				
				//Setting the background color.
				if (!(strbackgroundcolor.equalsIgnoreCase("NA")))
				{
					 CellStyle style = wb.createCellStyle();
					 if(strbackgroundcolor.equalsIgnoreCase("green"))
					 {
						 style.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());
					 }else if(strbackgroundcolor.equalsIgnoreCase("red"))
					 {
						 style.setFillForegroundColor(IndexedColors.RED.getIndex());
					 }					 
			         style.setFillPattern(FillPatternType.SOLID_FOREGROUND);		            
			         cell.setCellStyle(style);					
				}
				
				fout = new FileOutputStream(strFilePath);
				wb.write(fout);
				return true;
			}catch(Exception e)
			{
				oUtility.writeLog("Fail","Exception while executing 'setCellData' method. "+e.getMessage());
				return false;
			}
			finally {
				try {
					fout.flush();
					fout.close();
					fout=null;
					fin.close();
					fin=null;
					cell=null;
					row=null;
					sh=null;
					wb.close();
					wb=null;
				}catch(Exception e)
				{
					oUtility.writeLog("Fail","Exception while executing 'setCellData' method. "+e.getMessage());
					return false;
				}
			}
		}
*/
		
		public boolean setCellData(String strFilePath, String strSheetName, String strColName, int rowNum, String strData,String strbackgroundcolor,String countvalues)
		{
			FileInputStream fin = null;
			FileOutputStream fout = null;
			Workbook wb = null;
			Sheet sh = null;
			Row row = null;
			Cell cell = null;
			int colNum = 0;
			int colNumCount = 0;
			String Count = "Count";
			try {
				fin = new FileInputStream(strFilePath);
				wb = new XSSFWorkbook(fin);
				//sh = wb.getSheet(strSheetName);
				
				for (int i = 0; i < wb.getNumberOfSheets(); i++)
				{
				    if( wb.getSheetName(i).equalsIgnoreCase(strSheetName)) {
				    	sh = wb.getSheet(strSheetName);
				    	break;
				    }				    
				}
				
				if(sh==null) return false;
				//Find out the column number based on the given column name
				row = sh.getRow(1);
				for(int c=0;c<row.getPhysicalNumberOfCells();c++)
				{
					cell = row.getCell(c);
					if(cell.getStringCellValue().trim().equalsIgnoreCase(strColName))
					{
						colNum = c;
					
						break;
					}
				}
				
				for(int c=0;c<row.getPhysicalNumberOfCells();c++)
				{
					cell = row.getCell(c);
				        if(cell.getStringCellValue().trim().equalsIgnoreCase(Count))
				        {	
						colNumCount = c;
						break;
					}
				}

				
				row = sh.getRow(rowNum);
				if(row==null) row = sh.createRow(rowNum);
				cell = row.getCell(colNum);
				if(cell==null) cell = row.createCell(colNum);
			
				cell.setCellValue(strData);
				
				//Setting the background color.
				if (!(strbackgroundcolor.equalsIgnoreCase("NA")))
				{
					 CellStyle style = wb.createCellStyle();
					 if(strbackgroundcolor.equalsIgnoreCase("green"))
					 {
						 style.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());
					 }else if(strbackgroundcolor.equalsIgnoreCase("red"))
					 {
						 style.setFillForegroundColor(IndexedColors.RED.getIndex());
					 }					 
			         style.setFillPattern(FillPatternType.SOLID_FOREGROUND);		            
			         cell.setCellStyle(style);					
				}
				
				
				cell = row.getCell(colNumCount);
				if(cell==null) cell = row.createCell(colNumCount);
			if(!(countvalues.equalsIgnoreCase("")))
				cell.setCellValue(countvalues);
				//System.out.println("colNumCount::" +colNumCount);
				//System.out.println("countvalues::" +countvalues);
				
				fout = new FileOutputStream(strFilePath);
				wb.write(fout);
				return true;
			}catch(Exception e)
			{
				oUtility.writeLog("Fail","Exception while executing 'setCellData' method. "+e.getMessage());
				return false;
			}
			finally {
				try {
					fout.flush();
					fout.close();
					fout=null;
					fin.close();
					fin=null;
					cell=null;
					row=null;
					sh=null;
					wb.close();
					wb=null;
				}catch(Exception e)
				{
					oUtility.writeLog("Fail","Exception while executing 'setCellData' method. "+e.getMessage());
					return false;
				}
			}
		}
		/********************************
		 * Method Name			: getInputTestData
		 * Purpose				: 
		 * Author				:
		 * Reviewer 			:
		 * Date of Creation		:
		 * Date of modification :
		 * *
		 * @return *****************************
		 */
		public HashMap<String, String> getInputTestData(String strFilePath,String strSheetName)
		{
			FileInputStream fin = null;
			Workbook wb = null;
			Sheet sh = null;
			Cell cell2 = null;
			int rows = 0;
			int cols = 0;
			String strKey = null;
			String strVal = null;
			String strValue=null;
			HashMap<String, String> oMap = new HashMap<String, String>();
			
			
			try {
				fin = new FileInputStream(strFilePath);
				wb = new XSSFWorkbook(fin);
				//sh = wb.getSheet(strSheetName);
				for (int i = 0; i < wb.getNumberOfSheets(); i++)
				{
				    if( wb.getSheetName(i).equalsIgnoreCase(strSheetName)) {
				    	sh = wb.getSheet(strSheetName);
				    	break;
				    }				    
				}	
				
				if(sh==null) {
					oUtility.writeLog("Fail", "The Sheet '"+strSheetName+"' was not found");
					//return false;
				}
				
				rows = sh.getPhysicalNumberOfRows();
				for(int r=1;r<rows;r++)
				{
					   // Read TestscaseID and save it as strKey			    				
					strKey = sh.getRow(r).getCell(0).getStringCellValue();	
								
					//get column count of row header
					cols = sh.getRow(0).getPhysicalNumberOfCells();	
					
					for(int i=1;i<=cols-1;i++)
					{						
						cell2 =  sh.getRow(r).getCell(i);
						if(cell2==null || cell2.getCellTypeEnum()==cell2.getCellTypeEnum().BLANK)
						{
							strVal = "";
						}
						else if(cell2.getCellTypeEnum()==cell2.getCellTypeEnum().BOOLEAN)
						{
							strVal = String.valueOf(cell2.getBooleanCellValue());
						}
						else if(cell2.getCellTypeEnum()==cell2.getCellTypeEnum().STRING)
						{
							strVal = cell2.getStringCellValue();
						}
						else if(cell2.getCellTypeEnum()==cell2.getCellTypeEnum().NUMERIC)
						{
							if(HSSFDateUtil.isCellDateFormatted(cell2))
							{
								double dt = cell2.getNumericCellValue();
								Calendar cal = Calendar.getInstance();
								cal.setTime(HSSFDateUtil.getJavaDate(dt));
								strVal = String.valueOf(cal.get(Calendar.DAY_OF_MONTH))+"/"+String.valueOf((cal.get(Calendar.MONTH)+1))+"/"+String.valueOf(cal.get(Calendar.YEAR));
							}else {
								
								strVal = String.valueOf(cell2.getNumericCellValue());
								
							}
						}
						
					 strValue+= strVal+"#";
					}				
					
					oMap.put(strKey, strValue);	
					//defaulting the variables
					strKey=null;
					strValue=null;
					strVal=null;
					
			}
					return oMap;
			
			}catch(Exception e)
			{
				oUtility.writeLog("Exception", "Exception while executing 'getDataFromExcel' method. "+e.getMessage());
				return oMap;
			}
			finally
			{
				try {
					fin.close();
					fin = null;
					cell2 = null;
					sh = null;
					wb.close();
					wb = null;
				}catch(Exception e)
				{
					oUtility.writeLog("Exception", "Exception while executing 'getDataFromExcel' method. "+e.getMessage());
					return oMap;
				}
			}
		}
		
		
				
		/********************************
		 * Method Name			: create_Excel
		 * Purpose				: This method creates the excel in the specified path
		 * Parameters			: strFilePath: 
		 * 	 strFilePath		: File path to create the excel
		 * 	 strSheetName		: Excel Sheet Name has to be create
		 * 	 strColName			: 
		 * Author				: Mahesh TK
		 * Reviewer 			:
		 * Date of Creation		: 
		 * Date of modification :
		 * ******************************
		 */
		public boolean create_Excel(String strFilePath, String strSheetName, String strColName)
		{
			FileOutputStream fout = null;
			Workbook wb = null;
			Sheet sh = null;
			Row row = null;
			Cell cell = null;
			String objArr[]=null ;
			boolean bolstats=false;
			try {	
				wb = new XSSFWorkbook();
				sh=wb.createSheet(strSheetName);
				if(sh==null) bolstats=false;				
				
		        // Create a Font for styling header cells
		        Font headerFont = wb.createFont();
		        headerFont.setBold(true);
		        headerFont.setFontHeightInPoints((short) 14);
		        headerFont.setColor(IndexedColors.BLUE.getIndex());

		        // Create a CellStyle with the font
		        CellStyle headerCellStyle = wb.createCellStyle();
		        headerCellStyle.setFont(headerFont);
				
				
				//Create the 1st row for column Header
				row=sh.createRow(0);
				objArr=strColName.split("#");
				for(int c=0;c<=objArr.length-1;c++)
				{
					if (objArr[c]!=null)
					{
						cell = row.getCell(c);
						if(cell==null) cell = row.createCell(c);
						cell.setCellValue(objArr[c]);
						cell.setCellStyle(headerCellStyle);
						fout = new FileOutputStream(strFilePath);
						wb.write(fout);
						bolstats=true;						
					}
				}						
			}catch(Exception e)
			{
				oUtility.writeLog("Fail","Exception while executing 'create_Excel' method. "+e.getMessage());
				bolstats=false;
			}
			finally {
				try {
					fout.flush();
					fout.close();
					fout=null;					
					cell=null;
					row=null;
					sh=null;
					wb.close();
					wb=null;
					bolstats=false;
					oUtility.writeLog("#","Result file created sucessfully in path:   "+strFilePath);
					System.out.println("Created Excel result sheet");
				}catch(Exception e)
				{
					oUtility.writeLog("Fail","Exception while executing 'create_Excel' method. "+e.getMessage());
					bolstats=false;
				}
			}
			return bolstats;
		}
		
		
		public boolean DeleteSheet(String strFilePath, String strSheetName)
		{
			FileInputStream fin = null;
			FileOutputStream fout = null;
			Workbook wb = null;
			Sheet sh = null;
			
			try {
				fin = new FileInputStream(strFilePath);
				wb = new XSSFWorkbook(fin);
				//sh = wb.getSheet(strSheetName);
				
				for (int i = 0; i < wb.getNumberOfSheets(); i++)
				{
				    if( wb.getSheetName(i).equalsIgnoreCase(strSheetName)) {
				    	wb.removeSheetAt(i);
				    	break;
				    }				    
				}		
							
				fout = new FileOutputStream(strFilePath);
				wb.write(fout);
				return true;
			}catch(Exception e)
			{
				oUtility.writeLog("Fail","Exception while executing 'DeleteSheet' method. "+e.getMessage());
				return false;
			}
			finally {
				try {
					fout.flush();
					fout.close();
					fout=null;
					fin.close();
					fin=null;					
					sh=null;
					wb.close();
					wb=null;
				}catch(Exception e)
				{
					oUtility.writeLog("Fail","Exception while executing 'DeleteSheet' method. "+e.getMessage());
					return false;
				}
			}
		}




		
		
		
		
		
		
		
} //Close of DataTable Class
