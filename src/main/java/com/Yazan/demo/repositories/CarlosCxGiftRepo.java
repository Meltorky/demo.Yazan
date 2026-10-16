package com.Yazan.demo.repositories;

import com.Yazan.demo.entities.Carlos;
import com.Yazan.demo.entities.CarlosCxGift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarlosCxGiftRepo extends JpaRepository<CarlosCxGift, Long> {

    Optional<CarlosCxGift> findFirstByCarlosAndRedeemedFalse(Carlos carlos);

    List<CarlosCxGift> findByCarlosId(Long carlosId);
}
