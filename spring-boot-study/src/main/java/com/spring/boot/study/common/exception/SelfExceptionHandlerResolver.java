package com.spring.boot.study.common.exception;

import com.spring.boot.study.common.utils.RequestMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

/**
 * 通用错误处理器.
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
        Map<String, Object> modal = null;
        if((int)request.getAttribute("javax.servlet.error.status_code") == HttpStatus.NOT_FOUND.value()) {
            modal = new HashMap<>();
            modal.put("errorMessage", "访问的资源不存在");
        }
        return jsonResponseData(request, modal);
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
        modelAndView = modelAndView == null ? new ModelAndView("error", model) : modelAndView;
        if((int)request.getAttribute("javax.servlet.error.status_code") == HttpStatus.NOT_FOUND.value()) {
            modelAndView.addObject("errorMessage", "访问的资源不存在");
        }
        return modelAndView;
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

    /**
     * 处理简单参数验证异常
     * @param request
     * @param response
     * @param violationException
     * @return
     */
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ConstraintViolationException.class)
    public ModelAndView constraintViolationExceptionHandler(HttpServletRequest request,
                                                            HttpServletResponse response,
                                                           ConstraintViolationException violationException) {
        Map<String, Object> errorParam = new HashMap<>();
        Iterator iterator = violationException.getConstraintViolations().iterator();
        int count = 0;
        while (iterator.hasNext()) {
            ConstraintViolation exception = (ConstraintViolation)iterator.next();
            String errorMessage = exception.getMessage();
            if(errorMessage.contains(":")) {
                String[] messages = errorMessage.split(":");
                errorParam.put(messages[0], messages[1]);
            } else {
                errorParam.put("参数错误" + ++count, errorMessage);
            }
        }
        return jsonResponseData(request, errorParam);
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(LoginException.class)
    public ModelAndView loginException(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception {

        return jsonResponseData(request,null);
    }
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ModelAndView httpMessageNotReadableException(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.put("errorMessage", "请求参数不是标准json格式");
        return jsonResponseData(request,model);
    }

    /**
     * 参数不完整错误.
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BindException.class})
    public ModelAndView bindException(HttpServletRequest request, HttpServletResponse response, BindException ex) throws Exception {
        RequestMatcher matcher = new RequestMatcher("/**");
        if (matcher.matches(request)) {
            BindingResult result = ex.getBindingResult();
            List<FieldError> fieldErrors = result.getFieldErrors();
            Map<String, Object> errorParam = new HashMap<>();
            fieldErrors.stream().forEach(fieldError -> errorParam.put(fieldError.getField(), fieldError.getDefaultMessage()));
            return jsonResponseData(request, errorParam);
        } else {
            throw ex;
        }
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ModelAndView methodArgumentNotValidException(HttpServletRequest request, HttpServletResponse response, MethodArgumentNotValidException ex) throws Exception {
        return bindException(request, response, new BindException(ex.getBindingResult()));
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

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }


    private <T extends Throwable> T getException(HttpServletRequest request) {
        T exception = (T)this.errorAttributes.getError(new ServletWebRequest(request));
        if(exception != null) {
            exception.printStackTrace();
            return exception;
        }
        return null;
    }

    public HttpStatus getStatus(HttpServletRequest request, Exception exception) {
        HttpStatus httpStatus = super.getStatus(request);
        if(exception != null && exception instanceof LoginException) {
            httpStatus = HttpStatus.valueOf(((LoginException) exception).getCode());
        }
        return httpStatus;
    }

}
