package com.tlw.jdbctemplate.snippets.ch01_sample;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by hdp on 2017/6/18.
 */
public class UserTest {

    UserDao userDao;

    @BeforeClass
    public static void initClass() {
        System.out.println("create Table");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:./test");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("drop table user");
        jdbcTemplate.execute("create table user (id int primary key, userName varchar(32), password varchar(32))");
    }

    @Before
    public void init() {
        System.out.println("init...");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:./test");

        UserDaoImpl userDaoImpl = new UserDaoImpl();
        userDaoImpl.setDataSource(dataSource);

        this.userDao = userDaoImpl;
    }

    @Test//增
    public void demo1() {
        User user1 = new User();
        user1.setId(1);
        user1.setUserName("user1");
        user1.setPassword("111111");
        userDao.addUser(user1);
        User user2 = new User();
        user2.setId(2);
        user2.setUserName("user2");
        user2.setPassword("222222");
        userDao.addUser(user2);
        User user3 = new User();
        user3.setId(3);
        user3.setUserName("user3");
        user3.setPassword("333333");
        userDao.addUser(user3);
    }

    @Test//改
    public void demo2() {
        User user = new User();
        user.setId(3);
        user.setUserName("admin");
        user.setPassword("admin");
        userDao.updateUser(user);
    }

    @Test//删
    public void demo3() {
        userDao.deleteUser(3);
    }

    @Test//查（简单查询，返回字符串）
    public void demo4() {
        String name = userDao.searchUserName(1);
        System.out.println(name);
    }

    @Test//查（简单查询，返回对象）
    public void demo5() {
        User user = userDao.searchUser(1);
        System.out.println(user.getUserName());
    }

    @Test//查（复杂查询，返回对象集合）
    public void demo6() {
        List<User> users = userDao.findAll();
        System.out.println(users.size());
    }
}
