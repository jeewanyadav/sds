package com.batteries.controller;

import com.batteries.DTO.BatteryStatistics;
import com.batteries.entities.Battery;
import com.batteries.service.BatteryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BatteryControllerTest {
    @MockBean
    private BatteryService batteryService;

    private BatteryController batteryController;

    @BeforeEach
    public void setUp() {
        batteryController = new BatteryController(batteryService);
    }

    @Test
    public void createBattery() {
        List<Battery> batteries = new ArrayList<>();
        // Populate 'batteries' with test data

        when(batteryService.createBattery(batteries)).thenReturn(batteries);

        ResponseEntity<Object> response = batteryController.createBattery(batteries);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(batteries, response.getBody());
    }

    @Test
    public void createBatteryError() {
        List<Battery> batteries = new ArrayList<>();
        // Populate 'batteries' with test data

        when(batteryService.createBattery(batteries)).thenThrow(new RuntimeException("Test Exception"));

        ResponseEntity<Object> response = batteryController.createBattery(batteries);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred.", response.getBody());
    }

    @Test
    public void findBatteriesInPostcodeRange() {
        String startPostcode = "10001";
        String endPostcode = "20000";

        // Mock batteryService method
        BatteryStatistics batteryStatistics = new BatteryStatistics();
        when(batteryService.findBatteryStatisticsInPostcodeRange(startPostcode, endPostcode, PageRequest.of(0, 10)))
                .thenReturn(batteryStatistics);

        ResponseEntity<Object> response = batteryController.findBatteriesInPostcodeRange(startPostcode, endPostcode, 0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(batteryStatistics, response.getBody());
    }

    @Test
    public void findBatteriesInPostcodeRangeError() {
        String startPostcode = "10001";
        String endPostcode = "20000";

        // Mock batteryService method
        when(batteryService.findBatteryStatisticsInPostcodeRange(startPostcode, endPostcode, PageRequest.of(0, 10)))
                .thenThrow(new RuntimeException("Test Exception"));

        ResponseEntity<Object> response = batteryController.findBatteriesInPostcodeRange(startPostcode, endPostcode, 0, 10);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred.", response.getBody());
    }
}
