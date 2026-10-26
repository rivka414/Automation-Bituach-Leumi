package PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Calendar;

public class InsuranceCalculatorPage extends BasePages {
    public InsuranceCalculatorPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }
    @FindBy(id = "ctl00_ctl43_g_642b1586_5c41_436a_a04c_e3b5ba94ba69_ctl00_InsuranceNotSachirWizard_rdb_GetNechut") // זהו הדיב שמכיל את צעד 2
    private WebElement stepTwoIndicator;

    // לוקייטורים - שלב 1
    private By pageHeader = By.cssSelector("#mainContent div h1");
    private By genderMale = By.id("ctl00_ctl43_g_642b1586_5c41_436a_a04c_e3b5ba94ba69_ctl00_InsuranceNotSachirWizard_rdb_Gender_0");
    private By yeshivaStudentRadio = By.id("ctl00_ctl43_g_642b1586_5c41_436a_a04c_e3b5ba94ba69_ctl00_InsuranceNotSachirWizard_rdb_employeType_0");
    private By birthDateInput = By.id("ctl00_ctl43_g_642b1586_5c41_436a_a04c_e3b5ba94ba69_ctl00_InsuranceNotSachirWizard_DynDatePicker_BirthDate_Date");
    private By nextButtonStep1 = By.id("ctl00_ctl43_g_642b1586_5c41_436a_a04c_e3b5ba94ba69_ctl00_InsuranceNotSachirWizard_StartNavigationTemplateContainerID_StartNextButton");

    // לוקייטורים - שלב 2
    private By disabilityNoRadio = By.id("ctl00_ctl43_g_642b1586_5c41_436a_a04c_e3b5ba94ba69_ctl00_InsuranceNotSachirWizard_rdb_GetNechut_1_lbl");
    private By nextButtonStep2 = By.id("ctl00_ctl43_g_642b1586_5c41_436a_a04c_e3b5ba94ba69_ctl00_InsuranceNotSachirWizard_StepNavigationTemplateContainerID_StepNextButton");

    // סיום
    private By finishHeader = By.id("header"); // וודאי שזה ה-ID של כותרת הסיכום
    private By totalAmountRow = By.cssSelector("#ctl00_ctl43_g_642b1586_5c41_436a_a04c_e3b5ba94ba69_ctl00_InsuranceNotSachirWizard_div_Result ul li:last-child");

    public String getHeaderText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(pageHeader)).getText();
    }

    // שלב 1: בחירת מגדר, סטטוס ותאריך
    public void fillStepOne(String birthDate) {
        safeClick(genderMale);
        safeClick(yeshivaStudentRadio);

        WebElement dateField = wait.until(ExpectedConditions.visibilityOfElementLocated(birthDateInput));
        dateField.clear();
        dateField.sendKeys(birthDate);

        safeClick(nextButtonStep1);
    }

    // שלב 2: קצבת נכות והמשך
    public void fillStepTwo() {
        safeClick(disabilityNoRadio);
        safeClick(nextButtonStep2);
    }

    public boolean isStepTwoDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOf(stepTwoIndicator)).isDisplayed();
    }

    // בדיקה אם הגענו לסיום
    public boolean isFinished() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(finishHeader)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getTotalInsuranceAmount() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(totalAmountRow)).getText();
    }
    public String generateRandomBirthDate() {
        Calendar cal = Calendar.getInstance();

        // נלך 25 שנה אחורה מהיום (זה בטווח שבין 18 ל-70)
        cal.add(Calendar.YEAR, -25);

        // עיצוב התאריך לפורמט שהאתר מקבל: DD/MM/YYYY
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH) + 1; // חודשים ב-Java מתחילים מ-0
        int year = cal.get(Calendar.YEAR);

        return String.format("%02d/%02d/%d", day, month, year);
    }



    private void safeClick(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        // גלילה למרכז
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (Exception e) {
            // אם הלחיצה הרגילה נכשלה (נחסמה), נשתמש ב-JS
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }


}
