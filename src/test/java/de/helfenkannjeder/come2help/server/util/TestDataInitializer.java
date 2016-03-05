package de.helfenkannjeder.come2help.server.util;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

@Component
public class TestDataInitializer {

    @Autowired
    private DataSource come2helpDatasource;

    public void initDatabase() {
        runSQLScript(come2helpDatasource, "db/initTestData.sql");
    }

    public void cleanupDatabase() {
        runSQLScript(come2helpDatasource, "db/cleanupTestData.sql");
    }

    private void runSQLScript(DataSource dataSource, String sqlScriptPath) {
        Resource resource = new ClassPathResource(sqlScriptPath);

        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.execute(dataSource);
    }
}
