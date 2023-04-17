package com.hotel.service.impl;

import com.hotel.dao.UserDAO;
import com.hotel.dao.impl.UserDAOImpl;
import com.hotel.model.User;
import com.hotel.service.UserService;
import com.hotel.util.BeanFactory;
import com.hotel.util.Md5Utils;

public class UserServiceImpl implements UserService {
    private final UserDAO dao = BeanFactory.getBean(UserDAOImpl.class);

    @Override
    public User findByUsername(String username) {
        return dao.findByUsername(username);
    }

    @Override
    public boolean checkLogin(User user, String password) {
        if(user == null) {
            return false;
        }
        String encrypt = Md5Utils.encrypt(password + "{" + user.getUsername() + "}");//密码加密之后的密文
        if (encrypt.equals(user.getPassword())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean update(User user) {
        return dao.update(user) > 0;
    }
}
