package com.screenplay.tasks;

import java.util.Optional;

import com.screenplay.config.TestConstants;
import com.screenplay.config.UiLabels;
import com.screenplay.ui.ProductPageTargets;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import net.serenitybdd.screenplay.actions.Open;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import net.serenitybdd.screenplay.waits.WaitUntil;

public class NavigateToHomePage implements Task {

    private final String baseUrl;

    public NavigateToHomePage() {
        this.baseUrl = Optional.ofNullable(System.getenv(TestConstants.ENV_BASE_URL))
                               .orElse(TestConstants.FALLBACK_BASE_URL);
    }

    @Step(UiLabels.STEP_NAVIGATE_HOME)
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Open.url(baseUrl),
                WaitUntil.the(ProductPageTargets.HOME_PAGE_LOGO, isVisible())
                         .forNoMoreThan(TestConstants.ELEMENT_WAIT_SECS).seconds()
        );
    }

    public static NavigateToHomePage now() {
        return instrumented(NavigateToHomePage.class);
    }
}
