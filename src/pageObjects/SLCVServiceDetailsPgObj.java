/**
 * 
 */
package pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author mbole
 *
 */
public class SLCVServiceDetailsPgObj {

	@FindBy(css = "#SDetails")
	private WebElement header;

	@FindBy(css = "#serviceDetailsForm>h2>a")
	private WebElement supplier_header;

	@FindBy(css = "#btn_11")
	private WebElement update_button;

	@FindBy(css = "#btn_21")
	private WebElement book_button;

	@FindBy(css = "#remarks")
	private WebElement splRequest_textbox;

	@FindBy(xpath = ".//*[@id='serviceDetailsForm']/table/tbody/tr[7]/td[2]/p/input")
	private WebElement pickupLocation_txtbox;

	@FindBy(css = "#infoAgree")
	private WebElement checkbox;

	public WebElement getHeader() {
		return header;
	}

	public WebElement getSupplier_header() {
		return supplier_header;
	}

	public WebElement getUpdate_button() {
		return update_button;
	}

	public WebElement getBook_button() {
		return book_button;
	}

	public WebElement getSplRequest_textbox() {
		return splRequest_textbox;
	}

	public WebElement getPickupLocation_txtbox() {
		return pickupLocation_txtbox;
	}

	public WebElement getCheckbox() {
		return checkbox;
	}
}
