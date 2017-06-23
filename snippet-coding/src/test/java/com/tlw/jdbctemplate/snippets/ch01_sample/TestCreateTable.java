package com.tlw.jdbctemplate.snippets.ch01_sample;

import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Created by hdp on 2017/6/18.
 */
public class TestCreateTable {
    @Test
    public void createTable() {
        System.out.println("create Table");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:./test");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("create table user (id int AUTO_INCREMENT primary key, userName varchar(32), password varchar(32))");
//        jdbcTemplate.execute("insert into user (id, userName, password) values (1, 'userName1', 'password1')");
        jdbcTemplate.execute("select count(*) from user");
    }
}
