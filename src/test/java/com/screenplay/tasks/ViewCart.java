package com.screenplay.tasks;

import java.time.Duration;

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

public class ViewCart implements Task {

    @Step(UiLabels.STEP_VIEW_CART)
    @Override
    public <T extends Actor> void performAs(T actor) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TestConstants.ELEMENT_WAIT_SECS));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector(ProductPageTargets.CART_MODAL_SHOWN_CSS)));
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector(ProductPageTargets.VIEW_CART_IN_MODAL_CSS)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
    }

    public static ViewCart now() {
        return instrumented(ViewCart.class);
    }
}
