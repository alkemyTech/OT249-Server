package com.alkemy.ong.service;

import com.alkemy.ong.model.News;

import java.util.List;

public interface NewsService {
    News getNews(String id);
    List<News> getAllNews();
    void deleteNews(String id);
    News updateNews(News news, String id);
}
