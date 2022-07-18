package com.alkemy.ong.service;

import com.alkemy.ong.model.News;
import com.alkemy.ong.repository.NewsRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class NewsServiceImpl implements NewsService {

    private NewsRepository newsRepository;
    @Override
    public News getNews(Long id) {

        return null;
    }

    @Override
    public List<News> getAllNews() {

        return null;
    }

    @Override
    public void deleteNews(Long id) {

    }

    @Override
    public News updateNews(News news, Long id) {

        return null;
    }
}
