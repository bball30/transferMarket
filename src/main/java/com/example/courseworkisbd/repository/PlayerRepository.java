package com.example.courseworkisbd.repository;

import com.example.courseworkisbd.entity.FootballClub;
import com.example.courseworkisbd.entity.Player;
import com.example.courseworkisbd.entity.SportDirector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    Player findByName(String name);

    Player findByNameAndSurname(String name, String surname);

    List<Player> findAllByFootballClub_SportDirector(SportDirector sportDirector);
}
