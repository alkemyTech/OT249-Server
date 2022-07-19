package com.alkemy.ong.controller;

import com.alkemy.ong.dtos.LoginRequestDTO;
import com.alkemy.ong.service.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth/")
@AllArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping(value = "login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO, BindingResult bindingResult) {

        return ResponseEntity.ok( loginService.login( loginRequestDTO, bindingResult ) );
    }
}
