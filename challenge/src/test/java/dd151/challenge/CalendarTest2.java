package dd151.challenge;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CalendarTest2 {

	private WebDriver driver;

	@BeforeTest
	public void setup() {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//drivers//chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");
		options.addArguments("--remote-allow-origins=*");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().deleteAllCookies();
		driver.get("https://www.hyrtutorials.com/p/calendar-practice.html");
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 1500)");
	}

	@Test(dataProvider = "datePickerDataProvider")
	public void testCalendar(String date, String pickerStr) throws InterruptedException {
		int picker = Integer.valueOf(pickerStr);
		DatePicker datePicker = new DatePicker(driver, picker);
		datePicker.setPickerDate(date);
		Thread.sleep(2000);
		String pickerText = datePicker.getPickerDate();
		Assert.assertEquals(pickerText, getInputBoxDate(date));
	}

	public String getInputBoxDate(String date) {
		String[] dateArr = date.split(" ");
		String day = dateArr[0];
		String month = getNumericMonth(dateArr[1]);
		String year = dateArr[2];

		return month + "/" + day + "/" + year;

	}

	public String getNumericMonth(String month) {
		switch (month) {
		case "January":
			return "01";
		case "February":
			return "02";
		case "March":
			return "03";
		case "April":
			return "04";
		case "May":
			return "05";
		case "June":
			return "06";
		case "July":
			return "07";
		case "August":
			return "08";
		case "September":
			return "09";
		case "October":
			return "10";
		case "November":
			return "11";
		case "December":
			return "12";
		}
		return "";
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}

	@DataProvider(name = "datePickerDataProvider")
	public String[][] dataPickerProvider() {
		String[][] data = new String[][] { { "05 November 2023", "1" }, { "08 May 2024", "2" }, { "01 June 2027", "3" },
				{ "17 April 2029", "4" }, { "08 December 2023", "5" }, { "12 January 2024", "6" }

		};
		return data;
	}

}

class DatePicker {
	private int picker;
	private By pickerPath;
	private WebDriver driver;
	private WebElement pickerEl;

	DatePicker(WebDriver driver, int picker) {
		this.picker = picker;
		this.driver = driver;
		assignPickerPath();
	}

	private void assignPickerPath() {
		switch (picker) {
		case 1:
			this.pickerPath = By.id("first_date_picker");
			break;
		case 2:
			this.pickerPath = By.id("second_date_picker");
			break;
		case 3:
			this.pickerPath = By.id("third_date_picker");
			break;
		case 4:
			this.pickerPath = By.id("fourth_date_picker");
			break;
		case 5:
			this.pickerPath = By.id("fifth_date_picker");
			break;
		case 6:
			this.pickerPath = By.id("sixth_date_picker");
			break;
		}
	}

	public void setPickerDate(String date) {
		String[] dateArr = date.split(" ");
		String day = dateArr[0].startsWith("0") ? dateArr[0].substring(1) : dateArr[0];
		String month = dateArr[1];
		String year = dateArr[2];

		pickerEl = driver.findElement(pickerPath);
		if (picker == 6) {
			pickerEl.findElement(By.xpath("//following-sibling::img[@class='ui-datepicker-trigger']")).click();
		} else {
			pickerEl.click();
		}

		pickMonthYear(month, year);
		pickDay(day);

	}

	public String getPickerDate() {
		return driver.findElement(pickerPath).getAttribute("value");
	}

	private void pickMonthYear(String month, String year) {
		WebElement monthEl = driver.findElement(By.className("ui-datepicker-month"));
		WebElement yearEl = driver.findElement(By.className("ui-datepicker-year"));
		if (picker <= 2 || picker >= 5) {
			WebElement nextEl = driver.findElement(By.xpath("//span[normalize-space()='Next']"));
			String actualMonthYearText = monthEl.getText() + " " + yearEl.getText();
			String expectedMonthYearText = month + " " + year;

			while (!actualMonthYearText.equals(expectedMonthYearText)) {
				nextEl.click();
				monthEl = driver.findElement(By.className("ui-datepicker-month"));
				yearEl = driver.findElement(By.className("ui-datepicker-year"));
				nextEl = driver.findElement(By.xpath("//span[normalize-space()='Next']"));
				actualMonthYearText = monthEl.getText() + " " + yearEl.getText();
			}
		} else {
			Select selectMonthEl = new Select(monthEl);
			selectMonthEl.selectByVisibleText(month.substring(0, 3));
			yearEl = driver.findElement(By.className("ui-datepicker-year"));
			Select selectYearEl = new Select(yearEl);
			selectYearEl.selectByVisibleText(year);
		}
	}

	private void pickDay(String day) {
		List<WebElement> dateEls = driver.findElements(By.xpath(
				"//td/a[contains(@class,'ui-state-default')  and not(contains(@class,'ui-priority-secondary'))]"));
		for (WebElement el : dateEls) {
			if (el.getText().equals(day)) {
				el.click();
				break;
			}
		}
	}
}
