package com.batteries.serviceImpl;

import com.batteries.DTO.BatteryStatistics;
import com.batteries.entities.Battery;
import com.batteries.reository.BatteryRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
@SpringBootTest

@RunWith(MockitoJUnitRunner.class)
class BatteryServiceImplTest {
    @InjectMocks
    private BatteryServiceImpl batteryService;

    @Mock
    private BatteryRepository batteryRepository;
    @Test
    void createBattery() {
        List<Battery> batteries = Arrays.asList(
                new Battery("Battery1", "12345", 2000),
                new Battery("Battery2", "67890", 1500)
        );

        when(batteryRepository.saveAll(batteries)).thenReturn(batteries);

        List<Battery> createdBatteries = batteryService.createBattery(batteries);

        assertEquals(batteries.size(), createdBatteries.size());
        assertEquals(batteries, createdBatteries);
    }

    @Test
    public void testCreateBatteryError() {
        List<Battery> batteries = Arrays.asList(
                new Battery("Battery1", "12345", 2000),
                new Battery("Battery2", "67890", 1500)
        );

        when(batteryRepository.saveAll(batteries)).thenThrow(new RuntimeException("Test Exception"));

        assertThrows(RuntimeException.class, () -> batteryService.createBattery(batteries));
    }


    @Test
    void findBatteryStatisticsInPostcodeRange() {
        String startPostcode = "10000";
        String endPostcode = "20000";
        Pageable pageable = PageRequest.of(0, 10);
        List<Battery> batteries = Arrays.asList(
                new Battery("Battery1", "12345", 2000),
                new Battery("Battery2", "67890", 1500)
        );

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Battery> batteryList;

        if (batteries.size() < startItem) {
            batteryList = new ArrayList<>();
        } else {
            int toIndex = Math.min(startItem + pageSize, batteries.size());
            batteryList = batteries.subList(startItem, toIndex);
        }

        Page<Battery> batteryPage = new PageImpl<>(batteryList, pageable, batteries.size());

        when(batteryRepository.findByPostcodeBetweenOrderByName(startPostcode, endPostcode, pageable))
                .thenReturn(batteryPage);

        BatteryStatistics batteryStatistics = batteryService.findBatteryStatisticsInPostcodeRange(startPostcode, endPostcode, pageable);
        assertThat(batteryList).isEqualTo(batteryList);
        assertThat(batteryStatistics.getTotalCapacity()).isEqualTo(batteryList.stream().mapToInt(Battery::getCapacity).sum());
        assertThat(batteryStatistics.getAverageCapacity()).isEqualTo(batteryList.stream().mapToInt(Battery::getCapacity).average().orElse(0.0));

    }

    @Test
    public void testFindBatteryStatisticsInPostcodeRangeError() {
        String startPostcode = "10001";
        String endPostcode = "20000";
        PageRequest pageable = PageRequest.of(0, 10);

        when(batteryRepository.findByPostcodeBetweenOrderByName(startPostcode, endPostcode, pageable))
                .thenThrow(new RuntimeException("Test Exception"));

        assertThrows(RuntimeException.class,
                () -> batteryService.findBatteryStatisticsInPostcodeRange(startPostcode, endPostcode, pageable));
    }
}
