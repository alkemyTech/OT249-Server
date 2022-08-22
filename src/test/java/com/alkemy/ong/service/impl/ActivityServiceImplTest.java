package com.alkemy.ong.service.impl;

import com.alkemy.ong.model.Activity;
import com.alkemy.ong.repository.ActivityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ActivityServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisplayNameGeneration( DisplayNameGenerator.ReplaceUnderscores.class )

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
    void CrearActivity_al_guardar_debe_devolver_una_entidad_no_nula() {

        Activity activity = getActivity();
        when( activityRepository.save( any() ) ).thenReturn( activity );

        Activity activity1 = getActivity();
        Activity actual = activityServiceImpl.crearActivity( activity1 );
        assertThat(  actual).isNotNull();
        assertThat(  actual ).isSameAs( activity );
        verify( activityRepository ).save( any() );
    }
    /**
     * Method under test: {@link ActivityServiceImpl#crearActivity(Activity)}
     */
    @Test
    void CrearActivity_al_guardar_debe_guardar_los_mismos_campos_y_devolver_una_entidad_no_nula() {

        Activity activity = getActivity();
        when( activityRepository.save( any() ) ).thenReturn( activity );

        Activity actual = activityServiceImpl.crearActivity( activity );
        assertThat(  actual).isNotNull();
        assertThat(  actual ).isSameAs( activity );
        ArgumentCaptor<Activity> argumentCaptor = ArgumentCaptor.forClass( Activity.class );
        verify( activityRepository ).save( argumentCaptor.capture() );

        Activity captorValue = argumentCaptor.getValue();

        assertThat( captorValue.getContent() ).isEqualTo( activity.getContent() );
        assertThat( captorValue.getName() ).isEqualTo( activity.getName() );
        assertThat( captorValue.getDeleted() ).isEqualTo( activity.getDeleted() );
        assertThat( captorValue.getImage() ).isEqualTo( activity.getImage() );
        assertThat( captorValue.getId() ).isEqualTo( activity.getId() );

    }
    /**
     * Method under test: {@link ActivityServiceImpl#findById(String)}
     */
    @Test
    void FindById_al_encontrar_la_entidad_devuelve_la_entidad_no_nula() {

        Activity activity = getActivity();
        Optional<Activity> ofResult = Optional.of( activity );
        when( activityRepository.findById( anyString() ) ).thenReturn( ofResult );
        assertThat(  activityServiceImpl.findById( "42" ) ).isSameAs( activity );
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
    void FindById_al_no_encontrar_la_entidad_devuelve_la_entidad_nula() {

        when( activityRepository.findById( anyString() ) ).thenReturn( Optional.empty() );
        assertThat( activityServiceImpl.findById( "42" ) ).isNull();
        verify( activityRepository ).findById( anyString() );
    }
}

