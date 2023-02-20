package com.seedon.SeedOnTanda.common.request;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public record PageableRequest(
        int pageNo,
        int pageSize,
        String direction,
        String[] sortBy


) {
    public PageRequest createPageRequest() {
        return PageRequest.of(
                pageNo,
                pageSize,
                Sort.by(Sort.Direction.valueOf(direction.toUpperCase()), sortBy)
        );
    }
}

