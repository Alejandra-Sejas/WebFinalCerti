package com.upb.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage extends BasePage {

    private final By title = By.className("title");

    // Solo nombres que están realmente dentro de elementos del carrito
    private final By itemNames =
            By.cssSelector(".cart_item .inventory_item_name");

    private final By cartItems =
            By.className("cart_item");

    private final By checkoutButton =
            By.id("checkout");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return "Your Cart".equalsIgnoreCase(text(title));
    }

    public List<String> getProductNames() {

        return driver.findElements(itemNames)
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public boolean containsProduct(String productName) {

        return getProductNames()
                .contains(productName);
    }

    public int getNumberOfProducts() {

        return driver.findElements(cartItems).size();
    }

    public boolean isEmpty() {

        return driver.findElements(cartItems).isEmpty();
    }

    public void removeProduct(String productName) {

        click(By.id(
                "remove-" + slug(productName)
        ));
    }

    public CheckoutInfoPage checkout() {

        click(checkoutButton);

        return new CheckoutInfoPage(driver);
    }

    private String slug(String productName) {

        return productName
                .toLowerCase()
                .replace("&", "")
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-|-$", "");
    }
}