package com.spring.boot.study.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.boot.study.common.RequestMatcher;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 通用错误处理器.
 *
 * @author Wang.ch
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class SelfExceptionHandlerResolver extends AbstractErrorController {

    private static final Logger log = LoggerFactory.getLogger(SelfExceptionHandlerResolver.class);

    @Value("${server.error.path:${error.path:/error}}")
    private String ERROR_PATH;
    private ErrorAttributes errorAttributes;
    @Value("${server.error.response-json-include-trace}")
    private boolean includeTrace = true;

    public SelfExceptionHandlerResolver(ErrorAttributes errorAttributes) {
        super(errorAttributes);
        this.errorAttributes = errorAttributes;
    }


    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping
    public ModelAndView handleApplicationJsonError(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return jsonResponseData(request, null);
    }

    /**
     * 处理 content-type:text/html, text/plain application/x-www-form-urlencoded 请求的异常
     * 返回spring-boot默认error页面
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(produces = {MediaType.TEXT_HTML_VALUE, MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView handleHtmlError(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Exception exception = this.getException(request);
        HttpStatus status = this.getStatus(request, exception);
        Map<String, Object> model = Collections.unmodifiableMap(super.getErrorAttributes(request, true));
        response.setStatus(status.value());
        ModelAndView modelAndView = this.resolveErrorView(request, response, status, model);
        return modelAndView == null ? new ModelAndView("error", model) : modelAndView;
    }

    private ModelAndView jsonResponseData(HttpServletRequest request, Map<String, Object> responseData) {
        Exception exception = this.getException(request);
        HttpStatus status = this.getStatus(request, exception);
        Map<String, Object> model = super.getErrorAttributes(request, true);
        if(!includeTrace) {
            model.remove("trace");
        }
        if(responseData != null) {
            model.putAll(responseData);
        }
        ModelAndView jsonView = new ModelAndView();
        jsonView.setView(new MappingJackson2JsonView());
        jsonView.setStatus(status);
        jsonView.addAllObjects(model);
        return jsonView;
    }


    /**
     * 500错误.
     *
     * @param request
     * @param rsp
     * @param ex
     * @return
     * @throws Exception
     */
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ModelAndView serverError(HttpServletRequest request, HttpServletResponse rsp, Exception ex) throws Exception {
        RequestMatcher matcher = new RequestMatcher("/**");
        if (matcher.matches(request)) {
            return jsonResponseData(request, null);
        } else {
            throw ex;
        }
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ConstraintViolationException.class)
    public ModelAndView ConstraintViolationExceptionHandler(HttpServletRequest request, HttpServletResponse response,
                                                           ConstraintViolationException violationException) {
        Map<String, Object> errorParam = new HashMap<>();
        Iterator iterator = violationException.getConstraintViolations().iterator();
        while (iterator.hasNext()) {
            ConstraintViolation exception = (ConstraintViolation)iterator.next();
            String[] messages = exception.getMessage().split(":");
            errorParam.put(messages[0], messages[1]);
        }

        return jsonResponseData(request, errorParam);
    }

    /**
     * 404的拦截.
     *
     * @param request
     * @param response
     * @param ex
     * @return
     * @throws Exception
     */
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> notFound(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception {
//        log.error("!!! request uri:{} from {} not found exception:{}", request.getRequestURI(), request.getRemoteHost(), ex.getMessage());
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//        String msg = mapper.writeValueAsString(BaseResponse.newFail(BaseResponse.STATUS_BADREQUEST, "你访问的资源不存在"));
        String msg = mapper.writeValueAsString("你访问的资源不存在");
        handleJSONError(response, msg, HttpStatus.OK);
        return null;
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(LoginException.class)
    public ResponseEntity<?> loginException(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception {
//        log.error("!!! request uri:{} from {} not found exception:{}", request.getRequestURI(), request.getRemoteHost(), ex.getMessage());
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//        String msg = mapper.writeValueAsString(BaseResponse.newFail(BaseResponse.STATUS_BADREQUEST, "你访问的资源不存在"));
        String msg = mapper.writeValueAsString(ex.getMessage());
        handleJSONError(response, msg, HttpStatus.OK);
        return null;
    }

    /**
     * 参数不完整错误.
     *
     * @param req
     * @param rsp
     * @param ex
     * @return
     * @throws Exception
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ModelAndView methodArgumentNotValidException(HttpServletRequest req, HttpServletResponse rsp, BindException ex) throws Exception {
        RequestMatcher matcher = new RequestMatcher("/**");
        if (matcher.matches(req)) {
            BindingResult result = ex.getBindingResult();
            List<FieldError> fieldErrors = result.getFieldErrors();
            Map<String, Object> errorParam = new HashMap<>();
            fieldErrors.stream().forEach(fieldError -> errorParam.put(fieldError.getField(), fieldError.getDefaultMessage()));
            return jsonResponseData(req, errorParam);
        } else {
            throw ex;
        }
    }


    protected ModelAndView handleJSONError(HttpServletResponse rsp, String errorMessage, HttpStatus status) throws IOException {
        rsp.setCharacterEncoding("UTF-8");
        rsp.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        rsp.setStatus(status.value());
        PrintWriter writer = rsp.getWriter();
        writer.write(errorMessage);
        writer.flush();
        writer.close();
        return null;
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }


    private <T extends Throwable> T getException(HttpServletRequest request) {
        T exception = (T)this.errorAttributes.getError(new ServletWebRequest(request));
        exception.printStackTrace();
        return exception;
    }

    public HttpStatus getStatus(HttpServletRequest request, Exception exception) {
        HttpStatus httpStatus = super.getStatus(request);
        if(exception != null && exception instanceof LoginException) {
            httpStatus = HttpStatus.valueOf(((LoginException) exception).getCode());
        }
        return httpStatus;
    }

}
