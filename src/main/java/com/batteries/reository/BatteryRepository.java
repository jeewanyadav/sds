package com.batteries.reository;

import com.batteries.entities.Battery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BatteryRepository extends JpaRepository<Battery, Long> {
    Page<Battery> findByPostcodeBetweenOrderByName(String startPostcode, String endPostcode, Pageable pageable);
}

