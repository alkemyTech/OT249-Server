package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.dto.NewDTO;
import com.alkemy.ong.dto.PageDto;
import com.alkemy.ong.exceptions.BindingResultException;
import com.alkemy.ong.exceptions.RecordException;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.model.News;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.utils.PageUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindingResult;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {NewsServiceImpl.class})
@ExtendWith(SpringExtension.class)
@ActiveProfiles(value = {"test"})
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
class NewsServiceImplTest {

    @MockBean
    private CategoryRepository categoryRepository;

    @SpyBean
    private ModelMapper modelMapper;

    @MockBean
    private NewsRepository newsRepository;

    private NewsServiceImpl newsServiceImpl;

    @BeforeEach
    void setUp() {

        newsServiceImpl = new NewsServiceImpl( newsRepository, categoryRepository, modelMapper );
    }

    @AfterEach
    void tearDown() {


    }
    private News getNews(Category category) {

        News news = new News();
        news.setCategory( category );
        news.setContent( "Not all who wander are lost" );
        news.setId( "42" );
        news.setImage( "Image" );
        news.setName( "Name" );
        news.setSoftDelete( false );
        news.setComments( new HashSet<>() );
        news.setTimestamp( LocalDateTime.of( 1, 1, 1, 1, 1 ) );
        return news;
    }

    private Category getCategory() {

        Category category = new Category();
        category.setDeleted( false );
        category.setDescription( "The characteristics of someone or something" );
        category.setId( "42" );
        category.setImage( "Image" );
        category.setName( "Name" );
        category.setTimestamp( Timestamp.from( LocalDateTime.of( 1, 1, 1, 1, 1, 1 ).toInstant( ZoneOffset.UTC ) ) );
        return category;
    }

    private List<News> getNewsList() {

        News news = getNews( getCategory() );
        return List.of( news, news, news );
    }

    private PageImpl<News> getPage(List<News> newsList, Pageable pageable, int total) {

        return new PageImpl<>( newsList, pageable, total );
    }

    private NewDTO getNewsDto(News news) {

        NewDTO newsDto = new NewDTO();
        newsDto.setName( "News updated" );
        newsDto.setTimestamp( news.getTimestamp() );
        newsDto.setContent(  "News updated" );
        newsDto.setImage( "News updated" );
        newsDto.setSoftDelete( news.isSoftDelete() );
        Category newsCategory = new Category();
        newsCategory.setName( "Category updated" );
        newsDto.setCategory( getCategoryDto( newsCategory ) );
        return newsDto;
    }

    private CategoryDto getCategoryDto(Category category) {

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName( category.getName() );
        return categoryDto;
    }

    /**
     * Method under test: {@link NewsServiceImpl#updateNews(String, NewDTO, BindingResult)}
     */
    @Test
    void UpdateNEWS_cuando_no_encuentra_una_categoria_deberia_tirar_una_excepcion() {

        //given
        Category category = getCategory();
        News news = getNews( category );
        NewDTO newsDTO = getNewsDto( news );
        BindingResult bindingResult = mock( BindingResult.class );

        //when
        when( newsRepository.findById( anyString() ) ).thenReturn( Optional.of( news ) );
        when( categoryRepository.findByName( anyString() ) ).thenReturn( Optional.empty() );

        //then
        assertThrows( RecordException.RecordNotFoundException.class,
                () -> newsServiceImpl.updateNews( "42", newsDTO, bindingResult ) );
        verify( newsRepository ).findById( anyString() );
        verify( modelMapper, atMostOnce() ).map( any( News.class ), eq( NewDTO.class ) );
        verify( modelMapper, atMostOnce() ).map( any( Category.class ), eq( CategoryDto.class ) );
        verify( categoryRepository, atMostOnce() ).findByName( anyString() );
    }

    /**
     * Method under test: {@link NewsServiceImpl#updateNews(String, NewDTO, BindingResult)}
     */
    @Test
    void UpdateNEWS_no_deberia_encontrar_la_novedad_y_tirar_una_exception() {
        //given
        NewDTO newsDTO = new NewDTO();
        BindingResult bindingResult = mock( BindingResult.class );

        //when
        when( newsRepository.findById( anyString() ) ).thenReturn( Optional.empty() );
        when( bindingResult.hasFieldErrors() ).thenReturn( false );

        //then
        assertThrows( RecordException.RecordNotFoundException.class,
                () -> newsServiceImpl.updateNews( "42", newsDTO, bindingResult ) );
        verify( newsRepository ).findById( anyString() );
        verifyNoInteractions( modelMapper );
    }

