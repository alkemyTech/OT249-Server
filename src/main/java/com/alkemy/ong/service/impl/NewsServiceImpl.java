package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.dto.NewDTO;
import com.alkemy.ong.exceptions.BindingResultException;
import com.alkemy.ong.exceptions.RecordException;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.model.News;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.service.NewsService;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;

    @Override
    public NewDTO getNews(UUID id) {

        Optional<News> answer = newsRepository.findById(id);

        if (answer.isPresent()){
            News news = answer.get();
            NewDTO newDTO = modelMapper.map(news,NewDTO.class);

            return newDTO;
        }
        return null;
    }

    @Override
    public List<NewDTO> getAllNews() {

        return null;
    }

    @Override
    public boolean deleteNews(UUID id) {
    	try {
    		newsRepository.deleteById(id);
    		return true;
    	} catch (Exception e) {
    		return false;
    	}
    }

    @Override
    public NewDTO updateNews(UUID id, NewDTO newsDTO, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors())
            throw new BindingResultException( bindingResult );
        News found = newsRepository.findById( id ).orElseThrow( () -> new RecordException.RecordNotFoundException( "News not found" ) );

        NewDTO newDTO = this.modelMapper.map( found, NewDTO.class );
        CategoryDto categoryDto = this.modelMapper.map( newsDTO.getCategory(), CategoryDto.class );
        if (categoryDto.getName() != null) {
            Category category = categoryRepository.findByName( categoryDto.getName() ).orElseThrow( () -> new RecordException.RecordNotFoundException( "Category not found" ) );
            categoryDto = this.modelMapper.map( category, CategoryDto.class );
        }
        newDTO.setContent( newsDTO.getContent() );
        newDTO.setImage( newsDTO.getImage() );
        newDTO.setName( newsDTO.getName() );
        newDTO.setCategory( categoryDto );

        News newsToSave = this.modelMapper.map( newDTO, News.class );
        if (categoryDto != null)
            newsToSave.setCategory( this.modelMapper.map( categoryDto, Category.class ) );
        News savedNews = newsRepository.save( newsToSave );
        return this.modelMapper.map( savedNews, NewDTO.class );
    }

	@Override
	public News findNewsById(UUID id) {
		Optional<News> newsOptional = newsRepository.findById(id);
		if (newsOptional.isPresent()) {
			return newsOptional.get();
		} else {
			return null;
		}
	}
}
