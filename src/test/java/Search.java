import PageObject.BranchesAndServiceChannelsPage;
import PageObject.BtlBasePage;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Duration;


public class Search extends BaseTest{

@Test
public void searchCalc ()throws Throwable
    {

        try {
            String expectedTitle = "תוצאות חיפוש עבור חישוב סכום דמי לידה ליום";
            test.info("פותח את דף הבית ומתחיל חיפוש");

            BtlBasePage btlBasePage = new BtlBasePage(driver);
            btlBasePage.search("חישוב סכום דמי לידה ליום");
            test.info("בוצע חיפוש");

            // המתנה ש-h2 תחת #results יקבל את הטקסט הדרוש
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.textToBePresentInElementLocated(
                            By.cssSelector("#results h2"), expectedTitle
                    ));
            test.info("בוצעה המתנה לתוצאת החיפוש");

            String actualTitle = driver.findElement(By.cssSelector("#results h2")).getText().trim();

            Assertions.assertEquals(expectedTitle, actualTitle, "כותרת עמוד התוצאות אינה תואמת את הציפייה");
            test.pass("הבדיקה עברה בהצלחה");

        } catch (Throwable t) {
            captureFailure("searchCalc", t);
            throw t; // חשוב! כדי ש-JUnit יסמן את הטסט כנכשל
        }

    }


    @Test
    public void Branches() throws Exception{
        try {
            test = extent.createTest("בדיקת דף סניפים", "בדיקת שדות וניווט בדף הסניפים");

            BtlBasePage btlBasePage = new BtlBasePage(driver);
            test.info("מעבר לדף הסניפים");
            BranchesAndServiceChannelsPage branchesPage = btlBasePage.BankBranches();

            String expectedUrl = "https://www.btl.gov.il/snifim/Pages/default.aspx";
            test.info("המתנה ל-URL: " + expectedUrl);

            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.urlToBe(expectedUrl));
            test.pass("הגעתי ל-URL בהצלחה");

            String actualUrl = driver.getCurrentUrl();
            Assertions.assertEquals(expectedUrl, actualUrl, "ה-URL של דף הסניפים אינו תקין");
            test.pass("כתובת דף הסניפים תקינה: " + actualUrl);

            WebElement snifName = driver.findElement(By.className("SnifName"));
            snifName.click();
            test.info("בוצע קליק על סניף");

            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_PlaceHolderMain_SnifTitle")));
            test.pass("כותרת הסניף נטענה");

            Assertions.assertTrue(driver.findElements(By.xpath("//*[contains(text(),'כתובת')]")).size() > 0,
                    "שדה 'כתובת' לא נמצא");
            test.pass("נמצא שדה 'כתובת'");

            Assertions.assertTrue(driver.findElements(By.xpath("//*[contains(text(),'קבלת קהל')]")).size() > 0,
                    "שדה 'קבלת קהל' לא נמצא");
            test.pass("נמצא שדה 'קבלת קהל'");

            Assertions.assertTrue(driver.findElements(By.xpath("//*[contains(text(),'מענה טלפוני')]")).size() > 0,
                    "שדה 'מענה טלפוני' לא נמצא");
            test.pass("נמצא שדה 'מענה טלפוני'");



        } catch (Throwable t) {
            captureFailure("Branches", t);
            throw t; // חשוב! כדי ש-JUnit יסמן את הטסט כנכשל
        }
    }



    private void captureFailure(String testName, Throwable t) {
        try {
            String fileName = testName + "_" + System.currentTimeMillis() + ".png";
            String path = "./target/screenShot/" + fileName;

            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destination = new File(path);
            FileHandler.createDir(new File("./target/screenShot"));
            FileHandler.copy(screenshot, destination);

            test.fail("הטסט נכשל: " + t.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath(path).build());
        } catch (IOException e) {
            test.fail("נכשל בצילום מסך: " + e.getMessage());
        }
    }
}

