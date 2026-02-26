package driverFactory;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class AppTest {
String inputpath ="./FileInput/TestDataForHybrid.xlsx";
String outputpath ="./FileOutput/HybridResults.xlsx";
String TCSheet ="MasterTestCases";
ExtentReports reports;
ExtentTest logger;
WebDriver driver;
@Test
public void startTest() throws Throwable
{
	String Module_Status ="";
	String Module_New="";
	//create object for excelfile util class
	ExcelFileUtil xl = new ExcelFileUtil(inputpath);
	//count no of rows in TCsheet
	int rc = xl.rowCount(TCSheet);
	//iterate all rows
	for(int i=1;i<=rc;i++)
	{
		if(xl.getCellData(TCSheet, i, 2).equalsIgnoreCase("Y"))
		{
		//read corresponding sheet from TCSheet
			String TCModule =xl.getCellData(TCSheet, i, 1);
			reports = new ExtentReports("./target/Reports/"+TCModule+FunctionLibrary.generateDate()+".html");
			logger = reports.startTest(TCModule);

			//iterate all rows in TCModule
			for(int j=1;j<=xl.rowCount(TCModule);j++)
			{
				//read each cell from TCModule
				String Description =xl.getCellData(TCModule, j, 0);
				String ObjectType = xl.getCellData(TCModule, j, 1);
				String Ltype =xl.getCellData(TCModule, j, 2);
				String LValue = xl.getCellData(TCModule, j, 3);
				String TestData = xl.getCellData(TCModule, j, 4);
				String EleName = xl.getCellData(TCModule, j, 6);
				try {
					if(ObjectType.equalsIgnoreCase("startBrowser"))
					{
					driver =FunctionLibrary.startBrowser();	
					logger.log(LogStatus.INFO, Description);
					}
					if(ObjectType.equalsIgnoreCase("openUrl"))
					{
						FunctionLibrary.openUrl();
						logger.log(LogStatus.INFO, Description);
					}
					if(ObjectType.equalsIgnoreCase("waitForElement"))
					{
						FunctionLibrary.waitForElement(Ltype, LValue, TestData, EleName);
						logger.log(LogStatus.INFO, Description);
					}
					if(ObjectType.equalsIgnoreCase("typeAction"))
					{
						FunctionLibrary.typeAction(Ltype, LValue, TestData);
						logger.log(LogStatus.INFO, Description);
					}
					if(ObjectType.equalsIgnoreCase("clickAction"))
					{
						FunctionLibrary.clickAction(Ltype, LValue);
						logger.log(LogStatus.INFO, Description);
					}
					if(ObjectType.equalsIgnoreCase("validateTitle"))
					{
						FunctionLibrary.validateTitle(TestData);
						logger.log(LogStatus.INFO, Description);
					}
					if(ObjectType.equalsIgnoreCase("closeBrowser"))
					{
						FunctionLibrary.closeBrowser();
						logger.log(LogStatus.INFO, Description);
					}
					if(ObjectType.equalsIgnoreCase("getText"))
					{
						FunctionLibrary.getText(Ltype, LValue, TestData, EleName);
						logger.log(LogStatus.INFO, Description);
					}
					if(ObjectType.equalsIgnoreCase("getValue"))
					{
						FunctionLibrary.getValue(Ltype, LValue, TestData);
						logger.log(LogStatus.INFO, Description);
					}
					if(ObjectType.equalsIgnoreCase("waitTillPageLoad"))
					{
						FunctionLibrary.getValue(Ltype, LValue, TestData);
						logger.log(LogStatus.INFO, Description);
					}
					if(ObjectType.equalsIgnoreCase("minimizeScreen"))
					{
						FunctionLibrary.minimizeScreen();
						logger.log(LogStatus.INFO, Description);
					}
					//write as pass into status cell in TCModule
					xl.setCellData(TCModule, j, 5, "Pass", outputpath);
					logger.log(LogStatus.PASS, Description);

					Module_Status="True";
				} catch (Exception e) {
					System.out.println(e.getMessage());
					//write as Fail into status cell in TCModule
					xl.setCellData(TCModule, j, 5, "Fail", outputpath);
					logger.log(LogStatus.FAIL, Description);
					Module_New="False";
				}
				if(Module_Status.equalsIgnoreCase("True"))
				{
					//write as pass into TCSheet
					xl.setCellData(TCSheet, i, 3, "Pass", outputpath);
					logger.log(LogStatus.PASS, Description);
				}
				if(Module_New.equalsIgnoreCase("False"))
				{
					//write as Fail into TCSheet
					xl.setCellData(TCSheet, i, 3, "Fail", outputpath);
					logger.log(LogStatus.FAIL, Description);
				}
				reports.endTest(logger);
				reports.flush();
			}
		}
		else
		{
			//write as blocked into TCsheet which testcase flag to N
			xl.setCellData(TCSheet, i, 3, "Blocked", outputpath);
		}
	}
}
}











