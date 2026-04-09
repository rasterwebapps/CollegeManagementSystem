package com.cms.config;

import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.cms.exception.ResourceNotFoundException;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;

@ControllerAdvice
public class GraphQLExceptionHandler {

    @GraphQlExceptionHandler(ResourceNotFoundException.class)
    public GraphQLError handleNotFound(ResourceNotFoundException ex, DataFetchingEnvironment env) {
        return GraphqlErrorBuilder.newError(env)
            .message(ex.getMessage())
            .errorType(graphql.ErrorType.DataFetchingException)
            .build();
    }

    @GraphQlExceptionHandler(IllegalArgumentException.class)
    public GraphQLError handleIllegalArgument(IllegalArgumentException ex, DataFetchingEnvironment env) {
        return GraphqlErrorBuilder.newError(env)
            .message(ex.getMessage())
            .errorType(graphql.ErrorType.ValidationError)
            .build();
    }

    @GraphQlExceptionHandler(IllegalStateException.class)
    public GraphQLError handleIllegalState(IllegalStateException ex, DataFetchingEnvironment env) {
        return GraphqlErrorBuilder.newError(env)
            .message(ex.getMessage())
            .errorType(graphql.ErrorType.ValidationError)
            .build();
    }

    @GraphQlExceptionHandler(AccessDeniedException.class)
    public GraphQLError handleAccessDenied(AccessDeniedException ex, DataFetchingEnvironment env) {
        return GraphqlErrorBuilder.newError(env)
            .message("Access denied")
            .errorType(graphql.ErrorType.ExecutionAborted)
            .build();
    }
}
