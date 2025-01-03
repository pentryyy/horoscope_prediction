package com.pentryyy.horoscope_prediction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pentryyy.horoscope_prediction.enumeration.GenderType;
import com.pentryyy.horoscope_prediction.enumeration.PredictionType;
import com.pentryyy.horoscope_prediction.enumeration.ZodiacType;
import com.pentryyy.horoscope_prediction.exception.PredictionDoesNotExistException;
import com.pentryyy.horoscope_prediction.model.Prediction;
import com.pentryyy.horoscope_prediction.repository.PredictionRepository;

import java.time.LocalDateTime;

@Service
public class PredictionService {

    @Autowired
    private PredictionRepository predictionRepository;

    public Prediction createPrediction(Prediction request) {
        request.setCreatedAt(LocalDateTime.now());
        request.setUpdatedAt(LocalDateTime.now());
        return predictionRepository.save(request);
    }

    public Prediction findById(Long id) {
        return predictionRepository.findById(id)
                                   .orElseThrow(() -> new PredictionDoesNotExistException(id));

    }

    public void updatePrediction(
        Long id, 
        Prediction request) {
        
        Prediction prediction = findById(id);

        prediction.setPredictionText(request.getPredictionText());
        prediction.setGender(request.getGender());
        prediction.setZodiac(request.getZodiac());
        prediction.setPredictionType(request.getPredictionType());
        prediction.setUpdatedAt(LocalDateTime.now());
        predictionRepository.save(prediction);
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

    public void deletePrediction(Long id) {
        if (!predictionRepository.existsById(id)) {
            throw new PredictionDoesNotExistException(id);
        }

        predictionRepository.deleteById(id);
    }
}
