/**
 * 
 */
package automationFramework;

import java.io.File;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @author
 *
 */
public class Driver {

	//public static WebDriver driver = new FirefoxDriver();
	
	static{
		File f= new File("");
	
		
	System.setProperty("webdriver.chrome.driver", f.getAbsolutePath());
		
	}
	public static WebDriver driver= new ChromeDriver();

}
