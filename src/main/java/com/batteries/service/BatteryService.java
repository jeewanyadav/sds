package com.batteries.service;

import com.batteries.DTO.BatteryStatistics;
import com.batteries.entities.Battery;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BatteryService {
    List<Battery> createBattery(List<Battery> battery);

    BatteryStatistics findBatteryStatisticsInPostcodeRange(String startPostcode, String endPostcode, Pageable pageable);

}

