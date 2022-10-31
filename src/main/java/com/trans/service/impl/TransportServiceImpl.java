package com.trans.service.impl;

import com.trans.model.Transport;
import com.trans.model.User;
import com.trans.model.enums.TypeTransport;
import com.trans.repository.TransportRepository;
import com.trans.service.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class TransportServiceImpl implements TransportService {

    private final TransportRepository transportRepository;

    @Autowired
    public TransportServiceImpl(TransportRepository repository) {
        this.transportRepository = repository;
    }

    @Override
    public List<Transport> findAll() {
        return transportRepository.findAll();
    }

    @Override
    public List<Transport> findAllByUserId(int user_id) {
        return transportRepository.findAllByUserId(user_id);
    }

    @Override
    public Transport findById(int id) {
        return transportRepository.findById(id);
    }

    @Override
    public boolean deleteById(int id) {
        if (transportRepository.findById(id) == null) {
            return false;
        } else {
            transportRepository.deleteById(id);
            return true;
        }
    }

    @Override
    public boolean deleteAllByUserId(int user_id) {
        return transportRepository.deleteAllByUserId(user_id);
    }

    @Override
    public void save(Transport transport) {
        transportRepository.save(transport);
    }

    @Override
    public void saveWithUser(Transport transport, User user) {
        transport.setUser(user);
        this.save(transport);
    }
    @Override
    public Page<Transport> findAll(int page) {
        return transportRepository.findAll(PageRequest.of(page - 1, 8));
    }

    @Override
    public Page<Transport> findAllByType(String type, Integer page) {
        if(Objects.equals(type, "All")){
         return this.findAll(page);
        }
            return transportRepository.findAllByType(TypeTransport.fromString(type),
                    PageRequest.of(page - 1,8));
    }

    @Override
    public List<Transport> search(String s) {
        if(s != null) {
            return transportRepository.searchAllByKeyword(s);
        }
        else {
            return transportRepository.findAllByIsFreeIsTrue();
        }
    }
}
