package com.upb.hooks;

import com.upb.utils.DriverManager;
import com.upb.utils.ExtentReportManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {

    @Before(order = 0)
    public void setUp(Scenario scenario) {
        DriverManager.createDriver();
        ExtentReportManager.startTest(scenario.getName());
        ExtentReportManager.getTest().info("Inicio del escenario Cucumber");
    }

    @After(order = 1)
    public void captureEvidenceAndReport(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver())
                        .getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Evidencia del fallo");
                ExtentReportManager.fail("Escenario fallido: " + scenario.getName());
            } else {
                ExtentReportManager.pass("Escenario aprobado: " + scenario.getName());
            }
        } finally {
            ExtentReportManager.flush();
        }
    }

    @After(order = 0)
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
