package com.pentryyy.horoscope_prediction.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pentryyy.horoscope_prediction.enumeration.GenderType;
import com.pentryyy.horoscope_prediction.enumeration.PredictionType;
import com.pentryyy.horoscope_prediction.enumeration.ZodiacType;
import com.pentryyy.horoscope_prediction.model.Prediction;

public interface PredictionRepository extends JpaRepository<Prediction, Long> {
    @Query("SELECT p FROM Prediction p WHERE "
        + "(:gender IS NULL OR p.gender = :gender) AND "
        + "(:zodiac IS NULL OR p.zodiac = :zodiac) AND "
        + "(:predictionType IS NULL OR p.predictionType = :predictionType)")
    Page<Prediction> findPredictions(
        @Param("gender") GenderType gender,
        @Param("zodiac") ZodiacType zodiac,
        @Param("predictionType") PredictionType predictionType,
        Pageable pageable);
}