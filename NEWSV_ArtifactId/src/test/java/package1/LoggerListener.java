package package1;
/*   про FireEventListener  тема протоколирование  */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class LoggerListener  {
  //  private EventFiringWebDriver driver;  // исп. когда нужно протоколировать команды/действия Selenium
    private WebDriver driver;
    private WebDriverWait wait;

 //   public static ThreadLocal<EventFiringWebDriver> tlDriver = new ThreadLocal<>();

    public static class MyListener extends AbstractWebDriverEventListener {

        @Override
        public void beforeFindBy(By by, WebElement element, WebDriver driver) {
           System.out.println(by);
        }

        @Override
        public void afterFindBy(By by, WebElement element, WebDriver driver) {
           System.out.println(by +  " found");
        }

        @Override
        public void onException(Throwable throwable, WebDriver driver) {
            System.out.println(throwable);

        }
    }

    @Before
    public void start() {
        // * Устанавливаем более  широкий уровень логгирования:
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        logPrefs.enable(LogType.DRIVER, Level.ALL);
        logPrefs.enable(LogType.CLIENT, Level.ALL);

        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        driver = new ChromeDriver(cap);

        //driver = new ChromeDriver();

        /* если надо протоколирование  действий Selenium :
        driver = new EventFiringWebDriver(new ChromeDriver());
        driver.register(new MyListener()); */

        wait = new WebDriverWait(driver,10);
    }
    @Test
    public void LoggingTest() {
        driver.navigate().to ("https://www.google.com");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        System.out.println(driver.manage().logs().getAvailableLogTypes());


        /*for (LogEntry l : driver.manage().logs().get("browser")) {
            System.out.println( l);
        }*/
        driver.findElement(By.name("q")).sendKeys("webdriver");
        WebElement btng =  driver.findElement(By.name("btnG"));
        btng.click();
        wait.until(titleIs("webdriver - Buscar con Google"));
    }

   /* @Test
    public void mySecondTest() {
        driver.navigate().to("https://www.google.com");
        wait.until((WebDriver d) -> d.findElement(By.name("q"))).sendKeys("webdriver");
        driver.findElement(By.name("btnG")).click();

        //.click();
        wait.until(titleIs("webdriver - Buscar con Google"));
    } */
/*
    @Test
    public void myThirdTest() {
        driver.navigate().to("https://www.google.com");
        driver.findElement(By.name("q")).sendKeys("webdriver");
        driver.findElement(By.name("btnG")).click();
        wait.until(titleIs("webdriver - Поиск в Google"));
    }
    */

    @After
    public void stop() {
        System.out.println("logs qty = " + driver.manage().logs().get("browser").getAll().size());
        driver.manage().logs().get("browser").getAll().forEach(l -> System.out.println(l));
        driver.quit();
        driver = null;
    }


}
