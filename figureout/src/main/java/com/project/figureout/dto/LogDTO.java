package com.project.figureout.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class LogDTO {

    private LocalDateTime timestamp;

    private String user;

    private String action;

    private String table;

    private String column;

    private String data;

    private String oldData;

}
