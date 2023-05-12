package com.example.courseworkisbd.controller;

import com.example.courseworkisbd.dto.TransferRequestDto;
import com.example.courseworkisbd.entity.FootballClub;
import com.example.courseworkisbd.entity.SportDirector;
import com.example.courseworkisbd.service.FootballClubService;
import com.example.courseworkisbd.service.TransferService;
import com.example.courseworkisbd.service.impl.UserServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class TransferController {
    private TransferService transferService;
    private UserServiceImpl userService;
    private FootballClubService footballClubService;

    public TransferController(TransferService transferService, UserServiceImpl userService, FootballClubService footballClubService) {
        this.transferService = transferService;
        this.userService = userService;
        this.footballClubService = footballClubService;
    }

    @GetMapping("/transfers")
    public String transfers(Model model) {
        List<TransferRequestDto> transferRequestsDto = transferService.findAllTransfersDto();
        model.addAttribute("transfers", transferRequestsDto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        SportDirector sportDirector = userService.getSportDirectorByEmail(login);
        if (footballClubService.findFootballClubBySportDirector(sportDirector) == null) {
            return "index";
        }
        model.addAttribute("clubBalance", footballClubService.findFootballClubBySportDirector(sportDirector).getBudget());
        model.addAttribute("clubCurrency", footballClubService.findFootballClubBySportDirector(sportDirector).getCurrency());
        return "transfers";
    }

    @GetMapping("/transfer_add")
    public String transferAdd(Model model) {
        TransferRequestDto transferRequestDto = new TransferRequestDto();
        model.addAttribute("transferDto", transferRequestDto);
        return "transfer_add";
    }

    @PostMapping("/transfer_make/{id}")
    public String transferMake(@PathVariable(value = "id") long id, Model model) {
        TransferRequestDto transferRequestDto = transferService.getTransferDtoById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        SportDirector sportDirector = userService.getSportDirectorByEmail(login);
        FootballClub footballClub = footballClubService.findFootballClubBySportDirector(sportDirector);
        System.out.println(footballClub + " " + footballClub.getBudget() + " " +transferRequestDto.getValue() + " ");
        if (footballClub != null && footballClub.getBudget() >= transferRequestDto.getValue()) {
            transferService.makeTransfer(transferRequestDto, footballClub);
        }
        return "transfers";
    }

   /* @PostMapping("/transfer/make")
    public String transferMake(@Valid @ModelAttribute("transferMakeDto") TransferRequestDto transferRequestDto,
                               BindingResult result, Model model) {
        System.out.println(transferRequestDto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        SportDirector sportDirector = userService.getSportDirectorByEmail(login);
        FootballClub footballClub = footballClubService.findFootballClubBySportDirector(sportDirector);
        if (footballClub != null && transferService.checkBalance(footballClub.getBudget(), footballClub.getCurrency(), transferRequestDto.getValue(), transferRequestDto.getCurrency())) {
            transferService.makeTransfer(transferRequestDto, footballClub);
        }
        return "transfers";
    }
*/
    @PostMapping("/transfer/add")
    public String transferAdd(@Valid @ModelAttribute("transferDto") TransferRequestDto transferRequestDto,
                              BindingResult result, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        SportDirector sportDirector = userService.getSportDirectorByEmail(login);
        if (footballClubService.findFootballClubBySportDirector(sportDirector) != null) {
            transferService.saveTransferRequest(transferRequestDto, sportDirector);
        }
        return "index";
    }
}
