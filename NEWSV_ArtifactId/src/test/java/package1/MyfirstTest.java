package package1;

        import org.junit.After;
        import org.junit.Before;
        import org.junit.Test;
        import org.openqa.selenium.By;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.chrome.ChromeDriver;
        import org.openqa.selenium.support.ui.WebDriverWait;

        import java.util.concurrent.TimeUnit;

        import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class MyfirstTest  {
  private WebDriver driver;
  private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver,10);
    }
    @Test
    public void myfirstTest() {
        driver.navigate().to ("https://www.google.com");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.name("q")).sendKeys("webdriver");
        WebElement btng =  driver.findElement(By.name("btnG"));
        btng.click();
        wait.until(titleIs("webdriver - Buscar con Google"));
    }

    @Test
    public void mySecondTest() {
        driver.navigate().to("https://www.google.com");
        wait.until((WebDriver d) -> d.findElement(By.name("q"))).sendKeys("webdriver");
        driver.findElement(By.name("btnG")).click();

                //.click();
        wait.until(titleIs("webdriver - Buscar con Google"));
    }
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
       driver.quit();
       driver = null;
   }


}

