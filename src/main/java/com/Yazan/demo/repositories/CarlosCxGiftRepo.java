package com.Yazan.demo.repositories;

import com.Yazan.demo.entities.CarlosCxGift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarlosCxGiftRepo extends JpaRepository<CarlosCxGift, Long> {
}
