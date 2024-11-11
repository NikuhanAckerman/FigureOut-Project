package com.project.figureout.service;
import com.project.figureout.dto.ClientBasicDataDTO;
import com.project.figureout.dto.LogDTO;
import com.project.figureout.model.Client;
import com.project.figureout.model.Log;
import com.project.figureout.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    public void logTransaction(String user, String action, String table, String column, String data, String oldData) {
        Log log = new Log();
        log.setTimestamp(LocalDateTime.now());
        log.setUser(user);
        log.setAction(action);
        log.setTable(table);
        log.setColumn(column);
        log.setData(data);
        log.setOldData(oldData);
        logRepository.save(log);
    }

    public void logTransaction(LogDTO logDTO) {
        Log log = new Log();
        log.setTimestamp(logDTO.getTimestamp());
        log.setUser(logDTO.getUser());
        log.setAction(logDTO.getAction());
        log.setTable(logDTO.getTable());
        log.setColumn(logDTO.getColumn());
        log.setData(logDTO.getData());
        log.setOldData(logDTO.getOldData());
        logRepository.save(log);
    }

    public void logUpdateClient(Client clientToUpdate, ClientBasicDataDTO clientBasicDataDTO) throws IllegalAccessException {




    }

}
