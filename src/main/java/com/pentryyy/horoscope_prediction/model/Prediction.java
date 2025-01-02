package com.pentryyy.horoscope_prediction.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "predictions")
public class Prediction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "prediction_text", nullable = false, length = 500)
    private String predictionText;

    @Column(name = "prediction_date", nullable = false)
    private LocalDate predictionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "prediction_type", nullable = false)
    private PredictionType predictionType;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}