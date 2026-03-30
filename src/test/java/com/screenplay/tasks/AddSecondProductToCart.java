package com.screenplay.tasks;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.screenplay.config.TestConstants;
import com.screenplay.config.UiLabels;
import com.screenplay.ui.ProductPageTargets;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;

public class AddSecondProductToCart implements Task {

    private static final String VISIBLE_ADD_TO_CART_BUTTONS_CSS =
        ".features_items .productinfo.text-center a.add-to-cart";

    @Step(UiLabels.STEP_ADD_SECOND_PRODUCT)
    @Override
    public <T extends Actor> void performAs(T actor) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
    List<WebElement> addToCartButtons = driver.findElements(
        By.cssSelector(VISIBLE_ADD_TO_CART_BUTTONS_CSS)
        );
    WebElement secondButton = addToCartButtons.get(TestConstants.SECOND_PRODUCT_INDEX);

    ((JavascriptExecutor) driver).executeScript(
        "arguments[0].scrollIntoView({block:'center'}); arguments[0].click();",
        secondButton
    );
    new WebDriverWait(driver, Duration.ofSeconds(TestConstants.ELEMENT_WAIT_SECS))
        .until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector(ProductPageTargets.CART_MODAL_SHOWN_CSS)));
    }

    public static AddSecondProductToCart now() {
        return instrumented(AddSecondProductToCart.class);
    }
}
