package com.hotel.dao;

import com.hotel.model.Client;
import com.hotel.model.ClientSearchBean;
import com.hotel.util.PaginateInfo;

import java.util.List;

public interface ClientDAO {
    List<Client> findAll(ClientSearchBean ssb, PaginateInfo pi);

    //根据id删除一条记录
    boolean deleteById(Integer id);

    int save(Client client);

    int update(Client clt);

    List<Client> findAll(Integer[] ids);
}
