package com.example.courseworkisbd.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="football_clubs")
public class FootballClub {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique=true)
    private String name;

    @OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name = "coach", referencedColumnName = "id")
    private Coach coach;

    @Column(nullable = false)
    private int playersCount;

    @Column(nullable = false)
    private int budget;

    @Column
    private String currency;

    @Column(nullable = false)
    private int wonMatches;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sport_director", referencedColumnName = "id")
    private SportDirector sportDirector;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FootballLeague> footballLeagues;

}
