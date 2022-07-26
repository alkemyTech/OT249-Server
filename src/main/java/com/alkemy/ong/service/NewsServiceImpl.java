package com.alkemy.ong.service;

import com.alkemy.ong.dto.NewDTO;
import com.alkemy.ong.model.News;
import com.alkemy.ong.repository.NewsRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
    public List<NewDTO> getAllNews() {

        return null;
    }

    @Override
    public void deleteNews(UUID id) {

    }

    @Override
    public NewDTO updateNews(NewDTO news, UUID id) {

        return null;
    }

	@Override
	public void createNews(News news) {
		
		newsRepository.save(news);
		
	}
}
