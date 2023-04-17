package com.hotel.service.impl;

import com.hotel.dao.ClientDAO;
import com.hotel.dao.impl.ClientDAOImpl;
import com.hotel.model.Client;
import com.hotel.model.ClientSearchBean;
import com.hotel.service.ClientService;
import com.hotel.util.BeanFactory;
import com.hotel.util.PaginateInfo;

import java.util.List;

public class ClientServiceImpl implements ClientService {


    private final ClientDAO dao = BeanFactory.getBean(ClientDAOImpl.class);

    @Override
    public List<Client> findAll(ClientSearchBean csb, PaginateInfo pi) {
        return dao.findAll(csb, pi);
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
    public boolean save(Client clt) {
        return dao.save(clt) > 0;
    }

    @Override
    public boolean update(Client clt) {
        return dao.update(clt) > 0;
    }

    @Override
    public boolean hasClientId(String clientId) {
        ClientSearchBean csb = new ClientSearchBean();
        csb.setClientId(clientId);

        List<Client> clients = dao.findAll(csb, new PaginateInfo(1, 2));
        return clients.size() > 0;
    }

    @Override
    public List<Client> findAll(Integer[] ids) {
        return dao.findAll(ids);
    }
}
