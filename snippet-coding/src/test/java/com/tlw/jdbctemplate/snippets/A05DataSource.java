package com.tlw.jdbctemplate.snippets;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.datasource.SmartDataSource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * Created by hdp on 2017/6/19.
 */
public class A05DataSource {

    String DRIVER = "org.h2.Driver";
    String URL = "jdbc:h2:mem:test";

    @Test
    public void testDriverManagerDataSource() throws SQLException {
        //19.3.1 DataSource
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DRIVER);
        dataSource.setUrl(URL);
        Connection connection1 = dataSource.getConnection();
        Connection connection2 = dataSource.getConnection();
        Assert.assertNotEquals(connection1, connection2);
    }

    @Test
    public void testSingleDataSource() throws SQLException {
        //19.3.5 SingleConnectionDataSource
        SingleConnectionDataSource singleConnectionDataSource = new SingleConnectionDataSource();
        singleConnectionDataSource.setDriverClassName(DRIVER);
        singleConnectionDataSource.setUrl(URL);
        Connection connection1 = singleConnectionDataSource.getConnection();
        Connection connection2 = singleConnectionDataSource.getConnection();
        Assert.assertEquals(connection1, connection2);
    }

}
