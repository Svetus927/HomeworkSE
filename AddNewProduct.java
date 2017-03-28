package package1;

/**
 * Created by Svetlana Verkholantceva on 25/03/2017.
 * Задание 12. Сделайте сценарий добавления товара
 Сделайте сценарий для добавления нового товара (продукта) в учебном приложении litecart (в админке).

 Для добавления товара нужно открыть меню Catalog, в правом верхнем углу нажать кнопку "Add New Product",
 заполнить поля с информацией о товаре и сохранить.

 Достаточно заполнить только информацию на вкладках General, Information и Prices.
 Скидки (Campains) на вкладке Prices можно не добавлять.

 Переключение между вкладками происходит не мгновенно, поэтому после переключения можно сделать небольшую паузу
 (о том, как делать более правильные ожидания, будет рассказано в следующих занятиях).

 Картинку с изображением товара нужно уложить в репозиторий вместе с кодом.
 При этом указывать в коде полный абсолютный путь к файлу плохо, на другой машине работать не будет.
 Надо средствами языка программирования преобразовать относительный путь в абсолютный.

 После сохранения товара нужно убедиться, что он появился в каталоге (в админке).
 Клиентскую часть магазина можно не проверять.

 Можно оформить сценарий либо как тест, либо как отдельный исполняемый файл.
 *
 */
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Keys;
import sun.dc.path.PathException;

import java.net.URISyntaxException;
import java.net.URL;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


import static junit.framework.TestCase.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.javaScriptThrowsNoExceptions;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;


public class AddNewProduct {

    private WebDriver driver;
    private WebDriverWait wait;

    // * Main data for new product, used further in  presence check  //
    String productName = "Sleeping Beauty";
    String productCode = "sb005";
    String imageFileName = "BeautyDuck.png"; // stored in src\test\resources

