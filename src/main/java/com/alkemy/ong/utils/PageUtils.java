package com.alkemy.ong.utils;

import com.alkemy.ong.dto.PageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import static org.springframework.data.domain.Sort.by;

@Component
public class PageUtils {

    public static Pageable getPageable(int page, String order) {

        Sort.Direction direction = order.equalsIgnoreCase( "desc" ) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort.Order sort = new Sort.Order( direction, "id" );
        return PageRequest.of( page, 10, by( sort ) );
    }

    public static <T> PageDto.Links createLinks(Page<T> page, String prefix) {

        PageDto.Links links = new PageDto.Links();
        links.setPrefix(prefix);
        if (page.hasNext())
            links.setNextOrLast( String.valueOf( page.getPageable().next().getPageNumber() ) );
        else if (page.getTotalPages() > page.getNumber())
            links.setNextOrLast( String.valueOf( page.getPageable().getPageNumber() ) );
        else if(page.getTotalPages() > 0)
            links.setNextOrLast( String.valueOf( page.getTotalPages() - 1 ) );
        else
            links.setNextOrLast( String.valueOf( page.getTotalPages() ) );


        if (page.getTotalPages() > page.getPageable().getPageNumber())
            links.setPrevOrFirst( String.valueOf( page.getPageable().previousOrFirst().getPageNumber() ) );
        else
            links.setPrevOrFirst( String.valueOf( page.getPageable().first().getPageNumber() ) );


        return links;
    }

    public static <T> PageDto<T> getPageDto(Page<T> pageDto, String prefix) {
        PageDto.Links pageLinks = PageUtils.createLinks(pageDto, prefix);
        return new PageDto<>( pageDto.getContent(), pageDto.getPageable(), pageDto.getTotalElements(), pageLinks );
    }
}
