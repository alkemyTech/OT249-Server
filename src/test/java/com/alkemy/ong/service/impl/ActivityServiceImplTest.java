package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.ActivityDto;
import com.alkemy.ong.model.Activity;
import com.alkemy.ong.repository.ActivityRepository;
import com.alkemy.ong.service.ActivityService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ActivityServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
class ActivityServiceImplTest {

    @SpyBean
    private ModelMapper modelMapper;

    @MockBean
    private ActivityRepository activityRepository;

    @MockBean
    private ActivityService activityService;
    private ActivityServiceImpl activityServiceImpl;

    @BeforeEach
    void setUp() {
        activityServiceImpl = new ActivityServiceImpl( activityRepository, modelMapper );
    }

    private static ActivityDto getActivityDto() {

        ActivityDto activityDto = new ActivityDto();
        activityDto.setContent( "Not all who wander are lost" );
        activityDto.setImage( "Image" );
        activityDto.setName( "Name" );
        return activityDto;
    }

    private Activity getActivityWithId() {

        Activity activity = getActivity( false );
        activity.setId( "algo" );
        return activity;
    }

    private static Activity getActivity(boolean softDelete) {

        Activity activity = new Activity();
        activity.setContent( "Not all who wander are lost" );
        activity.setId( "42" );
        activity.setImage( "Image" );
        activity.setName( "Name" );
        activity.setDeleted( softDelete );
        activity.setTimestamp( Timestamp.from( LocalDateTime.of( 1, 1, 1, 1, 1, 1 ).toInstant( ZoneOffset.UTC ) ) );
        return activity;
    }

    private Activity getUpdatedTestimonial() {

        Activity activityWithId = getActivityWithId();
        activityWithId.setId( "42" );
        activityWithId.setName( "updated" );
        activityWithId.setImage( "updated" );
        activityWithId.setContent( "updated" );
        activityWithId.setDeleted( false );
        return activityWithId;
    }

    @Test
    void Update_activity_and_return_entity_updated() {

        Activity activity = new Activity();
        activity.setContent( "Not all who wander are lost" );
        activity.setId( "42" );
        activity.setImage( "Image" );
        activity.setName( "Name" );
        activity.setDeleted( false );
        activity.setTimestamp( Timestamp.from( LocalDateTime.of( 1, 1, 1, 1, 1, 1 ).toInstant( ZoneOffset.UTC ) ) );

        when( activityRepository.findById( anyString() ) ).thenReturn( Optional.of( activity ) );
        ActivityDto activityDto = getActivityDto();
        activityDto.setContent( "updated" );
        activityDto.setImage( "updated" );
        activityDto.setName( "updated" );
        Activity value = new Activity();
        value.setName( activityDto.getName() );
        value.setContent( activityDto.getContent() );
        value.setImage( activityDto.getImage() );
        when( activityRepository.save( any() ) ).thenReturn( value );

        Activity updateActivity = activityServiceImpl.crearActivity( activity);

        assertThat( updateActivity.getName() ).isEqualTo( activityDto.getName() ).isNotEqualTo( activity.getName() );

        assertThat( updateActivity.getImage() ).isEqualTo( activityDto.getImage() ).isNotEqualTo( activity.getImage() );

        assertThat( updateActivity.getContent() ).isEqualTo( activityDto.getContent() ).isNotEqualTo( activity.getContent() );

    }

    @Test
    void Create_activity_and_return_entity_saved() {

        Activity activity = getActivity( true );
        Activity activityWithId = getActivityWithId();
        ArgumentCaptor<Activity> activity2 = ArgumentCaptor.forClass( Activity.class );
        when( activityRepository.save( activity2.capture() ) ).thenReturn( activityWithId );
        ActivityDto activityDto = getActivityDto();
        Activity actual = activityServiceImpl.crearActivity( activity );
        assertThat( actual ).isNotNull();
        Activity value = activity2.getValue();
        assertThat( actual.getId() )
                .isNotEqualTo( activity.getId() )
                .isNotEqualTo( value.getId() );
        assertThat( actual.getName() )
                .isEqualTo( activity.getName() )
                .isEqualTo( value.getName() );
        assertThat( actual.getContent() )
                .isEqualTo( activity.getContent() )
                .isEqualTo( value.getContent() );
        assertThat( actual.getDeleted() )
                .isNotEqualTo( activity.getDeleted() )
                .isEqualTo( value.getDeleted() )
                .isFalse();

        verify( activityRepository ).save( any() );
        verify( modelMapper, atLeast( 1 ) ).map( any(), any() );
    }

    @Test
    void FindByIdReturnActivity() {

        Activity activity = getActivity( true );
        activity.setId( "42" );
        Optional<Activity> ofResult = Optional.of( activity );
        when( activityRepository.findById( any() ) ).thenReturn( ofResult );
        Activity activityBusqueda = getActivity(true);
        activityBusqueda.setId( "42" );
        Activity actual = activityServiceImpl.findById( "42" );

        assertThat( actual.getId() ).isEqualTo( activityBusqueda.getId() );
        assertThat( actual.getContent() ).isEqualTo( activityBusqueda.getContent() );
        assertThat( actual.getName() ).isEqualTo( activityBusqueda.getName() );
        assertThat( actual.getImage() ).isEqualTo( activityBusqueda.getImage() );
        verify( activityRepository ).findById( any() );
        verify( modelMapper ).map( any(), any() );
    }
}