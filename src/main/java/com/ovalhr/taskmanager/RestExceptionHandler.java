package com.ovalhr.taskmanager;

import com.ovalhr.taskmanager.mapper.Response;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletException;

/**
 * Created by rana on 6/12/20.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends RuntimeException  {


//    /**
//     * Handle MissingServletRequestParameterException. Triggered when a 'required' request parameter is missing.
//     *
//     * @param ex      MissingServletRequestParameterException
//     * @param headers HttpHeaders
//     * @param status  HttpStatus
//     * @param request WebRequest
//     * @return the ApiError object
//     */
//    @Override
//    protected ResponseEntity<Object> handleMissingServletRequestParameter(
//            MissingServletRequestParameterException ex, HttpHeaders headers,
//            HttpStatus status, WebRequest request) {
//        String error = ex.getParameterName() + " parameter is missing";
//        return buildResponseEntity(new Response(error, null));
//    }
//
//
//    /**
//     * Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is invalid as well.
//     *
//     * @param ex      HttpMediaTypeNotSupportedException
//     * @param headers HttpHeaders
//     * @param status  HttpStatus
//     * @param request WebRequest
//     * @return the ApiError object
//     */
//    @Override
//    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
//            HttpMediaTypeNotSupportedException ex,
//            HttpHeaders headers,
//            HttpStatus status,
//            WebRequest request) {
//        StringBuilder builder = new StringBuilder();
//        builder.append(ex.getContentType());
//        builder.append(" media type is not supported.");
//        //ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
//        return buildResponseEntity(new Response(builder.substring(0, builder.length() - 2), null));
//    }
//
//    /**
//     * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
//     *
//     * @param ex      the MethodArgumentNotValidException that is thrown when @Valid validation fails
//     * @param headers HttpHeaders
//     * @param status  HttpStatus
//     * @param request WebRequest
//     * @return the ApiError object
//     */
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(
//            MethodArgumentNotValidException ex,
//            HttpHeaders headers,
//            HttpStatus status,
//            WebRequest request) {
//        StringBuilder builder = new StringBuilder();
//        builder.append("Validation error");
//        builder.append(ex.getBindingResult().getFieldErrors()+": ");
//        builder.append(ex.getBindingResult().getGlobalErrors()+"\n");
//        return buildResponseEntity(new Response(builder.toString(), null));
//    }

    /**
     * Handles javax.validation.ConstraintViolationException. Thrown when @Validated fails.
     *
     * @param ex the ConstraintViolationException
     * @return the ApiError object
     */
//    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
//    protected ResponseEntity<Object> handleConstraintViolation(
//            javax.validation.ConstraintViolationException ex) {
//        ex.printStackTrace();
//        StringBuilder builder = new StringBuilder();
//        builder.append("Validation error");
//        builder.append(ex.getConstraintViolations());
//        return buildResponseEntity(new Response(builder.toString(), null));
//    }

    /**
     * Handles EntityNotFoundException. Created to encapsulate errors with more detail than javax.persistence.EntityNotFoundException.
     *
     * @param ex the EntityNotFoundException
     * @return the ApiError object
     */
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(
            EntityNotFoundException ex) {
        ex.printStackTrace();
        return buildResponseEntity(new Response(ex.getMessage(), null));
    }

//    /**
//     * Handle HttpMessageNotReadableException. Happens when request JSON is malformed.
//     *
//     * @param ex      HttpMessageNotReadableException
//     * @param headers HttpHeaders
//     * @param status  HttpStatus
//     * @param request WebRequest
//     * @return the ApiError object
//     */
//    @Override
//    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
////        log.info("{} to {}", servletWebRequest.getHttpMethod(), servletWebRequest.getRequest().getServletPath());
//        return buildResponseEntity(new Response("Malformed JSON request", null));
//    }

//    /**
//     * Handle HttpMessageNotWritableException.
//     *
//     * @param ex      HttpMessageNotWritableException
//     * @param headers HttpHeaders
//     * @param status  HttpStatus
//     * @param request WebRequest
//     * @return the ApiError object
//     */
//    @Override
//    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        String error = "Error writing JSON output";
//        return buildResponseEntity(new Response(error, null));
//    }
//
//    /**
//     * Handle NoHandlerFoundException.
//     *
//     * @param ex
//     * @param headers
//     * @param status
//     * @param request
//     * @return
//     */
//    @Override
//    protected ResponseEntity<Object> handleNoHandlerFoundException(
//            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        StringBuilder builder = new StringBuilder();
//        builder.append(String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));
//        builder.append("\n"+ex.getMessage());
//        return buildResponseEntity(new Response(ex.getMessage(), null));
//    }


    /**
     * Handle DataIntegrityViolationException, inspects the cause for different DB causes.
     *
     * @param ex the DataIntegrityViolationException
     * @return the ApiError object
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex,
                                                                  WebRequest request) {
        if (ex.getCause() instanceof ConstraintViolationException) {
            ex.printStackTrace();
            return buildResponseEntity(new Response("Database error"+ex.getCause(), null));
        }
        return buildResponseEntity(new Response(ex.getMessage(), null));
    }

    /**
     * Handle Exception, handle generic Exception.class
     *
     * @param ex the Exception
     * @return the ApiError object
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                      WebRequest request) {
        ex.printStackTrace();
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'", ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));
        builder.append("\n"+ex.getMessage());
        return buildResponseEntity(new Response(ex.getMessage(), null));
    }

    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<Object> handleEntityNotFound(NullPointerException ex) {
        ex.printStackTrace();
        return buildResponseEntity(new Response(ex.getMessage(), null));
    }

    @ExceptionHandler(ServletException.class)
    protected ResponseEntity<Object> handleEntityNotFound(ServletException ex) {
        ex.printStackTrace();
        return buildResponseEntity(new Response(ex.getMessage(), null));
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<Object> handleEntityNotFound(RuntimeException ex) {
        ex.printStackTrace();
        return buildResponseEntity(new Response(ex.getMessage(), null));
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    protected ResponseEntity<Object> unauthorized(RuntimeException ex) {
        ex.printStackTrace();
        return buildResponseEntity(new Response(ex.getMessage(), null));
    }


    private ResponseEntity<Object> buildResponseEntity(Response apiError) {
        return new ResponseEntity<Object>(apiError, HttpStatus.OK);
    }
}
