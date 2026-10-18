package com.Yazan.demo.controllers;

import com.Yazan.demo.dto.AdminLoginDTO;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://loyalty-card-delta-jet.vercel.app"
})
@RestController
@RequestMapping("/carlos")
public class CarlosController {
    // Hardcoded constants — no application.properties needed
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin2026";

    @Autowired
    private CarlosRepo carlosRepo;

    // Customer Endpoints
    @Autowired
    private CarlosCxVisiteRepo carlosCxVisiteRepo;
    @Autowired
    private CarlosCxGiftRepo carlosCxGiftRepo;

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


    @PostMapping("/login")
    public ResponseEntity<GlobalResponse<Carlos>> login(@RequestBody @Valid CarlosCreate c) {

        Optional<Carlos> carlos = carlosRepo.findByPhoneNumber(c.phoneNumber());
        if (carlos.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(GlobalResponse
                            .success(carlos.get(), "created successfully"));
        } else {

            // create cx endpoint
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
            throw new RuntimeException("You need to Redeem your Gift First");

        Carlos updatedCarlos = carlosRepo.save(c);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(GlobalResponse
                        .success(updatedCarlos, "updated successfully"));
    }

    @GetMapping("get-cx-gifts/{qrCodeIdentifier}")
    public ResponseEntity<GlobalResponse<List<CarlosCxGift>>> getCxGifts(
            @PathVariable String qrCodeIdentifier) {

        Carlos carlos = carlosRepo.findByQrCodeIdentifier(qrCodeIdentifier)
                .orElseThrow(() -> new RuntimeException("Carlos not found with QR Code: " + qrCodeIdentifier));

        // Get the gifts list directly from the Carlos object
        List<CarlosCxGift> gifts = carlosCxGiftRepo.findByCarlosId(carlos.getId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(GlobalResponse.success(gifts, "List published successfully"));
    }

    @PostMapping("redeem-gift/{carlosId}")
    public ResponseEntity<GlobalResponse<Carlos>> redeemCxGift(@PathVariable Long carlosId) {

        Carlos carlos = carlosRepo.findById(carlosId)
                .orElseThrow(() -> new RuntimeException("Carlos not found with ID: " + carlosId));

        if (carlos.getStamps() != 5)
            throw new RuntimeException("You did not reach req visits");

        carlos.setStamps(0);
        carlos.setLevel(carlos.getLevel() + 1);

        CarlosCxGift gift = carlosCxGiftRepo.findFirstByCarlosAndRedeemedFalse(carlos)
                .orElseThrow(() -> new RuntimeException("Carlos not found"));
        gift.setRedeemed(true);
        gift.setRedeemingDate(LocalDateTime.now());

        carlosRepo.save(carlos);
        carlosCxGiftRepo.save(gift);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(GlobalResponse.success(carlos, "Redeemed Successfully"));
    }

    @PostMapping("/admin-login")
    public ResponseEntity<GlobalResponse<Void>> login(@Valid @RequestBody AdminLoginDTO loginDTO) {

        if (ADMIN_USERNAME.equals(loginDTO.userName()) && ADMIN_PASSWORD.equals(loginDTO.password())) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(GlobalResponse.success("Login successful!"));
        }
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(GlobalResponse.success("Login successful!"));
    }
}
// need to check first if there is only one is false and the rest are true and make sure the stamp is = 5
// remove eager loading very annoying
// need to redeem first before the 5+1 visit
