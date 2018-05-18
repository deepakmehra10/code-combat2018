package com.knoldus;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

public class CodeSquadModule extends AbstractModule implements ServiceGuiceSupport {
    @Override
    protected void configure() {
        bindService(CodeSquadService.class, CodeSquadServiceImpl.class);
        bindClient(ExternalCodeSquadService.class);
    }
}