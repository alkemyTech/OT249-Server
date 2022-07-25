package com.alkemy.ong.service;

import com.alkemy.ong.dto.NewDTO;
import com.alkemy.ong.exceptions.BindingResultException;
import com.alkemy.ong.exceptions.RecordException;
import com.alkemy.ong.model.News;
import com.alkemy.ong.repository.NewsRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
@AllArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final ModelMapper modelMapper;
    @Override
    public News getNews(String id) {

        return null;
    }

    @Override
    public List<News> getAllNews() {

        return null;
    }

    @Override
    public void deleteNews(String id) {

    }

    @Override
    public NewDTO updateNews(String id, NewDTO newsDTO, BindingResult bindingResult) {
        if(bindingResult.hasFieldErrors())
            throw new BindingResultException( bindingResult );
        News found = newsRepository.findById( id ).orElseThrow( () -> new RecordException.RecordNotFoundException( "News not found" ) );
        NewDTO newDTO = this.modelMapper.map(found, NewDTO.class);
        newDTO.setContent( newsDTO.getContent() );
        newDTO.setImage( newsDTO.getImage() );
        newDTO.setName( newsDTO.getName() );
        News newsToSave = this.modelMapper.map(newDTO, News.class);
        News savedNews = newsRepository.save( newsToSave );
        return this.modelMapper.map(savedNews, NewDTO.class);
    }

}
