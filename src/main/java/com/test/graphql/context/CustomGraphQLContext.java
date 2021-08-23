package com.test.graphql.context;

import graphql.kickstart.servlet.context.GraphQLServletContext;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.dataloader.DataLoaderRegistry;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public class CustomGraphQLContext implements GraphQLServletContext {
    private final GraphQLServletContext graphQLServletContext;
    @Override
    public List<Part> getFileParts() {
        return getFileParts();
    }

    @Override
    public Map<String, List<Part>> getParts() {
        return getParts();
    }

    @Override
    public HttpServletRequest getHttpServletRequest() {
        return getHttpServletRequest();
    }

    @Override
    public HttpServletResponse getHttpServletResponse() {
        return getHttpServletResponse();
    }

    @Override
    public Optional<Subject> getSubject() {
        return Optional.empty();
    }

    @Override
    public Optional<DataLoaderRegistry> getDataLoaderRegistry() {
        return Optional.empty();
    }
}
