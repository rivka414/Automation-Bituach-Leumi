package Report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtentReportManager {

    private static ExtentReports extent;
    private static final String REPORT_DIR = "./target/test-output";

    public static ExtentReports getExtent() {
        if (extent == null) {
            initReport();
        }
        return extent;
    }

    private static void initReport() {
        File reportsDir = new File(REPORT_DIR);
        if (!reportsDir.exists()) reportsDir.mkdirs();

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String reportPath = REPORT_DIR + "/ExtentReport_" + timestamp + ".html";
        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        extent = new ExtentReports();
        extent.attachReporter(spark);
        System.out.println("Extent report will be written to: " + new File(reportPath).getAbsolutePath());
    }

    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}
