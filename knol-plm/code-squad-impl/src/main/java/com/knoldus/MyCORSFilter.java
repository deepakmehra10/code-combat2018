package com.knoldus;

import play.filters.cors.CORSFilter;
import play.http.DefaultHttpFilters;

import javax.inject.Inject;

// See https://playframework.com/documentation/2.5.x/CorsFilter
public class MyCORSFilter extends DefaultHttpFilters {
    @Inject
    public MyCORSFilter(CORSFilter corsFilter) {
        super(corsFilter);
    }
}
