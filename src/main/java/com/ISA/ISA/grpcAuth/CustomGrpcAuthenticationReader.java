package com.ISA.ISA.grpcAuth;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component
public class CustomGrpcAuthenticationReader implements GrpcAuthenticationReader {

    @Nullable
    @Override
    public Authentication readAuthentication(ServerCall<?, ?> serverCall, Metadata metadata) throws AuthenticationException {
        return null;
    }
}
