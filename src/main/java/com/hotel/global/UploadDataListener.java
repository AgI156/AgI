package com.hotel.global;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.hotel.model.Client;
import com.hotel.service.ClientService;
import com.hotel.service.impl.ClientServiceImpl;
import com.hotel.util.BeanFactory;

import java.util.ArrayList;
import java.util.List;

public class UploadDataListener implements ReadListener<Client> {
    private final ClientService clientService = BeanFactory.getBean(ClientServiceImpl.class);
    private final List<Client> list = new ArrayList<>();

    @Override
    public void invoke(Client client, AnalysisContext analysisContext) {
        list.add(client);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("读取结束");
        for (Client client : list) {
            clientService.save(client);
        }
    }
}