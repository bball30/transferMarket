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
@Table(name="transfer_requests")
public class TransferRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int value;

    @Column
    private String currency;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "club_from", referencedColumnName = "id")
    private FootballClub footballClub;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player", referencedColumnName = "id")
    private Player player;
}
