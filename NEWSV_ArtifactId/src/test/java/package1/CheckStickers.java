package package1;

/**
 * Created by uasso on 03/03/2017.
 *  Задание 8. Сделайте сценарий, проверяющий наличие стикеров у товаров

 Сделайте сценарий, проверяющий наличие стикеров у всех товаров в учебном приложении litecart на главной странице.
 Стикеры -- это полоски в левом верхнем углу изображения товара, на которых написано New или Sale или что-нибудь другое.
 Сценарий должен проверять, что у каждого товара имеется ровно один стикер.

 Можно оформить сценарий либо как тест, либо как отдельный исполняемый файл.

 Если возникают проблемы с выбором локаторов для поиска элементов -- обращайтесь в чат за помощью.
 */


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;



public class CheckStickers {

    private WebDriver driverff;
    private WebDriverWait wait;

    @Before
    public void start() {
  /*      DesiredCapabilities caps = new DesiredCapabilities();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);    driver = new ChromeDriver(caps); */

        driverff = new FirefoxDriver();
        wait = new WebDriverWait(driverff,10);
      //  driverff.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }

    @Test
    public void Task8() {

          driverff.navigate().to ("http://localhost/litecart/");
            driverff.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            wait.until(titleIs("Online Store | My Store"));

            List <WebElement> wrappers =driverff.findElements(By.cssSelector(".image-wrapper"));

            int wrappers_qty= wrappers.size();
            int imagesqty = (driverff.findElements(By.cssSelector("img.image"))).size();
            int sales_qty = (driverff.findElements(By.cssSelector(".image-wrapper>.sticker.sale"))).size();
            int new_qty = (driverff.findElements(By.cssSelector(".image-wrapper>.sticker.new"))).size();

            if ((!(wrappers_qty == imagesqty)) ||  (!(wrappers_qty == sales_qty+new_qty)) ){
                System.out.println("Something  wrong with stickers! Stickers quantity is not equal to ducks quantity! ");
              }

            else {
                int image_sticker_qty;
                int wrongimages_qty =0;  // счет неправильных изображений
                for (WebElement wrapper : wrappers) {
                    List<WebElement>  items = wrapper.findElements(By.cssSelector("div.sticker"));
                    image_sticker_qty = items.size();
                    if (!( image_sticker_qty == 1 )) {
                        WebElement wrinfo =wrapper.findElement(By.cssSelector("img"));
                        System.out.println("An image has stickers quantity = "+ image_sticker_qty + ". The link is "+ wrinfo.getAttribute("src"));
                        wrongimages_qty++;
                    }
                }
                if (!( wrongimages_qty == 0 )) {
                    System.out.println("In total images with problem stickers = " + wrongimages_qty);
                    assert false;
                }
                System.out.println("OK, All ducks have only one sticker! ");
             }

    }


    @After
    public void stop() {
        driverff.quit();
        driverff = null;
    }

}





