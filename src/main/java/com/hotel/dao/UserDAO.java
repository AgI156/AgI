package com.hotel.dao;

import com.hotel.model.Room;
import com.hotel.model.User;

public interface UserDAO {
    User findByUsername(String username);



    int update(User user);

}
