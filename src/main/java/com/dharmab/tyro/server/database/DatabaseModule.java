package com.dharmab.tyro.server.database;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import javax.sql.DataSource;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseModule extends AbstractModule {

    private final Logger logger = Logger.getLogger("com.dharmab.tyro.server.database.DatabaseModule");

    @Override
    protected void configure() {
        bind(String.class)
                .annotatedWith(Names.named("HikariCP Properties Filename"))
                .toInstance("/opt/tyro/properties/hikari.properties");
        bind(DataSource.class).to(HikariDataSource.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    HikariDataSource provideHikariDataSource(@Named("HikariCP Properties Filename") String filename) {
        File propertiesFile = new File(filename);
        HikariConfig config;
        if (propertiesFile.exists()) {
            config = new HikariConfig(filename);
        } else {
            logger.log(Level.SEVERE, "Couldn't find " + filename + " - falling back on development mode defaults!");
            config = new HikariConfig();
            config.setJdbcUrl("jdbc:postgresql://10.17.71.10/tyro");
            config.setUsername("tyro");
            config.setPassword("tyro");
        }
        return new HikariDataSource(config);
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
