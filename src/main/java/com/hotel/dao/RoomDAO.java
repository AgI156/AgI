package com.hotel.dao;

import com.hotel.model.Room;
import com.hotel.model.RoomSearchBean;
import com.hotel.util.PaginateInfo;

import java.util.List;

public interface RoomDAO {
    List<Room> findAll();


    List<Room> findAll(RoomSearchBean rsb, PaginateInfo pi);

    //根据id删除一条记录
    boolean deleteById(Integer id);

    int save(Room rm);

    int update(Room room);

}
