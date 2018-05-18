package com.knoldus;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.ServiceCall;

public class CodeSquadServiceImpl implements CodeSquadService {
    @Override
    public ServiceCall<NotUsed, String> viewCodeSquadReport(String githubUrl) {
        return null;
    }
    
    @Override
    public Descriptor descriptor() {
        return null;
    }
}
