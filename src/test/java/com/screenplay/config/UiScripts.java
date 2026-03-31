package com.screenplay.config;

public final class UiScripts {

    public static final String SCROLL_INTO_VIEW_AND_CLICK
            = "arguments[0].scrollIntoView({block:'center'}); arguments[0].click();";

    private UiScripts() {
    }
}
