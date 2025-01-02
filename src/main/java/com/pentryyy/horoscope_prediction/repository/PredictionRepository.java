package com.pentryyy.horoscope_prediction.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pentryyy.horoscope_prediction.model.Prediction;
import com.pentryyy.horoscope_prediction.model.PredictionType;

public interface PredictionRepository extends JpaRepository<Prediction, Long> {
    Page<Prediction> findByPredictionType(PredictionType type, Pageable pageable);
}