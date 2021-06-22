package sunLoverTestCases;

import java.util.*;
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

public class SLConFreeNight_RateVerification {

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

	public SLConFreeNight_RateVerification() {
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
			Thread.sleep(3000);
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
			// selectionFactory.Choose_Hotel_Name(hotel);
			homePgFact.select_CheckInmonth(excel.getCellData(20, 3));
			homePgFact.select_CheckInDay(excel.getCellData(20, 4));
			homePgFact.enter_NightsToStay(nights);
			String roomType = excel.getCellData(20, 11);
			homePgObj.getGo_button().click();
			int roomnights = Integer.parseInt(nights);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("searchFormWaiter_pleaseWait")));
			Assert.assertTrue(BrouchPgobj.getSupplier_header().isDisplayed(),
					"Brochure page not loaded for selected property");
			int freeNight = 0;
			int j = 0;
			List<WebElement> roomnames = Driver.driver.findElements(By.cssSelector(".roomName"));
			String rooms[] = new String[roomnames.size()];
			String ratetblval[] = new String[roomnights];
			outer: for (int i = 0; i < roomnames.size(); i++) {
				rooms[i] = roomnames.get(i).getText();

				System.out.println("Room name: " + rooms[i]);

				if (rooms[i].toString().contentEquals(roomType)) {
					List<WebElement> Table = Driver.driver.findElements(
							By.xpath(".//*[@id='rooms']/div['" + i + "']//tr[@class='maintintfifty resultrow']"));
					System.out.println("Rate Table Size:" + Table.size());
					// String ratetblval[] = new String[Table.size()];
					Iterator<WebElement> tblelm = Table.iterator();
					while (tblelm.hasNext()) {
						System.out.println("Printing rate Table values");
						ratetblval[j] = tblelm.next().getText();
						System.out.println(ratetblval[j]);
						if (ratetblval[j].toString().contains("Free")) {
							System.out.println("Free Night are found");
							freeNight++;
						}
						j++;
						System.out.println("Total number of Free Nights are:" + freeNight);
						break outer;
					}

				}

			}
			/* Spliting String For Rate and Night Seperately */
			String price = Driver.driver.findElement(By.cssSelector(".currency")).getText();
			String ratewithnights[] = price.split("\\R+");
			System.out.println("Rate in AU$ is :" + ratewithnights[0]);
			String nightsinString = ratewithnights[1];
			/* Counting Nights From Rate String */
			String totnights = nightsinString.substring(1, 3);
			System.out.println("Total Nights are:" + totnights);
			/*
			 * Spiliting String for seperating A$, so that rate numeric values
			 * can use for futher Comparision
			 */
			String priceinAU$ = ratewithnights[0];
			String numvalofRate = priceinAU$.substring(2);
			int totalrate = Integer.parseInt(numvalofRate);

			System.out.println("totalrate :" + totalrate);

			/* Calculation of Rates excluding Free nights */

			int nighttopay = roomnights - freeNight;
			System.out.println("Rate are Calculated for :" + nighttopay);

			// nights2pay);
		}

		catch (Exception e) {
			System.out.println();

		}

	}

}