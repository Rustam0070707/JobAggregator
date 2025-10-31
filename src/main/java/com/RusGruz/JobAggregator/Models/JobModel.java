package com.RusGruz.JobAggregator.Models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.aot.generate.GeneratedTypeReference;

import java.time.LocalDate;

@Data
@Entity
public class JobModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String company;
    private String source ;
    @Column(length = 2048)
    private String url;
    private LocalDate postDate;
    private String Location;


}
