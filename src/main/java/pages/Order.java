package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class Order {
    private WebDriver driver;
    private JavascriptExecutor jsExecutor;

    // Конструктор
    public Order(WebDriver driver) {
        this.driver = driver;
        this.jsExecutor = (JavascriptExecutor) driver;
    }

    // Метод для открытия главной страницы
    public void openPage() {
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    private final By orderHeader = By.className("Order_Header__BZXOb");
    private final By name = By.xpath(".//input[@placeholder='* Имя']");
    private final By surname = By.xpath(".//input[@placeholder='* Фамилия']");
    private final By address = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By stateMetro = By.className("select-search__input");
    private final By telephone = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By buttonNext = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");
    private final String MetroStation = ".//button[@value='%s']";
    private final By yandexButton = By.xpath(".//*[@alt='Yandex']");
    private final By scooterButton = By.xpath(".//*[@alt='Scooter']");

    // Метод ожидания загрузки страницы заказа
    public Order waitForLoadOrderPage() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(driver ->
                driver.findElement(orderHeader).getText() != null &&
                        !driver.findElement(orderHeader).getText().isEmpty()
        );
        return this;
    }

    public Order inputName(String newName) {
        driver.findElement(name).sendKeys(newName);
        return this;
    }

    public Order inputSurname(String newSurname) {
        driver.findElement(surname).sendKeys(newSurname);
        return this;
    }

    public Order inputAddress(String newAddress) {
        driver.findElement(address).sendKeys(newAddress);
        return this;
    }

    public Order changeStateMetro(int stateName) {
        driver.findElement(stateMetro).click();
        By newStateMetro = By.xpath(String.format(MetroStation, stateName));
        jsExecutor.executeScript("arguments[0].scrollIntoView();", driver.findElement(newStateMetro)); // Прокрутка к элементу
        driver.findElement(newStateMetro).click(); // Выбор станции метро
        return this;
    }

    public Order inputTelephone(String newTelephone) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(telephone)); // Ожидание кликабельности поля
        driver.findElement(telephone).sendKeys(newTelephone);
        return this;
    }

    public void clickNextButton() {
        driver.findElement(buttonNext).click(); // Клик по кнопке "Далее"
    }

    public void clickYandex() {
        driver.findElement(yandexButton).click(); // Клик по кнопке "Яндекс"
    }

    public void clickScooter() {
        driver.findElement(scooterButton).click(); // Клик по кнопке "Скутер"
    }
}