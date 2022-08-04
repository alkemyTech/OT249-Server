package com.alkemy.ong.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@Setter
@Getter
public class PageDto<T> extends PageImpl<T> {
    private Links _links;

    @Override
    @JsonIgnore
    public Sort getSort() {

        return super.getSort();
    }

    @Override
    @JsonIgnore
    public Pageable getPageable() {

        return super.getPageable();
    }

    public PageDto(List<T> content, Pageable pageable, long total) {

        super( content, pageable, total );
    }

    public PageDto(List<T> content, Pageable pageable, long total, Links _links) {

        super( content, pageable, total );
        this._links = _links;
    }

    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class Links{
        private String nextOrLast;
        private String prevOrFirst;
        private String prefix;

        public String getNextOrLast() {

            return "/" + prefix + "?page="+ nextOrLast;
        }

        public String getPrevOrFirst() {

            return "/" + prefix + "?page="+ prevOrFirst;
        }

    }
}
