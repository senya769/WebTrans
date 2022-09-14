package com.trans.service.impl;

import com.trans.model.Transport;
import com.trans.repository.TransportRepository;
import com.trans.service.TransportService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TransportServiceImpl implements TransportService {
    private TransportRepository repository;

    public TransportServiceImpl(TransportRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Transport> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Transport> findAllByUserId(int user_id) {
        return repository.findAllByUserId(user_id);
    }

    @Override
    public Transport findTransportById(int id) {
        return repository.findTransportById(id);
    }

    @Override
    public boolean deleteTransportById(int id) {
        return repository.deleteTransportById(id);
    }

    @Override
    public Transport findTransportByUserId(int user_id) {
        return repository.findTransportByUserId(user_id);
    }

    @Override
    public int countTransport() {
        return 1;
        //return repository.countTransport();
    }

    @Override
    public boolean deleteAllByUserId(int user_id) {
        return repository.deleteAllByUserId(user_id);
    }

    @Override
    public void save(Transport transport) {
        repository.save(transport);
    }
}
