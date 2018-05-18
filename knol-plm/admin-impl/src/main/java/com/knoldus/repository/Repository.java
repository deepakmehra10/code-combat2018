package com.knoldus.repository;

import akka.Done;
import com.datastax.driver.core.Row;
import com.knoldus.models.Docs;
import com.knoldus.models.ProjectResource;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

public interface Repository {
    
    CompletionStage<Done> addResource(ProjectResource projectResource);
    
    CompletionStage<Done> deleteResource(String id);
    
    CompletionStage<List<ProjectResource>> getResources(String managerId, String loginType);
    
    CompletionStage<List<ProjectResource>> getAllResources();
    
    CompletionStage<List<Row>> getByEmployeeId(String eid);
    
    CompletionStage<Done> updateAdminAndProject(String eid,String managerId, String project);
    
    CompletionStage<Optional<Docs>> getProjectDocs(String projectName);
    
    CompletionStage<Done> addProjectDocs(Docs docs);
    
}
