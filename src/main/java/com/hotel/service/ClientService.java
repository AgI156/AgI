package com.hotel.service;

import com.hotel.model.Client;
import com.hotel.model.ClientSearchBean;
import com.hotel.util.PaginateInfo;

import java.util.List;

public interface ClientService {
    List<Client> findAll(ClientSearchBean csb, PaginateInfo pi);

    default Client findById(Integer id) {
        ClientSearchBean csb = new ClientSearchBean();
        csb.setId(id);
        PaginateInfo pi = new PaginateInfo(1, 1);
        List<Client> clients = findAll(csb, pi);
        return clients.size() > 0 ? clients.get(0) : null;
    }

    int deleteByIds(Integer[] ids);

    boolean save(Client clt);

    boolean update(Client clt);

    //判断是否已存在某个指定的学号
    boolean hasClientId(String clientId);

    List<Client> findAll(Integer[] ids);

}
