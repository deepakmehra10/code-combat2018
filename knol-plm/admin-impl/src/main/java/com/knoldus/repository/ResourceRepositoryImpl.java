package com.knoldus.repository;

import akka.Done;
import com.knoldus.models.ProjectResource;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

public class ResourceRepositoryImpl implements Repository {
    
    private final CassandraSession session;
    
    @Inject
    public ResourceRepositoryImpl(CassandraSession session) {
        this.session = session;
    }
    
    
    @Override
    public CompletionStage<Done> addResource(ProjectResource projectResource) {
        return session.executeWrite("INSERT INTO knol_plm.employee(eid, name, designation,login_type, manager,project_name) " +
                        "VALUES(?,?,?,?,?,?)", projectResource.getEmployeeId(),
                projectResource.getEmployeeName(),
                projectResource.getEmployeeDesignation(),
                projectResource.getLoginType().toString(),
                projectResource.getManager(),
                projectResource.getProjectName());
    }
    
    @Override
    public CompletionStage<Done> deleteResource(Integer id) {
        return session.executeWrite("DELETE FROM knol_plm.employee where eid=? IF EXISTS", id);
    }
}
