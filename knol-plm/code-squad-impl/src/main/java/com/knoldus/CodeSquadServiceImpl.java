package com.knoldus;

import akka.NotUsed;
import com.fasterxml.jackson.databind.JsonNode;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class CodeSquadServiceImpl implements CodeSquadService {
    
    private final ExternalCodeSquadService codeSquadService;
    
    @Inject
    CodeSquadServiceImpl(ExternalCodeSquadService codeSquadService) {
        this.codeSquadService = codeSquadService;
    }
    
    @Override
    public ServiceCall<NotUsed, List<ProjectStats>> viewCodeSquadReport() {
        return req ->
                codeSquadService.getCodeSquadReport("abhishekg")
                        .invoke()
                        .thenApply(this::parseJson);
    }
    
    public List<ProjectStats> parseJson(JsonNode originalJson) {
        
        JsonNode modules = originalJson.get("data")
                .get("reports").get(0)
                .get("organisationReports").get(0)
                .get("moduleInformation");
        
        List<ProjectStats> projectStats = new ArrayList<>();
        
        for (JsonNode module : modules) {
            
            String bugs = module.get("data").get("findBugsReport") != null ?
                    (module.get("data").get("findBugsReport").get("result") != null ?
                            module.get("data").get("findBugsReport").get("result").textValue() : null) : null;
            
            String style = module.get("data").get("styleWarningReport") != null ?
                    (module.get("data").get("styleWarningReport").get("result") != null ?
                            module.get("data").get("styleWarningReport").get("result").textValue() : null) : null;
            
            String jacoco = module.get("data").get("jacocoReport") != null ?
                    (module.get("data").get("jacocoReport").get("result") != null ?
                            module.get("data").get("jacocoReport").get("result").textValue() : null) : null;
            
            ProjectStats projectStat = ProjectStats.builder()
                    .moduleName(module.get("moduleName").textValue())
                    .findBugs(bugs)
                    .checkStyle(style)
                    .coverageJacoco(jacoco)
                    .build();
            
            projectStats.add(projectStat);
        }
        return projectStats;
        
    }
    
}
