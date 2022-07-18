package com.alkemy.ong.service;

import com.alkemy.ong.model.News;

import java.util.List;

public interface NewsService {
    News getNews(Long id);
    List<News> getAllNews();
    void deleteNews(Long id);
    News updateNews(News news, Long id);
}
