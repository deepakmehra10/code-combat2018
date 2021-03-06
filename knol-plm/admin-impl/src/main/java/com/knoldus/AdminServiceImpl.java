package com.knoldus;

import akka.Done;
import akka.NotUsed;
import akka.japi.Pair;
import com.knoldus.models.LoginType;
import com.knoldus.models.ProjectInfo;
import com.knoldus.models.ProjectResource;
import com.knoldus.models.ProjectUpdateParams;
import com.knoldus.repository.Repository;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.deser.ExceptionMessage;
import com.lightbend.lagom.javadsl.api.transport.ResponseHeader;
import com.lightbend.lagom.javadsl.api.transport.TransportErrorCode;
import com.lightbend.lagom.javadsl.api.transport.TransportException;
import com.lightbend.lagom.javadsl.server.HeaderServiceCall;
import org.springframework.util.CollectionUtils;
import play.Logger;

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
            CompletionStage<Done> doneCompletionStage = repository.addResource(req);
            doneCompletionStage.thenRunAsync(() -> EmailUtil.sendSimpleMessage(req));
            
            return doneCompletionStage
                    .thenApply(done -> Pair.apply(ResponseHeader.OK.withStatus(201), "Project resource inserted."))
                    .exceptionally(throwable -> {
                        Logger.error(throwable.getMessage());
                        throw new TransportException(TransportErrorCode.InternalServerError,
                                new ExceptionMessage("FAILURE", "Add resource failed."));
                    });
        };
    }
    
    @Override
    public HeaderServiceCall<NotUsed, String> deleteResource(String employeeId, String role) {
        return (rh, req) -> {
            if (!LoginType.SUPER_ADMIN.toString().equalsIgnoreCase(role)) {
                throw new TransportException(TransportErrorCode.BadRequest,
                        new ExceptionMessage("FAILURE", "Only SUPER_ADMIN user can delete resources."));
            }
            
            return repository.deleteResource(employeeId)
                    .thenApply(done -> Pair.apply(ResponseHeader.OK.withStatus(204), "Project resource deleted."))
                    .exceptionally(throwable -> {
                        Logger.error(throwable.getMessage());
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
    
    @Override
    public HeaderServiceCall<NotUsed, String> removeFromProject(String eid) {
        return (rh, req) -> {
            return repository.getByEmployeeId(eid)
                    .thenCompose(rows -> {
                        if (CollectionUtils.isEmpty(rows)) {
                            throw new TransportException(TransportErrorCode.BadRequest,
                                    new ExceptionMessage("FAILURE", eid + " does not exist."));
                        }
                        return this.updateAdminAndProject(eid).invokeWithHeaders(rh, ProjectUpdateParams.builder().build());
                    });
        };
    }
    
    @Override
    public HeaderServiceCall<ProjectUpdateParams, String> updateAdminAndProject(String eid) {
        return (rh, req) -> {
            return repository.updateAdminAndProject(eid, req.getManagerId(), req.getProjectName())
                    .thenApply(done -> Pair.apply(ResponseHeader.OK, "Admin and project is updated for " + eid + "."))
                    .exceptionally(throwable -> {
                        
                        throw new TransportException(TransportErrorCode.InternalServerError,
                                new ExceptionMessage("FAILURE", eid + " admin and projectName could not be updated."));
                    });
        };
    }
    
    @Override
    public ServiceCall<NotUsed, ProjectInfo> getProjectInfo(String projectName) {
        return req -> {
            return repository.getProjectInfo(projectName)
                    .thenApply(optionalDoc -> optionalDoc
                            .<RuntimeException>orElseThrow(() -> new TransportException(TransportErrorCode.InternalServerError,
                                    new ExceptionMessage("FAILURE", "Project " + projectName + " does not exist."))));
        };
    }
    
    @Override
    public HeaderServiceCall<ProjectInfo, String> postProjectInfo() {
        return (rh, req) -> {
            return repository.addProjectInfo(req)
                    .thenApply(done -> Pair.apply(ResponseHeader.OK.withStatus(201), req.getProjectName() + " docs has been added."))
                    .exceptionally(throwable -> {
                        Logger.error(throwable.getMessage());
                        throw new TransportException(TransportErrorCode.InternalServerError,
                                new ExceptionMessage("FAILURE", String.format("Add details failed for %s project", req.getProjectName())));
                    });
        };
    }
}
