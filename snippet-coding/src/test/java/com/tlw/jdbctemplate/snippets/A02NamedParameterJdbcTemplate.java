package com.tlw.jdbctemplate.snippets;

import com.tlw.jdbctemplate.snippets.ch01_sample.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

/**
 * Created by hdp on 2017/6/23.
 */
public class A02NamedParameterJdbcTemplate {

    Context context = new Context();

    @Before
    public void create() {
        context.getJDBC_Template().execute("create table user (id int AUTO_INCREMENT primary key, userName varchar(32), password varchar(32))");
        context.getJDBC_Template().execute("insert into user (userName, password) values ('xxx1', 'zzz')");
        context.getJDBC_Template().execute("insert into user (userName, password) values ('xxx2', 'zzz')");

    }

    @Test
    public void test() throws SQLException {
        //19.2.2 NamedParameterJdbcTemplate
        String sql = "select count(*) from user where userName = :userName";
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(context.getDataSource());
        SqlParameterSource namedParameters = new MapSqlParameterSource("userName", "xxx2");
        System.out.println(namedParameterJdbcTemplate.queryForObject(sql, namedParameters, Integer.class));

        sql = "select count(*) from user where userName = :userName";
        Map<String, String> namedParametersMap = Collections.singletonMap("userName", "xxx2");
        System.out.println(namedParameterJdbcTemplate.queryForObject(sql, namedParametersMap, Integer.class));

        //19.4.2 Batch operations with a List of objects
        User user1 = new User();
        user1.setId(1);
        user1.setUserName("user1");
        User user2 = new User();
        user2.setId(2);
        user2.setUserName("user2");
        User[] users = new User[]{user1, user2};
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(context.getDataSource());
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(users);
        int[] updateCounts = namedParameterJdbcTemplate.batchUpdate("update user set userName = :userName where id = :id", batch);
        System.out.println(Arrays.toString(updateCounts));
        ResultSet resultSet = context.getDataSource().getConnection().createStatement().executeQuery("select * from user");
        DBTablePrinter.printResultSet(resultSet);
    }
}
