package com.pentryyy.horoscope_prediction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pentryyy.horoscope_prediction.enumeration.GenderType;
import com.pentryyy.horoscope_prediction.enumeration.PredictionType;
import com.pentryyy.horoscope_prediction.enumeration.ZodiacType;
import com.pentryyy.horoscope_prediction.model.Prediction;
import com.pentryyy.horoscope_prediction.repository.PredictionRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PredictionService {

    @Autowired
    private PredictionRepository predictionRepository;

    public Prediction createPrediction(Prediction prediction) {
        prediction.setCreatedAt(LocalDateTime.now());
        prediction.setUpdatedAt(LocalDateTime.now());
        return predictionRepository.save(prediction);
    }

    public boolean existsById(Long id) {
        return predictionRepository.existsById(id);
    }

    public void deleteById(Long id) {
        predictionRepository.deleteById(id);
    }

    public Optional<Prediction> findById(Long id) {
        return predictionRepository.findById(id);
    }

    public Prediction updatePrediction(
        Prediction newPrediction, 
        Prediction prediction) {
        
        newPrediction.setPredictionText(prediction.getPredictionText());
        newPrediction.setGender(prediction.getGender());
        newPrediction.setZodiac(prediction.getZodiac());
        newPrediction.setPredictionType(prediction.getPredictionType());
        newPrediction.setUpdatedAt(LocalDateTime.now());
        return predictionRepository.save(newPrediction);
    }

    public Page<Prediction> getAllPredictions(
        int page,
        int limit,
        String sortBy,
        String sortOrder,
        GenderType gender,
        ZodiacType zodiac,
        PredictionType predictionType) {
    
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name())
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();
        
        return predictionRepository.findPredictions(
            gender, 
            zodiac, 
            predictionType, 
            PageRequest.of(page, limit, sort));
    }

}