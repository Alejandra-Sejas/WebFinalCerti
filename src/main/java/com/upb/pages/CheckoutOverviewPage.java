package com.upb.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutOverviewPage extends BasePage {

    private final By title = By.className("title");
    private final By finishButton = By.id("finish");

    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return "Checkout: Overview".equalsIgnoreCase(text(title));
    }

    public CheckoutCompletePage finish() {
        click(finishButton);
        return new CheckoutCompletePage(driver);
    }
}
