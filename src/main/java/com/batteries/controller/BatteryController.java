package com.batteries.controller;

import com.batteries.DTO.BatteryStatistics;
import com.batteries.entities.Battery;
import com.batteries.service.BatteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/batteries")
public class BatteryController {
    private final BatteryService batteryService;

    @Autowired
    public BatteryController(BatteryService batteryService) {
        this.batteryService = batteryService;
    }

    @PostMapping
    public ResponseEntity<Object> createBattery(@RequestBody List<Battery> batteries) {
        try {
            List<Battery> createdBatteries = batteryService.createBattery(batteries);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBatteries);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Object> findBatteriesInPostcodeRange(
            @RequestParam String startPostcode,
            @RequestParam String endPostcode,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            BatteryStatistics batteryPage = batteryService.findBatteryStatisticsInPostcodeRange(startPostcode, endPostcode, pageable);
            return ResponseEntity.ok(batteryPage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }
}

