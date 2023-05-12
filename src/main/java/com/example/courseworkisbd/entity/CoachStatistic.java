package com.example.courseworkisbd.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coach_statistics")
public class CoachStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "won_matches", nullable = false)
    private int wonMatches;

    @Column(name = "count_matches", nullable = false)
    private int countMatches;

    @Column(name = "count_trophies", nullable = false)
    private int countTrophies;
}
