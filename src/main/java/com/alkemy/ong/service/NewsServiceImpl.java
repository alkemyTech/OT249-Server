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
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
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
    public List<News> getAllNews() {

        return null;
    }

    @Override
    public void deleteNews(UUID id) {

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
