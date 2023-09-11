package com.batteries.serviceImpl;

import com.batteries.DTO.BatteryStatistics;
import com.batteries.entities.Battery;
import com.batteries.reository.BatteryRepository;
import com.batteries.service.BatteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatteryServiceImpl implements BatteryService {
    private final BatteryRepository batteryRepository;

    @Autowired
    public BatteryServiceImpl(BatteryRepository batteryRepository) {
        this.batteryRepository = batteryRepository;
    }

    @Override
    public List<Battery> createBattery(List<Battery> batteries) {
        try {
            int totalBatteries = batteries.size();
            int batchSize = 100;
            int index = 0;
            while (index < totalBatteries) {
                int endIndex = Math.min(index + batchSize, totalBatteries);
                List<Battery> batch = batteries.subList(index, endIndex);
                batteryRepository.saveAll(batch);
                index += batchSize;
            }
            return batteryRepository.saveAll(batteries);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create battery.", e);
        }
    }

    @Override
    public BatteryStatistics findBatteryStatisticsInPostcodeRange(String startPostcode, String endPostcode, Pageable pageable) {
        try {
            Page<Battery> batteriesInRange = batteryRepository.findByPostcodeBetweenOrderByName(startPostcode, endPostcode, pageable);
            int totalCapacity = batteriesInRange.stream().mapToInt(Battery::getCapacity).sum();
            double averageCapacity = batteriesInRange.stream().mapToDouble(Battery::getCapacity).average().orElse(0.0);

            BatteryStatistics statistics = new BatteryStatistics();
            statistics.setBatteries(batteriesInRange);
            statistics.setTotalCapacity(totalCapacity);
            statistics.setAverageCapacity(averageCapacity);

            return statistics;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve battery statistics.", e);
        }
    }
}

