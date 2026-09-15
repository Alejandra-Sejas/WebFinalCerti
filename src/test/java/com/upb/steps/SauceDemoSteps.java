package com.upb.steps;

import com.upb.pages.CartPage;
import com.upb.pages.CheckoutCompletePage;
import com.upb.pages.CheckoutInfoPage;
import com.upb.pages.CheckoutOverviewPage;
import com.upb.pages.InventoryPage;
import com.upb.pages.LoginPage;
import com.upb.utils.DriverManager;
import com.upb.utils.ExtentReportManager;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SauceDemoSteps {

    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private CheckoutInfoPage checkoutInfoPage;
    private CheckoutOverviewPage checkoutOverviewPage;
    private CheckoutCompletePage checkoutCompletePage;
    private List<Double> currentPrices;

    @Given("que el usuario ingresa a SauceDemo")
    public void openSauceDemo() {
        loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.open();
        ExtentReportManager.getTest().info("Se abrió https://www.saucedemo.com/");
    }

    @Given("inicia sesión con usuario {string} y contraseña {string}")
    public void login(String username, String password) {
        inventoryPage = loginPage.loginAs(username, password);
        assertTrue(inventoryPage.isLoaded(), "No se cargó la página Products después del login");
        ExtentReportManager.getTest().pass("Login correcto y página Products visible");
    }

    @When("agrega el producto {string} al carrito")
    public void addOneProduct(String productName) {
        inventoryPage.addProduct(productName);
        ExtentReportManager.getTest().info("Producto agregado: " + productName);
    }

    @Then("el contador del carrito debe mostrar {int}")
    public void validateCartBadge(int expectedCount) {
        assertEquals(expectedCount, inventoryPage.getCartCount(),
                "El contador del carrito no coincide");
        ExtentReportManager.getTest().pass("Contador del carrito validado: " + expectedCount);
    }

    @Then("el producto {string} debe estar dentro del carrito")
    public void validateProductInCart(String productName) {
        cartPage = inventoryPage.openCart();
        assertTrue(cartPage.isLoaded(), "No se abrió la página del carrito");
        assertTrue(cartPage.containsProduct(productName),
                "El producto esperado no está en el carrito: " + productName);
        ExtentReportManager.getTest().pass("Producto validado en carrito: " + productName);
    }

    @When("agrega los siguientes productos al carrito:")
    public void addProductsWithDataTable(DataTable table) {
        List<String> products = table.asList(String.class);
        for (String product : products) {
            inventoryPage.addProduct(product);
        }
        ExtentReportManager.getTest().info("DataTable procesada con " + products.size() + " productos");
    }

    @Then("el carrito debe contener exactamente los siguientes productos:")
    public void validateProductsWithDataTable(DataTable table) {
        List<String> expectedProducts = table.asList(String.class);
        cartPage = inventoryPage.openCart();
        List<String> actualProducts = cartPage.getProductNames();

        assertEquals(expectedProducts.size(), actualProducts.size(),
                "La cantidad de productos del carrito no coincide");
        assertTrue(actualProducts.containsAll(expectedProducts),
                "El carrito no contiene todos los productos esperados");
        ExtentReportManager.getTest().pass("DataTable validada contra los productos del carrito");
    }

    @When("ordena los productos por {string}")
    public void sortProducts(String sortValue) {
        inventoryPage.sortBy(sortValue);
        currentPrices = inventoryPage.getPrices();
        ExtentReportManager.getTest().info("Orden aplicado: " + sortValue);
    }

    @Then("los precios deben quedar ordenados en forma {string}")
    public void validatePriceOrder(String direction) {
        List<Double> expected = new ArrayList<>(currentPrices);
        if (direction.equalsIgnoreCase("ascendente")) {
            expected.sort(Comparator.naturalOrder());
        } else if (direction.equalsIgnoreCase("descendente")) {
            expected.sort(Comparator.reverseOrder());
        } else {
            throw new IllegalArgumentException("Dirección no soportada: " + direction);
        }

        assertEquals(expected, currentPrices, "Los precios no están correctamente ordenados");
        ExtentReportManager.getTest().pass("Orden de precios validado: " + direction);
    }

    @When("agrega {string} y abre el carrito")
    public void addAndOpenCart(String productName) {
        inventoryPage.addProduct(productName);
        cartPage = inventoryPage.openCart();
        assertTrue(cartPage.containsProduct(productName), "El producto no llegó al carrito");
    }

    @When("elimina {string} desde el carrito")
    public void removeFromCart(String productName) {
        cartPage.removeProduct(productName);
        ExtentReportManager.getTest().info("Producto eliminado desde el carrito: " + productName);
    }

    @Then("el carrito debe quedar vacío")
    public void validateEmptyCart() {
        assertEquals(0, cartPage.getNumberOfProducts(), "El carrito todavía contiene productos");
        assertFalse(cartPage.containsProduct("Sauce Labs Bike Light"),
                "El producto eliminado todavía aparece en el carrito");
        ExtentReportManager.getTest().pass("Carrito vacío validado");
    }

    @When("agrega {string} y realiza el checkout con los datos:")
    public void checkoutWithDataTable(String productName, DataTable table) {
        var data = table.asMap(String.class, String.class);

        inventoryPage.addProduct(productName);
        cartPage = inventoryPage.openCart();
        checkoutInfoPage = cartPage.checkout();
        checkoutOverviewPage = checkoutInfoPage.completeInformation(
                data.get("nombre"),
                data.get("apellido"),
                data.get("codigoPostal")
        );

        assertTrue(checkoutOverviewPage.isLoaded(), "No se cargó Checkout: Overview");
        checkoutCompletePage = checkoutOverviewPage.finish();
        ExtentReportManager.getTest().info("Checkout ejecutado con datos provenientes de DataTable");
    }

    @Then("debe mostrarse el mensaje de compra {string}")
    public void validateCheckoutMessage(String expectedMessage) {
        assertTrue(checkoutCompletePage.isLoaded(), "No se cargó Checkout: Complete!");
        assertEquals(expectedMessage, checkoutCompletePage.getConfirmationMessage(),
                "El mensaje final de compra no coincide");
        ExtentReportManager.getTest().pass("Compra completada y mensaje final validado");
    }
}
