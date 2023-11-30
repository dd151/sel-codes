package dd151.challenge;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class WebTableTest {

	private WebDriver driver;
	private List<ArrayList<String>> data;

	@BeforeTest
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//drivers//chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		driver.manage().deleteAllCookies();
		driver.get("https://wpdatatables.com/documentation/table-examples/catalog-of-drivers/");
	}

	@Test(priority = 1, enabled = false)
	public void displayTableData() {

		data = new ArrayList<ArrayList<String>>();

		List<String> headerElsData = driver.findElements(By.xpath("//table[@id='table_1']/thead/tr/th")).stream()
				.map(WebElement::getText).limit(4).collect(Collectors.toList());
		data.add((ArrayList<String>) headerElsData);
		while (true) {
			List<WebElement> rowEls = driver.findElements(By.xpath("//table[@id='table_1']/tbody/tr"));
			for (WebElement row : rowEls) {
				List<String> colElsData = row.findElements(By.tagName("td")).stream().map(el -> el.getText()).limit(4)
						.collect(Collectors.toList());

				data.add((ArrayList<String>) colElsData);
			}

			WebElement nxtArrow = driver.findElement(By.id("table_1_next"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView()", nxtArrow);
			System.out.println(nxtArrow.getAttribute("class"));
			if (nxtArrow.getAttribute("class").contains("disabled")) {
				break;
			} else {
				nxtArrow.click();
			}
		}
	}

	@Test
	public void filterElements() {
		List<String> rowEls = driver.findElements(By.xpath("//table[@id='table_1']/tbody/tr")).stream()
				.filter(el -> el.findElements(By.tagName("td")).get(3).getText().contains(" A"))
				.map(el -> el.findElements(By.tagName("td")).get(0).getText()).collect(Collectors.toList());
		System.out.println(rowEls);
	}

	@Test(priority = 2, enabled = false)
	public void writeData() {
		ExcelUtils excelUtils = new ExcelUtils();
		excelUtils.writeDataToFile("wpdatatables", data);
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
