package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ActivityDto;
import com.alkemy.ong.model.Activity;
import com.alkemy.ong.repository.ActivityRepository;
import com.alkemy.ong.service.ActivityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
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

    void create_Activity_save_this_and_return_200_ok_if_the_user_has_Role_ADMIN() throws Exception {

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
    void create_activity_method_return_400_Bad_Request_if_request_is_not_valid() throws Exception {

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
    void Update_activity_return_200_ok_if_the_user_has_Role_ADMIN() throws Exception {

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
    void Update_Activity_return_404_Not_Found() throws Exception {

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
    void Update_activity_method_return_400_Bad_Request_if_request_is_not_valid() throws Exception {

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


