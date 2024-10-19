package com.project.figureout.service;

import com.project.figureout.model.Country;
import com.project.figureout.model.Manufacturer;
import com.project.figureout.model.State;
import com.project.figureout.repository.CountryRepository;
import com.project.figureout.repository.ManufacturerRepository;
import com.project.figureout.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerService {

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    public Manufacturer getManufacturerById(long id) {
        return manufacturerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Fabricante não encontrado pelo ID."));
    }


}
