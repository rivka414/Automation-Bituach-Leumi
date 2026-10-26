package PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class UnemploymentPage extends BasePages{
    public UnemploymentPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//*[@id=\"mainContent\"]/div[1]/div[2]/span/div[2]/div[4]/a")
    private WebElement unemploymentBenefitCalculatorsLink;

    @FindBy(xpath = "//*[@id=\"ctl00_PlaceHolderMain_SiteNodesControl_ChildrensDiv\"]/ul/li[2]/a")
    private WebElement UnemploymentBenefitCalculation;

    @FindBy(css = "input[id*='PiturimDate']") // תאריך הפסקת עבודה
    private WebElement terminationDateInput;

    @FindBy(xpath = "//label[contains(@id, 'age_1_lbl')]") // שדה גיל
    private WebElement ageInput;

    @FindBy(css = "input[id*='StartNextButton']") // כפתור המשך
    private WebElement nextBtn1;
    @FindBy(css = "input[id*='StepNextButton']") // כפתור המשך
    private WebElement nextBtn2;

    // רשימת שדות שכר (דוגמה לשימוש ברשימה אם יש הרבה חודשים)
    @FindBy(css = "input[id*='Txt_Sallary']")
    private List<WebElement> salaryInputs;

    @FindBy(xpath = "//h3[contains(text(),'תוצאות החישוב')]")
    private WebElement theCalculationResultsPageHasBeenUploaded;
    // אלמנטים בדף התוצאות
    @FindBy(xpath = "//label[contains(text(),'שכר יומי ממוצע')]")
    private WebElement averageDailyWageLabel;

    @FindBy(xpath = "//label[contains(text(),'דמי אבטלה ליום')]")
    private WebElement dailyUnemploymentBenefitLabel;

    @FindBy(xpath = "//label[contains(text(),'דמי אבטלה לחודש')]")
    private WebElement monthlyUnemploymentBenefitLabel;



    public void goToCalculator() {
        wait.until(ExpectedConditions.elementToBeClickable(unemploymentBenefitCalculatorsLink)).click();
        wait.until(ExpectedConditions.elementToBeClickable(UnemploymentBenefitCalculation)).click();
    }

    public void fillStepOne(String date) {
        wait.until(ExpectedConditions.visibilityOf(terminationDateInput)).sendKeys(date);
        ageInput.click();
        nextBtn1.click();
    }

    public void fillSalariesAndFinish() {
        wait.until(ExpectedConditions.visibilityOfAllElements(salaryInputs));
        wait.until(d -> salaryInputs.size() > 0);
        Random random = new Random();

        for (WebElement input : salaryInputs) {
            // הגרלת מספר בין 3000 ל-7000
            int randomSalary = random.nextInt(4001) + 3000;
            input.clear();
            input.sendKeys(String.valueOf(randomSalary));
        }
        nextBtn2.click();
    }

    public boolean isResultsPageLoaded() {
        try {
            // נחכה עד 15 שניות כי החישוב לוקח זמן
            WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(15));
            return longWait.until(ExpectedConditions.visibilityOf(theCalculationResultsPageHasBeenUploaded)).isDisplayed();
        } catch (Exception e) {
            System.out.println("דף התוצאות לא הופיע: " + e.getMessage());
            return false;
        }
    }

    public boolean areAllResultsDisplayed() {
        return averageDailyWageLabel.isDisplayed() &&
                dailyUnemploymentBenefitLabel.isDisplayed() &&
                monthlyUnemploymentBenefitLabel.isDisplayed();
    }
}
