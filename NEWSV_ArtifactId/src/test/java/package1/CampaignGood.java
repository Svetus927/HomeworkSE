package package1;

/**
 * Created by Svetlana Verkholantceva on 11/03/2017.  домашка 10
 */

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.util.List;
import java.util.concurrent.TimeUnit;
import static junit.framework.TestCase.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

public class CampaignGood {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver,10);

    }
    @Test
    public void Task10() {

        String targetUrl ="http://localhost/litecart/";
        String targettitle = "Online Store | My Store";
       // loginAsAdmin(targetUrl, targettitle);
        driver.navigate().to (targetUrl);
        wait.until(titleIs(targettitle));

        WebElement priceWrapper = driver.findElement(By.cssSelector("#box-campaigns li:first-child .price-wrapper"));
        WebElement regPriceInList = priceWrapper.findElement(By.cssSelector("s.regular-price"));
        WebElement camPriceInList = priceWrapper.findElement(By.cssSelector("strong.campaign-price"));
        String regPriceValue= regPriceInList.getAttribute("textContent");
        String camPriceValue= camPriceInList.getAttribute("textContent");
        priceWrapper.click();
        wait.until(titleContains("Subcategory"));



        System.out.println("Cprocess is going ");

    }



    public void loginAsAdmin(String useUrl, String waitForTitle){
        driver.navigate().to (useUrl);
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        wait.until(titleIs(waitForTitle));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

}
