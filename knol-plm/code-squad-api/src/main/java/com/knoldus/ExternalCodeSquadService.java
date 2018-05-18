package com.knoldus;

import akka.NotUsed;
import com.fasterxml.jackson.databind.JsonNode;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import static com.lightbend.lagom.javadsl.api.Service.pathCall;
import static com.lightbend.lagom.javadsl.api.Service.named;

public interface ExternalCodeSquadService extends Service {
    
    ServiceCall<NotUsed, JsonNode> getCodeSquadReport(String userName);
    
    @Override
    default Descriptor descriptor() {
        return named("external-codesquad").withCalls(
                pathCall("/dashboard/:userName", this::getCodeSquadReport)
        ).withAutoAcl(true);
    }
}
