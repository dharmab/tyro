package com.dharmab.tyro.client.inject;

import com.dharmab.tyro.client.requestfactory.AppRequestFactory;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.web.bindery.event.shared.EventBus;

@GinModules(GinModule.class)
public interface DependencyInjector extends Ginjector {
    AppRequestFactory getRequestFactory();

    EventBus getEventBus();

    PlaceController getPlaceController();

    PlaceHistoryHandler getPlaceHistoryHandler();

    ActivityManager getActivityManager();
}
