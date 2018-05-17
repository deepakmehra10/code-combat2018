package com.knoldus;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

/**
 * The module that binds the AdminService so that it can be served.
 */
public class AdminModule extends AbstractModule implements ServiceGuiceSupport {
    @Override
    protected void configure() {
        bindService(AdminService.class, AdminServiceImpl.class);
    }
}
