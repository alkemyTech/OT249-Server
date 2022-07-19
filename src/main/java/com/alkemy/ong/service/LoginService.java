package com.alkemy.ong.service;

import com.alkemy.ong.dtos.LoginRequestDTO;
import org.springframework.validation.BindingResult;

import java.util.Map;

public interface LoginService {

    Map<String,Object> login(LoginRequestDTO loginRequestDTO, BindingResult bindingResult);
}
