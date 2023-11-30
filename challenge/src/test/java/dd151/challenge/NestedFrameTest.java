package dd151.challenge;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class NestedFrameTest {
	private WebDriver driver;

	@BeforeMethod
	public void setup() {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//drivers//chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		driver.manage().deleteAllCookies();
		driver.get("https://the-internet.herokuapp.com/");
	}

	@Test(enabled = true)
	public void nestedFrameTest() {
		driver.findElement(By.linkText("Frames")).click();
		driver.findElement(By.linkText("Nested Frames")).click();

		driver.switchTo().frame("frame-top");

		String leftFrameText = driver.switchTo().frame("frame-left").findElement(By.tagName("body")).getText();

		driver.switchTo().parentFrame();
		String midFrameText = driver.switchTo().frame("frame-middle").findElement(By.tagName("body")).getText();

		driver.switchTo().parentFrame();
		String rightFrameText = driver.switchTo().frame("frame-right").findElement(By.tagName("body")).getText();

		driver.switchTo().defaultContent();
		String bottomFrameText = driver.switchTo().frame("frame-bottom").findElement(By.tagName("body")).getText();

		System.out.println(leftFrameText);
		System.out.println(midFrameText);
		System.out.println(rightFrameText);
		System.out.println(bottomFrameText);
	}


	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
