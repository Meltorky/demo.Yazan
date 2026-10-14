package com.Yazan.demo.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "carlos_cx_visit")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarlosCxVisite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "visit_time")
    private LocalDateTime visitTime = LocalDateTime.now();

    // Foreign key
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carlos_id", nullable = false)
    @JsonProperty("carlosId")
    private Carlos carlos;

    public long getDepartment() {
        return carlos.getId();
    }
}
