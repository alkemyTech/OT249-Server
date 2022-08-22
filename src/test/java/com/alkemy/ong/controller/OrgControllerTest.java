package com.alkemy.ong.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;


import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.alkemy.ong.dto.PublicOrganizationDto;
import com.alkemy.ong.dto.SlideResponseDto;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.security.CustomExceptionHandler;
import com.alkemy.ong.service.OrganizationService;
import com.alkemy.ong.service.SlideService;
import com.alkemy.ong.service.impl.OrganizationServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.netty.handler.codec.http.HttpHeaderValues;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {OrgController.class})
//@WebMvcTest(controllers = OrgController.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class OrgControllerTest {
	@SpyBean
    private ModelMapper modelMapper;
	
	@MockBean
	private OrganizationRepository organizationRepository;
	
	
	
	@MockBean
	private OrganizationService organizationService;
	
	@MockBean
	private SlideService slideService;
	
	private OrgController orgController;
	
	@BeforeEach
    public void setUp() {
		orgController = new OrgController(organizationService, slideService);
	}
	
	/*
	private Organization getOrganization(String name) {
		Organization org = new Organization("Org1","iamge","address","123","somosmas@example.org","bienvenido","somos una ong", 
				new Timestamp(System.currentTimeMillis()), false);
		org.setId("90");
		org.setName(name);
		return org;
	}*/
	
	private PublicOrganizationDto getOrgDto() {
		PublicOrganizationDto dto = new PublicOrganizationDto("90","org", "image", "123", "address1", "facebook", "linkedin", "ig");
		return dto;
	}
	
	private PublicOrganizationDto getOrgDto(String name) {
		PublicOrganizationDto dto = new PublicOrganizationDto("12","org", "image", "123", "address1", "facebook", "linkedin", "ig");
		dto.setName(name);
		return dto;
	}
	
	private SlideResponseDto getSlide() {
		SlideResponseDto slide = new SlideResponseDto("12", "image", "text", 1, getOrgDto());
		return slide;
	}
	
	private static <T> String getAsString(T t) throws JsonProcessingException {

        return (new ObjectMapper()).writeValueAsString( t );
    }
	
	
	/**
     * Method under test: {@link orgController#getPublicDataConfig()}
	 * @throws Exception 
	 * 
     */
	@Test
	void getPublicDataConfig_whenListIsEmpty_thenReturnNotFound() throws Exception {
		//PublicOrganizationDto response = new PublicOrganizationDto();
		when(organizationService.getPublicData()).thenThrow( new ArrayIndexOutOfBoundsException() );
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/organization/public");
		MockMvcBuilders.standaloneSetup(orgController).setControllerAdvice( CustomExceptionHandler.class )
			.build()
			.perform(requestBuilder)
			.andExpect(status().isNotFound())
			.andExpect( content().string( "" ) );
	}
	
	/**
     * Method under test: {@link orgController#getPublicDataConfig()}
	 * @throws Exception 
	 * 
     */
	@Test
	void getPublicDataConfig_whenListIsNotEmpty_thenReturnOkStatus() throws Exception {
		PublicOrganizationDto orgDto = new PublicOrganizationDto();
		when(organizationService.getPublicData()).thenReturn(orgDto);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/organization/public");
		MockMvcBuilders.standaloneSetup(orgController)
			.build()
			.perform(requestBuilder)
			.andExpect(status().isOk())
			.andExpect(content().contentType("application/json"))
			.andExpect(jsonPath("$.name", is(orgDto.getName())))
			.andExpect(jsonPath("$.image", is(orgDto.getImage())))
			.andExpect(jsonPath("$.address", is(orgDto.getAddress())))
			.andExpect(jsonPath("$.phone", is(orgDto.getPhone())))
			.andExpect(jsonPath("$.facebookUrl", is(orgDto.getFacebookUrl())))
			.andExpect(jsonPath("$.linkedinUrl", is(orgDto.getLinkedinUrl())))
			.andExpect(jsonPath("$.instagramUrl", is(orgDto.getInstagramUrl())))
			.andExpect(content().json("{\"name\":null,\"image\":null,\"phone\":null,\"address\":null,\"facebookUrl\":null,\"linkedinUrl\":null,\"instagramUrl"
                                        + "\":null}"));
	}
	
	/**
     * Method under test: {@link orgController#slidesList(String)}
	 * @throws Exception 
	 * 
     */
	@Test
	void slidesList_cuandoRecibeUnIdValido_retornarListaDeSlidesPorOngId() throws Exception {
		List<SlideResponseDto> slides = new ArrayList<>();
		SlideResponseDto slide = getSlide();
		slides.add(slide);
		
		when(slideService.slideForOng(anyString())).thenReturn(slides);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get( "/organization/public/slides/{id}", "12" );
		MockMvcBuilders.standaloneSetup(orgController)
			.build()
			.perform(requestBuilder)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", Matchers.hasSize(1)));
	}
	
	/**
     * Method under test: {@link orgController#slidesList(String)}
	 * @throws Exception 
	 * 
     */
	@Test
	void slidesList_cuandoRecibeUnIdInexistente_retornarNull() throws Exception {
		when(slideService.slideForOng(anyString())).thenReturn(null);
		//doThrow(new Exception("Organization not Found")).when(slideService).slideForOng(anyString());
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get( "/organization/public/slides/{id}", "90" );
		MockMvcBuilders.standaloneSetup(orgController).setControllerAdvice( CustomExceptionHandler.class )
			.build()
			.perform(requestBuilder)
			.andExpect(status().isOk());
	}
	
	/**
     * Method under test: {@link orgController#updatePublicOrganization(String, PublicOrganizationDto)}
	 * @throws Exception 
	 * 
     */
	@Test
	void updatePublicOrganization_whenOngFound_returnOng() throws Exception {
		PublicOrganizationDto organization = getOrgDto("new ong name");
		String name = getAsString(organization);
		
		when(organizationService.update(anyString(), any())).thenReturn(new PublicOrganizationDto());
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/organization/public/{id}", "12")
			.contentType( org.springframework.http.MediaType.APPLICATION_JSON )
			.content( name );
		MockMvcBuilders.standaloneSetup(orgController)
			.build()
			.perform(requestBuilder)
			.andExpect(status().isAccepted())
			.andExpect( MockMvcResultMatchers.content().contentType( HttpHeaderValues.APPLICATION_JSON.toString() ) )
			.andExpect(content().json("{\"id\":null,\"name\":null,\"image\":null,\"phone\":null,\"address\":null,"+
			"\"facebookUrl\":null,\"linkedinUrl\":null,\"instagramUrl\":null}"));
	}
	
	/**
     * Method under test: {@link orgController#updatePublicOrganization(String, PublicOrganizationDto)}
	 * @throws Exception 
	 * 
     */
	@Test
	void updatePublicOrganization_whenOngNotFound_returnBadRequestStatus() throws Exception {
		OrganizationServiceImpl orgServiceImpl = new OrganizationServiceImpl(organizationRepository, modelMapper);
		OrgController orgController = new OrgController(orgServiceImpl, slideService);
		PublicOrganizationDto organization = new PublicOrganizationDto();
		String name = getAsString(organization);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/organization/public/{id}", "12")
				.contentType( org.springframework.http.MediaType.APPLICATION_JSON )
				.content( name );
		MockMvcBuilders.standaloneSetup(orgController)
				.build()
				.perform(requestBuilder)
				.andExpect(status().isBadRequest());
				//.andExpect( MockMvcResultMatchers.content().contentType( HttpHeaderValues.APPLICATION_JSON.toString() ) );
	}
}
