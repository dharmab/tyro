package com.dharmab.tyro.server;

import com.dharmab.tyro.server.database.DatabaseModule;
import com.dharmab.tyro.server.database.DatabaseSessionFilter;
import com.dharmab.tyro.server.requestfactory.InjectableRequestFactoryModule;
import com.dharmab.tyro.server.requestfactory.InjectableRequestFactoryServlet;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.hibernate.SessionFactory;

import javax.servlet.ServletContextEvent;

/**
 * Listens for context initialization and destruction. Manages application-wide resources.
 */
public class LifecycleManager extends GuiceServletContextListener {
    private final Injector injector;

    public LifecycleManager() {
        injector = createInjector();
    }

    private Injector createInjector() {
        return Guice.createInjector(
                new DatabaseModule(),
                new ServletModule() {
                    @Override
                    protected void configureServlets() {
                        // Hibernate thread-scoped sessions
                        filter("/*").through(DatabaseSessionFilter.class);

                        // RequestFactory servlet mapping
                        install(new InjectableRequestFactoryModule());
                        serve("/gwtRequest").with(InjectableRequestFactoryServlet.class);

                        // RPC servlet mappings go here
                        // example:
                        //serve("/someurl").with(MyServiceImpl.class);
                    }
                });
    }

    @Override
    protected Injector getInjector() {
        return injector;
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        super.contextInitialized(servletContextEvent);

        // Run database migrations
        injector.getInstance(Flyway.class).migrate();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        // Close database connections
        injector.getInstance(SessionFactory.class).close();
        injector.getInstance(HikariDataSource.class).close();

        super.contextDestroyed(servletContextEvent);
    }
}
