package com.hotel.dao.impl;

import com.hotel.dao.UserDAO;
import com.hotel.global.Global;
import com.hotel.model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserDAOImpl implements UserDAO {
    private final JdbcTemplate template = new JdbcTemplate(Global.getDataSource());

    @Override
    public User findByUsername(String username) {
        String sql = "select id,username,`password` from t_security_user where username = ?";

        List<User> users = template.query(sql, new BeanPropertyRowMapper(User.class), username);

//        System.out.println(users.size());

        return users.size() > 0 ? users.get(0) : null;
    }

    @Override
    public int update(User user) {
        String sql = "update t_security_user set password=? where username = ?";
        int rows = template.update(sql, user.getPassword(), user.getUsername());
        return rows;
    }
}
