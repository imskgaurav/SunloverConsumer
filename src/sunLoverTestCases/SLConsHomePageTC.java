
package sunLoverTestCases;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.webdriven.commands.WaitForPageToLoad;

import automationFramework.Driver;
import automationFramework.XLReader;
import pageObjects.SLVCHomePgObj;
import sunLoverModule.SLConHomePageAction;
import sunLoverModule.SLConsPropertySelectionFactory;
import utilities.ScreenShotOnFailure;

/**
 * Test Objective: To Verify all search functions
 * Expected Result: User should successfully do any property search for valid data
 * Unit test cases
 */

@Listeners(ScreenShotOnFailure.class)
public class SLConsHomePageTC {

	static String URL = LaunchSunloverCons.URL;
	By Header = By.xpath("//div[@id='srch']/div/h1");
	SLConHomePageAction pgAct = PageFactory.initElements(Driver.driver, SLConHomePageAction.class);
	SLConsPropertySelectionFactory selectionFactory = PageFactory.initElements(Driver.driver, SLConsPropertySelectionFactory.class);
	SLVCHomePgObj HomePgObj = PageFactory.initElements(Driver.driver, SLVCHomePgObj.class);
	WebDriverWait wait = new WebDriverWait(Driver.driver, 10);
	XLReader excel = new XLReader();
	JavascriptExecutor jse = (JavascriptExecutor) Driver.driver;

	public SLConsHomePageTC() {

		// TODO Auto-generated constructor stub
		PageFactory.initElements(Driver.driver, this);
	}

	// Subscription pop-up handle here. Applicable to sun lover consumer test
	// suite only..
	@BeforeTest
	public void HandleSLCPopUp() throws Exception {
		try {
			new WebDriverWait(Driver.driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("popup")));

			if (Driver.driver.findElement(By.id("popup")).isDisplayed()) {

				System.out.println("Subcription popup displayed...");
				WebElement element = Driver.driver.findElement(By.name("submit"));
				JavascriptExecutor executor = (JavascriptExecutor) Driver.driver;
				executor.executeScript("arguments[0].click();", element); 
			}

			Assert.assertTrue(Driver.driver.findElement(By.id("popup")).isDisplayed() == false);
			System.out.println("popup disappeared................");

		} catch (Exception e) {
			e.printStackTrace();
			AssertJUnit.fail(e.getMessage());
		}

	}

	@Test(priority = 1)
	public void TC_RG_SLC_Search_By_Location() throws InterruptedException {
		try {
			new WebDriverWait(Driver.driver, 10).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(Header));
			System.out.println("************************Find Accomodation SearchBy Location TC started......************************");
			String Header_Text = Driver.driver.findElement(By.xpath("//div[@id='srch']/div/h1")).getText();
			System.out.println("Current window: " + Header_Text);
			String location = excel.getCellData(2, 1);
			System.out.println("Location value: " + location);
			pgAct.selectLocation(location);
			String checkInMonth = excel.getCellData(2, 3);
			String checkInDay = excel.getCellData(2, 4);
			String checkOutMonth = excel.getCellData(2, 5);
			String checkOutDay = excel.getCellData(2, 6);
			String nights = excel.getCellData(2, 7);
			pgAct.select_CheckInmonth(checkInMonth);
			pgAct.select_CheckOutmonth(checkOutMonth);
			Thread.sleep(3000);
			jse.executeScript("scroll(250, 0)");
			pgAct.select_CheckInDay(checkInDay);
			pgAct.select_CheckOutDay(checkOutDay);
			pgAct.enter_NightsToStay(nights);
			String adult = excel.getCellData(2, 8); 
			pgAct.select_Adults(adult);
			Thread.sleep(3000);
			if (HomePgObj.getSearch_button().isEnabled()) {
				System.out.println("search button appeared..");
				HomePgObj.getSearch_button().click();
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("searchFormWaiter_P")));
			} else {
				System.out.println("Search button is disable wait..");
				new WebDriverWait(Driver.driver, 5).until(ExpectedConditions.visibilityOf(HomePgObj.getSearch_button()));
				HomePgObj.getSearch_button().click();
			}
			// Verify search results list coming or not
			Assert.assertTrue(Driver.driver.findElement(By.xpath(".//*[@id='content']/span/h1")).isEnabled() == true, "Search Test Fail");
			Assert.assertTrue(Driver.driver.findElement(By.id("searchResults")).isDisplayed() == true);
		} catch (Exception e) {
			e.printStackTrace();
			AssertJUnit.fail(e.getMessage());
		}

	}

	@Test(priority = 2)
	public void TC_RG_SLC_Validate_CheckIn_CheckOut() throws Exception {
		try {
			Thread.sleep(3000);
			System.out.println("************************Running a testcase to validate checkIn-checkOut************************");
			String location = excel.getCellData(3, 1);
			String checkInMonth = excel.getCellData(3, 3);
			String checkInDay = excel.getCellData(3, 4);
			String checkOutMonth = excel.getCellData(3, 5);
			String checkOutDay = excel.getCellData(3, 6);
			pgAct.selectLocation(location);
			pgAct.select_CheckInmonth(checkInMonth);
			pgAct.select_CheckInDay(checkInDay);
			Thread.sleep(3000);
			jse.executeScript("scroll(250, 0)");
			pgAct.select_CheckOutmonth(checkOutMonth);
			pgAct.select_CheckOutDay(checkOutDay);
			HomePgObj.getGo_button().click();
			new WebDriverWait(Driver.driver, 20);
			// Verify validation message
			String expected_Text = "Check-Out cannot be before Check-In date.";
			Driver.driver.switchTo().alert();
			Thread.sleep(3000);
			String actText = Driver.driver.switchTo().alert().getText();
			System.out.println("Alert apppeared: " + actText);
			Assert.assertEquals(actText, expected_Text);
			Driver.driver.switchTo().alert().accept();
		} catch (Exception e) {
			e.getCause();
			e.printStackTrace();
			AssertJUnit.fail(e.getMessage());
		}
	}

