import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;


public class AutomationPracticeTest {
    private ChromeDriver driver;
    private String URL = "http://automationpractice.com/index.php";
    private final By SELECT_WOMEN_SECTION = By.xpath(".//a[@title='Women']");
    private final By SELECT_DRESSES_SECTION = By.xpath("(.//a[@title = 'Find your favorites dresses from our wide choice of evening, casual or summer dresses! \n" +
            " We offer dresses for every day, every style and every occasion.'])[1]");
    private final By CHOOSE_ORANGE_COLOR = By.xpath(".//input[@id='layered_id_attribute_group_13']");
    private final By ORANGE_COLOR = By.xpath(".//input[@id='layered_id_attribute_group_13']");
    private final By QUICK_VIEW = By.xpath(".//iframe[@class='fancybox-iframe']");
    private final By SELECTED_COLOR = By.xpath(".//li[@class='selected']");
    private final By PRICE = By.xpath(".//span[@id='our_price_display']");
    private final By ADD_TO_CART = By.xpath(".//button[@type='submit']");
    private final By CONTINUE_SHOPPING_BTN = By.xpath(".//i[@class='icon-chevron-left left']");
    private final By CART = By.xpath(".//b[contains(text(), 'Cart')]");
    private final By TOTAL_PRICE = By.xpath(".//td[@id='total_product']");
    private final By DRESS_COLORS = By.xpath(".//a[@class='color_pick']");
    private final By FILTERED_ITEMS = By.xpath(".//div[@class='product-container']");
    private final By QUICK_VIEW_BTN = By.xpath(".//a[@class='product_img_link']");
    private String price;
    private String cartTotalPrice;
    private WebElement selectedColor;
    private String actualColor;
    private String orange;
    private int match = 0;
    private int size;
    private double sum = 0;
    private double actualTotal;
    Random rand = new Random();
    List<WebElement> dresses;
    List<WebElement> availableColors;


    @Test
    public void checkFilter() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/Maria/Projects/CognizantAcademy/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get(URL);
        driver.findElement(SELECT_WOMEN_SECTION).click();
        driver.findElement(SELECT_DRESSES_SECTION).click();
        driver.findElement(CHOOSE_ORANGE_COLOR).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.invisibilityOfAllElements());
        dresses = driver.findElements(FILTERED_ITEMS);
        size = dresses.size();
        availableColors = driver.findElements(DRESS_COLORS);
        orange = driver.findElement(ORANGE_COLOR).getAttribute("style");
        for (WebElement color : availableColors) {
            actualColor = color.getAttribute("style");
            if (orange.equals(actualColor)) match++;
        }
        Assertions.assertEquals(size, match, "Filter is broken! Matched only " + match + " dresses but showed " + size);
        for (int i = 0; i < 2; i++) {
            dresses.get(rand.nextInt(size)).findElement(QUICK_VIEW_BTN).click();
            driver.switchTo().frame(driver.findElement(QUICK_VIEW));
            selectedColor = driver.findElement(SELECTED_COLOR);
            actualColor = selectedColor.findElement(By.tagName("a")).getAttribute("style");
            Assertions.assertEquals(orange, actualColor, "Orange color is not selected by default.");
            price = driver.findElement(PRICE).getText();
            driver.findElement(ADD_TO_CART).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(CONTINUE_SHOPPING_BTN));
            driver.findElement(CONTINUE_SHOPPING_BTN).click();
            sum += convertValue(price);
        }
        driver.findElement(CART).click();
        cartTotalPrice = driver.findElement(TOTAL_PRICE).getText();
        actualTotal = convertValue(cartTotalPrice);
        Assertions.assertEquals(sum, actualTotal, "Price is not correct!");
        driver.quit();
    }


    public double convertValue(String stringNumber) {
        stringNumber = stringNumber.substring(1);
        double converted = Double.parseDouble(stringNumber);
        return converted;
    }
}
