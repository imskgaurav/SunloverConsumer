/**
 * 
 */
package pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author tetambes
 *
 */
public class SLVCBookingSummaryPgobj {

	public WebElement getBookingNo() {
		return bookingNo;
	}

	@FindBy(id = "bookingReference")
	private WebElement bookingNo;

	@FindBy(xpath = "//div[@class='ires']/h1")
	private WebElement summary_header;

	@FindBy(xpath = "//div[@class='ires']//p")
	private WebElement bookingStatus;

	public WebElement getSummary_header() {
		return summary_header;
	}

	public WebElement getBookingStatus() {
		return bookingStatus;
	}

}
