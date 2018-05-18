package com.knoldus.models;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ProjectUpdateParams {
    
    String managerId;
    
    String projectName;
}
