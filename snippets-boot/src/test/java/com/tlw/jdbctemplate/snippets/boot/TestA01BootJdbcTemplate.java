package com.tlw.jdbctemplate.snippets.boot;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by hdp on 2017/6/18.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestA01BootJdbcTemplate {
    @Autowired
    A01BootJdbcTemplate a01BootJdbcTemplate;

    @Test
    public void test() {
        int result = a01BootJdbcTemplate.process();
        Assert.assertEquals(2, result);
    }
}
