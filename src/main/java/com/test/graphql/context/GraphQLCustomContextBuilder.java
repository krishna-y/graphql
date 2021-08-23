package com.test.graphql.context;

import graphql.kickstart.execution.context.DefaultGraphQLContext;
import graphql.kickstart.execution.context.GraphQLContext;
import graphql.kickstart.servlet.context.DefaultGraphQLServletContext;
import graphql.kickstart.servlet.context.GraphQLServletContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;

@Component
public class GraphQLCustomContextBuilder implements GraphQLServletContextBuilder {
    @Autowired
    DataLoaderRegistryFactory dataLoaderRegistryFactory;

    @Override
    public GraphQLContext build(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse) {
        DefaultGraphQLContext context = DefaultGraphQLServletContext.createServletContext()
                .with(httpServletRequest)
                .with(httpServletResponse)
                .with(dataLoaderRegistryFactory.create())
                .build();
        return context;
    }

    @Override
    public GraphQLContext build(Session session, HandshakeRequest handshakeRequest) {
        throw new IllegalStateException("Un Supported");
    }

    @Override
    public GraphQLContext build() {
        throw new IllegalStateException("Un Supported");
    }
}
