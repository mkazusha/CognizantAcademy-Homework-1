import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverSettings {
    public ChromeDriver driver;

    @BeforeAll
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/Maria/Projects/CognizantAcademy/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterAll
    public void close() {
        driver.quit();
    }
}
