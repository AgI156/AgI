package com.hotel.service;

import com.hotel.model.Room;
import com.hotel.model.User;

public interface UserService {
    User findByUsername(String username);

    boolean checkLogin(User user, String password);


    boolean update(User user);






}