    /**
     * Method under test: {@link NewsServiceImpl#updateNews(String, NewDTO, BindingResult)}
     */
    @Test
    void UpdateNEWS_deberia_tirar_una_excepcion_al_existir_un_error_de_validacion() {

        //given
        Category category = getCategory();
        News news = getNews( category );
        Optional<News> ofResult = Optional.of( news );
        BindingResult bindingResult = mock( BindingResult.class );
        NewDTO newsDTO = getNewsDto( news );

        //when
        when( newsRepository.findById( anyString() ) ).thenReturn( ofResult );
        when( bindingResult.getFieldErrors() ).thenReturn( new ArrayList<>() );
        when( bindingResult.hasFieldErrors() ).thenReturn( true );

        //then
        assertThrows( BindingResultException.class, () -> newsServiceImpl.updateNews( "42", newsDTO,
                bindingResult ) );
        verify( bindingResult ).hasFieldErrors();
        verify( bindingResult ).getFieldErrors();
        verifyNoInteractions( categoryRepository );
        verifyNoInteractions( newsRepository );
    }

    /**
     * Method under test: {@link NewsServiceImpl#updateNews(String, NewDTO, BindingResult)}
     */
    @Test
    void UpdateNEWS_cuando_actualiza_deberia_actualizar_la_categoria() {

        //given
        Category category = getCategory();
        News news = getNews( category );
        NewDTO dto = getNewsDto( news );
        BindingResult bindingResult = mock( BindingResult.class );
        ArgumentCaptor<News> argumentCaptor = ArgumentCaptor.forClass( News.class );
        News updatedNews = getUpdatedNews(dto);

        //when
        when( newsRepository.findById( anyString() ) ).thenReturn( Optional.of( news ) );
        when( categoryRepository.findByName( anyString() ) ).thenReturn( Optional.of( updatedNews.getCategory() ) );
        when( bindingResult.hasFieldErrors() ).thenReturn( false );
        when( newsRepository.save( argumentCaptor.capture() ) ).
                thenReturn( updatedNews );
        //then
        NewDTO updateNews = newsServiceImpl.updateNews( "42", dto, bindingResult );
        verify( newsRepository ).save( any() );
        verify( modelMapper, atLeast( 6 ) ).map( any(), any() );
        verify( bindingResult ).hasFieldErrors();

        News captorValue = argumentCaptor.getValue();
        assertThat( dto.getCategory().getName() )
                .isEqualTo( captorValue.getCategory().getName() )
                .isEqualTo( updateNews.getCategory().getName() )
                .isNotEqualToIgnoringCase( news.getCategory().getName() );
        assertThat( captorValue.getName() )
                .isEqualTo( dto.getName() )
                .isEqualTo( updateNews.getName() )
                .isNotEqualToIgnoringCase( news.getName() );
        assertThat( captorValue.getImage() )
                .isEqualTo( dto.getImage() )
                .isEqualTo( updateNews.getImage() )
                .isNotEqualToIgnoringCase( news.getImage() );
        assertThat( captorValue.getContent() )
                .isEqualTo( dto.getContent() )
                .isEqualTo( updateNews.getContent() )
                .isNotEqualToIgnoringCase( news.getName() );
        assertThat( captorValue.getTimestamp() )
                .isEqualTo( dto.getTimestamp() )
                .isEqualTo( updateNews.getTimestamp() );
        assertThat( captorValue.getTimestamp() )
                .isEqualTo( dto.getTimestamp() )
                .isEqualTo( updateNews.getTimestamp() );


    }
    /**
     * Method under test: {@link NewsServiceImpl#updateNews(String, NewDTO, BindingResult)}
     */
    @Test
    void UpdateNEWS_deberia_actualizar_los_datos_devolver_un_objecto_no_nulo_y_no_tirar_excepciones() {

        //given
        Category category = getCategory();
        News news = getNews( category );
        Optional<News> ofResult = Optional.of( news );
        NewDTO dto = getNewsDto( news );
        News updatedNews = getUpdatedNews(dto);
        BindingResult bindingResult = mock( BindingResult.class );

        //when
        when( newsRepository.findById( anyString() ) ).thenReturn( ofResult );
        when( categoryRepository.findByName( anyString() ) ).thenReturn( Optional.of( category ) );
        when( bindingResult.getFieldErrors() ).thenReturn( new ArrayList<>() );
        when( bindingResult.hasFieldErrors() ).thenReturn( false );
        when( categoryRepository.findByName( anyString() ) ).thenReturn( Optional.of( category ) );
        when( newsRepository.save( any(News.class) ) ).thenReturn( updatedNews );

        //then
        assertDoesNotThrow( () -> newsServiceImpl.updateNews( "42", dto, bindingResult ) );
        verify( newsRepository, atMost( 2 ) ).save( any( News.class ) );
        NewDTO updateNews = newsServiceImpl.updateNews( "42", dto, bindingResult );
        assertNotNull( updateNews );
    }

