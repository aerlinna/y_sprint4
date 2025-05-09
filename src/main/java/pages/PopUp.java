package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

//Страница https://qa-scooter.praktikum-services.ru/order После оформления заказа появится окно (popup) -Заказ оформлен

public class PopUp {
    WebDriver driver;
    private final By popUpHeaderAfterCreateOrder = By.xpath(".//div[text()='Заказ оформлен']");
    private final By buttonYes = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM' and text()='Да']");

    public PopUp(WebDriver driver) {

        this.driver = driver;
    }

    public void clickButtonYes() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(buttonYes)).click();
    }

    public String getHeaderAfterCreateOrder() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(driver -> (driver.findElement(popUpHeaderAfterCreateOrder).getText() != null
                && !driver.findElement(popUpHeaderAfterCreateOrder).getText().isEmpty()
        ));
        return driver.findElement(popUpHeaderAfterCreateOrder).getText();
    }
}