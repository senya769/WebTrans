package com.trans.service.impl;

import com.trans.model.Cargo;
import com.trans.model.Transport;
import com.trans.model.User;
import com.trans.model.enums.TypeTransport;
import com.trans.repository.TransportRepository;
import com.trans.service.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
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
        Transport byId = transportRepository.findById(id);
        if (byId == null) {
            return false;
        }
        if (byId.getOrderList().isEmpty()) {
            transportRepository.deleteById(id);
        } else {
            byId.setDelete(true);
        }
        return true;
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
        transport.setLocalDateCreated(LocalDateTime.now());
        this.save(transport);
    }

    @Override
    public Page<Transport> findAll(int page) {
        return transportRepository.findAll(PageRequest.of(page - 1, 8));
    }

    @Override
    public Page<Transport> findAllByType(String type, Integer page) {
        if (Objects.equals(type, "All")) {
            return this.findAll(page);
        }
        return transportRepository.findAllByType(TypeTransport.fromString(type),
                PageRequest.of(page - 1, 8));
    }

    @Override
    public Page<Transport> search(String s,int page) {
        if (s != null) {
            return transportRepository.searchAllByKeyword(s,
                    PageRequest.of(page-1,8, Sort.by("localDateCreated").descending()));
        } else {
            return transportRepository.findAllByIsFreeIsTrue(PageRequest.of(page-1,8, Sort.by("localDateCreated").descending()));
        }
    }

    @Override
    public List<Transport> findAllByDeleteIsFalseAndFreeIsTrue() {
        return transportRepository.findAllByDeleteIsFalseAndFreeIsTrue();
    }

}
