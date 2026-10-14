package com.Yazan.demo.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "carlos_cx_gift")
@AllArgsConstructor
@NoArgsConstructor
public class CarlosCxGift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gift_name", nullable = false, length = 100)
    private String giftName = "Free Drink";

    @Column(name = "is_redeemed")
    private boolean redeemed = false;

    @Column(name = "redeeming_time", nullable = true)
    private LocalDateTime redeemingDate;

    // Foreign key
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carlos_id", nullable = false)
    @JsonProperty("carlosId")
    private Carlos carlos;

    public long getDepartment() {
        return carlos.getId();
    }
}
