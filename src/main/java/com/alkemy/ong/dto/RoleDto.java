package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto implements GrantedAuthority {
    private String id;

    private String name;

    private String description;

    private Timestamp timestamp;

    @Override
    public String getAuthority() {

        return "ROLE_"+this.getName();
    }

}
