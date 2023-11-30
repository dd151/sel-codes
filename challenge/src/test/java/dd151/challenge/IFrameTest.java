package dd151.challenge;

import java.time.Duration;
import java.util.Stack;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class IFrameTest {

	private WebDriver driver;

	@BeforeTest
	public void setup() {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//drivers//chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		driver.manage().deleteAllCookies();
		driver.get("https://the-internet.herokuapp.com/iframe");
	}

	@Test(priority = 1)
	public void testNewDocument() {
		driver.switchTo().defaultContent();
		clickMainMenuItem("File");
		clickMenuItem("New document");
		Assert.assertTrue(getText().isEmpty());
	}

	@Test(priority = 2)
	public void testFormatText() throws InterruptedException {
		driver.switchTo().defaultContent();
		clickMainMenuItem("File");
		clickMenuItem("New document");
		String text = "A brand new Text";
		enterText(text);
		Assert.assertEquals(getText(), text);
		selectAllText();
		clickMainMenuItem("Format");
		clickMenuItem("Bold");
		Thread.sleep(2000);
		verifyTextStyle("Bold");
	}

	public void clickMainMenuItem(String item) {
		driver.findElement(By.xpath("//button[@role='menuitem']/span[text()='" + item + "']")).click();
	}

	public void clickMenuItem(String item) {
		driver.findElement(By.xpath("//div[@role='menuitem' and @title='" + item + "']")).click();
	}

	public void clickMenuItemCheckBox(String item) {
		driver.findElement(By.xpath("//div[@role='menuitemcheckbox' and @title='" + item + "']")).click();
	}

	public String getText() {
		switchToFrame();
		String text = driver.findElement(By.tagName("body")).findElement(By.tagName("p")).getText();
		switchToDefaultContent();
		return text;
	}

	public void enterText(String text) {
		switchToFrame();
		WebElement textArea = driver.findElement(By.tagName("body")).findElement(By.tagName("p"));
		textArea.sendKeys(text);
		switchToDefaultContent();
	}

	public void switchToFrame() {
		driver.switchTo().frame(0);
	}

	public void switchToDefaultContent() {
		driver.switchTo().defaultContent();
	}

	public void selectAllText() {
		switchToFrame();
		WebElement textArea = driver.findElement(By.tagName("body")).findElement(By.tagName("p"));
		Actions action = new Actions(driver);
		action.moveToElement(textArea).click().keyDown(Keys.CONTROL).keyDown("a").keyUp("a").keyUp(Keys.CONTROL)
				.perform();
		switchToDefaultContent();
	}

	public void verifyTextStyle(String style) {
		switchToFrame();
		WebElement textArea = driver.findElement(By.tagName("body")).findElement(By.tagName("p"));
		WebElement styledText = null;
		switch (style) {
		case "Bold":
			styledText = textArea.findElement(By.xpath("//descendant::strong"));
			break;
		}
		Assert.assertNotEquals(styledText, null);
		switchToDefaultContent();
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
