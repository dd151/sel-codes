package dd151.challenge;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CalendarTest {
	private WebDriver driver;
	By calendarElPath = By.className("ui-calendar");
	By nextArrowElPath = By.className("ui-datepicker-next-icon");
	By dateElPath = By.xpath("//td/a[contains(@class,'ui-state-default')]");

	@BeforeTest
	public void setup() {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//drivers//chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().deleteAllCookies();
		driver.get("https://www.irctc.co.in/nget/train-search");
	}

	@Test
	public void calendarTest() throws InterruptedException {
		selectCalDate("1", "December", "2023");
		Thread.sleep(3000);
	}

	public void selectCalDate(String day, String month, String year) throws InterruptedException {
		WebElement calendarEl = driver.findElement(calendarElPath);
		calendarEl.click();
		Thread.sleep(2000);

		String expectedMonthYearText = month + " " + year;

		WebElement nextEl = driver.findElement(nextArrowElPath);
		WebElement actualMonthEl = driver.findElement(By.className("ui-datepicker-month"));
		WebElement actualYearEl = driver.findElement(By.className("ui-datepicker-year"));

		String monthYearText = actualMonthEl.getText() + " " + actualYearEl.getText();
		while (!monthYearText.equals(expectedMonthYearText)) {
			nextEl.click();

			// Relocate the elements to over stale-element exception
			nextEl = driver.findElement(nextArrowElPath);
			actualMonthEl = driver.findElement(By.className("ui-datepicker-month"));
			actualYearEl = driver.findElement(By.className("ui-datepicker-year"));
			// Get the new monthYear Text
			monthYearText = actualMonthEl.getText() + " " + actualYearEl.getText();
		}

		List<WebElement> dateElList = driver.findElements(dateElPath);
		for (WebElement el : dateElList) {
			if (el.getText().equals(day)) {
				el.click();
				break;
			}
		}
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
