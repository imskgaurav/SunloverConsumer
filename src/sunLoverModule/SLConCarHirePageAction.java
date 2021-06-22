package sunLoverModule;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;

import automationFramework.Driver;
import pageObjects.SLVCCarHirePgObj;

public class SLConCarHirePageAction {

	SLVCCarHirePgObj carPgObj = PageFactory.initElements(Driver.driver,
			SLVCCarHirePgObj.class);
	WebDriverWait wait = new WebDriverWait(Driver.driver, 5);
	JavascriptExecutor jse = (JavascriptExecutor) Driver.driver;

	/*
	 * @FindBy(xpath="//div[@class='formrow submit']//input[@id='btn_10']")
	 * WebElement go_button;
	 */
	public void selectLocation(String location) {
		try {
			WebElement location_id = carPgObj.getLocation();
			Select locationId = new Select(location_id);
			locationId.selectByVisibleText(location);
			String selected_Loc = locationId.getFirstSelectedOption().getText();
			System.out.println("Location selected is:  " + selected_Loc);
		} catch (Exception ex) {
			ex.getCause();
			AssertJUnit.fail(ex.getMessage());
		}
	}

	public void select_CheckInMonth(String month) {
		try {
			WebElement checkInMonth = carPgObj.getCheck_In_Month();
			Select select_month = new Select(checkInMonth);
			select_month.selectByVisibleText(month);
			String selected_month = select_month.getFirstSelectedOption()
					.getText();
			System.out.println("Check In Month selected: " + selected_month);
		} catch (Exception ex) {
			ex.getCause();
			AssertJUnit.fail(ex.getMessage());
		}
	}

	public void select_CheckInDay(String day) {
		try {
			WebElement checkInDay = carPgObj.getcheckInDay();
			Select select_day = new Select(checkInDay);
			select_day.selectByValue(day);
			String selected_day = select_day.getFirstSelectedOption().getText();
			System.out.println("Check In Day selected: " + selected_day);
		} catch (Exception ex) {
			ex.getCause();
			ex.printStackTrace();
			AssertJUnit.fail(ex.getMessage());
		}
	}

	public void select_CheckOutMonth(String month) {
		try {
			WebElement checkOutMonth = carPgObj.getCheck_Out_Month();
			Select select_month = new Select(checkOutMonth);
			select_month.selectByVisibleText(month);
			String selected_month = select_month.getFirstSelectedOption()
					.getText();
			System.out.println("Check out Month selected: " + selected_month);
		} catch (Exception ex) {
			ex.getCause();
			ex.printStackTrace();
			AssertJUnit.fail(ex.getMessage());
		}
	}

	public void select_CheckOutDay(String day) {
		try {
			WebElement checkOutday = carPgObj.getCheckout_day();
			Select select_day = new Select(checkOutday);
			select_day.selectByValue(day);
			String selected_day = select_day.getFirstSelectedOption().getText();
			System.out.println("Check Out day selected: " + selected_day);
		} catch (Exception ex) {
			ex.getCause();
			ex.printStackTrace();
		}
	}

	public void select_PickUp_Time(String pickUp) {
		try {
			WebElement pickUptime = carPgObj.getPickUpTime();
			Select select_pickUpTime = new Select(pickUptime);
			select_pickUpTime.selectByValue(pickUp);
			String pickup_Time = select_pickUpTime.getFirstSelectedOption()
					.getText();
			System.out.println("PickUpTime :" + pickup_Time);

		} catch (Exception ex) {

			ex.getCause();
			ex.printStackTrace();
			AssertJUnit.fail(ex.getMessage());

		}
	}

	public void select_DropOff_Time(String dropOff) {
		try {
			WebElement DropOffTime = carPgObj.getDropOffTime();
			Select select_drop_Off_Time = new Select(DropOffTime);
			select_drop_Off_Time.selectByValue(dropOff);
			String dropOff_Time = select_drop_Off_Time.getFirstSelectedOption()
					.getText();
			System.out.println("DropOffTime :" + dropOff_Time);

		} catch (Exception ex) {

			ex.getCause();
			ex.printStackTrace();
			AssertJUnit.fail(ex.getMessage());
		}
	}

	public void Click_Go() throws Exception {
		try {
			if (carPgObj.get_GoButton().isEnabled()) {
				System.out.println("go button appeared..");
				carPgObj.get_GoButton().click();
				jse.executeScript("arguments[0].click();",
						carPgObj.get_GoButton());
			} else {
				System.out.println("Go button failed to click");
			}
		} catch (Exception e) {
			e.printStackTrace();
			AssertJUnit.fail(e.getMessage());
		}
	}
}
