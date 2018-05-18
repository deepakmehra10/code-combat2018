package com.knoldus.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectResource {
    
    String employeeId;
    
    String employeeName;
    
    String employeeDesignation;
    
    LoginType loginType;
    
    String manager;
    
    String projectName;
    
}
