package com.alkemy.ong.Utils;

import com.alkemy.ong.dto.PageDto;
import com.alkemy.ong.model.Category;
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
            links.setNextLink( String.valueOf( page.getPageable().next().getPageNumber() ) );
        else if (page.getTotalPages() > page.getPageable().getPageNumber())
            links.setNextLink( String.valueOf( page.getPageable().getPageNumber() ) );
        else if(page.getTotalPages() > 0)
            links.setNextLink( String.valueOf( page.getTotalPages() - 1 ) );
        else
            links.setNextLink( String.valueOf( page.getTotalPages() ) );


        if (page.getTotalPages() > page.getPageable().getPageNumber())
            links.setPrevLink( String.valueOf( page.getPageable().previousOrFirst().getPageNumber() ) );
        else
            links.setPrevLink( String.valueOf( page.getPageable().first().getPageNumber() ) );


        return links;
    }
}
