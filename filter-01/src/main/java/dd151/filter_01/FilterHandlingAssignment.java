package dd151.filter_01;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;
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

		Thread.sleep(2000);

		try {
//			selectFilter("Brands", "Apple", "TCL");
//			selectFilter("Deals", "New", "Special offer");
//			selectFilter("Operating System", "iPadOS", "Android");
			selectFilter("Brands", "all");
		} finally {
			Thread.sleep(2000);
			driver.quit();
		}
	}

	public static void selectFilter(String filterName, String... options) {

		WebElement filterEl = driver
				.findElement(By.xpath("//legend[contains(text(),'" + filterName + "')]//ancestor::fieldset"));
		filterEl.click();

		if (options.length == 1 && options[0].equals("all")) {
			List<WebElement> optionsElList = driver
					.findElements(By.xpath("//input[@name='" + filterName + "']/parent::span"));
			optionsElList.forEach(optionEl -> optionEl.click());
		} else {
			for (String option : options) {
				By optionXpath = By.xpath("//span[contains(text(),'" + option + "')]/../preceding-sibling::span");
				Optional.ofNullable(driver.findElement(optionXpath)).ifPresent(e -> e.click());
			}
		}
	}
}