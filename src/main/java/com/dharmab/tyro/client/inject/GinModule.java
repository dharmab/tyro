package com.dharmab.tyro.client.inject;

import com.dharmab.tyro.client.ActivityMapperImpl;
import com.dharmab.tyro.client.places.AppPlaceHistoryMapper;
import com.dharmab.tyro.client.places.HomePlace;
import com.dharmab.tyro.client.requestfactory.AppRequestFactory;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.validation.client.impl.Validation;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

import javax.validation.Validator;

public class GinModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(EventBus.class).to(SimpleEventBus.class).asEagerSingleton();

        bind(ActivityMapper.class).to(ActivityMapperImpl.class).asEagerSingleton();

        // Views (singletons due to expense of construction)
        // bind(MyView.class).to(MyViewImpl.class).asEagerSingleton();

        // Factories for presenters
        // install(new GinFactoryModuleBuilder()
        //         .implement(MyPresenter.class, MyActivity.class)
        //         .build(PresenterFactory.class));
    }

    @Provides
    @Singleton
    PlaceController providePlaceController(EventBus eventBus) {
        return new PlaceController(eventBus);
    }

    @Provides
    @Singleton
    PlaceHistoryHandler providePlaceHistoryHandler(AppPlaceHistoryMapper placeHistoryMapper, PlaceController placeController, EventBus eventBus) {
        PlaceHistoryHandler placeHistoryHandler = new PlaceHistoryHandler(placeHistoryMapper);
        placeHistoryHandler.register(placeController, eventBus, new HomePlace());
        return placeHistoryHandler;
    }

    @Provides
    @Singleton
    ActivityManager provideActivityManager(ActivityMapper activityMapper, EventBus eventBus) {
        return new ActivityManager(activityMapper, eventBus);
    }

    @Provides
    @Singleton
    AppRequestFactory provideRequestFactory(EventBus eventBus) {
        AppRequestFactory requestFactory = GWT.create(AppRequestFactory.class);
        requestFactory.initialize(eventBus);
        return requestFactory;
    }

    @Provides
    Validator provideValidator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }
}
