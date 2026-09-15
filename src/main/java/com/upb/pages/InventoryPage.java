package com.upb.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class InventoryPage extends BasePage {

    private final By title = By.className("title");
    private final By cartLink = By.className("shopping_cart_link");
    private final By cartBadge = By.className("shopping_cart_badge");
    private final By sortSelect = By.className("product_sort_container");
    private final By productPrices = By.className("inventory_item_price");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return "Products".equalsIgnoreCase(text(title));
    }

    public void addProduct(String productName) {
        click(By.id("add-to-cart-" + slug(productName)));
    }

    public void removeProduct(String productName) {
        click(By.id("remove-" + slug(productName)));
    }

    public int getCartCount() {
        if (!isPresent(cartBadge)) return 0;
        return Integer.parseInt(text(cartBadge));
    }

    public CartPage openCart() {
        click(cartLink);
        return new CartPage(driver);
    }

    public void sortBy(String value) {
        Select select = new Select(visible(sortSelect));
        select.selectByValue(value);
    }

    public List<Double> getPrices() {
        return all(productPrices).stream()
                .map(WebElement::getText)
                .map(price -> price.replace("$", ""))
                .map(Double::parseDouble)
                .toList();
    }

    private String slug(String productName) {
        return productName.toLowerCase()
                .replace("&", "")
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-|-$", "");
    }
}
