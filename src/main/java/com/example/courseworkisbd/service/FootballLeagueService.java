package com.example.courseworkisbd.service;

import com.example.courseworkisbd.dto.FootballClubDto;
import com.example.courseworkisbd.dto.FootballLeagueDto;
import com.example.courseworkisbd.entity.FootballClub;
import com.example.courseworkisbd.entity.FootballLeague;
import com.example.courseworkisbd.repository.FootballLeagueRepository;

public class FootballLeagueService {
    private FootballLeagueRepository footballLeagueRepository;

    public FootballLeagueService(FootballLeagueRepository footballLeagueRepository) {
        this.footballLeagueRepository = footballLeagueRepository;
    }

    public FootballLeague findByFootballLeagueName(FootballLeagueDto footballLeagueDto) {
        return footballLeagueRepository.findByName(footballLeagueDto.getName());
    }

    public void saveFooballLeague(FootballLeagueDto footballLeagueDto) {
        FootballLeague footballLeague = new FootballLeague();
        footballLeague.setName(footballLeagueDto.getName());
        footballLeague.setCountry(footballLeagueDto.getCountry());
        footballLeague.setCount_matches(footballLeagueDto.getCount_matches());
        footballLeagueRepository.save(footballLeague);
    }
}
