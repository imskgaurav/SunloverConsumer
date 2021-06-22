package sunLoverModule;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import automationFramework.Driver;
import pageObjects.SLVCTransfersPgObj;
import utilities.SelectDropdown;

/**
 * @author tetambes
 *
 */
public class SLConTransfersPageAction {

	SLVCTransfersPgObj trsfsPgObj = PageFactory.initElements(Driver.driver,
			SLVCTransfersPgObj.class);
	WebDriverWait wait = new WebDriverWait(Driver.driver, 5);
	JavascriptExecutor jse = (JavascriptExecutor) Driver.driver;
	SelectDropdown dropdown = new SelectDropdown();

	public void selectLocation(String departsFrom) {
		try {
			WebElement location_id = trsfsPgObj.getDepartsFrom();
			Select locationId = new Select(location_id);
			locationId.selectByVisibleText(departsFrom);
			String selected_Loc = locationId.getFirstSelectedOption().getText();
			System.out.println(
					"Departs From location selected is:  " + selected_Loc);
		} catch (Exception ex) {
			AssertJUnit.fail(ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void select_TransMonth(String transMonth) {
		try {
			WebElement month = trsfsPgObj.getTransfersMonth();
			Select select_month = new Select(month);
			select_month.selectByVisibleText(transMonth);
			String selected_month = select_month.getFirstSelectedOption()
					.getText();
			System.out.println("Transfers Month selected: " + selected_month);
		} catch (Exception ex) {
			ex.getCause();
			AssertJUnit.fail(ex.getMessage());
		}
	}

	public void select_TransDay(String transDay) {
		try {
			WebElement day = trsfsPgObj.getTransfersDay();
			Select select_day = new Select(day);
			select_day.selectByValue(transDay);
			String selected_day = select_day.getFirstSelectedOption().getText();
			System.out.println("Transfers Day selected: " + selected_day);
		} catch (Exception ex) {
			ex.printStackTrace();
			AssertJUnit.fail(ex.getMessage());
		}
	}

	public void click_Go() throws Exception {
		try {
			if (trsfsPgObj.getGoBtn().isEnabled()) {
				System.out.println("Go button is enabled...");
				trsfsPgObj.getGoBtn().click();
				jse.executeScript("arguments[0].click();",
						trsfsPgObj.getGoBtn());
			} else {
				System.out.println("Go button failed to click");
			}
		} catch (Exception e) {
			AssertJUnit.fail(e.getMessage());
		}
	}

	// This method update cost of a service if no. of adults changed
	public boolean update_pax_on_service_details(String adult)
			throws Exception {
		try {
			if (trsfsPgObj.getUpdateRates_btn().isDisplayed()) {
				System.out.println("Update Rate button is enabled..");
				dropdown.selectTextFromElement(trsfsPgObj.getAdult_drpdwn(),
						adult);
				System.out.println("Adult selected: " + adult);
				trsfsPgObj.getUpdateRates_btn().click();
				jse.executeScript("arguments[0].click();",
						trsfsPgObj.getUpdateRates_btn());
				wait.until(
						ExpectedConditions.visibilityOf(trsfsPgObj.getPrice()));

			} else {
				System.out.println("Update Rates button not loaded....");
			}
		} catch (Exception e) {
			AssertJUnit.fail(e.getMessage());
		}
		return false;
	}
}
