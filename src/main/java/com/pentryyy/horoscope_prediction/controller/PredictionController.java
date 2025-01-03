package com.pentryyy.horoscope_prediction.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pentryyy.horoscope_prediction.enumeration.GenderType;
import com.pentryyy.horoscope_prediction.enumeration.PredictionType;
import com.pentryyy.horoscope_prediction.enumeration.ZodiacType;
import com.pentryyy.horoscope_prediction.model.Prediction;
import com.pentryyy.horoscope_prediction.service.PredictionService;

@RestController
@RequestMapping("/predictions")
public class PredictionController {

    @Autowired
    private PredictionService predictionService;

    @PostMapping("/create-prediction")
    public ResponseEntity<?> createPrediction(@RequestBody Prediction request) {
        Prediction prediction = predictionService.createPrediction(request);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", prediction.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(jsonObject.toString());
    }

    @DeleteMapping("/delete-prediction/{id}")
    public ResponseEntity<?> deletePrediction(@PathVariable Long id) {
        predictionService.deletePrediction(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-prediction/{id}")
    public ResponseEntity<?> updatePrediction(
        @PathVariable Long id,
        @RequestBody Prediction request) {    
            
        predictionService.updatePrediction(id, request);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "Данные предсказания обновлены");
        return ResponseEntity.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(jsonObject.toString());   
    }

    @GetMapping("/get-prediction/{id}")
    public ResponseEntity<?> getPredictionById(@PathVariable Long id) {
        Prediction prediction = predictionService.findById(id);
        return ResponseEntity.ok(prediction);
    }

    @GetMapping("/get-all-predictions")
    public ResponseEntity<Page<Prediction>> getAllPredictions(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int limit,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "ASC") String sortOrder,
        @RequestParam(required = false) GenderType gender,
        @RequestParam(required = false) ZodiacType zodiac,
        @RequestParam(required = false) PredictionType predictionType) {
        
        Page<Prediction> predictions = predictionService.getAllPredictions(
            page, 
            limit, 
            sortBy, 
            sortOrder, 
            gender, 
            zodiac, 
            predictionType);
        return ResponseEntity.ok(predictions);
    }
}
