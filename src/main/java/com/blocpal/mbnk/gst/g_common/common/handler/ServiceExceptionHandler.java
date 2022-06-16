package com.blocpal.mbnk.gst.g_common.common.handler;



import com.blocpal.mbnk.gst.g_common.ServiceException;
import com.blocpal.mbnk.gst.g_common.ServiceResult;
import com.blocpal.mbnk.gst.g_common.ServiceStatus;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Produces
@Singleton
@Requires(classes = {ServiceException.class, ExceptionHandler.class})
public class ServiceExceptionHandler implements ExceptionHandler<ServiceException, HttpResponse<?>>{

    @Override
    public HttpResponse<?> handle(HttpRequest request, ServiceException ex) {

        if (null != ex)
            ex.printStackTrace();

        log.warn(ex.getMessage());

        Boolean logError = ex.getLogError();
        Integer code  = ex.getStatusCode();
        String message  = ex.getMessage();
        if (logError) {
            logException (ex,code, message, request);
        }

        Integer httpCode = getHttpCode(request, code);

        log.info("exception Status Code"+code);
        log.info ("httpCode : "+ httpCode);

        ServiceStatus status = new ServiceStatus(code,message);
        ServiceResult<Object> result = new ServiceResult<Object>(null,status);
        return getResponse (httpCode, result);
    }

    /*
     * Log Exception to Sentry and Database
     */
    void logException(Exception ex,Integer code, String msg,
                      HttpRequest<?> request ){

//        Sentry.captureException(ex);
//
//        String appName =  AppConstants.APP_NAME;
//        ExceptionLog error = CommonUtility.getException(appName,
//                code, msg, request);
//        errorDao.logError(error);
    }

    /*
     * getResponse()
     */
    public static HttpResponse<?> getResponse(Integer httpCode, ServiceResult<Object> result) {

        HttpResponse<?> response = null;
        if ( null == httpCode)
            response = HttpResponse.ok(result);
        else if (httpCode == HttpStatus.BAD_REQUEST.getCode())
            response = HttpResponse.badRequest(result);
        else if (httpCode == HttpStatus.NOT_FOUND.getCode())
            response =HttpResponse.notFound(result);
        else if(httpCode == HttpStatus.INTERNAL_SERVER_ERROR.getCode() )
            response = HttpResponse.serverError(result);
        else if (httpCode == HttpStatus.FORBIDDEN.getCode())
            response = HttpResponse.status(HttpStatus.FORBIDDEN);
        else if (httpCode == HttpStatus.UNAUTHORIZED.getCode())
            response = HttpResponse.unauthorized();
        else
            response = HttpResponse.ok(result);

        return response;
    }

    public  Integer getHttpCode(HttpRequest<?> request, Integer code) {
        return 200;
    }

}
