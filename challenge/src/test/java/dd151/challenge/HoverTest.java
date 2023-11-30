package dd151.challenge;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class HoverTest {
	private WebDriver driver;

	@BeforeTest
	public void setup() {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//drivers//chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");
		driver = new ChromeDriver(options);
		driver.manage().window().setSize(new Dimension(1000, 500));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		driver.manage().deleteAllCookies();
		driver.get("https://the-internet.herokuapp.com/hovers");
	}

	@Test
	public void hoverTest() {
		List<WebElement> figEls = driver.findElements(By.className("figure"));
		Actions a = new Actions(driver);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
		figEls.forEach(el -> {
			a.moveToElement(el).perform();
			WebElement figCapEl = wait
					.until(ExpectedConditions.visibilityOf(el.findElement(By.className("figcaption"))));
			System.out.println(figCapEl.findElement(By.tagName("h5")).getText());
			System.out.println(figCapEl.findElement(By.tagName("a")).getAttribute("href"));
		});
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
