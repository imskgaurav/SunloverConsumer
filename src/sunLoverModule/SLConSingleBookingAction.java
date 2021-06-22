/**
 * 
 */
package sunLoverModule;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;

import automationFramework.Driver;
import automationFramework.XLReader;
import pageObjects.SLCVPaymentPgObj;
import pageObjects.SLVCBroucherPgObj;
import pageObjects.SLVCHomePgObj;
import utilities.SelectDropdown;

/**
 * @author tetambes
 *
 */
public class SLConSingleBookingAction {

	/**
	 * This Class contains all action perform for E2E Single booking of any
	 * Accommodation
	 * 
	 */

	WebDriverWait wait = new WebDriverWait(Driver.driver, 5);

	SLConHomePageAction homePgFact = PageFactory.initElements(Driver.driver,
			SLConHomePageAction.class);
	SLVCHomePgObj homePgObj = PageFactory.initElements(Driver.driver,
			SLVCHomePgObj.class);
	SLVCBroucherPgObj BrouchPgobj = PageFactory.initElements(Driver.driver,
			SLVCBroucherPgObj.class);
	SLCVPaymentPgObj payPgobj = PageFactory.initElements(Driver.driver,
			SLCVPaymentPgObj.class);
	XLReader excel = new XLReader();
	SelectDropdown drpdwn = PageFactory.initElements(Driver.driver,
			SelectDropdown.class);
	String propertyFilePath = "\\src\\testData\\Card Details.properties";

	public void updateDatesandGuestDetails() throws Exception {
		try {
			Actions actions = new Actions(Driver.driver);
			actions.keyUp(Keys.CONTROL).sendKeys(Keys.HOME).perform();
			String checkInMonth = excel.getCellData(6, 3);
			String checkInDay = excel.getCellData(6, 4);
			String checkOutMonth = excel.getCellData(6, 5);
			String checkOutDay = excel.getCellData(6, 6);
			homePgFact.select_CheckInmonth(checkInMonth);
			homePgFact.select_CheckOutmonth(checkOutMonth);
			actions.keyUp(Keys.CONTROL).sendKeys(Keys.HOME).perform();
			homePgFact.select_CheckInDay(checkInDay);
			homePgFact.select_CheckOutDay(checkOutDay);
			String nights = excel.getCellData(6, 7);
			homePgFact.enter_NightsToStay(nights);
			String adult = excel.getCellData(6, 8);
			String child = (String) excel.getCellData(6, 9);
			System.out.println("Pax Details: " + adult + "&" + child);
			homePgFact.select_Adults(adult);
			homePgObj.getSearch_button().click();
			// Wait for room loader to disappear...
			wait.until(ExpectedConditions
					.invisibilityOfElementLocated(By.id("roomPanelWait")));

		} catch (Exception ae) {
			ae.printStackTrace();
			AssertJUnit.fail(ae.getMessage());
		}
	}

}
