/**
 * 
 */
package sunLoverTestCases;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import com.thoughtworks.selenium.webdriven.commands.WaitForPageToLoad;

import automationFramework.Driver;
import automationFramework.EnvConfiguration;
import automationFramework.XLReader;
import automationFramework.XLWriter;
import utilities.ScreenShotOnFailure;

/**
 * @author tetambes
 *
 */
@Listeners(ScreenShotOnFailure.class)
public class LaunchSunloverCons {

	static String URL = EnvConfiguration.BRD_URL;
	private static StringBuffer verificationErrors = new StringBuffer();

	@BeforeSuite(alwaysRun = true)
	public static void launchUrl() throws Exception {
		try {
			Driver.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			Driver.driver.manage().window().maximize();
			Driver.driver.get(URL);
			new WaitForPageToLoad();
			String webTitle = Driver.driver.getTitle();
			System.out.println("Website Title is: " + webTitle);
			// URl Verification handling here
			AssertJUnit.assertTrue("Website Launch fail..", Driver.driver.getTitle().contains("Sunlover Holidays") == true);
			String Upgrade_Str = "This website is currently unavailable due to a scheduled upgrade.";
			WebElement contentHeader = Driver.driver.findElement(By.id("content"));
			if (contentHeader.getText().contains(Upgrade_Str)) {
				System.out.println(verificationErrors);
				System.exit(0);
			} else {
				Assert.assertTrue(Driver.driver.findElement(By.xpath(".//*[@id='servicetypes']")).isDisplayed() == true, "Website not launch properly.");
			}
			XLReader excelSheet = PageFactory.initElements(Driver.driver, XLReader.class);
			String excelPath = EnvConfiguration.Path_TestData + EnvConfiguration.File_TestData;
			String website = "SunLoverCN";
			excelSheet.setExcelFile(excelPath, website);
			XLWriter writer = PageFactory.initElements(Driver.driver, XLWriter.class);
			writer.setTestOutputFile();
		} catch (Exception e) {
			e.getMessage();
			e.getCause();
		}
	}

	@AfterSuite(alwaysRun = true)
	public void tearDown() throws Exception 
	{
		Driver.driver.manage().deleteAllCookies();
		Driver.driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			Assert.fail(verificationErrorString);
		}
	}


}
