package com.knoldus.repository;

import akka.Done;
import com.knoldus.models.ProjectResource;

import java.util.List;
import java.util.concurrent.CompletionStage;

public interface Repository {
    
    CompletionStage<Done> addResource(ProjectResource projectResource);
    
    CompletionStage<Done> deleteResource(Integer id);
    
    CompletionStage<List<ProjectResource>> getResources(String managerId,String loginType);
    
    CompletionStage<List<ProjectResource>> getAllResources();
    
}
