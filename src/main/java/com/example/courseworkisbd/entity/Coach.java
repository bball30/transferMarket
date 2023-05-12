package com.example.courseworkisbd.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="coaches")
public class Coach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private int age;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "football_club", referencedColumnName = "id")
    private FootballClub footballClub;

    @Column
    private int rating;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "statistic", referencedColumnName = "id")
    private CoachStatistic sportDirector;
}
