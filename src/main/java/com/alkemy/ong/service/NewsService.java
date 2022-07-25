package com.alkemy.ong.service;

import com.alkemy.ong.dto.NewDTO;
import com.alkemy.ong.model.News;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.UUID;

public interface NewsService {
    NewDTO getNews(UUID id);
    List<News> getAllNews();
    void deleteNews(UUID id);

    NewDTO updateNews(String id, NewDTO newsDTO, BindingResult bindingResult);
}
