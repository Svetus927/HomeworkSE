package package1;

/**
 * Created by Svetlana Verkholantceva on 09/03/2017. Домашка 9 сортировка стран
 */


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.util.List;
import java.util.concurrent.TimeUnit;
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


public class CountrySort {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver,10);

    }
    @Test
    public void Task9_1a() {

        String countryUrl ="http://localhost/litecart/admin/?app=countries&doc=countries";
        String titleCountries = "Countries | My Store";
        loginAsAdmin(countryUrl, titleCountries);

        List <WebElement> rows = driver.findElements(By.cssSelector("tr.row"));
        String countryPrev="";
        for (WebElement row : rows) {
           String country =   row.findElement(By.cssSelector("a:not([title=Edit])")).getAttribute("innerText");

            assertTrue("Country "+ country + " is not in its place!", country.compareTo(countryPrev) >0);
            countryPrev =country;
        }
        System.out.println("Country list on page "+ titleCountries + " is sorted correctly!");

    }
    @Test
    public void Task9_1b() {

        String countryUrl ="http://localhost/litecart/admin/?app=countries&doc=countries";
        String titleCountries = "Countries | My Store";
        loginAsAdmin(countryUrl, titleCountries);

        int rowsQty = driver.findElements(By.cssSelector("tr.row")).size();

        for (int i = 0; i<rowsQty; i++) {
            WebElement row = driver.findElements(By.cssSelector("tr.row")).get(i);
            WebElement  zoneQty =row.findElement(By.cssSelector("td:nth-child(6)"));

            String  zoneqtystr = zoneQty.getAttribute("innerText");
            if(!(zoneqtystr.compareTo("0")== 0)) {
                row.findElement(By.cssSelector("td:nth-child(3) a")).click();
                wait.until(titleIs("Edit Country | My Store"));

                assertGeoZonesAreSorted(); //
                driver.navigate().back();
                wait.until(urlToBe(countryUrl));
                //    int j =0;
            }
        }
        System.out.println("Zones for each country on page " +titleCountries + " are sorted!");
    }
    @Test
    public void Task9_2 () {

        String geozonesUrl ="http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones";
        String titleGeoZones = "Geo Zones | My Store";
        loginAsAdmin(geozonesUrl, titleGeoZones);

        List<WebElement> zonerows = driver.findElements(By.cssSelector("tr.row"));

        String prevzone="";

        for (WebElement zonerow : zonerows) {
            String zonename = zonerow.findElement(By.cssSelector("td:nth-child(3) a")).getAttribute("textContent");

            if (!zonename.isEmpty()) {
                assertTrue("Wrong sorting on a page "+ titleGeoZones +" " + zonename, zonename.compareTo(prevzone) > 0);
            }
            prevzone = zonename;
        }

        System.out.println(" Geozones on page "+ titleGeoZones+" are sorted correctly");

    }


    public void assertGeoZonesAreSorted () {

        List<WebElement> zonerows = driver.findElements(By.cssSelector("#table-zones tr:not([class=header])"));
        String prevzone="";
        //int i=0;
        for (WebElement zonerow : zonerows) {
            String zonename = zonerow.findElement(By.cssSelector("td:nth-child(3)")).getAttribute("innerText");

            if (!zonename.isEmpty()) {
                assertTrue("Wrong sorting! " + zonename, zonename.compareTo(prevzone) > 0);
            }
            prevzone = zonename;
        }
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






