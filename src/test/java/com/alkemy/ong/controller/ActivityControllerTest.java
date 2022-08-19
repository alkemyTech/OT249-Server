package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ActivityDto;
import com.alkemy.ong.model.Activity;
import com.alkemy.ong.repository.ActivityRepository;
import com.alkemy.ong.service.ActivityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ActivityController.class})
@ExtendWith(SpringExtension.class)
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
public class ActivityControllerTest {

    private ActivityController activityController;

    @MockBean
    private ActivityService activityService;

    @MockBean
    private ActivityRepository activityRepository;

    @SpyBean
    private ModelMapper modelMapper;

    Activity activity;

    @BeforeEach
    void setUp() {
        activityController = new ActivityController(activityService);

        activity = new Activity();
        activity.setId("1");
        activity.setName("Name");
        activity.setContent("Content");
        activity.setImage("Image");
        activity.setTimestamp(Timestamp.from(Instant.now()));
        activity.setDeleted(false);
    }

    private static ActivityDto getActivityDto(String content) {

        ActivityDto activityDto = new ActivityDto();
        activityDto.setContent(content);
        activityDto.setImage("Image");
        activityDto.setName("Name");
        return activityDto;


    }

    /**
     * Method under test: {@link ActivityController#crearActividad(ActivityDto)}
     */
    @Test
    @DisplayName("Add activity, save this and return 200 ok if the user has Role 'ADMIN'")
    void createActivityOk() throws Exception {

        when(activityService.crearActivity(any()))
                .thenReturn(activity);

        ActivityDto activityDto = getActivityDto("Content");
        String content = (new ObjectMapper()).writeValueAsString(activity);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/activities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(activityController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content()
                        .json(content)); }


    @Test
    @DisplayName("create activity method return 400 Bad Request if request is not valid")
    void createActivityAndReturnBadRequestIfRequestIsNotValid() throws Exception {

       when(activityService.crearActivity(activity))
                .thenReturn(activity);

        ActivityDto activityDto = getActivityDto("");
        String content = (new ObjectMapper()).writeValueAsString(activityDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/activities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(activityController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isBadRequest());
        }

    @Test
    @DisplayName("Update activity return 200 ok if the user has Role 'ADMIN'")
    void UpdateActivityOk() throws Exception {

        when(activityRepository.findById(anyString())).thenReturn(Optional.of(new Activity()));

        when(activityService.crearActivity(activity)).thenReturn(activity);

        ActivityDto activityDto = getActivityDto("content");
        String content = (new ObjectMapper()).writeValueAsString(activityDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/activities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(activityController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk());  }

    @Test
    @DisplayName("Update activity return 404 Not Found")
    void UpdateActivityNotFound() throws Exception {

        when(activityRepository.findById(anyString())).thenReturn(Optional.empty());

        ActivityDto activityDto = getActivityDto("Content");
        String content = (new ObjectMapper()).writeValueAsString(activity);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/activities/{id}","1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(activityController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    @DisplayName("Update activity method return 400 Bad Request if request is not valid")
    void UpdateActivityBadRequestForInvalidRequestBody() throws Exception {

        when(activityRepository.findById(anyString())).thenReturn(Optional.of(new Activity()));

        when(activityService.crearActivity(activity)).thenReturn(activity);

        ActivityDto activityDto = getActivityDto("");
        String content = (new ObjectMapper()).writeValueAsString(activityDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/activities/{id}","1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(activityController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}


