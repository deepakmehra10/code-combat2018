package com.knoldus;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.pathCall;

/**
 * The Admin service interface.
 * <p>
 * This describes everything that Lagom needs to know about how to serve and
 * consume the Admin.
 */
public interface AdminService extends Service {
    
    /**
     * Example: curl http://localhost:9000/api/hello/Alice
     */
    ServiceCall<NotUsed, String> helloAdmin(String id);
    
    
    /**
     * Example: curl -H "Content-Type: application/json" -X POST -d '{"message":
     * "Hi"}' http://localhost:9000/api/hello/Alice
     */
    
        @Override
        default Descriptor descriptor() {
            // @formatter:off
            return named("admin").withCalls(
                    pathCall("/api/admin/:id", this::helloAdmin)
            ).withAutoAcl(true);
            // @formatter:on
        }
}
