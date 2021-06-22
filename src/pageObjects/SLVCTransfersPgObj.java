/**
 * 
 */
package pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Bole
 *
 */
public class SLVCTransfersPgObj {

	@FindBy(css = "#location")
	private WebElement departsFrom;

	@FindBy(name = "dateFrom_month")
	private WebElement transfersMonth;

	@FindBy(name = "dateFrom_day")
	private WebElement transfersDay;

	@FindBy(css = "#btn_10")
	private WebElement goBtn;

	@FindBy(name = "refresh")
	private WebElement UpdateRates_btn;

	@FindBy(css = "#adults")
	private WebElement adult_drpdwn;

	@FindBy(css = "#childpaxamount")
	private WebElement children_drpdwn;

	@FindBy(xpath = ".//*[@id='serviceDetailsForm']//tr[6]/td[2]/p[1]")
	private WebElement price;

	public WebElement getDepartsFrom() {
		return departsFrom;
	}

	public WebElement getTransfersMonth() {
		return transfersMonth;
	}

	public WebElement getTransfersDay() {
		return transfersDay;
	}

	public WebElement getGoBtn() {
		return goBtn;
	}

	public WebElement getUpdateRates_btn() {
		return UpdateRates_btn;
	}

	public WebElement getAdult_drpdwn() {
		return adult_drpdwn;
	}

	public WebElement getChildren_drpdwn() {
		return children_drpdwn;
	}

	public WebElement getPrice() {
		return price;
	}

}
