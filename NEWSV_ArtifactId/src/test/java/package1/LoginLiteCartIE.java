package package1;

/**
 * Created by uasso on 26/02/2017
 * включены  примеры  исползования  сcapabilities
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;


public class LoginLiteCartIE {

    private WebDriver driver;
    private WebDriverWait wait;
 //   private DesiredCapabilities caps;

    @Before
    public void start() {
      DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("unexpectedAlertBehaviour", "ignore");  // игнорит окошки левые
        driver = new InternetExplorerDriver(caps);
        wait = new WebDriverWait(driver,10);
        System.out.println(((HasCapabilities) driver).getCapabilities() );  // привидение типов  к типу hascapabilities
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





