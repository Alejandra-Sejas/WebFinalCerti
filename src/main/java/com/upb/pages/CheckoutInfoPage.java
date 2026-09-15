package com.upb.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutInfoPage extends BasePage {

    private final By firstName = By.id("first-name");
    private final By lastName = By.id("last-name");
    private final By postalCode = By.id("postal-code");
    private final By continueButton = By.id("continue");

    public CheckoutInfoPage(WebDriver driver) {
        super(driver);
    }

    public CheckoutOverviewPage completeInformation(String name, String surname, String zip) {
        type(firstName, name);
        type(lastName, surname);
        type(postalCode, zip);
        click(continueButton);
        return new CheckoutOverviewPage(driver);
    }
}
