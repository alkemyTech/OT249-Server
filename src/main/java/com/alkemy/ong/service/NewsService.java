package com.alkemy.ong.service;

import com.alkemy.ong.dto.NewDTO;
import com.alkemy.ong.dto.PageDto;
import com.alkemy.ong.model.News;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.UUID;

public interface NewsService {
    NewDTO getNews(String id) throws Exception;
    PageDto<NewDTO> getAllNews(int page, String order);
    boolean deleteNews(String id);
    NewDTO updateNews(String id, NewDTO newsDTO, BindingResult bindingResult);
    News findNewsById(String id);
    void createNews(News news);
}
