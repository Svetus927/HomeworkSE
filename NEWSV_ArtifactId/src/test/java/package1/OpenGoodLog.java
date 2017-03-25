package package1;



/**
 * Created by Svetlana Verkholantceva on 24/03/2017.
 * Задание 17. Проверьте отсутствие сообщений в логе браузера
 Сделайте сценарий, который проверяет, не появляются ли в логе БРАУЗЕРА сообщения при открытии страниц в учебном
 приложении, а именно -- страниц товаров в каталоге в административной панели.

 Сценарий должен состоять из следующих частей:

 1) зайти в админку
 2) открыть каталог, категорию, которая содержит товары
 (страница http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1)
 3) последовательно открывать страницы товаров и проверять, не появляются ли в логе браузера сообщения (любого уровня)

 Можно оформить сценарий либо как тест, либо как отдельный исполняемый файл.
 */
        import org.junit.After;
        import org.junit.Assert;
        import org.junit.Before;
        import org.junit.Test;

        import java.util.List;
        import java.util.concurrent.TimeUnit;
        import java.util.logging.Level;

        import static javax.swing.text.html.CSS.getAttribute;
        import static junit.framework.TestCase.assertNotNull;
        import static junit.framework.TestCase.assertTrue;
        import static org.openqa.selenium.support.ui.ExpectedConditions.*;

        import org.openqa.selenium.By;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.support.ui.WebDriverWait;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.firefox.FirefoxDriver;
        import org.openqa.selenium.chrome.ChromeDriver;
        import org.openqa.selenium.chrome.ChromeOptions;
        import org.openqa.selenium.logging.LogEntry;
        import org.openqa.selenium.logging.LogType;
        import org.openqa.selenium.logging.LoggingPreferences;
        import org.openqa.selenium.remote.CapabilityType;
        import org.openqa.selenium.remote.DesiredCapabilities;
        import org.openqa.selenium.HasCapabilities;

public class OpenGoodLog {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
// * Устанавливаем более  широкий уровень логгирования:
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);
      /*  logPrefs.enable(LogType.DRIVER, Level.ALL);
        logPrefs.enable(LogType.CLIENT, Level.ALL); */

        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        driver = new ChromeDriver(cap);
        wait = new WebDriverWait(driver, 10);
        System.out.println("Available log types are: " + driver.manage().logs().getAvailableLogTypes());

    }

    @Test
    public void Task17() {

        String countryUrl = "http://localhost/litecart/admin/?app=countries&doc=countries";
        String titleCountries = "Countries | My Store";
        loginAsAdmin(countryUrl, titleCountries);

        driver.navigate().to("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");
        wait.until(titleIs("Catalog | My Store"));
        // считаем сколько всего строчек для редактиования  таблице:
        int pencilQty =  driver.findElements(By.cssSelector("tr.row a [class='fa fa-pencil']")).size();
        // считаем сколько строчек из них это фолдеры:
        int folderQty = driver.findElements(By.cssSelector("tr.row i[class='fa fa-folder-open']")).size();
        boolean BrowserLogPresent =  false;
        for (int i=folderQty; i<pencilQty; i++ ) {
            WebElement pencil = driver.findElements(By.cssSelector("tr.row a [class='fa fa-pencil']")).get(i);
            pencil.click();
            wait.until(titleContains("Edit Product"));
            System.out.println(driver.findElement(By.cssSelector("h1")).getAttribute("textContent")  );
            List<LogEntry> logs = driver.manage().logs().get("browser").getAll();
            if ( logs.size()>0 ) {
                BrowserLogPresent = true;
                for (LogEntry l : logs) {
                   // System.out.println(l.getLevel());
                    System.out.println(l);

                }
            }

           // System.out.println("logs qty = " + driver.manage().logs().get("browser").getAll().size());
            //driver.manage().logs().get("browser").getAll().forEach(l -> System.out.println(l)); */
            driver.navigate().back();
            wait.until(titleIs("Catalog | My Store"));
        }
        assertTrue("Logs of browser are present in opened pages! ", !BrowserLogPresent);
    }

    public void loginAsAdmin(String useUrl, String waitForTitle){
        driver.navigate().to (useUrl);
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        wait.until(titleIs(waitForTitle));
    }


}
