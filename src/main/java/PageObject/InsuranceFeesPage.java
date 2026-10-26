package PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InsuranceFeesPage extends BasePages{

    public InsuranceFeesPage(WebDriver driver) {
        super(driver);
    }
    @FindBy(id = "lbl_title")
    private WebElement pageHeader;

    @FindBy(xpath = "//*[@id=\"mainContent\"]/div[1]/div[2]/span/div[2]/p[3]/a")
    private WebElement calculatorLink;


    public String getHeaderText() {

        wait.until(ExpectedConditions.visibilityOf(pageHeader));
        return pageHeader.getText();

    }

    public InsuranceCalculatorPage clickCalculatorLink() {
        wait.until(ExpectedConditions.elementToBeClickable(calculatorLink)).click();
        return new InsuranceCalculatorPage(driver);
    }

}
