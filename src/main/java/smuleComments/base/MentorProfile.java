package smuleComments.base;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class MentorProfile extends CommonMethods {

	@Test
	public void mentorProfile() throws InterruptedException, IOException {

		PrintWriter out = null;
		BufferedWriter bufWriter;
		int collabCount;
		String fileName = "./" + TRACK + " - Ranjani - Joiners.txt";
		boolean isScrollRequired = false;
		int collabCounter = 1;

		try {
			driver = startApp(SMULE_URL);
			hit(LOGIN_BUTTON);
			hit(SELECT_LOGIN_TYPE);
			enter(EMAIL_INPUT, "music@learn2learn.in");
			Thread.sleep(100);
			enter(PASSWORD_INPUT, "Patchakili@7890");
			hit(SUBMIT_LOGIN);

			hit(SEARCH);
			enter(SEARCH_CURSOR, "Ranjani_V");
			Actions act = new Actions(driver);
			act.sendKeys(Keys.ENTER).perform();
			hit(GO_TO_PROFILE);

			hit(SONG_ + TRACK + _TITLE);
			Thread.sleep(2000);

			if (isScrollRequired) {
				for (int i = 0; i < 3; i++) {
					((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
					Thread.sleep(2000);
				}
			}

			List<WebElement> allCollabsLinks = driver.findElements(By.xpath(JOINS));

			try {
				bufWriter = Files.newBufferedWriter(Paths.get(fileName), Charset.forName("UTF8"),
						StandardOpenOption.WRITE, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
				out = new PrintWriter(bufWriter, true);
			} catch (IOException e) {
			}

			Actions actions = new Actions(driver);

			// Comments section for each collab
			for (collabCount = collabCounter; collabCount <= allCollabsLinks.size(); collabCount++) {
				int counter = 1;

				if (isScrollRequired) {
					for (int i = 0; i < 2; i++) {
						Thread.sleep(2000);
						((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
					}
				}
				new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions
						.elementToBeClickable(driver.findElement(By.xpath(JOINS + "[" + collabCount + "]"))));
				actions.moveToElement(driver.findElement(By.xpath(JOINS + "[" + collabCount + "]"))).click().build()
						.perform();
				// hit(JOINS + "[" + collabCount + "]");
				String joiner = driver.findElement(By.xpath("//a[@type='PERFORMER']/span")).getText();

				if (!isElementPresent(LIKED)) {
					hit(LIKE_TAB);
					Thread.sleep(2000);
				}
				if (checkForExisitngJoin(joiner, fileName)) {
					logger("-----------------------" + collabCount + "---------------------------------------");
					logger(" Duplicate joiner - " + joiner + ". Skip.");
					driver.navigate().back();
					continue;
				}
				out.println(joiner);
				hit(COMMENTS_TAB);
				if (driver.findElement(By.xpath(COMMENTS_COUNT)).getText().contains("No comments")
						|| !checkForPrevComments(joiner)) {

					boolean retry = false;
					logger("-----------------------" + collabCount + "---------------------------------------");
					comment(joiner, LINE1, 1, retry);
					Thread.sleep(2000);
					logger(" Comment Line " + 1 + " has been posted successfully for " + joiner + ".");
					retry = false;
					comment(joiner, LINE2, 2, retry);
					Thread.sleep(2000);
					logger(" Comment Line " + 2 + " has been posted successfully for " + joiner + ".");
					retry = false;
					comment(joiner, LINE3, 3, retry);
					Thread.sleep(2000);
					logger(" Comment Line " + 3 + " has been posted successfully for " + joiner + ".");
					retry = false;
					comment(joiner, LINE4, 4, retry);
					Thread.sleep(2000);
					logger(" Comment Line " + 4 + " has been posted successfully for " + joiner + ".");
					retry = false;
					comment(joiner, LINE5, 5, retry);
					Thread.sleep(2000);
					logger(" Comment Line " + 5 + " has been posted successfully for " + joiner + ".");
					retry = false;
					comment(joiner, LINE6, 6, retry);
					Thread.sleep(2000);
					logger(" Comment Line " + 6 + " has been posted successfully for " + joiner + ".");
					logger("------------------------------------------------------------------------");
				} else {
					break;
				}
				driver.navigate().back();
			}
			out.close();
			Thread.sleep(2000);
			driver.quit();
		} catch (UnhandledAlertException e) {
			try {
				Alert alert = driver.switchTo().alert();
				String alertText = alert.getText();
				System.out.println("Alert data: " + alertText);
				alert.accept();
				return;
			} catch (NoAlertPresentException ex) {
				ex.printStackTrace();
			}
		}
	}

}
