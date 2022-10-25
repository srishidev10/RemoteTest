package org.example;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URL;

public class ChromeStandaloneTest2 {

    public static void main(String args[]) throws Exception {
        new ChromeStandaloneTest2().test3();
    }

    @Test
    public void test3() throws Exception {
        /**	To run in Cotainer, initiate remote webdriver class*/
        URL url = new URL("http://localhost:4444/wd/hub");

        /** Set Chrome desired capabilties*/
        //Setting up capabilities to run our test script
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        RemoteWebDriver driver = new RemoteWebDriver(url,capabilities);

        driver.get("https://in.search.yahoo.com/?fr2=inr");
        System.out.println(driver.getTitle());
        driver.quit();
    }
}
