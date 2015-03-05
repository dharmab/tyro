package com.dharmab.tyro.client.inject;

import com.dharmab.tyro.client.places.AppPlaceHistoryMapper;
import com.dharmab.tyro.client.places.HomePlace;
import com.dharmab.tyro.client.presenters.HomePresenter;
import com.dharmab.tyro.client.presenters.activities.ActivityMapperImpl;
import com.dharmab.tyro.client.presenters.activities.HomeActivity;
import com.dharmab.tyro.client.requestfactory.AppRequestFactory;
import com.dharmab.tyro.client.views.HomeView;
import com.dharmab.tyro.client.views.uibinder.HomeViewImpl;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.validation.client.impl.Validation;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

import javax.validation.Validator;

public class GinModule extends AbstractGinModule {

    /**
     * Bind each view to its implementation here.
     * Binding a view as an eager singleton will create each view when the user first loads the page, which improves
     * responsiveness as the application switches views.
     * <p/>
     * Example: <pre>bind(MyView.class).to(MyViewImpl.class).asEagerSingleton();</pre>
     */
    private void bindViews() {
        bind(HomeView.class).to(HomeViewImpl.class).asEagerSingleton();
    }

    /**
     * Bind each presenter to its implementation.
     * Presenter constructors may requires a mix of automatically injected and non-automatically injected
     * dependencies. For example, a constructor might require a component of the current browser URI to determine the
     * ID of a resource to display. Assisted injection is used to satisfy this requirement.
     * <p/>
     * Example which does not require assisted injection:
     * <p/>
     * Write the constructor as normal:
     * <pre>
     * <code>public class MyActivity implements MyPresenter {
     *     private MyView view;
     *
     *     {@literal @}Inject
     *     MyActivity(MyView view) {
     *         this.view = view;
     *     }
     * }</code>
     * </pre>
     * <p/>
     * Add a create() factory method to {@link ActivityFactory}:
     * <pre>
     * <code>public interface ActivityFactory {
     *     public MyActivity create();
     * }</code>
     * </pre>
     * <p/>
     * Example which requires assisted injection:
     * <p/>
     * Annotate the constructor parameters with {@link com.google.inject.assistedinject.Assisted} to indicate assisted
     * dependencies. In this example, the place object contains a required browser URL token.
     * <pre>
     * <code>public class MyActivity implements MyPresenter {
     *     private MyView view;
     *     private MyPlace place;
     *
     *     {@literal @}Inject
     *     MyActivity({@literal @}Assisted MyPlace place, MyView view) {
     *         this.view = view;
     *         this.place = place;
     *     }
     * }</code></pre>
     *
     * Add a create() factory method to {@link ActivityFactory} with the
     * assisted parameters:
     * <pre>
     * <code>public interface ActivityFactory {
     *     public MyActivity create(MyPlace place);
     * }</code>
     * </pre>
     * <p/>
     * For either case, the presenter can now be found to the factory builder within this method:
     * <pre><code>builder.implement(MyPresenter.class, MyActivity.class);</code></pre>
     *
     * @param builder FactoryModuleBuilder which will build the factory to provide assisted injection.
     * @see ActivityFactory
     * @see <a href="https://github.com/google/guice/wiki/AssistedInject">Guice wiki page on Assisted Injection</a>
     */
    private void bindPresenters(GinFactoryModuleBuilder builder) {
        builder.implement(HomePresenter.class, HomeActivity.class);
    }

    @Override
    protected void configure() {
        bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
        bind(ActivityMapper.class).to(ActivityMapperImpl.class).in(Singleton.class);

        bindViews();
        GinFactoryModuleBuilder presenterFactoryBuilder = new GinFactoryModuleBuilder();
        bindPresenters(presenterFactoryBuilder);
        install(presenterFactoryBuilder.build(ActivityFactory.class));
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
