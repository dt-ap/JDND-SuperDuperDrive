package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.page.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	private static final String FIRST_NAME = "User";
	private static final String LARST_NAME = "Satu";
	private static final String USERNAME = "user1";
	private static final String PASSWORD = "pass123";

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private WebDriverWait wait;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		this.wait = new WebDriverWait(driver, 150);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void unauthorizedUserRedirectedToLogin() {
		driver.get("http://localhost:" + port + "/home");
		wait.until(ExpectedConditions.titleIs("Login"));
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void loginAndLogoutFlow() {
		driver.get("http://localhost:" + port + "/signup");
		var signupPage = new SignupPage(driver, wait);
		signupPage.signup(FIRST_NAME, LARST_NAME, USERNAME, PASSWORD);

		driver.get("http://localhost:" + port + "/login");
		var loginPage = new LoginPage(driver, wait);
		loginPage.login(USERNAME, PASSWORD);

		wait.until(ExpectedConditions.titleIs("Home"));
		Assertions.assertEquals("Home", driver.getTitle());

		driver.get("http://localhost:" + port + "/home");
		var homePage = new HomePage(driver, wait);
		homePage.logout();
		wait.until(ExpectedConditions.titleIs("Login"));
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get("http://localhost:" + port + "/home");
		wait.until(ExpectedConditions.titleIs("Login"));
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void noteAddEditDelete() {
		driver.get("http://localhost:" + port + "/signup");
		var signupPage = new SignupPage(driver, wait);
		signupPage.signup(FIRST_NAME, LARST_NAME, USERNAME, PASSWORD);

		driver.get("http://localhost:" + port + "/login");
		var loginPage = new LoginPage(driver, wait);
		loginPage.login(USERNAME, PASSWORD);

		wait.until(ExpectedConditions.titleIs("Home"));
		driver.get("http://localhost:" + port + "/home");
		var tab = new NoteTab(driver, wait);

		tab.clickNavTab();
		tab.add("Title 1", "Description 1");
		tab.add("Title 2", "Description 2");
		Assertions.assertEquals("Note created successfully!", tab.getSuccessSpan());
		Assertions.assertEquals(2, tab.getRowsCount());

		final var updated = "Updated Title 1";
		tab.edit(1, updated, null);
		Assertions.assertEquals("Note updated successfully!", tab.getSuccessSpan());
		Assertions.assertTrue(tab.isTitleExist(updated));

		tab.delete(1);
		Assertions.assertEquals("Note deleted successfully!", tab.getSuccessSpan());
		Assertions.assertEquals(1, tab.getRowsCount());
	}


	@Test
	public void credentialAddEditDelete() {
		driver.get("http://localhost:" + port + "/signup");
		var signupPage = new SignupPage(driver, wait);
		signupPage.signup(FIRST_NAME, LARST_NAME, USERNAME, PASSWORD);

		driver.get("http://localhost:" + port + "/login");
		var loginPage = new LoginPage(driver, wait);
		loginPage.login(USERNAME, PASSWORD);

		wait.until(ExpectedConditions.titleIs("Home"));
		driver.get("http://localhost:" + port + "/home");
		var tab = new CredentialTab(driver, wait);

		tab.clickNavTab();
		tab.add("https://urlsatu.com", "user1", "user1051");
		tab.add("https://urldua.com", "user2", "user20159");
		Assertions.assertEquals("Credential created successfully!", tab.getSuccessSpan());
		Assertions.assertEquals(2, tab.getRowsCount());

		final var updated = "https://newurlsatu.com";
		tab.edit(1, updated, null, null);
		Assertions.assertEquals("Credential updated successfully!", tab.getSuccessSpan());
		Assertions.assertTrue(tab.isUrlExists(updated));

		tab.delete(1);
		Assertions.assertEquals("Credential deleted successfully!", tab.getSuccessSpan());
		Assertions.assertEquals(1, tab.getRowsCount());
	}

}
