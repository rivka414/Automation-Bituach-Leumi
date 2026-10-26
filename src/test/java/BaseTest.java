import Report.ExtentReportManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.net.NetworkInterface;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BaseTest {

    WebDriver driver;
    protected static ExtentReports extent;
    protected ExtentTest test;

    protected static ExtentSparkReporter reporter;
    @BeforeAll
    public static void startTests(){

        String reportDirPath = "target/test-output";

        File reportsDir = new File(reportDirPath);
        if (!reportsDir.exists()) {
            boolean created = reportsDir.mkdirs();
            if (!created) {
                throw new RuntimeException("לא הייתה אפשרות ליצור את תיקיית הדוחות: " + reportDirPath);
            }}

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String reportFilePath = reportDirPath + "/ExtentReport_" + timestamp + ".html";

        reporter = new ExtentSparkReporter(reportFilePath);
        extent = new ExtentReports();
        extent.attachReporter(reporter);

    }

    @AfterAll
    public static void endTests(){

        System.out.println("end run tests");
        if (extent != null) {
            extent.flush();
        }
    }


    @BeforeEach
    public void loadDriver(){

          test = extent.createTest("Example", " ב לשימוש דוגמהExtent Report ");

        test.info("webType = " +System.getProperty("WebType"));
        String webType = System.getProperty("WebType","chrome") ;
        if(webType  .equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();

        }else if(webType.equals("edge")){

            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        }

        driver.manage().window().maximize();
        driver.get("https://www.btl.gov.il/Pages/default.aspx");
    }


    @AfterEach
    public void closeDriver(){driver.quit();}



}
