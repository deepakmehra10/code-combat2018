package com.knoldus;

import akka.NotUsed;
import com.knoldus.models.Docs;
import com.knoldus.models.ProjectResource;
import com.knoldus.models.ProjectUpdateParams;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;

import java.util.List;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.pathCall;
import static com.lightbend.lagom.javadsl.api.Service.restCall;

/**
 * The Admin service interface.
 * <p>
 * This describes everything that Lagom needs to know about how to serve and
 * consume the Admin.
 */
public interface AdminService extends Service {
    
    ServiceCall<NotUsed, String> helloAdmin(String id);
    
    ServiceCall<ProjectResource, String> addResource();
    
    ServiceCall<NotUsed, String> deleteResource(String id, String role);
    
    ServiceCall<NotUsed, List<ProjectResource>> getResources(String managerId, String loginType);
    
    ServiceCall<NotUsed, String> removeFromProject(String eid); //removes project_name and manager_id
    
    ServiceCall<ProjectUpdateParams, String> updateAdminAndProject(String eid);
    
    ServiceCall<NotUsed, Docs> getDocsInfo(String projectName);
    
    ServiceCall<Docs, String> postDocs();
    
    @Override
    default Descriptor descriptor() {
        // @formatter:off
        return named("admin-service").withCalls(
                pathCall("/api/admin/:id", this::helloAdmin),
                restCall(Method.POST, "/api/admin/addResource", this::addResource),
                restCall(Method.DELETE, "/api/admin/deleteResource/:id/logintype/:role", this::deleteResource),
                restCall(Method.GET, "/api/admin/getResource/email/:emailId/loginType/:loginType",
                        this::getResources),
                restCall(Method.DELETE, "/api/admin/remove/:id", this::removeFromProject),
                restCall(Method.PUT, "/api/admin/project/update/:id", this::updateAdminAndProject),
                restCall(Method.GET, "/api/admin/docs/:project", this::getDocsInfo),
                restCall(Method.POST, "/api/admin/docs", this::postDocs)
        ).withAutoAcl(true);
        // @formatter:on
    }
}
