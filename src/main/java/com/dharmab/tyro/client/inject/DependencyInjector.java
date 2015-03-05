package com.dharmab.tyro.client.inject;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.place.shared.PlaceHistoryHandler;

@GinModules(GinModule.class)
public interface DependencyInjector extends Ginjector {
    PlaceHistoryHandler getPlaceHistoryHandler();

    ActivityManager getActivityManager();
}
