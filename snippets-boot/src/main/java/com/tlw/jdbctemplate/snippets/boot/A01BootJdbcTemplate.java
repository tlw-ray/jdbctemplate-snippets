package com.tlw.jdbctemplate.snippets.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by hdp on 2017/6/18.
 */
@SpringBootApplication
@Service
public class A01BootJdbcTemplate {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        ApplicationContext context = SpringApplication.run(A01BootJdbcTemplate.class, args);
        int result = context.getBean(A01BootJdbcTemplate.class).process();
        System.out.println(result);
        System.out.println("Spend: " + (System.currentTimeMillis() - start));
    }

    @Bean
    public DriverManagerDataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:test");
        return dataSource;
    }

    public int process() {
        return jdbcTemplate.query("select 1+1", new ResultSetExtractor<Integer>() {
            @Override
            public Integer extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                resultSet.next();
                return resultSet.getInt(1);
            }
        });
    }

}
