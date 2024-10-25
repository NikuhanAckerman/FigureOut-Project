package com.project.figureout.service;
import com.project.figureout.model.Log;
import com.project.figureout.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    public void logTransaction(String user, String action, String details) {
        Log log = new Log();
        log.setTimestamp(LocalDateTime.now());
        log.setUser(user);
        log.setAction(action);
        log.setDetails(details);
        logRepository.save(log);
    }
}
