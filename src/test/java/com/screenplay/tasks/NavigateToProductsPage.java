package com.screenplay.tasks;

import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.screenplay.config.TestConstants;
import com.screenplay.config.UiLabels;
import com.screenplay.ui.ProductPageTargets;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import net.serenitybdd.screenplay.waits.WaitUntil;

public class NavigateToProductsPage implements Task {

    private final String baseUrl;

    public NavigateToProductsPage() {
        this.baseUrl = Optional.ofNullable(System.getenv(TestConstants.ENV_BASE_URL))
                               .orElse(TestConstants.FALLBACK_BASE_URL);
    }

    @Step(UiLabels.STEP_NAVIGATE_PRODUCTS)
    @Override
    public <T extends Actor> void performAs(T actor) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        List<WebElement> productsNav = driver.findElements(By.cssSelector("a[href='/products']"));

        if (!productsNav.isEmpty() && productsNav.get(0).isDisplayed()) {
            productsNav.get(0).click();
        } else {
            actor.attemptsTo(Open.url(baseUrl + TestConstants.PRODUCTS_PATH));
        }

        actor.attemptsTo(
                WaitUntil.the(ProductPageTargets.PRODUCTS_PAGE_TITLE, isVisible())
                         .forNoMoreThan(TestConstants.ELEMENT_WAIT_SECS).seconds()
        );
    }

    public static NavigateToProductsPage now() {
        return instrumented(NavigateToProductsPage.class);
    }
}
