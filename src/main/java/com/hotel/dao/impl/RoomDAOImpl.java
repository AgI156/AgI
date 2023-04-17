package com.hotel.dao.impl;

import com.hotel.dao.RoomDAO;
import com.hotel.global.Global;
import com.hotel.model.Room;
import com.hotel.model.RoomSearchBean;
import com.hotel.util.PaginateInfo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class RoomDAOImpl implements RoomDAO {
    private final JdbcTemplate template = new JdbcTemplate(Global.getDataSource());

    @Override
    public List<Room> findAll() {
        String sql = "select id , name from room where state = '空闲'";
        List<Room> rooms = template.query(sql, new BeanPropertyRowMapper(Room.class));
        return rooms;
    }

    @Override
    public List<Room> findAll(RoomSearchBean rsb, PaginateInfo pi) {
        String countSql = "SELECT count(0) FROM room";//查询总数
        String sql = "SELECT id,name,kind,state,price,description FROM room";

        List<Object> args = new ArrayList<>();
        StringBuilder whereSql = new StringBuilder();
        if (rsb != null) {
            whereSql.append(" where 1=1");
            if (rsb.getId() != null) {
                whereSql.append(" and id = ?");
                args.add(rsb.getId());
            }
            if (StringUtils.hasText(rsb.getName())) {
                whereSql.append(" and name like ?");
                args.add("%" + rsb.getName() + "%");
            }
            if (StringUtils.hasText(rsb.getKind())) {
                whereSql.append(" and kind = ?");
                args.add(rsb.getKind());
            }
            if (rsb.getState() != null) {
                whereSql.append(" and state = ?");
                args.add(rsb.getState());
            }
            if (rsb.getPriceFrom() != null) {
                whereSql.append(" and price >= ?");
                args.add(rsb.getPriceFrom());
            }
            if (rsb.getPriceTo() != null) {
                whereSql.append(" and price < ?");
                args.add(rsb.getPriceTo());
                System.out.println(rsb.getPriceTo());
            }
        }

//        System.out.println(sql);
        countSql += whereSql.toString();
        Long count = template.queryForObject(countSql, Long.class, args.toArray());
        pi.setCount(count);

//        System.out.println(countSql);
//        System.out.println(count);

        whereSql.append(" limit ?,?");
        args.add(pi.getOffset());
        args.add(pi.getLimit());
        sql += whereSql.toString();

        List<Room> rooms = template.query(sql, new BeanPropertyRowMapper(Room.class), args.toArray());

        return rooms;
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM room WHERE id = ?";
        String sql2 = "update client set room_id = '1' where room_id = ?";
        int rows2 = template.update(sql2, id);
        int rows = template.update(sql, id);
        return rows > 0;
    }

    @Override
    public int save(Room rm) {
        String sql = "insert into room " +
                "(name,kind,state,price,description) " +
                "values (?,?,?,?,?)";
        int rows = template.update(sql, rm.getName(), rm.getKind(), rm.getState(), rm.getPrice(), rm.getDescription());

        return rows;
    }

    @Override
    public int update(Room room) {
        String sql = "update room set name =?,kind=?,state=?,price=?,description=? where id = ?";
        int rows = template.update(sql, room.getName(), room.getKind(), room.getState(),
                room.getPrice(), room.getDescription(), room.getId());
        return rows;
    }
}
