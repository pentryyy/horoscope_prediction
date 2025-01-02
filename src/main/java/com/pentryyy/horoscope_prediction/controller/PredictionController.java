package com.pentryyy.horoscope_prediction.controller;

import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pentryyy.horoscope_prediction.enumeration.PredictionType;
import com.pentryyy.horoscope_prediction.model.Prediction;
import com.pentryyy.horoscope_prediction.service.PredictionService;

@RestController
@RequestMapping("/predictions")
public class PredictionController {

    @Autowired
    private PredictionService predictionService;

    @PostMapping("/create-prediction")
    public ResponseEntity<?> createPrediction(@RequestBody Prediction prediction) {
        predictionService.createPrediction(prediction);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", prediction.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(jsonObject.toString());
    }

    @DeleteMapping("/delete-prediction/{id}")
    public ResponseEntity<?> deletePrediction(@PathVariable Long id) {
        if (!predictionService.existsById(id)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Предсказание не найдено");
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .body(jsonObject.toString());   
        }
        
        predictionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-prediction/{id}")
    public ResponseEntity<?> updatePrediction(@PathVariable Long id,
                                              @RequestBody Prediction prediction) {                                            
        
        Optional<Prediction> optionalPrediction = predictionService.findById(id);

        if (!optionalPrediction.isPresent()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Предсказание не найдено");
           
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .body(jsonObject.toString());   
        }

        Prediction newPrediction = optionalPrediction.get();
        predictionService.updatePrediction(newPrediction, prediction);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", newPrediction.getId());
        return ResponseEntity.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(jsonObject.toString());   
    }

    @GetMapping("/get-prediction/{id}")
    public ResponseEntity<?> getPredictionById(@PathVariable Long id) {
        Optional<Prediction> optionalPrediction = predictionService.findById(id);

        if (!optionalPrediction.isPresent()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Предсказание не найдено");
           
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .body(jsonObject.toString());   
        }

        Prediction prediction = optionalPrediction.get();
        return ResponseEntity.ok(prediction);
    }

    @GetMapping("/get-all-predictions")
    public ResponseEntity<Page<Prediction>> getAllPredictions(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int limit,
                                                              @RequestParam(required = false) PredictionType type) {
        
        Page<Prediction> predictions = predictionService.getAllPredictions(page, limit, type);
        return ResponseEntity.ok(predictions);
    }
}