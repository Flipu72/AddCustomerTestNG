package variousConcepts;
  
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LearnTestNG {

	WebDriver driver;
	String browser;

	@BeforeClass
	public void readConfig() {
		// InputStream //BufferedReader //FileReader //Scanner

		Properties prop = new Properties();

		try {
			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			prop.load(input);
			browser = prop.getProperty("browser");
			System.out.println("Browser used: " + browser);

		} catch (IOException e) {
			e.getStackTrace();
		}

	}

	@BeforeMethod
	public void init() {

		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://techfios.com/billing/?ng=admin/");
	}

	// @Test(priority=1)
	public void loginTest() {
		By userNameField = By.xpath("//input[@id='username']");
		By passwordField = By.xpath("//*[@id=\"password\"]");
		By signInButtonField = By.xpath("/html/body/div/div/div/form/div[3]/button");
		By dashboardField = By.xpath("//*[@id=\"page-wrapper\"]/div[2]/div/h2");

		driver.findElement(userNameField).clear();
		// userNameElement.clear();
		driver.findElement(userNameField).sendKeys("demo@techfios.com");
		driver.findElement(passwordField).sendKeys("abc123");
		driver.findElement(signInButtonField).click();

		String dashboardExpected = driver.findElement(dashboardField).getText();
		Assert.assertEquals("Dashboard", dashboardExpected, "Dashboard page not found!!!");

	}

	@Test(priority = 2)
	public void AddCustomerTest() {

		By userNameField = By.xpath("//input[@id='username']");
		By passwordField = By.xpath("//*[@id=\"password\"]");
		By signInButtonField = By.xpath("/html/body/div/div/div/form/div[3]/button");
		By dashboardField = By.xpath("//*[@id=\"page-wrapper\"]/div[2]/div/h2");
		By customerField = By.xpath("//*[@id=\"side-menu\"]/li[3]/a/span[1]");
		By addCustomerField = By.xpath("//*[@id=\"side-menu\"]/li[3]/ul/li[1]/a");
		
		
		By fullNameField = By.xpath("//*[@id=\"account\"]");
		By companyField = By.xpath("//*[@id=\"cid\"]");
		By emailField = By.xpath("//*[@id=\"email\"]");
		By countryField = By.xpath("//select[@id='country']");

		// Login Data
		String userName = "demo@techfios.com";
		String password = "abc123";

		// Test Data
		String fullName = "student";
		String companyName = "Techfios";
		String email = "abc123@techfios.com";
		String countryName = "Afghanistan";

		driver.findElement(userNameField).clear();
		// userNameElement.clear();
		driver.findElement(userNameField).sendKeys(userName);
		driver.findElement(passwordField).sendKeys(password);
		driver.findElement(signInButtonField).click();

		String dashboardExpected = driver.findElement(dashboardField).getText();
		Assert.assertEquals("Dashboard", dashboardExpected, "Dashboard page not found!!!");

		driver.findElement(customerField).click();
		driver.findElement(addCustomerField).click();
		
		driver.findElement(fullNameField).sendKeys(fullName + randomGenerator(999));
		
		selectFromDropdown(companyField, countryName);

		driver.findElement(emailField).sendKeys(randomGenerator(9999) + email);

		selectFromDropdown(countryField, countryName);

	}
	
	public int randomGenerator(int boundaryNumber) {
		Random rnd = new Random();
		int randomNo = rnd.nextInt(boundaryNumber);
		return randomNo;
	}

	public void selectFromDropdown(By field, String visibleText) {

		Select sel1 = new Select(driver.findElement(field));  //Element
		sel1.selectByVisibleText(visibleText);

	}

	// @AfterMethod
	public void tearDown() {
		driver.close();
		driver.quit();
	}

}