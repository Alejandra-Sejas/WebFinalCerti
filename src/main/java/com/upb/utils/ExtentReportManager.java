package com.upb.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.nio.file.Files;
import java.nio.file.Path;

public final class ExtentReportManager {

    private static final ExtentReports EXTENT = createExtent();
    private static final ThreadLocal<ExtentTest> TEST = new ThreadLocal<>();

    private ExtentReportManager() {
    }

    private static ExtentReports createExtent() {
        try {
            Files.createDirectories(Path.of("test-output", "ExtentReport"));
        } catch (Exception e) {
            throw new RuntimeException("No se pudo crear la carpeta del reporte Extent", e);
        }

        ExtentSparkReporter htmlReporter =
                new ExtentSparkReporter("test-output/ExtentReport/ExtentReport.html");
        htmlReporter.config().setDocumentTitle("SauceDemo - Examen Final");
        htmlReporter.config().setReportName("Framework WEB - Selenium + JUnit + Cucumber");
        htmlReporter.config().setTheme(Theme.STANDARD);

        ExtentReports extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Proyecto", "WEB Final - SauceDemo");
        extent.setSystemInfo("Herramientas", "Selenium + JUnit 5 + Cucumber + POM");
        extent.setSystemInfo("Java", System.getProperty("java.version"));
        extent.setSystemInfo("Navegador", "Google Chrome");
        return extent;
    }

    public static synchronized void startTest(String scenarioName) {
        TEST.set(EXTENT.createTest(scenarioName));
    }

    public static ExtentTest getTest() {
        return TEST.get();
    }

    public static synchronized void pass(String message) {
        if (TEST.get() != null) TEST.get().pass(message);
    }

    public static synchronized void fail(String message) {
        if (TEST.get() != null) TEST.get().fail(message);
    }

    public static synchronized void flush() {
        EXTENT.flush();
        TEST.remove();
    }
}
