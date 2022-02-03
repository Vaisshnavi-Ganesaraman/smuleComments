package smuleComments.base;

public class Constants {

	public static final String SMULE_URL = "https://smule.com/";

	// Login
	public static final String LOGIN_BUTTON = "//a[text()='Login']";
	public static final String SELECT_LOGIN_TYPE = "//span[text()='Sign in with Email']";
	public static final String EMAIL_INPUT = "//input[@placeholder='Email']";
	public static final String PASSWORD_INPUT = "//input[@placeholder='Password']";
	public static final String SUBMIT_LOGIN = "//span[text()='Log In']";

	// Profile selection
	public static final String PROFILE_ICON = "//div[@class='sc-jxgQPf hBXCNW']";
	public static final String PROFILE_TAB = "//a[text()='Profile']";
	public static final String SEARCH = "//button[@aria-label='Search']";
	public static final String GO_TO_PROFILE = "//div[@class='user-results-container']/a";
	public static final String SEARCH_CURSOR = "//input[@type='search']";

	// Song Selection
	public static final String TRACK = "Maanguyile Poonguyile";
	public static final String SONG_ = "//a[contains(text(),'";
	public static final String _TITLE = "')]";
	public static final String COLLAB_COUNT = "//span[contains(text(),'collabs')]";
	public static final String JOINS = "(//div[contains(@class,'is-playable')]/a[2])";

	// Comment Section
	public static final String LIKED = "(//div[@class='sc-cSaEtG cHpspU'])";
	public static final String LIKE_TAB = "(//div[@class='sc-cuWcWY jmonRw'])[1]";
	public static final String COMMENTS_TAB = "(//div[@class='sc-cuWcWY jmonRw'])[3]";
	public static final String COMMENTS_COUNT = "//div[text()='Comments']/following-sibling::div/span";
	public static final String COMMENTS = "//div[@class='sc-hScDUP jzxFYP']/a";
	public static final String ADD_COMMENT = "//input[@placeholder='Add a comment']";
	public static final String POST_COMMENT = "//span[text()='Post']";
	public static final String CAPTCHA_VERIFY = "//div[@class='recaptcha-checkbox-border']";

	// Comment Text
	public static final String LINE1 = "Thanks for joining the collab. Extremely glad that you have taken efforts to join the Collab.";
	public static final String LINE2 = "For a brief feedback of your singing, please visit http://learn2learn.in and click 'Review request form' on top right.";
	public static final String LINE3 = "Paadarivom Padipparivom teaches a song every week. Join us on 1st & 3rd Fridays @7pm , 2nd & 4th Saturdays @11am (IST).";
	public static final String LINE4 = "We feature select joins on Sundays @6.05pm (IST).Please visit https://m.facebook.com/learn2learnmusic/ for more details.";
	public static final String LINE5 = "All these are absolutely FREE OF COST.";
	public static final String LINE6 = "Thank you if you have already submitted the collab.";

}
