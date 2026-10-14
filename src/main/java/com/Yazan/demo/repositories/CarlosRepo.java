package com.Yazan.demo.repositories;

import com.Yazan.demo.entities.Carlos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarlosRepo extends JpaRepository<Carlos, Long> {
    Optional<Carlos> findByPhoneNumber(String phoneNumber);

    Optional<Carlos> findByQrCodeIdentifier(String phoneNumber);
}
