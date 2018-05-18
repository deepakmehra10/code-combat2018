package com.knoldus.repository;

import akka.Done;
import com.datastax.driver.core.Row;
import com.knoldus.models.Docs;
import com.knoldus.models.ProjectResource;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public CompletionStage<Done> deleteResource(String id) {
        return session.executeWrite("DELETE FROM knolway.employee where eid=? IF EXISTS", id);
    }
    
    @Override
    public CompletionStage<List<ProjectResource>> getResources(String managerId, String loginType) {
        return this.getByEmployeeId(managerId)
                .thenCombineAsync(session.selectAll("SELECT * from knolway.employee where manager_id=?", managerId),
                        (eidRows, managerRows) -> {
                            if (eidRows.isEmpty() || !(eidRows.get(0).getString("login_type").equalsIgnoreCase(loginType))) {
                                return new ArrayList<Row>();
                            }
                            List<Row> rows = new ArrayList<>(eidRows);
                            rows.addAll(managerRows);
                            return rows;
                        })
                .thenApply(rowList -> rowList.stream()
                        .map(this::mapToProjectResource)
                        .collect(Collectors.toList()));
    }
    
    public CompletionStage<List<Row>> getByEmployeeId(String eid) {
        return session.selectAll("SELECT * from knolway.employee where eid=?", eid);
    }
    
    @Override
    public CompletionStage<Done> updateAdminAndProject(String eid, String managerId, String project) {
        return session.executeWrite("UPDATE knolway.employee SET manager_id=? ,project_name=? where eid=? IF EXISTS",
                managerId, project, eid);
    }
    
    @Override
    public CompletionStage<Optional<Docs>> getProjectDocs(String projectName) {
        return session.selectOne("SELECT * from knolway.project where name=?", projectName)
                .thenApply(optionalRow -> optionalRow.map(this::mapToDocs));
    }
    
    @Override
    public CompletionStage<Done> addProjectDocs(Docs docs) {
        return  session.executeWrite("INSERT INTO knolway.project(name,admin_id,documents,github_url) " +
                "VALUES(?,?,?,?)", docs.getProjectName(), docs.getAdminId(),docs.getDocuments(),docs.getGithubUrl());
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
    
    private Docs mapToDocs(Row row){
        return Docs.builder()
                .adminId(row.getString("admin_id"))
                .documents(row.getMap("documents", String.class,String.class))
                .githubUrl(row.getString("github_url"))
                .projectName(row.getString("name"))
                .build();
    }
}
