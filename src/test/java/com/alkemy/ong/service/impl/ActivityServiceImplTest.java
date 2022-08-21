package com.alkemy.ong.service.impl;

import com.alkemy.ong.model.Activity;
import com.alkemy.ong.repository.ActivityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ActivityServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ActivityServiceImplTest {

    @MockBean
    private ActivityRepository activityRepository;

    private ActivityServiceImpl activityServiceImpl;

    @BeforeEach
    void setUp() {
        activityServiceImpl = new ActivityServiceImpl( activityRepository );

    }

    /**
     * Method under test: {@link ActivityServiceImpl#crearActivity(Activity)}
     */
    @Test
    void testCrearActivity() {

        Activity activity = getActivity();
        when( activityRepository.save( any() ) ).thenReturn( activity );

        Activity activity1 = getActivity();
        assertSame( activity, activityServiceImpl.crearActivity( activity1 ) );
        verify( activityRepository ).save( any() );
    }

    /**
     * Method under test: {@link ActivityServiceImpl#findById(String)}
     */
    @Test
    void testFindById() {

        Activity activity = getActivity();
        Optional<Activity> ofResult = Optional.of( activity );
        when( activityRepository.findById( anyString() ) ).thenReturn( ofResult );
        assertSame( activity, activityServiceImpl.findById( "42" ) );
        verify( activityRepository ).findById( anyString() );
    }

    private static Activity getActivity() {

        Activity activity = new Activity();
        activity.setContent( "Not all who wander are lost" );
        activity.setDeleted( true );
        activity.setId( "42" );
        activity.setImage( "Image" );
        activity.setName( "Name" );
        activity.setTimestamp( mock( Timestamp.class ) );
        return activity;
    }

    /**
     * Method under test: {@link ActivityServiceImpl#findById(String)}
     */
    @Test
    void testFindById2() {

        when( activityRepository.findById( anyString() ) ).thenReturn( Optional.empty() );
        assertThat( activityServiceImpl.findById( "42" ) ).isNull();
        verify( activityRepository ).findById( anyString() );
    }
}

