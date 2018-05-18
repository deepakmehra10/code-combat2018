package com.knoldus.models;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ProjectResource {
    
    Integer employeeId;
    
    String employeeName;
    
    String employeeDesignation;
    
    LoginType loginType;
    
    String manager;
    
    String projectName;
    
}
