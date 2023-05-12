package com.example.courseworkisbd.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequestDto {
    private long id;

    private long playerId;

    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;

    private String position;



    private int age;


    private String footballClub;

    private int value;

    private String currency;

    @Override
    public String toString() {
        return "TransferRequestDto{" +
                "id=" + id +
                ", playerId=" + playerId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", position='" + position + '\'' +
                ", age=" + age +
                ", footballClub='" + footballClub + '\'' +
                ", value=" + value +
                ", currency='" + currency + '\'' +
                '}';
    }
}
