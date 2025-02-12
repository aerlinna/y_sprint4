package pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.rules.ExternalResource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;


public class DriverRule extends ExternalResource {
    private WebDriver driver;

    // Метод before()
    @Override
    protected void before() throws Throwable {
        initDriver(); // Инициализация WebDriver.
    }

    // Метод after()
    @Override
    protected void after() {
        System.out.println("Закрытие WebDriver...");
        if (driver != null) {
            try {
                driver.quit();
                driver = null;
                System.out.println("WebDriver закрыт.");
            } catch (Exception e) {
                System.err.println("Ошибка при закрытии WebDriver: " + e.getMessage());
            }
        }
    }

    // Метод для инициализации WebDriver
    public void initDriver() throws Exception {
        if ("firefox".equalsIgnoreCase(System.getProperty("browser"))) {
            startUpFirefox(); // Запуск Firefox, если указан "firefox".
        } else {
            startUpChrome(); // По умолчанию  Chrome.
        }
    }

    // Метод для получения экземпляра WebDriver.
    public WebDriver getDriver() {
        return driver;
    }

    // Метод для настройки и запуска ChromeDriver.
    public void startUpChrome() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    // Метод для настройки и запуска FirefoxDriver.
    public void startUpFirefox() {
        WebDriverManager.firefoxdriver().setup();
        var opts = new FirefoxOptions()
                .configureFromEnv();
        driver = new FirefoxDriver(opts);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }
}