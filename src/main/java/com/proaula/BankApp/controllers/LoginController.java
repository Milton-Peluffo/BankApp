package com.proaula.BankApp.controllers;

import com.proaula.BankApp.dtos.LoginDTO;
import com.proaula.BankApp.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@CrossOrigin("*") // por si haces pruebas desde el frontend
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public String login(@RequestBody LoginDTO loginDTO) {
        return loginService.login(loginDTO);
    }
}
