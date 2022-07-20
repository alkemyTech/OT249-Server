package com.alkemy.ong.controller;

import com.alkemy.ong.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "users")
public class UserController {

    private final UserService userService;
    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getPagedUsers(@RequestParam(defaultValue = "0", name = "page") int page,
                                      @RequestParam(defaultValue = "asc", name = "order") String order) {
        return ResponseEntity.ok( userService.getAllUsers(page, order) );
    }
}
