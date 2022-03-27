package com.transit.ticketing.service;

import com.transit.ticketing.entity.Boats;
import com.transit.ticketing.repository.BoatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoatService {
    @Autowired
    BoatsRepository boatsRepository;
    public List<Boats> listAllBoats() {
        return boatsRepository.findAll();
    }
    public Page<Boats> listAllBoats(int pageNo,int numberOfRecords) {
        return boatsRepository.findAll(PageRequest.of(pageNo, numberOfRecords));
    }
}
