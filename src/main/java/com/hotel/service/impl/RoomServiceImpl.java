package com.hotel.service.impl;

import com.hotel.dao.RoomDAO;
import com.hotel.dao.impl.RoomDAOImpl;
import com.hotel.model.Room;
import com.hotel.model.RoomSearchBean;
import com.hotel.service.RoomService;
import com.hotel.util.BeanFactory;
import com.hotel.util.PaginateInfo;

import java.util.List;

public class RoomServiceImpl implements RoomService {
    private final RoomDAO dao = BeanFactory.getBean(RoomDAOImpl.class);

    @Override
    public List<Room> findAll() {
        return dao.findAll();
    }

    @Override
    public List<Room> findAll(RoomSearchBean rsb, PaginateInfo pi) {
        return dao.findAll(rsb, pi);
    }

    @Override
    public int deleteByIds(Integer[] ids) {
        int count = 0;
        for (Integer id : ids) {
            boolean b = dao.deleteById(id);
            if (b) {
                count++;
            }
        }
        return count;
    }

    @Override
    public boolean save(Room rm) {
        return dao.save(rm) > 0;
    }

    @Override
    public boolean update(Room rm) {
        return dao.update(rm) > 0;
    }
}
