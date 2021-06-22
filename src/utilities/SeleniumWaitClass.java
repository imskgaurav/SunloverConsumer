package utilities;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.google.common.base.Function;

import automationFramework.Driver;

/**
 * This class perform wait operation Calls different wait conditions
 * 
 * @author tetambes
 * @since 2015-12-16
 **/
public class SeleniumWaitClass {

	WebDriverWait wait = null;

	/**
	 * implicity wait for script
	 */
	public void waitforPageToLoad() {

		Driver.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	public void until(ExpectedCondition<Boolean> elementToBeSelected) {

	}

	/**
	 * fluent wait to handle dynamic changing element
	 * 
	 * @param locator
	 *            to locate webelement
	 * @return
	 */
	public WebElement fluentWait(final By locator) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(Driver.driver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class, StaleElementReferenceException.class);

		WebElement WaitFor = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return Driver.driver.findElement(locator);
			}
		});

		return WaitFor;
	};

	/**
	 * wait until element become visible if element visible then click on it
	 * 
	 * @param locator
	 *            to locate element
	 * @param timeout
	 *            wait for web element
	 * @return
	 */
	public WebElement getWhenVisible(By locator, int timeout) {
		WebElement element = null;
		WebDriverWait wait = new WebDriverWait(Driver.driver, timeout);
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		return element;
	}

	/**
	 * wait when element get ready for execution
	 * 
	 * @param locator
	 *            to locate element
	 * @param timeout
	 *            wait for web element
	 */
	public void clickWhenReady(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(Driver.driver, timeout);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		element.click();
	}

	/**
	 * set page load timeout
	 */
	public void pageLoadTime() {
		Driver.driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
	}

	/**
	 * set script load timeout
	 */
	public void setScriptTime() {
		Driver.driver.manage().timeouts().setScriptTimeout(100, TimeUnit.SECONDS);
	}

	/**
	 * If element displayed click on it
	 * 
	 * @param by
	 *            to identify the web element
	 * @return boolean
	 */
	public boolean isElementPresent(By by) {
		try {
			Driver.driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * wait until the element is visible
	 * 
	 * @param webe
	 *            to select element
	 * @return boolean
	 */
	public static boolean isClickable(WebElement ele) {
		try {
			WebDriverWait wait = new WebDriverWait(Driver.driver, 5);
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Find element to avoid stale element error
	 * 
	 * @param by
	 *            to identify the web element
	 * @return boolean
	 */
	public boolean retryingFindClick(By by) {
		boolean result = false;
		int attempts = 0;
		while (attempts < 2) {
			try {
				Driver.driver.findElement(by).click();
				result = true;
				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}
		return result;
	}

	public void waitForVisibilityOfElement(WebElement elm) {

		wait = new WebDriverWait(Driver.driver, 20);
		wait.until(ExpectedConditions.visibilityOf(elm));

	}

	public void click(WebElement elm) {

		waitForVisibilityOfElement(elm);

		elm.click();
		
		
	}
	
	public void sendKeys(WebElement elm, String str){
		
		
	waitForVisibilityOfElement(elm);
	
	elm.clear();
	
	elm.sendKeys(str);
		
	}
	
}
