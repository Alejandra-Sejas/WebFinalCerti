package com.upb.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutCompletePage extends BasePage {

    private final By title = By.className("title");
    private final By completeHeader = By.className("complete-header");

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return "Checkout: Complete!".equalsIgnoreCase(text(title));
    }

    public String getConfirmationMessage() {
        return text(completeHeader);
    }
}
