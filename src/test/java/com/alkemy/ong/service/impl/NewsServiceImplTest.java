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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindingResult;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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

    private News getUpdatedNews(NewDTO dto) {

        Category category = getCategory();
        category.setName( dto.getCategory().getName() );
        News news = getNews( category );
        news.setName( dto.getName() );
        news.setContent( dto.getContent() );
        news.setImage( dto.getImage() );
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
        newsDto.setContent( "News updated" );
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
    void UpdateNEWS_cuando_la_categoria_no_se_encuentra_deberia_tirar_una_excepcion() {

        //given
        Category category = getCategory();
        News news = getNews( category );
        NewDTO newsDTO = getNewsDto( news );
        BindingResult bindingResult = mock( BindingResult.class );

        //when
        when( newsRepository.findById( anyString() ) ).thenReturn( Optional.of( news ) );
        when( categoryRepository.findByName( anyString() ) ).thenReturn( Optional.empty() );

        //then
        assertThatThrownBy(
                () -> newsServiceImpl.updateNews( "42", newsDTO, bindingResult ) ).isInstanceOf( RecordException.RecordNotFoundException.class );
        verify( newsRepository ).findById( anyString() );
        verify( modelMapper, atMostOnce() ).map( any( News.class ), eq( NewDTO.class ) );
        verify( modelMapper, atMostOnce() ).map( any( Category.class ), eq( CategoryDto.class ) );
        verify( categoryRepository, atMostOnce() ).findByName( anyString() );
    }

    /**
     * Method under test: {@link NewsServiceImpl#updateNews(String, NewDTO, BindingResult)}
     */
    @Test
    void UpdateNEWS_cuando_no_encuentra_la_novedad_deberia_tirar_una_exception() {
        //given
        NewDTO newsDTO = new NewDTO();
        BindingResult bindingResult = mock( BindingResult.class );

        //when
        when( newsRepository.findById( anyString() ) ).thenReturn( Optional.empty() );
        when( bindingResult.hasFieldErrors() ).thenReturn( false );

        //then
        assertThatThrownBy(
                () -> newsServiceImpl.updateNews( "42", newsDTO, bindingResult ) ).isInstanceOf( RecordException.RecordNotFoundException.class );
        verify( newsRepository ).findById( anyString() );
        verifyNoInteractions( modelMapper );
    }

    /**
     * Method under test: {@link NewsServiceImpl#updateNews(String, NewDTO, BindingResult)}
     */
    @Test
    void UpdateNEWS_cuando_existe_un_error_de_validacion_deberia_tirar_una_excepcion() {

        //given
        Category category = getCategory();
        News news = getNews( category );
        BindingResult bindingResult = mock( BindingResult.class );
        NewDTO newsDTO = getNewsDto( news );

        //when
        when( bindingResult.hasFieldErrors() ).thenReturn( true );

        //then
        assertThatThrownBy( () -> newsServiceImpl.updateNews( "42", newsDTO,
                bindingResult ) ).isInstanceOf( BindingResultException.class );
        verify( bindingResult ).hasFieldErrors();
        verify( bindingResult ).getFieldErrors();
        verifyNoInteractions( categoryRepository );
        verifyNoInteractions( newsRepository );
    }

    /**
     * Method under test: {@link NewsServiceImpl#updateNews(String, NewDTO, BindingResult)}
     */
    @Test
    void UpdateNEWS_cuando_hay_una_condicion_de_categoria_nula_no_deberia_actualizar_categoria() {


        //given
        Category category = getCategory();
        News news = getNews( category );
        Optional<News> ofResult = Optional.of( news );
        BindingResult bindingResult = mock( BindingResult.class );
        NewDTO newsDTO = getNewsDto( news );
        ModelMapper localModelMapper = mock( ModelMapper.class );
        newsServiceImpl = new NewsServiceImpl( newsRepository, categoryRepository, localModelMapper );

        //when
        when( localModelMapper.map( any(), eq( CategoryDto.class ) ) ).thenReturn( new CategoryDto() );
        when( localModelMapper.map( any(), eq( NewDTO.class ) ) ).thenReturn( new NewDTO() );
        when( localModelMapper.map( any( NewDTO.class ), eq( News.class ) ) ).thenReturn( news );
        when( newsRepository.findById( anyString() ) ).thenReturn( ofResult );
        when( newsRepository.save( any() ) ).thenReturn( news );
        when( bindingResult.hasFieldErrors() ).thenReturn( false );

        NewDTO actual = newsServiceImpl.updateNews( "42", newsDTO,
                bindingResult );

        //then
        assertThat( actual ).isNotNull();
        assertThat( actual.getCategory().getName() ).isNull();
        verify( bindingResult ).hasFieldErrors();
        verifyNoInteractions( categoryRepository );
        verify( newsRepository ).save( any() );
        verify( newsRepository ).findById( anyString() );
        verify( localModelMapper, atMost( 5 ) ).map( any(), any() );
    }

    /**
     * Method under test: {@link NewsServiceImpl#updateNews(String, NewDTO, BindingResult)}
     */
    @Test
    void UpdateNEWS_cuando_se_pasa_una_categoria_nula_no_deberia_actualizar_categoria() {

        //given
        Category category = getCategory();
        News news = getNews( category );
        Optional<News> ofResult = Optional.of( news );
        BindingResult bindingResult = mock( BindingResult.class );
        NewDTO newsDTO = getNewsDto( news );
        ModelMapper localModelMapper = mock( ModelMapper.class );
        newsServiceImpl = new NewsServiceImpl( newsRepository, categoryRepository, localModelMapper );

        //when
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName( category.getName() );
        when( localModelMapper.map( any( CategoryDto.class ), eq( CategoryDto.class ) ) ).thenReturn( categoryDto );
        when( localModelMapper.map( any( Category.class ), eq( CategoryDto.class ) ) ).thenReturn( null );
        when( localModelMapper.map( any(), eq( NewDTO.class ) ) ).thenReturn( new NewDTO() );
        when( localModelMapper.map( any( NewDTO.class ), eq( News.class ) ) ).thenReturn( news );
        when( newsRepository.findById( anyString() ) ).thenReturn( ofResult );
        when( newsRepository.save( any() ) ).thenReturn( news );
        when( bindingResult.hasFieldErrors() ).thenReturn( false );
        when( categoryRepository.findByName( anyString() ) ).thenReturn( Optional.of( category ) );

        NewDTO actual = newsServiceImpl.updateNews( "42", newsDTO,
                bindingResult );

        //then
        assertThat( actual ).isNotNull();
        assertThat( actual.getCategory() ).isNull();
        verify( bindingResult ).hasFieldErrors();
        verify( categoryRepository ).findByName( anyString() );
        verify( newsRepository ).save( any() );
        verify( newsRepository ).findById( anyString() );
        verify( localModelMapper, atMost( 5 ) ).map( any(), any() );
    }

    /**
     * Method under test: {@link NewsServiceImpl#updateNews(String, NewDTO, BindingResult)}
     */
    @Test
    void UpdateNEWS_cuando_actualiza_la_entidad_deberia_actualizar_la_categoria() {

        //given
        Category category = getCategory();
        News news = getNews( category );
        NewDTO dto = getNewsDto( news );
        BindingResult bindingResult = mock( BindingResult.class );
        ArgumentCaptor<News> argumentCaptor = ArgumentCaptor.forClass( News.class );
        News updatedNews = getUpdatedNews( dto );

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
    void UpdateNEWS_al_actualizar_los_datos_devolver_un_objecto_no_nulo_y_actualizado() {

        //given
        Category category = getCategory();
        News news = getNews( category );
        Optional<News> ofResult = Optional.of( news );
        NewDTO dto = getNewsDto( news );
        News updatedNews = getUpdatedNews( dto );
        BindingResult bindingResult = mock( BindingResult.class );
        ArgumentCaptor<News> argumentCaptor = ArgumentCaptor.forClass( News.class );
        //when
        when( newsRepository.findById( anyString() ) ).thenReturn( ofResult );
        when( categoryRepository.findByName( anyString() ) ).thenReturn( Optional.of( category ) );
        when( bindingResult.hasFieldErrors() ).thenReturn( false );
        when( categoryRepository.findByName( anyString() ) ).thenReturn( Optional.of( category ) );
        when( newsRepository.save( any() ) ).thenReturn( updatedNews );

        //then
        NewDTO updateNews = newsServiceImpl.updateNews( "42", dto, bindingResult );
        verify( newsRepository ).save( argumentCaptor.capture() );
        assertThat( updateNews ).isNotNull();
        News toPersist = argumentCaptor.getValue();
        assertThat( toPersist ).isNotNull();
        assertThat( toPersist.getId() )
                .isEqualTo( "42" );
        assertThat( toPersist.getName() )
                .isEqualTo( dto.getName() )
                .isNotEqualTo( news.getName() );
        assertThat( toPersist.getImage() )
                .isEqualTo( dto.getImage() )
                .isNotEqualTo( news.getName() );
        assertThat( toPersist.getContent() )
                .isEqualTo( dto.getContent() )
                .isNotEqualTo( news.getContent() );
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
        when( newsRepository.findById( anyString() ) ).thenThrow( new IllegalArgumentException( "Error" ) );

        //then
        assertThatThrownBy( () -> newsServiceImpl.getNews( "42" ) )
                .isInstanceOf( IllegalArgumentException.class )
                .hasMessage( "Error" )
                .isNotNull();
        verify( newsRepository ).findById( anyString() );
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
        verify( newsRepository ).findById( anyString() );
        verifyNoInteractions( modelMapper );
    }

    /**
     * Method under test: {@link NewsServiceImpl#getAllNews(int, String)}
     */
    @Test
    void GetAllNEWS_cuando_pasan_valor_valido_deberia_listar_todos_los_elementos_y_no_tirar_una_excepcion() {
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
        assertThat( pageDto.getTotalElements() ).isEqualTo( 13 );
        verify( newsRepository ).findAll( any( Pageable.class ) );
        verify( modelMapper, atMost( 3 ) ).map( any(), any() );
    }

    /**
     * Method under test: {@link NewsServiceImpl#getAllNews(int, String)}
     */
    @Test
    void GetAllNEWS_cuando_pasan_valor_de_order_desc_deberia_listar_todos_los_elementos_en_desc_y_no_tirar_una_excepcion() {

        //given
        List<News> newsList = getNewsList();
        Pageable pageable = PageUtils.getPageable( 1, "desc" );
        //when
        when( newsRepository.findAll( any( Pageable.class ) ) ).thenReturn( getPage( newsList, pageable, 10 ) );

        //then
        PageDto<NewDTO> order = newsServiceImpl.getAllNews( 1, "desc" );

        assertThat( order.getSize() ).isEqualTo( 10 );
        Optional<Sort.Order> orderOptional = order.getSort().get().findFirst();
        assertThat( orderOptional ).isPresent();
        assertThat( orderOptional.get().isAscending() ).isEqualTo( false );
        assertThat( orderOptional.get().isDescending() ).isEqualTo( true );
        assertThat( order.getContent().size() ).isEqualTo( 3 );


    }

    /**
     * Method under test: {@link NewsServiceImpl#deleteNews(String)}
     */
    @Test
    void DeleteNEWS_cuando_elimina_un_elemento_devolver_verdadero() {
        //given
        //when
        doNothing().when( newsRepository ).deleteById( any() );
        //then
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
    void CreateNEWS_cuando_se_crea_una_novedad_con_categoria_deberia_guardarla() {

        Category category = getCategory();
        News news = getNews( category );
        when( newsRepository.save( any() ) ).thenReturn( news );

        newsServiceImpl.createNews( news );
        verify( newsRepository ).save( any() );
        assertThat( category ).isEqualTo( news.getCategory() );
        assertThat( news.isSoftDelete() ).isFalse();
        assertThat( news.getComments().isEmpty() ).isTrue();
        assertThat( news.getId() ).isEqualTo( "42" );
        assertThat( news.getImage() ).isEqualTo( "Image" );
        assertThat( news.getContent() ).isEqualTo( "Not all who wander are lost" );
        assertThat( news.getName() ).isEqualTo( "Name" );
        assertThat( news.getTimestamp().toLocalDate().toString() ).isEqualTo( "0001-01-01" );
    }

    /**
     * Method under test: {@link NewsServiceImpl#createNews(News)}
     */
    @Test
    void CreateNEWS_cuando_se_crea_una_novedad_con_categoria_deberia_ser_iguales_los_valores_que_se_guardan() {
        //given

        Category category = getCategory();
        News news = getNews( category );
        ArgumentCaptor<News> argumentCaptor = ArgumentCaptor.forClass( News.class );

        //when
        when( newsRepository.save( any() ) ).thenReturn( news );
        newsServiceImpl.createNews( news );
        //then
        verify( newsRepository ).save( argumentCaptor.capture() );
        News value = argumentCaptor.getValue();

        assertThat( value ).isNotNull();
        assertThat( news.getName() )
                .isEqualTo( value.getName() );
        assertThat( news.getImage() )
                .isEqualTo( value.getImage() );
        assertThat( news.getContent() )
                .isEqualTo( value.getContent() );
        assertThat( category ).isEqualTo( news.getCategory() );
        assertThat( news.isSoftDelete() ).isFalse();
        assertThat( news.getComments().isEmpty() ).isTrue();
        assertThat( news.getId() ).isEqualTo( "42" );
        assertThat( news.getImage() ).isEqualTo( "Image" );
        assertThat( news.getContent() ).isEqualTo( "Not all who wander are lost" );
        assertThat( news.getName() ).isEqualTo( "Name" );
        assertThat( news.getTimestamp().toLocalDate().toString() ).isEqualTo( "0001-01-01" );
    }

    /**
     * Method under test: {@link NewsServiceImpl#createNews(News)}
     */
    @Test
    void CreateNEWS_cuando_se_pasa_un_valor_nulo_deberia_tirar_una_excepcion() {
        //given
        Category category = getCategory();
        News news = getNews( category );


        //when
        when( newsRepository.save( any() ) )
                .thenThrow( new IllegalArgumentException() );

        //then
        assertThatThrownBy( () -> newsServiceImpl.createNews( news ) ).isInstanceOf( IllegalArgumentException.class );
        verify( newsRepository ).save( any() );
    }

    /**
     * Method under test: {@link NewsServiceImpl#findNewsById(String)}
     */
    @Test
    void FindNewsById_cuando_busca_la_novedad_por_id_y_encontrarla_deberia_devolver_la_entidad() {
        //given

        Category category = getCategory();
        News news = getNews( category );
        Optional<News> ofResult = Optional.of( news );

        //when
        when( newsRepository.findById( any() ) ).thenReturn( ofResult );

        //then
        assertThat( newsServiceImpl.findNewsById( "42" ) ).isSameAs( news );
        verify( newsRepository ).findById( any() );
    }

    /**
     * Method under test: {@link NewsServiceImpl#findNewsById(String)}
     */
    @Test
    void FindNewsById_cuando_no_encuentra_una_entidad_deberia_devolver_null() {
        //given
        //when
        when( newsRepository.findById( any() ) )
                .thenReturn( Optional.empty() );
        News newsById = newsServiceImpl.findNewsById( "42" );
        //then
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
        when( newsRepository.findById( anyString() ) ).thenThrow( new IllegalArgumentException( "Error" ) );

        //then
        assertThatThrownBy(
                () -> newsServiceImpl.findNewsById( anyString() ) )
                .isInstanceOf( IllegalArgumentException.class )
                .isExactlyInstanceOf( IllegalArgumentException.class )
                .hasMessage( "Error" )
                .isNotNull();
        verify( newsRepository ).findById( any() );
    }
}
