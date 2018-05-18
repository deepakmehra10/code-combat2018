package com.knoldus.repository;

import akka.Done;
import com.datastax.driver.core.Row;
import com.knoldus.models.ProjectResource;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

public class ResourceRepositoryImpl implements Repository {
    
    private final CassandraSession session;
    
    @Inject
    public ResourceRepositoryImpl(CassandraSession session) {
        this.session = session;
    }
    
    
    @Override
    public CompletionStage<Done> addResource(ProjectResource projectResource) {
        return session.executeWrite("INSERT INTO knolway.employee(eid, name, designation,login_type, manager_id,project_name) " +
                        "VALUES(?,?,?,?,?,?)", projectResource.getEmployeeId(),
                projectResource.getEmployeeName(),
                projectResource.getEmployeeDesignation(),
                projectResource.getLoginType().toString(),
                projectResource.getManager(),
                projectResource.getProjectName());
    }
    
    @Override
    public CompletionStage<Done> deleteResource(Integer id) {
        return session.executeWrite("DELETE FROM knolway.employee where eid=? IF EXISTS", id);
    }
    
    @Override
    public CompletionStage<List<ProjectResource>> getResources(String managerId) {
        return session.selectAll("SELECT * from knolway.employee where manager_id=?", managerId)
                .thenApply(rowList -> rowList.stream()
                        .map(this::mapToProjectResource)
                        .collect(Collectors.toList()));
    }
    
    @Override
    public CompletionStage<List<ProjectResource>> getAllResources() {
        return session.selectAll("SELECT * from knolway.employee")
                .thenApply(rowList -> rowList.stream()
                        .map(this::mapToProjectResource)
                        .collect(Collectors.toList()));
    }
    
    private ProjectResource mapToProjectResource(Row row) {
        return ProjectResource.builder()
                .employeeId(row.getString("eid"))
                .employeeName(row.getString("name"))
                .employeeDesignation(row.getString("designation"))
                .projectName(row.getString("project_name"))
                .manager(row.getString("manager_id"))
                .build();
    }
}
