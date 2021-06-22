/**
 * 
 */
package sunLoverTestCases;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.thoughtworks.selenium.webdriven.commands.WaitForPageToLoad;

import automationFramework.Driver;
import automationFramework.XLReader;
import pageObjects.SLVCCarHirePgObj;
import sunLoverModule.SLConCarHirePageAction;
import utilities.ScreenShotOnFailure;

/**
 * @author tetambes
 *
 */

@Listeners(ScreenShotOnFailure.class)
public class SLConsValidateCarHireTimeTC {

	/** Test Objective: To validate Car hire timing
	 *  Expected Result: User couldn't book a car hire if pick up> drop off time
	 *  Negative Test Case
	 *  @throws Assertion Error
	 */
	static String URL = LaunchSunloverCons.URL;
	XLReader excel = new XLReader();
	SLConCarHirePageAction CarPgAct = PageFactory.initElements(Driver.driver, SLConCarHirePageAction.class);
	WebDriverWait wait = new WebDriverWait(Driver.driver, 2000);
	SLVCCarHirePgObj pgObj = PageFactory.initElements(Driver.driver, SLVCCarHirePgObj.class);
	
	
	public SLConsValidateCarHireTimeTC() {
		// TODO Auto-generated constructor stub
		PageFactory.initElements(Driver.driver, this);
	}

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {

		Driver.driver.get(URL);
		Driver.driver.manage().window().maximize();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header"))).isDisplayed();
		Driver.driver.findElement(By.linkText("Car Hire")).click();
		new WaitForPageToLoad();
		System.out.println("Car Hire tab clicked.");
		Driver.driver.manage().deleteAllCookies();
	}
	
	@Test
	public void TC_RG_SLC_Validate_CarHire_PickUpDropOff_Time() throws Exception
	{
	 try{
		System.out.println("************************ Running a Test Case of Validate Car Hire PickUp/ Drop off Time ************************");
		String location = excel.getCellData(34, 1);
		String PickUpMonth = excel.getCellData(34, 3);
		String PickUPday = excel.getCellData(34, 4);
		String DropOffMonth = excel.getCellData(34, 5);
		String DropOffday = excel.getCellData(34, 6);
		Thread.sleep(3000);
		CarPgAct.selectLocation(location);
		CarPgAct.select_CheckInMonth(PickUpMonth);
		CarPgAct.select_CheckInDay(PickUPday);
		CarPgAct.select_CheckOutMonth(DropOffMonth);
		CarPgAct.select_CheckOutDay(DropOffday);
		wait.until(ExpectedConditions.elementToBeClickable(pgObj.get_GoButton()));
		CarPgAct.Click_Go();
		new WebDriverWait(Driver.driver, 20);
		// Verify validation message
		String expected_Text = "Dropoff date must be after the Pickup date.";
		Driver.driver.switchTo().alert();
		Thread.sleep(3000);
		String actText = Driver.driver.switchTo().alert().getText();
		System.out.println("Alert apppeared: " + actText);
		Assert.assertEquals(actText, expected_Text);
		Driver.driver.switchTo().alert().accept();
		// Time validation still pending
	} catch (Exception e) {
		e.getCause();
		e.printStackTrace();
		AssertJUnit.fail(e.getMessage());
		}
	}
	
	@AfterMethod(alwaysRun=true)
	public void clear_cookies(){
		
		Driver.driver.manage().deleteAllCookies();
		Driver.driver.navigate().refresh();
	}
}
