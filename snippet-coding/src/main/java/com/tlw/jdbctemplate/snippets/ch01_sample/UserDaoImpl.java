package com.tlw.jdbctemplate.snippets.ch01_sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by hdp on /6/.
 */
public class UserDaoImpl extends JdbcDaoSupport implements UserDao {

    public void addUser(User user) {
        String sql = "insert into user values(?,?,?)";
        this.getJdbcTemplate().update(sql, user.getId(), user.getUserName(),
                user.getPassword());
    }

    public void deleteUser(int id) {
        String sql = "delete from user where id=?";
        this.getJdbcTemplate().update(sql, id);
    }

    public void updateUser(User user) {
        String sql = "update user set username=?,password=? where id=?";
        this.getJdbcTemplate().update(sql, user.getUserName(),
                user.getPassword(), user.getId());
    }

    public String searchUserName(int id) {// 简单查询，按照ID查询，返回字符串
        String sql = "select userName from user where id=?";
// 返回类型为String(String.class)
        return this.getJdbcTemplate().queryForObject(sql, String.class, id);
    }

    public List<User> findAll() {// 复杂查询返回List集合
        String sql = "select * from user";
        return this.getJdbcTemplate().query(sql, new UserRowMapper());
    }

    public User searchUser(int id) {
        String sql = "select * from user where id=?";
        return this.getJdbcTemplate().queryForObject(sql, new UserRowMapper(), id);
    }

    class UserRowMapper implements RowMapper<User> {
        //rs为返回结果集，以每行为单位封装着

        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUserName(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    }
}