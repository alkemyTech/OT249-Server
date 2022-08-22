package com.alkemy.ong.service.impl;

import com.alkemy.ong.model.Role;
import com.alkemy.ong.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {RoleServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RoleServiceImplTest {

    @MockBean
    private RoleRepository roleRepository;


    private RoleServiceImpl roleServiceImpl;

    @BeforeEach
    void setUp() {

        roleServiceImpl = new RoleServiceImpl( roleRepository );
    }


    /**
     * Method under test: {@link RoleServiceImpl#getRoleById(String)}
     */
    @Test
    void GetRoleById_deberia_devolver_entidad_completa_al_encontrar_el_resultado() {

        Role role = getRole();
        Optional<Role> ofResult = Optional.of( role );
        when( roleRepository.findById( any() ) ).thenReturn( ofResult );
        assertSame( role, roleServiceImpl.getRoleById( "42" ) );
        verify( roleRepository ).findById( any() );
    }

    private static Role getRole() {

        Role role = new Role();
        role.setDescription( "The characteristics of someone or something" );
        role.setId( "42" );
        role.setName( "Name" );
        role.setTimestamp( mock( Timestamp.class ) );
        role.setUsers( new HashSet<>() );
        return role;
    }

    /**
     * Method under test: {@link RoleServiceImpl#getRoleById(String)}
     */
    @Test
    void GetRoleById_deberia_devolver_nulo_al_no_encontrar_la_entidad() {

        when( roleRepository.findById( any() ) ).thenReturn( Optional.empty() );
        assertNull( roleServiceImpl.getRoleById( "42" ) );
        verify( roleRepository ).findById( any() );
    }

    /**
     * Method under test: {@link RoleServiceImpl#getAllRoles()}
     */
    @Test
    void GetAllRoles_deberia_devolver_nulo_y_no_interactuar_con_la_base() {

        when( roleRepository.findById( any() ) ).thenReturn( Optional.empty() );
        assertNull( roleServiceImpl.getAllRoles() );
        verifyNoInteractions( roleRepository );
    }

    /**
     * Method under test: {@link RoleServiceImpl#deleteRoleById(String)}
     */
    @Test
    void DeleteRoleById_no_implementado_no_deberia_interactuar_con_la_base_de_datos() {

        roleServiceImpl.deleteRoleById( "" );
        verifyNoInteractions( roleRepository );
    }

    /**
     * Method under test: {@link RoleServiceImpl#updateRole(Role, String)}
     */
    @Test
    void update_role_no_deberia_interactuar_con_la_base_de_datos() {

        roleServiceImpl.updateRole( new Role(), "" );
        verifyNoInteractions( roleRepository );
    }

    /**
     * Method under test: {@link RoleServiceImpl#updateRole(Role, String)}
     */
    @Test
    void GetRoleByName_deberia_devolver_nulo() {

        when( roleRepository.findByName( anyString() ) ).thenReturn( new Role() );
        Role roleByName = roleServiceImpl.getRoleByName( "" );
        verify( roleRepository ).findByName( anyString() );
        assertThat( roleByName ).isNotNull();
    }
}

