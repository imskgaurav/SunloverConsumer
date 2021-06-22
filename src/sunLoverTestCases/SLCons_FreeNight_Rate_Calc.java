package sunLoverTestCases;

import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.webdriven.commands.WaitForPageToLoad;

import automationFramework.Driver;
import automationFramework.EnvConfiguration;
import automationFramework.XLReader;
import pageObjects.SLCVPaymentPgObj;
import pageObjects.SLVCBookingSummaryPgobj;
import pageObjects.SLVCBroucherPgObj;
import pageObjects.SLVCHomePgObj;
import sunLoverModule.SLConHomePageAction;
import sunLoverModule.SLConsBookingFactory;
import sunLoverModule.SLConsPropertySelectionFactory;

public class SLCons_FreeNight_Rate_Calc {

	static String URL = EnvConfiguration.TRN_URL;
	WebDriverWait wait = new WebDriverWait(Driver.driver, 3000);
	SLConHomePageAction homePgFact = PageFactory.initElements(Driver.driver, SLConHomePageAction.class);
	SLConsBookingFactory bookingFact = PageFactory.initElements(Driver.driver, SLConsBookingFactory.class);
	SLConsPropertySelectionFactory selectionFactory = PageFactory.initElements(Driver.driver,
			SLConsPropertySelectionFactory.class);
	SLVCHomePgObj homePgObj = PageFactory.initElements(Driver.driver, SLVCHomePgObj.class);
	SLVCBroucherPgObj BrouchPgobj = PageFactory.initElements(Driver.driver, SLVCBroucherPgObj.class);
	SLCVPaymentPgObj payPgobj = PageFactory.initElements(Driver.driver, SLCVPaymentPgObj.class);
	SLVCBookingSummaryPgobj summaryPgobj = PageFactory.initElements(Driver.driver, SLVCBookingSummaryPgobj.class);
	SLConsPropertySelectionFactory BrochPgFunction = PageFactory.initElements(Driver.driver,
			SLConsPropertySelectionFactory.class);
	XLReader excel = new XLReader();

	public SLCons_FreeNight_Rate_Calc() {
		// TODO Auto-generated constructor stub
		PageFactory.initElements(Driver.driver, this);
	}

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		Driver.driver.get(URL);
		Driver.driver.manage().window().maximize();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header"))).isDisplayed();
		Driver.driver.findElement(By.linkText("Accommodation")).click();
		new WaitForPageToLoad();
		System.out.println("Accomodation tab clicked.");
	}

	@Test(priority = 1)
	public void TC_RG_SLC_Free_Nights_Rates_Verifivation() throws InterruptedException {
		try {
			System.out.println("Checking Free Night Rates Calculation on Broucher Page...");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("AccomSearchForm"))).isDisplayed();
			String hotel = excel.getCellData(20, 2);
			String nights = excel.getCellData(20, 7);
			System.out.println("Property selected from XL: " + hotel);
			homePgObj.getHotel_name().click();
			homePgObj.getHotel_name().clear();
			homePgObj.getHotel_name().sendKeys(hotel);
			selectionFactory.Choose_Hotel_Name(hotel);
			homePgFact.select_CheckInmonth(excel.getCellData(20, 3));
			homePgFact.select_CheckInDay(excel.getCellData(20, 4));
			homePgFact.enter_NightsToStay(nights);
			homePgFact.Enter_HotelName(hotel);
			homePgFact.select_CheckInmonth(excel.getCellData(20, 3));
			homePgFact.select_CheckInDay(excel.getCellData(20, 4));
			homePgFact.enter_NightsToStay(nights);
			String roomType = excel.getCellData(20, 11);
			homePgObj.getGo_button().click();

			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("searchFormWaiter_pleaseWait")));
			Assert.assertTrue(BrouchPgobj.getSupplier_header().isDisplayed(),
					"Brochure page not loaded for selected property");
			int roomnights = Integer.parseInt(nights);
			System.out.println("No of Nights" + roomnights);
			System.out.println("Selected Nights:" + roomnights);
			List<WebElement> roomnames = Driver.driver.findElements(By.cssSelector(".roomName"));
			Iterator<WebElement> roomItr = roomnames.iterator();
			String rooms[] = new String[roomnames.size()];
			Object ratetblval[] = new Object[roomnights];
			Integer totSum = 0;
			Integer value = 0;
			for (WebElement roomName : roomnames) {
				System.out.println("PrintRoomType :" + roomName.getText());
				if (roomName.getText().contentEquals(roomType)) {
					try {
						// List<WebElement> dailyRates =
						// Driver.driver.findElements(By.xpath("//td[contains(@class,
						// 'rate')]"));
					List<WebElement> dailyRates = Driver.driver.findElements(By.xpath("//*[@id='rooms']/div[3]/table"));
						//List<WebElement> dailyRates = Driver.driver.findElements(By.cssSelector(".rate"));
						System.out.println("List value of Table RATE");
						
						int i=0;
						for (WebElement rate : dailyRates) {
							System.out.println("value of i" +i);
							try {
								System.out.println("Hi");
								System.out.print(rate.getText());
								
								System.out.println("Hello");
								
								/*String rateValue = rate.getText();
								value = Integer.parseInt(rateValue);
								System.out.println(value);
								totSum = totSum + value;*/
							} catch (Exception e) {
								e.printStackTrace();
								

							}
							i++;
						}

	
}
						/*
						 * System.out.println("Rate Table Size:" +
						 * Table.size()); // String ratetblval[] = new
						 * String[Table.size()]; Iterator<WebElement> tblelm =
						 * Table.iterator(); while (tblelm.hasNext()) {
						 * System.out.println( "Printing rate Table values");
						 * ratetblval[j] = tblelm.next().getText();
						 * System.out.println(ratetblval[j]); if
						 * (ratetblval[j].toString().contains("Free")) {
						 * System.out.println("Free Night are found");
						 * freeNight++; } j++; System.out.println(
						 * "Total number of Free Nights are:" + freeNight);
						 * break outer;
						 */

					catch (Exception e) {
						e.printStackTrace();

					}

				}

			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}
