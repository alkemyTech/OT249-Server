package com.alkemy.ong.service.impl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.UnexpectedTypeException;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.alkemy.ong.dto.PublicOrganizationDto;
import com.alkemy.ong.model.Organization;
import com.alkemy.ong.repository.OrganizationRepository;

@ContextConfiguration(classes = {OrganizationServiceImpl.class})
@ExtendWith(SpringExtension.class)
//@ActiveProfiles(value = {"test"})
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
public class OrganizationServiceImplTest {
	@SpyBean
    private ModelMapper modelMapper;
	
	@MockBean
	private OrganizationRepository organizationRepository;
	
	private OrganizationServiceImpl orgServiceImpl;
	
	@BeforeEach
	void setUp() {
		orgServiceImpl = new OrganizationServiceImpl(organizationRepository, modelMapper);
	}
	
	@AfterEach
    void tearDown() {
    }
	
	private Organization getOrganization() {
		Organization org = new Organization("Org1","iamge","address","1234","somosmas@example.org","bienvenido","somos una ong", 
				new Timestamp(System.currentTimeMillis()), false);
		org.setId("90");
		return org;
	}
	
	private PublicOrganizationDto getPublicOrganizationDto(Organization organization) {
		PublicOrganizationDto org = new PublicOrganizationDto(organization.getId(), organization.getName(), organization.getImage(), organization.getPhone(),
				organization.getAddress(),organization.getUrlFacebook(), organization.getUrlLinkedin(),organization.getUrlInstagram());
		return org;
	}
	
	private Organization getOrgUpdated(PublicOrganizationDto orgDto) {
		Organization org = getOrganization();
		org.setName(orgDto.getName());
		org.setAddress(orgDto.getAddress());
		org.setImage(orgDto.getImage());
		org.setPhone(orgDto.getPhone());
		org.setUrlFacebook(orgDto.getFacebookUrl());
		org.setUrlLinkedin(orgDto.getLinkedinUrl());
		org.setUrlInstagram(orgDto.getInstagramUrl());
		return org;
	}
	
	/**
     * Method under test: {@link OrganizationServiceImpl#get(String)}
     */
    @Test
    void get_whenValueIsNull_thenThrowException() {
    	//given
        //when
        when( organizationRepository.getById( any() ) ).thenThrow(new IllegalArgumentException("Error") );
        //then
        assertThatThrownBy( () -> orgServiceImpl.get("100") )
                .isInstanceOf( IllegalArgumentException.class )
                .hasMessage( "Error" )
                .isNotNull();
        verify( organizationRepository ).getById( any() );
        verifyNoInteractions( modelMapper );
    }
    
    /**
     * Method under test: {@link OrganizationServiceImpl#get(String)}
     */
    @Test
    void get_whenFoundOngById_thenReturnOng() {
    	//given
    	Organization org = getOrganization();
    	//when
    	when(organizationRepository.getById( anyString() )).thenReturn(org);
    	//then
    	Organization currentOng = orgServiceImpl.get("90");
    	assertThat(currentOng).isNotNull();
    	assertThat(currentOng).isEqualTo(org);
        verify( organizationRepository ).getById( any() );
        verifyNoInteractions( modelMapper );
    }
    
    /**
     * Method under test: {@link organizationServiceImpl#get(String)}
     */
    @Test
    void get_whenNotFoundOngByID_thenReturnNull() {
    	//given
    	//when
    	when(organizationRepository.getById( any() )).thenReturn(null);
    	//then
    	Organization currentOng = orgServiceImpl.get("100");
    	assertThat(currentOng).isNull();
    	verify( organizationRepository ).getById( any() );
    	verifyNoInteractions( modelMapper );
    }
    
    /**
     * Method under test: {@link organizationServiceImpl#getPublicData()}
     */
    
    @Test
    void getPublicData_whenListIsEmpty_thenThrowException() {
    	//when
    	when(organizationRepository.findAll()).thenThrow(new IndexOutOfBoundsException("Error"));
    	//then
    	assertThatThrownBy(() -> orgServiceImpl.getPublicData())
    	.isInstanceOf( IndexOutOfBoundsException.class )
    	.hasMessage("Error")
    	.hasNoCause();
    	verify(organizationRepository).findAll();
    	verifyNoInteractions( modelMapper );
    } 
    
