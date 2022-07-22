package com.alkemy.ong.Utils;

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

}
