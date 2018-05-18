package com.knoldus.models;

import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Builder
@Value
public class ProjectInfo {
    
    String projectName;
    
    String adminId;
    
    Map<String, String> documents;
    
    String githubUrl;
    
    String scrumCall;
    
    String sprintEndDate;
    
    String sprintStartDate;
    
    String standupCall;
    
}
