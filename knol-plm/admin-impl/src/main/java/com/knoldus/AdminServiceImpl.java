package com.knoldus;

import akka.Done;
import akka.NotUsed;
import akka.japi.Pair;
import com.knoldus.models.ProjectResource;
import com.knoldus.repository.Repository;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.ResponseHeader;
import com.lightbend.lagom.javadsl.server.HeaderServiceCall;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;

/**
 * Implementation of the AdminService.
 */
public class AdminServiceImpl implements AdminService {
    
    private final Repository repository;
    
    @Inject
    public AdminServiceImpl(Repository repository) {
        
        this.repository = repository;
        
    }
    
    @Override
    public ServiceCall<NotUsed, String> helloAdmin(String id) {
        return request -> CompletableFuture.completedFuture("Admin," + id);
    }
    
    @Override
    public HeaderServiceCall<ProjectResource, Done> addResource() {
        return (rh, req) -> {
            return repository.addResource(req)
                    .thenApply(done -> Pair.apply(ResponseHeader.OK.withStatus(201), done));
        };
    }
    
    @Override
    public HeaderServiceCall<NotUsed, Done> deleteResource(Integer employeeId) {
        return (rh, req) -> {
            return repository.deleteResource(employeeId)
                    .thenApply(done -> Pair.apply(ResponseHeader.OK.withStatus(204), done));
        };
    }
}
