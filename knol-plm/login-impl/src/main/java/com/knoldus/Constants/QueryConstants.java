package com.knoldus.Constants;

public class QueryConstants {
    
    public static final String LOGIN_TYPE_BY_USERNAME = "SELECT * FROM geospatial"
            + ".geojsonlist_by_voyage_code_v1 WHERE username = ? and password=?";
}
