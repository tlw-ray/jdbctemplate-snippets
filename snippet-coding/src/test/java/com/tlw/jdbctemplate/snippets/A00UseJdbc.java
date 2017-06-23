package com.tlw.jdbctemplate.snippets;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by hdp on 2017/6/18.
 */
public class A00UseJdbc {
    @Test
    public void testJdbc() throws SQLException, ClassNotFoundException {
        Class.forName("org.h2.Driver");
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:test");
        ResultSet resultSet = connection.createStatement().executeQuery("select 1+1");
        resultSet.next();
        int result = resultSet.getInt(1);

        connection.createStatement().execute("create table user (id int AUTO_INCREMENT primary key, userName varchar(32), password varchar(32))");
        connection.createStatement().execute("insert into user (userName, password) values ('u1', 'p1')");
        ResultSet rs = connection.createStatement().executeQuery("select * from user");
        DBTablePrinter.printResultSet(rs);
        connection.close();
        Assert.assertEquals(2, result);
    }
}
