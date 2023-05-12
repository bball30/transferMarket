package com.example.courseworkisbd.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FootballLeagueDto {
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String country;

    @NotEmpty
    private int count_matches;
}
