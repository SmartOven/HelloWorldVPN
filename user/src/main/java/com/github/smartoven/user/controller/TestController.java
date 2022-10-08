package com.github.smartoven.user.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.SQLException;

@RestController
public class TestController {

    @GetMapping("/ping")
    public String getOk() {
        return "0;OK";
    }

    @GetMapping("/postgres")
    public String getPostgresStatus() {
        try (var c = getTestDataSource().getConnection();
             var st = c.createStatement()) {
            var rs = st.executeQuery("SELECT 1;");
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    private DataSource testDataSource;

    private DataSource getTestDataSource() {
        // Setup
        if (testDataSource == null) {
            DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
            dataSourceBuilder.driverClassName(driverClassName);
            dataSourceBuilder.url(url);
            dataSourceBuilder.username(username);
            dataSourceBuilder.password(password);
            testDataSource = dataSourceBuilder.build();
        }

        return testDataSource;
    }

}
