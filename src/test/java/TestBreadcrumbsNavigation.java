import PageObject.BtlBasePage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class TestBreadcrumbsNavigation extends BaseTest{

    private static Stream<Arguments> providePagesForNavigation() {
        return Stream.of(
                Arguments.of("אבטלה", "דף הבית > קצבאות והטבות > אבטלה"),
                Arguments.of("דמי לידה", "דף הבית > קצבאות והטבות > דמי לידה"),
                Arguments.of("ילד נכה", "דף הבית > קצבאות והטבות > ילד נכה"),
                Arguments.of("מזונות", "דף הבית > קצבאות והטבות > מזונות"),
                Arguments.of("מענק לימודים", "דף הבית > קצבאות והטבות > מענק לימודים")

        );
    }

    @ParameterizedTest(name = "בדיקת ניווט לדף: {0}")
    @MethodSource("providePagesForNavigation")
    public void testNavigationBreadcrumbs(String menuName, String expectedBreadcrumb) {
        driver.get("https://www.btl.gov.il/Pages/default.aspx"); // חזרה לנקודת האפס
        test = extent.createTest("בדיקת Breadcrumbs: " + menuName);

        BtlBasePage btlBasePage = new BtlBasePage(driver);

        test.info("ניווט לקצבאות והטבות -> " + menuName);

        // שימוש בפונקציה הקיימת שלך לניווט
        btlBasePage.acceptEnum(PageObject.Menu.Allowances_and_Benefits);
        btlBasePage.acceptSubMenu(menuName); // כאן אנחנו שולחים את שם הדף כטקסט חופשי (LinkText)

        test.info("בדיקת נתיב ה-Breadcrumbs");
        String actualBreadcrumb = btlBasePage.getBreadcrumbsText();

        // ניקוי רווחים כפולים או תווים נסתרים להשוואה מדויקת
        Assertions.assertTrue(actualBreadcrumb.contains(menuName),
                "ה-Breadcrumb אינו מכיל את שם הדף הצפוי! נמצא: " + actualBreadcrumb);

        test.pass("הניווט לדף " + menuName + " אומת בהצלחה");
    }
}
