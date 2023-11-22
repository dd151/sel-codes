package dd151.filter_01;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class FilterHandlingAssignment {

	static WebDriver driver;

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://www.t-mobile.com/tablets");

		Thread.sleep(5000);

		try {
//			selectFilter("Brands", "Apple", "TCL");
			selectFilter("Deals", "New", "Special offer");
//			selectFilter("Operating System", "iPadOS", "Android");
			selectFilter("Brands", "all");
			
			//Get Screenshot for better visual evidence
			getScreenshot();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Thread.sleep(2000);
			driver.quit();
		}
	}

	public static void selectFilter(String filterName, String... options) throws IOException {

		//Clicking on the Filter
		WebElement filterEl = driver
				.findElement(By.xpath("//legend[contains(text(),'" + filterName + "')]//ancestor::fieldset"));
		filterEl.click();

		
		//If "all" is sent as an argument -> Iterate through all possible options and click
		//Else iterate over the options passed in arguments -> Handled Valid and Invalid Options [Using Optional class]
		if (options.length == 1 && options[0].equals("all")) {
			List<WebElement> optionsElList = driver
					.findElements(By.xpath("//input[@name='" + filterName + "']/parent::span"));
			optionsElList.forEach(optionEl -> optionEl.click());
		} else {
			for (String option : options) {
				By optionXpath = By.xpath("//span[contains(text(),'" + option + "')]/../preceding-sibling::span");
				driver.findElement(optionXpath).click();
			}
		}
	}

	public static void getScreenshot() throws IOException {
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,505)");
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DD-hh-mm-ss-SSS");
		String formattedDate = formatter.format(date);
		File op = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(op, new File(System.getProperty("user.dir") + "\\screenshots\\" + formattedDate + ".png"));
	}
}