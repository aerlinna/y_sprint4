package praktikum;

import pages.HomePage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.DriverRule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestOrderButton {

    private HomePage homePage;

    @Rule //  WebDriver
    public DriverRule driverRule = new DriverRule();

    @Before
    public void setUp() {
        // Создаем экземпляр WebDriver через DriverRule
        WebDriver driver = driverRule.getDriver();
        homePage = new HomePage(driver);
        homePage.openPage(); // Открываем главную страницу
    }

    @Test
    public void testHeaderOrderButton() {
        // Проверка активности кнопки "Заказать"
        assertTrue("Кнопка Заказать не активна или не найдена", homePage.isOrderButtonHeaderEnabled());

        // Нажатие на кнопку "Заказать"
        homePage.clickOrderButtonUp();

        // Проверка перехода на URL
        String expectedUrl = "https://qa-scooter.praktikum-services.ru/order";
        String actualUrl = driverRule.getDriver().getCurrentUrl();
        System.out.println(actualUrl);
        assertEquals("Кнопка Заказать в хедере не ведет на " + expectedUrl, expectedUrl, actualUrl);
    }

    @Test
    public void testSecondOrderButton() {
        // Проверка активности второй кнопки "Заказать"
        assertTrue("Вторая кнопка 'Заказать' не активна или не найдена", homePage.isOrderButtonUltraBigEnabled());

        // Нажатие на вторую кнопку "Заказать"
        homePage.clickOrderButtonMain();

        // Проверка перехода на нужный URL
        String expectedUrl = "https://qa-scooter.praktikum-services.ru/order";
        String actualUrl = driverRule.getDriver().getCurrentUrl(); // Получаем URL через DriverRule
        assertEquals("Вторая кнопка 'Заказать' не ведет на " + expectedUrl, expectedUrl, actualUrl);
    }

}