    private News getUpdatedNews(NewDTO dto) {
        Category category = getCategory();
        category.setName( dto.getCategory().getName() );
        News news = getNews( category );
        news.setName( dto.getName() );
        news.setContent( dto.getContent() );
        news.setImage( dto.getImage() );
        return news;
    }

    /**
     * Method under test: {@link NewsServiceImpl#getNews(String)}
     */
    @Test
    void GetNEWS_cuando_encuentra_una_novedad_por_id_deberia_devolverla() throws Exception {

        //given
        Category category = getCategory();
        News news = getNews( category );
        Optional<News> ofResult = Optional.of( news );

        //when
        when( newsRepository.findById( anyString() ) ).thenReturn( ofResult );

        //then
        NewDTO actual = newsServiceImpl.getNews( "42" );
        assertThat( actual ).isNotNull();
        assertThat( actual.getName() ).isEqualTo( news.getName() );
        assertThat( actual.getImage() ).isEqualTo( news.getImage() );
        assertThat( actual.getContent() ).isEqualTo( news.getContent() );
        assertThat( actual.getCategory().getName() ).isEqualTo( news.getCategory().getName() );
        verify( newsRepository ).findById( any() );
        verify( modelMapper ).map( any(), any() );
    }

    /**
     * Method under test: {@link NewsServiceImpl#getNews(String)}
     */
    @Test
    void GetNEWS_cuando_se_pasa_un_valor_nulo_deberia_tirar_una_excepcion() {

        //given
        //when
        when( newsRepository.findById( any() ) ).thenThrow(new IllegalArgumentException("Error") );

        //then
        assertThatThrownBy( () -> newsServiceImpl.getNews( "42" ) )
                .isInstanceOf( IllegalArgumentException.class )
                .hasMessage( "Error" )
                .isNotNull();
        verify( newsRepository ).findById( any() );
        verifyNoInteractions( modelMapper );
    }

    /**
     * Method under test: {@link NewsServiceImpl#getNews(String)}
     */
    @Test
    void GetNEWS_cuando_no_encuentra_la_novedad_por_id_deberia_tirar_una_excepcion() {

        when( newsRepository.findById( any() ) ).thenReturn( Optional.empty() );
        assertThatThrownBy( () -> newsServiceImpl.getNews( "42" ) )
                .isExactlyInstanceOf( Exception.class )
                .hasMessage( "New not found" );
        verify( newsRepository ).findById( any() );
        verifyNoInteractions( modelMapper );
    }

    /**
     * Method under test: {@link NewsServiceImpl#getAllNews(int, String)}
     */
    @Test
    void GetAllNEWS_deberia_listar_todos_los_elementos_y_no_tirar_una_excepcion() {
        //given
        List<News> newsList = getNewsList();
        Pageable pageable = PageUtils.getPageable( 1, "asc" );

        PageDto<News> pageDto = PageUtils.getPageDto( getPage( newsList, pageable, 3 ), "" );

        //when
        when( newsRepository.findAll( any( Pageable.class ) ) ).thenReturn( pageDto );

        //then
        PageDto<NewDTO> allNews = newsServiceImpl.getAllNews( 1, "Order" );
        assertThat( allNews ).isNotNull();
        assertThat( allNews.getSize() ).isEqualTo( 10 );
        Optional<Sort.Order> orderOptional = allNews.getSort().stream().findFirst();
        assertThat( orderOptional ).isPresent();
        assertThat( orderOptional.get().getDirection().isAscending() ).isTrue();
        assertThat( pageDto.getContent().size() ).isEqualTo( 3 );
        assertThat( pageDto.getTotalElements() ).isEqualTo(  13 );
        verify( newsRepository ).findAll( any( Pageable.class ) );
        verify( modelMapper, atMost( 3 ) ).map( any(), any() );
    }

    /**
     * Method under test: {@link NewsServiceImpl#getAllNews(int, String)}
     */
    @Test
    void GetAllNEWS_deberia_listar_todos_los_elementos_en_desc_y_no_tirar_una_excepcion() {

        //given
        List<News> newsList = getNewsList();
        Pageable pageable = PageUtils.getPageable( 1, "desc" );
        //when
        when( newsRepository.findAll( any(Pageable.class) ) ).thenReturn( getPage( newsList, pageable, 10 ) );

        //then
        PageDto<NewDTO> order = newsServiceImpl.getAllNews( 1, "desc" );

        assertThat( order.getSize()).isEqualTo( 10 );
        Optional<Sort.Order> orderOptional = order.getSort().get().findFirst();
        assertThat( orderOptional ).isPresent();
        assertThat( orderOptional.get().isAscending()).isEqualTo( false );
        assertThat( orderOptional.get().isDescending()).isEqualTo( true );
        assertThat( order.getContent().size()).isEqualTo( 3 );


    }

