package com.tlw.jdbctemplate.snippets;

import com.alibaba.fastjson.JSON;
import com.tlw.jdbctemplate.snippets.ch01_sample.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by hdp on 2017/6/18.
 */
public class A01JdbcTemplate {
    Context context = new Context();

    @Before
    public void create() {
        context.getJDBC_Template().execute("create table user (id int AUTO_INCREMENT primary key, userName varchar(32), password varchar(32))");
        context.getJDBC_Template().execute("insert into user (userName, password) values ('xxx1', 'zzz')");
        context.getJDBC_Template().execute("insert into user (userName, password) values ('xxx2', 'zzz')");
    }

    @Test
    public void useJDBC_Template() throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(context.getDataSource());
        int result = jdbcTemplate.query("select 1+1", resultSet -> {
            resultSet.next();
            return resultSet.getInt(1);
        });

        System.out.println(result);
        Assert.assertEquals(2, result);
    }

    @Test
    public void examples() throws SQLException {
        ResultSet resultSet = context.getDataSource().getConnection().createStatement().executeQuery("select * from user");
        DBTablePrinter.printResultSet(resultSet);

        //19.2.5 Running queries
        int result = context.getJDBC_Template().queryForObject("select count(*) from user", Integer.class);
        System.out.println(result);

        List<Map<String, Object>> items = context.getJDBC_Template().queryForList("select * from user");
        System.out.println(JSON.toJSON(items));

        //19.2.6 Updating the database
        context.getJDBC_Template().update("update user set userName = ? where id = ?", "name1", 1);

        String userName = context.getJDBC_Template().queryForObject("select userName from user limit 1", String.class);
        System.out.println(userName);

        //19.2.7 Retrieving auto-generated keys
        KeyHolder keyHolder = new GeneratedKeyHolder();
        context.getJDBC_Template().update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement("insert into user (userName) values(?)",
                            new String[]{"id"});
                    ps.setString(1, "Rob");
                    return ps;
                },
                keyHolder);
        System.out.println("generatedID: " + keyHolder.getKey());
        resultSet = context.getDataSource().getConnection().createStatement().executeQuery("select * from user");
        DBTablePrinter.printResultSet(resultSet);

        //19.4.1 Basic batch operations with the JdbcTemplate
        context.getJDBC_Template().batchUpdate("update user set userName = ? where id = ?",
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, items.get(i).get("userName").toString() + i);
                        ps.setInt(2, (Integer) items.get(i).get("id"));
                    }

                    public int getBatchSize() {
                        return items.size();
                    }
                });
        resultSet = context.getDataSource().getConnection().createStatement().executeQuery("select * from user");
        DBTablePrinter.printResultSet(resultSet);

        List<Object[]> params = new ArrayList();
        params.add(new Object[]{"user5", 1});
        params.add(new Object[]{"user6", 2});
        context.getJDBC_Template().batchUpdate("update user set userName = ? where id = ?", params);
        resultSet = context.getDataSource().getConnection().createStatement().executeQuery("select * from user");
        DBTablePrinter.printResultSet(resultSet);
    }
}
