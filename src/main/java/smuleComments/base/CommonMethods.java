package smuleComments.base;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CommonMethods extends Constants {

	public static RemoteWebDriver driver;
	public static File joinerList;

	public static boolean checkForPrevComments(String joiner) {

		List<WebElement> allComments = driver.findElements(By.xpath(COMMENTS));
		if (allComments.size() != 0) {
			for (WebElement comment : allComments) {
				if (comment.getAttribute("href").contains("learn2learnMusic")) {
					logger("\n");
					logger("Comments already added for " + joiner);
					logger("-------------------------------------\n");
					return true;
				}
			}
		}
		return false;
	}

	public static boolean checkForExisitngJoin(String joiner, String fileName) throws IOException {

		List<String> lines = Collections.emptyList();
		lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
		for (String line : lines) {
			if (line.equals(joiner)) {
				return true;
			}
		}
		return false;
	}

	public static RemoteWebDriver startApp(String url) {
		return startApp("chrome", url);
	}

	public static RemoteWebDriver startApp(String browser, String url) {
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		options.addExtensions(new File("./driverFile/Buster--Captcha-Solver-for-Humans.crx"));
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);

		ChromeDriver driver = new ChromeDriver(capabilities);

		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
		return driver;
	}

	public static void hit(String xpath) {
		driver.findElement(By.xpath(xpath)).click();
	}

	public static void enter(String xpath, String text) {
		if (isElementPresent(xpath)) {
			driver.findElement(By.xpath(xpath)).sendKeys(text);
		}
	}

	public static void comment(String joiner, String line, int size, boolean retry) throws InterruptedException {
		Thread.sleep(2000);
		enter(ADD_COMMENT, "@" + joiner + " " + line);
		hit(POST_COMMENT);
		Thread.sleep(1000);
		captchaVerify(joiner, line, size, retry);
		Thread.sleep(2000);
	}

	public static boolean isElementPresent(String xpath) {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		try {
			driver.findElement(By.xpath(xpath));
			return true;
		} catch (NoSuchElementException e) {
			return false;
		} finally {
			driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
		}
	}

	public static RemoteWebDriver captchaVerify(String joiner, String line, int size, boolean retry)
			throws InterruptedException {

		try {
			WebElement frame = driver.findElement(By.tagName("iframe"));
			driver.switchTo().frame(frame);
			driver.findElement(By.xpath("//div[@class='recaptcha-checkbox-border']")).click();
			driver.switchTo().defaultContent();

			List<WebElement> allhelpIFrames = driver
					.findElements(By.xpath("(//iframe[@title='recaptcha challenge expires in two minutes'])"));
			// for (WebElement webElement : allhelpIFrames) {
			String iFrame = "(//iframe[@title='recaptcha challenge expires in two minutes'])" + "["
					+ allhelpIFrames.size() + "]";
			new WebDriverWait(driver, Duration.ofSeconds(10))
					.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(iFrame)));
			// }
			List<WebElement> allhelpButtons = driver
					.findElements(By.xpath("//div[@class='button-holder help-button-holder']"));
			// for (WebElement webElement : allhelpButtons) {
			String helpButton = "//div[@class='button-holder help-button-holder']" + "[" + allhelpButtons.size() + "]";
			driver.findElement(By.xpath(helpButton)).click();
			Actions act = new Actions(driver);
			act.sendKeys(Keys.ENTER).perform();

			if (retry == false) {
				try {
					driver.switchTo().defaultContent();
					new WebDriverWait(driver, Duration.ofSeconds(10))
							.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(ADD_COMMENT))));
					return driver;
				} catch (TimeoutException e) {
					retry = true;
					driver.navigate().refresh();

					WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(10));
					if (w.until(ExpectedConditions.alertIsPresent()) != null) {
						Alert alert = driver.switchTo().alert();
						String alertText = alert.getText();
						System.out.println("Reload Alert: " + alertText);
						alert.accept();
					}
					Thread.sleep(2000);
					hit(COMMENTS_TAB);
					comment(joiner, line, size, retry);
				}
			}
			return driver;
			// }
		} catch (

		Exception noCaptcha) {
			return driver;
		} finally {
			driver.switchTo().defaultContent();
		}
		// return driver;
	}

	public static void logger(String text) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		System.out.println("[INFO]" + "[" + dtf.format(now) + "]" + text);
	}

}
