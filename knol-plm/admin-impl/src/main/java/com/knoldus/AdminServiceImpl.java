package com.knoldus;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import java.util.concurrent.CompletableFuture;

/**
 * Implementation of the AdminService.
 */
public class AdminServiceImpl implements AdminService {
    
    @Override
    public ServiceCall<NotUsed, String> helloAdmin(String id) {
        return request -> CompletableFuture.completedFuture("Admin," + id);
    }
}
