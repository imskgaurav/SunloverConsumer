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
public class SLVCTransfersSearchResultPgObj {

	@FindBy(css = "#btn_43")
	private WebElement bookBtn;

	public WebElement getBookBtn() {
		return bookBtn;
	}

}
