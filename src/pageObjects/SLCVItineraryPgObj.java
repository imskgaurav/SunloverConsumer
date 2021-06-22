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
public class SLCVItineraryPgObj {

	@FindBy(css = ".ires>div>h1")
	private WebElement header;

	@FindBy(css = ".ires>h2>a>span")
	private WebElement supplier_header;

	@FindBy(xpath = ".//*[@id='detailsForm']/div/div[2]/h2/a/span")
	private WebElement supplier_header_2;

	@FindBy(css = "#btn_10")
	private WebElement add_Another_button;

	@FindBy(css = "#btn_21")
	private WebElement change_button;

	@FindBy(css = "#btn_32")
	private WebElement delete_button;

	@FindBy(css = ".noBooking>p")
	private WebElement actMessage;

	@FindBy(xpath = ".//*[@id='detailsForm']/div/div[1]/table/tbody/tr[9]/td[2]/p/span[1]")
	private WebElement splRequest_text;

	@FindBy(css = ".consumerBookEnabled")
	private WebElement bookBtn;

	public WebElement getHeader() {
		return header;
	}

	public WebElement getSupplier_header() {
		return supplier_header;
	}

	public WebElement getSupplier_header_2() {
		return supplier_header_2;
	}

	public WebElement getAdd_Another_button() {
		return add_Another_button;
	}

	public WebElement getChange_button() {
		return change_button;
	}

	public WebElement getDelete_button() {
		return delete_button;
	}

	public WebElement getActMessage() {
		return actMessage;
	}

	public WebElement getSplRequest_text() {
		return splRequest_text;
	}

	public WebElement getBookBtn() {
		return bookBtn;
	}

}
