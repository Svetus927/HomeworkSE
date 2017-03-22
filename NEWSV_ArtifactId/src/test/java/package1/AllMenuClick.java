package package1;

/**
 * Created by Svetlana Verkholantceva on 22/03/2017.
 * Задание 7. Сделайте сценарий, проходящий по всем разделам админки
 Сделайте сценарий, который выполняет следующие действия в учебном приложении litecart.

 1) входит в панель администратора http://localhost/litecart/admin
 2) прокликивает последовательно все пункты меню слева, включая вложенные пункты
 3) для каждой страницы проверяет наличие заголовка (то есть элемента с тегом h1)
 */
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static javax.swing.text.html.CSS.getAttribute;
import static junit.framework.TestCase.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlToBe;
public class AllMenuClick {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver,10);

    }
    @Test
    public void Task7() {

        String countryUrl = "http://localhost/litecart/admin/?app=countries&doc=countries";
        String titleCountries = "Countries | My Store";
        loginAsAdmin(countryUrl, titleCountries);

        int itemSize = driver.findElements(By.cssSelector("li#app-")).size();

        System.out.println("ITEM qty = "+ itemSize);
        for (int i=0; i<itemSize; i++) {
            WebElement item = driver.findElements(By.cssSelector("li#app-")).get(i);
            System.out.println("ITEM "+i);// item.findElement(By.cssSelector("a")).getAttribute("textContent"));
            WebElement linka = item.findElement(By.cssSelector("a"));
            linka.click();
            item = driver.findElements(By.cssSelector("li#app-")).get(i);
            int  subItemSize = item.findElements(By.cssSelector("ul.docs li")).size();
            if (subItemSize >0 ) {
                for (int j=0; j< subItemSize; j++) {
                    item = driver.findElements(By.cssSelector("li#app-")).get(i);
                    // String.format
                    String css = "ul.docs li:nth-child(" + (j + 1) + ") a";
                    WebElement sublinka = item.findElement(By.cssSelector(css));
                    sublinka.click();
                    findHeader(css);
                }
            }
            else findHeader(" item without subitem ");

        }
    }

    public void loginAsAdmin(String useUrl, String waitForTitle){
        driver.navigate().to (useUrl);
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        wait.until(titleIs(waitForTitle));
    }
    public void findHeader (String cssSel) {

    WebElement header = driver.findElement(By.cssSelector("h1"));
    assertTrue("No header on a page got by clicking on "+cssSel,!(header==null));

    String head = header.getAttribute("textContent").substring(8);


    System.out.println("HEADER IS " + head);
    }
    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
