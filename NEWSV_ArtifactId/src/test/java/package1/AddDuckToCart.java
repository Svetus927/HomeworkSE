package package1;

/**
 * Created by Svetlana Verkholantceva on 15/03/2017. домашка 13
 */
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.util.List;
import java.util.concurrent.TimeUnit;
import java.lang.InterruptedException;
import static junit.framework.TestCase.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.Keys;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;


public class AddDuckToCart {
    private WebDriver driver;
    private WebDriverWait wait;
    //private String userEmail;private String pwd;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver,10);
    }
    @Test
    public void Task13() {

        for (int i=0; i<3; i++ ) {
            Addition(i);
            System.out.println("added element " +(i+1));

        }
        int k =0;
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        // удаление из корзины
        driver.navigate().to("http://localhost/litecart/en/checkout");
        wait.until(titleIs("Checkout | My Store"));
      //  $$("div#order_confirmation-wrapper td.item")
        int n = driver.findElements(By.cssSelector("div#order_confirmation-wrapper td.item")).size(); // сколько строк в таблице внизу
        for (int i=0; i<n; i++ ) {
            WebElement DuckDel = wait.until(presenceOfElementLocated(By.cssSelector("[name=cart_form]")));
            String refValue = DuckDel.findElement(By.cssSelector("input[name=key]")).getAttribute("value");
            DuckDel.findElement(By.cssSelector("[name=remove_cart_item]")).click(); // нажали ремув

            System.out.println("removed " +(i+1));
             By newItems = null;

            try {
                //строим локатор для проверки обновления строк в таблице внизу на странице чекаута
                newItems =By.cssSelector("div#order_confirmation-wrapper td.item");
            } catch (Exception e) {
                e.printStackTrace();
            }
            wait.until(ExpectedConditions.numberOfElementsToBe(newItems, n-1-i));

        }


    }
    public void Addition (int duckNumber)  {
        String targetUrl = "http://localhost/litecart/";
        String targettitle = "Online Store | My Store";
    //    driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        driver.navigate().to(targetUrl);
        wait.until(titleIs(targettitle));
        List <WebElement> ducks = driver.findElements(By.cssSelector("div.image-wrapper .image"));

        ducks.get(duckNumber).click();
        wait.until(visibilityOfElementLocated(By.name("add_cart_product")));

        int ssz = driver.findElements(By.cssSelector("select[required=required")).size(); //для уток с комбобоксом размера ssz дб =1

        System.out.println("ssz ПОИСКАЛИ " +ssz );
        if (ssz>0) {
            Select sizeSelect = new Select(driver.findElement(By.cssSelector("select[required=required")));
            sizeSelect.selectByVisibleText("Small");
        }
        String preQty = driver.findElement(By.cssSelector("span.quantity")).getAttribute("textContent");

        WebElement addTocart =driver.findElement(By.name("add_cart_product"));
        addTocart.click();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        // дожидаемся пока обновится колво товаров в корзине
        wait.until(not(ExpectedConditions.attributeContains(By.cssSelector("span.quantity"),"textContent",preQty)));

      //  String Qty = driver.findElement(By.cssSelector("span.quantity")).getAttribute("textContent");
    }
    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
