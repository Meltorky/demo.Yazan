package com.Yazan.demo.controllers;

import com.Yazan.demo.dto.CarlosCreate;
import com.Yazan.demo.entities.Carlos;
import com.Yazan.demo.entities.CarlosCxGift;
import com.Yazan.demo.entities.CarlosCxVisite;
import com.Yazan.demo.repositories.CarlosCxGiftRepo;
import com.Yazan.demo.repositories.CarlosCxVisiteRepo;
import com.Yazan.demo.repositories.CarlosRepo;
import com.Yazan.demo.shared.GlobalResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carlos")
public class CarlosController {
    @Autowired
    private CarlosRepo carlosRepo;
    @Autowired
    private CarlosCxVisiteRepo carlosCxVisiteRepo;
    @Autowired
    private CarlosCxGiftRepo carlosCxGiftRepo;

    // Customer Endpoints

    @GetMapping("/get-customer/{phoneNumber}")
    public ResponseEntity<GlobalResponse<Carlos>> getCustomer(
            @PathVariable
            @Size(min = 11, max = 11, message = "Phone number must be exactly 11 characters")
            @Pattern(regexp = "^01\\d{9}$", message = "Phone number must start with '01' and be exactly 11 digits long.")
            String phoneNumber) {

        Carlos c = carlosRepo.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("Carlos not found with phone number: " + phoneNumber));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(GlobalResponse
                        .success(c, "created successfully"));
    }

    @PostMapping("/create-customer")
    public ResponseEntity<GlobalResponse<Carlos>> createCustomer(@RequestBody @Valid CarlosCreate c) {
        Carlos newCarlos = new Carlos();
        newCarlos.setPhoneNumber(c.phoneNumber());
        newCarlos.setName(c.firstName() + " " + c.lastName());
        newCarlos.setLevel(1);
        newCarlos.setStamps(1);

        carlosRepo.save(newCarlos);

        CarlosCxVisite carlosCxVisite = new CarlosCxVisite();
        carlosCxVisite.setCarlos(newCarlos);

        carlosCxVisiteRepo.save(carlosCxVisite);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(GlobalResponse
                        .success(carlosRepo.save(newCarlos), "created successfully"));


    }


    // ADMIN Endpoints

    @PostMapping("add-visit/{qrCodeIdentifier}")
    public ResponseEntity<GlobalResponse<Carlos>> addVisit(@PathVariable String qrCodeIdentifier) {

        Carlos c = carlosRepo.findByQrCodeIdentifier(qrCodeIdentifier)
                .orElseThrow(() -> new RuntimeException("Carlos not found with QR Code: " + qrCodeIdentifier));

        if (c.getStamps() < 4) {
            c.setStamps(c.getStamps() + 1);
        } else if (c.getStamps() == 4) {
            c.setStamps(c.getStamps() + 1);
            CarlosCxGift gift = new CarlosCxGift();
            gift.setCarlos(c);
            carlosCxGiftRepo.save(gift);
        } else
            c.setStamps(0);

        Carlos updatedCarlos = carlosRepo.save(c);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(GlobalResponse
                        .success(updatedCarlos, "updated successfully"));
    }
}
