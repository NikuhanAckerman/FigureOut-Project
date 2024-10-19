package com.project.figureout.service;

import com.project.figureout.model.Manufacturer;
import com.project.figureout.model.Size;
import com.project.figureout.repository.ManufacturerRepository;
import com.project.figureout.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SizeService {

    @Autowired
    private SizeRepository sizeRepository;

    public List<Size> getAllSizes() {
        return sizeRepository.findAll();
    }

    public Size getSizeById(long id) {
        return sizeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Tamanho de produto não encontrado pelo ID."));
    }

}
