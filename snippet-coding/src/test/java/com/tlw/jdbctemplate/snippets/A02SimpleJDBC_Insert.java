package com.tlw.jdbctemplate.snippets;

import com.alibaba.fastjson.JSON;
import com.tlw.jdbctemplate.snippets.ch01_sample.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.util.*;

/**
 * Created by hdp on 2017/6/18.
 * 19.5 Simplifying JDBC operations with the SimpleJdbc classes
 */
public class A02SimpleJDBC_Insert {
    Context context = new Context();

    @Before
    public void create() {
        context.getJDBC_Template().execute("create table user (id int AUTO_INCREMENT primary key, userName varchar(32), password varchar(32))");
    }

    @Test
    public void useSimpleJDBC_Insert() throws SQLException, InterruptedException {
        //19.5.1 Inserting data using SimpleJdbcInsert
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(context.getDataSource());
        jdbcInsert.withTableName("user");
        Map<String, Object> parameters = new HashMap<String, Object>(1);
        parameters.put("userName", "xxx3");
        jdbcInsert.execute(parameters);
        ResultSet resultSet = context.getDataSource().getConnection().createStatement().executeQuery("select * from user");
        DBTablePrinter.printResultSet(resultSet);

        //19.5.2 Retrieving auto-generated keys using SimpleJdbcInsert
        SimpleJdbcInsert jdbcInsert1 = new SimpleJdbcInsert(context.getDataSource());
        jdbcInsert1.withTableName("user");
        jdbcInsert1.usingGeneratedKeyColumns("id");
        Number newId = jdbcInsert1.executeAndReturnKey(parameters);
        System.out.println(newId.longValue());

        //19.5.3 Specifying columns for a SimpleJdbcInsert
        SimpleJdbcInsert jdbcInsert2 = new SimpleJdbcInsert(context.getDataSource());
        jdbcInsert2.withTableName("user");
//        jdbcInsert2.usingColumns("password");
        Map<String, Object> parameters1 = new HashMap();
        parameters1.put("password", "ps1");
        jdbcInsert2.execute(parameters1);
        resultSet = context.getDataSource().getConnection().createStatement().executeQuery("select * from user");
        DBTablePrinter.printResultSet(resultSet);

        //19.5.4 Using SqlParameterSource to provide parameter values
        SimpleJdbcInsert jdbcInsert3 = new SimpleJdbcInsert(context.getDataSource())
                .withTableName("user")
                .usingGeneratedKeyColumns("id");
        User user = new User();
        user.setUserName("userName1");
        user.setPassword("password1");
        SqlParameterSource beanParam = new BeanPropertySqlParameterSource(user);
        jdbcInsert3.executeAndReturnKey(beanParam);
        resultSet = context.getDataSource().getConnection().createStatement().executeQuery("select * from user");
        DBTablePrinter.printResultSet(resultSet);
    }
}
