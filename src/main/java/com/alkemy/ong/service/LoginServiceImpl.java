package com.alkemy.ong.service;

import com.alkemy.ong.dtos.LoginRequestDTO;
import com.alkemy.ong.model.User;
import com.alkemy.ong.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final BCryptPasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Override
    public Map<String, Object> login(LoginRequestDTO loginRequestDTO, BindingResult bindingResult) {

        if (bindingResult.hasFieldErrors())
            return Map.of( "ok", false );

        Optional<User> byEmail = userRepository.findByEmail( loginRequestDTO.getEmail() );
        if (byEmail.isPresent() && passwordEncoder.matches( loginRequestDTO.getPassword(), byEmail.get().getPassword() ))
            return Map.of( "ok", byEmail.get() );
        return Map.of( "ok", Boolean.FALSE );
    }
}
