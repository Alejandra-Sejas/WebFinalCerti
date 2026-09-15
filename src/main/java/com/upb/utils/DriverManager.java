package com.upb.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public final class DriverManager {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverManager() {
    }

    public static void createDriver() {

        ChromeOptions options = new ChromeOptions();

        options.addArguments("--disable-notifications");
        options.addArguments("--disable-search-engine-choice-screen");

        // Evita avisos y ventanas del gestor de contraseñas de Chrome
        options.addArguments("--disable-features=PasswordLeakDetection");
        options.addArguments("--disable-features=PasswordManagerOnboarding");

        Map<String, Object> prefs = new HashMap<>();

        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);

        options.setExperimentalOption("prefs", prefs);

        boolean headless =
                Boolean.parseBoolean(System.getProperty("headless", "false"));

        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        }

        WebDriver driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        if (!headless) {
            driver.manage().window().maximize();
        }

        DRIVER.set(driver);
    }

    public static WebDriver getDriver() {

        WebDriver driver = DRIVER.get();

        if (driver == null) {
            throw new IllegalStateException(
                    "El WebDriver todavía no fue inicializado por Hooks."
            );
        }

        return driver;
    }

    public static void quitDriver() {

        WebDriver driver = DRIVER.get();

        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }
}