    /**
     * Method under test: {@link organizationServiceImpl#getPublicData()}
     */
    @Test
    void getPublicData_whenListIsNotEmpty_thenReturnList() {
    	//given
    	Organization org = getOrganization();
    	List<Organization> orgList = new ArrayList<>();
    	orgList.add(org);
    	//when
    	when(organizationRepository.findAll()).thenReturn(orgList);
    	//then
    	PublicOrganizationDto orgDto = orgServiceImpl.getPublicData();
    	assertThat(orgDto).isNotNull();
    	assertThat(orgDto.getName()).isEqualTo(org.getName());
    	assertThat(orgDto.getAddress()).isEqualTo(org.getAddress());
    	assertThat(orgDto.getPhone()).isEqualTo(org.getPhone());
    	assertThat(orgDto.getImage()).isEqualTo(org.getImage());
    	assertThat(orgDto.getFacebookUrl()).isEqualTo(org.getUrlFacebook());
    	assertThat(orgDto.getInstagramUrl()).isEqualTo(org.getUrlInstagram());
    	assertThat(orgDto.getLinkedinUrl()).isEqualTo(org.getUrlLinkedin());
    	verify( organizationRepository ).findAll();
    	verify( modelMapper ).map( any(), any() );
    }
    
    /**
     * Method under test: {@link organizationServiceImpl#update(String, PublicOrganizationDto)}
     */
    @Test
    void update_whenValidationError_thenThrowException() {
    	//given
    	Organization org = getOrganization();
    	PublicOrganizationDto PublicOrgDto = getPublicOrganizationDto(org);
        //when
        when( organizationRepository.findById( anyString() ) ).thenThrow(new UnexpectedTypeException());
        //then
        assertThatThrownBy( () -> orgServiceImpl.update( "90", PublicOrgDto) )
        .isInstanceOf( UnexpectedTypeException.class );
        verify( organizationRepository ).findById( anyString());
        verifyNoInteractions( modelMapper );
    }
    
    /**
     * Method under test: {@link organizationServiceImpl#update(String, PublicOrganizationDto)}
     */
    @Test
    void update_whenOrganizationNotFound_thenThrowException() {
    	//given
    	PublicOrganizationDto publicOrganizationDto = new PublicOrganizationDto();
    	//when
    	when(organizationRepository.findById(anyString())).thenReturn(Optional.empty());
    	//then
    	assertThrows(NoSuchElementException.class, () -> orgServiceImpl.update("100", publicOrganizationDto));
    	verify( organizationRepository ).findById(anyString());
    }
    
    /**
     * Method under test: {@link organizationServiceImpl#update(String, PublicOrganizationDto)}
     */
    @Test
    void update_whenOrganizationFound_thenReturnOrganizationUpdated() {
    	//given
    	Organization org = getOrganization();
    	PublicOrganizationDto orgDto = getPublicOrganizationDto(org);
    	Optional<Organization> ofResult = Optional.of(org);
    	Organization orgUpdated = getOrgUpdated(orgDto);
    	//when
        when( organizationRepository.findById( anyString() ) ).thenReturn( ofResult );
        when( organizationRepository.save( any(Organization.class) ) ).thenReturn( orgUpdated );
        //then
        assertDoesNotThrow(() -> orgServiceImpl.update("90", orgDto));
        verify( organizationRepository, atMost( 2 ) ).save( any( Organization.class ) );
        PublicOrganizationDto updateOrg = orgServiceImpl.update("90", orgDto);
        assertNotNull(updateOrg);
    }
    
    /**
     * Method under test: {@link organizationServiceImpl#getAll()}
     */
    @Test
    void getAll_returnNull() {
    	assertEquals(orgServiceImpl.getAll(), null);
    }
    
    /**
     * Method under test: {@link organizationServiceImpl#delete(String)}
     */
    @Test
    void delete_void() {
    	assertEquals(orgServiceImpl.delete("12"), null);
    	verifyNoInteractions(organizationRepository);
    }
}
