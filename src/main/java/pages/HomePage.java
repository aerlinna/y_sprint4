package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import org.openqa.selenium.NoSuchElementException;
import java.util.HashMap;
import java.util.Map;

public class HomePage {
    private WebDriver driver;
    private JavascriptExecutor jsExecutor;

    // Локаторы для вопросов FAQ
    private final Map<String, String> faqLocators = new HashMap<>();

    // Конструктор: инициализируем локаторы для FAQ
    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.jsExecutor = (JavascriptExecutor) driver;

        // Добавляем вопросы и их локаторы
        faqLocators.put("Сколько это стоит? И как оплатить?", "accordion__panel-0");
        faqLocators.put("Хочу сразу несколько самокатов! Так можно?", "accordion__panel-1");
        faqLocators.put("Как рассчитывается время аренды?", "accordion__panel-2");
        faqLocators.put("Можно ли заказать самокат прямо на сегодня?", "accordion__panel-3");
        faqLocators.put("Можно ли продлить заказ или вернуть самокат раньше?", "accordion__panel-4");
        faqLocators.put("Вы привозите зарядку вместе с самокатом?", "accordion__panel-5");
        faqLocators.put("Можно ли отменить заказ?", "accordion__panel-6");
        faqLocators.put("Я жизу за МКАДом, привезёте?", "accordion__panel-7");
    }

    // Открывает главную страницу
    public void openPage() {
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    // Нажимает на вопрос в FAQ и ждет появления ответа
    public void clickQuestion(String question) {
        String locatorAccordion = faqLocators.get(question);
        if (locatorAccordion == null) {
            throw new IllegalArgumentException("Локатор не найден для вопроса: " + question);
        }

        // Кликаем по вопросу
        WebElement questionButton = driver.findElement(By.xpath("//div[contains(text(), '" + question + "')]"));
        scrollToElement(questionButton);
        jsExecutor.executeScript("arguments[0].click();", questionButton);

        // Ждем появления ответа (время ожидания увеличено до 5 секунд)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorAccordion)));
    }

    // Получает текст ответа на вопрос
    public String getAnswerText(String question) {
        String locatorAccordion = faqLocators.get(question);
        if (locatorAccordion == null) {
            throw new IllegalArgumentException("Локатор не найден для вопроса: " + question);
        }

        // Ждем, пока ответ станет видимым (время ожидания увеличено до 5 секунд)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement answerElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorAccordion)));

        return answerElement.getText();
    }

    // Прокручивает страницу до элемента
    private void scrollToElement(WebElement element) {
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    // Локатор кнопки "Заказать" в верхней части страницы
    private final By orderButtonHeader = By.className("Button_Button__ra12g");

    // Локатор кнопки "Заказать" внизу страницы
    private final By orderButtonMain = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");

    // Проверяет, доступна ли кнопка "Заказать" в верхней части страницы
    public boolean isOrderButtonHeaderEnabled() {
        return driver.findElement(orderButtonHeader).isEnabled();
    }

    // Проверяет доступность второй кнопки "Заказать", ловит исключение, если элемент не найден
    public boolean isOrderButtonUltraBigEnabled() {
        try {
            WebElement button = driver.findElement(orderButtonMain);
            scrollToElement(button);
            return button.isEnabled();
        } catch (NoSuchElementException e) {
            System.err.println("Элемент не найден: " + orderButtonMain);
            return false;
        }
    }

    // Кликает по кнопке "Заказать" в верхней части страницы
    public void clickOrderButtonUp() {
        WebElement button = driver.findElement(orderButtonHeader);
        button.click();
    }

    // Кликает по кнопке "Заказать" внизу страницы
    public void clickOrderButtonMain() {
        WebElement button = driver.findElement(orderButtonMain);
        scrollToElement(button);
        button.click();
    }
}