    /**
     * Method under test: {@link NewsServiceImpl#getAllNews(int, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllNews3() {

        Page<News> page = new PageImpl<>( new ArrayList<>() );
        when( newsRepository.findAll( any( Pageable.class ) ) ).thenReturn( page );

    }

    /**
     * Method under test: {@link NewsServiceImpl#deleteNews(String)}
     */
    @Test
    void DeleteNEWS_cuando_elimina_un_elemento_devolver_verdadero() {

        doNothing().when( newsRepository ).deleteById( any() );
        assertThat( newsServiceImpl.deleteNews( "42" ) ).isTrue();
        verify( newsRepository ).deleteById( any() );
    }

    /**
     * Method under test: {@link NewsServiceImpl#deleteNews(String)}
     */
    @Test
    void DeleteNEWS_cuando_tira_una_excepcion_deberia_devolver_falso() {

        doThrow( new IllegalArgumentException() ).when( newsRepository )
                .deleteById( any() );
        assertThat( newsServiceImpl.deleteNews( "42" ) ).isFalse();
        verify( newsRepository ).deleteById( any() );
    }



    /**
     * Method under test: {@link NewsServiceImpl#createNews(News)}
     */
    @Test
    void CreateNEWS_crear_una_novedad_con_categoria_deberia_guardarla() {

        Category category = getCategory();
        News news = getNews( category );
        when( newsRepository.save( any() ) ).thenReturn( news );

        newsServiceImpl.createNews( news );
        verify( newsRepository ).save( any() );
        assertThat(category).isEqualTo( news.getCategory() );
        assertThat( news.isSoftDelete() ).isFalse();
        assertThat( news.getComments().isEmpty() ).isTrue();
        assertThat( news.getId()).isEqualTo( "42" );
        assertThat(  news.getImage() ).isEqualTo( "Image" );
        assertThat(  news.getContent() ).isEqualTo( "Not all who wander are lost" );
        assertThat(  news.getName() ).isEqualTo( "Name" );
        assertThat(  news.getTimestamp().toLocalDate().toString() ).isEqualTo( "0001-01-01" );
    }

    /**
     * Method under test: {@link NewsServiceImpl#createNews(News)}
     */
    @Test
    void CreateNEWS_cuando_se_pasa_un_valor_nulo_tirar_una_excepcion() {

        when( newsRepository.save( any() ) )
                .thenThrow( new IllegalArgumentException() );

        Category category = getCategory();

        News news = getNews( category );
        assertThrows( IllegalArgumentException.class, () -> newsServiceImpl.createNews( news ) );
        verify( newsRepository ).save( any() );
    }
    /**
     * Method under test: {@link NewsServiceImpl#findNewsById(String)}
     */
    @Test
    void FindNewsById_cuando_busca_la_novedad_por_id_y_encuentra_deberia_devolver_la_entidad() {

        Category category = getCategory();
        News news = getNews( category );
        Optional<News> ofResult = Optional.of( news );
        when( newsRepository.findById( any() ) ).thenReturn( ofResult );
        assertSame( news, newsServiceImpl.findNewsById( "42" ) );
        verify( newsRepository ).findById( any() );
    }

    /**
     * Method under test: {@link NewsServiceImpl#findNewsById(String)}
     */
    @Test
    void FindNewsById_cuando_no_encuentra_una_entidad_deberia_devolver_null() {

        when( newsRepository.findById( any() ) )
                .thenReturn( Optional.empty() );
        News newsById = newsServiceImpl.findNewsById( "42" );
        verify( newsRepository ).findById( any() );
        assertThat( newsById ).isNull();
    }

    /**
     * Method under test: {@link NewsServiceImpl#findNewsById(String)}
     */
    @Test
    void FindsNEWSById_cuando_se_pasa_un_valor_nulo_deberia_tirar_la_excepcion() {

        //given
        //when
        when( newsRepository.findById( anyString() ) ).thenThrow( new IllegalArgumentException("Error") );

        //then
        assertThatThrownBy(
                ()-> newsServiceImpl.findNewsById( anyString() ) )
                .isInstanceOf( IllegalArgumentException.class )
                .isExactlyInstanceOf( IllegalArgumentException.class )
                .hasMessage( "Error" )
                .isNotNull();
        verify( newsRepository ).findById( any() );
    }
}

