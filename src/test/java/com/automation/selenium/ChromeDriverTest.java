package com.automation.selenium;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * Created by haozuo on 3/22/16.
 */
public class ChromeDriverTest {

    private String testUrl;
    private WebDriver driver;

    @Before
    public void prepare() {
        //setup chromedriver
        System.setProperty(
                "webdriver.chrome.driver",
                "webdriver/chromedriver.exe");

        testUrl = "https://www.flipkart.com/";

        // Create a new instance of the Chrome driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--disable-notifications");

        driver = new ChromeDriver();

        //maximize window
        driver.manage().window().maximize();

        // And now use this to visit myBlog
        // Alternatively the same thing can be done like this
        // driver.navigate().to(testUrl);
        driver.get(testUrl);
    }

    @Test
    public void testTitle() throws IOException, InterruptedException {

        Thread.sleep(5000);

        //close popup
        driver.findElement(By.xpath(" /html/body/div[2]/div/div/button")).click();
        Thread.sleep(5000);

        takeScreenShot(driver);

        String firstTime = "#container > div > div._36fx1h._6t1WkM._3HqJxg > div._1YokD2._2GoDe3 > div:nth-child(2) > div:nth-child(3) > div > div > div";
        searchAndAddToCart(firstTime, 1);
        String secondItem = "#container > div > div._36fx1h._6t1WkM._3HqJxg > div._1YokD2._2GoDe3 > div:nth-child(2) > div:nth-child(5) > div > div > div";

        searchAndAddToCart(secondItem, 2);
        String thirdItem = "#container > div > div._36fx1h._6t1WkM._3HqJxg > div._1YokD2._2GoDe3 > div:nth-child(2) > div:nth-child(4) > div > div > div";
        searchAndAddToCart(thirdItem, 3);
        Thread.sleep(5000);

        driver.navigate().back();
        Thread.sleep(5000);

        driver.findElement(By.xpath(

                "//*[@id=\"container\"]/div/div[1]/div[1]/div[2]/div[5]/div/div")).click();
        Thread.sleep(5000);
        driver.findElement(By.cssSelector("#container > div > div._1Er18h > div > div._1YokD2._2GoDe3.col-12-12 > div:nth-child(1) > div > div:nth-child(2) > div > div.nZz3kj._1hNI6F > div._1uc2IE > div > button:nth-child(3)")).click();

        driver.findElement(By.cssSelector("#container > div > div._1Er18h > div > div._1YokD2._2GoDe3.col-12-12 > div:nth-child(1) > div > div:nth-child(2) > div > div.nZz3kj._1hNI6F > div._1uc2IE > div > button:nth-child(3)")).click();
        driver.findElement(By.cssSelector("#container > div > div._1Er18h > div > div > div:nth-child(1) > div > div:nth-child(3) > div > div.nZz3kj._1hNI6F > div > div:nth-child(2)")).click();
        Thread.sleep(3000);

        driver.findElement(By.cssSelector("#container > div > div._2wQ56C._1h3i_z > div > div.row._1lPa3S > div > div._3dsJAO._24d-qY.FhkMJZ")).click();

        System.out.println(driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[2]/div/div/div[1]/div/div[2]")).getText());

    }

    private void takeScreenShot(WebDriver webdriver) throws IOException {


        TakesScreenshot scrShot = ((TakesScreenshot) webdriver);

        //Call getScreenshotAs method to create image file

        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);

        //Move image file to new destination

        File DestFile = new File("test.jpg");

        //Copy file at destination

        FileUtils.copyFile(SrcFile, DestFile);

    }

    private void searchAndAddToCart(String item, int tabNumber) throws InterruptedException {
        //Search text box
        driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[1]/div[1]/div[2]/div[2]/form/div/div/input"))
                .sendKeys("mobile")
        ;
        Thread.sleep(5000);

        //Click on Search button
        driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[1]/div[1]/div[2]/div[2]/form/div/button")).click();
        // wait for load result
        Thread.sleep(5000);

        //select the item
        driver.findElement(By.cssSelector(item)).click();
        Thread.sleep(5000);

        //Tab
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        //Change tab
        driver.switchTo().window(tabs.get(tabNumber));
        Thread.sleep(5000);
        driver.findElement(By.cssSelector("#container > div > div._2c7YLP.UtUXW0._6t1WkM._3HqJxg > div._1YokD2._2GoDe3 > div._1YokD2._3Mn1Gg.col-5-12._78xt5Y > div:nth-child(2) > div > ul > li:nth-child(1) > button")).click();
        System.out.println(driver.getTitle());
    }

    @After
    public void teardown() throws IOException {
        // driver.quit();
    }

}
