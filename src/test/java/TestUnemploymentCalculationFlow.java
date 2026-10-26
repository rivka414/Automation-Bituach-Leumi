import PageObject.BtlBasePage;
import PageObject.InsuranceFeesPage;
import PageObject.UnemploymentPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestUnemploymentCalculationFlow extends BaseTest{
    @Test
    public void testUnemploymentCalculationFlow() {
        try {
            // יצירת דיווח בדו"ח
            test = extent.createTest("תסריט חישוב דמי אבטלה", "בדיקת מחשבון אבטלה כולל מילוי שכר ואימות תוצאות");

            BtlBasePage btlBasePage = new BtlBasePage(driver);
            test.info("ניווט לקצבאות והטבות -> אבטלה");
            UnemploymentPage unemploymentPage = btlBasePage.goToUnemployment();

            // 1. כניסה למחשבון (דרך קצבאות והטבות -> אבטלה)
            test.info("ניווט למחשבון דמי אבטלה");
            unemploymentPage.goToCalculator();

            // 2. יצירת תאריך - יומיים לפני התאריך הנוכחי (עומד בתנאי של "עד חודש אחורה")
            String formattedDate = LocalDate.now().minusDays(2).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            test.info("מילוי שלב ראשון: תאריך הפסקה " + formattedDate + " וגיל מעל 28");
            unemploymentPage.fillStepOne(formattedDate);
            test.pass("שלב ראשון הושלם בהצלחה");

            // 3. מילוי שכר בלולאה (ערכים בין 3000 ל-7000)
            test.info("מילוי סכומי השתכרות בצורה אוטומטית");
            unemploymentPage.fillSalariesAndFinish();
            test.pass("שלב מילוי השכר הושלם");

            // 4. בדיקת דף תוצאות
            test.info("אימות הגעה לדף תוצאות החישוב");
            Assertions.assertTrue(unemploymentPage.isResultsPageLoaded(), "דף תוצאות החישוב לא נטען!");

            // 5. אימות הופעת נתוני התוצאה (ללא בדיקת סכומים)
            test.info("אימות הופעת שדות: שכר יומי, דמי אבטלה ליום וחודש");
            Assertions.assertTrue(unemploymentPage.areAllResultsDisplayed(), "חלק מהנתונים (שכר יומי/דמי אבטלה) חסרים בדף התוצאות");

            test.pass("הטסט הסתיים בהצלחה - כל הנתונים מופיעים כנדרש");

        } catch (Throwable t) {
            // דיווח על כישלון כולל זריקת השגיאה
            test.fail("הטסט נכשל: " + t.getMessage());
            throw t;
        }
    }

}
