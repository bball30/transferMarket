package com.example.courseworkisbd.dto;

import com.example.courseworkisbd.entity.FootballClub;
import com.example.courseworkisbd.entity.PlayerContract;
import com.example.courseworkisbd.entity.PlayerStatistic;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDto {
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;

    private int age;

    private String position;

    private String playerStatistic;

    private String playerContract;

    private int rating;

    private String footballClub;
}
