package com.alkemy.ong.service;

import com.alkemy.ong.dto.NewDTO;
import com.alkemy.ong.model.News;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface NewsService {
    News getNews(String id);
    List<News> getAllNews();
    void deleteNews(String id);

    NewDTO updateNews(String id, NewDTO newsDTO, BindingResult bindingResult);
}
