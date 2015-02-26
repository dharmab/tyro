package com.dharmab.tyro.client;

import com.dharmab.tyro.client.inject.DependencyInjector;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class Tyro implements EntryPoint {
    public void onModuleLoad() {
        DependencyInjector injector = GWT.create(DependencyInjector.class);
        PlaceHistoryHandler placeHistoryHandler = injector.getPlaceHistoryHandler();
        ActivityManager activityManager = injector.getActivityManager();

        SimplePanel panel = new SimplePanel();
        activityManager.setDisplay(panel);
        panel.add(new Label("Hello"));
        RootPanel.get().add(panel);

        placeHistoryHandler.handleCurrentHistory();
    }
}
