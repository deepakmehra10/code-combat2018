package com.knoldus;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProjectStats {
    
    String moduleName;
    
    String findBugs;
    
    String checkStyle;
    
    String coverageJacoco;
}
