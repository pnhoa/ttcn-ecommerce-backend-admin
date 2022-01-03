package com.ttcn.ecommerce.backend.app.utils;

import org.springframework.data.domain.Sort;

public class CommonUtils {

    public static Sort.Direction getSortDirection(String sort) {
        return sort.contains("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
    }
}
