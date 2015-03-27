package com.dharmab.tyro.client.inject;

import com.dharmab.tyro.client.presenters.activities.HomeActivity;

/**
 * Factory enabling assisted injection of Activities.
 *
 * @see com.dharmab.tyro.client.inject.GinModule#bindPresenters(com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder)
 * @see <a href="https://github.com/google/guice/wiki/AssistedInject">Guice wiki page on Assisted Injection</a>
 */
public interface ActivityFactory {
    public HomeActivity create();
}
