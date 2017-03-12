package package1;

/**
 * Created by uasso on 26/02/2017.
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;


public class LoginLiteCartFF {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
       /* WebDriver firefoxDriver = new FirefoxDriver();  объявление по другому*/
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver,10);
    }
    @Test
    public void myfirstTest() {
        driver.navigate().to ("http://localhost/litecart/admin/login.php?redirect_url=%2Flitecart%2Fadmin%2F");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait.until(titleIs("My Store"));
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");

        driver.findElement(By.name("login")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

}

