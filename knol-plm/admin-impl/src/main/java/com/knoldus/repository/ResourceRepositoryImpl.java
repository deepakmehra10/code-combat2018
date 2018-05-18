package com.knoldus.repository;

import akka.Done;
import com.datastax.driver.core.Row;
import com.knoldus.models.ProjectInfo;
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
    public CompletionStage<Optional<ProjectInfo>> getProjectInfo(String projectName) {
        return session.selectOne("SELECT * from knolway.project where name=?", projectName)
                .thenApply(optionalRow -> optionalRow.map(this::mapToProjectInfo));
    }
    
    @Override
    public CompletionStage<Done> addProjectInfo(ProjectInfo projectInfo) {
        return session.executeWrite("INSERT INTO knolway.project(name,admin_id,documents,github_url," +
                        "scrum_call,sprint_end_date,sprint_start_date,standup_call) " +
                        "VALUES(?,?,?,?,?,?,?,?)",
                projectInfo.getProjectName(), projectInfo.getAdminId(), projectInfo.getDocuments(),
                projectInfo.getGithubUrl(), projectInfo.getScrumCall(), projectInfo.getSprintEndDate(),
                projectInfo.getSprintStartDate(), projectInfo.getStandupCall());
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
    
    private ProjectInfo mapToProjectInfo(Row row) {
        return ProjectInfo.builder()
                .adminId(row.getString("admin_id"))
                .documents(row.getMap("documents", String.class, String.class))
                .githubUrl(row.getString("github_url"))
                .projectName(row.getString("name"))
                .scrumCall(row.getString("scrum_call"))
                .sprintEndDate(row.getString("sprint_end_date"))
                .sprintStartDate(row.getString("sprint_start_date"))
                .standupCall(row.getString("standup_call"))
                .build();
    }
}
