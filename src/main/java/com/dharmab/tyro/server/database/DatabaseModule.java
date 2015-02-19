package com.dharmab.tyro.server.database;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import javax.sql.DataSource;

public class DatabaseModule extends AbstractModule {

    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    DataSource provideDataSource() {
        return new HikariDataSource(new HikariConfig("/opt/tyro/properties/hikari.properties"));
    }

    @Provides
    Flyway provideFlyway(DataSource dataSource) {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        return flyway;
    }

    @Provides
    @Singleton
    SessionFactory provideSessionFactory(DataSource dataSource) {
        Configuration hibernateConfiguration = new Configuration().configure();
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(hibernateConfiguration.getProperties())
                .applySetting(Environment.DATASOURCE, dataSource)
                .build();
        return hibernateConfiguration.buildSessionFactory(serviceRegistry);
    }
}
