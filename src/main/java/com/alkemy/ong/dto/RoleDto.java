package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto implements GrantedAuthority {
    private UUID id;

    private String name;

    private String description;

    private Timestamp timestamp;

    @Override
    public String getAuthority() {

        return this.getName();
    }

}
