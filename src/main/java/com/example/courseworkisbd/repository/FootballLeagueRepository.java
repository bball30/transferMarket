package com.example.courseworkisbd.repository;

import com.example.courseworkisbd.entity.FootballLeague;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FootballLeagueRepository extends JpaRepository<FootballLeague, Long> {
    FootballLeague findByName(String name);
}
