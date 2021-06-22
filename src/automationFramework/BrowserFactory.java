/**
 * 
 */
package automationFramework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

/**
 * @author tetambes
 *
 */
public class BrowserFactory {
	
private static WebDriver driver = null;
 
/**
 * @param browserType
 * @return
 */
public static WebDriver getBrowser(String browserType)
 
{
if(driver == null)
 
{
 
if(browserType.equals("Firefox"))
 
{
 
driver = new FirefoxDriver();
 
}
 
else if(browserType.equals("Chrome"))
 
{
 
driver = new ChromeDriver();
 
}
 
else if(browserType.equals("IE"))
 
{
 
driver = new InternetExplorerDriver();
 
}
 
}
return driver;
 
}
}
