package com.hotel.dao.impl;

import com.hotel.dao.ClientDAO;
import com.hotel.global.Global;
import com.hotel.model.Client;
import com.hotel.model.ClientSearchBean;
import com.hotel.util.PaginateInfo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClientDAOImpl implements ClientDAO {

    private final JdbcTemplate template = new JdbcTemplate(Global.getDataSource());

    @Override
    public List<Client> findAll(ClientSearchBean csb, PaginateInfo pi) {
        String countSql = "select count(0) from client t1 LEFT JOIN room t2 ON t1.room_id = t2.id";//查询总数
        String sql = "select t1.id,client_id,t1.`name`,sex,birthday,phone,t2.`name` as room_name,t1.description " +
                "from client t1 LEFT JOIN room t2 ON t1.room_id = t2.id";//

        List<Object> args = new ArrayList<>();
        StringBuilder whereSql = new StringBuilder();
        if (csb != null) {
            whereSql.append(" where 1=1");
            if (csb.getId() != null) {
                whereSql.append(" and t1.id = ?");
                args.add(csb.getId());
            }
            if (StringUtils.hasText(csb.getClientId())) {
                whereSql.append(" and client_id = ?");
                args.add(csb.getClientId());
            }
            if (StringUtils.hasText(csb.getName())) {
                whereSql.append(" and t1.name like ?");
                args.add("%" + csb.getName() + "%");
            }
            if (StringUtils.hasText(csb.getSex())) {
                whereSql.append(" and sex = ?");
                args.add(csb.getSex());
            }
            if (StringUtils.hasText(csb.getPhone())) {
                whereSql.append(" and phone like ?");
                args.add("%" + csb.getPhone() + "%");
            }
            /*if (csb.getAgeFrom() != null) {
                whereSql.append(" and age >= ?");
                args.add(csb.getAgeFrom());
            }
            if (csb.getAgeTo() != null) {
                whereSql.append(" and age < ?");
                args.add(csb.getAgeTo());
            }*/
            if (csb.getBirthdayFrom() != null) {
                whereSql.append(" and birthday >= ?");
                args.add(csb.getBirthdayFrom());
            }
            if (csb.getBirthdayTo() != null) {
                whereSql.append(" and birthday < ?");
                args.add(csb.getBirthdayTo());
            }
        }

        countSql += whereSql.toString();

        Long count = template.queryForObject(countSql, Long.class, args.toArray());
        pi.setCount(count);

        whereSql.append(" limit ?,?");
        args.add(pi.getOffset());
        args.add(pi.getLimit());

        sql += whereSql.toString();
//        System.out.println(sql);
        List<Client> clients = template.query(sql, new BeanPropertyRowMapper(Client.class), args.toArray());

        //查询总数
        return clients;

    }

    @Override
    public boolean deleteById(Integer id) {
        String sql = "delete from client where id = ?";
        int rows = template.update(sql, id);
        return rows > 0;
    }

    @Override
    public int save(Client client) {
        String sql = "insert into client " +
                "(client_id,name,sex,birthday,phone,room_id,description) " +
                "values (?,?,?,?,?,?,?)";
        String sql1 = "update room set state=? where id =?";
        int rows = template.update(sql, client.getClientId(), client.getName(), client.getSex(),
                client.getBirthday(), client.getPhone(), client.getRoomId(), client.getDescription());
       template.update(sql1, "有客", client.getRoomId());
        return rows;
    }

    @Override
    public int update(Client client) {
        String sql = "update client set client_id =?,name=?,sex=?,birthday=?,phone=?,description=? where id =?";
        int rows = template.update(sql, client.getClientId(), client.getName(), client.getSex(),
                client.getBirthday(), client.getPhone(), client.getDescription(), client.getId());
        return rows;
    }

    @Override
    public List<Client> findAll(Integer[] ids) {
        String clt = Stream.of(ids).map(String::valueOf).collect(Collectors.joining(","));
        String sql = "select t1.id,client_id,t1.`name`,sex,birthday,phone,t1.id as room_id,t2.`name` as room_name,t1.description " +
                "from client t1 LEFT JOIN room t2 ON t1.room_id = t2.id where t1.id in (" + clt + ")";
        return template.query(sql, new BeanPropertyRowMapper<>(Client.class));
    }


}
