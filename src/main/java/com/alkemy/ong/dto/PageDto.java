package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;

@Setter
@Getter
public class PageDto<T> extends PageImpl<T> {
    private Links _links;

    public PageDto(List<T> content, Pageable pageable, long total) {

        super( content, pageable, total );
    }

    @Override
    public <U> Page<U> map(Function<? super T, ? extends U> converter) {

        return super.map( converter );
    }

    public PageDto(List<T> content, Pageable pageable, long total, Links _links) {

        super( content, pageable, total );
        this._links = _links;
    }

    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class Links{
        private String nextLink;
        private String prevLink;
        private String prefix;

        public String getNextLink() {

            return "/" + prefix + "?page="+ nextLink;
        }

        public String getPrevLink() {

            return "/" + prefix + "?page="+ prevLink;
        }

    }
}
