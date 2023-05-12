package com.example.courseworkisbd.service;

import com.example.courseworkisbd.dto.FootballClubDto;
import com.example.courseworkisbd.dto.TransferRequestDto;
import com.example.courseworkisbd.entity.*;
import com.example.courseworkisbd.repository.FootballClubRepository;
import com.example.courseworkisbd.service.impl.UserServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class FootballClubService {
    private FootballClubRepository footballClubRepository;
    private UserServiceImpl userService;

    public FootballClubService(FootballClubRepository footballClubRepository, UserServiceImpl userService) {
        this.footballClubRepository = footballClubRepository;
        this.userService = userService;
    }

    public FootballClub findFootballClubByName(String name) {
        return footballClubRepository.findByName(name);
    }
    public FootballClub findFootballClubBySportDirector(SportDirector sportDirector) {
        return footballClubRepository.findFootballClubBySportDirector(sportDirector);
    }

    public FootballClub getById(long id) {
        return footballClubRepository.getById(id);
    }

    public void saveFootballClub(FootballClubDto footballClubDto) {
        FootballClub footballClub = new FootballClub();
        footballClub.setName(footballClubDto.getName());
        footballClub.setBudget(footballClubDto.getBudget());
        footballClub.setCurrency(footballClubDto.getCurrency());
        footballClub.setWonMatches(footballClubDto.getWonMatches());
        FootballLeague footballLeague = new FootballLeague();
        footballLeague.setName(footballClubDto.getLeague());
        List<FootballLeague> footballLeagues = new ArrayList<>();
        footballLeagues.add(footballLeague);
        footballClub.setFootballLeagues(footballLeagues);
        Coach coach = new Coach();
        coach.setName(footballClubDto.getCoach());
        footballClub.setCoach(coach);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        SportDirector sportDirector = userService.getSportDirectorByEmail(login);
        footballClub.setSportDirector(sportDirector);
        footballClubRepository.save(footballClub);
        sportDirector.setFootballClub(footballClub);
        //userService.findByEmail(sportDirector.getEmail()).setFootballClub(footballClub);
        userService.updateUser(sportDirector, footballClub);
    }

    public List<FootballClubDto> findAllFootballClubsDto() {
        List<FootballClub> footballClubs = footballClubRepository.findAll();
        return footballClubs.stream().map((club) -> convertEntityToDto(club))
                .collect(Collectors.toList());
    }

    private FootballClubDto convertEntityToDto(FootballClub footballClub) {
        FootballClubDto footballClubDto = new FootballClubDto();
        footballClubDto.setName(footballClub.getName());
        footballClubDto.setLeague(footballClub.getFootballLeagues().get(0).getName());
        footballClubDto.setBudget(footballClub.getBudget());
        footballClubDto.setCurrency(footballClub.getCurrency());
        footballClubDto.setWonMatches(footballClub.getWonMatches());
        footballClubDto.setSportDirector(footballClub.getSportDirector().getName());
        footballClubDto.setCoach(footballClub.getCoach().getName());
        footballClubDto.setPlayersCount(footballClub.getPlayersCount());
        return footballClubDto;
    }
}
