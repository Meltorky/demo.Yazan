package com.Yazan.demo.repositories;

import com.Yazan.demo.entities.CarlosCxVisite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarlosCxVisiteRepo extends JpaRepository<CarlosCxVisite, Long> {
    List<CarlosCxVisite> findByCarlosId(Long carlosId);

}
