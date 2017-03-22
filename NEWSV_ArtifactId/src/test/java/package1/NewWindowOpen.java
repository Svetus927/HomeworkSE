package package1;

/**
 * Created by Svetlana Verkholantceva on 20/03/2017.
 * Задание 14. Проверьте, что ссылки открываются в новом окне

 Сделайте сценарий, который проверяет, что ссылки на странице редактирования страны открываются в новом окне.

 Сценарий должен состоять из следующих частей:

 1) зайти в админку
 2) открыть пункт меню Countries (или страницу http://localhost/litecart/admin/?app=countries&doc=countries)
 3) открыть на редактирование какую-нибудь страну или начать создание новой
 4) возле некоторых полей есть ссылки с иконкой в виде квадратика со стрелкой -- они ведут на внешние страницы и открываются в новом окне, именно это и нужно проверить.

 Конечно, можно просто убедиться в том, что у ссылки есть атрибут target="_blank". Но в этом упражнении требуется именно кликнуть по ссылке, чтобы она открылась в новом окне, потом переключиться в новое окно, закрыть его, вернуться обратно, и повторить эти действия для всех таких ссылок.

 Не забудьте, что новое окно открывается не мгновенно, поэтому требуется ожидание открытия окна.

 */
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.util.Set;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static junit.framework.TestCase.assertTrue;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlToBe;

public class NewWindowOpen {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver,10);

    }
    @Test
    public void Task14() {

        String countryUrl = "http://localhost/litecart/admin/?app=countries&doc=countries";
        String titleCountries = "Countries | My Store";
        loginAsAdmin(countryUrl, titleCountries);
// находим любую страну например андорра
        WebElement row = driver.findElements(By.cssSelector("tr.row ")).get(4);  // Андорра
        String country = row.findElement(By.cssSelector("td a")).getAttribute("textContent");
// переходим на страницу едит для редактирования
        row.findElement(By.cssSelector("td a")).click();
        wait.until(titleIs("Edit Country | My Store"));
        // находим линку квадратик
        WebElement linka= driver.findElements(By.cssSelector("i[class = 'fa fa-external-link'")).get(0);

        String mainWindow = driver.getWindowHandle();
        System.out.println("Current url before switch is "+ driver.getCurrentUrl());
        System.out.println("Current window handle is " + mainWindow);
        Set<String> oldWindows = driver.getWindowHandles();
        linka.click();


        String newWindow = thereIsWindowOtherThan(oldWindows);

        driver.switchTo().window(newWindow);
        wait.until(ExpectedConditions.titleContains("ISO"));
        System.out.println("Current url after switch is "+ driver.getCurrentUrl());
        driver.close();
        driver.switchTo().window(mainWindow);
        System.out.println("Current url after switch BACK is "+ driver.getCurrentUrl());



    }

    public String thereIsWindowOtherThan (Set<String> oldWindowHandles) {
        Set<String> newWindowHandles = driver.getWindowHandles(); // неупорядоч множество элементов типа string
        System.out.println("After clicling external link  open window qty =  " + newWindowHandles.size());
        for (String newWindowHandle : newWindowHandles) { // для каждого эл-та в множестве стрингов
            if (!oldWindowHandles.contains(newWindowHandle)) {

                System.out.println("New window handle is:  " + newWindowHandle);
                return newWindowHandle;
            }
        }
        return null;
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
