package smuleComments.base;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ReCaptcha extends CommonMethods {

	public static RemoteWebDriver driver;

	public static void main(String args[]) {
		driver = startApp("https://www.google.com/recaptcha/api2/demo");
		recaptcha();
	}

	public static void recaptcha() {

		try {

			WebElement frame = driver.findElement(By.tagName("iframe"));
			driver.switchTo().frame(frame);

			driver.findElement(By.xpath("//div[@class='recaptcha-checkbox-border']")).click();
			driver.switchTo().defaultContent();

			List<WebElement> allhelpIFrames = driver
					.findElements(By.xpath("(//iframe[@title='recaptcha challenge expires in two minutes'])"));
			for (WebElement webElement : allhelpIFrames) {
				driver.switchTo().frame(webElement);
			}
			List<WebElement> allhelpButtons = driver
					.findElements(By.xpath("//div[@class='button-holder help-button-holder']"));
			for (WebElement webElement : allhelpButtons) {
				webElement.click();
				Actions act = new Actions(driver);
				act.sendKeys(Keys.ENTER).perform();

				try {
					new WebDriverWait(driver, Duration.ofSeconds(10))
							.until(ExpectedConditions.invisibilityOfElementLocated(
									By.xpath("//div[@class='button-holder help-button-holder']")));
				} catch (TimeoutException e) {
					driver.navigate().refresh();
					recaptcha();
				}
				return;
			}
		} catch (Exception noCaptcha) {
		} finally {
			driver.switchTo().defaultContent();
		}
	}
}
