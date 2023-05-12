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
@Table(name = "player_statistics")
public class PlayerStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "won_matches", nullable = false)
    private int wonMatches;

    @Column(name = "count_matches", nullable = false)
    private int countMatches;

    @Column(name = "count_trophies", nullable = false)
    private int countTrophies;

    @Column(name = "count_assists", nullable = false)
    private int countAssists;

    @Column(name = "count_goals", nullable = false)
    private int countGoals;

    @Column(name = "red_cards", nullable = false)
    private int redCards;

    @Override
    public String toString() {
        return wonMatches +
                ":" + countMatches +
                ":" + countTrophies +
                ":" + countAssists +
                ":" + countGoals +
                ":" + redCards;
    }

    public PlayerStatistic(String stat) {
        String[] ints = stat.split(":");
        this.wonMatches = Integer.parseInt(ints[0]);
        this.countMatches = Integer.parseInt(ints[1]);
        this.countTrophies = Integer.parseInt(ints[2]);
        this.countAssists = Integer.parseInt(ints[3]);
        this.countGoals = Integer.parseInt(ints[4]);
        this.redCards = Integer.parseInt(ints[5]);
    }
}
