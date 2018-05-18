package com.knoldus;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import java.util.List;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.pathCall;

/**
 * The Admin service interface.
 * <p>
 * This describes everything that Lagom needs to know about how to serve and
 * consume the Admin.
 */
public interface CodeSquadService extends Service {
    
    ServiceCall<NotUsed, List<ProjectStats>> viewCodeSquadReport();
    
    @Override
    default Descriptor descriptor() {
        // @formatter:off
        return named("code-squad-service").withCalls(
                pathCall("/api/viewReport", this::viewCodeSquadReport)
        ).withAutoAcl(true);
        // @formatter:on
    }
}
