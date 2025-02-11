package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import pages.HomePage;
import pages.Order;
import pages.Rent;
import pages.PopUp;
import static org.junit.Assert.assertTrue;
import pages.DriverRule;
import org.junit.Rule;

@RunWith(Parameterized.class)
public class TestOrder {
    private HomePage homePage;
    private final String name;
    private final String surname;
    private final String address;
    private final int MetroStation;
    private final String telephoneNumber;
    private final String date;
    private final String duration;
    private final String colour;
    private final String comment;
    private final String OrderAccepted = "Заказ оформлен";

    @Rule
    public DriverRule driverRule = new DriverRule();

    public TestOrder(String name, String surname, String address, int MetroStation, String telephoneNumber,
                     String date, String duration, String colour, String comment) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.MetroStation = MetroStation;
        this.telephoneNumber = telephoneNumber;
        this.date = date;
        this.duration = duration;
        this.colour = colour;
        this.comment = comment;
    }

    @Parameterized.Parameters(name = "Аренда на: {6}")
    public static Object[][] getParameters() {
        return new Object[][]{
                {"Гарри", "Поттер", "Номер 4, Привит Драйв", 1, "71122334455", "01.09.2024", "1 день", "GREY", "Шалость удалась!"},
                {"Гермиона", "Грейнджер", "Хитгейт", 2, "72233745566", "05.09.2024", "3 дня", "BLACK", "У тебя эмоциональный диапазон, как у зубочистки!!"}
        };
    }

    @Before
    public void setUp() {
        WebDriver driver = driverRule.getDriver();
        homePage = new HomePage(driver);
        homePage.openPage(); // Загружаем главную страницу
    }

    @Test
    public void testScooterOrderOrder() {
        assertTrue("Кнопка Заказать в хедере не доступна", homePage.isOrderButtonHeaderEnabled());
        homePage.clickOrderButtonUp();
        new Order(driverRule.getDriver())
                .waitForLoadOrderPage()
                .inputName(name)
                .inputSurname(surname)
                .inputAddress(address)
                .changeStateMetro(MetroStation)
                .inputTelephone(telephoneNumber)
                .clickNextButton();

        new Rent(driverRule.getDriver())
                .waitAboutRentHeader()
                .inputDate(date)
                .inputDuration(duration)
                .changeColour(colour)
                .inputComment(comment)
                .clickButtonCreateOrder();

        PopUp popUpPage = new PopUp(driverRule.getDriver());
        popUpPage.clickButtonYes(); // Подтверждаем заказ
        assertTrue(popUpPage.getHeaderAfterCreateOrder().contains(OrderAccepted)); // Проверяем, что заказ оформлен
    }
}
