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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AddCustomerTestNG {
	WebDriver driver;
	String browser;

	@BeforeClass
	public void readConfig() {
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

	@Test(priority = 1)
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
		By phoneField = By.xpath("//*[@id=\"phone\"]");
		By addressField = By.xpath("//*[@id=\"address\"]");
		By cityField = By.xpath("//*[@id=\"city\"]");
		By stateField = By.xpath("//*[@id=\"state\"]");
		By zipField = By.xpath("//*[@id=\"zip\"]");
		By countryField = By.xpath("//select[@id='country']");
		By submitButtonField = By.xpath("//*[@id=\"submit\"]");

		String userName = "demo@techfios.com";
		String password = "abc123";

		String fullName = "Paul Logan";
		String companyName = "SpiderMan LTD";
		String email = "a.ferrell@gmail.com";
		String phone = "7232218";
		String address = "2526 Spring St";
		String city = "Palestine";
		String state = "Texas";
		String zip = "75801";
		String countryName = "United States";

		driver.findElement(userNameField).clear();
		driver.findElement(userNameField).sendKeys(userName);
		driver.findElement(passwordField).sendKeys(password);
		driver.findElement(signInButtonField).click();

		String dashboardExpected = driver.findElement(dashboardField).getText();
		Assert.assertEquals("Dashboard", dashboardExpected, "Dashboard page not found!!!");

		driver.findElement(customerField).click();
		driver.findElement(addCustomerField).click();

		String dashboard1Expected = driver.findElement(dashboardField).getText();
		Assert.assertEquals("Dashboard", dashboardExpected, "Contacts page not found!!!");

		driver.findElement(fullNameField).sendKeys(fullName + randomGenerator(999));

		selectFromDropdown(companyField, companyName);

		driver.findElement(emailField).sendKeys(randomGenerator(9999) + email);
		driver.findElement(phoneField).sendKeys(randomGenerator(999) + phone);
		driver.findElement(addressField).sendKeys(address);
		driver.findElement(cityField).sendKeys(city);
		driver.findElement(stateField).sendKeys(state);
		driver.findElement(zipField).sendKeys(zip);
 

		selectFromDropdown(countryField, countryName);

		driver.findElement(submitButtonField).click();

	}

	public int randomGenerator(int boundaryNum) {
		Random rnd = new Random();
		int randomNo = rnd.nextInt(boundaryNum);
		return randomNo;
	}

	public void selectFromDropdown(By field, String visibleText) {
		Select sel1 = new Select(driver.findElement(field));
	}

	//  @AfterMethod
	public void tearDown() {
		driver.close();
		driver.quit();
	}
}
