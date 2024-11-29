package example.micronaut;

import com.codahale.metrics.MetricRegistry;
import io.dropwizard.db.DataSourceFactory;
import io.micronaut.configuration.jdbc.hikari.DatasourceConfiguration;
import io.micronaut.context.annotation.*;
import io.micronaut.core.annotation.Order;
import io.micronaut.jdbc.BasicJdbcConfiguration;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Named;
import org.h2.Driver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.function.Supplier;

@MicronautTest
class MicronautguideTest {

    @Inject
    EmbeddedApplication<?> application;


    private static DataSourceFactory createDataSourceFactory() {
        DataSourceFactory factory = new DataSourceFactory();
        factory.setDriverClass(org.h2.Driver.class.getName());
        factory.setUrl("""
                jdbc:h2:mem:test;
                MODE=Oracle;
                INIT=CREATE ROLE IF NOT EXISTS TEST\\;CREATE SCHEMA IF NOT EXISTS TEST\\;SET SCHEMA TEST;" +
                TRACE_LEVEL_SYSTEM_OUT=1;" +
                TRACE_LEVEL_FILE=3
        """);
        factory.setUser("sa");
        factory.setPassword("");
        factory.setValidationQuery("select 1");
        factory.setLogValidationErrors(true);
        return factory;
    }

    @Bean
    @Named("default") @Primary
    @Order(-1000)
    public static DataSource testDataSource() {
        return createDataSourceFactory().build(new MetricRegistry(), "h2");
    }

    @Test
    void testItWorks() {
        Assertions.assertTrue(application.isRunning());
    }

}
