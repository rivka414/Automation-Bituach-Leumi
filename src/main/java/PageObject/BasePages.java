package PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePages {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePages(WebDriver driver) {
        this.driver = driver;
        // האיתור והאיתחול הקריטי:
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

//    public static class HomePage {
//
//    }
}
