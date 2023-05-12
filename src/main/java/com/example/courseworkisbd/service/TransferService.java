package com.example.courseworkisbd.service;

import com.example.courseworkisbd.dto.TransferRequestDto;
import com.example.courseworkisbd.entity.*;
import com.example.courseworkisbd.repository.PlayerRepository;
import com.example.courseworkisbd.repository.TransferRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TransferService {
    private PlayerRepository playerRepository;
    private TransferRepository transferRepository;
    private FootballClubService footballClubService;
    private PlayerService playerService;

    public TransferService(PlayerRepository playerRepository, TransferRepository transferRepository, FootballClubService footballClubService, PlayerService playerService) {
        this.playerRepository = playerRepository;
        this.transferRepository = transferRepository;
        this.footballClubService = footballClubService;
        this.playerService = playerService;
    }

    public List<TransferRequestDto> findAllTransfersDto() {
        List<TransferRequest> transferRequests = transferRepository.findAll();
        return transferRequests.stream().map((transferRequest) -> convertEntityToDto(transferRequest))
                .collect(Collectors.toList());
    }

    public TransferRequestDto getTransferDtoById(long id) {
        return convertEntityToDto(transferRepository.getById(id));
    }


    public void makeTransfer(TransferRequestDto transferRequestDto, FootballClub footballClub) {
        System.out.println(transferRequestDto.getFootballClub());
        FootballClub footballClubTo = footballClubService.findFootballClubByName(transferRequestDto.getFootballClub());

        String buyerCurrency = footballClub.getCurrency();
        String ownerCurrency = footballClubTo.getCurrency();

        System.out.println(footballClubTo.toString());
        System.out.println(footballClub.toString());
        if (Objects.equals(buyerCurrency, "RUB")) {
            int payment = convertToRub(transferRequestDto.getValue(), ownerCurrency);
            footballClub.setBudget(footballClub.getBudget() - payment);
            footballClub.setPlayersCount(footballClub.getPlayersCount() + 1);

            footballClubTo.setBudget(footballClubTo.getBudget() + transferRequestDto.getValue());
            footballClubTo.setPlayersCount(footballClubTo.getPlayersCount() - 1);
        } else if (Objects.equals(buyerCurrency, "USD")) {
            int payment = convertToUsd(transferRequestDto.getValue(), ownerCurrency);
            footballClub.setBudget(footballClub.getBudget() - payment);
            footballClub.setPlayersCount(footballClub.getPlayersCount() + 1);

            footballClubTo.setBudget(footballClubTo.getBudget() + transferRequestDto.getValue());
            footballClubTo.setPlayersCount(footballClubTo.getPlayersCount() - 1);
        } else if (Objects.equals(buyerCurrency, "EUR")) {
            int payment = convertToEur(transferRequestDto.getValue(), ownerCurrency);
            footballClub.setBudget(footballClub.getBudget() - payment);
            footballClub.setPlayersCount(footballClub.getPlayersCount() + 1);

            footballClubTo.setBudget(footballClubTo.getBudget() + transferRequestDto.getValue());
            footballClubTo.setPlayersCount(footballClubTo.getPlayersCount() - 1);
        }

        playerService.findPlayerById(transferRequestDto.getPlayerId()).setFootballClub(footballClub);

        transferRepository.deleteById(transferRequestDto.getId());
    }

    private int convertToRub(int value, String currency) {
        if (Objects.equals(currency, "RUB")) {
            return value;
        } else if (Objects.equals(currency, "USD")) {
            return value * 65;
        } else if (Objects.equals(currency, "EUR")) {
            return value * 70;
        } else return 0;
    }

    private int convertToUsd(int value, String currency) {
        if (Objects.equals(currency, "USD")) {
            return value;
        } else if (Objects.equals(currency, "RUB")) {
            return value * 65;
        } else if (Objects.equals(currency, "EUR")) {
            double result = value * 1.07;
            return (int) result;
        } else return 0;
    }

    private int convertToEur(int value, String currency) {
        if (Objects.equals(currency, "EUR")) {
            return value;
        } else if (Objects.equals(currency, "USD")) {
            double result = value * 0.93;
            return (int) result;
        } else if (Objects.equals(currency, "RUB")) {
            return value * 70;
        } else return 0;
    }

    public boolean checkBalance(int value1, String currency1, int value2, String currency2) {
        int value1Rub = convertToRub(value1, currency1);
        int value2Rub = convertToRub(value2, currency2);
        return value1Rub >= value2Rub;
    }

    private TransferRequestDto convertEntityToDto(TransferRequest transferRequest) {
        TransferRequestDto transferRequestDto = new TransferRequestDto();
        transferRequestDto.setPlayerId(transferRequest.getPlayer().getId());
        transferRequestDto.setId(transferRequest.getId());
        transferRequestDto.setValue(transferRequest.getValue());
        transferRequestDto.setCurrency(transferRequest.getCurrency());
        transferRequestDto.setPosition(transferRequest.getPlayer().getPosition());
        transferRequestDto.setName(transferRequest.getPlayer().getName());
        transferRequestDto.setSurname(transferRequest.getPlayer().getSurname());
        transferRequestDto.setFootballClub(transferRequest.getFootballClub().getName());
        transferRequestDto.setAge(transferRequest.getPlayer().getAge());
        return transferRequestDto;
    }

    public void saveTransferRequest(TransferRequestDto transferRequestDto, SportDirector sportDirector) {
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setFootballClub(footballClubService.findFootballClubBySportDirector(sportDirector));
        transferRequest.setPlayer(playerRepository.findByNameAndSurname(transferRequestDto.getName(), transferRequestDto.getSurname()));
        transferRequest.setValue(transferRequestDto.getValue());
        transferRequest.setCurrency(footballClubService.findFootballClubBySportDirector(sportDirector).getCurrency());
        transferRepository.save(transferRequest);
    }

}
