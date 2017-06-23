package com.tlw.jdbctemplate.snippets.ch01_sample;

import java.util.List;

/**
 * Created by hdp on 2017/6/18.
 */
public interface UserDao {
    void addUser(User user);

    void deleteUser(int id);

    void updateUser(User user);

    String searchUserName(int id);

    User searchUser(int id);

    List<User> findAll();
}
