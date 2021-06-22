/**
 * 
 */
package utilities;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * @author tetambes
 *
 */
public class SelectDropdown {

	public String selectValueFromElement(WebElement element, String value) {
		Select select = new Select(
				element);
		select.selectByValue(value);
		return value;

	}

	public String selectTextFromElement(WebElement element, String text) {
	Select select = new Select(
				element);
		select.selectByVisibleText(text);
		return text;

	}

}
