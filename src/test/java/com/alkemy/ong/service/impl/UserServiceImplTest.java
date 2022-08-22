package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.LoginRequestDTO;
import com.alkemy.ong.dto.PageDto;
import com.alkemy.ong.dto.RoleDto;
import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.model.Role;
import com.alkemy.ong.model.User;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.utils.JwtUtil;
import com.alkemy.ong.utils.PageUtils;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UserServiceImplTest {

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private EmailServiceImpl emailServiceImpl;

    @MockBean
    private JwtUtil jwtUtil;

    @SpyBean
    private ModelMapper modelMapper;

    @MockBean
    private UserRepository userRepository;


    @Autowired
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    /**
     * Method under test: {@link UserServiceImpl#guardarUsuario(User)}
     */
    @Test
    void GuardarUsuario_al_guardar_devuelve_el_mismo_usuario_creado() {

        doNothing().when( emailServiceImpl ).WelcomeMail( anyString(), anyString() );

        Role role = getRole();

        User user = getUser( true, role );
        when( userRepository.save( any() ) ).thenReturn( user );

        Role role1 = getRole();

        User user1 = getUser( true, role1 );
        assertSame( user, userServiceImpl.guardarUsuario( user1 ) );
        verify( emailServiceImpl ).WelcomeMail( anyString(), anyString() );
        verify( userRepository ).save( any() );
    }

    /**
     * Method under test: {@link UserServiceImpl#guardarUsuario(User)}
     */
    @Test
    void GuardarUsuario_al_tirar_una_excepcion_no_se_envia_el_correo_eletronico() {

        when( userRepository.save( any() ) ).thenThrow( new UsernameNotFoundException( "Msg" ) );
        Role role1 = getRole();
        User user1 = getUser( true, role1 );
        assertThatThrownBy( () -> userServiceImpl.guardarUsuario( user1 ) )
                .isInstanceOf( UsernameNotFoundException.class )
                .hasMessage( "Msg" );
        verifyNoInteractions( emailServiceImpl );
        verify( userRepository ).save( any() );
    }

    /**
     * Method under test: {@link UserServiceImpl#getAllUsers(int, String)}
     */
    @Test
    void GetAllUsers_al_solicitar_devuelve_todos_los_usuarios_disponibles() {
        // TODO: Complete this test.

        ArrayList<User> arrayList = new ArrayList<>();
        arrayList.add( new User() );
        when( userRepository.findAll( any( Pageable.class ) ) ).thenReturn( new PageDto<>( arrayList, PageUtils.getPageable( 0, "" ), 0 ) );
        PageDto<UserDto> actualPage = userServiceImpl.getAllUsers( 1, "Order" );
        assertThat( actualPage ).isNotNull();
        assertThat( actualPage.getContent().size() ).isNotNull().isEqualTo( 1 );

    }


    /**
     * Method under test: {@link UserServiceImpl#loadUserByUsername(String)}
     */
    @Test
    void LoadUserByUsername_cuando_el_usuario_esta_eliminado_tira_una_excepcion() throws UsernameNotFoundException {

        Role role = getRole();

        User user = getUser( true, role );
        Optional<User> ofResult = Optional.of( user );
        when( userRepository.findByEmail( anyString() ) ).thenReturn( ofResult );
        assertThatThrownBy( () -> userServiceImpl.loadUserByUsername( "foo" ) ).isInstanceOf( UsernameNotFoundException.class ).hasMessage( "NOT FOUND" );
        verify( userRepository ).findByEmail( anyString() );
    }

    /**
     * Method under test: {@link UserServiceImpl#loadUserByUsername(String)}
     */
    @Test
    void LoadUserByUsername_cuando_el_usuario_no_existe_tira_una_excepcion() throws UsernameNotFoundException {

        when( userRepository.findByEmail( any() ) ).thenReturn( Optional.empty() );

        assertThatThrownBy( () -> userServiceImpl.loadUserByUsername( "foo" ) ).isInstanceOf( UsernameNotFoundException.class ).hasMessage( "NOT FOUND" );
        verifyNoInteractions( modelMapper );
        verify( userRepository ).findByEmail( any() );
    }

    /**
     * Method under test: {@link UserServiceImpl#loadUserByUsername(String)}
     */
    @Test
    void LoadUserByUsername_cuando_el_usuario_existe_y_no_esta_eliminado_no_tira_una_excepcion_y_devuelve_el_usuario() throws UsernameNotFoundException {


        Role role = getRole();
        User user = getUser( false, role );
        when( userRepository.findByEmail( any() ) ).thenReturn( Optional.of( user ) );

        assertThat( userServiceImpl.loadUserByUsername( "foo" ) ).isNotNull();
        verify( modelMapper, atLeast( 2 ) ).map( any(), any() );
        verify( userRepository ).findByEmail( any() );
    }

    /**
     * Method under test: {@link UserServiceImpl#findById(String)}
     */
    @Test
    void FindById_al_encontrar_el_usuario_devuelve_el_usuario_no_nulo() {

        Role role = getRole();

        User user = getUser( true, role );
        Optional<User> ofResult = Optional.of( user );
        when( userRepository.findById( anyString() ) ).thenReturn( ofResult );
        Optional<User> actualFindByIdResult = userServiceImpl.findById( "42" );
        assertThat( actualFindByIdResult ).isPresent();
        assertThat( actualFindByIdResult.get() ).isNotNull().isSameAs( ofResult.get() );
        verify( userRepository ).findById( anyString() );
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

    private static User getUser(boolean deleted, Role role) {

        User user = new User();
        user.setDeleted( deleted );
        user.setEmail( "jane.doe@example.org" );
        user.setFirstName( "Jane" );
        user.setId( "42" );
        user.setLastName( "Doe" );
        user.setPassword( "iloveyou" );
        user.setPhoto( "alice.liddell@example.org" );
        user.setRole( role );
        user.setTimestamp( mock( Timestamp.class ) );
        return user;
    }

    /**
     * Method under test: {@link UserServiceImpl#findById(String)}
     */
    @Test
    void findById_al_no_encontrar_el_usuario_devuelve_un_opcional_vacio() {

        when( userRepository.findById( anyString() ) ).thenReturn( Optional.empty() );
        assertThat( userServiceImpl.findById( "42" ) ).isNotNull().isNotPresent();
        verify( userRepository ).findById( anyString() );
    }

    /**
     * Method under test: {@link UserServiceImpl#deleteUser(String)}
     */
    @Test
    void DeleteUser_al_eliminar_el_usuario_devuelve_un_boleano_true() {

        doNothing().when( userRepository ).deleteById( anyString() );
        assertThat( userServiceImpl.deleteUser( "42" ) ).isTrue();
        verify( userRepository ).deleteById( anyString() );
    }

    /**
     * Method under test: {@link UserServiceImpl#deleteUser(String)}
     */
    @Test
    void DeleteUser_al_eliminar_al_tirar_una_excepcion_devuelve_un_boleano_false() {

        doThrow( new UsernameNotFoundException( "Msg" ) ).when( userRepository ).deleteById( anyString() );
        assertThat( userServiceImpl.deleteUser( "42" ) ).isNotNull().isFalse();
        verify( userRepository ).deleteById( anyString() );
    }

    /**
     * Method under test: {@link UserServiceImpl#login(LoginRequestDTO)}
     */
    @Test
    void Login_al_iniciar_sesion_devuelve_el_jwt_no_nulo() throws AuthenticationException {

        when( jwtUtil.generateToken( any() ) ).thenReturn( "ABC123" );

        UserDto.UserPagedDto userPagedDto = getUserPagedDto();
        TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken( userPagedDto,
                "Credentials" );

        when( authenticationManager.authenticate( any() ) ).thenReturn( testingAuthenticationToken );
        assertThat( userServiceImpl.login( new LoginRequestDTO( "jane.doe@example.org", "iloveyou" ) ).getJwt() ).isEqualTo( "ABC123" );
        verify( jwtUtil ).generateToken( any() );
        verify( authenticationManager ).authenticate( any() );
    }


    /**
     * Method under test: {@link UserServiceImpl#login(LoginRequestDTO)}
     */
    @Test
    void Login_al_iniciar_sesion_con_credenciales_incorrectas_tira_una_excepcion() throws AuthenticationException {

        when( jwtUtil.generateToken( any() ) ).thenReturn( "ABC123" );

        when( authenticationManager.authenticate( any() ) ).thenThrow( new BadCredentialsException( "Error" ) );
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        assertThatThrownBy( () -> userServiceImpl.login( loginRequestDTO ) ).isInstanceOf( BadCredentialsException.class ).hasMessage( "Email o contraseña incorrecta " );
    }

    private static UserDto.UserPagedDto getUserPagedDto() {

        UserDto.UserPagedDto userPagedDto = new UserDto.UserPagedDto();
        userPagedDto.setDeleted( true );
        userPagedDto.setEmail( "jane.doe@example.org" );
        userPagedDto.setFirstName( "Jane" );
        userPagedDto.setLastName( "Doe" );
        userPagedDto.setPassword( "iloveyou" );
        userPagedDto.setPhoto( "alice.liddell@example.org" );
        userPagedDto.setRole( new RoleDto() );
        return userPagedDto;
    }

    /**
     * Method under test: {@link UserServiceImpl#validarId(String)}
     */
    @Test
    @WithMockUser(roles = {"ADMIN"})
    void ValidarId_al_usuario_con_rol_ADMIN_siempre_devuelve_un_booleano_true() throws Exception {
        // TODO: Complete this test.
        User user = new User();
        Role role = new Role();
        role.setName( "ADMIN" );
        user.setRole( role );
        when( userRepository.findByEmail( anyString() ) ).
                thenReturn( Optional.of( user ) );
        boolean validarId = userServiceImpl.validarId( "42" );
        assertThat( validarId ).isTrue();
    }

    /**
     * Method under test: {@link UserServiceImpl#validarId(String)}
     */
    @Test
    @WithMockUser(roles = {"ADMIN"})
    void ValidarId_al_usuario_con_rol_USER_con_distinto_id_devuelve_false() throws Exception {
        // TODO: Complete this test.
        User user = new User();
        user.setId( "ddd" );
        Role role = new Role();
        role.setName( "USER" );
        user.setRole( role );
        when( userRepository.findByEmail( anyString() ) ).
                thenReturn( Optional.of( user ) );
        boolean validarId = userServiceImpl.validarId( "42" );
        assertThat( validarId ).isFalse();
    }

    /**
     * Method under test: {@link UserServiceImpl#validarId(String)}
     */
    @Test
    @WithMockUser(roles = {"ADMIN"})
    void ValidarId_al_usuario_no_encontrarse_tira_una_excepcion() {
        // TODO: Complete this test.
        User user = new User();
        user.setId( "ddd" );
        Role role = new Role();
        role.setName( "USER" );
        user.setRole( role );
        when( userRepository.findByEmail( anyString() ) ).
                thenReturn( Optional.empty() );
        assertThatThrownBy( () -> userServiceImpl.validarId( "42" ) ).isInstanceOf( Exception.class );
    }

    /**
     * Method under test: {@link UserServiceImpl#validarId(String)}
     */
    @Test
    @WithMockUser(roles = {"ADMIN"})
    void ValidarId_al_usuario_con_rol_USER_con_igual_id_devuelve_true() throws Exception {
        // TODO: Complete this test.
        User user = new User();
        user.setId( "42" );
        Role role = new Role();
        role.setName( "USER" );
        user.setRole( role );
        when( userRepository.findByEmail( anyString() ) ).
                thenReturn( Optional.of( user ) );
        boolean validarId = userServiceImpl.validarId( "42" );
        assertThat( validarId ).isTrue();
        verify( userRepository ).findByEmail( anyString() );
    }

    /**
     * Method under test: {@link UserServiceImpl#authenticatedUser()}
     */
    @Test
    @WithMockUser()
    void AuthenticatedUser_al_existir_el_usuario_debe_devolver_un_usuario_no_nulo() throws Exception {
        // TODO: Complete this test.
        when( userRepository.findByEmail( any() ) ).thenReturn( Optional.of( new User() ) );
        UserDto userDto = userServiceImpl.authenticatedUser();
        assertThat( userDto ).isNotNull();
        verify( userRepository ).findByEmail( anyString() );
    }

    /**
     * Method under test: {@link UserServiceImpl#authenticatedUser()}
     */
    @Test
    @WithMockUser()
    void AuthenticatedUser_al_no_existir_el_usuario_debe_tirar_una_excepcion() {
        // TODO: Complete this test.
        when( userRepository.findByEmail( any() ) ).thenReturn( Optional.empty() );
        assertThatThrownBy( () -> userServiceImpl.authenticatedUser() ).isInstanceOf( Exception.class );
    }
}