    @Before
    public void start() {

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver,10);


    }
    @Test
    public void Task12() throws URISyntaxException {

        String countryUrl = "http://localhost/litecart/admin/";
        String titleCountries = "My Store";
        loginAsAdmin(countryUrl, titleCountries);

        // * Open catalog   *//
        WebElement catalogItem = driver.findElements(By.cssSelector("li#app- a")).get(1);
        String itemName = catalogItem.findElement(By.className("name")).getAttribute("textContent");
        assertTrue("Menu item with 'Catalog' name is not found! ", itemName.compareTo("Catalog")==0);
        catalogItem.click();
        wait.until(titleIs("Catalog | My Store"));

        newGoodAdd();
        goodPresenceCheck(productName, productCode);

    }
    public void goodPresenceCheck (String goodName, String goodCode) {
        int rowQty = driver.findElements(By.cssSelector("tr.row")).size();
        List<WebElement> rows = driver.findElements(By.cssSelector("tr.row"));
        boolean found = false; // Индикатор, когда  продукт с нужным именем и кодом будет найден, становится true.
        for (int i = rowQty-1; i>=0; i--) {
            String curProductName = rows.get(i).findElement(By.cssSelector("td a")).getAttribute("textContent");
            if (curProductName.compareTo(productName)==0) {
                rows.get(i).findElement(By.cssSelector("td a")).click();
                wait.until(titleContains("Edit Product"));
                String curCode =driver.findElement(By.name("code")).getAttribute("value");
                if (curCode.compareTo(productCode)==0 ) {
                    System.out.println("New added product with code " + productCode + " successfully found in the product list!");
                    i = -1;
                    found = true;
                }
                else   System.out.println("Name is the same with added, but code is different. Finding next...");


            }
        }
    assertTrue("New product with code " + productCode + "IS NOT FOUND IN THE PRODUCT LIST!", found);

    }
    public void newGoodAdd() {
        WebElement addProductButton  =  driver.findElement(By.cssSelector("a.button[href*=edit_product]"));
        addProductButton.click();
        wait.until(titleIs("Add New Product | My Store" ));

        ///////////////  * FIILING INFO FOR NEW PRODUCT  *///////////////////////////////
        //           General tab:
        driver.findElement(By.cssSelector("input[name=status][value='1']")).click();
        driver.findElement(By.name("name[en]")).sendKeys(productName);
        driver.findElement(By.name("code")).sendKeys(productCode);
        WebElement category = driver.findElement(By.name("categories[]"));
        if (! category.isSelected() ) {
            category.click();
        }
        Select catSelect = new Select(driver.findElement(By.cssSelector("select[name=default_category_id")));
        catSelect.selectByVisibleText("Root");
        driver.findElement(By.name("quantity")).clear();
        driver.findElement(By.name("quantity")).sendKeys("3");

        WebElement imageField = driver.findElement(By.name("new_images[]"));
        String fullPathString = convertToAbsolutePath(imageFileName).toString();
        imageField.sendKeys(fullPathString);

        driver.findElement(By.name("date_valid_from")).sendKeys("01.01.2017");
        driver.findElement(By.name("date_valid_to")).sendKeys("01.01.2018");

        // * Information Tab:  *//
        driver.findElement(By.cssSelector("a[href='#tab-information']")).click();
        wait.until(ExpectedConditions.visibilityOf( driver.findElement(By.name("manufacturer_id"))));
        Select manufacturerSelect = new Select(driver.findElement(By.name("manufacturer_id")));
        manufacturerSelect.selectByVisibleText("ACME Corp.");
        driver.findElement(By.name("keywords")).sendKeys("Plastic duck");
        driver.findElement(By.name("short_description[en]")).sendKeys("Plastic duck");
        driver.findElement(By.cssSelector("div.trumbowyg-editor")).sendKeys("This is a very good product!");
        driver.findElement(By.name("head_title[en]")).sendKeys("Plastic duck");
        driver.findElement(By.name("meta_description[en]")).sendKeys("meta Plastic duck");

        // * DATA Tab:  *//
        driver.findElement(By.cssSelector("a[href='#tab-data']")).click();
        wait.until(ExpectedConditions.visibilityOf( driver.findElement(By.name("sku"))));
        driver.findElement(By.name("sku")).sendKeys("123");
        driver.findElement(By.name("gtin")).sendKeys("123");
        driver.findElement(By.name("taric")).sendKeys("123");

        driver.findElement(By.name("weight")).clear();
        driver.findElement(By.name("weight")).sendKeys("50");

        Select weightSelect = new Select (driver.findElement(By.name("weight_class")));
        weightSelect.selectByVisibleText("g");

        driver.findElement(By.name("dim_x")).clear();
        driver.findElement(By.name("dim_x")).sendKeys("10");

        driver.findElement(By.name("dim_y")).clear();
        driver.findElement(By.name("dim_y")).sendKeys("15");

        driver.findElement(By.name("dim_z")).clear();
        driver.findElement(By.name("dim_z")).sendKeys("5");
        driver.findElement(By.name("attributes[en]")).sendKeys("Duck attributes");


        // * PRICES Tab:  *//
        driver.findElement(By.cssSelector("a[href='#tab-prices']")).click();
        wait.until(ExpectedConditions.visibilityOf( driver.findElement(By.name("purchase_price"))));

        driver.findElement(By.name("purchase_price")).clear();
        driver.findElement(By.name("purchase_price")).sendKeys("14");

        Select curSelect = new Select(driver.findElement(By.name("purchase_price_currency_code")));
        curSelect.selectByVisibleText("Euros");

        driver.findElement(By.name("gross_prices[EUR]")).clear();

        driver.findElement(By.name("gross_prices[EUR]")).sendKeys("20");

        //////////////////////////  End of entering info about new product  //////////////////////////////////////////


        driver.findElement(By.name("save")).click();
        wait.until(titleIs("Catalog | My Store"));
        System.out.println("All the steps of addition performed up to Save and return to catalog.");
    }
    public Path convertToAbsolutePath(final String fileName) {

        Path fullPath = Paths.get("src/test/resources/" + fileName); // get relative path

        return fullPath.toAbsolutePath();  // convert to absolete
        /* another way to do do the same :
        URL PathURL = AddNewProduct.class.getResource("/");
        URI uri = new URI(PathURL.toString()+fileName);
        String fullPathString = Paths.get(uri).toAbsolutePath().toString();  */
    }

    public void loginAsAdmin(String useUrl, String waitForTitle){
        driver.navigate().to (useUrl);
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        wait.until(titleIs(waitForTitle));
    }

 /*   public void setDatepicker(WebDriver driver, String cssSelector, String date) {
      //  new WebDriverWait(driver, 3000).until((WebDriver d) -> d.findElement(By.cssSelector(cssSelector)).isDisplayed());
        WebElement dateFrom = driver.findElement(By.cssSelector(cssSelector));
        JavascriptExecutor.class.cast(driver).executeScript(
                String.format("$('%s').datepicker('setDate', '%s')", cssSelector, date));
    } */

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

}
