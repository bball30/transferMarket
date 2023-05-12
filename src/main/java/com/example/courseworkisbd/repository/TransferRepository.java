package com.example.courseworkisbd.repository;

import com.example.courseworkisbd.entity.TransferRequest;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransferRepository extends JpaRepository<TransferRequest, Long> {

}
