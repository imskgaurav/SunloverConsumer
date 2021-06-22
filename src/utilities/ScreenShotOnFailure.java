/**
 * 
 */
package utilities;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

import automationFramework.Driver;

/**
 * @author tetambes
 *
 */
public class ScreenShotOnFailure extends TestListenerAdapter {


	@Override
	public void onTestFailure(ITestResult testResult) {

		File srcFile = ((TakesScreenshot) Driver.driver).getScreenshotAs(OutputType.FILE);
		DateFormat dateFormat = new SimpleDateFormat("dd_MM_yy__hh_mm_ssaa");
		String Current_Dir = System.getProperty("user.dir");
		System.out.println("Current working dir: " + Current_Dir);
		String destDir = "Test Report/Screenshots";
		new File(destDir).mkdirs();
		String _folder = testResult.getInstanceName();
		// String TestClass = testResult.getTestName();
		String methodName = testResult.getMethod().getMethodName();
		System.out.println(methodName + " Test Case got Failed.....Saving a screenshot");
		String capture_Date = dateFormat.format(new Date());
		String destFile = methodName + "_" + capture_Date + ".png";
		try {
			FileUtils.copyFile(srcFile,
					new File(destDir + "/" + _folder + "/" + destFile));

		} catch (IOException e) {
			e.printStackTrace();
		}

		// Below code to print screen shot in Test Report and Create Log for it.
		Reporter.setEscapeHtml(false);
		Reporter.log("Saved <a href= "  + Current_Dir + "/" + destDir + "/" + _folder + "/" + destFile+ ">Captured Screenshot</a> for Failed Test Case");
	}
}

