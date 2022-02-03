//package smuleComments.base;
//
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.nio.charset.Charset;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.nio.file.StandardOpenOption;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//
//import org.openqa.selenium.Alert;
//import org.openqa.selenium.By;
//import org.openqa.selenium.NoAlertPresentException;
//import org.openqa.selenium.UnhandledAlertException;
//import org.openqa.selenium.WebElement;
//import org.testng.annotations.Test;
//
//public class Learn2LearnProfile extends CommonMethods {
//
//	@Test
//	public void learn2LearnProfile() throws InterruptedException, IOException {
//
//		PrintWriter out = null;
//		BufferedWriter bufWriter;
//		int collabCount;
//
//		try {
//			driver = startApp(SMULE_URL);
//			hit(LOGIN_BUTTON);
//			hit(SELECT_LOGIN_TYPE);
//			enter(EMAIL_INPUT, "vgv.5597@gmail.com");
//			Thread.sleep(100);
//			enter(PASSWORD_INPUT, "JAYAVAISSH5");
//			hit(SUBMIT_LOGIN);
//
//			hit(PROFILE_ICON);
//			hit(PROFILE_TAB);
//
//			hit(SONG_ + TRACK + _TITLE);
//			List<WebElement> allCollabsLinks = driver.findElements(By.xpath(JOINS));
//
//			try {
//				bufWriter = Files.newBufferedWriter(Paths.get("./" + TRACK + " - Joiners.txt"), Charset.forName("UTF8"),
//						StandardOpenOption.WRITE, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
//				out = new PrintWriter(bufWriter, true);
//			} catch (IOException e) {
//			}
//
//			// Comments section for each collab
//			for (collabCount = 1; collabCount <= allCollabsLinks.size(); collabCount++) {
//				Thread.sleep(100);
//				int counter = 1;
//				hit(JOINS + "[" + collabCount + "]");
//				String joiner = driver.findElement(By.xpath("//a[@type='PERFORMER']/span")).getText();
//
//				if (!isElementPresent(LIKED)) {
//					hit(LIKE_TAB);
//					Thread.sleep(2000);
//				}
//				if (checkForExisitngJoin(joiner)) {
//					logger("-----------------------" + collabCount + "---------------------------------------");
//					logger(" Duplicate joiner - " + joiner + ". Skip.");
//					driver.navigate().back();
//					continue;
//				}
//				out.println(joiner);
//				hit(COMMENTS_TAB);
//				if (driver.findElement(By.xpath(COMMENTS_COUNT)).getText().contains("No comments")
//						|| !checkForPrevComments(joiner)) {
//
//					boolean retry = false;
//					logger("-----------------------" + collabCount + "---------------------------------------");
//					comment(joiner, LINE1, 1, retry);
//					logger(" Comment Line " + 1 + " has been posted successfully for " + joiner + ".");
//					retry = false;
//					comment(joiner, LINE2, 2, retry);
//					logger(" Comment Line " + 2 + " has been posted successfully for " + joiner + ".");
//					retry = false;
//					comment(joiner, LINE3, 3, retry);
//					logger(" Comment Line " + 3 + " has been posted successfully for " + joiner + ".");
//					retry = false;
//					comment(joiner, LINE4, 4, retry);
//					logger(" Comment Line " + 4 + " has been posted successfully for " + joiner + ".");
//					retry = false;
//					comment(joiner, LINE5, 5, retry);
//					logger(" Comment Line " + 5 + " has been posted successfully for " + joiner + ".");
//					retry = false;
//					comment(joiner, LINE6, 6, retry);
//					logger(" Comment Line " + 6 + " has been posted successfully for " + joiner + ".");
//					logger("------------------------------------------------------------------------");
//				} else {
//					break;
//				}
//				driver.navigate().back();
//			}
//
//			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
//			LocalDateTime now = LocalDateTime.now();
//			out.close();
//			Thread.sleep(2000);
//			driver.quit();
//		} catch (UnhandledAlertException e) {
//			try {
//				Alert alert = driver.switchTo().alert();
//				String alertText = alert.getText();
//				System.out.println("Alert data: " + alertText);
//				alert.accept();
//				return;
//			} catch (NoAlertPresentException ex) {
//				ex.printStackTrace();
//			}
//		}
//	}
//
//}
