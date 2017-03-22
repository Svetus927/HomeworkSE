package package1;

/**
 * Created by Svetlana Verkholantceva on 12/03/2017.  домашка 11.
 * Задание 11. Сделайте сценарий регистрации пользователя
 Сделайте сценарий для регистрации нового пользователя в учебном приложении litecart (не в админке, а в клиентской части магазина).

 Сценарий должен состоять из следующих частей:

 1) регистрация новой учётной записи с достаточно уникальным адресом электронной почты (чтобы не конфликтовало с ранее созданными пользователями, в том числе при предыдущих запусках того же самого сценария),
 2) выход (logout), потому что после успешной регистрации автоматически происходит вход,
 3) повторный вход в только что созданную учётную запись,
 4) и ещё раз выход.

 В качестве страны выбирайте United States, штат произвольный. При этом формат индекса -- пять цифр.
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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.Keys;

public class NewUserEnter {
    private WebDriver driver;
    private WebDriverWait wait;
    private String userEmail;
    private String pwd;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver,10);
    }
    @Test
    public void Task11() {

        String targetUrl = "http://localhost/litecart/";
        String targettitle = "Online Store | My Store";

        driver.navigate().to(targetUrl);
        wait.until(titleIs(targettitle));

        userEmail = "svetutest5@gmail.com";
        pwd= "q123";
        RegisterNewAccount(userEmail, pwd);
        LogoutUser ();

        LoginAsUser(targettitle);
        LogoutUser ();

    }

    public void RegisterNewAccount(String regEmail, String setpwd) {
        WebElement createAccountLink = driver.findElement(By.cssSelector("tr a[href*=create_account"));
        createAccountLink.click();
        wait.until(titleIs("Create Account | My Store"));
        WebElement firstName = driver.findElement(By.name("firstname"));
        firstName.sendKeys("Svetlana");
        WebElement lastName = driver.findElement(By.name("lastname"));
        lastName.sendKeys("Verkh");
        WebElement address1 = driver.findElement(By.name("address1"));
        address1.sendKeys("Girasoles");
     //   WebElement address2 = driver.findElement(By.name("address2"));
        WebElement postCode = driver.findElement(By.name("postcode"));
        postCode.sendKeys("12345");
        WebElement city = driver.findElement(By.name("city"));
        city.sendKeys("Calpe");

        Select countrySelect = new Select(driver.findElement(By.cssSelector("[name=country_code]")));
        countrySelect.selectByVisibleText("United States");
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        Select zoneSelect = new Select(driver.findElements(By.cssSelector("[name=zone_code]")).get(1));
        zoneSelect.selectByVisibleText("Colorado");
        WebElement email= driver.findElement(By.name("email"));
        email.sendKeys(regEmail);

        WebElement phone= driver.findElement(By.name("phone"));
        phone.sendKeys(Keys.HOME+"+13551923");

        WebElement password= driver.findElement(By.name("password"));
        password.sendKeys(setpwd);
        WebElement cfmPassword= driver.findElement(By.name("confirmed_password"));
        cfmPassword.sendKeys(setpwd);

        WebElement createAccButton= driver.findElement(By.cssSelector("button[name=create_account]"));
        createAccButton.click();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        wait.until(titleIs("Online Store | My Store"));
        WebElement  success = driver.findElement(By.cssSelector(".account a[href*=edit_account"));
        assertTrue("A problem with  new user creation!", !(success==null));
        System.out.println("User with email " + regEmail+ " successfully created");
    }

    public void LogoutUser() {
        WebElement logoutlink = driver.findElement(By.cssSelector(".account a[href*=logout"));
        assertTrue("There is no logout link on a page!", !(logoutlink == null));
        logoutlink.click();
        System.out.println("User with email " + userEmail + " has successfully logged out.");
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    public void LoginAsUser ( String waitForTitle) {
        WebElement emailfield = driver.findElement(By.name("email"));
        emailfield.sendKeys(userEmail);
        WebElement pwdfield = driver.findElement(By.name("password"));
         pwdfield.sendKeys(pwd);
        driver.findElement(By.name("login")).click();
        wait.until(titleIs(waitForTitle));
        WebElement  success = driver.findElement(By.cssSelector(".account a[href*=edit_account"));
        assertTrue("Looks like a user logged in but there is no link to edit account!", !(success==null));
        System.out.println("User with email " + userEmail+ " successfully logged in");

    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
