package com.batteries.DTO;

import com.batteries.entities.Battery;
import lombok.Data;
import org.springframework.data.domain.Page;


@Data
public class BatteryStatistics {
    private Page<Battery> batteries;
    private int totalCapacity;
    private double averageCapacity;
}
