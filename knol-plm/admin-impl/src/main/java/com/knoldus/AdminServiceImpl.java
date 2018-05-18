package com.knoldus;

import akka.NotUsed;
import akka.japi.Pair;
import com.knoldus.models.LoginType;
import com.knoldus.models.ProjectResource;
import com.knoldus.repository.Repository;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.deser.ExceptionMessage;
import com.lightbend.lagom.javadsl.api.transport.ResponseHeader;
import com.lightbend.lagom.javadsl.api.transport.TransportErrorCode;
import com.lightbend.lagom.javadsl.api.transport.TransportException;
import com.lightbend.lagom.javadsl.server.HeaderServiceCall;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

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
    public HeaderServiceCall<ProjectResource, String> addResource() {
        return (rh, req) -> {
            return repository.addResource(req)
                    .thenApply(done -> Pair.apply(ResponseHeader.OK.withStatus(201), "Project resource inserted."))
                    .exceptionally(throwable -> {
                        System.out.println("\n\n" + throwable.getMessage());
                        throw new TransportException(TransportErrorCode.InternalServerError,
                                new ExceptionMessage("FAILURE", "Add resource failed."));
                    });
        };
    }
    
    @Override
    public HeaderServiceCall<NotUsed, String> deleteResource(Integer employeeId) {
        return (rh, req) -> {
            return repository.deleteResource(employeeId)
                    .thenApply(done -> Pair.apply(ResponseHeader.OK.withStatus(204), "Project resource deleted."))
                    .exceptionally(throwable -> {
                        System.out.println("\n\n" + throwable.getMessage());
                        throw new TransportException(TransportErrorCode.InternalServerError,
                                new ExceptionMessage("FAILURE", "Delete resource failed."));
                    });
        };
    }
    
    @Override
    public HeaderServiceCall<NotUsed, List<ProjectResource>> getResources(String managerId, String loginType) {
        return (rh, req) -> {
            CompletionStage<List<ProjectResource>> list;
            if (LoginType.SUPER_ADMIN.toString().equalsIgnoreCase(loginType)) {
                list = repository.getAllResources();
            } else {
                list = repository.getResources(managerId, loginType);
            }
            
            return list.thenApply(resources -> Pair.apply(ResponseHeader.OK, resources));
        };
    }
}
