package com.bank.backend.controller;

import com.bank.backend.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bank")
public class BankController {

    private final BankService bankService;

}
