import PageObject.BtlBasePage;
import PageObject.InsuranceCalculatorPage;
import PageObject.InsuranceFeesPage;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Dmi_Bituac extends BaseTest {

    @Test
    public void Dmi_Bituac_for_yeshivah_student() {
        try {
            test = extent.createTest("חישוב דמי ביטוח לבחור ישיבה", "בדיקת תהליך המחשבון ואימות סכומי התשלום");

            BtlBasePage btlBasePage = new BtlBasePage(driver);
            test.info("ניווט לתפריט דמי ביטוח -> דמי ביטוח לאומי");
            InsuranceFeesPage insuranceFeesPage = btlBasePage.goToInsuranceFees();

            // בדיקת כותרת
            Assertions.assertTrue(insuranceFeesPage.getHeaderText().contains("דמי ביטוח לאומי"));
            test.pass("הגעתי לדף דמי ביטוח לאומי");

            test.info("לחיצה על לינק מחשבון");
            InsuranceCalculatorPage calculatorPage = insuranceFeesPage.clickCalculatorLink();


            String expectedTitle = "חישוב דמי ביטוח עבור עצמאי, תלמיד, שוהה בחוץ לארץ ומי שלא עובד";
            String actualTitle = calculatorPage.getHeaderText();
            Assertions.assertEquals(expectedTitle, actualTitle, "כותרת המחשבון אינה תואמת לציפיות!");

            test.info("ביצוע צעד ראשון: בחירת סטטוס והזנת תאריך");
            String birthDate = calculatorPage.generateRandomBirthDate();
            calculatorPage.fillStepOne(birthDate); // קריאה לפונקציה הנכונה
            test.pass("צעד ראשון הושלם");
            Assertions.assertTrue(calculatorPage.isStepTwoDisplayed(), "שגיאה: לא הגענו לצעד שני!");
            // שלב 4: צעד שני
            test.info("ביצוע צעד שני: בחירת 'לא' בקצבת נכות");
            calculatorPage.fillStepTwo();
            test.pass("צעד שני הושלם");
            Assertions.assertTrue(calculatorPage.isFinished(), "שגיאה: לא הגענו לשלב הסיכום!");
            // שלב 5: אימות סכום
            String totalAmount = calculatorPage.getTotalInsuranceAmount();
            Assertions.assertTrue(totalAmount.contains("163"), "הסכום שנמצא: " + totalAmount);
            test.pass("החישוב הסתיים בהצלחה: " + totalAmount);

        } catch (Throwable t) {
            throw t;
        }

    }
}

