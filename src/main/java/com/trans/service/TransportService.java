package com.trans.service;

import com.trans.model.Transport;

import java.util.List;

public interface TransportService {
    List<Transport> findAll();
    List<Transport> findAllByUserId(int user_id);
    Transport findTransportById(int id);
    boolean deleteTransportById(int id);
    Transport findTransportByUserId(int user_id);
    int countTransport();
    boolean deleteAllByUserId(int user_id);
    void save(Transport transport);
}
