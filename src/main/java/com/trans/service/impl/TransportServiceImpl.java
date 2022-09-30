package com.trans.service.impl;

import com.trans.model.Transport;
import com.trans.model.User;
import com.trans.repository.TransportRepository;
import com.trans.service.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TransportServiceImpl implements TransportService {

    private final TransportRepository repository;
    @Autowired
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
    public Transport findById(int id) {
        return repository.findById(id);
    }

    @Override
    public boolean deleteById(int id) {
        return repository.deleteById(id);
    }

    @Override
    public boolean deleteAllByUserId(int user_id) {
        return repository.deleteAllByUserId(user_id);
    }

    @Override
    public int save(Transport transport) {
       return repository.save(transport).getId();
    }

    @Override
    public int saveWithUser(Transport transport, User user) {
        transport.setUser(user);
        return this.save(transport);
    }
}
