package org.example;


import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;

import java.io.IOException;
import java.net.URL;

public class ChromeStandaloneTest {

	public static void main(String args[]) throws Exception {
		new ChromeStandaloneTest().test1();
	}

	@BeforeTest
	public static void startDocker() throws IOException, InterruptedException {
		Docker.startYamlFile();
	}

	@Test
	public void test1() throws Exception {
		/**	To run in Cotainer, initiate remote webdriver class*/
		URL url = new URL("http://localhost:4444/wd/hub");

		/** Set Chrome desired capabilties*/
		//Setting up capabilities to run our test script
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-dev-shm-usage");
		DesiredCapabilities capabilities = new DesiredCapabilities();

		capabilities.setCapability(ChromeOptions.CAPABILITY, options);

		RemoteWebDriver driver = new RemoteWebDriver(url,capabilities);

		driver.get("https://google.com");
		System.out.println(driver.getTitle());
		driver.quit();
	}

	@AfterTest
	public static void stopDocker() throws IOException, InterruptedException {
		Docker.stopDocker();
		if(Docker.checkServerStopped())
		{
			Docker.deleteLogs("server-logs");
		}

	}
}
