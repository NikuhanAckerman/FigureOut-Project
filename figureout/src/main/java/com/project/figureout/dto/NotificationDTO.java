package com.project.figureout.dto;

import com.project.figureout.model.Client;
import com.project.figureout.model.NotificationCategoryEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class NotificationDTO {

    private NotificationCategoryEnum category;

    private String title;

    private String description;

}
