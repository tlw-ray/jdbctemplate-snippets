package com.tlw.jdbctemplate.snippets;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by hdp on 2017/6/22.
 */
public class A03EmbeddedDatabase {

    @Test
    public void test1() throws SQLException {
        //19.8.3 Creating an embedded database programmatically
        EmbeddedDatabase db = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .addScript("schema.sql")
                .addScripts("data.sql")
                .build();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(db);
        jdbcTemplate.execute("insert into user (userName, password) values ('xxx1', 'zzz')");
        jdbcTemplate.execute("insert into user (userName, password) values ('xxx2', 'zzz')");
        ResultSet resultSet = db.getConnection().createStatement().executeQuery("select * from user");
        DBTablePrinter.printResultSet(resultSet);

        Connection connection = db.getConnection();
        DBTablePrinter.printTable(connection, "user");

        // perform actions against the db (EmbeddedDatabase extends javax.sql.DataSource)

        db.shutdown();
    }
}
