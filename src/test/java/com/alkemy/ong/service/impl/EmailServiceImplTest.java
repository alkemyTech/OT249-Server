package com.alkemy.ong.service.impl;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.objects.Content;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {EmailServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisplayNameGeneration( DisplayNameGenerator.ReplaceUnderscores.class )

class EmailServiceImplTest {

    private EmailServiceImpl emailService;


    @MockBean
    private SendGrid sendGrid;

    @BeforeEach
    void setUp() {

        emailService = new EmailServiceImpl( "", "", sendGrid );

    }

    /**
     * Method under test: {@link EmailServiceImpl#sendEmail(String, String, Content)}
     */
    @Test
    void testSendEmail() throws IOException {

        IOException cause = new IOException( "ERROR" );
        when( sendGrid.api( any() ) ).thenThrow( cause );

        assertThatThrownBy( ()-> emailService.sendEmail( "Hello from the Dreaming Spires", "jane.doe@example.org", new Content( "Type", "42" ) ))
                .isInstanceOf( IOException.class )
                .hasMessage( "ERROR" );

    }

    /**
     * Method under test: {@link EmailServiceImpl#sendEmail(String, String, Content)}
     */
    @Test
    void testSendEmail2() throws IOException {
        Response response = new Response();
        response.setBody( "Body" );
        response.setStatusCode( HttpStatus.OK.value() );
        when( sendGrid.api( any() ) ).thenReturn( response );

        Response actual = emailService.sendEmail( "Hello from the Dreaming Spires", "jane.doe@example.org", new Content() );

        verify( sendGrid ).api( any() );
        assertThat( actual).isNotNull();
        assertThat( actual.getStatusCode() ).isEqualTo( HttpStatus.OK.value() );

    }

    /**
     * Method under test: {@link EmailServiceImpl#sendEmailToContact(String, String)}
     */
    @Test
    void testSendEmailToContact() throws IOException {
        // TODO: Complete this test.
        Response actual = new Response();
        when( sendGrid.api( any() ) ).thenReturn( actual );
        String name = "Name";
        emailService.sendEmailToContact( "jane.doe@example.org", name );

        ArgumentCaptor<Request> argumentCaptor = ArgumentCaptor.forClass( Request.class );
        verify( sendGrid ).api( argumentCaptor.capture() );

        Request captorValue = argumentCaptor.getValue();
        assertThat( captorValue.getBody() ).isEqualTo( "{\"from\":{\"email\":\"\"},\"subject\":\"¡Somos Más! - Información de contacto\",\"personalizations\":[{\"to\":[{\"email\":\"jane.doe@example.org\"}]}],\"content\":[{\"type\":\"text/html\",\"value\":\"<h2>Hola Name ¡Gracias por completar tu contacto en somos mas!</h2>\"}]}" );
    }
    /**
     * Method under test: {@link EmailServiceImpl#sendEmailToContact(String, String)}
     */
    @Test
    void testSendEmailToContact2() throws IOException {
        // TODO: Complete this test.
        when( sendGrid.api( any() ) ).thenThrow( new IOException("ERROR") );
        String name = "Name";
        emailService.sendEmailToContact( "jane.doe@example.org", name );


        verify( sendGrid ).api( any() );
    }
    /**
     * Method under test: {@link EmailServiceImpl#WelcomeMail(String, String)}
     */
    @Test

    void testWelcomeMail() throws IOException {

        when( sendGrid.api( any() ) ).thenThrow( new IOException("ERROR") );
        emailService.WelcomeMail( "jane.doe@example.org", "Jane" );

    }

    /**
     * Method under test: {@link EmailServiceImpl#WelcomeMail(String, String)}
     */
    @Test
    void testWelcomeMail2() throws IOException {
        // TODO: Complete this test.

        Response actual = new Response();
        when( sendGrid.api( any() ) ).thenReturn( actual );

        emailService.WelcomeMail( "alice.liddell@example.org", "Jane" );
        ArgumentCaptor<Request> request =  ArgumentCaptor.forClass( Request.class );
        verify( sendGrid ).api( request.capture() );
        Request actual1 = request.getValue();
        assertThat( actual1 ).isNotNull();
        assertThat( actual1.getMethod() ).isEqualTo( Method.POST );
        assertThat( actual1.getEndpoint() ).isEqualTo( "mail/send" );
    }

    /**
     * Method under test: {@link EmailServiceImpl#welcomeEmailTemplate(String)}
     */
    @Test
    void testWelcomeEmailTemplate() {

        assertThat(
                emailService.welcomeEmailTemplate( "Jane" ) ).isNotNull();
    }
}

