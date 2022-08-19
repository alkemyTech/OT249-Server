package com.alkemy.ong.controller;

import com.alkemy.ong.dto.RoleDto;
import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.model.Role;
import com.alkemy.ong.model.User;
import com.alkemy.ong.service.IRoleService;
import com.alkemy.ong.service.UserService;
import com.alkemy.ong.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UserController.class})
class UserControllerTest {


    @MockBean
    private UserService userService;
    @MockBean
    private IRoleService roleService;

    @MockBean
    private PasswordEncoder passwordEncode;
    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    JwtUtil jwtUtil;

    @MockBean
    ModelMapper modelMapper;


    @DisplayName("El cliente ingresa un usuario de tipo USER, lo que se espera es que el usuario " +
            "se cree correctamente")
    @Test
    void registrarUsuarioUSERConExito() throws Exception {
        when(userService.guardarUsuario(any()))
                .thenReturn(new User());
    }

    @Test
    void authenticatedUser() throws Exception {
        when(userService.authenticatedUser()).thenReturn(new UserDto());
    }
}