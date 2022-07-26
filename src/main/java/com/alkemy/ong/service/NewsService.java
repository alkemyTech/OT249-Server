package com.alkemy.ong.service;

import com.alkemy.ong.dto.NewDTO;
import com.alkemy.ong.model.News;

import java.util.List;
import java.util.UUID;

public interface NewsService {
    NewDTO getNews(UUID id);
    List<NewDTO> getAllNews();
    void deleteNews(UUID id);
    NewDTO updateNews(NewDTO news, UUID id);
    public void createNews(News news);
}
