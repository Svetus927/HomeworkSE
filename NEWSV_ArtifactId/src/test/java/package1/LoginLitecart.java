package package1;

    import org.junit.After;
    import org.junit.Before;
    import org.junit.Test;
    import org.openqa.selenium.By;
    import org.openqa.selenium.WebDriver;
    import org.openqa.selenium.chrome.ChromeDriver;
    import org.openqa.selenium.support.ui.WebDriverWait;

    import java.util.concurrent.TimeUnit;

    import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

/**
 * Created by uasso on 24/02/2017.
 */
public class LoginLitecart {

        private WebDriver driver;
        private WebDriverWait wait;

        @Before
        public void start() {
            driver = new ChromeDriver();
            wait = new WebDriverWait(driver,10);
        }
        @Test
        public void myfirstTest() {
            driver.navigate().to ("http://localhost/litecart/admin/");
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




