package sunLoverTestSuite;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;
import org.testng.Assert;

import automationFramework.Driver;

public class CheckingAnElementText {

	public CheckingAnElementText() {

		PageFactory.initElements(Driver.driver, this);
	}

	@Test(description = "")
	public void TC_getElement_Text() {
		WebElement msgButton = Driver.driver.findElement(By.name("msg"));

		String msgText = msgButton.getText();

		Assert.assertEquals("Click on me and my color will change", msgText);
		System.out.println("Verify");

		// Asserstion with Partial Match using Java Script API

		Assert.assertTrue(msgText.contains("color"));

		Assert.assertTrue(msgText.startsWith("Click On"));

		Assert.assertTrue(msgText.endsWith("will change"));

	}

	@Test(description = "Checking an element's attribute values")
	public void testElementAttributeValue() {

		WebElement currency = Driver.driver.findElement(By.cssSelector(".currency"));
		
		Assert.assertEquals("AS$", currency.getAttribute("text"));

	}
	
	@Test(description="Let's create a test which reads the CSS width attribute and verifies the value.")
	public void testElementStyle(){
		
		WebElement elm= Driver.driver.findElement(By.name("button"));
		String width = elm.getCssValue("width");
		Assert.assertEquals("150px", width);
		
	}
	
	
}
