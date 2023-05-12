package com.example.courseworkisbd.service;

import com.example.courseworkisbd.dto.FootballClubDto;
import com.example.courseworkisbd.dto.PlayerDto;
import com.example.courseworkisbd.entity.*;
import com.example.courseworkisbd.repository.FootballClubRepository;
import com.example.courseworkisbd.repository.PlayerRepository;
import com.example.courseworkisbd.service.impl.UserServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerService {
    private PlayerRepository playerRepository;
    private UserServiceImpl userService;
    private FootballClubService footballClubService;

    public PlayerService(PlayerRepository playerRepository, UserServiceImpl userService, FootballClubService footballClubService) {
        this.playerRepository = playerRepository;
        this.userService = userService;
        this.footballClubService = footballClubService;
    }

    public Player findByPlayerName(PlayerDto playerDto) {
        return playerRepository.findByName(playerDto.getName());
    }

    public List<PlayerDto> findAllPlayersDtoBySportDirector() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        SportDirector sportDirector = userService.getSportDirectorByEmail(login);
        List<Player> players = playerRepository.findAllByFootballClub_SportDirector(sportDirector);
        return players.stream().map((player) -> convertEntityToDto(player))
                .collect(Collectors.toList());
    }

    public Player findPlayerById(long id){
        return playerRepository.getById(id);
    }

    public List<PlayerDto> findAllPlayersDto() {
        List<Player> players = playerRepository.findAll();
        return players.stream().map((player) -> convertEntityToDto(player))
                .collect(Collectors.toList());
    }

    private PlayerDto convertEntityToDto(Player player) {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setName(player.getName());
        playerDto.setSurname(player.getSurname());
        playerDto.setAge(player.getAge());
        playerDto.setPosition(player.getPosition());
        playerDto.setPlayerStatistic(player.getPlayerStatistic().toString());
        playerDto.setPlayerContract(String.valueOf(player.getPlayerContract().getSalary()));
        playerDto.setRating(player.getRating());
        playerDto.setFootballClub(player.getFootballClub().getName());

        return playerDto;
    }

    public void savePlayerBySportDirector(PlayerDto playerDto,SportDirector sportDirector) {
        Player player = new Player();
        player.setName(playerDto.getName());
        player.setSurname(playerDto.getSurname());
        player.setAge(playerDto.getAge());
        player.setPosition(playerDto.getPosition());
        PlayerStatistic playerStatistic = new PlayerStatistic(playerDto.getPlayerStatistic());
        player.setPlayerStatistic(playerStatistic);
        PlayerContract playerContract = new PlayerContract();
        playerContract.setSalary(Integer.parseInt(playerDto.getPlayerContract()));
        player.setPlayerContract(playerContract);
        player.setRating(playerDto.getRating());
        FootballClub footballClub = footballClubService.findFootballClubBySportDirector(sportDirector);
        footballClub.setPlayersCount(footballClub.getPlayersCount() + 1);
        player.setFootballClub(footballClub);
        playerRepository.save(player);
    }

}
