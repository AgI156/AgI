package com.hotel.service;

import com.hotel.model.Room;
import com.hotel.model.RoomSearchBean;
import com.hotel.util.PaginateInfo;

import java.util.List;

public interface RoomService {

    List<Room> findAll();


    List<Room> findAll(RoomSearchBean rsb, PaginateInfo pi);

    default Room findById(Integer id) {
        RoomSearchBean rsb = new RoomSearchBean();
        rsb.setId(id);
        PaginateInfo pi = new PaginateInfo(1, 1);
        List<Room> rooms = findAll(rsb, pi);
        return rooms.size() > 0 ? rooms.get(0) : null;
    }

    int deleteByIds(Integer[] ids);

    boolean save(Room rm);

    boolean update(Room room);


}
