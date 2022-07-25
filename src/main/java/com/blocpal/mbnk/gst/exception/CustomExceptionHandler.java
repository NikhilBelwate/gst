package com.blocpal.mbnk.gst.exception;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import javax.inject.Singleton;
import java.util.Map;

@Produces
@Singleton
@Requires(classes = { GstException.class, ExceptionHandler.class })
public class CustomExceptionHandler
        implements ExceptionHandler<GstException, HttpResponse<Map>> {

    @Override
    public HttpResponse<Map> handle(HttpRequest request, GstException exception) {

        return HttpResponse.serverError(exception.getExceptionDetails()).
                status(HttpStatus.BAD_REQUEST);
    }
}