//	  @Ignore
	  @Test(priority=3)
	  public void TC_RG_SLC_Search_By_Hotel_Name() throws Exception { 
		  try{ 
			  Thread.sleep(3000); 
			  System.out.println("************************Running a testcase to serach Hotel By Name..************************"); 
			  String hotelName =  excel.getCellData(4, 2); 
			  HomePgObj.getHotel_name().click();
			  HomePgObj.getHotel_name().clear();
			  HomePgObj.getHotel_name().sendKeys(hotelName);
			  selectionFactory.Choose_Hotel_Name(hotelName);
			  String checkInMonth= excel.getCellData(4, 3); 
			  String checkInDay = excel.getCellData(4, 4); 
			  String checkOutMonth = excel.getCellData(4, 5);
			  String checkOutDay = excel.getCellData(4, 6);
			  String nights = excel.getCellData(4, 7); 
			  pgAct.select_CheckInmonth(checkInMonth);
			  pgAct.select_CheckInDay(checkInDay); Thread.sleep(3000);
			  jse.executeScript("scroll(500, 0)");
			  pgAct.select_CheckOutmonth(checkOutMonth);
			  pgAct.select_CheckOutDay(checkOutDay); 
			  pgAct.enter_NightsToStay(nights);
			  String adult = excel.getCellData(4, 8); 
			  String children = (String)excel.getCellData(4, 9);
			  String child_age = excel.getCellData(4, 10); 
			  pgAct.select_Adults(adult); 
			  Thread.sleep(3000); 
			  pgAct.select_Child_specify_age(children, child_age); 
			  System.out.println("Pax Details: "+adult+"&"+children); 
			  Thread.sleep(3000);
			  HomePgObj.getGo_button().click();
			  wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("searchFormWaiter_P")));							 // Wait for wait loader to disappear....
			  //Verify search results list coming or not
			  Assert.assertTrue(Driver.driver.findElement(By.cssSelector("#supplieraddress>h1")).getText().contains(hotelName)); 
		  }
		  catch(Exception e) { 
			  e.getCause(); 
			  e.printStackTrace(); 
			  AssertJUnit.fail(e.getMessage());
		  } 
		  finally{
			  Driver.driver.navigate().back(); 
			  new WaitForPageToLoad(); 
		  	}
	  }
	  
	  
	  
	  @Test(priority=4) 
	  public void TC_RG_SLC_Verify_InValide_SearchResult()  throws Exception
	  { 
	   try{ 
		  Thread.sleep(3000);
		  System.out.println("************************Running a test case to verify InValid Search with No Accomodation Availability************************"); 
		  String location = excel.getCellData(5, 1); 
		  String checkInMonth= excel.getCellData(5, 3); 
		  String checkInDay= excel.getCellData(5, 4);
		  String checkOutMonth= excel.getCellData(5, 5); 
		  String checkOutDay = excel.getCellData(5, 6);
		  pgAct.selectLocation(location);
		  pgAct.select_CheckInmonth(checkInMonth);
		  pgAct.select_CheckInDay(checkInDay); 
		  Thread.sleep(3000);
		  jse.executeScript("scroll(250, 0)");
		  pgAct.select_CheckOutmonth(checkOutMonth);
		  pgAct.select_CheckOutDay(checkOutDay); 
		  String adult = excel.getCellData(5, 8); 
		  String child = (String)excel.getCellData(5, 9);
		  String child_age = excel.getCellData(5, 10); 
		  System.out.println("Pax Details: "+adult+"&"+child); 
		  pgAct.select_Adults(adult);
		  pgAct.select_Child_specify_age(child, child_age); 
		  Thread.sleep(3000);
		  HomePgObj.getGo_button().click();
		  wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(
		  "searchFormWaiter_P"))); // Wait for wait loader to disappear....
		  By.xpath(".//*[@id='toolbar']/div");
		  //Verify search results list coming or not
		  Assert.assertTrue(Driver.driver.findElement(By.className("no_results")).isDisplayed(),
		  "No Search Result Failed...");
	  }
	  catch(Exception e) 
		  { 
		  	e.getCause(); 
		  	e.printStackTrace(); 
		  	AssertJUnit.fail(e.getMessage());
	  	  } 
	  }
	 

	@Test(priority = 5)
	public void TC_RG_SLC_Check_Destination_tab() throws Exception {
		try {
			Thread.sleep(3000);
			System.out.println("************************Running a test case to verify Destination link************************");
			Driver.driver.findElement(By.linkText("Destinations")).click();
			String expHeader = "Australian Destinations - Where can I go?";
			String actHeader = Driver.driver.findElement(By.xpath(".//*[@id='navLevel2']/h1")).getText();
			Assert.assertEquals(actHeader, expHeader);
			System.out.println("Destination tab link opened...");
		} catch (Exception e) {
			e.getCause();
			e.printStackTrace();
			AssertJUnit.fail(e.getMessage());
		}
	}

	// This test case is not in priority
	@Test(priority = 6)
	public void TC_RG_SLC_Check_Visiting_Links() throws Exception {
		try {
			System.out.println("Running a test to verify Visiting Links...");
			jse.executeScript("scroll(250, 0)");

			System.out.println("Checking link - About Us");
			HomePgObj.getAbout_us_Link().click();
			pgAct.compare_Headers("About Sunlover Holidays", HomePgObj.getAbout_us_text());

			System.out.println("Checking link - Brochure");
			HomePgObj.getBrochures_Link().click();
			pgAct.compare_Headers("Sunlover Holidays Brochure Range", HomePgObj.getBrochures_text());

			System.out.println("Checking link - Contact Us");
			HomePgObj.getContact_us_Link().click();
			pgAct.compare_Headers("Customer Support", HomePgObj.getContact_us_text());

			System.out.println("Checking link - Privacy");
			HomePgObj.getPrivacy_Link().click();
			pgAct.compare_Headers("Privacy and Security", HomePgObj.getPrivacy_text());

			// System.out.println("Checking link - Imp Booking Conditions");
			// HomePgObj.getImportant_booking_conditions_Link().click();
			// ArrayList<String> tabs2 = new ArrayList<String>
			// (Driver.driver.getWindowHandles());
			// Driver.driver.switchTo().window(tabs2.get(1));
			// pgAct.compare_Headers("Important Booking Conditions",
			// HomePgObj.getImportant_booking_conditions_text());
			// Driver.driver.close();
			// Driver.driver.switchTo().defaultContent();

			System.out.println("Checking link - Site Map");
			HomePgObj.getSitemap_Link().click();
			pgAct.compare_Headers("Site Map", HomePgObj.getSitemap_text());

		} catch (Exception e) {
			e.getCause();
			e.printStackTrace();
			AssertJUnit.fail(e.getMessage());
		}
	}


	
	@AfterMethod(alwaysRun=true)
	public void clear_Cache(){
		Driver.driver.manage().deleteAllCookies();
	}

}
