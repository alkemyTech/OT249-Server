package com.alkemy.ong.service;

import com.alkemy.ong.model.News;
import com.alkemy.ong.repository.NewsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
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
    public News updateNews(News news, String id) {

        return null;
    }
}
