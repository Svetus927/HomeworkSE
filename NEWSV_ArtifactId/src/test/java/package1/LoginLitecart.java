package package1;
/**
 * Created by uasso on 24/02/2017.
 * включены  примеры  исползования  capabilities and commandline *опции командной строки
 */

    import org.junit.After;
    import org.junit.Before;
    import org.junit.Test;
    import java.util.concurrent.TimeUnit;

    import org.openqa.selenium.By;
    import org.openqa.selenium.WebDriver;
    import org.openqa.selenium.chrome.ChromeDriver;
    import org.openqa.selenium.chrome.ChromeOptions;
    import org.openqa.selenium.support.ui.WebDriverWait;
    import org.openqa.selenium.HasCapabilities;
    import org.openqa.selenium.remote.DesiredCapabilities;
    import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;


public class LoginLitecart {

        private WebDriver driver;
        private WebDriverWait wait;

        @Before
        public void start() {
            DesiredCapabilities caps = new DesiredCapabilities();
            ChromeOptions options = new ChromeOptions();
          //  options.setBinary("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
            options.addArguments("start-maximized");
            driver = new ChromeDriver(options);   // driver = new ChromeDriver(caps);
            wait = new WebDriverWait(driver,10);
            System.out.println(((HasCapabilities)driver).getCapabilities());  // привидение  типов к hascapability
        }
        @Test
        public void myfirstTest() {
          /*  driver.navigate().to ("http://localhost/litecart/admin/login.php?redirect_url=%2Flitecart%2Fadmin%2F");
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            wait.until(titleIs("My Store"));
            driver.findElement(By.name("username")).sendKeys("admin");
            driver.findElement(By.name("password")).sendKeys("admin");

            driver.findElement(By.name("login")).click();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
 */
        }


        @After
        public void stop() {
            driver.quit();
            driver = null;
        }

    }




