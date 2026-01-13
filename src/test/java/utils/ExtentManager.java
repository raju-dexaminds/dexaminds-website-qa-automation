package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtentManager {

    private static ExtentReports extent;
    public static String reportDir;

    public static ExtentReports getExtent() {
        if (extent == null) {

            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));

            reportDir = "target/extent-reports/Run_" + timestamp;

            new File(reportDir + "/screenshots").mkdirs();

            ExtentSparkReporter spark =
                    new ExtentSparkReporter(reportDir + "/ExtentReport.html");

            extent = new ExtentReports();
            extent.attachReporter(spark);
        }
        return extent;
    }
}

