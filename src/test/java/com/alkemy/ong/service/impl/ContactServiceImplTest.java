package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.ContactDto;
import com.alkemy.ong.model.Contact;
import com.alkemy.ong.repository.ContactRepository;
import com.alkemy.ong.service.EmailService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ContactServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ContactServiceImplTest {

    @MockBean
    private ContactRepository contactRepository;

    private ContactServiceImpl contactServiceImpl;

    @BeforeEach
    void setUp() {

        contactServiceImpl = new ContactServiceImpl( modelMapper, contactRepository, emailService );

    }

    @MockBean
    private EmailService emailService;

    @SpyBean
    private ModelMapper modelMapper;

    /**
     * Method under test: {@link ContactServiceImpl#saveContact(ContactDto)}
     */
    @Test
    void testSaveContact() {

        doNothing().when( emailService ).sendEmailToContact( any(), any() );

        Contact contact = new Contact();
        contact.setDeleted( true );
        contact.setEmail( "jane.doe@example.org" );
        contact.setId( "42" );
        contact.setMessage( "Not all who wander are lost" );
        contact.setName( "Name" );
        contact.setPhone( "4105551212" );
        when( contactRepository.save( any() ) ).thenReturn( contact );
        ContactDto contactDto = new ContactDto( "Name", "4105551212", "jane.doe@example.org",
                "Not all who wander are lost" );

        assertSame( contactDto, contactServiceImpl.saveContact( contactDto ) );
        verify( emailService ).sendEmailToContact( any(), any() );
        verify( modelMapper, atMostOnce() ).map( any(), any() );
        verify( contactRepository ).save( any() );
    }

    /**
     * Method under test: {@link ContactServiceImpl#saveContact(ContactDto)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testSaveContact2() {
        // TODO: Complete this test.

        doNothing().when( emailService ).sendEmailToContact( any(), any() );

        Contact contact = new Contact();
        contact.setDeleted( true );
        contact.setEmail( "jane.doe@example.org" );
        contact.setId( "42" );
        contact.setMessage( "Not all who wander are lost" );
        contact.setName( "Name" );
        contact.setPhone( "4105551212" );
        when( contactRepository.save( any() ) ).thenReturn( contact );
        contactServiceImpl.saveContact( null );
    }

    /**
     * Method under test: {@link ContactServiceImpl#getAllContacts()}
     */
    @Test
    void testGetAllContacts() {

        when( contactRepository.findAll() ).thenReturn( new ArrayList<>() );
        assertTrue( contactServiceImpl.getAllContacts().isEmpty() );
        verify( contactRepository ).findAll();
    }

    /**
     * Method under test: {@link ContactServiceImpl#getAllContacts()}
     */
    @Test
    void testGetAllContacts2() {

        Contact contact = new Contact();
        contact.setDeleted( true );
        contact.setEmail( "jane.doe@example.org" );
        contact.setId( "42" );
        contact.setMessage( "Not all who wander are lost" );
        contact.setName( "Name" );
        contact.setPhone( "4105551212" );

        ArrayList<Contact> contactList = new ArrayList<>();
        contactList.add( contact );
        when( contactRepository.findAll() ).thenReturn( contactList );
        assertEquals( 1, contactServiceImpl.getAllContacts().size() );
        verify( modelMapper ).map( any(), any() );
        verify( contactRepository ).findAll();
    }

    /**
     * Method under test: {@link ContactServiceImpl#getAllContacts()}
     */
    @Test
    void testGetAllContacts3() {

        Contact contact = new Contact();
        contact.setDeleted( true );
        contact.setEmail( "jane.doe@example.org" );
        contact.setId( "42" );
        contact.setMessage( "Not all who wander are lost" );
        contact.setName( "Name" );
        contact.setPhone( "4105551212" );

        Contact contact1 = new Contact();
        contact1.setDeleted( true );
        contact1.setEmail( "jane.doe@example.org" );
        contact1.setId( "42" );
        contact1.setMessage( "Not all who wander are lost" );
        contact1.setName( "Name" );
        contact1.setPhone( "4105551212" );

        ArrayList<Contact> contactList = new ArrayList<>();
        contactList.add( contact1 );
        contactList.add( contact );
        when( contactRepository.findAll() ).thenReturn( contactList );
        assertEquals( 2, contactServiceImpl.getAllContacts().size() );
        verify( modelMapper, atLeast( 1 ) ).map( any(), any() );
        verify( contactRepository ).findAll();
    }

    /**
     * Method under test: {@link ContactServiceImpl#getContact()}
     */
    @Test
    void test_GetContact() {

        assertThat( contactServiceImpl.getContact() ).isNull();
        verifyNoInteractions( contactRepository );
    }

    /**
     * Method under test: {@link ContactServiceImpl#deleteContact()}
     */
    @Test
    void test_Delete() {

        contactServiceImpl.deleteContact();
        verifyNoInteractions( contactRepository );
    }

}

