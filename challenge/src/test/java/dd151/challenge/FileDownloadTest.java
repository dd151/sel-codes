package dd151.challenge;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class FileDownloadTest {
	private WebDriver driver;
	private String path = System.getProperty("user.dir") + "\\target";

	@BeforeTest
	public void setup() {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//drivers//chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
//		options.addArguments("--incognito");
		
		Map<String, Object> prefs = new HashMap<>();
		prefs.put("download.default_directory", path);
		options.setExperimentalOption("prefs", prefs);
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		driver.manage().deleteAllCookies();
		driver.get("https://the-internet.herokuapp.com/download");
	}

	@Test
	public void fileDownloadTest() throws InterruptedException {
		String fName = "sampleFile2.txt";
		driver.findElement(By.linkText(fName)).click();
		Thread.sleep(5000);
		File f = new File(path + "\\" + fName);
		if(f.exists()) {
			Assert.assertTrue(true);
			f.delete();
		}else {
			Assert.assertTrue(false);
		}
		
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
