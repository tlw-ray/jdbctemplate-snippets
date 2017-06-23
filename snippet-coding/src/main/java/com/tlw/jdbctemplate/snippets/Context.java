package com.tlw.jdbctemplate.snippets;

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDataSourceConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.datasource.SmartDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * Created by hdp on 2017/6/18.
 */
public class Context {

    DataSource dataSource;
    JdbcTemplate jdbcTemplate;
    SimpleJdbcInsert simpleJdbcInsert;
    SimpleJdbcCall simpleJdbcCall;

    public Context() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("org.h2.Driver");
//        dataSource.setUrl("jdbc:h2:./test");

        SingleConnectionDataSource dataSource = new SingleConnectionDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:test");
//        dataSource.setUrl("jdbc:h2:./test");
        dataSource.setSuppressClose(true);

//        SingleConnectionDataSource dataSource = new SingleConnectionDataSource();
//        dataSource.setDriverClassName("org.apache.derby.jdbc.EmbeddedDriver");
//        dataSource.setUrl("jdbc:derby:memory:testdb;create=true");
//        dataSource.setSuppressClose(true);

//        JdbcDataSource dataSource = new JdbcDataSource();
//        dataSource.setUrl("jdbc:h2:mem:test");

//        EmbeddedDatabaseFactory factory = new EmbeddedDatabaseFactory();
//        EmbeddedDatabase dataSource = factory.getDatabase();

        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource);
        this.simpleJdbcCall = new SimpleJdbcCall(dataSource);
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public JdbcTemplate getJDBC_Template() {
        return jdbcTemplate;
    }

    public SimpleJdbcInsert getSimpleJDBC_Insert() {
        return simpleJdbcInsert;
    }

    public SimpleJdbcCall getSimpleJdbcCall() {
        return simpleJdbcCall;
    }
}
