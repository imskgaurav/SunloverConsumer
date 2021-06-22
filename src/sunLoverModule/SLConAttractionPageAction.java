/**
 * 
 */
package sunLoverModule;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import automationFramework.Driver;

/**
 * @author tetambes
 *
 */
public class SLConAttractionPageAction {

	JavascriptExecutor jse = (JavascriptExecutor) Driver.driver;

	@FindBy(css = ".formrow.location>select")
	public WebElement Location_ele;

	@FindBy(css = "#btn_10")
	private WebElement goBtn;

	@FindBy(css = "#btn_43")
	private WebElement bookBtn;

	@FindBy(css = "#btn_21")
	private WebElement book_service_btn;

	public WebElement getBook_service_btn() {
		return book_service_btn;
	}

	@FindBy(css = "#infoAgree")
	private WebElement infoAgree_checkBox;

	public WebElement getBookBtn() {
		return bookBtn;
	}

	public WebElement getLocation_ele() {
		return Location_ele;
	}

	public WebElement getGoBtn() {
		return goBtn;
	}

	public WebElement getInfoAgree_checkBox() {
		return infoAgree_checkBox;
	}

	public void selectLocation(String location) {
		try {
			Select locationId = new Select(Location_ele);
			locationId.selectByVisibleText(location);
			String selected_Loc = locationId.getFirstSelectedOption().getText();
			System.out.println(
					"Departs From location selected is:  " + selected_Loc);
		} catch (Exception ex) {
			ex.getCause();
			ex.printStackTrace();
		}
	}

	public void click_Go() throws Exception {
		try {
			if (goBtn.isEnabled()) {
				System.out.println("Go button is enabled...");
				goBtn.click();
				jse.executeScript("arguments[0].click();", getGoBtn());
			} else {
				System.out.println("Go button failed to click");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
