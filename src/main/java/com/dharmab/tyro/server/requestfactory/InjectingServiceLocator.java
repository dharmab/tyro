package com.dharmab.tyro.server.requestfactory;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.web.bindery.requestfactory.shared.ServiceLocator;

public class InjectingServiceLocator implements ServiceLocator {
    private final Injector injector;

    @Inject
    public InjectingServiceLocator(Injector injector) {
        this.injector = injector;
    }

    @Override
    public Object getInstance(Class<?> clazz) {
        return injector.getInstance(clazz);
    }
}
