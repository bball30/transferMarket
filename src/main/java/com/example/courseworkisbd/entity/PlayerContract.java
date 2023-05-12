package com.example.courseworkisbd.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "player_contracts")
public class PlayerContract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int value;

    @Column
    private int coast_id;

    @Column
    private Timestamp startDate;

    @Column
    private Timestamp endDate;

    @Column
    private int salary;

    @Column
    private int compensationValue;
}
